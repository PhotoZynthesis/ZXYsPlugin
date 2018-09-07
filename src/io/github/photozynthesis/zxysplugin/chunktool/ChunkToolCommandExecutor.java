package io.github.photozynthesis.zxysplugin.chunktool;

import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.photozynthesis.zxysplugin.ZXYsPlugin;

public class ChunkToolCommandExecutor implements CommandExecutor {

	private ZXYsPlugin plugin;
	private ChunkToolBroadcastListener broadcastListener;
	private HashMap<Player, Chunk[]> map = new HashMap<Player, Chunk[]>();

	public ChunkToolCommandExecutor(ZXYsPlugin plugin) {
		super();
		this.plugin = plugin;
		broadcastListener = new ChunkToolBroadcastListener();
		plugin.getServer().getPluginManager().registerEvents(broadcastListener, plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// check whether the command matches
		if (!("chunktool".equalsIgnoreCase(label) || "chunk".equalsIgnoreCase(label))) {
			return false;
		}
		// check args length
		if (args == null || args.length > 2 || args.length < 1) {
			return false;
		}
		// check player
		if (sender == null || !(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		// invoke functions for individual command
		if ("register".equalsIgnoreCase(args[0]) && args.length == 2) {
			int radius = 0;
			try {
				radius = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				return false;
			}
			if (radius < 1 || 12 < radius) {
				return false;
			}
			return registerChunk(player, radius);
		} else if ("show".equalsIgnoreCase(args[0]) && args.length == 1) {
			return showChunkTrace(player);
		}
		return false;
	}

	public boolean registerChunk(Player player, int radius) {
		// get the location of current chunk and the amount of chunks to register
		Chunk currentChunk = player.getLocation().getChunk();
		int currentChunkX = currentChunk.getX();
		int currentChunkZ = currentChunk.getZ();
		int amount = 4 * radius * radius + 4 * radius + 1;
		// create registered chunks array and put chunks in
		Chunk[] registered = new Chunk[amount];
		World world = player.getWorld();
		int x = currentChunkX - radius;
		int z = currentChunkZ - radius;
		for (int count = 0; count < amount; count++) {
			registered[count] = world.getChunkAt(x + (count % (2 * radius + 1)), z + (count / (2 * radius + 1)));
		}
		// add the register information to map and send the success message
		map.put(player, registered);
		int side = 2 * radius + 1;
		player.sendMessage("[ChunkTool] §a成功注册以当前区块为中心的§6" + side + "x" + side + "=" + (side * side) + "§a个区块！");
		player.sendMessage("[ChunkTool] §a在任何位置使用 §6/<chunktool/chunk> show §a来查看区块的加载情况！");
		player.sendMessage("[ChunkTool] §a注意：区块注册信息保存在内存中，§6服务器关闭/重启后将会清除。");
		// return the method
		return true;
	}

	public boolean showChunkTrace(Player player) {
		if (!map.containsKey(player)) {
			player.sendMessage("[ChunkTool] §c你还没有注册区块！");
			return true;
		}
		Chunk[] registered = map.get(player);
		int rows = (int) Math.sqrt(registered.length);
		// print the above lines
		StringBuilder sb = null;
		for (int i = 0; i < rows / 2; i++) {
			sb = new StringBuilder("");
			for (int j = 0; j < rows; j++) {
				if (registered[i * rows + j].isLoaded()) {
					sb.append("§a#");
				} else {
					sb.append("§0#");
				}
			}
			player.sendMessage("[ChunkTool] " + sb.toString());
		}
		// print the middle line
		sb = new StringBuilder("");
		for (int j = 0; j < rows; j++) {
			if (j == rows / 2) {
				if (registered[(rows / 2) * rows + j].isLoaded()) {
					sb.append("§6$");
				} else {
					sb.append("§f$");
				}
				continue;
			}
			if (registered[(rows / 2) * rows + j].isLoaded()) {
				sb.append("§a#");
			} else {
				sb.append("§0#");
			}
		}
		player.sendMessage("[ChunkTool] " + sb.toString());
		// print the bottom lines
		for (int i = rows / 2 + 1; i < rows; i++) {
			sb = new StringBuilder("");
			for (int j = 0; j < rows; j++) {
				if (registered[i * rows + j].isLoaded()) {
					sb.append("§a#");
				} else {
					sb.append("§0#");
				}
			}
			player.sendMessage("[ChunkTool] " + sb.toString());
		}
		// show notifications
		player.sendMessage("[ChunkTool] Notes: §a#§r=加载的区块  §0#§r=卸载的区块");
		player.sendMessage("[ChunkTool] Notes: §6$§r=加载的中心区块  §f$§r=卸载的中心区块");
		return true;
	}
}

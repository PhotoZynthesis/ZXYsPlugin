package io.github.photozynthesis.zxysplugin.chunktool;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.photozynthesis.zxysplugin.ZXYsPlugin;

public class ChunkToolCommandExecutor implements CommandExecutor {

	// private ZXYsPlugin plugin;
	private ChunkToolBroadcastListener broadcastListener;
	private HashMap<String, Chunk[]> map = new HashMap<String, Chunk[]>();
	int regCount;

	public ChunkToolCommandExecutor(ZXYsPlugin plugin) {
		super();
		// this.plugin = plugin;
		broadcastListener = new ChunkToolBroadcastListener();
		plugin.getServer().getPluginManager().registerEvents(broadcastListener, plugin);
	}

	/**
	 * Resolves the command and invokes specific functions.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// check whether the command matches
		if (!"chunktool".equalsIgnoreCase(label)) {
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
		// reg
		if (("register".equalsIgnoreCase(args[0]) || "reg".equalsIgnoreCase(args[0])) && args.length == 2) {
			int radius = 0;
			try {
				radius = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				player.sendMessage("[ChunkTool] §c请输入有效的半径§a（0 - 12）§c！");
				return true;
			}
			if (radius < 0 || 12 < radius) {
				player.sendMessage("[ChunkTool] §c请输入有效的半径§a（0 - 12）§c！");
				return true;
			}
			return registerChunks(player, radius);
			// show
		} else if ("show".equalsIgnoreCase(args[0]) && args.length == 1) {
			return showChunkTrace(player, player.getName());
			// unreg
		} else if (("unregister".equalsIgnoreCase(args[0]) || "unreg".equalsIgnoreCase(args[0])) && args.length == 1) {
			return unregisterChunks(player);
			// unregall
		} else if (("unregisterall".equalsIgnoreCase(args[0]) || "unregall".equalsIgnoreCase(args[0]))
				&& args.length == 1) {
			if (!player.hasPermission("zxy.chunktool.op")) {
				player.sendMessage("[ChunkTool] §c只有OP可以清除所有玩家的区块注册 !");
				return true;
			}
			return unregisterAll(player);
			// showregs
		} else if ("showregs".equalsIgnoreCase(args[0]) || args.length == 1) {
			if (!player.hasPermission("zxy.chunktool.op")) {
				player.sendMessage("[ChunkTool] §c只有OP可以查看所有玩家的区块注册 !");
				return true;
			}
			return showRegs(player);
			// showRegOfOthers
		} else if ("show".equalsIgnoreCase(args[0]) || args.length == 2) {
			if (!player.hasPermission("zxy.chunktool.op")) {
				player.sendMessage("[ChunkTool] §c只有OP可以查看其他玩家注册区块的加载情况 !");
				return true;
			}
			return showRegOfOthers(player, args[1]);
		}
		return false;
	}

	/**
	 * Functions as its name.
	 * 
	 * @param player
	 * @param radius
	 * @return true when finished
	 */
	public boolean registerChunks(Player player, int radius) {
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
		map.put(player.getName(), registered);
		int side = 2 * radius + 1;
		player.sendMessage("[ChunkTool] §a成功注册以当前区块为中心的§6" + side + "x" + side + "=" + (side * side) + "§a个区块！");
		player.sendMessage("[ChunkTool] §a在任何位置使用 §6/<chunktool/chunk> show §a来查看区块的加载情况！");
		player.sendMessage("[ChunkTool] §c注意：§a区块注册信息保存在内存中，§6服务器关闭/重启后将会清除。");
		// return the method
		return true;
	}

	/**
	 * Prints the loaded status of registered chunks in chat area.
	 * 
	 * @param player -> player to send message to
	 * @param name -> name to query
	 * @return true when finished
	 */
	public boolean showChunkTrace(Player player, String name) {
		if (!map.containsKey(name)) {
			player.sendMessage("[ChunkTool] §c你还没有注册过区块！");
			return true;
		}
		Chunk[] registered = map.get(name);
		int rows = (int) Math.sqrt(registered.length);
		StringBuilder sb = null;
		// print the top navigate line
		sb = new StringBuilder("");
		for (int j = 0; j < rows; j++) {
			if (j == rows / 2) {
				sb.append("§bN ");
				continue;
			}
			if (j % 2 == 0) {
				sb.append("§9");
				sb.append(Integer.toHexString(Math.abs(j - rows / 2)));
				sb.append(" ");
			} else {
				sb.append("§c");
				sb.append(Integer.toHexString(Math.abs(j - rows / 2)));
				sb.append(" ");
			}
		}
		player.sendMessage("[ChunkTool] §d$ " + sb.toString());
		// print the above lines (with the left navigate line)
		for (int i = 0; i < rows / 2; i++) {
			sb = new StringBuilder("");
			if (i % 2 == 0) {
				sb.append("§9");
				sb.append(Integer.toHexString(rows / 2 - i));
				sb.append(" ");
			} else {
				sb.append("§c");
				sb.append(Integer.toHexString(rows / 2 - i));
				sb.append(" ");
			}
			for (int j = 0; j < rows; j++) {
				if (registered[i * rows + j].isLoaded()) {
					sb.append("§a# ");
				} else {
					sb.append("§0# ");
				}
			}
			player.sendMessage("[ChunkTool] " + sb.toString());
		}
		// print the middle line (with the left navigate line)
		sb = new StringBuilder("");
		sb.append("§bW ");
		for (int j = 0; j < rows; j++) {
			if (j == rows / 2) {
				if (registered[(rows / 2) * rows + j].isLoaded()) {
					sb.append("§6$ ");
				} else {
					sb.append("§f$ ");
				}
				continue;
			}
			if (registered[(rows / 2) * rows + j].isLoaded()) {
				sb.append("§a# ");
			} else {
				sb.append("§0# ");
			}
		}
		player.sendMessage("[ChunkTool] " + sb.toString());
		// print the bottom lines (with the left navigate line)
		for (int i = rows / 2 + 1; i < rows; i++) {
			sb = new StringBuilder("");
			if (i % 2 == 0) {
				sb.append("§9");
				sb.append(Integer.toHexString(i - rows / 2));
				sb.append(" ");
			} else {
				sb.append("§c");
				sb.append(Integer.toHexString(i - rows / 2));
				sb.append(" ");
			}
			for (int j = 0; j < rows; j++) {
				if (registered[i * rows + j].isLoaded()) {
					sb.append("§a# ");
				} else {
					sb.append("§0# ");
				}
			}
			player.sendMessage("[ChunkTool] " + sb.toString());
		}
		// show notifications
		Location firstChunkLocation = registered[0].getBlock(0, 0, 0).getLocation();
		Location lastChunkLocation = registered[registered.length - 1].getBlock(15, 0, 15).getLocation();
		player.sendMessage("[ChunkTool] Notes: '§a#§r'=加载的区块     '§6$§r'=加载的中心区块");
		player.sendMessage("[ChunkTool] Notes: '§0#§r'=卸载的区块     '§f$§r'=卸载的中心区块");
		player.sendMessage("[ChunkTool] Range: §6(" + firstChunkLocation.getX() + ", " + firstChunkLocation.getZ()
				+ ")§r -> §6(" + lastChunkLocation.getX() + ", " + lastChunkLocation.getZ() + ")");
		return true;
	}

	/**
	 * Functions as its name.
	 * 
	 * @param player
	 * @return true when finished
	 */
	public boolean unregisterChunks(Player player) {
		if (!map.containsKey(player.getName())) {
			player.sendMessage("[ChunkTool] §c你还没有注册过区块！");
			return true;
		}
		map.put(player.getName(), null);
		map.remove(player.getName());
		player.sendMessage("[ChunkTool] §a已成功注销你的区块注册信息。");
		return true;
	}

	/**
	 * OP : Unregisters registrations of all players.
	 * 
	 * @param player
	 * @return true when finished
	 */
	public boolean unregisterAll(Player player) {
		map.clear();
		player.sendMessage("[ChunkTool] §6已§a成功§6注销所有玩家的区块注册信息。");
		return true;
	}

	/**
	 * OP : Show the players' names whom had registered chunks.
	 * 
	 * @param player
	 * @return true when finished
	 */
	public boolean showRegs(Player player) {
		Set<String> names = map.keySet();
		if (names.isEmpty()) {
			player.sendMessage("[ChunkTool] §6当前没有人注册了区块！");
			return true;
		}
		StringBuilder sb = new StringBuilder("");
		regCount = 0;
		names.stream().forEach(s -> {
			if (regCount == map.size() - 1) {
				sb.append("§a").append(s);
			} else {
				sb.append("§a").append(s).append("§6").append(", ");
				regCount++;
			}
		});
		player.sendMessage("[ChunkTool] §6当前注册了区块的玩家有： " + sb.toString());
		return true;
	}
	
	/**
	 * OP : Show another player's registered chunks' trace.
	 * 
	 * @param player -> player to send message to
	 * @param name -> name to query
	 * @return
	 */
	public boolean showRegOfOthers(Player player, String name) {
		if (!map.containsKey(name)) {
			player.sendMessage("[ChunkTool] §a" + name + "§c没有注册区块！");
			return true;
		}
		player.sendMessage("[ChunkTool] §6=== §a" + name + "§6注册的区块的加载情况 ===");
		return showChunkTrace(player, name);
	}
}

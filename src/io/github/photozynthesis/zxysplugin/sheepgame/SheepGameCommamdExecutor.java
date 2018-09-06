package io.github.photozynthesis.zxysplugin.sheepgame;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.photozynthesis.zxysplugin.ZXYsPlugin;

public class SheepGameCommamdExecutor implements CommandExecutor {

	private ZXYsPlugin plugin;
	private SheepGameListener sheepGameListener;
	private SheepGameBroadcastListener broadcastListener;
	// signs whether the listener is registered
	private boolean flag = false;

	public SheepGameCommamdExecutor(ZXYsPlugin plugin) {
		super();
		this.plugin = plugin;
		sheepGameListener = new SheepGameListener(plugin);
		broadcastListener = new SheepGameBroadcastListener();
		plugin.getServer().getPluginManager().registerEvents(broadcastListener, plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender == null || !(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("sheepgame") || label.equalsIgnoreCase("sheep")) {
			if (player.hasPermission("zxy.sheepgame")) {
				if (args.length != 0) {
					if ("start".equalsIgnoreCase(args[0])) {
						if (flag == true) {
							player.sendMessage("[SheepGame] 已处于打开状态！");
							return true;
						}
						plugin.getServer().getPluginManager().registerEvents(sheepGameListener, plugin);
						plugin.getServer().broadcastMessage("[SheepGame] 已开启！");
						flag = true;
						return true;
					} else if ("stop".equalsIgnoreCase(args[0])) {
						if (flag == false) {
							player.sendMessage("[SheepGame] 已处于关闭状态！");
							return true;
						}
						sheepGameListener.handlerList.unregister(sheepGameListener);
						plugin.getServer().broadcastMessage("[SheepGame] 已关闭！");
						flag = false;
						return true;
					}
				}
			}
		}
		return false;
	}
}

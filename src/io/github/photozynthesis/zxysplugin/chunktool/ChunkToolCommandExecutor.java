package io.github.photozynthesis.zxysplugin.chunktool;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChunkToolCommandExecutor implements CommandExecutor {

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
		if ("register".equalsIgnoreCase(args[0])) {
			return registerChunk(player, label, args);
		} else if ("show".equalsIgnoreCase(args[0])) {
			return showChunkTrace(player);
		}
		return false;
	}

	public boolean registerChunk(Player player, String label, String[] args) {
		
		return false;
	}

	public boolean showChunkTrace(Player player) {
		
		return false;
	}
}

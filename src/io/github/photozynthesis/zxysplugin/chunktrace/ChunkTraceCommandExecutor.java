package io.github.photozynthesis.zxysplugin.chunktrace;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChunkTraceCommandExecutor implements CommandExecutor {

	// the function currently not completed
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender == null || !(sender instanceof Player)) {
			return false;
		}
//		Player player = (Player) sender;
		
		return false;
	}

}

package io.github.photozynthesis.zxysplugin.chunktool;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ChunkToolBroadcastListener implements Listener {

	public ChunkToolBroadcastListener() {
		super();
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent ev) {
		Player p = null;
		if (ev != null && (p = ev.getPlayer()) != null) {
			p.sendMessage("[ZXYsPlugin] §a新功能 §6[ChunkTool]§a 已安装！ 使用 §6/chunktool§a 来查看用法！");
		}
	}
	
}

package io.github.photozynthesis.zxysplugin.sheepgame;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SheepGameBroadcastListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		if (p != null) {
			p.sendMessage("[ZXYsPlugin] §a新功能 §6[SheepGame]§a 已安装！ 使用 §6/<sheepgame|sheep>§a 来查看§6玩法§a！");
		}
	}
	
}

package io.github.photozynthesis.zxysplugin.sheepgame;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SheepGameBroadcastListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		if (p != null) {
			p.sendMessage("[ZXYsPlugin] §a新功能 [SheepGame] 已安装！");
			p.sendMessage("[ZXYsPlugin] §a使用 /sheepgame 或 /sheep 来查看用法并开始游戏！");
		}
		return;
	}
}

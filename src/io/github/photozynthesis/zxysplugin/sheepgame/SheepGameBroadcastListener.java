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
			p.sendMessage("[SheepGame] 插件已启用！");
			p.sendMessage("[SheepGame] 使用 /sheepgame 或 /sheep 来查看用法并开始游戏！");
		}
		return;
	}
}

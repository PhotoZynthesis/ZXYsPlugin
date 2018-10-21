package io.github.photozynthesis.zxysplugin.temperature;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TemperatureBroadcastListener implements Listener {

	public TemperatureBroadcastListener() {
		super();
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent ev) {
		Player p = null;
		if (ev != null && (p = ev.getPlayer()) != null) {
			p.sendMessage("[ZXYsPlugin] §a新功能 §6[Temperature]§a 已安装！ 使用 §6/<temperature|tem> help§a 来查看用法！");
		}
	}

}

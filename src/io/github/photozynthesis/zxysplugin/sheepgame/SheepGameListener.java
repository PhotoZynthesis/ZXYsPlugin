package io.github.photozynthesis.zxysplugin.sheepgame;

import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.photozynthesis.zxysplugin.ZXYsPlugin;

public class SheepGameListener implements Listener {

	private ZXYsPlugin plugin;
	private Random r = new Random();
//	private BukkitRunnable[] bukkitrunnables = new BukkitRunnable[40];
	public HandlerList handlerList = PlayerInteractAtEntityEvent.getHandlerList();

	public SheepGameListener(ZXYsPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onClickingOnEntity(PlayerInteractAtEntityEvent ev) {
		final Player player = ev.getPlayer();
		Entity en = ev.getRightClicked();
		if (player == null || en == null) {		// 空值检查
			return;
		}
		if(ev.getHand() != EquipmentSlot.HAND) {
			return;
		}
		if (en.getType() != EntityType.SHEEP) {
			return;
		}
		final Sheep sheep = (Sheep) en;
		int explode = r.nextInt(5);
		if (explode == 1) {
			player.sendMessage("[sheep] 我猪肉佬...");
			sheep.setColor(DyeColor.WHITE);
			new BukkitRunnable() {
				@Override
				public void run() {
					player.sendMessage("[sheep] 何尝不想成为一个伟大的舞蹈家？");
					sheep.setColor(DyeColor.YELLOW);
				}
			}.runTaskLaterAsynchronously(plugin, 10);
			new BukkitRunnable() {
				@Override
				public void run() {
					player.sendMessage("[sheep] 在这个Moment...");
					sheep.setColor(DyeColor.RED);
				}
			}.runTaskLaterAsynchronously(plugin, 30);
			new BukkitRunnable() {
				@Override
				public void run() {
					player.sendMessage("[sheep] 要爆了！");
					sheep.setColor(DyeColor.BLACK);
				}
			}.runTaskLaterAsynchronously(plugin, 60);
			new BukkitRunnable() {
				@Override
				public void run() {
					sheep.getWorld().createExplosion(sheep.getLocation(), 8.0F);
				}
			}.runTaskLaterAsynchronously(plugin, 100);
			return;
		}
		player.sendMessage("[Sheep] 咩~");
		return;
	}
}

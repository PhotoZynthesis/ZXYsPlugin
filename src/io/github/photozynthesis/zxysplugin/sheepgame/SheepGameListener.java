package io.github.photozynthesis.zxysplugin.sheepgame;

import java.util.HashSet;
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
	private HashSet<Sheep> exploding;
	// §
	private String[] formats = {"§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§a", "§b", "§c", "§d", "§e"};
	private int formatPointer = 14;
	
	public HandlerList handlerList = PlayerInteractAtEntityEvent.getHandlerList();

	public SheepGameListener(ZXYsPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onClickingOnEntity(PlayerInteractAtEntityEvent ev) {
		final Player player = ev.getPlayer();
		Entity en = ev.getRightClicked();
		// Null Check
		if (player == null || en == null) {
			return;
		}
		// Only runs for main hand
		if(ev.getHand() != EquipmentSlot.HAND) {
			return;
		}
		// Only runs for sheeps
		if (en.getType() != EntityType.SHEEP) {
			return;
		}
		final Sheep sheep = (Sheep) en;
		// Only runs when the sheep is not exploding
		if(exploding.contains(sheep)) {
			return;
		}
		int explode = r.nextInt(5);
		if (explode == 1) {
			exploding.add(sheep);
			String format = nextFormat();
			player.sendMessage("[sheep] " + format + "我猪肉佬...");
			sheep.setColor(DyeColor.WHITE);
			new BukkitRunnable() {
				Sheep s = sheep;
				Player p = player;
				String f = format;
				@Override
				public void run() {
					p.sendMessage("[sheep] " + f + "何尝不想成为一个伟大的舞蹈家？");
					s.setColor(DyeColor.YELLOW);
				}
			}.runTaskLater(plugin, 10);
			new BukkitRunnable() {
				Sheep s = sheep;
				Player p = player;
				String f = format;
				@Override
				public void run() {
					p.sendMessage("[sheep] " + f + "在这个Moment...");
					s.setColor(DyeColor.RED);
				}
			}.runTaskLater(plugin, 30);
			new BukkitRunnable() {
				Sheep s = sheep;
				Player p = player;
				String f = format;
				@Override
				public void run() {
					p.sendMessage("[sheep] " + f + "要爆了！");
					s.setColor(DyeColor.BLACK);
				}
			}.runTaskLater(plugin, 60);
			new BukkitRunnable() {
				HashSet<Sheep> set = exploding;
				Sheep s = sheep;
				@Override
				public void run() {
					s.getWorld().createExplosion(s.getLocation(), 5.0F);
					set.remove(s);
				}
			}.runTaskLater(plugin, 100);
			return;
		}
		player.sendMessage("[Sheep] 咩~");
		return;
	}
	
	public String nextFormat() {
		if((formatPointer + 1) == formats.length) {
			formatPointer = 0;
		} else {
			formatPointer ++;
		}
		return formats[formatPointer];
	}
}

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.photozynthesis.zxysplugin.ZXYsPlugin;

public class SheepGameListener implements Listener {

	private ZXYsPlugin plugin;
	private Random r = new Random();
	private HashSet<Sheep> exploding = new HashSet<Sheep>();
	// §
	private String[] formats = {"§2", "§3", "§5", "§6", "§9", "§a", "§b", "§c", "§d", "§e"};
	private int formatPointer = formats.length - 1;

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
		if (ev.getHand() != EquipmentSlot.HAND) {
			return;
		}
		// Only runs for sheeps
		if (en.getType() != EntityType.SHEEP) {
			return;
		}
		final Sheep sheep = (Sheep) en;
		// Only runs when the sheep is not exploding
		if (exploding.contains(sheep)) {
			return;
		}
		int explode = r.nextInt(10);
		if (explode == 1) {
			exploding.add(sheep);
			String format = nextFormat();
			player.sendMessage(new StringBuilder("[sheep] ").append(format).append("我猪肉佬...").toString());
			sheep.setColor(DyeColor.YELLOW);
			new BukkitRunnable() {
				Sheep s = sheep;
				Player p = player;
				String f = format;
				HashSet<Sheep> set = exploding;
				@Override
				public void run() {
					if (s.getHealth() == 0 || s.isDead()) {
						set.remove(s);
						return;
					}
					s.setColor(DyeColor.RED);
					p.sendMessage(new StringBuilder("[sheep] ").append(f).append("何尝不想成为一个伟大的舞蹈家？").toString());
				}
			}.runTaskLater(plugin, 10);
			new BukkitRunnable() {
				Sheep s = sheep;
				Player p = player;
				String f = format;
				HashSet<Sheep> set = exploding;
				@Override
				public void run() {
					if (s.getHealth() == 0 || s.isDead()) {
						set.remove(s);
						return;
					}
					s.setColor(DyeColor.BLACK);
					p.sendMessage(new StringBuilder("[sheep] ").append(f).append("在这个Moment...").toString());
				}
			}.runTaskLater(plugin, 30);
			new BukkitRunnable() {
				Sheep s = sheep;
				Player p = player;
				String f = format;
				HashSet<Sheep> set = exploding;
				@Override
				public void run() {
					if (s.getHealth() == 0 || s.isDead()) {
						set.remove(s);
						return;
					}
					s.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 150, 1));
					p.sendMessage(new StringBuilder("[sheep] ").append(f).append("要爆了！").toString());
				}
			}.runTaskLater(plugin, 60);
			new BukkitRunnable() {
				Sheep s = sheep;
				HashSet<Sheep> set = exploding;
				@Override
				public void run() {
					if (s.getHealth() == 0 || s.isDead()) {
						set.remove(s);
						return;
					}
					s.getWorld().createExplosion(s.getLocation(), 3.0F);
					set.remove(s);
				}
			}.runTaskLater(plugin, 100);
			return;
		}
		player.sendMessage("[Sheep] 咩~");
		return;
	}

	public String nextFormat() {
		if ((formatPointer + 1) == formats.length) {
			formatPointer = 0;
		} else {
			formatPointer++;
		}
		return formats[formatPointer];
	}
}

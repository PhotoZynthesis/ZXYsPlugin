package io.github.photozynthesis.zxysplugin.temperature;

import java.math.BigDecimal;

import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.photozynthesis.zxysplugin.ZXYsPlugin;

public class TemperatureCommandExecutor implements CommandExecutor{
	
//	private ZXYsPlugin plugin;
	private TemperatureBroadcastListener broadcastListener;
	
	public TemperatureCommandExecutor(ZXYsPlugin plugin) {
		broadcastListener = new TemperatureBroadcastListener();
		plugin.getServer().getPluginManager().registerEvents(broadcastListener, plugin);
//		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// check whether the command matches
		if (!"temperature".equalsIgnoreCase(label) && !"tem".equalsIgnoreCase(label)) {
			return false;
		}
		
		// check args length
		if (args.length >= 1) {
			return false;
		}
		
		// check player
		if (sender == null || !(sender instanceof Player)) {
			return false;
		}
		
		// do main task
		Player player = (Player)sender;
		Block localBlock = player.getLocation().getBlock();
		double localBiomeTemperature = localBlock.getTemperature();
		BigDecimal localTemperature = new BigDecimal(localBiomeTemperature);
		
		String locationStr = null;
		Biome localBiome = null;
		String localBiomeTemperatureStr = null;
		String localTemperatureStr = null;
		String localBiomeWeather = null;
		String localWeather = null;
		String canWaterFreeze = null;
		String canLocalWaterFreeze = null;
		String causeSnowmanDeath = null;
		
		locationStr = "§a(" + localBlock.getX() + ", " + localBlock.getY() + ", " + localBlock.getZ() + ")";
		
		localBiome = localBlock.getBiome();
		
		if (localBiomeTemperature >= 0.15 && localBiomeTemperature <= 0.95) {
			localBiomeTemperatureStr = "§a" + new BigDecimal(localBiomeTemperature).setScale(6, BigDecimal.ROUND_HALF_UP).toString();
			localBiomeWeather = "§arain§r, §bsnow";
			canWaterFreeze = "§cfalse";
		} else if (localBiomeTemperature < 0.15) {
			localBiomeTemperatureStr = "§b" + new BigDecimal(localBiomeTemperature).setScale(6, BigDecimal.ROUND_HALF_UP).toString();
			localBiomeWeather = "§bsnow";
			canWaterFreeze = "§btrue";
		} else {
			localBiomeTemperatureStr = "§6" + new BigDecimal(localBiomeTemperature).setScale(6, BigDecimal.ROUND_HALF_UP).toString();
			localBiomeWeather = "§6none";
			canWaterFreeze = "§cfalse";
		}
		
		if (localBlock.getY() > 64) {
			BigDecimal heightDiff = new BigDecimal(String.valueOf(localBlock.getY() - 64));
			BigDecimal temDiff = heightDiff.divide(new BigDecimal("600"), 10, BigDecimal.ROUND_HALF_UP);
			localTemperature = new BigDecimal(localBiomeTemperature).subtract(temDiff);
		}
		if (localTemperature.compareTo(new BigDecimal("0.15")) == -1) {
			localTemperatureStr = "§b" + localTemperature.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
			localWeather = "§bsnow";
			canLocalWaterFreeze = "§btrue";
		} else if (localTemperature.compareTo(new BigDecimal("0.95")) == -1 || localTemperature.compareTo(new BigDecimal("0.95")) == 0) {
			localTemperatureStr = "§a" + localTemperature.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
			localWeather = "§arain";
			canLocalWaterFreeze = "§cfalse";
		} else {
			localTemperatureStr = "§6" + localTemperature.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
			localWeather = "§6none";
			canLocalWaterFreeze = "§cfalse";
		}
		
		if (localBiomeTemperature > 1.0) {
			causeSnowmanDeath = "§ctrue";
		} else {
			causeSnowmanDeath = "§afalse";
		}
		
//		player.sendMessage(" ");
		player.sendMessage("\n[Temperature] 当前方块坐标(x,y,z)： " + locationStr);
		player.sendMessage("[Temperature] 当前生物群系： §a" + localBiome);
		player.sendMessage("[Temperature] 当前基础温度： " + localBiomeTemperatureStr);
		player.sendMessage("[Temperature] 当前实时温度： " + localTemperatureStr);
		player.sendMessage("[Temperature] 当前群系可发生的降水类型： " + localBiomeWeather);
		player.sendMessage("[Temperature] 当前高度可发生的降水类型： " + localWeather);
		player.sendMessage("[Temperature] 当前群系是否自动结冰： " + canWaterFreeze);
		player.sendMessage("[Temperature] 当前高度是否自动结冰： " + canLocalWaterFreeze);
		player.sendMessage("[Temperature] 当前群系雪傀儡是否致死： " + causeSnowmanDeath);
		
		return true;
	}
	
}

package io.github.photozynthesis.zxysplugin;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.photozynthesis.zxysplugin.chunktool.ChunkToolCommandExecutor;
import io.github.photozynthesis.zxysplugin.sheepgame.SheepGameCommamdExecutor;
import io.github.photozynthesis.zxysplugin.temperature.TemperatureCommandExecutor;

public class ZXYsPlugin extends JavaPlugin{
	
	private SheepGameCommamdExecutor sheepGameCommandExecutor;
	private ChunkToolCommandExecutor chunkToolCommandExecutor;
	private TemperatureCommandExecutor temperatureCommandExecutor;
	
	@Override
	public void onEnable() {
		super.onEnable();
		// enabling SheepGame
		sheepGameCommandExecutor = new SheepGameCommamdExecutor(this);
		this.getCommand("sheepgame").setExecutor(sheepGameCommandExecutor);
		
		// enabling ChunkTool
		chunkToolCommandExecutor = new ChunkToolCommandExecutor(this);
		this.getCommand("chunktool").setExecutor(chunkToolCommandExecutor);
		
		// enabling Temperature
		temperatureCommandExecutor = new TemperatureCommandExecutor(this);
		this.getCommand("temperature").setExecutor(temperatureCommandExecutor);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
}

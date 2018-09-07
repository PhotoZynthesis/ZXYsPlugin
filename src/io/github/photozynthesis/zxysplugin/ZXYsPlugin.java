package io.github.photozynthesis.zxysplugin;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.photozynthesis.zxysplugin.chunktool.ChunkToolCommandExecutor;
import io.github.photozynthesis.zxysplugin.sheepgame.SheepGameCommamdExecutor;

public class ZXYsPlugin extends JavaPlugin{
	
	private SheepGameCommamdExecutor sheepGameCommandExecutor;
	private ChunkToolCommandExecutor chunkToolCommandExecutor;
	
	@Override
	public void onEnable() {
		super.onEnable();
		// enabling SheepGame
		sheepGameCommandExecutor = new SheepGameCommamdExecutor(this);
		this.getCommand("sheepgame").setExecutor(sheepGameCommandExecutor);
		
		// enabling ChunkTool
		chunkToolCommandExecutor = new ChunkToolCommandExecutor();
		this.getCommand("chunktool").setExecutor(chunkToolCommandExecutor);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
}

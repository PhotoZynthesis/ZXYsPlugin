package io.github.photozynthesis.zxysplugin;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.photozynthesis.zxysplugin.sheepgame.SheepGameCommamdExecutor;

public class ZXYsPlugin extends JavaPlugin{
	
	private SheepGameCommamdExecutor sheepGameCommandExecutor;
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		// enabling SheepGame
		sheepGameCommandExecutor = new SheepGameCommamdExecutor(this);
		this.getCommand("sheepgame").setExecutor(sheepGameCommandExecutor);
		
		// enabling ChunkTrace
		
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
}

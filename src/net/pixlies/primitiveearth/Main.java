package net.pixlies.primitiveearth;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import net.pixlies.primitiveearth.commands.NationsCommand;
import net.pixlies.primitiveearth.entities.Nation;
import net.pixlies.primitiveearth.entities.NationChunk;

public class Main extends JavaPlugin {

	public static Main instance;
	
	public Map<String, Nation> nations;
	public Map<String, NationChunk> chunks;
	
	@Override
	public void onEnable() {
		final long start = System.currentTimeMillis();
		log("Initializing...");
		instance = this;
		nations = new HashMap<>();
		chunks = new HashMap<>();
		
		for (Nation nation : Nation.loadAll())
			nations.put(nation.getId(), nation);
		
		initCommands();
		
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
			for (Nation nation : nations.values())
				nation.backup();
		}, (20 * 60) * 15, (20 * 60) * 15);
		
		final long needed = System.currentTimeMillis() - start;
		log("Initializing finished in " + needed + "ms");
	}
	
	public void initCommands() {
		try {
			
			Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
	        bukkitCommandMap.setAccessible(true);
	        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
	        
	        commandMap.register("nations", new NationsCommand());
        
		} catch (Exception e) {
			log("Error while initializing commands.");
		}
	}

	@Override
	public void onDisable() {
		
	}
	
	public void log(String message) {
		System.out.println("[EARTH] " + message);
	}
	
}

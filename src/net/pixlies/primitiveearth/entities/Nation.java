package net.pixlies.primitiveearth.entities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.pixlies.primitiveearth.Main;
import net.pixlies.primitiveearth.utils.Utils;

public class Nation {
	
	private String id;
	private String name;
	private List<String> chunks;
	private Map<String, String> members;
	
	public Nation(String id, String name, List<String> chunks, Map<String, String> members) {
		this.id = id;
		this.name = name;
		this.chunks = chunks;
		this.members = members;
	}
	
	public static Nation create(String name, Player creator) {
		Map<String, String> members = new HashMap<>();
		members.put(creator.getUniqueId().toString(), "leader");
		return new Nation(Utils.getAlphaNumericString(7), name, new ArrayList<>(), members).save();
	}
	
	public Nation backup() {
		try {
			File file = new File("plugins/PrimitiveEarth/nations/", id + ".yml");
			
			if (file.exists())
				file.delete();
			
			file.createNewFile();
			
			FileConfiguration natCfg = YamlConfiguration.loadConfiguration(file);
			natCfg.set("id", id);
			natCfg.set("name", name);
			natCfg.set("chunks", chunks);
			for (Entry<String, String> entry : members.entrySet())
				natCfg.set("members." + entry.getKey(), entry.getValue());
			natCfg.save(file);
			
		} catch (IOException e) {
			Main.instance.log("Could not save nation " + ChatColor.AQUA + name + ChatColor.WHITE + ".");
		}
		return this;
	}
	
	public Nation save() {
		Main.instance.nations.put(id, this);
		return this;
	}
	
	public static Set<Nation> loadAll() {
		Set<Nation> set = new HashSet<>();
		for (File file : new File("plugins/PrimitiveEarth/nations/").listFiles()) {
			FileConfiguration natCfg = YamlConfiguration.loadConfiguration(file);
			String id = natCfg.getString("id");
			String name = natCfg.getString("name");
			List<String> chunks = natCfg.getStringList("chunks");
			Map<String, String> members = new HashMap<>();
			for (String s : natCfg.getConfigurationSection("members").getKeys(false))
				members.put(s, natCfg.getString("members." + s));
			Nation nation = new Nation(id, name, chunks, members);
			nation.loadChunks();
			set.add(nation);
		}
		return set;
	}

	public void loadChunks() {
		for (String s : chunks) {
			NationChunk nc = NationChunk.deserialize(s);
			Main.instance.chunks.put(nc.anonSerialize(), nc);
		}
	}
	
	// GETTERS AND SETTERS
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getChunks() {
		return chunks;
	}
	public void setChunks(List<String> chunks) {
		this.chunks = chunks;
	}
	public Map<String, String> getMembers() {
		return members;
	}
	public void setMembers(Map<String, String> members) {
		this.members = members;
	}

}

package net.pixlies.primitiveearth.entities;

import org.bukkit.Bukkit;
import org.bukkit.World;

import net.pixlies.primitiveearth.Main;

public class NationChunk {
	
	private String nationId;
	private World world;
	private int x;
	private int z;
	
	public NationChunk(String nationId, World world, int x, int z) {
		this.nationId = nationId;
		this.world = world;
		this.x = x;
		this.z = z;
	}

	public String serialize() {
		return nationId + ";" + world.getName() + ";" + x + ";" + z;
	}

	public static NationChunk deserialize(String s) {
		String[] split = s.split(";");
		return new NationChunk(split[0], Bukkit.getWorld(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
	}
	
	public String anonSerialize() {
		return world.getName() + ";" + x + ";" + z;
	}
	
	
	public void claim() {
		Main.instance.chunks.put(anonSerialize(), this);
		Nation nat = Main.instance.nations.get(nationId);
		nat.getChunks().add(serialize());
		nat.save();
		Main.instance.log("Chunk claimed at " + anonSerialize().replace(";" , ", ") + " for " + nat.getName());
	}
	
	public String getNationId() {
		return nationId;
	}
	public void setNationId(String nationId) {
		this.nationId = nationId;
	}
	public World getWorld() { return world; }
	public void setWorld(World world) { this.world = world; }
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	
}

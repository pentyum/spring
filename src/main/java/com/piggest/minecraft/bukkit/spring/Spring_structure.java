package com.piggest.minecraft.bukkit.spring;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Spring_structure implements Structure {
	private String world_name;
	private int x = 0;
	private int y = 0;
	private int z = 0;

	public Spring_structure(String world_name, int x, int y, int z) {
		this.world_name = world_name;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int get_x() {
		return x;
	}

	public int get_y() {
		return x;
	}

	public int get_z() {
		return x;
	}

	public World get_world() {
		return Bukkit.getWorld(world_name);
	}

	public Location get_location() {
		return new Location(get_world(), x, y, z);
	}

	public Block get_core_block() {
		return this.get_world().getBlockAt(x, y, z);
	}

	public boolean completed() {
		Location water = this.get_location();
		Location fire = this.get_location().add(0, -2, 0);
		if (water.getBlock().getType() == Material.WATER
				&& (fire.getBlock().getType() == Material.FIRE || fire.getBlock().getType() == Material.LAVA)) {
			return true;
		}
		return false;
	}

}

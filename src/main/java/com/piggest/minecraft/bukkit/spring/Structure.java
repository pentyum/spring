package com.piggest.minecraft.bukkit.spring;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public interface Structure {
	public int get_x();

	public int get_y();

	public int get_z();

	public World get_world();

	public Location get_location();
	
	public Block get_core_block();
	
	public boolean completed();
}

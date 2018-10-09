package com.piggest.minecraft.bukkit.spring;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class Spring_build_listener implements Listener {
	private Spring spring = null;

	public Spring_build_listener(Spring spring) {
		this.spring = spring;
	}
	
	@EventHandler
	public void on_player_place(BlockPlaceEvent event) {
		if(event.getBlock().getType()==Material.WATER) {
			
		}
	}

}

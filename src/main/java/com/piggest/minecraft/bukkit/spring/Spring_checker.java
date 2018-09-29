package com.piggest.minecraft.bukkit.spring;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Spring_checker implements Condition_checker {

	public boolean check(Player player) {
		Location loc = player.getLocation();
		if (loc.getBlock().getType() == Material.WATER) {
			loc.setY(loc.getY() - 2);
			if (loc.getBlock().getType() == Material.LAVA || loc.getBlock().getType() == Material.FIRE) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}

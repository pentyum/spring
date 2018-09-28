package com.piggest.minecraft.bukkit.spring;

import java.util.Collection;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Spring_scanner extends BukkitRunnable {
	private Spring spring = null;
	private Collection<? extends Player> player_list = null;
	private HashMap<Player, Spring_buffer> buffer_list = new HashMap<Player, Spring_buffer>();

	public Spring_scanner(Spring plugin) {
		this.spring = plugin;
	}

	public Spring_buffer get_buffer(Player player) {
		return this.buffer_list.get(player);
	}

	public void enable_buffer(Player player) {
		Spring_buffer buffer = buffer_list.get(player);
		if (buffer == null) {
			buffer = new Spring_buffer(player);
			buffer_list.put(player, buffer);
			buffer.runTaskTimer(spring, 0, 5 * 20);
			player.sendMessage("持续性buff已添加");
		} else {
			if (buffer.isCancelled() == true) {
				buffer.runTaskTimer(spring, 0, 5 * 20);
			}
		}
	}

	public void disable_buffer(Player player) {
		if (buffer_list.get(player) != null) {
			buffer_list.get(player).cancel();
			buffer_list.remove(player);
			player.sendMessage("buff已移除");
		}
	}

	public void run() {
		player_list = spring.getServer().getOnlinePlayers();
		for (Player player : player_list) {
			Location loc = player.getLocation();
			if (loc.getBlock().getType() == Material.WATER) {
				loc.setY(loc.getY() - 2);
				if (loc.getBlock().getType() == Material.LAVA || loc.getBlock().getType() == Material.FIRE) {
					enable_buffer(player);
				} else {
					disable_buffer(player);
				}
			} else {
				disable_buffer(player);
			}
		}
	}
}

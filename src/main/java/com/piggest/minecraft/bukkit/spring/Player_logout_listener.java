package com.piggest.minecraft.bukkit.spring;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Player_logout_listener implements Listener {
	private Spring spring = null;

	public Player_logout_listener(Spring spring) {
		this.spring = spring;
	}
	
	@EventHandler
	public void on_player_logout(PlayerQuitEvent event) {
		spring.get_spring_scanner().disable_buffer(event.getPlayer());
	}
}

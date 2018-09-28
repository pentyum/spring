package com.piggest.minecraft.bukkit.spring;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Spring_buffer extends BukkitRunnable {
	private Player player = null;

	public Spring_buffer(Player player) {
		this.player = player;
	}

	public void run() {
		PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 6 * 20, 3);
		player.addPotionEffect(effect, true);
		player.sendMessage("buff已添加");
	}
}

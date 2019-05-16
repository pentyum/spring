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
		if (Spring.use_vault == true) {
			String world_name = player.getWorld().getName();
			if(!Spring.price.getKeys(false).contains(world_name)) {
				world_name = "other";
			}
			int price = Spring.price.getInt(world_name);
			if (Spring.economy.has(player, price)) {
				Spring.economy.withdrawPlayer(player, price);
			} else {
				player.sendMessage("你的钱不够泡温泉");
			}
		} else {
			buff(player);
		}
	}
	
	private void buff(Player player) {
		PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 6 * 20, 3);
		player.addPotionEffect(effect, true);
	}
}

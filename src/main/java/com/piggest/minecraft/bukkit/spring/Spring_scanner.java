package com.piggest.minecraft.bukkit.spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Spring_scanner extends BukkitRunnable {
	private Spring spring = null;
	private Collection<? extends Player> player_list = null;
	private HashMap<Player, Spring_buffer> buffer_list = new HashMap<Player, Spring_buffer>();
	private HashSet<Condition_checker> checker_list = new HashSet<Condition_checker>();

	public Spring_scanner(Spring plugin) {
		this.spring = plugin;
		this.register_checker("com.piggest.minecraft.bukkit.spring.Spring_checker");
		// Spring_checker spring_checker = new Spring_checker();
		// this.checker_list.add(spring_checker);
	}

	public void register_checker(String checker_name) {
		Class<?> checker_clazz;
		try {
			checker_clazz = Class.forName(checker_name);
		} catch (ClassNotFoundException e1) {
			spring.getLogger().warning("注册失败！未找到名为" + checker_name + "的buff条件检测器");
			return;
		}
		Class<Condition_checker> condition_checker_clazz = Condition_checker.class;
		if (condition_checker_clazz.isAssignableFrom(checker_clazz)) {
			Condition_checker checker;
			try {
				checker = (Condition_checker) checker_clazz.newInstance();
			} catch (IllegalAccessException e) {
				spring.getLogger().warning(checker_name + "访问失败");
				return;
			} catch (InstantiationException e) {
				spring.getLogger().warning(checker_name + "实例创建失败");
				return;
			}
			this.checker_list.add(checker);
			spring.getLogger().info("已加载 " + checker_name + " buff条件检测器");
		} else {
			spring.getLogger().warning("注册失败！" + checker_name + "没有实现Condition_checker接口");
		}
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

	public boolean check_player_condition(Player player) {
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

	public void run() {
		player_list = spring.getServer().getOnlinePlayers();
		for (Player player : player_list) {
			for (Condition_checker checker : this.checker_list) {
				if (checker.check(player) == true) {
					enable_buffer(player);
				} else {
					disable_buffer(player);
				}
			}
		}
	}
}

package com.piggest.minecraft.bukkit.spring;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

public class Spring extends JavaPlugin {
	static boolean use_vault = true;
	static Economy economy = null;
	static ConfigurationSection price = null;
	private FileConfiguration config = null;
	private final Spring_scanner spring_scanner = new Spring_scanner(this);
	private Player_logout_listener logout_listener = new Player_logout_listener(this);

	public Spring_scanner get_spring_scanner() {
		return this.spring_scanner;
	}

	private boolean initVault() {
		boolean hasNull = false;
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			if ((economy = economyProvider.getProvider()) == null) {
				hasNull = true;
			}
		}
		return !hasNull;
	}

	@Override
	public void onEnable() {
		saveDefaultConfig();
		config = getConfig();
		use_vault = config.getBoolean("use-vault");
		price = config.getConfigurationSection("price");
		if (use_vault == true) {
			getLogger().info("使用Vault");
			if (!initVault()) {
				getLogger().severe("初始化Vault失败,请检测是否已经安装Vault插件和经济插件");
				return;
			}
		} else {
			getLogger().info("不使用Vault");
		}
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(logout_listener, this);
		spring_scanner.register_checker("com.piggest.minecraft.bukkit.spring.Spring_checker");
		spring_scanner.runTaskTimerAsynchronously(this, 0, 10);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spring")) {
			sender.sendMessage("在水的下面两格点火或者放岩浆后可以构成温泉，进入温泉后可以回血。");
			return true;
		}
		return true;
	}

}

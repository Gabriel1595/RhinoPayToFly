package me.joao.whitshs.rhinomc.paytofly;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.joao.whitshs.rhinomc.paytofly.command.PayToFlyCommand;
import me.joao.whitshs.rhinomc.paytofly.task.PayToFlyTask;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin{
	
	private static Main plugin;
	private static Economy economy;
	
	public void onEnable() {
		
		setPlugin(this);
		saveDefaultConfig();
		
		setupEconomy();
		
		new PayToFlyTask().runTaskTimerAsynchronously(this, 20L, 20L);
		
		getCommand("paytofly").setExecutor(new PayToFlyCommand());

	}
	
	public void onDisable() {

	}

	public static Main getPlugin() {
		return plugin;
	}

	public static void setPlugin(Main plugin) {
		Main.plugin = plugin;
	}
	
	public static void setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
	}
	
	public static Economy getEconomy() {
		return economy;
	}

	public static void setEconomy(Economy economy) {
		Main.economy = economy;
	}

}

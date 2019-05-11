package me.joao.whitshs.rhinomc.paytofly.command;

import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.joao.whitshs.rhinomc.paytofly.Main;
import me.joao.whitshs.rhinomc.paytofly.manager.FlyManager;
import me.joao.whitshs.rhinomc.paytofly.utils.Formatter;
import me.joao.whitshs.rhinomc.paytofly.utils.Helper;

public class PayToFlyCommand implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		if (!sender.hasPermission("paytofly.use")) {
			sender.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.SemPermissao").replace("&", "§"));
			return true;
		}
		
		if (args.length != 1) {
			sender.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.PayToFlySintaxe").replace("&", "§"));
			return true;
		}
		Player player = (Player)sender;
		
		if (!Main.getPlugin().worlds.contains(player.getWorld())) {
			sender.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.MundoErrado").replace("&", "§"));
			return true;
		}
		
		if (FlyManager.getHashMap().containsKey((Player)sender)) {
			sender.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.JaEstaVoando").replace("&", "§"));
			return true;
		}
		
		try {
			Integer.parseInt(args[0]);
		} catch (Exception e) {
			sender.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.PayToFlySintaxe").replace("&", "§"));
			return true;
		}
		
		if (args[0].contains("-")) {
			sender.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.PayToFlySintaxe").replace("&", "§"));
			return true;
		}
		
		if (Integer.valueOf(args[0]) > Main.getPlugin().getConfig().getInt("PayToFly.TempoMaximo") || Integer.valueOf(args[0]) == 0) {
			sender.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.TempoMaximo").replace("&", "§"));
			return true;
		}
		
		int money = Main.getPlugin().getConfig().getInt("PayToFly.CustoPorSegundo") * Integer.valueOf(args[0]);
		
		if (!Main.getEconomy().has(player, money)) {
			sender.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.SemMoney").replace("&", "§"));
			return true;
		}

		for (String st : Main.getPlugin().getConfig().getStringList("Mensagens.PayToFly")) {
			player.sendMessage(st.replace("&", "§").replace("{tempo}", args[0]).replace("{custo}", Formatter.format(money)));
		}
		player.setAllowFlight(true);
		Main.getEconomy().withdrawPlayer(player, money);
		Helper.sendActionBar(player, ac.replace("&", "§").replace("{tempo}", args[0] + " segundos"));
		FlyManager.getHashMap().put(player, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(Integer.valueOf(args[0])));
		
		return false;
	}

	String ac = Main.getPlugin().getConfig().getString("Mensagens.ActionBar").replace("&", "§");
	
}

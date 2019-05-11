package me.joao.whitshs.rhinomc.paytofly.task;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.joao.whitshs.rhinomc.paytofly.Main;
import me.joao.whitshs.rhinomc.paytofly.manager.FlyManager;
import me.joao.whitshs.rhinomc.paytofly.utils.Helper;

public class PayToFlyTask extends BukkitRunnable{

	List<String> tempo = Main.getPlugin().getConfig().getStringList("Mensagens.TempoAcabou");
	String ac = Main.getPlugin().getConfig().getString("Mensagens.ActionBar").replace("&", "§");
	
	@Override
	public void run() {

		if (FlyManager.getHashMap().isEmpty()) {
			return;
		}
		
		long tempoAtual = System.currentTimeMillis();
		
		for (Entry<Player, Long> entry : FlyManager.getHashMap().entrySet()) {
			
			Player player = entry.getKey();
			long time = entry.getValue() - tempoAtual;
			
			if (entry.getValue() <= tempoAtual || !player.isOnline()) {
				FlyManager.getHashMap().remove(player);
				for (String st : Main.getPlugin().getConfig().getStringList("Mensagens.TempoAcabou")) {
					player.sendMessage(st.replace("&", "§"));
				}
				try {
					player.setAllowFlight(false);
				} catch (Exception e) {

				}
			}else {
				Helper.sendActionBar(player, ac.replace("&", "§").replace("{tempo}", Helper.format(time)));
			}
		}
		
	}
	
}

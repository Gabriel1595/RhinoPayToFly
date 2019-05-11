package me.joao.whitshs.rhinomc.paytofly.listeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import me.joao.whitshs.rhinomc.paytofly.Main;
import me.joao.whitshs.rhinomc.paytofly.manager.FlyManager;

public class PlayerChangeWorld implements Listener{
	
	@EventHandler
	public void onChange(PlayerChangedWorldEvent e) {
		
		World world = e.getPlayer().getWorld();
		Player player = e.getPlayer();
		
		if (FlyManager.getHashMap().containsKey(player) && !Main.getPlugin().worlds.contains(world)) {
			
			FlyManager.getHashMap().remove(player);
			for (String st : Main.getPlugin().getConfig().getStringList("Mensagens.MudouParaMundoNaoPermitido")) {
				player.sendMessage(st.replace("&", "§"));
			}
			player.setAllowFlight(false);
			
		}
		
	}

}

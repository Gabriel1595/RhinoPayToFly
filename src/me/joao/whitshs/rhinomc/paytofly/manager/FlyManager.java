package me.joao.whitshs.rhinomc.paytofly.manager;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class FlyManager {
	
	private static HashMap<Player, Long> fly = new HashMap<>();
	
	public static HashMap<Player, Long> getHashMap() {
		return fly;
	}

}

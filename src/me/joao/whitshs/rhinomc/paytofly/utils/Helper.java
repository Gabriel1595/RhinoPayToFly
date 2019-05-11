package me.joao.whitshs.rhinomc.paytofly.utils;

import java.util.concurrent.TimeUnit;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class Helper {
	
	public static Boolean percentChance(double chance) {
		return Math.random() <= chance / 100;
	}

	public static ItemStack addGlow(ItemStack item) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbt = nmsItem.getTag() == null ? new NBTTagCompound() : nmsItem.getTag();
		NBTTagList ench = new NBTTagList();
		nbt.set("ench", ench);
		nmsItem.setTag(nbt);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
	public static void sendTitle(Player p, String title, String sub, int in, int time, int out) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
		PacketPlayOutTitle titled = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle length = new PacketPlayOutTitle(EnumTitleAction.TIMES, chatTitle, 20, 40, 20);
		
		IChatBaseComponent subt = ChatSerializer.a("{\"text\": \"" + sub + "\"}");
		PacketPlayOutTitle subp = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subt);
		PacketPlayOutTitle subl = new PacketPlayOutTitle(EnumTitleAction.TIMES, subt, in, time, out);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(titled);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(subp);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(subl);
		
	}
	
	public static String format(long tempo) {
		if (tempo == 0)
			return "0 segundos";

		long dias = TimeUnit.MILLISECONDS.toDays(tempo);
		long horas = TimeUnit.MILLISECONDS.toHours(tempo) - (dias * 24);
		long minutos = TimeUnit.MILLISECONDS.toMinutes(tempo) - (TimeUnit.MILLISECONDS.toHours(tempo) * 60);
		long segundos = TimeUnit.MILLISECONDS.toSeconds(tempo) - (TimeUnit.MILLISECONDS.toMinutes(tempo) * 60);

		StringBuilder sb = new StringBuilder();

		if (dias > 0)
			sb.append(dias + (dias == 1 ? " dia" : " dias"));

		if (horas > 0)
			sb.append(dias > 0 ? (minutos > 0 ? ", " : " e ") : "").append(horas + (horas == 1 ? " hora" : " horas"));

		if (minutos > 0)
			sb.append(dias > 0 || horas > 0 ? (segundos > 0 ? ", " : " e ") : "")
					.append(minutos + (minutos == 1 ? " minuto" : " minutos"));

		if (segundos > 0)
			sb.append(dias > 0 || horas > 0 || minutos > 0 ? " e " : (sb.length() > 0 ? ", " : ""))
					.append(segundos + (segundos == 1 ? " segundo" : " segundos"));

		String s = sb.toString();
		return s.isEmpty() ? "0 segundo" : s;
	}
	
	public static void sendActionBar(Player p, String msg) {
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}

}

package main.kaden.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

import net.md_5.bungee.api.ChatColor;

public class Portals implements Listener {
	
	@EventHandler
	public void onPortalCreateEvent(PortalCreateEvent event) {
		Bukkit.getServer().broadcastMessage(ChatColor.RED + "Creating Portals are not allowed!");
		
		event.setCancelled(true);
	}
	
}

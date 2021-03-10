package main.kaden.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import main.kaden.DeathSwap;

public class TeleporterMenu implements Listener {
	private Plugin server = DeathSwap.getPlugin(DeathSwap.class);
	
	public void teleporterMenu(Player p) {
		Inventory i = server.getServer().createInventory(null, 9, "Custom Inventory");
		
	}
}

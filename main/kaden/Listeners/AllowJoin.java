package main.kaden.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import main.kaden.Commands.Activate;
import net.md_5.bungee.api.ChatColor;

public class AllowJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		
		if (Activate.dswapallow) {
			p.kickPlayer(ChatColor.RED + "A Death Swap Game is currently in play. Please wait until it is finished.");
		}
	}
}

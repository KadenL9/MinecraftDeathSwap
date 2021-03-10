package main.kaden.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import main.kaden.Commands.Activate;

public class Spectator implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSpawn(PlayerRespawnEvent event) {
		if (Activate.dswapallow) {
			Player p = (Player) event.getPlayer();
			p.setGameMode(GameMode.ADVENTURE);
			p.setAllowFlight(true);
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				p.hidePlayer(player);
			}
			
			Activate.dead.add(p);
		}
	}
	
}

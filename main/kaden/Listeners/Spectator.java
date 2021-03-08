package main.kaden.Listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import main.kaden.Commands.Activate;

public class Spectator implements Listener {
	
	@EventHandler
	public void onSpawn(PlayerRespawnEvent event) {
		if (Activate.dswapallow) {
			Player p = (Player) event.getPlayer();
			p.setGameMode(GameMode.SPECTATOR);
			
			Activate.dead.add(p);
		}
	}

}

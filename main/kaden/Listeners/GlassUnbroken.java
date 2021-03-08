package main.kaden.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import main.kaden.Commands.Activate;
import net.md_5.bungee.api.ChatColor;

public class GlassUnbroken implements Listener{
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (! Activate.glassbreak) {
			Player p = event.getPlayer();
			
			if (event.getBlock().getType() == Material.GLASS) {
				p.sendMessage(ChatColor.RED + "You can't break the glass!");
				event.setCancelled(true);
			}
		}
	}
	
}

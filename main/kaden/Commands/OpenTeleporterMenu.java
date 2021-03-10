package main.kaden.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class OpenTeleporterMenu implements Listener, CommandExecutor {
	public String teleportercmd = "teleporter";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase(teleportercmd)) {
			
		}
		
		return false;
	}
}

package main.kaden;


import org.bukkit.plugin.java.JavaPlugin;


import main.kaden.Commands.Activate;
import main.kaden.Listeners.GlassUnbroken;
import main.kaden.Listeners.Spectator;
import net.md_5.bungee.api.ChatColor;

public class DeathSwap extends JavaPlugin {
	private Activate ds = new Activate();
	
	public void onEnable() {
		getCommand(ds.deathswapcmd).setExecutor(ds);
		
		getServer().getPluginManager().registerEvents(new GlassUnbroken(), this);
		getServer().getPluginManager().registerEvents(new Spectator(), this);
		
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nDeathSwap has been enabled\n\n");
		loadConfig();
	}
	
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nDeathSwap has been disabled\n\n");
	}
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
	}

}

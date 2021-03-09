package main.kaden.Commands;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import main.kaden.DeathSwap;
import net.md_5.bungee.api.ChatColor;

public class Activate implements Listener, CommandExecutor {
	public String deathswapcmd = "deathswap";
	public static boolean glassbreak = true;
	public static boolean dswapallow = false;
	private ArrayList<Player> deop = new ArrayList<Player>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			if (cmd.getName().equalsIgnoreCase(deathswapcmd)) {
				
				if (args.length != 0) {
					
					if (args[0].equalsIgnoreCase("on")) {
						
						// make sure deathswap not already on
						if (dswapallow == true) {
							sender.sendMessage(ChatColor.RED + "DeathSwap is already on!");
							return true;
						}
						
						// make sure that there are at least 2 people on the server
						int numPlayers = Bukkit.getServer().getOnlinePlayers().size();
						if (numPlayers < 2) {
							sender.sendMessage(ChatColor.RED + "Not enough players! Must have at least 2 players on the server!");
							return true;
						}						
						
						glassbreak = false;
						dswapallow = true;
						
						for (Player p : Bukkit.getOnlinePlayers()) {
							spawnGlass(p);
							
							Location ploc = p.getLocation();
							int x = ploc.getBlockX();
							int y = ploc.getBlockY();
							int z = ploc.getBlockZ();
							World w = ploc.getWorld();
							p.teleport(new Location(w, x + 0.5, y + 3, z + 0.5));
							
							// clear player inventory
							p.getInventory().clear();
							
							// give players the rulebook
							addRuleBook(p);
							
							// set gamemode to survival
							p.setGameMode(GameMode.SURVIVAL);
							
							// remove any potion effects
							for (PotionEffect effect : p.getActivePotionEffects()) {
								p.removePotionEffect(effect.getType());
							}
							
							// give player full health and hunger
							p.setHealth(20);
							p.setFoodLevel(20);
							
							// deop all players, so they can't cheat
							if (p.isOp()) {
								p.setOp(false);
								deop.add(p);
							}
						}
						
						new BukkitRunnable() {
							int x = 5;
							@Override
							public void run() {			
								Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "DeathSwap is starting in " + ChatColor.GOLD + x + "...");
								x = x - 1;
								
								if (x < 1) {
									Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "DeathSwap has started!");
									glassbreak = true;
									
									for (Player p : Bukkit.getOnlinePlayers()) {
										removeGlass(p);
									}
									
									deathSwap();
									
									this.cancel();
								}

							}					
						}.runTaskTimer(DeathSwap.getPlugin(DeathSwap.class), 0, 20);
						
					}
					else if (args[0].equalsIgnoreCase("off")) {
						dswapallow = false;
						Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "Deathswap has been disabled!");
						
						// re-op players that were de-oped
						reop();
						
						return true;
					}
					else {
						sender.sendMessage(ChatColor.RED + "Invalid arguments! Use (on) or (off)");
						return true;
					}
				}
				else {
					
					sender.sendMessage(ChatColor.RED + "Invalid arguments! Us (on) or (off)");
					return true;
					
				}
				
			}
			else {
				
				return true;
			}
			
			
		return true;
		
		}
		else {
			sender.sendMessage(ChatColor.RED + "You do not have permission to run this command! Only players may run this command!");
			return true;
		}
	}
	
	// add glass for death swap
	public void spawnGlass(Player p) {
		// For String, either add or remove block
		Material mat = Material.GLASS;
		
		Location ploc = p.getLocation();
		int x = ploc.getBlockX();
		int y = ploc.getBlockY();
		int z = ploc.getBlockZ();
		World w = ploc.getWorld();
		
		Location l1 = new Location(w, x, y + 2, z);
		l1.getBlock().setType(mat);
		Location l2 = new Location(w, x, y + 5, z);
		l2.getBlock().setType(mat);
		Location l3 = new Location(w, x + 1, y + 3, z);
		l3.getBlock().setType(mat);
		Location l4 = new Location(w, x + 1, y + 4, z);
		l4.getBlock().setType(mat);
		Location l5 = new Location(w, x, y + 3, z + 1);
		l5.getBlock().setType(mat);
		Location l6 = new Location(w, x, y + 4, z + 1);
		l6.getBlock().setType(mat);
		Location l7 = new Location(w, x - 1, y + 3, z);
		l7.getBlock().setType(mat);
		Location l8 = new Location(w, x - 1, y + 4, z);
		l8.getBlock().setType(mat);
		Location l9 = new Location(w, x, y + 3, z - 1);
		l9.getBlock().setType(mat);
		Location l10 = new Location(w, x, y + 4, z - 1);
		l10.getBlock().setType(mat);
	}
	
	// remove starting glass of death swap
	public void removeGlass(Player p) {
		Material mat = Material.AIR;
		Location ploc = p.getLocation();
		int x = ploc.getBlockX();
		int y = ploc.getBlockY();
		int z = ploc.getBlockZ();
		World w = ploc.getWorld();
		
		Location l1 = new Location(w, x, y - 1, z);
		l1.getBlock().setType(mat);
		Location l2 = new Location(w, x, y + 2, z);
		l2.getBlock().setType(mat);
		Location l3 = new Location(w, x + 1, y, z);
		l3.getBlock().setType(mat);
		Location l4 = new Location(w, x + 1, y + 1, z);
		l4.getBlock().setType(mat);
		Location l5 = new Location(w, x, y, z + 1);
		l5.getBlock().setType(mat);
		Location l6 = new Location(w, x, y + 1, z + 1);
		l6.getBlock().setType(mat);
		Location l7 = new Location(w, x - 1, y, z);
		l7.getBlock().setType(mat);
		Location l8 = new Location(w, x - 1, y + 1, z);
		l8.getBlock().setType(mat);
		Location l9 = new Location(w, x, y, z - 1);
		l9.getBlock().setType(mat);
		Location l10 = new Location(w, x, y + 1, z - 1);
		l10.getBlock().setType(mat);
	}

	private int rTime;
	private int sec;
	private ArrayList<Location> pLocations;
	private ArrayList<Player> players;
	public static ArrayList<Player> dead = new ArrayList<Player>();

	// death swap function
	public void deathSwap() {
		new BukkitRunnable() {
			@Override
			public void run() {
				// condition to see if all players are dead yet
				if (dead.size() == players.size() - 1) {
					dswapallow = false;
					reop();
					
					// get the last player remaining
					for (Player p : players) {
						if (dead.contains(p)) {
							continue;
						}
						else {
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + p.getName() + ChatColor.RESET + ChatColor.LIGHT_PURPLE + " has won the game!");
						}
					}
					
					this.cancel();
				}
			}
		}.runTaskTimer(DeathSwap.getPlugin(DeathSwap.class), 0, 20);
		
		dead.clear();
		
		// get all the players
		players = new ArrayList<Player>();
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			players.add(p);
		}
		
		Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "You have " + ChatColor.BOLD + "" + ChatColor.GOLD + "5" + ChatColor.RESET + ChatColor.AQUA + " minutes to prepare!");
		
		// set 5 minute starting timer
		rTime = 6000;
		System.out.println("Time: 6000 ticks or 300 seconds");
		
		new BukkitRunnable() {
			@Override
			public void run() {
				cancelRunnable(this, "Main Runnable has been cancelled");
				
				// run when there is 30 seconds remaining
				new BukkitRunnable() {
					@Override
					public void run() {
						cancelRunnable(this, "30 Second Warning has been cancelled");
						if (dswapallow) {
							Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "30 " + ChatColor.LIGHT_PURPLE + "seconds remaining!");
						}
					}
				}.runTaskLater(DeathSwap.getPlugin(DeathSwap.class), rTime - 600);
				
				new BukkitRunnable() {
					@Override
					public void run() {
						cancelRunnable(this, "Countdown Setup has been cancelled");
						
						if (dswapallow) {
							// countdown - when there is 10 seconds remaining
							sec = 10;
							new BukkitRunnable() {
								@Override
								public void run() {
									cancelRunnable(this, "Countdown has been cancelled");
									
									if (sec != 0) {
										Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Swapping in " + ChatColor.GOLD + sec + "...");
										sec = sec - 1;
									}
									else {
										this.cancel();
									}
								}
							}.runTaskTimer(DeathSwap.getPlugin(DeathSwap.class), 0, 20);
							
							new BukkitRunnable() {
								@Override
								public void run() {
									cancelRunnable(this, "Swapping has been cancelled");
									
									players = removePlayers(players, dead);
									
									pLocations = getLocations(players);
									
									// condition for # of players
									if (dswapallow) {
										resetFall(players);
										
										if (players.size() == 2) {
											players.get(0).teleport(pLocations.get(1));
											players.get(1).teleport(pLocations.get(0));
											
											Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "Players have been swapped!");
										}
										
										else if (players.size() == 3) {
											players.get(0).teleport(pLocations.get(1));
											players.get(1).teleport(pLocations.get(2));
											players.get(2).teleport(pLocations.get(0));
										}
										
										else if (players.size() <= 1) {
											Bukkit.getServer().broadcastMessage(ChatColor.RED + "Error! Death Swap has been turned off!");
											dswapallow = false;
											reop();
										}
										
										else {
											for (int x = players.size() - 1; x > 1; x--) {
												Random rand = new Random();
												int l = rand.nextInt(x);
												
												players.get(x).teleport(pLocations.get(l));
											}
										}
									}
									else {
										this.cancel();
									}
									
								}
							}.runTaskLater(DeathSwap.getPlugin(DeathSwap.class), 200);
						}
					}
				}.runTaskLater(DeathSwap.getPlugin(DeathSwap.class), rTime - 200);
				
				new BukkitRunnable() {
					@Override
					public void run() {
						cancelRunnable(this, "Reset Timer has been cancelled");
						
						if (dswapallow) {
							// reset the time
							rTime = randomTime(1200, 3600);
							int seconds = (int) Math.floor(rTime / 20);
							System.out.println("Time: " + rTime + " ticks or " + seconds + " seconds");
						}
					}
				}.runTaskLater(DeathSwap.getPlugin(DeathSwap.class), rTime);
								
				if (! dswapallow) {
					this.cancel();
				}
				
			}
		}.runTaskTimer(DeathSwap.getPlugin(DeathSwap.class), 0, rTime);
	}
	
	// return a random time for death swap
	public int randomTime(int min, int max) {
		Random rand = new Random();
		int time = rand.nextInt(max - min);
		time += min;
		
		return time;
	}
		
	public ArrayList<Location> getLocations(ArrayList<Player> people) {
		ArrayList<Location> locations = new ArrayList<Location>();
		
		for (Player p : people) {
			locations.add(p.getLocation());
		}
		
		return locations;
	}
	
	// remove players from players list
	public ArrayList<Player> removePlayers(ArrayList<Player> alive, ArrayList<Player> death) {
		for (Player p : death) {
			if (alive.contains(p)) {
				alive.remove(p);
			}
		}
		
		return alive;
	}
	// reop any players that were unoped because of the plugin
	public void reop() {
		for (Player p : deop) {
			p.setOp(true);
		}
	}
	
	public void resetFall(ArrayList<Player> players) {
		for (Player p : players) {
			p.setFallDistance(0);
		}
	}
	
	// cancel the swaps when the game has finished or is stopped
	public void cancelRunnable(BukkitRunnable bk, String msg) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (! dswapallow) {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + msg);
					bk.cancel();
					this.cancel();
				}
			}
		}.runTaskTimer(DeathSwap.getPlugin(DeathSwap.class), 0, 1);
	}

	
	// give players the rulebook for death swap
	public void addRuleBook(Player p) {
		ItemStack rulebook = new ItemStack(Material.WRITTEN_BOOK);
		
		BookMeta meta = (BookMeta) rulebook.getItemMeta();
		meta.setAuthor("KadenL9");
		meta.setTitle("How To Play Minecraft Death Swap");
		
		ArrayList<String> pages = new ArrayList<String>();
		pages.add(
		ChatColor.DARK_BLUE + "1. " + ChatColor.DARK_PURPLE + "There must be at least" + ChatColor.GOLD + " 2 " + ChatColor.DARK_PURPLE + "players.\n\n" +
		ChatColor.DARK_BLUE + "2. " + ChatColor.DARK_PURPLE + "The goal of Minecraft Death Swap is to get the other people killed before you are killed.\n\n" +
		ChatColor.DARK_BLUE + "3. " + ChatColor.DARK_PURPLE + "Players are not allowed to enter the " + ChatColor.GOLD + "nether " + ChatColor.DARK_PURPLE + "or the " + ChatColor.GOLD + "end\n\n" +
		ChatColor.DARK_BLUE + "4. " + ChatColor.DARK_PURPLE + "All players will swap ");	
		
		pages.add(
		ChatColor.DARK_PURPLE + "positions every " + ChatColor.GOLD + "1 " + ChatColor.DARK_PURPLE +  " to " + ChatColor.GOLD + " 3 " + ChatColor.DARK_PURPLE + "minutes which is randomized. Also, before the first swap, you will have a set 5 minutes to prepare.\n\n" +
		ChatColor.DARK_BLUE + "5. " + ChatColor.DARK_PURPLE + "Once you die, you're out and will be put into spectator mode until everyone wins. You will have the ability to teleport to other players");

		pages.add(ChatColor.DARK_BLUE + "6. " + ChatColor.DARK_PURPLE + "At the beginning of each game, all inventories are cleared.");
		
		pages.add(
		ChatColor.DARK_BLUE + "|||| " + ChatColor.DARK_PURPLE + "In the case of " + ChatColor.GOLD + "2 " + ChatColor.DARK_PURPLE + "players, both players will swap positions\n\n" +
		ChatColor.DARK_BLUE + "|||| " + ChatColor.DARK_PURPLE + "In the case of " + ChatColor.GOLD + "3 " + ChatColor.DARK_PURPLE + "players, all three players will swap fixed positions.\n\n" +
		ChatColor.DARK_BLUE + "|||| " + ChatColor.DARK_PURPLE + "In the case of " + ChatColor.GOLD + "4+ " + ChatColor.DARK_PURPLE + "players, swapped positions are random, giving a chance of not swapping at all");

		meta.setPages(pages);
		
		rulebook.setItemMeta(meta);
		p.getInventory().addItem(rulebook);
	}
}

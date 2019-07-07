package fr.themsou.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.themsou.TntWars.RunParty;
import fr.themsou.main.main;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.Spawns;

public class SpawnCmd implements TabCompleter, CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg1, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("spawn")){
				
				if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
					Spawns CSpawns = new Spawns();
					
					if(args.length == 0){
						
						p.teleport(CSpawns.getSpawnSpawn(CSpawns.getSpawns().split("/")[new Random().nextInt(CSpawns.getSpawns().split("/").length)]));
						p.sendMessage("§bTéléportation à un spawn §3aléatoire");
						p.sendMessage("§6Vous pouvez aussi vous téléporter aux autres villes avec §c/spawn <" + new Spawns().getSpawns() + ">");
						
					}else if(args.length == 1){
						
						args[0] = args[0].toLowerCase();
						String first = args[0].substring(0, 1).toUpperCase();
						if(args[0].split("_").length >= 2){
							String second = args[0].split("_")[1].substring(0, 1).toUpperCase();
							args[0] = first + args[0].split("_")[0].substring(1) + "_" + second + args[0].split("_")[1].substring(1);
						}else args[0] = first + args[0].substring(1);
								
						if(main.config.contains("claim.list." + args[0]) && new Spawns().getSpawns().contains(args[0])){
							
							p.teleport(new Spawns().getSpawnSpawn(args[0]));
							p.sendMessage("§bTéléportation au §3Spawn de " + args[0]);
							
						}else p.sendMessage("§c/spawn [" + new Spawns().getSpawns() + "]");
					}else p.sendMessage("§c/spawn [" + new Spawns().getSpawns() + "]");
				}else p.sendMessage("§c/spawn [" + new Spawns().getSpawns() + "]");
				
			}else if(cmd.getName().equalsIgnoreCase("hub") || cmd.getName().equalsIgnoreCase("lobby")){
				
				
				if(p.getWorld() == Bukkit.getWorld("BedWars")){
					
					if(p.getGameMode() == GameMode.SURVIVAL){
						for(ItemStack item : p.getInventory().getContents()){
							if(item != null){
								if(item.getType() == Material.IRON_INGOT || item.getType() == Material.GOLD_INGOT || item.getType() == Material.DIAMOND || item.getType() == Material.EMERALD){
									p.getWorld().dropItem(p.getLocation(), item);
									p.getInventory().remove(item);
								}
							}
						}
						
					}
					
				}
				
				p.teleport(new Location(Bukkit.getWorld("hub"), 0, 50, 0));
				
				p.setGameMode(GameMode.SURVIVAL);
				
				p.getInventory().clear();
				p.setHealth(20);
				p.setFoodLevel(20);
				
				ItemStack a = new ItemStack(Material.DIAMOND, 1);
				ItemMeta aM = a.getItemMeta();
				aM.setDisplayName("§3§lVIP");
				a.setItemMeta(aM);
				p.getInventory().setItem(0, a);
				
				ItemStack b = new ItemStack(Material.COMPASS, 1);
				ItemMeta bM = b.getItemMeta();
				bM.setDisplayName("§3§lMENU");
				b.setItemMeta(bM);
				p.getInventory().setItem(4, b);
				
				title.sendTitle(p, "§cChoissez votre mode de jeu,", "§cavec la bousole", 60);
				
				RunParty CRunParty = new RunParty();
				CRunParty.Playerquit(p);
				
			}else if(cmd.getName().equalsIgnoreCase("world")){
				
				if(args.length != 0){
//		TP					
					if(args[0].equalsIgnoreCase("tp")){
						
						if(args.length == 2){
							
							if(Bukkit.getWorld(args[1]) != null){
								
								int x = main.config.getInt("world.list." + args[1] + ".x");
								int y = main.config.getInt("world.list." + args[1] + ".y");
								int z = main.config.getInt("world.list." + args[1] + ".z");
								if(y == 0) y = 100;
								p.teleport(new Location(Bukkit.getWorld(args[1]), x, y, z));
								
							}else p.sendMessage("§cMonde introuvable");
							
						}else p.sendMessage("§c/world tp <monde>");
						
						
//		SETSPAWN
					}else if(args[0].equalsIgnoreCase("setspawn")){
											
							p.sendMessage("§bLe spawn du monde a bien été défini a votre positon actuelle");
							main.config.set("world.list." + p.getLocation().getWorld().getName() + ".x", p.getLocation().getBlockX());
							main.config.set("world.list." + p.getLocation().getWorld().getName() + ".y", p.getLocation().getBlockY());
							main.config.set("world.list." + p.getLocation().getWorld().getName() + ".z", p.getLocation().getBlockZ());
								
					}else p.sendMessage("§c/world <tp/setspawn> [monde]");
				}else p.sendMessage("§c/world <tp/setspawn> [monde]");
				
			}
			
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg, String[] args){
		
		if(sender instanceof Player){
			
			if(args.length == 1){
				
				if(cmd.getName().equalsIgnoreCase("spawn")){
					ArrayList<String> returns = new ArrayList<>();
					
					for(String city : main.config.getConfigurationSection("claim.list").getKeys(false)){
						if(city.startsWith(args[0]) && main.config.contains("claim.list." + city + ".x")){
							returns.add(city);
						}
					}
					return returns;
				}
				if(cmd.getName().equalsIgnoreCase("world")){
					return GeneralCmd.matchTab(Arrays.asList("tp","setspawn"), args[0]);
				}
				
			}else if(args.length == 2){
				
				if(cmd.getName().equalsIgnoreCase("world")){
					if(args[0].equals("tp")){
						return GeneralCmd.matchTab(new ArrayList<>(main.config.getConfigurationSection("world.list").getKeys(false)), args[0]);
					}
				}
			}
		}
			
		return Arrays.asList("");
	}
	

}

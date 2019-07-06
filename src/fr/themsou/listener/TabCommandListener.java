package fr.themsou.listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.themsou.TntWars.RunParty;
import fr.themsou.discord.Link;
import fr.themsou.inv.admin;
import fr.themsou.main.main;
import fr.themsou.methodes.Boss;
import fr.themsou.methodes.Config;
import fr.themsou.methodes.Gamemode;
import fr.themsou.methodes.Grade;
import fr.themsou.methodes.Punish;
import fr.themsou.methodes.Schematics;
import fr.themsou.methodes.login;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.BasicCommand;
import fr.themsou.rp.claim.Spawns;
import fr.themsou.rp.ent.Commands;
import fr.themsou.rp.inv.Inventory;

public class TabCommandListener implements CommandExecutor{

	@SuppressWarnings("unused")
	private main pl;
	
	public TabCommandListener(main pl) {
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg1, String[] args) {
		
		Grade CGrade = new Grade();
		
		Inventory CInventory = new Inventory();
		login CLogin = new login();
		Schematics CSchematics = new Schematics();
		
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			int perm = CGrade.getPlayerPermition(p.getName());
			
//
//	STAT
//
			if(cmd.getName().equalsIgnoreCase("getstat")){
				if(args.length == 2){
					p.sendMessage("§c" + args[0] + "§6 a miné §c" + Bukkit.getPlayer(args[0]).getStatistic(Statistic.MINE_BLOCK, Material.valueOf(args[1])) + " " + args[1]);
				}
				else p.sendMessage("§c/getstat <joueur> <block>");
			}		
//
//	GRADES
//
			else if(cmd.getName().equalsIgnoreCase("grade")){
				if(args.length ==  2) CGrade.changePlayerGrade(p, args[0], args[1]);
				else p.sendMessage("§c/grade <Joueur> <joueur,vip,youtuber,modo,Super-Modo,admin,developeur,fondateur>");
			}
//
//	DISCORD
//
			else if(cmd.getName().equalsIgnoreCase("discord")){
				if(args.length == 1){
					new Link().linkPlayer(p.getName(), args[0]);
					p.sendMessage("§3Un message de confirmation vous a été envoyé sur discord");
				}
				else p.sendMessage("§c/discord <pseudo>");
			}
//
//	CLAIM
//
			else if(cmd.getName().equalsIgnoreCase("claim")){
				new BasicCommand(p).command(args);
			}
//
//	GAMEMODE
//
			else if(cmd.getName().equalsIgnoreCase("g")){
				new Gamemode().gamemode(p);
				
			}
//
//	MENU GUI
//
			else if(cmd.getName().equalsIgnoreCase("?") || cmd.getName().equalsIgnoreCase("help")){
				if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
					
					CInventory.openMainInventory(p);
					
				}else if(p.getWorld() == Bukkit.getWorld("BedWars") || p.getWorld() == Bukkit.getWorld("BedWars-ressources")){
					
					p.openInventory(main.Lapmenu);
					
				}else{
					
					p.openInventory(main.menu);
					
				}
			}
//
//	SPAWN
//
			else if(cmd.getName().equalsIgnoreCase("spawn")){
				if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
					if(args.length == 0){
						Spawns CSpawns = new Spawns();
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
					}
					
					
				}
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
				
				
					
			}
			
//
//	WORLD
//	
			else if(cmd.getName().equalsIgnoreCase("world")){
				
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
			
			
//
//	LOGIN
//		
			else if(cmd.getName().equalsIgnoreCase("l") || cmd.getName().equalsIgnoreCase("login")) CLogin.loginPlayer(p, args);
			else if(cmd.getName().equalsIgnoreCase("reg") || cmd.getName().equalsIgnoreCase("register")) CLogin.RegisterPlayer(p, args);
//
//	SCHEMATIC
//
			else if(cmd.getName().equalsIgnoreCase("loadschematic")){
				if(args.length == 1){
					CSchematics.loadSchematic(p.getLocation(), args[0]);
					p.sendMessage("§bLa Schematic a bien été chargé à votre location !");
				}else p.sendMessage("§c/loadschematic <nom>");
				
			}
//
//	CONFIG
//				
			else if(cmd.getName().equalsIgnoreCase("config")){
				new Config().config(args, p);
			}
//
//	MONEY
//				
			else if(cmd.getName().equalsIgnoreCase("setmoney")){
				if(args.length == 2){
					main.economy.withdrawPlayer(args[0], main.economy.getBalance(args[0]));
					main.economy.depositPlayer(args[0], Double.parseDouble(args[1]));
					p.sendMessage("§3" + args[0] + "§b à maintenant §3" + main.economy.getBalance(args[0]) + "€");
				}else p.sendMessage("§c/setmoney <joueur> <quantité>");
			}
			else if(cmd.getName().equalsIgnoreCase("money")){
				if(args.length == 1){
					p.sendMessage("§3" + args[0] + "§b a §3" + main.economy.getBalance(args[0]) + " €");
				}else p.sendMessage("§bVous avez §3" + main.economy.getBalance(p.getName()) + " §3€");
			}
			
//
//	ENTREPRISES
//				
			else if(cmd.getName().equalsIgnoreCase("ent")){
				Commands CCommands = new Commands(p);
				if(args.length >= 1){
					
					if(args[0].equalsIgnoreCase("create")){ // Créer
						if(args.length == 2){
							CCommands.create(args[1]);
						}else p.sendMessage("§c/ent create <nom>");
						
					}else if(args[0].equalsIgnoreCase("leave")){ // Quitter
						CCommands.leave();
						
					}else if(args[0].equalsIgnoreCase("list")){ // List
						CCommands.list();
						
					}else if(args[0].equalsIgnoreCase("info")){ // List
						if(args.length == 2){
							CCommands.info(args[1]);
						}else p.sendMessage("§c/ent info <entreprise>");
						
					}else if(args[0].equalsIgnoreCase("join")){ // Join
						CCommands.join();
						
					}else if(args[0].equalsIgnoreCase("hire")){ // Embaucher
						if(args.length == 3 && (args[2].equalsIgnoreCase("aucun") || args[2].equalsIgnoreCase("salarié") || args[2].equalsIgnoreCase("manager"))){
							int role = 0;
							if(args[2].equalsIgnoreCase("salarié")) role = 1;
							else if(args[2].equalsIgnoreCase("manager")) role = 2;
							CCommands.hire(Bukkit.getOfflinePlayer(args[1]), role);
						}else p.sendMessage("§c/ent hire <joueur> <aucun/salarié/manager>");
						
					}else if(args[0].equalsIgnoreCase("money")){ // Money
						CCommands.money();
						
					}else if(args[0].equalsIgnoreCase("pay")){ // Payer
						if(args.length == 3){
							if(Integer.parseInt(args[2]) >= 1){
								CCommands.pay(args[1], Integer.parseInt(args[2]));
							}else p.sendMessage("§cVous devez entrer un nombre supèrieur à 0");
						}else p.sendMessage("§c/ent pay <joueur>/ent:<entrprise> <argent>");
						
					}else if(args[0].equalsIgnoreCase("sign")){ // Pancarte
						if(args.length >= 3){
							if(Integer.parseInt(args[1]) >= 1){
								
								String desc = ""; int argP = 0;
								for(String dscP : args){
									if(argP == 2) desc = dscP;
									else if(argP >= 2) desc = desc + " " + dscP;
									argP ++;
								}
								
								CCommands.sign(Integer.parseInt(args[1]), desc);
							}else p.sendMessage("§cVous devez entrer des nombres supèrieurs à 0");
						}else p.sendMessage("§c/ent sign <prix> <description");
						
						
					}else if(args[0].equalsIgnoreCase("src")){ // Ressource
						if(args.length == 2){
							if(args[1].equalsIgnoreCase("clear")) CCommands.srcRemove();
							else p.sendMessage("/ent src clear");
						}else if(args.length == 1) CCommands.src();
						else p.sendMessage("/ent src clear");
					}else if(args[0].equalsIgnoreCase("log")){ // Logs
						if(args.length == 2){
							CCommands.log(args[1]);
						}else p.sendMessage("§c/ent log <joueur>");
						
					}else if(args[0].equalsIgnoreCase("buyers")){ // Logs
						if(args.length == 1){
							CCommands.buyers();
						}else p.sendMessage("§c/ent buyers");
						
					}else if(args[0].equalsIgnoreCase("rename")){ // Logs
						if(args.length == 2){
							CCommands.rename(args[1]);
						}else p.sendMessage("§c/ent rename <nom>");
						
					}else{
						CCommands.infoMessage();
					}
				}else{
					CCommands.infoMessage();
				}
			}
			
//
//	ADMIN-GUI
//					
			else if(cmd.getName().equalsIgnoreCase("a")){
				if(perm >= 3){
					
					admin Cadmin = new admin();
					Punish CPunish = new Punish();
					
					if(args.length == 0) Cadmin.oppenadmininventory(p);
					else{
						if(perm == 3 || perm == 4){ // MODO - BUILDER
								
							if(args[0].equalsIgnoreCase("mute")){
								
								if(args.length == 4) CPunish.punishPlayer(args[1], p.getName(), "mute", Integer.parseInt(args[2]), args[3]);
								else p.sendMessage("§c/a mute <Joueur> <Minutes> <Raison>");
								
							}else if(args[0].equalsIgnoreCase("unmute")){
								
								if(args.length == 3) CPunish.pardonPlayer(args[1], p.getName(), "mute", args[2]);
								else p.sendMessage("§c/a unmute <Joueur> <Raison>");
								
							}else if(args[0].equalsIgnoreCase("kick")){
								
								if(args.length == 3) CPunish.punishPlayer(args[1], p.getName(), "kick", 0, args[2]);
								else p.sendMessage("§c/a kick <Joueur> <Raison>");
								
							}else if(args[0].equalsIgnoreCase("all")) Cadmin.oppenadminallinventory(p);
								
							else p.sendMessage("§c/a [mute/unmute/kick/all]");
							
						}else if(perm >= 5){ // SM - ADMIN
							
							if(args[0].equalsIgnoreCase("ban")){
								
								if(args.length == 4) CPunish.punishPlayer(args[1], p.getName(), "ban", Integer.parseInt(args[2]), args[3]);
								else p.sendMessage("§c/a ban <Joueur> <Jours> <Raison>");
								
							}else if(args[0].equalsIgnoreCase("unban")){
								
								if(args.length == 3) CPunish.pardonPlayer(args[1], p.getName(), "ban", args[2]);
								else p.sendMessage("§c/a unban <Joueur> <Raison>");
								
							}else if(args[0].equalsIgnoreCase("mute")){
								
								if(args.length == 4) CPunish.punishPlayer(args[1], p.getName(), "mute", Integer.parseInt(args[2]), args[3]);
								else p.sendMessage("§c/a mute <Joueur> <Minutes> <Raison>");
								
							}else if(args[0].equalsIgnoreCase("unmute")){
								
								if(args.length == 3) CPunish.pardonPlayer(args[1], p.getName(), "mute", args[2]);
								else p.sendMessage("§c/a unmute <Joueur> <Raison>");
								
							}else if(args[0].equalsIgnoreCase("kick")){
								
								if(args.length == 3) CPunish.punishPlayer(args[1], p.getName(), "kick", 0, args[2]);
								else p.sendMessage("§c/a kick <Joueur> <Raison>");
								
							}else if(args[0].equalsIgnoreCase("all")) Cadmin.oppenadminallinventory(p);
								
							else p.sendMessage("§c/a [ban/mute/unban/unmute/kick/all]");
						}
					}
					
				}else p.sendMessage("§cCette commande est interdite !");
			}
//
//	BOSS
//	
			else if(cmd.getName().equalsIgnoreCase("boss")){
				if(args.length >= 1){
					if(args[0].equals("add")){	
						if(args.length >= 5){
							String texte = ""; int skip = 4;
							for(String text : args){
								if(skip == 0) texte = texte + text.replaceAll("&", "§") + " "; else skip --;
							}
							new Boss().createBar(args[1], args[2], args[3], texte, p);
						}else p.sendMessage("§c/boss add <nom> <blanc/bleu/vert/rose/violet/rouge/jaune/aléatoire> <6/10/12/20/none> <texte");
					}else if(args[0].equals("remove")){	
						if(args.length >= 2){
							new Boss().removeBar(args[1], p);
						}else p.sendMessage("§c/boss remove <nom>");
					}else if(args[0].equals("list")){	
						new Boss().list(p);
					}
				}else p.sendMessage("§c/boss <add/list/remove>");
			}
//
//	TAG
//	
			else if(cmd.getName().equalsIgnoreCase("tag")){
				if(args.length != 0){
					if(args[0].equals("remove")){
						for (Entity entity : p.getLocation().getChunk().getEntities()){
					    	if(entity instanceof ArmorStand){
					    		entity.remove();
					    	}
					    }
						p.sendMessage("§btag du chunck suprimée !");
					}else{
						String texte = "";
						for(String text : args){
							texte = texte + text.replaceAll("&", "§") + " ";
						}
						
						ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
						stand.setCustomNameVisible(true);
						stand.setCustomName(texte);
						stand.setVisible(false);
						stand.setCollidable(false);
						p.sendMessage("§bTag placée");
					}
				}else p.sendMessage("§c/tag <Texte>     /tag remove");	
			}
			
		}else{
			
			if(cmd.getName().equalsIgnoreCase("grade")){
				if(args.length ==  2) CGrade.changePlayerGradeWithConsole(args[0], args[1]);
				else sender.sendMessage("§c/grade <Joueur> <joueur,vip,youtuber,modo,builder,admin,developeur,fondateur>");
				
				
			}else if(cmd.getName().equalsIgnoreCase("a")){
				
				Punish CPunish = new Punish();
							
				if(args[0].equalsIgnoreCase("ban")){
					
					if(args.length == 4) CPunish.punishPlayer(args[1], "Console", "ban", Integer.parseInt(args[2]), args[3]);
					else Bukkit.getConsoleSender().sendMessage("§c/a ban <Joueur> <Jours> <Raison>");
					
				}else if(args[0].equalsIgnoreCase("unban")){
					
					if(args.length == 3) CPunish.pardonPlayer(args[1], "Console", "ban", args[2]);
					else Bukkit.getConsoleSender().sendMessage("§c/a unban <Joueur> <Raison>");
					
				}else if(args[0].equalsIgnoreCase("mute")){
					
					if(args.length == 4) CPunish.punishPlayer(args[1], "Console", "mute", Integer.parseInt(args[2]), args[3]);
					else Bukkit.getConsoleSender().sendMessage("§c/a mute <Joueur> <Minutes> <Raison>");
					
				}else if(args[0].equalsIgnoreCase("unmute")){
					
					if(args.length == 3) CPunish.pardonPlayer(args[1], "Console", "mute", args[2]);
					else Bukkit.getConsoleSender().sendMessage("§c/a unmute <Joueur> <Raison>");
					
				}else if(args[0].equalsIgnoreCase("kick")){
					
					if(args.length == 3) CPunish.punishPlayer(args[1], "Console", "kick", 0, args[2]);
					else Bukkit.getConsoleSender().sendMessage("§c/a kick <Joueur> <Raison>");
					
				}else Bukkit.getConsoleSender().sendMessage("§c/a [ban/mute/unban/unmute/kick]");
						
			}
			
			
		}
		return false;
	}

}

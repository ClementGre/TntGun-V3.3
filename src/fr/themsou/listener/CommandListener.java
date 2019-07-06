package fr.themsou.listener;

import java.util.Date;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.meta.ItemMeta;
import fr.themsou.discord.Roles;
import fr.themsou.main.main;
import fr.themsou.methodes.Grade;
import fr.themsou.methodes.Schematics;
import fr.themsou.methodes.SendStat;
import fr.themsou.methodes.info;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.message;
import fr.themsou.rp.claim.Spawns;

public class CommandListener implements Listener{
	
	
	main pl;
	public CommandListener(main pl) {
		this.pl = pl;
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnCmd(PlayerCommandPreprocessEvent e){
		
		Player p = e.getPlayer();
		String msg = e.getMessage();
		String[] args = msg.split(" ");
		
		Grade CGrade = new Grade();
		
		int i = CGrade.getPlayerPermition(p.getName());
		
		if(main.config.getInt(p.getName() + ".status") == 1){
			if(p.getGameMode() == GameMode.SURVIVAL){
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////// ROLE PLAY ////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
//
//	COMMANDES JOUEURS +
//	
					if(args[0].equalsIgnoreCase("/hub") ||
						args[0].equalsIgnoreCase("/spawn") ||
						args[0].equalsIgnoreCase("/lobby") ||
						args[0].equalsIgnoreCase("/?") ||
						args[0].equalsIgnoreCase("/help") ||
						args[0].equalsIgnoreCase("/tete") ||
						args[0].equalsIgnoreCase("/ec") ||
						args[0].equalsIgnoreCase("/craft") ||
						args[0].equalsIgnoreCase("/nick")  || 
						args[0].equalsIgnoreCase("/skin") ||
						args[0].equalsIgnoreCase("/tpa") ||
						args[0].equalsIgnoreCase("/tphere") ||
						args[0].equalsIgnoreCase("/tpayes") ||
						args[0].equalsIgnoreCase("/tphereyes") ||
						args[0].equalsIgnoreCase("/money") ||
						args[0].equalsIgnoreCase("/home") ||
						args[0].equalsIgnoreCase("/sethome") ||
						args[0].equalsIgnoreCase("/delhome") ||
						args[0].equalsIgnoreCase("/r") ||
						args[0].equalsIgnoreCase("/msg") ||
						args[0].equalsIgnoreCase("/m") ||
						args[0].equalsIgnoreCase("/claim") ||
						args[0].equalsIgnoreCase("/itemsell") ||
						args[0].equalsIgnoreCase("/a") ||
						args[0].equalsIgnoreCase("/discord") ||
						args[0].equalsIgnoreCase("/reg") ||
						args[0].equalsIgnoreCase("/register") ||
						args[0].equalsIgnoreCase("/ent")){}

//								
//	COMMANDES BUILDER +
//
					else if(args[0].equalsIgnoreCase("/g")){
						if(i <= 3){
							p.sendMessage("§cCette commande n'existe pas ou ne vous est pas autorisée !");
							e.setCancelled(true);
						}
					}
//
//	COMMANDES AUTRE
//
					else if(args[0].equalsIgnoreCase("/infoview")){
						
						e.setCancelled(true);
						new info().viewPlayerInfo(p, args[1], args[2]);
						
					}else if(args[0].equalsIgnoreCase("/pay")){
						e.setCancelled(true);
						double prix = 0;
						if(args.length == 3){
							
							try{
								prix = Double.parseDouble(args[2]);
							}catch (NumberFormatException e2){
								p.sendMessage("§c/pay <joueur>/ent:<entreprise> <argent>");
								return;
						    }
							
							if(prix < 0){
								p.sendMessage("§cHahaha petit malin, tu croyais vraiment pouvoir arnaquer les gens comme ça !!!  XD");
								return;
							}
							
							if(main.economy.getBalance(p) >= prix){
								if(args[1].split(":")[0].equals("ent")){
									args[1] = args[1].replace("ent:", "");
									
									if(main.config.contains("ent.list." + args[1])){
										
										main.economy.withdrawPlayer(p, prix);
										main.config.set("ent.list." + args[1] + ".money", main.config.getInt("ent.list." + args[1] + ".money") + prix);
										p.sendMessage("§c" + prix + "€§6 ont bien été envoyés à l'entreprise §c" + args[1]);
										
										String pdg = main.config.getString("ent.list." + args[1] + ".pdg");
										if(Bukkit.getOfflinePlayer(pdg).isOnline()) Bukkit.getPlayerExact(pdg).sendMessage("§c" + p.getName() + " §6a envoyé §c" + prix + "€§6 à votre entreprise");
										
									}else p.sendMessage("§cIl n'existe pas d'entreprises avec le nom §6" + args[1]);
									
								}else{
									
									if(main.config.contains(p.getName())){
										
										main.economy.withdrawPlayer(p, prix);
										main.economy.depositPlayer(Bukkit.getOfflinePlayer(args[1]), prix);
										p.sendMessage("§c" + prix + "€§6 ont bien été envoyés à §c" + args[1]);
										if(Bukkit.getOfflinePlayer(args[1]).isOnline()) Bukkit.getPlayerExact(args[1]).sendMessage("§c" + p.getName() + " §6vous a envoyé §c" + prix + "€");
									
									}else p.sendMessage("§cAucun joueur trouvé avec le nom §6" + p.getName());
								
								}
							}else p.sendMessage("§cVous devez avoir §4" + prix + "€");
							
						}else p.sendMessage("§c/pay <joueur>/ent:<entreprise> <argent>");
						
					}else{
						e.setCancelled(true);
						p.sendMessage("§cCette commande n'existe pas ou ne vous est pas autorisée !");
					}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////// BEDWARS //////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				}else if(p.getWorld() == Bukkit.getWorld("BedWars")){
					
					if(args[0].equalsIgnoreCase("/hub")
					|| args[0].equalsIgnoreCase("/lobby")
					|| args[0].equalsIgnoreCase("/msg")
					|| args[0].equalsIgnoreCase("/m")
					|| args[0].equalsIgnoreCase("/r")
					|| args[0].equalsIgnoreCase("/?")
					|| args[0].equalsIgnoreCase("/help")
					|| args[0].equalsIgnoreCase("/nick")
					|| args[0].equalsIgnoreCase("/link")
					|| args[0].equalsIgnoreCase("/a")
					|| args[0].equalsIgnoreCase("/discord")
					|| args[0].equalsIgnoreCase("/reg")
					|| args[0].equalsIgnoreCase("/register")
					|| args[0].equalsIgnoreCase("/skin")){}
					
//												
//	COMMANDES BUILDER +
//
				else if(args[0].equalsIgnoreCase("/g")){
					if(i <= 3){
						p.sendMessage("§cCette commande n'existe pas ou ne vous est pas autorisée !");
						e.setCancelled(true);
					}
				}
//
//	COMMANDES AUTRE
//
				else if(args[0].equalsIgnoreCase("/infoview")){
					
					e.setCancelled(true);
					new info().viewPlayerInfo(p, args[1], args[2]);
					
				}else{
					e.setCancelled(true);
					p.sendMessage("§cCette commande n'existe pas ou ne vous est pas autorisée !");
				}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////// HUB //////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				}else{
				
					if(args[0].equalsIgnoreCase("/hub")
						|| args[0].equalsIgnoreCase("/lobby")
						|| args[0].equalsIgnoreCase("/msg")
						|| args[0].equalsIgnoreCase("/m")
						|| args[0].equalsIgnoreCase("/r")
						|| args[0].equalsIgnoreCase("/?")
						|| args[0].equalsIgnoreCase("/help")
						|| args[0].equalsIgnoreCase("/nick")
						|| args[0].equalsIgnoreCase("/a")
						|| args[0].equalsIgnoreCase("/discord")
						|| args[0].equalsIgnoreCase("/reg")
						|| args[0].equalsIgnoreCase("/register")
						|| args[0].equalsIgnoreCase("/skin")){}
						
						

//
//COMMANDES BUILDER +
//
						else if(args[0].equalsIgnoreCase("/g")){
							if(i <= 3){
								p.sendMessage("§cCette commande n'existe pas ou ne vous est pas autorisée !");
								e.setCancelled(true);
							}
						}
//
//COMMANDES AUTRE
//
						else if(args[0].equalsIgnoreCase("/infoview")){
							
							e.setCancelled(true);
							new info().viewPlayerInfo(p, args[1], args[2]);
							
						}else{
							e.setCancelled(true);
							p.sendMessage("§cCette commande n'existe pas ou ne vous est pas autorisée !");
							
							
						}
					}
				}
			
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////// GAMEMODE 1 ///////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			else{
				
				
				if(args[0].equalsIgnoreCase("/ban") || args[0].equalsIgnoreCase("/tempban") || args[0].equalsIgnoreCase("/banip") || args[0].equalsIgnoreCase("/unban") || args[0].equalsIgnoreCase("/mute")){
					
					e.setCancelled(true);
					p.sendMessage("§cVeuillez utiliser le /a !");
					
				}else if(args[0].equalsIgnoreCase("/infoview")){
					
					e.setCancelled(true);
					new info().viewPlayerInfo(p, args[1], args[2]);
					
				}else if(args[0].equalsIgnoreCase("/clearconfig")){
					e.setCancelled(true);
					
					
					Set<String> section = main.config.getConfigurationSection("").getKeys(false);	
					String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
					String[] item = items.split(",");
				
					for(int id = 1; id <= section.size(); id++){
						int number = id - 1;
						
						if(!main.config.contains(item[number] + ".list")){
							
							
							if(!main.config.contains(item[number] + ".mdp")){
								main.config.set(item[number], null);
								Bukkit.broadcastMessage("§cSupression du compte : §6" + item[number] + "§e (non enregistré)");
								
							}else{
								
								double time = main.config.getInt(item[number] + ".time.rp") + main.config.getInt(item[number] + ".time.tntwars") + main.config.getInt(item[number] + ".time.bedwars");
								double space = new realDate().getMinutesSpace(main.config.getString(item[number] + ".stat.last"));
								if(time == 0) time = 1;
								
								if((space / 60.0 / 24.0) / (time / 60.0) > 10){
							
								
									Bukkit.broadcastMessage("§cSupression du compte : §6" + item[number] + "§e (innactivitée)");
									
									if(main.config.contains(item[number] + ".claim")){
										for(String claimId : main.config.getConfigurationSection(item[number] + ".claim").getKeys(false)){
											
											String spawn = new Spawns().getSpawnNameWithId(Integer.parseInt(claimId));
											
											int minx = main.config.getInt("claim.list." + spawn + "." + claimId + ".x1");
											int minz = main.config.getInt("claim.list." + spawn + "." + claimId + ".z1");
											int maxx = main.config.getInt("claim.list." + spawn + "." + claimId + ".x2");
											int maxz = main.config.getInt("claim.list." + spawn + "." + claimId + ".z2");
											int x = minx + (maxx - minx) / 2;
											int z = minz + (maxz - minz) / 2;
											int y = main.config.getInt("claim.list." + spawn + "." + claimId + ".y1");
											
											message.sendNmsMessageCmd(p, "§4" + item[number] + " §c avait un claim, vous devez le supprimer", "§3Cliquez pour vous téléporter", "/tp " + x + " " + y + " " + z);
										}
									}
									
									main.config.set(item[number], null);
								}
							}
							
						}
					}
				
				}else if(args[0].equalsIgnoreCase("/date")){
					
					e.setCancelled(true);
					Date date = new Date();
					p.sendMessage("§cDATE : §bJour de la semaine:§3 " + date.getDay() + " §bHeure:§3 " + date.getHours() + ":" + date.getMinutes() + " §bJour du mois:§3 " + date.getDate());
					Date realDate = new realDate().getRealDate();
					p.sendMessage("§cDATE RÈELLE: §bJour de la semaine:§3 " + realDate.getDay() + " §bHeure:§3 " + realDate.getHours() + ":" + realDate.getMinutes() + " §bJour du mois:§3 " + realDate.getDate());
				
				}else if(args[0].equalsIgnoreCase("/sendstatsday")){
					e.setCancelled(true);
					
					SendStat CSendStat = new SendStat();
					CSendStat.sendDay(pl);
				
				}else if(args[0].equalsIgnoreCase("/sendstatsdayno")){
					e.setCancelled(true);
					
					SendStat CSendStat = new SendStat();
					CSendStat.sendDayNoClear(pl);
				
				}else if(args[0].equalsIgnoreCase("/sendstatsweek")){
					e.setCancelled(true);
					
					SendStat CSendStat = new SendStat();
					CSendStat.sendWeek(pl);
				
				}else if(args[0].equalsIgnoreCase("/bedwarsnewgame")){
					e.setCancelled(true);
					
					main.config.set("bedwars.list.teams", null);
					for(int i2 = 1; i2 <= 4; i2++){
						
						main.config.set("bedwars.list.teams." + i2 + ".bed", 1);
						
					}
					main.config.set("bedwars.list.status", 0);
					Location loc1 = new Location(Bukkit.getWorld("BedWars"), -125, 130, -125);
					Location loc2 = new Location(Bukkit.getWorld("BedWars"), 125, 80, 125);
					new Schematics().replaceBwBlocks(loc1, loc2);
					for(String user : main.config.getConfigurationSection("").getKeys(false)){
						main.config.set(user + ".inv.bw-ec", null);
					}
				
				}else if(args[0].equalsIgnoreCase("/bedwarsup")){
					e.setCancelled(true);
					
					Villager villager = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
					villager.setCollidable(false);
					villager.setCustomNameVisible(true);
					villager.setCustomName("§cAMELIORATIONS");
					villager.setAI(false);
					villager.setSilent(true);
					villager.setProfession(Profession.BUTCHER);
					
				}else if(args[0].equalsIgnoreCase("/bedwarssh")){
					e.setCancelled(true);
					
					Villager villager = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
					villager.setCollidable(false);
					villager.setCustomNameVisible(true);
					villager.setCustomName("§cBOUTIQUE");
					villager.setAI(false);
					villager.setSilent(true);
					villager.setVelocity(p.getVelocity());
					villager.setProfession(Profession.LIBRARIAN);
					
				}else if(args[0].equalsIgnoreCase("/bedwarsrm")){
					e.setCancelled(true);
					
					for (Entity entity : p.getLocation().getChunk().getEntities()){
				    	if(entity instanceof Villager){
				    		entity.remove();
				    	}
				    }
					
				}else if(args[0].equalsIgnoreCase("/rename")){
					e.setCancelled(true);
					
					ItemMeta itemMeta = p.getInventory().getItemInMainHand().getItemMeta();
					itemMeta.setDisplayName(args[1].replace("&", "§"));
					p.getInventory().getItemInMainHand().setItemMeta(itemMeta);
					
				}else if(args[0].equalsIgnoreCase("/discordweek")){
					e.setCancelled(true);
					
					new Roles().onWeek();
					
				}
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////// GLOGIN ///////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(args[0].equalsIgnoreCase("/l") || args[0].equalsIgnoreCase("/login") || args[0].equalsIgnoreCase("/reg") || args[0].equalsIgnoreCase("/register")){
		
		}else{
			e.setCancelled(true);
		}
	}
}

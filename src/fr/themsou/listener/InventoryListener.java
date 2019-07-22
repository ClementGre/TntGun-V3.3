package fr.themsou.listener;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.themsou.BedWars.getteam;
import fr.themsou.BedWars.join;
import fr.themsou.BedWars.menu;
import fr.themsou.TntWars.RunParty;
import fr.themsou.admin.AdminInv;
import fr.themsou.admin.Punish;
import fr.themsou.commands.GradeCmd;
import fr.themsou.diffusion.api.roles;
import fr.themsou.inv.VipInv;
import fr.themsou.main.main;
import fr.themsou.methodes.PInfos;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.Spawns;
import fr.themsou.rp.inv.ShopPts;
import fr.themsou.rp.inv.cmd;
import fr.themsou.rp.inv.crafts;
import fr.themsou.rp.inv.metier;
import fr.themsou.rp.inv.shop;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class InventoryListener implements Listener {
	
	public main pl;
	
	public InventoryListener(main pl) {
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCliqueInventaire(InventoryClickEvent e){
		
		Player  p = (Player) e.getWhoClicked();
		String titre = e.getView().getTitle();
		
		if(e.getCurrentItem() != null){
			
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// SELF ////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
		
			if(e.getInventory().getType() == InventoryType.PLAYER){
				
				if(p.getWorld() == Bukkit.getWorld("Bedwars")){
					if(e.getSlotType() == SlotType.ARMOR){
						e.setCancelled(true);
					}
				}
				
				
			}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// HUB /////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(titre.equalsIgnoreCase("§4MODES DE JEUX")){
				e.setCancelled(true);
			
				if(e.getCurrentItem().getType() == Material.BRICK){
				
					main.config.set(p.getName() + ".rp.lastday", new realDate().getRealDate().getDate());
					if(main.config.contains(p.getName() + ".discord")){
						roles Croles = new roles();
						Croles.addRole("RolePlay Player", main.config.getString(p.getName() + ".discord"));
					}
					
					p.sendMessage("§6Bienvenue en RolePlay, ici, vous devrez trouver des ressources pour ensuite les vendre dans le menu principal (/?) ou les donner à votre patron pour les vendre via votre entreprise (/ent). Vous pourrez ensuite acheter des claims en cliquant sur les pancartes [Acheter] des spawn. Vous avez aussi comme défi d'arriver à 100% dans chacune des compétences afin de débloquer des outils spéciaux qui vous seront nécessaires.");
					
					if(main.TntWarsFillea.contains(p)){
						main.TntWarsFillea.remove(p);
						p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
						System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
					}
					if(main.TntWarsFilleb.contains(p)){
						main.TntWarsFilleb.remove(p);
						p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
						System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
					}
					if(main.TntWarsFille.contains(p)){
						main.TntWarsFille.remove(p);
						p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
						System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
					}
					
					if(new getteam().getplayerteam(p) != 0) new join().leaveBedWars(p);
					
					p.closeInventory();
					
					p.setGameMode(GameMode.SURVIVAL);
					
					
					Spawns CSpawns = new Spawns();
					p.teleport(CSpawns.getSpawnSpawn(CSpawns.getSpawns().split("/")[new Random().nextInt(CSpawns.getSpawns().split("/").length)]));
					p.sendMessage("§bTéléportation à un spawn §3aléatoire");
					p.sendMessage("§6Vous pouvez aussi vous téléporter aux autres villes avec §c/spawn <" + new Spawns().getSpawns() + ">");
					title.sendTitle(p, "§bBienvenue en§3 Role Play", "§6Faites /? pour ouvrir le menu", 60);
					
					RunParty CRunParty = new RunParty();
					CRunParty.Playerquit(p);
					
					p.setBedSpawnLocation(p.getLocation());
				
				}if(e.getCurrentItem().getType() == Material.TNT){
				
					
					p.closeInventory();
					if(new GradeCmd().getPlayerPermition(p.getName()) >= 2){
						
						p.openInventory(main.TntWars);
						
					}else{
						
						if(!main.TntWarsCurrent.contains(p)){
							if(!main.TntWarsFille.contains(p) && !main.TntWarsFillea.contains(p) && !main.TntWarsFilleb.contains(p)){
								main.TntWarsFille.add(p);
								System.out.println(p.getName() + " rejoint la fille d'attente du TntWars - r");
								p.sendMessage("§6Vous venez de rejoindre la fille d'attente du §eTntWars");
							}else p.sendMessage("§cVous etes déja dans la fille d'atente");
						}else p.sendMessage("§cVous etes déja dans une partie");
					}
					
					if(new getteam().getplayerteam(p) != 0) new join().leaveBedWars(p);
					
				}if(e.getCurrentItem().getType() == Material.WHITE_BED){
					
					
					
					if(main.TntWarsFillea.contains(p)){
						main.TntWarsFillea.remove(p);
						p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
						System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
					}
					if(main.TntWarsFilleb.contains(p)){
						main.TntWarsFilleb.remove(p);
						p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
						System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
					}
					if(main.TntWarsFille.contains(p)){
						main.TntWarsFille.remove(p);
						p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
						System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
					}
					
					RunParty CRunParty = new RunParty();
					CRunParty.Playerquit(p);
					
					
					getteam CGetteam = new getteam();
					join Cjoin = new join();
					
					if(CGetteam.getplayerteam(p) == 0 || main.config.getInt("bedwars.list.status") == 0 || main.config.getInt("bedwars.list.status") == 5){
						
						p.openInventory(new menu().getTeamsInventory(p));
						
					}else Cjoin.joinBedWars(p, pl);
					
					
					
					
				}
			}
			
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// BEDWARS /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(titre.equalsIgnoreCase("§4BedWars - Menu")){
				
				e.setCancelled(true);
				p.closeInventory();
				
				if(e.getCurrentItem().getType() == Material.COMPARATOR){
					
					p.closeInventory();
					p.openInventory(main.LapMenuCmd);
					
				}else if(e.getCurrentItem().getType() == Material.DIAMOND){
					
					p.closeInventory();
					new VipInv().openVipInventory(p);
					
				}
				
				
				
				
			}else if(titre.equalsIgnoreCase("§4CHOIX DE L'ÉQUIPE")){
					
				
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() == Material.RED_WOOL || e.getCurrentItem().getType() == Material.BLUE_WOOL || e.getCurrentItem().getType() == Material.GREEN_WOOL || e.getCurrentItem().getType() == Material.YELLOW_WOOL){
					
					int team = e.getSlot() - 19;
					
					new join().joinFirstBedWars(p, pl, team);
					
					e.getInventory().setContents(new menu().getTeamsInventory(p).getContents());
					
					
				}else if(e.getCurrentItem().getType() == Material.SKELETON_SKULL){
					
					new join().joinFirstBedWars(p, pl, 0);
					
				}
				
				
					
			}else if(titre.equalsIgnoreCase("§cAMELIORATIONS")){
				
				
				e.setCancelled(true);
					
				if(e.getCurrentItem().getItemMeta() != null){
					
					String name = e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", "");
					
					if(main.configuration.contains("bedwars.upgrades." + name)){
						
						getteam Cgetteam = new getteam();
						int team = Cgetteam.getplayerteam(p);
						String[] levels = main.configuration.getConfigurationSection("bedwars.upgrades." + name + ".levels").getKeys(false).toString().replace("[", "").replace("]", "").split(", ");
						int actLevel = main.config.getInt("bedwars.list.teams." + team + ".upgrades." + name);
	
						if(actLevel < levels.length){
							
							int diamond = main.configuration.getInt("bedwars.upgrades." + name + ".levels." + levels[actLevel]);
							if(p.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND, 1), diamond)){
								p.getInventory().removeItem(new ItemStack(Material.DIAMOND, diamond));
								for(Player players : Bukkit.getWorld("BedWars").getPlayers()){
									if(Cgetteam.getplayerteam(players) == team){
										players.sendMessage("§c" + p.getName() + " §6vient d'acheter l'amélioration §c" + levels[actLevel]);
									}
								}
								main.config.set("bedwars.list.teams." + team + ".upgrades." + name, actLevel + 1);
								p.closeInventory();
								p.openInventory(new menu().getUpgradesInventory(p));
								
							}else{
								p.closeInventory();
								p.sendMessage("§cVous devez avoir §6" + diamond + " §Cdiamant");
							}
							
						}else p.sendMessage("§cVous avez déjà amélioré cette compétence au maximumm !");
						
					}
				}
					
			}else if(titre.equalsIgnoreCase("§cBOUTIQUE")){
				
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() != Material.WHITE_STAINED_GLASS_PANE || e.getCurrentItem().getType() != Material.RED_STAINED_GLASS_PANE || e.getCurrentItem().getType() != Material.BLUE_STAINED_GLASS_PANE || e.getCurrentItem().getType() != Material.GREEN_STAINED_GLASS_PANE || e.getCurrentItem().getType() != Material.YELLOW_STAINED_GLASS_PANE){
					
					String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
					
					if(itemName.contains("§6")){
						
						int tab = e.getSlot() - 1;
						
						e.getInventory().setContents(new menu().getShopInventory(p, tab).getContents());
						
					}else{
						
						ItemStack item = e.getCurrentItem();
						
						int price = Integer.parseInt(item.getItemMeta().getLore().get(0).split(" ")[0].replace("§7", "").replace("§6", "").replace("§2", ""));
						String moneyName = item.getItemMeta().getLore().get(0).split(" ")[1];
						String moneyColor = item.getItemMeta().getLore().get(0).split(" ")[0].replace(price + "", "");
						Material moneyMaterial = Material.IRON_INGOT;
								
						if(moneyName.equalsIgnoreCase("Or")) moneyMaterial = Material.GOLD_INGOT;
						else if(moneyName.equalsIgnoreCase("Emeraude")) moneyMaterial = Material.EMERALD;
						
						if(p.getInventory().containsAtLeast(new ItemStack(moneyMaterial), price)){
							
							if(item.getType() == Material.STONE_SWORD || item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD){
								if(p.getInventory().contains(Material.WOODEN_SWORD)){
									p.getInventory().remove(Material.WOODEN_SWORD);
								}
							}
							
							if(item.getType() != Material.CHAINMAIL_CHESTPLATE && item.getType() != Material.IRON_CHESTPLATE && item.getType() != Material.DIAMOND_CHESTPLATE){
								
								p.getInventory().removeItem(new ItemStack(moneyMaterial, price));
								ItemStack itemToGive = item.clone();
								ItemMeta itemToGiveM = itemToGive.getItemMeta();
								itemToGiveM.setLore(null);
								itemToGive.setItemMeta(itemToGiveM);
								p.getInventory().addItem(itemToGive);
								p.sendMessage("§6Vous venez d'acheter §c" + item.getAmount() + " " + itemName.replace("§r", "§c"));
								
							}else{
								int team = new getteam().getplayerteam(p);
								if(item.getType() == Material.CHAINMAIL_CHESTPLATE){
									if(main.config.getInt("bedwars.list.teams." + team + ".armor." + p.getName()) <= 0){
										p.getInventory().removeItem(new ItemStack(moneyMaterial, price));
										main.config.set("bedwars.list.teams." + team + ".armor." + p.getName(), 1);
										p.sendMessage("§6Vous venez d'acheter §c" + item.getAmount() + " " + itemName.replace("§r", "§c"));
									}
									else p.sendMessage("§cVous avez déjà acheté ce niveau d'armure !");
								}
								else if(item.getType() == Material.IRON_CHESTPLATE){
									if(main.config.getInt("bedwars.list.teams." + team + ".armor." + p.getName()) <= 1){
										p.getInventory().removeItem(new ItemStack(moneyMaterial, price));
										main.config.set("bedwars.list.teams." + team + ".armor." + p.getName(), 2);
										p.sendMessage("§6Vous venez d'acheter §c" + item.getAmount() + " " + itemName.replace("§r", "§c"));
									}
									else p.sendMessage("§cVous avez déjà acheté ce niveau d'armure !");
								}
								else if(item.getType() == Material.DIAMOND_CHESTPLATE){
									if(main.config.getInt("bedwars.list.teams." + team + ".armor." + p.getName()) <= 2){
										p.getInventory().removeItem(new ItemStack(moneyMaterial, price));
										main.config.set("bedwars.list.teams." + team + ".armor." + p.getName(), 3);
										p.sendMessage("§6Vous venez d'acheter §c" + item.getAmount() + " " + itemName.replace("§r", "§c"));
									}
									else p.sendMessage("§cVous avez déjà acheté ce niveau d'armure !");
										
									
								}
							}
							
						}else p.sendMessage("§cVous devez avoir " + moneyColor + price + " " + moneyName);
						
						
					}
					
				}
			}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// TNTWARS /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(titre.equalsIgnoreCase("§4TntWars - Séléction de map §eVIP")){
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() == Material.GRASS){

					
					if(!main.TntWarsCurrent.contains(p)){
						if(!main.TntWarsFille.contains(p) && !main.TntWarsFillea.contains(p) && !main.TntWarsFilleb.contains(p)){
							
							main.TntWarsFillea.add(p);
							System.out.println(p.getName() + " rejoint la fille d'attente du TntWars- a");
							p.sendMessage("§6Vous venez de rejoindre la fille d'attente du §eTntWars");
							p.closeInventory();
							
						}else p.sendMessage("§cVous etes déja dans la fille d'atente");
					}else p.sendMessage("§cVous etes déja dans une partie");
					
				}else if(e.getCurrentItem().getType() == Material.SAND){

					
					if(!main.TntWarsCurrent.contains(p)){
						if(!main.TntWarsFille.contains(p) && !main.TntWarsFillea.contains(p) && !main.TntWarsFilleb.contains(p)){
							
							main.TntWarsFilleb.add(p);
							System.out.println(p.getName() + " rejoint la fille d'attente du TntWars - b");
							p.sendMessage("§6Vous venez de rejoindre la fille d'attente du §eTntWars");
							p.closeInventory();
							
						}else p.sendMessage("§cVous etes déja dans la fille d'atente");
					}else p.sendMessage("§cVous etes déja dans une partie");
				}else if(e.getCurrentItem().getType() == Material.NETHER_WART_BLOCK){

					if(!main.TntWarsCurrent.contains(p)){
						if(!main.TntWarsFille.contains(p) && !main.TntWarsFillea.contains(p) && !main.TntWarsFilleb.contains(p)){
							
							main.TntWarsFille.add(p);
							System.out.println(p.getName() + " rejoint la fille d'attente du TntWars - r");
							p.sendMessage("§6Vous venez de rejoindre la fille d'attente du §eTntWars");
							p.closeInventory();
							
						}else p.sendMessage("§cVous etes déja dans la fille d'atente");
					}else p.sendMessage("§cVous etes déja dans une partie");
				}
			}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// COMMANDES ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(titre.equalsIgnoreCase("§4COMMANDES")){
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() == Material.PLAYER_HEAD){
					
					p.closeInventory();
					p.sendMessage("§6COMMANDES >§b Voici les commandes sur §3les téléportations");
					p.sendMessage("§c/spawn [" + new Spawns().getSpawns() + "] §b> §3Se 'tp' aux différentes villes");
					p.sendMessage("§c/hub §bou §c/lobby §b> §3Se rendre au hub (choix du mode de jeux)");
					p.sendMessage("§c/home <nom> §b> §3Aller à un de ces 'homes' définis auparavent");
					p.sendMessage("§c/sethome <nom> §b> §3Définir un 'home' §4\u21e7");
					p.sendMessage("§c/delhome <nom> §b> §3Supprimer un de ces homes");
					p.sendMessage("§c/tpa <Joueur> §b> §3Se 'tp' à un autre joueur");
					p.sendMessage("§c/tphere <Joueur> §b> §3'tp' un joueur a soi");
					
				}else if(e.getCurrentItem().getType() == Material.BOOK){
						
					p.closeInventory();
					p.sendMessage("§6COMMANDES >§b Voici les commandes sur §3les discutions");
					p.sendMessage("§c/msg §bou §c/m <joueur> §b> §3Envoyer un message privé");
					p.sendMessage("§c/r <joueur> §b> §3Répondre à un msg privé");
					p.sendMessage("§c/discord <pseudo> §b> §3Lier votre compte Minecraft et Discord");
					
					
				}else if(e.getCurrentItem().getType() == Material.EMERALD){
					
					p.closeInventory();
					p.sendMessage("§6COMMANDES >§b Voici les commandes sur §3l'argent");
					p.sendMessage("§c/money §b> §3Voir son argent");
					p.sendMessage("§c/pay <joueur>/ent:<entrprise> <argent> §b> §3Envoyer de l'argent");
					p.sendMessage("§c/ent §b> §3Gérer son entreprise");
					
					
				}else if(e.getCurrentItem().getType() == Material.SHULKER_SHELL){
					
					p.performCommand("claim");
					p.closeInventory();
					
				}else if(e.getCurrentItem().getType() == Material.NETHER_STAR){
					
					p.performCommand("ent");
					p.closeInventory();
					
				}else if(e.getCurrentItem().getType() == Material.ARROW){
				
					
					p.closeInventory();
					if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
						p.openInventory(main.rpmenu);
					}else{
						p.openInventory(main.Lapmenu);
					}
					
					
				}
				
				
			}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// VIP /////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////		
			else if(titre.equalsIgnoreCase("§4VIP")){
				e.setCancelled(true);
				if(e.getCurrentItem().getType() == Material.ARROW){
					
					p.closeInventory();
					if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
						p.openInventory(main.rpmenu);
					}else if(p.getWorld() == Bukkit.getWorld("BedWars") || p.getWorld() == Bukkit.getWorld("BedWars")){
						p.openInventory(main.Lapmenu);
					}
					
				}
				
				if(e.getCurrentItem().getType() == Material.EMERALD){
					
					p.closeInventory();
					
					TextComponent m = new TextComponent( "§6VIP >§b Envoyez-nous un e-mail à tntgun@free.fr" );
					m.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3tntgun@free.fr").create()));
					p.spigot().sendMessage(m);
					
					TextComponent m2 = new TextComponent( "§6VIP >§b Ou contactez nous sur discord" );
					m2.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3Cliquez pour ouvrir le lien").create()));
					m2.setClickEvent( new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/EyPmvuZ"));
					p.spigot().sendMessage(m2);
					
				}if(e.getCurrentItem().getType() == Material.BOOK){
						
					p.closeInventory();
					
					p.sendMessage("§6VIP >§b LISTE DES COMMANDES VIP :");
					p.sendMessage("§b  ROLEPLAY :");
					p.sendMessage("§c    /ec §b> §3Ouvrir son enderchest");
					p.sendMessage("§c    /craft §b> §3Ouvrir une table de craft virtuelle");
					p.sendMessage("§c    /tete <pseudo> §b> §3Obtenir la tete d'un joueur");
					p.sendMessage("§c    /feed §b> §3Se nourrir");
					p.sendMessage("§c    /night §b> §3Désactiver ou activer la vision nocturne");
					p.sendMessage("§b  TNTWARS :");
					p.sendMessage("§3    Choix de la map");
					p.sendMessage("§b  BedWARS :");
					p.sendMessage("§c    /ec §b> §3Ouvrir son enderchest");
					p.sendMessage("§b  SERVEUR :");
					p.sendMessage("§c    /nick §b> §3Changer son pseudo");
					
					
				}if(e.getCurrentItem().getType() == Material.GRASS){
					
					p.closeInventory();
					
					if(!PInfos.getGame(p).equalsIgnoreCase("RP")){
						
						if(main.config.getInt(p.getName() + ".points") >= 20000){
							if(new GradeCmd().getPlayerPermition(p.getName()) == 1){
								
								main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 20000);
								p.sendMessage("§bVous venez de dépencer §620 000 pts§b pour un garde §3VIP");
								new GradeCmd().changePlayerGradeWithConsole(p.getName(), "Vip");
								
							}else p.sendMessage("§cVous Avez déja un grade");
						}else p.sendMessage("§cVous n'avez pas assez de points");
						
					}else new ShopPts().openInventory(p);
				}
				
			}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// RP //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
				
			else if(titre.equalsIgnoreCase("§4SHOP DE POINTS")){
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() == Material.DIAMOND){
					
					p.closeInventory();
					if(main.config.getInt(p.getName() + ".points") >= 20000){
						if(new GradeCmd().getPlayerPermition(p.getName()) == 1){
							
							main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 20000);
							p.sendMessage("§bVous venez de dépencer §620 000 pts§b pour un garde §3VIP");
							new GradeCmd().changePlayerGradeWithConsole(p.getName(), "Vip");
							
						}else p.sendMessage("§cVous Avez déja un grade");
					}else p.sendMessage("§cVous n'avez pas assez de points");
					
				}else if(e.getCurrentItem().getType() == Material.EMERALD){
					
					p.closeInventory();
					if(main.config.getInt(p.getName() + ".points") >= 2000){
							
						main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 2000);
						p.sendMessage("§bVous venez de dépencer §62 000 pts§b pour §31 000€");
						main.economy.depositPlayer(p, 1000);
							
					}else p.sendMessage("§cVous n'avez pas assez de points");
					
				}else if(e.getCurrentItem().getType() == Material.DIAMOND_CHESTPLATE){
					
					p.closeInventory();
					if(main.config.getInt(p.getName() + ".points") >= 8000){
							
						main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 8000);
						p.sendMessage("§bVous venez de dépencer §68 000 pts§b pour un §3Stuff Full P4U3");
						ItemStack a = new ItemStack(Material.DIAMOND_HELMET, 1); 
						a.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
						a.addEnchantment(Enchantment.DURABILITY, 3);
						p.getInventory().addItem(a); a.setType(Material.DIAMOND_CHESTPLATE);
						p.getInventory().addItem(a); a.setType(Material.DIAMOND_LEGGINGS);
						p.getInventory().addItem(a); a.setType(Material.DIAMOND_BOOTS);
						p.getInventory().addItem(a);
						
					}else p.sendMessage("§cVous n'avez pas assez de points");
					
				}else if(e.getCurrentItem().getType() == Material.ELYTRA){
					
					p.closeInventory();
					if(main.config.getInt(p.getName() + ".points") >= 10000){
							
						main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 10000);
						p.sendMessage("§bVous venez de dépencer §610 000 pts§b pour des §3Elytra");
						p.getInventory().addItem(new ItemStack(Material.ELYTRA, 1));
						
					}else p.sendMessage("§cVous n'avez pas assez de points");
					
				}else if(e.getCurrentItem().getType() == Material.TOTEM_OF_UNDYING){
					
					p.closeInventory();
					if(main.config.getInt(p.getName() + ".points") >= 5000){
							
						main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 5000);
						p.sendMessage("§bVous venez de dépencer §65 000 pts§b pour un §3Totem d'immortalité");
						p.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
							
					}else p.sendMessage("§cVous n'avez pas assez de points");
					
				}else if(e.getCurrentItem().getType() == Material.ARROW){
				
					p.closeInventory();
					if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
						p.openInventory(main.rpmenu);
					}else{
						p.openInventory(main.Lapmenu);
					}
					
					
				}
				
			}
				
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// RP //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////			
				
			else if(titre.equalsIgnoreCase("§4MENU")){
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() == Material.COMMAND_BLOCK){
					p.closeInventory();
					if(p.isOp()){
						new AdminInv().oppenadmininventory(p);
					}else{
						p.sendMessage("§cVous n'êtes pas administrateur");
					}
				}else if(e.getCurrentItem().getType() == Material.COMPARATOR){
					p.closeInventory();
					new cmd().openCmdInventory(p);
				}else if(e.getCurrentItem().getType() == Material.NETHER_STAR){
					p.closeInventory();
					new ShopPts().openInventory(p);
				}else if(e.getCurrentItem().getType() == Material.EMERALD){
					p.closeInventory();
					new shop().oppenshopinventory(p);
				}else if(e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE){
					p.closeInventory();
					metier Cmetier = new metier();
					Cmetier.openMetierInv(p);
				}else if(e.getCurrentItem().getType() == Material.DIAMOND){
					p.closeInventory();
					new VipInv().openVipInventory(p);
					
				}
				
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// CRAFTS //////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			
			}else if(titre.equals("§4MINEUR - CRAFTS") || titre.equals("§4BUCHERON - CRAFTS") || titre.equals("§4FERMIER - CRAFTS") || titre.equals("§4CHASSEUR - CRAFTS") || titre.equals("§4ENCHANTEUR - CRAFTS")){
				e.setCancelled(true);
				if(e.getCurrentItem().getType() == Material.ARROW){
					metier Cmetier = new metier();
					Cmetier.openMetierInv(p);
				}
					
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// ESPACE ADMIN ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
				
			}else if(titre.contains("§cADMIN - §6")){
				e.setCancelled(true);
				
				if(e.getCurrentItem() != null){
					if(e.getCurrentItem().getItemMeta() != null){
						if(e.getCurrentItem().getItemMeta().getDisplayName() != null){
							if(e.getCurrentItem().getItemMeta().getLore() != null){
								if(e.getCurrentItem().getItemMeta().getLore().get(1) != null){
									
									String playerName = e.getCurrentItem().getItemMeta().getDisplayName().replace("§3§l", "");
									new AdminInv().openPlayerInventory(playerName, p);
									
								}
							}
						}
					}
				}
				
				
				
			}else if(titre.contains("§cADMIN - Joueur §l§c")){
				e.setCancelled(true);
				
					if(e.getCurrentItem() != null){
						
						String playerName = titre.replace("§cADMIN - Joueur §l§c", "");
						
						
						if(e.getCurrentItem().getType() == Material.MAGMA_CREAM){
							
							new AdminInv().openPunishInventory(playerName, p);
							
						}else if(e.getCurrentItem().getType() == Material.NETHER_STAR){
							
							Player player = Bukkit.getPlayerExact(playerName);
							
							if(player != null){
								
								title.sendTitle(player, "§cVotre comportement est inadapté !", "§6Vous risquez un ban...", 60);
								
								p.sendMessage("§c" + playerName + "§6 a été avertis");
								p.closeInventory();
							
							}else{
								p.sendMessage("§c" + playerName + "§6 n'est pas connecté");
								p.closeInventory();
							}
						}
						
					}
					
			}else if(titre.contains("§4ADMIN - Sanctioner §l§c")){
				e.setCancelled(true);
				
				if(e.getCurrentItem() != null){
					if(e.getCurrentItem().getItemMeta() != null){
						if(e.getCurrentItem().getItemMeta().getDisplayName() != null){
							
							Punish CPunish = new Punish();
							String playerName = titre.replace("§4ADMIN - Sanctioner §l§c", "");
							String punishName = e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", "");
							List<String> lore = e.getCurrentItem().getItemMeta().getLore();
							
							CPunish.punishPlayerProgress(playerName, p.getName(), punishName, lore);
							
							
						}
					}
				}
				
				
				
				
				
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// COMPÉTENCES /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
				
			}else if(titre.equalsIgnoreCase("§4COMPÉTENCES")){
				
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE){
					if(main.config.getDouble(p.getName() + ".metier.mineur") >= 50.0){
						new crafts().openInventoryToMineur(p);
					}else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
				}else if(e.getCurrentItem().getType() == Material.DIAMOND_AXE){
					if(main.config.getDouble(p.getName() + ".metier.bucheron") >= 50.0){
						new crafts().openInventoryToBucheron(p);
					}else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
				}else if(e.getCurrentItem().getType() == Material.DIAMOND_HOE){
					if(main.config.getDouble(p.getName() + ".metier.fermier") >= 50.0){
						new crafts().openInventoryToFermier(p);
					}else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
				}else if(e.getCurrentItem().getType() == Material.DIAMOND_SWORD){
					if(main.config.getDouble(p.getName() + ".metier.chasseur") >= 50.0){
						new crafts().openInventoryToChasseur(p);
					}else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
				}else if(e.getCurrentItem().getType() == Material.ENCHANTING_TABLE){
					if(main.config.getDouble(p.getName() + ".metier.enchanteur") >= 50.0){
						new crafts().openInventoryToEnchanteur(p);
					}else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
					
				}else if(e.getCurrentItem().getType() == Material.ARROW){
					p.openInventory(main.rpmenu);
				}
				
			
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// SHOP ////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
				
				
			}else if(titre.equalsIgnoreCase("§4SHOP")){
				e.setCancelled(true);
				
				
				if(e.getCurrentItem().getType() != Material.AIR){
					if(p.getGameMode() == GameMode.SURVIVAL){
						if(e.getCurrentItem().hasItemMeta()){
							if(e.getCurrentItem().getItemMeta().hasLore()){
								if(e.getCurrentItem().getItemMeta().getLore().get(0).equals("RETOUR")){
									p.openInventory(main.rpmenu);
								}
								
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// ACHAT X1 ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
								
								else if(e.getAction() == InventoryAction.PICKUP_ALL){

									double priceTo64 = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(4));
									
									BigDecimal priceTo1 = new BigDecimal(priceTo64 / 64);
									priceTo1 = priceTo1.setScale(1, BigDecimal.ROUND_DOWN);

									String name = e.getCurrentItem().getType() + "";
									ItemStack item = new ItemStack(e.getCurrentItem().getType() , 1, e.getCurrentItem().getData().getData());
									item.setData(e.getCurrentItem().getData());

									if(main.economy.getBalance(p) >= priceTo1.doubleValue()){

										main.economy.withdrawPlayer(p.getName(), priceTo1.doubleValue());
										p.sendMessage("§bVous venez d'acheter§3 1 " + name + "§b pour §3" + priceTo1.doubleValue());
										p.getInventory().addItem(item);

									}else p.sendMessage("§cVous devez avoir §4" + priceTo1.doubleValue() + " € §c pour pouvoir acheter§4 1 " + name);
								}
								
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// ACHAT X64 ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

								else if(e.getAction() == InventoryAction.PICKUP_HALF){
									
									double priceTo64 = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(4));
									
									String name = e.getCurrentItem().getType() + "";
									ItemStack item = new ItemStack(e.getCurrentItem().getType() , 64, e.getCurrentItem().getData().getData());
									item.setData(e.getCurrentItem().getData());

									if(main.economy.getBalance(p) >= priceTo64){

										main.economy.withdrawPlayer(p.getName(), priceTo64);
										p.sendMessage("§bVous venez d'acheter§3 64 " + name + "§b pour §3" + priceTo64);
										p.getInventory().addItem(item);

									}else p.sendMessage("§cVous devez avoir §4" + priceTo64 + " € §c pour pouvoir acheter§4 1 " + name);

								}
							
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// VENTE ///////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

							}
						}else{
							
							String name = e.getCurrentItem().getType() + "";
							ItemStack item = e.getCurrentItem();
							
							if(main.config.contains("shop.list." + item.getType().toString())){
								
								double priceTo64 = main.config.getDouble("shop.list." + item.getType().toString());
								
								BigDecimal priceTo1 = new BigDecimal(priceTo64 / 64);
								priceTo1 = priceTo1.setScale(1, BigDecimal.ROUND_DOWN);
								
								BigDecimal priceToX = new BigDecimal(priceTo1.doubleValue() / 10 * item.getAmount());
								priceToX = priceToX.setScale(1, BigDecimal.ROUND_DOWN);

								main.economy.depositPlayer(p, priceToX.doubleValue());
								p.sendMessage("§bVous venez de vendre §3" + e.getCurrentItem().getAmount() + " " + e.getCurrentItem().getType() +"§b pour §3" + priceToX + " €");
								
								p.getInventory().removeItem(item);

								
							}else p.sendMessage("§cLes §4" + name + "§c ne sont pas à vendre");

						}
					}else p.sendMessage("§cveuillez passer en mode survie");
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// MÉTIERS /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
					
					
					
				}
			}
		}
		
	}
}

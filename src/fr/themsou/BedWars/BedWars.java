package fr.themsou.BedWars;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Bed;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import fr.themsou.main.main;
import fr.themsou.methodes.Schematics;
import fr.themsou.methodes.Scoreboards;
import fr.themsou.nms.title;

@SuppressWarnings({ "unused", "deprecation" })
public class BedWars {
	
	
	
	public void run(main pl){
		
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// LANCEMENT ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
		
		if(main.config.getInt("bedwars.list.status") == 0){
			
			int i1 = 4;
			int count = 0;
			for(i1 = 4; i1 >= 1; i1--){
				
				if(main.config.getString("bedwars.list.teams." + i1 + ".owners") != null){
					if(main.config.getString("bedwars.list.teams." + i1 + ".owners").split(",").length >= 1){
						
						count ++;
						
					}
				}
				
			}
			
			if(count >= 2){
				
				
				main.config.set("bedwars.list.status", 5);
				BossBar bossBar = Bukkit.createBossBar("§6Partie de §cBedWars §6en cours de démarage. (30s)", BarColor.BLUE, BarStyle.SOLID, new BarFlag[0]);
				
				
				new BukkitRunnable(){
		            int progress = 31;
		            @Override
		            public void run(){
						
						progress--;
						
						int i1 = 4;
		    			int count = 0;
		    			for(i1 = 4; i1 >= 1; i1--){
		    				
		    				if(main.config.getString("bedwars.list.teams." + i1 + ".owners") != null){
		    					if(main.config.getString("bedwars.list.teams." + i1 + ".owners").split(",").length >= 1){
		    						count ++;
		    					}
		    				}
		    				
		    			}
		    			if(count <= 1){
		    				main.config.set("bedwars.list.status", 0);
		    				bossBar.removeAll();
		    				this.cancel(); return;
		    			}
						
						if(progress <= 0){
							
							
							
							int i = 4;
							ArrayList<Player> joinPlayers = new ArrayList<>();
							for(i = 4; i >= 1; i--){
								
								if(main.config.getString("bedwars.list.teams." + i + ".owners") != null){
									if(main.config.getString("bedwars.list.teams." + i + ".owners").split(",").length >= 1){
										
										joinPlayers.add(Bukkit.getPlayerExact(main.config.getString("bedwars.list.teams." + i + ".owners").split(",")[0]));
										
										if(main.config.getString("bedwars.list.teams." + i + ".owners").split(",").length == 2){
											
											joinPlayers.add(Bukkit.getPlayerExact(main.config.getString("bedwars.list.teams." + i + ".owners").split(",")[1]));
											
										}
										
									}
								}
								
							}
							
							for(Player players : joinPlayers){
								
								getteam Cgetteam = new getteam();
								int team = Cgetteam.getplayerteam(players);
								
								int x = main.configuration.getInt("bedwars.teams." + team + ".x");
								int z = main.configuration.getInt("bedwars.teams." + team + ".z");
								
								players.setGameMode(GameMode.SURVIVAL);
								System.out.println(players.getName() + " rejoint le BedWars (équipe n°" + team + ")");
								players.teleport(new Location(Bukkit.getWorld("BedWars"), x, 100, z));
								title.sendTitle(players, "§cBienvenue en BedWars !", "§6Protégez votre lit", 60);
								players.getInventory().clear();
								
								
							}
							main.config.set("bedwars.list.status", 1);
							main.config.set("bedwars.list.timer", 600);
							bossBar.removeAll();
							this.cancel(); return;
						}
						
						bossBar.setTitle("§6Partie de §cBedWars §6en cours de démarage. §c(" + progress + "s)");
						bossBar.setProgress(((double) progress) / 30.0);
						
						for(Player players : Bukkit.getOnlinePlayers()){
							bossBar.addPlayer(players);
						}
						
					}
					
				}.runTaskTimer(pl, 20, 20);
				
				
				
				
			}
			
		}
		
////////////////////////////////////////////////////////////////////////////////
//////////////////////////////// STATUS DE GAME ////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
			
		if(Bukkit.getWorld("BedWars").getPlayers().size() >= 1){
			
			getteam Cgetteam = new getteam();
			
			if(main.config.getInt("bedwars.list.status") != 0 && main.config.getInt("bedwars.list.status") != 5){
				
				
				int status = main.config.getInt("bedwars.list.status") + 1;
				int timer = main.config.getInt("bedwars.list.timer") - 1;
				main.config.set("bedwars.list.timer", timer);
				if(timer <= 0){
					main.config.set("bedwars.list.status", status);
					if(status == 5){
						for(Player players : Bukkit.getWorld("BedWars").getPlayers()){
							
							title.sendTitle(players, "§cÉgalité" , "§6+80 points", 100);
							main.config.set(players.getName() + ".points", main.config.getInt(players.getName() + ".points") + 80);
							
							if(players.getGameMode() == GameMode.SPECTATOR) players.setGameMode(GameMode.SURVIVAL);
							
							players.teleport(new Location(Bukkit.getWorld("hub"), 0, 50, 0));
							
						}
						main.config.set("bedwars.list.teams", null);
						for(int i2 = 1; i2 <= 4; i2++){
							
							main.config.set("bedwars.list.teams." + i2 + ".bed", 1);
							
						}
						
						main.config.set("bedwars.list.status", 0);
						new world().deleteFile(new File("plugins/MultiInv/UUIDGroups/BedWars"));
						new world().copyworld("BedWars-save", "BedWars");
					}
					
					if(status == 2){
						main.config.set("bedwars.list.timer", 600);
					}else if(status == 3){
						main.config.set("bedwars.list.timer", 600);
					}else if(status == 4){
						main.config.set("bedwars.list.timer", 600);
					}else{
						main.config.set("bedwars.list.timer", 1800);
					}
					
					
				}
				
////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// SPAWNERS ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////	
				
				if(main.bedWarsEmerald <= 0){
					
					int players = Bukkit.getWorld("BedWars").getPlayers().size();
					if(status == 2){
						main.bedWarsEmerald = 150 / players;
					}else if(status == 3){
						main.bedWarsEmerald = 112 / players;
					}else if(status == 4){
						main.bedWarsEmerald = 75 / players;
					}else if(status == 5){
						main.bedWarsEmerald = 37 / players;
					}
					
					int entityAmount = 0;
					for(Entity entity : new Location(Bukkit.getWorld("BedWars"), 0, 101, 0).getChunk().getEntities()){
						
						if(entity instanceof Item){
							Item item = (Item) entity;
							if(item.getItemStack().getType() == Material.EMERALD){
								entityAmount += item.getItemStack().getAmount();
							}
						}
					}
					
					if(entityAmount <= 8){
						Bukkit.getWorld("BedWars").dropItemNaturally(new Location(Bukkit.getWorld("BedWars"), 0.5, 99, 0.5), new ItemStack(Material.EMERALD, 1));
					}
					
					
				}else main.bedWarsEmerald--;
				
				if(main.bedWarsDiamond <= 0){
					int teams = main.config.getConfigurationSection("bedwars.list.teams").getKeys(false).size();
					if(status == 2){
						main.bedWarsDiamond = 150 / teams;
					}else if(status == 3){
						main.bedWarsDiamond = 100 / teams;
					}else if(status == 4){
						main.bedWarsDiamond = 75 / teams;
					}else if(status == 5){
						main.bedWarsDiamond = 37 / teams;
					}
					
					for(int i = 4; i >= 1; i--){
						
						int x = main.configuration.getInt("bedwars.diams." + i + ".x");
						int z = main.configuration.getInt("bedwars.diams." + i + ".z");
						int entityAmount = 0;
						
						for(Entity entity : new Location(Bukkit.getWorld("BedWars"), x, 101, z).getChunk().getEntities()){
							
							if(entity instanceof Item){
								Item item = (Item) entity;
								if(item.getItemStack().getType() == Material.DIAMOND){
									entityAmount += item.getItemStack().getAmount();
								}
							}
						}
						
						if(entityAmount <= 8){
							Bukkit.getWorld("BedWars").dropItemNaturally(new Location(Bukkit.getWorld("BedWars"), x + 0.5, 101, z + 0.5), new ItemStack(Material.DIAMOND, 1));
						}
						
						
					}
					
				}else main.bedWarsDiamond--;
				
				
				for(int i = 4; i >= 1; i--){
					
					int x = main.configuration.getInt("bedwars.diams." + i + ".x");
					int z = main.configuration.getInt("bedwars.diams." + i + ".z");
					
					for(Entity entity : new Location(Bukkit.getWorld("BedWars"), x, 101, z).getChunk().getEntities()){
				    	if(entity instanceof ArmorStand){
				    		((ArmorStand) entity).setVisible(false);
				    		((ArmorStand) entity).setCustomNameVisible(true);
				    		((ArmorStand) entity).setCustomName("§3Spawner de diamant §c(" + main.bedWarsDiamond + "s)");
				    	}
				    }
					
					
				}
				
				
				for (Entity entity : new Location(Bukkit.getWorld("BedWars"), 0, 99, 0).getChunk().getEntities()){
			    	if(entity instanceof ArmorStand){
			    		((ArmorStand) entity).setVisible(false);
			    		((ArmorStand) entity).setCustomNameVisible(true);
			    		((ArmorStand) entity).setCustomName("§2Spawner d'émeraude §c(" + main.bedWarsEmerald + "s)");
			    	}
			    }
				
				
				
				
				
			}
			
////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// BEDS ET COMPTEUR ///////////////////////////////
////////////////////////////////////////////////////////////////////////////////
				
			
			ArrayList<Integer> rstTeams = new ArrayList<>();
			for(int i = 4; i >= 1; i--){
				
				if(main.config.getString("bedwars.list.teams." + i + ".owners") != null){
					
					String[] owners = main.config.getString("bedwars.list.teams." + i + ".owners").split(",");
					
					if(main.config.getInt("bedwars.list.teams." + i + ".bed") == 1){
							
						int x = main.configuration.getInt("bedwars.teams." + i + ".x");
						int z = main.configuration.getInt("bedwars.teams." + i + ".z");
						int ori = main.configuration.getInt("bedwars.teams." + i + ".ori");
						
						rstTeams.add(i);
						
						BlockFace bedOri = BlockFace.NORTH;
						Location bedLoc1 = new Location(Bukkit.getWorld("BedWars"), x, 100, z);
						Location bedLoc2 = new Location(Bukkit.getWorld("BedWars"), x, 100, z);
						
						if(ori == 1){
							
							bedOri = BlockFace.NORTH;
							bedLoc1.setZ(bedLoc1.getZ() + 9);
							bedLoc2.setZ(bedLoc1.getZ() + 1);
							
						}else if(ori == 2){
							
							bedOri = BlockFace.EAST;
							bedLoc1.setX(bedLoc1.getX() - 9);
							bedLoc2.setX(bedLoc1.getX() - 1);
							
						}else if(ori == 3){
							
							bedOri = BlockFace.SOUTH;
							bedLoc1.setZ(bedLoc1.getZ() - 9);
							bedLoc2.setZ(bedLoc1.getZ() - 1);
							
						}else{
							
							bedOri = BlockFace.WEST;
							bedLoc1.setX(bedLoc1.getX() + 9);
							bedLoc2.setX(bedLoc1.getX() + 1);
							
						}
						
						if(bedLoc1.getBlock().getType() != Material.RED_BED && bedLoc1.getBlock().getType() != Material.BLUE_BED && bedLoc1.getBlock().getType() != Material.GREEN_BED && bedLoc1.getBlock().getType() != Material.YELLOW_BED){
							Block bedHeadBlock = bedLoc1.getBlock();
							Block bedFootBlock = bedLoc2.getBlock();
							
							bedHeadBlock.setType(Material.WHITE_BED);
							bedFootBlock.setType(Material.WHITE_BED);
							
							String dataFootString = "minecraft:" + new getteam().getTeamEnglishColor(i) + "_bed[facing=" + bedOri.toString().toLowerCase() + ",part=foot]";
							BlockData dataFoot = Bukkit.createBlockData(dataFootString);
							bedFootBlock.setBlockData(dataFoot);
							
							String dataHeadString = "minecraft:" + new getteam().getTeamEnglishColor(i) + "_bed[facing=" + bedOri.toString().toLowerCase() + ",part=head]";
							BlockData dataHead = Bukkit.createBlockData(dataHeadString);
							bedHeadBlock.setBlockData(dataHead);
						}
						
						
						
					}else{
						
						if(owners.length >= 1){
							if(Bukkit.getPlayerExact(owners[0]) != null){
								if(Bukkit.getPlayerExact(owners[0]).getWorld() == Bukkit.getWorld("BedWars")){
									if(Bukkit.getPlayerExact(owners[0]).getGameMode() == GameMode.SURVIVAL){
										rstTeams.add(i);
									}
								}
							}
							
						}
						if(owners.length == 2){
							if(Bukkit.getPlayerExact(owners[1]) != null){
								if(Bukkit.getPlayerExact(owners[1]).getWorld() == Bukkit.getWorld("BedWars")){
									if(Bukkit.getPlayerExact(owners[1]).getGameMode() == GameMode.SURVIVAL){
										if(!rstTeams.contains(i)) rstTeams.add(i);
									}
								}
							}
							
						}
						
					}
				}
				
			}

////////////////////////////////////////////////////////////////////////////////
///////////////////////////////// FIN DE GAME //////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
			
			if(rstTeams.size() == 1 && main.config.getInt("bedwars.list.status") != 0 && main.config.getInt("bedwars.list.status") != 5){
				
				
				String teamName = Cgetteam.getTeamChatColor(rstTeams.get(0)) + Cgetteam.getTeamStringColor(rstTeams.get(0));
				for(Player players : Bukkit.getWorld("BedWars").getPlayers()){
					
					if(Cgetteam.getplayerteam(players) == rstTeams.get(0)){
						title.sendTitle(players, "§cLes " + teamName + "§c ont gagnés !" , "§6+100 points", 100);
						main.config.set(players.getName() + ".points", main.config.getInt(players.getName() + ".points") + 100);
					}else{
						title.sendTitle(players, "§cLes " + teamName + "§c ont gagnés !" , "§6+60 points", 60);
						main.config.set(players.getName() + ".points", main.config.getInt(players.getName() + ".points") + 60);
					}
					
					if(players.getGameMode() == GameMode.SPECTATOR) players.setGameMode(GameMode.SURVIVAL);
					players.teleport(new Location(Bukkit.getWorld("hub"), 0, 50, 0));
					
				}
				main.config.set("bedwars.list.teams", null);
				for(int i2 = 1; i2 <= 4; i2++){
					main.config.set("bedwars.list.teams." + i2 + ".bed", 1);
				}
				
				main.config.set("bedwars.list.status", 0);
				
				
				Location loc1 = new Location(Bukkit.getWorld("BedWars"), -125, 125, -125);
				Location loc2 = new Location(Bukkit.getWorld("BedWars"), 125, 85, 125);
				new Schematics().replaceBwBlocks(loc1, loc2);
				for(String user : main.config.getConfigurationSection("").getKeys(false)){
					main.config.set(user + ".inv.bw-ec", null);
				}
				
			}else if(rstTeams.size() == 0 && main.config.getInt("bedwars.list.status") != 0 && main.config.getInt("bedwars.list.status") != 5){
				
				for(Player players : Bukkit.getWorld("BedWars").getPlayers()){
					if(players.getGameMode() == GameMode.SPECTATOR) players.setGameMode(GameMode.SURVIVAL);
					players.teleport(new Location(Bukkit.getWorld("hub"), 0, 50, 0));
					
				}
				
				for(int i2 = 1; i2 <= 4; i2++){
					main.config.set("bedwars.list.teams." + i2, null);
					main.config.set("bedwars.list.teams." + i2 + ".bed", 1);
				}
				
				main.config.set("bedwars.list.status", 0);
				
				Location loc1 = new Location(Bukkit.getWorld("BedWars"), -125, 125, -125);
				Location loc2 = new Location(Bukkit.getWorld("BedWars"), 125, 85, 125);
				new Schematics().replaceBwBlocks(loc1, loc2);
				for(String user : main.config.getConfigurationSection("").getKeys(false)){
					main.config.set(user + ".inv.bw-ec", null);
				}
				
			}else{
			
////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// SPAWNERS D'ILES ////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
				for(int team : rstTeams){
					
					Random r = new Random();
					int x = main.configuration.getInt("bedwars.teams." + team + ".x");
					int z = main.configuration.getInt("bedwars.teams." + team + ".z");
					int ori = main.configuration.getInt("bedwars.teams." + team + ".ori");
					int spawnLevel = main.config.getInt("bedwars.list.teams." + team + ".upgrades.Forge");
					int spawnAmount = 1;
					Location spawnLoc = new Location(Bukkit.getWorld("BedWars"), x + 0.5, 100, z + 0.5);
					
					if(ori == 1){
						spawnLoc.setX(spawnLoc.getX() + 4);
					}else if(ori == 2){
						spawnLoc.setZ(spawnLoc.getZ() + 4);
					}else if(ori == 3){
						spawnLoc.setX(spawnLoc.getX() - 4);
					}else{
						spawnLoc.setZ(spawnLoc.getZ() - 4);
					}
					
					if(spawnLevel == 1){
						
						if(r.nextInt(2) == 0){
							spawnAmount = 2;
						}
						
					}else if(spawnLevel == 2){
						
						spawnAmount = 2;
						
					}else if(spawnLevel == 3){
						
						spawnAmount = 4;
						if(r.nextInt(30) == 0){
							Bukkit.getWorld("BedWars").dropItemNaturally(spawnLoc, new ItemStack(Material.EMERALD, 1));
						}
					}
					
					if(r.nextInt(15) == 0){
						Bukkit.getWorld("BedWars").dropItemNaturally(spawnLoc, new ItemStack(Material.GOLD_INGOT, spawnAmount));
					}
					if(r.nextInt(3) == 0){
						Bukkit.getWorld("BedWars").dropItemNaturally(spawnLoc, new ItemStack(Material.IRON_INGOT, spawnAmount));
					}
					
					
					
					
					
				}
				
			}
			
////////////////////////////////////////////////////////////////////////////////
///////////////////////////// UPGRADES ET ARMURES //////////////////////////////
////////////////////////////////////////////////////////////////////////////////
			
			for(Player players : Bukkit.getWorld("BedWars").getPlayers()){
				
				if(players.getGameMode() == GameMode.SURVIVAL){
					
					int team = Cgetteam.getplayerteam(players);
					
					/*     armure     */
					
					if(players.hasPotionEffect(PotionEffectType.INVISIBILITY)){
						players.getInventory().setArmorContents(null);
					}else{
						
						int armorType = main.config.getInt("bedwars.list.teams." + team + ".armor." + players.getName());
						int armorLevel = main.config.getInt("bedwars.list.teams." + team + ".upgrades.Protection");
						ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
						ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
						
						if(armorType == 1){
							helmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
							chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
						}else if(armorType == 2){
							helmet = new ItemStack(Material.IRON_HELMET, 1);
							chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
						}else if(armorType == 3){
							helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
							chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
						}else{
							LeatherArmorMeta helmetM = (LeatherArmorMeta) helmet.getItemMeta();
						    helmetM.setColor(Cgetteam.getTeamColor(players));
						    helmet.setItemMeta(helmetM);
						    
						    LeatherArmorMeta chestplateM = (LeatherArmorMeta) chestplate.getItemMeta();
						    chestplateM.setColor(Cgetteam.getTeamColor(players));
						    chestplate.setItemMeta(chestplateM);
						}
						
						if(armorLevel != 0){
							helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, armorLevel);
							chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, armorLevel);
						}
						
						
						ItemStack leging = new ItemStack(Material.LEATHER_LEGGINGS, 1);
						ItemStack boot = new ItemStack(Material.LEATHER_BOOTS, 1);
						
						LeatherArmorMeta legingM = (LeatherArmorMeta) leging.getItemMeta();
					    legingM.setColor(Cgetteam.getTeamColor(players));
					    leging.setItemMeta(legingM);
						
					    LeatherArmorMeta bootM = (LeatherArmorMeta) boot.getItemMeta();
					    bootM.setColor(Cgetteam.getTeamColor(players));
					    boot.setItemMeta(bootM);
					    
					    
					    players.getInventory().setHelmet(helmet);
						players.getInventory().setChestplate(chestplate);
						players.getInventory().setBoots(boot);
						players.getInventory().setLeggings(leging);
						
					}
					
					/*     ÉPÉE ET OUTIL     */
					
					int toolLevel = main.config.getInt("bedwars.list.teams." + team + ".upgrades.Efficacité");
					int swordLevel = main.config.getInt("bedwars.list.teams." + team + ".upgrades.Tranchant");
					
					if(toolLevel != 0){
						
						if(players.getInventory().contains(Material.IRON_AXE)) players.getInventory().getItem(players.getInventory().first(Material.IRON_AXE)).addEnchantment(Enchantment.DIG_SPEED, toolLevel);
						
						if(players.getInventory().contains(Material.DIAMOND_AXE)) players.getInventory().getItem(players.getInventory().first(Material.DIAMOND_AXE)).addEnchantment(Enchantment.DIG_SPEED, toolLevel);
							
						if(players.getInventory().contains(Material.IRON_PICKAXE)) players.getInventory().getItem(players.getInventory().first(Material.IRON_PICKAXE)).addEnchantment(Enchantment.DIG_SPEED, toolLevel);
						
						if(players.getInventory().contains(Material.DIAMOND_PICKAXE)) players.getInventory().getItem(players.getInventory().first(Material.DIAMOND_PICKAXE)).addEnchantment(Enchantment.DIG_SPEED, toolLevel);
							
						
					}
					
					if(!players.getInventory().contains(Material.WOODEN_SWORD) && !players.getInventory().contains(Material.IRON_SWORD) && !players.getInventory().contains(Material.DIAMOND_SWORD)){
						
						ItemStack sword = new ItemStack(Material.WOODEN_SWORD, 1);
						
						if(swordLevel != 0){
							sword.addEnchantment(Enchantment.DAMAGE_ALL, swordLevel);
						}
						
						players.getInventory().addItem(sword);
						
					}else{
						
						if(swordLevel != 0){
							if(players.getInventory().contains(Material.WOODEN_SWORD)) players.getInventory().getItem(players.getInventory().first(Material.WOODEN_SWORD)).addEnchantment(Enchantment.DAMAGE_ALL, swordLevel);
							if(players.getInventory().contains(Material.IRON_SWORD)) players.getInventory().getItem(players.getInventory().first(Material.IRON_SWORD)).addEnchantment(Enchantment.DAMAGE_ALL, swordLevel);
							if(players.getInventory().contains(Material.DIAMOND_SWORD)) players.getInventory().getItem(players.getInventory().first(Material.DIAMOND_SWORD)).addEnchantment(Enchantment.DAMAGE_ALL, swordLevel);
						}
						
					}
					
					
				}
				
				new Scoreboards().sendPlayersScoreboard();
				
			}
			
		}
			
			
	}
	
	
	public boolean hasbed(Player p){
		getteam Cgetteam = new getteam();
		return hasbed(Cgetteam.getplayerteam(p));
	}
	
	public boolean hasbed(int team){
		
		if(main.config.getInt("bedwars.list.teams." + team + ".bed") == 1){
			return true;
		}else{
			return false;
		}
		
	}
	
	

}

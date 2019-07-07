package fr.themsou.listener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.themsou.BedWars.getteam;
import fr.themsou.BedWars.menu;
import fr.themsou.inv.VipInv;
import fr.themsou.main.main;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.CanBuild;
import fr.themsou.rp.claim.GetZoneId;
import fr.themsou.rp.claim.SignClick;
import fr.themsou.rp.claim.Spawns;
import fr.themsou.rp.ent.Sign;
import fr.themsou.rp.tools.Tools;

public class interactListener implements Listener {
	
	main pl;
	
	public interactListener(main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onEntityInteract(EntityInteractEvent e){
		
		if(e.getBlock().getType() == Material.FARMLAND){
			e.setCancelled(true);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		
		if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
			
			CanBuild CCanBuild = new CanBuild();
			
			if(e.getAction() == Action.PHYSICAL){
				
				if(!CCanBuild.canBuildMessagewithEmployees(p, p.getLocation())){
					if(e.getPlayer().getLocation().getBlock().getType() == Material.OAK_PRESSURE_PLATE) return;
					if(p.getGameMode() != GameMode.CREATIVE){
						e.setCancelled(true);
					}
				}
			}
			
			if(e.getClickedBlock() != null){
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
					Material material = e.getClickedBlock().getType();
					Location loc = e.getClickedBlock().getLocation();
					
					if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
						
						// CIRCUIT BOURG_PALETTE
						
						if(material == Material.PACKED_ICE && p.getInventory().getItemInMainHand() != null){
							if(loc.getBlockY() == 67){
								if(new GetZoneId().getIdOfPlayerZone(loc) == 0 && new Spawns().isInSpawn(loc)){
									Material mainMaterial = p.getInventory().getItemInMainHand().getType();
									if(mainMaterial == Material.OAK_BOAT || mainMaterial == Material.ACACIA_BOAT || mainMaterial == Material.BIRCH_BOAT || mainMaterial == Material.DARK_OAK_BOAT || mainMaterial == Material.JUNGLE_BOAT || mainMaterial == Material.SPRUCE_BOAT){
										return;
									}
								}
							}
						}
						
						// MINECART
						if((material == Material.RAIL || material == Material.ACTIVATOR_RAIL || material == Material.POWERED_RAIL || material == Material.DETECTOR_RAIL) && p.getInventory().getItemInMainHand() != null){
							Material mainMaterial = p.getInventory().getItemInMainHand().getType();
							if(mainMaterial == Material.MINECART){
								return;
							}
						}
						
						// DÉCALAGE PORTES
							
						if((!p.isSneaking()) && (!CCanBuild.canBuild(p, loc)) && (material == Material.SPRUCE_DOOR || material == Material.BIRCH_DOOR || material == Material.JUNGLE_DOOR || material == Material.ACACIA_DOOR || material == Material.DARK_OAK_DOOR || material == Material.OAK_DOOR || material == Material.SPRUCE_FENCE_GATE || material == Material.BIRCH_DOOR || material == Material.JUNGLE_FENCE_GATE || material == Material.ACACIA_FENCE_GATE || material == Material.DARK_OAK_DOOR || material == Material.OAK_FENCE_GATE)){
							
							loc.setX(loc.getX() + 1);
							if(CCanBuild.canBuild(p, loc)){}
								
							else{
								
								loc.setX(loc.getX() - 2);
								if(CCanBuild.canBuild(p, loc)){}
									
								else{
									
									loc.setX(loc.getX() + 1);
									loc.setZ(loc.getZ() + 1);
									if(CCanBuild.canBuild(p, loc)){
									}else{
										
										loc.setZ(loc.getZ() - 2);
										if(CCanBuild.canBuild(p, loc)){
										}else{
											loc.setZ(loc.getZ() + 1);
										}
									}
								}
							}
						
						// DÉCALAGE POUR POSER
							
						}else if(!(material == Material.SPRUCE_DOOR || material == Material.BIRCH_DOOR || material == Material.JUNGLE_DOOR || material == Material.ACACIA_DOOR || material == Material.DARK_OAK_DOOR || material == Material.OAK_DOOR || material == Material.SPRUCE_FENCE_GATE || material == Material.BIRCH_DOOR || material == Material.JUNGLE_FENCE_GATE || material == Material.ACACIA_FENCE_GATE || material == Material.DARK_OAK_DOOR || material == Material.OAK_FENCE_GATE)){
							
							if(e.getBlockFace() == BlockFace.DOWN) loc.setY(loc.getY() - 1);
							if(e.getBlockFace() == BlockFace.UP) loc.setY(loc.getY() + 1);
							
							if(e.getBlockFace() == BlockFace.NORTH) loc.setZ(loc.getZ() - 1);
							if(e.getBlockFace() == BlockFace.SOUTH) loc.setZ(loc.getZ() + 1);
							
							if(e.getBlockFace() == BlockFace.EAST) loc.setX(loc.getX() + 1);
							if(e.getBlockFace() == BlockFace.WEST) loc.setX(loc.getX() - 1);
							
						}
					}
					if(CCanBuild.canBuildMessage(p, loc) && e.getAction() == Action.RIGHT_CLICK_BLOCK){
						new Tools().playerInteract(p, e.getClickedBlock().getLocation(), p.getInventory().getItemInMainHand());
					}
					new Tools().defineFace(p, e.getBlockFace());
					
					// CALCUL FINAL
					
					if(!CCanBuild.canBuild(p, loc)){ // Peut pas build
						
						if(e.getClickedBlock().getType() == Material.OAK_BUTTON && e.getAction() == Action.RIGHT_CLICK_BLOCK) return;
						
						if((!p.isSneaking()) && e.getAction() == Action.RIGHT_CLICK_BLOCK && (material == Material.CHEST || material == Material.TRAPPED_CHEST ||
								material == Material.SPRUCE_DOOR || material == Material.BIRCH_DOOR || material == Material.JUNGLE_DOOR || material == Material.ACACIA_DOOR || material == Material.DARK_OAK_DOOR || material == Material.OAK_DOOR ||
								material == Material.SPRUCE_FENCE_GATE || material == Material.BIRCH_DOOR || material == Material.JUNGLE_FENCE_GATE || material == Material.ACACIA_FENCE_GATE || material == Material.DARK_OAK_FENCE_GATE || material == Material.OAK_FENCE_GATE ||
								material == Material.FURNACE || material == Material.ENCHANTING_TABLE || material == Material.ANVIL || material == Material.CRAFTING_TABLE)){
							
							
							if(!CCanBuild.canBuildMessagewithEmployees(p, loc)){
								if(p.getGameMode() != GameMode.CREATIVE){
									e.setCancelled(true);
								}
							}else{
								if(material == Material.CHEST || material == Material.TRAPPED_CHEST){
									Inventory inv = ((Chest) e.getClickedBlock().getState()).getInventory();
									ItemStack[] lastContents = inv.getContents();
									ItemStack[] newContents = new ItemStack[lastContents.length];
									
									int i = 0;
									for(ItemStack item : lastContents){
										if(item != null){
											if(item.getType() != Material.AIR){
												newContents[i] = item.clone();
												i++;
											}
										}
									}
									
									
									main.entLog.put(p.getName(), newContents);
								}
								
							}
							
						}else{
							CCanBuild.canBuildMessage(p, loc);
							if(p.getGameMode() != GameMode.CREATIVE){
								e.setCancelled(true);
							}else{
								
								String debug = CCanBuild.canBuildDebug(p, loc);
								
								
								if(!debug.equals("Spawn")){
									
									Date rd = new realDate().getRealDate();
									String date = "[" + rd.getMonth() + "/" + rd.getDay() + " " + rd.getHours() + ":" + rd.getMinutes() + "] ";
									String block = "";
									if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
										
										if(p.getInventory().getItemInMainHand().getType().toString().equals("AIR")) return;
										
										block = "posé le bloc \"" + p.getInventory().getItemInMainHand().getType().toString();
										
									}else{
										
										block = "cassé le bloc \"" + material.toString();
										
									}
										
									try{
										FileWriter writer = new FileWriter(main.logblock.getAbsoluteFile(), true);
										BufferedWriter out = new BufferedWriter(writer);
										out.write(date + p.getName() + " a " + block + "\" dans le claim de " + debug);
										out.newLine();
										out.close();
									}catch(IOException e1){
										e1.printStackTrace();
									}
									
								}
								
							}
							
						}
					}else{  // Peut 
						if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
							if(p.getInventory().getItemInMainHand() != null){
								
								if(p.getInventory().getItemInMainHand().getType() == Material.OAK_SIGN){
									
									ItemStack item = p.getInventory().getItemInMainHand().clone();
									
									if(item.getItemMeta() != null){
										if(item.getItemMeta().getDisplayName() != null){
											if(item.getItemMeta().getDisplayName().contains("§r§bPancarte de vente (")){
												
												if(e.getBlockFace() == BlockFace.UP){ // normal sign
													new Sign().placeSign(e.getClickedBlock().getRelative(e.getBlockFace()), item, false, getDirection(p));
												}else if(e.getBlockFace() != BlockFace.DOWN){ // Wall sign
													new Sign().placeSign(e.getClickedBlock().getRelative(e.getBlockFace()), item, true, e.getBlockFace());
												}
												e.setCancelled(true);
												
											}
										}
									}
								}
							}
						}
					}
				}
			}
			new SignClick().signclick(e);
		}else if(p.getWorld() == Bukkit.getWorld("BedWars")){
				
			if(e.getClickedBlock() != null){
				if(e.getAction() == Action.LEFT_CLICK_BLOCK){
					if(p.getGameMode() == GameMode.SURVIVAL){
						
						Material material = e.getClickedBlock().getType();
						
						if(material == Material.RED_WOOL || material == Material.BLUE_WOOL || material == Material.GREEN_WOOL || material == Material.YELLOW_WOOL) material = Material.WHITE_WOOL;
						else if(material == Material.RED_CONCRETE || material == Material.BLUE_CONCRETE || material == Material.GREEN_CONCRETE || material == Material.YELLOW_CONCRETE) material = Material.WHITE_CONCRETE;
						
						for(String sectionTab : main.configuration.getConfigurationSection("bedwars.shop").getKeys(false)){
							for(String sectionItem : main.configuration.getConfigurationSection("bedwars.shop." + sectionTab).getKeys(false)){
								if(!sectionItem.equals("item")){
									if(Material.getMaterial(main.configuration.getString("bedwars.shop." + sectionTab + "." + sectionItem + ".item")) == material){
										
										ItemStack item = new ItemStack(material, 1);
										if(item.getType() == Material.WHITE_WOOL) item = new ItemStack(new getteam().getTeamWoolColor(new getteam().getplayerteam(p)), 1);
										else if(item.getType() == Material.WHITE_CONCRETE) item = new ItemStack(new getteam().getTeamConcreteColor(new getteam().getplayerteam(p)), 1);
										ItemMeta itemM = item.getItemMeta();
										itemM.setDisplayName("§r" + sectionItem);
										item.setItemMeta(itemM);
										
										return;
									}
								}
							}
						}
						
						if(material != Material.WHITE_BED && material != Material.RED_BED && material != Material.BLUE_BED && material != Material.GREEN_BED && material != Material.YELLOW_BED){
							title.sendActionBar(p, "§cVOUS NE POUVEZ PAS CASSER CE BLOC !");
							e.setCancelled(true);
						}
						
						
					}
					
				}
				
			}
			if(e.getAction() == Action.RIGHT_CLICK_AIR){
				if(p.getInventory().getItemInMainHand() != null){
					if(p.getInventory().getItemInMainHand().getType() != null){
						if(p.getInventory().getItemInMainHand().getType() == Material.FIRE_CHARGE){
							e.setCancelled(true);
							
							p.launchProjectile(Fireball.class);
							ItemStack fireBall = new ItemStack(Material.FIRE_CHARGE, 1);
							ItemMeta fireBallM = fireBall.getItemMeta();
							fireBallM.setDisplayName("§rFire ball");
							fireBall.setItemMeta(fireBallM);
							p.getInventory().removeItem(fireBall);
							
						}else if(p.getInventory().getItemInMainHand().getType() == Material.EGG){
							e.setCancelled(true);
							
							Egg egg = p.launchProjectile(Egg.class);
							egg.setVelocity(p.getLocation().getDirection().multiply(2.0D));
							Material wool = new getteam().getTeamWoolColor(new getteam().getplayerteam(p));
							
							ItemStack eggItem = new ItemStack(Material.EGG, 1);
							ItemMeta eggItemM = eggItem.getItemMeta();
							eggItemM.setDisplayName("§rCréateur de pont");
							eggItem.setItemMeta(eggItemM);
							p.getInventory().removeItem(eggItem);
							
							BukkitRunnable eggTimer = new BukkitRunnable() {
								@Override
								public void run() {
									if(egg.isDead()){
										this.cancel();
								        return;
								    }
									
									Location loc = egg.getLocation();
									loc.setY(loc.getBlockY() - 2);
									
									if(loc.getBlockY() <= 85 || loc.getBlockY() >= 125
											|| loc.getBlockX() >= 125 || loc.getBlockX() <= -125
											|| loc.getBlockZ() >= 125 || loc.getBlockZ() <= -125){
										this.cancel();
										return;
									}
									
									Location locx1 = loc.clone(); locx1.setX(loc.getBlockX() + 1);
									Location locx2 = loc.clone(); locx2.setX(loc.getBlockX() - 1);
									Location locz1 = loc.clone(); locz1.setZ(loc.getBlockZ() + 1);
									Location locz2 = loc.clone(); locz2.setZ(loc.getBlockZ() - 1);
									
									Location locc1 = locx1.clone(); locc1.setZ(loc.getBlockZ() + 1);
									Location locc2 = locx2.clone(); locc2.setZ(loc.getBlockZ() + 1);
									Location locc3 = locx1.clone(); locc3.setZ(loc.getBlockZ() - 1);
									Location locc4 = locx2.clone(); locc4.setZ(loc.getBlockZ() - 1);
									
									BukkitRunnable blockTimer = new BukkitRunnable() {
										@Override
										public void run() {
											
											if(loc.getBlock().getType() == Material.AIR){
												loc.getBlock().setType(wool);
											}
										    
										    if(locx1.getBlock().getType() == Material.AIR){
										    	locx1.getBlock().setType(wool);
										    }
										    if(locx2.getBlock().getType() == Material.AIR){
										    	locx2.getBlock().setType(wool);
										    }
										    if(locz1.getBlock().getType() == Material.AIR){
										    	locz1.getBlock().setType(wool);
										    }
										    if(locz2.getBlock().getType() == Material.AIR){
										    	locz2.getBlock().setType(wool);
										    }
										    
										    if(locc1.getBlock().getType() == Material.AIR){
										    	locc1.getBlock().setType(wool);
										    }
										    if(locc2.getBlock().getType() == Material.AIR){
										    	locc2.getBlock().setType(wool);
										    }
										    if(locc3.getBlock().getType() == Material.AIR){
										    	locc3.getBlock().setType(wool);
										    }
										    if(locc4.getBlock().getType() == Material.AIR){
										    	locc4.getBlock().setType(wool);
										    }
											
										}
									};
									blockTimer.runTaskLater(pl, 2);
									
								    
								}
							};
							eggTimer.runTaskTimer(this.pl, 1, 1);
							
						}
					}
				}
			}
			
			
		}else if(p.getWorld() == Bukkit.getWorld("hub")){
		
			if(e.getClickedBlock() != null){
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
					if(e.getClickedBlock() != null){
						if(p.getGameMode() == GameMode.SURVIVAL){
							title.sendActionBar(p, "§cVEUILLEZ NE PAS DÉTRUIRE LE SPAWN");
							e.setCancelled(true);
						}else{
							title.sendActionBar(p, "§cVEUILLEZ NE PAS DÉTRUIRE LE SPAWN");
						}
						
						
					}
				}
			}
			if(main.config.getInt(p.getName() + ".status") == 1){
				if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
					if(p.getInventory().getItemInMainHand() != null){
						if(p.getInventory().getItemInMainHand().getItemMeta() != null){
							if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null){
								if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lMENU")){
									p.openInventory(main.menu);
								}else if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lVIP")){
									new VipInv().openVipInventory(p);
								}
							}
						}
					}
				}
			}
			
		}
		
		
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e){
		
		Player p = e.getPlayer();
		if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){

			Location loc = e.getRightClicked().getLocation();
			if((!new CanBuild().canBuild(p, loc))){
				if(e.getRightClicked() instanceof LivingEntity){
					if(new GetZoneId().getIdOfPlayerZone(loc) != 0){
						new CanBuild().canBuildMessage(p, loc);
						if(p.getGameMode() != GameMode.CREATIVE){
							e.setCancelled(true);
						}
					}
				}else if(e.getRightClicked().getType() == EntityType.BOAT || e.getRightClicked().getType() == EntityType.MINECART){
				}else{
					new CanBuild().canBuildMessage(p, loc);
					if(p.getGameMode() != GameMode.CREATIVE){
						e.setCancelled(true);
					}
				}
				
			}
		}else if(p.getWorld() == Bukkit.getWorld("BedWars")){
			
			if(e.getRightClicked() instanceof Villager){
				
				if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.CREATIVE){
					
					if(e.getRightClicked().getCustomName().equals("§cAMELIORATIONS")){
						
						e.setCancelled(true);
						p.openInventory(new menu().getUpgradesInventory(p));
						
					}else if(e.getRightClicked().getCustomName().equals("§cBOUTIQUE")){
						
						e.setCancelled(true);
						p.openInventory(new menu().getShopInventory(p, 0));
						
					}
					
				}
				
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent e){
		
		Player p = e.getPlayer();
		
		if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){

			Location loc = e.getRightClicked().getLocation();
			if((!new CanBuild().canBuild(p, loc))){
				if(e.getRightClicked() instanceof LivingEntity){
					if(new GetZoneId().getIdOfPlayerZone(loc) != 0){
						new CanBuild().canBuildMessage(p, loc);
						if(p.getGameMode() != GameMode.CREATIVE){
							e.setCancelled(true);
						}
					}
					
				}else if(e.getRightClicked().getType() == EntityType.BOAT || e.getRightClicked().getType() == EntityType.MINECART){
				}else{
					new CanBuild().canBuildMessage(p, loc);
					if(p.getGameMode() != GameMode.CREATIVE){
						e.setCancelled(true);
					}
				}
				
			}
		}else if(p.getWorld() == Bukkit.getWorld("hub")){
			
			if(p.getGameMode() == GameMode.SURVIVAL){
				title.sendActionBar(p, "§cVEUILLEZ NE PAS DÉTRUIRE LE SPAWN");
				e.setCancelled(true);
			}else{
				title.sendActionBar(p, "§cVEUILLEZ NE PAS DÉTRUIRE LE SPAWN");
			}
			
		}
		
		
	}
	
	@EventHandler
	public void playerARmorStand(PlayerArmorStandManipulateEvent e){
	
		if(!new CanBuild().canBuildMessagewithEmployees(e.getPlayer(), e.getRightClicked().getLocation())){
			if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
				e.setCancelled(true);
			}
		}
		
		
	}
	
	@EventHandler
    public void onPistonExtendEvent(BlockPistonExtendEvent e){
		
		if(e.getBlock().getWorld() == Bukkit.getWorld("world") || e.getBlock().getWorld() == Bukkit.getWorld("world_nether") || e.getBlock().getWorld() == Bukkit.getWorld("world_the_end")){

			GetZoneId CGetZoneId = new GetZoneId();
			int id = CGetZoneId.getIdOfPlayerZone(e.getBlock().getLocation());
			for(Block block : e.getBlocks()){
				
				if(CGetZoneId.getIdOfPlayerZone(block.getLocation()) != id){
					e.setCancelled(true);
					return;
				}
			}
		}
		
        
    }
    @EventHandler
    public void onPistonRetractEvent(BlockPistonRetractEvent e){
    	
    	if(e.getBlock().getWorld() == Bukkit.getWorld("world") || e.getBlock().getWorld() == Bukkit.getWorld("world_nether") || e.getBlock().getWorld() == Bukkit.getWorld("world_the_end")){

			GetZoneId CGetZoneId = new GetZoneId();
			int id = CGetZoneId.getIdOfPlayerZone(e.getBlock().getLocation());
			for(Block block : e.getBlocks()){
				
				if(CGetZoneId.getIdOfPlayerZone(block.getLocation()) != id){
					e.setCancelled(true);
					return;
				}
			}
		}
        
    }
    @EventHandler
    public void onItemUseEvent(PlayerItemDamageEvent e){
    	
    	if(e.getPlayer().getWorld() == Bukkit.getWorld("BedWars")){
    		e.setDamage(0);
			e.setCancelled(true);
		}
        
    }
    
    private BlockFace getDirection(Player player) {
    	
    	
    	double rot = player.getLocation().getYaw();
    	
    	if(rot <= 22.5) return BlockFace.SOUTH;
    	if(rot <= 67.5) return BlockFace.SOUTH_WEST;
        else if (rot <= 112.5) return BlockFace.WEST;
    	if(rot <= 157.5) return BlockFace.NORTH_WEST;
        else if (rot <= 202.5) return BlockFace.NORTH;
    	if(rot <= 247.5) return BlockFace.NORTH_EAST;
        else if (rot <= 292.5) return BlockFace.EAST;
    	if(rot <= 337.5) return BlockFace.SOUTH_EAST;
        else if (rot <= 360) return BlockFace.SOUTH;
        
        else return null;
        
    }
}

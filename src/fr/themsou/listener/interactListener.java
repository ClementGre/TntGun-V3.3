package fr.themsou.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.themsou.BedWars.BedWarsPNJInv;
import fr.themsou.BedWars.getteam;
import fr.themsou.inv.HubInv;
import fr.themsou.inv.VipInv;
import fr.themsou.main.main;
import fr.themsou.methodes.PInfos;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.CanBuild;
import fr.themsou.rp.claim.GetZoneId;
import fr.themsou.rp.claim.RPInteractListener;
import fr.themsou.rp.claim.SignClick;
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
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		
		if(PInfos.getGame(p).equals("RP")){
			
			boolean cancel = false;
			
			if(e.getAction() == Action.PHYSICAL){
				
				if(!new RPInteractListener().canPlayerDoPhysical(p)){
					e.setCancelled(true);
				}
				
			}else if(e.getClickedBlock() != null && e.getAction() == Action.LEFT_CLICK_BLOCK){
				
				if(!new RPInteractListener().canGeneralInteract(p, e.getClickedBlock(), e.getAction())){
					e.setCancelled(true);
				}
				
			}else if(e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK){
				
				if(!new RPInteractListener().canInteractPlace(p, e.getClickedBlock(), e.getBlockFace())){
					e.setCancelled(true);
				}
			}
				
			//Tools
			if(!cancel && e.getAction() == Action.RIGHT_CLICK_BLOCK){
				new Tools().playerInteract(p, e.getClickedBlock().getLocation(), p.getInventory().getItemInMainHand());
			}
			new Tools().defineFace(p, e.getBlockFace());
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
									new HubInv().openInv(p);
								}else if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lVIP")){
									new VipInv().openInv(p);
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
		if(PInfos.getGame(p).equals("RP")){

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
						new BedWarsPNJInv().openUpgradesInv(p);
						
					}else if(e.getRightClicked().getCustomName().equals("§cBOUTIQUE")){
						
						e.setCancelled(true);
						new BedWarsPNJInv().openShopInv(p, 0);
						
					}
					
				}
				
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent e){
		
		Player p = e.getPlayer();
		
		if(PInfos.getGame(p).equals("RP")){

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
	public void playerArmorStand(PlayerArmorStandManipulateEvent e){
	
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
				
				Location blockLoc = block.getRelative(e.getDirection()).getLocation();
				
				if(CGetZoneId.getIdOfPlayerZone(blockLoc) != id){
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
}

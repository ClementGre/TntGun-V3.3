package fr.themsou.listener;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.themsou.BedWars.getteam;
import fr.themsou.TntWars.TntWarsGameEvents;
import fr.themsou.main.main;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.CanBuild;

public class DamageListener implements Listener{
	
	main pl;
	public DamageListener(main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void fooodlevel(FoodLevelChangeEvent e){
		
		
		if(e.getEntity() instanceof Player){
			
			Player p = (Player) e.getEntity();
			CanBuild CCanBuild = new CanBuild();
			
			if(!CCanBuild.canBuildMessage(p, p.getLocation()) || p.getWorld() == Bukkit.getWorld("hub") || p.getWorld() == Bukkit.getWorld("BedWars")){
				
				if(e.getFoodLevel() < p.getFoodLevel()) e.setCancelled(true);
			}
			
		}
		
		
	}
	
	@EventHandler
    public void HangingBreakByEntityEvent(org.bukkit.event.hanging.HangingBreakByEntityEvent e){
		
		CanBuild CCanBuild = new CanBuild();
		
		if(e.getEntity() instanceof ItemFrame || e.getEntity() instanceof Painting){
			
			if(e.getCause() == RemoveCause.EXPLOSION){
				
				e.setCancelled(true);
				
			}else if(e.getCause() == RemoveCause.ENTITY){
					
				if(e.getRemover() instanceof Player){
					
					Player p = (Player) e.getRemover();
					if(!CCanBuild.canBuildMessage(p, e.getEntity().getLocation())){
						if(p.getGameMode() == GameMode.SURVIVAL){
							e.setCancelled(true);
						}
					}
					
				}else{
					e.setCancelled(true);
				}
				
			}
			
		}
		
		
		
	}
	
	@EventHandler
	public void ondamage(EntityDamageByEntityEvent e){
		
		Entity killer = e.getDamager();
		Entity victim = e.getEntity();
		CanBuild CCanBuild = new CanBuild();
		getteam Cgetteam = new getteam();
		Location loc = victim.getLocation();
		
		if(killer.getLocation().getWorld() == Bukkit.getWorld("world") || killer.getLocation().getWorld() == Bukkit.getWorld("world_nether") || killer.getLocation().getWorld() == Bukkit.getWorld("world_the_end")){
			
			if(loc.getX() >= -231 && loc.getX() <= -206 && loc.getZ() >= -1026 && loc.getZ() <= -1003 && loc.getY() >= 33 && loc.getY() <= 36) return;
			// Quoted_sand
			
			if(killer instanceof Player){
				Player p = (Player) killer;
				if(e.getEntity() instanceof Player){ 
					if(p.getGameMode() != GameMode.CREATIVE){
						e.setCancelled(true);
					} // PVP
				}
				
				if(!CCanBuild.canBuild(p, p.getLocation())){
					if(p.getGameMode() != GameMode.CREATIVE){
						e.setCancelled(true);
					} // PVE // can't build
				}
			}else if(killer instanceof Arrow){
				
				if(victim instanceof Player){
					if(((Arrow) killer).getShooter() instanceof Player){
						
						Player p = (Player) ((Arrow) killer).getShooter();
						
						if(p.getGameMode() != GameMode.CREATIVE){
							e.setCancelled(true);
						}  // PVP // can't build // BOW
						
					}
				}else{
					if(((Arrow) killer).getShooter() instanceof Player){
						
						Player p = (Player) ((Arrow) killer).getShooter();
						
						if(!CCanBuild.canBuild(((Player)((Arrow) killer).getShooter()), victim.getLocation())){
							if(p.getGameMode() != GameMode.CREATIVE){
								e.setCancelled(true);
							}  // PVE // can't build // BOW
						}
						
					}
				}
			}else if(killer instanceof Trident){
				
				if(victim instanceof Player){
					if(((Trident) killer).getShooter() instanceof Player){
						
						Player p = (Player) ((Trident) killer).getShooter();
						
						if(p.getGameMode() != GameMode.CREATIVE){
							e.setCancelled(true);
						}  // PVP // can't build // BOW
						
					}
				}else{
					if(((Trident) killer).getShooter() instanceof Player){
						
						Player p = (Player) ((Trident) killer).getShooter();
						
						if(!CCanBuild.canBuild(((Player)((Trident) killer).getShooter()), victim.getLocation())){
							if(p.getGameMode() != GameMode.CREATIVE){
								e.setCancelled(true);
							}  // PVE // can't build // BOW
						}
						
					}
				}
				
				
			}
			
			
		}else if(victim.getWorld() == Bukkit.getWorld("TntWars")){
			if(victim instanceof Player){
				if(killer instanceof Player){
					e.setCancelled(true);
				}
				
			}
			
		}else if(victim.getWorld() == Bukkit.getWorld("BedWars")){
			if(victim instanceof Player){
				if(killer instanceof Player){
					
					if(Cgetteam.getplayerteam((Player) victim) == Cgetteam.getplayerteam((Player) killer)){
						e.setCancelled(true);
					}else{
						Player p = (Player) victim;
						if(p.hasPotionEffect(PotionEffectType.INVISIBILITY)) p.removePotionEffect(PotionEffectType.INVISIBILITY);
					}
					
				}else if(killer instanceof Arrow){
					
					if(((Arrow) killer).getShooter() instanceof Player){
						
						if(Cgetteam.getplayerteam((Player) victim) == Cgetteam.getplayerteam(((Player) ((Arrow) killer).getShooter()))){
							e.setCancelled(true);
						}else{
							Player p = (Player) victim;
							if(p.hasPotionEffect(PotionEffectType.INVISIBILITY)) p.removePotionEffect(PotionEffectType.INVISIBILITY);
						}
					}
					
				}
				
			}
		}else{
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void ondamage(EntityDamageEvent e){
		
		Entity victim = e.getEntity();
		CanBuild CCanBuild = new CanBuild();
		getteam Cgetteam = new getteam();
		Location loc = victim.getLocation();
		
		if(victim.getLocation().getWorld() == Bukkit.getWorld("world") || victim.getLocation().getWorld() == Bukkit.getWorld("world_nether") || victim.getLocation().getWorld() == Bukkit.getWorld("world_the_end")){
			
			if(loc.getX() >= -231 && loc.getX() <= -206 && loc.getZ() >= -1026 && loc.getZ() <= -1003 && loc.getY() >= 33 && loc.getY() <= 36) return;
			// Quoted_sand
			
			
			if(victim instanceof Player){
				Player p = (Player) victim;
				if(!CCanBuild.canBuild(p, p.getLocation())){
					
					e.setCancelled(true);
					
				}
			}
		}else if(victim.getLocation().getWorld() == Bukkit.getWorld("TntWars")){
			if(victim instanceof Player){
				Player p = (Player) victim;
				if(p.getHealth() <= e.getDamage()){
					
					p.getInventory().clear();
					e.setCancelled(true);
					p.setHealth(20);
					p.setFoodLevel(20);
					p.setGameMode(GameMode.SURVIVAL);
					
					Bukkit.getScheduler().runTaskLater(pl, new Runnable(){
						@Override
						public void run(){
							new TntWarsGameEvents().PlayerLeave(p);
						}
					}, 10);
					
					
				}
				
			}
		}else if(victim.getWorld() == Bukkit.getWorld("BedWars")){
		
			if(victim instanceof Player){
				Player p = (Player) victim;
				
				
				if(p.getHealth() <= e.getDamage()){
					
					for(ItemStack item : p.getInventory().getContents()){
						if(item != null){
							if(item.getType() == Material.IRON_INGOT || item.getType() == Material.GOLD_INGOT || item.getType() == Material.DIAMOND || item.getType() == Material.EMERALD){
								p.getWorld().dropItem(p.getLocation(), item);
								p.getInventory().remove(item);
							}
						}
					}
					
					p.getInventory().clear();
					e.setCancelled(true);
					p.setHealth(20);
					p.setFoodLevel(20);
					p.setGameMode(GameMode.SPECTATOR);
					p.teleport(new Location(Bukkit.getWorld("BedWars"), 0, 120, 0));
				
					for(Player players : Bukkit.getWorld("BedWars").getPlayers()){
						players.sendMessage("§3" + p.getName() + " §6est mort");
					}
					
					int team = Cgetteam.getplayerteam(p);
					if(team != 0){
						if(main.config.getInt("bedwars.list.teams." + team + ".bed") == 1){
						
							int x = main.configuration.getInt("bedwars.teams." + team + ".x");
							int z = main.configuration.getInt("bedwars.teams." + team + ".z");
							
							new BukkitRunnable(){
					            int countdown = 5;
					            @Override
					            public void run(){

					                if(countdown <= 0){

										p.teleport(new Location(Bukkit.getWorld("BedWars"), x, 100, z));
										p.setGameMode(GameMode.SURVIVAL);
											
					                    this.cancel(); //cancel the repeating task
					                    return; //exit the method

					                }
					                
					                title.sendTitle(p, "§c" + countdown + "s", "§6Vous etes mort", 20);
					                countdown--; //decrement

					            }
							}.runTaskTimer(pl, 0, 20);
						
						}else{
							title.sendTitle(p, "§cVous etes éliminé", "§6Vous n'avez plus de lit", 20);
						}
						
					}
				}
				
			}else{
				e.setCancelled(true);
			}
		}else{
			e.setCancelled(true);
		}
	}
}

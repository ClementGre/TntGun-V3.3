package fr.themsou.listener;

import java.util.List;
import java.util.Random;

import fr.themsou.methodes.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import fr.themsou.BedWars.getteam;
import fr.themsou.TntWars.TntWarsGame;
import fr.themsou.TntWars.TntWarsGameEvents;
import fr.themsou.main.main;
import fr.themsou.nms.title;

public class MoveListener implements Listener{
	
	main pl;
	public MoveListener(main pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onwalk(PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		PlayerInfo pInfo = main.playersInfos.get(p);
		
		if(!pInfo.isLoggin()){

			Location loc = new Location(Bukkit.getWorld("hub"), 0.5, 50, 0.5);
			p.teleport(loc);
			
			if(new Random().nextInt(5) == 4){
				if(main.config.contains(p.getName() + ".mdp")){

					if(!pInfo.isSecureLogin()) p.sendMessage("§4SÉCURITÉ > §bFaites §c/l <mot de passe>");
					else p.sendMessage("§4SÉCURITÉ > §bFaites §c/l <mot de passe> <mot de passe>");
					
				}else{
					p.sendMessage("§4SÉCURITÉ > §bFaites §c/reg <mot de passe>");
				}
			}
			
		}else{

			pInfo.setLastPitch(e.getTo().getPitch());
			pInfo.setLastYaw(e.getTo().getYaw());

			
			if(p.getWorld() == Bukkit.getWorld("BedWars")){
				
				if(p.getGameMode() == GameMode.SURVIVAL){
					if(p.getLocation().getBlockY() <= 30){
						
						p.damage(500);
						
					}else{
						
						int x = p.getLocation().getBlockX();
						int z = p.getLocation().getBlockZ();
						
						for(int i = 4; i >= 1; i--){
							
							if(main.config.getString("bedwars.list.teams." + i + ".owners") != null){
								
								String[] owners = main.config.getString("bedwars.list.teams." + i + ".owners").split(",");
								
								int bx = main.configuration.getInt("bedwars.teams." + i + ".x");
								int bz = main.configuration.getInt("bedwars.teams." + i + ".z");
									
								if(x <= bx + 20 && x >= bx - 20 && z <= bz + 20 && z >= bz - 20 && i != new getteam().getplayerteam(p)){
									
									int alertLevel = main.config.getInt("bedwars.list.teams." + i + ".upgrades.Alerte");
									
									if(alertLevel == 1){
										
										main.config.set("bedwars.list.teams." + i + ".upgrades.Alerte", 0);
										
										for(String playersName : owners){
											
											if(Bukkit.getPlayerExact(playersName) != null){
												if(Bukkit.getPlayerExact(playersName).getWorld() == Bukkit.getWorld("BedWars")){
													if(Bukkit.getPlayerExact(playersName).getGameMode() == GameMode.SURVIVAL){
														title.sendTitle(Bukkit.getPlayerExact(playersName), "§cQuelqu'un attaque votre base !", "§6L'amélioration a été désactivée", 60);
													}
												}
											}
											
										}
									}else if(alertLevel == 2){
										
										main.config.set("bedwars.list.teams." + i + ".upgrades.Alerte", 0);
										
										for(String playersName : owners){
											
											if(Bukkit.getPlayerExact(playersName) != null){
												if(Bukkit.getPlayerExact(playersName).getWorld() == Bukkit.getWorld("BedWars")){
													if(Bukkit.getPlayerExact(playersName).getGameMode() == GameMode.SURVIVAL){
														title.sendTitle(Bukkit.getPlayerExact(playersName), "§cQuelqu'un attaque votre base !", "§6L'amélioration a été désactivée", 60);
														Bukkit.getPlayerExact(playersName).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));
													}
												}
											}
											
										}
										
									}
									
								}
								
									
								
							}
						}
						
						
					}
					
					
					
				}
				
				
			}else if(p.getWorld() == Bukkit.getWorld("TntWars")){
				
				TntWarsGame game = TntWarsGame.getInstanceViaPlayer(p);
				if(game != null){
					
					int xl = game.team1Spawn.getBlockX();
					int zl = game.team1Spawn.getBlockZ();
					if(game.getPlayerTeam(p) == 2){
						xl = game.team2Spawn.getBlockX();
						zl = game.team2Spawn.getBlockZ();
					}
					
					int px = p.getLocation().getBlockX();
					int pz = p.getLocation().getBlockZ();
					
					if(px - xl <= 30 && px - xl >= -30 && pz - zl <= 25 && pz - zl >= -25){
						
					}else{
						
						p.teleport(new Location(Bukkit.getWorld("TntWars"), xl, 100 + 2, zl));
						p.sendMessage("§cVeuillez ne pas vous éloigner trop loin !");
						
					}
					
					if(p.getLocation().getBlockY() <= 80){
						new TntWarsGameEvents().PlayerLeave(p);
					}
				}
			}else if(p.getWorld() == Bukkit.getWorld("world")){
				
					
				List<Entity> entList = Bukkit.getWorld("world").getEntities();
	             
                for(Entity ent : entList){
                    
                	if(ent instanceof Minecart){
                		
                		double px = p.getLocation().getX();
                		double pz = p.getLocation().getZ();
                		
                		double mx = ent.getLocation().getX();
                		double mz = ent.getLocation().getZ();
                		
                		if(mx > px - 2 && mx < px + 2 && mz > pz - 2 && mz < pz + 2){
                			
                			Minecart m = (Minecart) ent;
        			    	
        			    	
        		    		Vector vector = ent.getVelocity();
        			    	
        			    	double y = vector.getY();
        			    	double x = 0.2;
        			    	double z = vector.getZ();
        			    	
        			    	Vector velocity = new Vector(x, y, z);
        			    
        			    	m.setVelocity(velocity);
                			
                		}
                	}
                	
                }
				
			}
			
		}
	}

}

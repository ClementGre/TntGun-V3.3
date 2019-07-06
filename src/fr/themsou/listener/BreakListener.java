package fr.themsou.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.themsou.BedWars.getteam;
import fr.themsou.main.main;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.GetZoneId;
import fr.themsou.rp.claim.Spawns;
import fr.themsou.rp.tools.Bucheron;
import fr.themsou.rp.tools.Fermier;
import fr.themsou.rp.tools.Mineur;
import fr.themsou.rp.tools.Tools;

public class BreakListener implements Listener{
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		
		Player p = e.getPlayer();
		getteam Cgetteam = new getteam();
		
		if(p.getWorld() == Bukkit.getWorld("world")){
			if(p.getGameMode() == GameMode.SURVIVAL){
				
				new Tools().playerBreak(p, e.getBlock().getLocation(), p.getInventory().getItemInMainHand());
				
				if(e.getBlock().getType() == Material.OAK_WALL_SIGN || e.getBlock().getType() == Material.OAK_SIGN){
					if(((Sign) e.getBlock().getState()).getLine(0).equals("§3[Acheter]")){
						e.setDropItems(false);
						
						// regive de sign
						
					}
					if(((Sign) e.getBlock().getState()).getLine(0).equals("§b[Acheter]")){
						e.setDropItems(false);
						
						p.sendMessage("§cCe claim n'est officielement plus en vente !");
						
						int id = new GetZoneId().getIdOfPlayerZone(p.getLocation());
						String spawn = new Spawns().getSpawnNameWithId(id);
						main.config.set("claim.list." + spawn + "." + id + ".sell", false);
					}
				}
				
				if(p.getInventory().getItemInMainHand().getType() != Material.AIR){
					if(p.getInventory().getItemInMainHand().getItemMeta() != null){
						if(p.getInventory().getItemInMainHand().getItemMeta().getEnchants() != null){
							if(p.getInventory().getItemInMainHand().getItemMeta().getEnchants().containsKey(Enchantment.SILK_TOUCH)){
								return;
							}
						}
					}
				}
				
				if(e.getBlock().getType() == Material.OAK_LOG || e.getBlock().getType() == Material.SPRUCE_LOG || e.getBlock().getType() == Material.BIRCH_LOG || e.getBlock().getType() == Material.JUNGLE_LOG || e.getBlock().getType() == Material.ACACIA_LOG || e.getBlock().getType() == Material.DARK_OAK_LOG){
					
					new Bucheron(p).addPCAuto();
				
				}else if(e.getBlock().getType() == Material.POTATO || e.getBlock().getType() == Material.CARROT || e.getBlock().getType() == Material.WHEAT_SEEDS){
					
					if(e.getBlock().getData() == (byte) 7) new Fermier(p).addPCAuto();

				}else if(e.getBlock().getType() == Material.MELON || e.getBlock().getType() == Material.PUMPKIN){
					
					new Fermier(p).addPCAuto();
					
				}else if(e.getBlock().getType() == Material.DIAMOND_ORE || e.getBlock().getType() == Material.COAL_ORE || e.getBlock().getType() == Material.REDSTONE_ORE  || e.getBlock().getType() == Material.LAPIS_ORE){
					
					new Mineur(p).addPCAuto();
				}
			}
		}else if(p.getWorld() == Bukkit.getWorld("BedWars")){
			if(e.getBlock().getType() == Material.RED_BED || e.getBlock().getType() == Material.BLUE_BED || e.getBlock().getType() == Material.GREEN_BED || e.getBlock().getType() == Material.YELLOW_BED){
				
				for(int i = 1; i <= 4; i++){
					
					if(main.config.getString("bedwars.list.teams." + i + ".owners") != null){
						if(main.config.getInt("bedwars.list.teams." + i + ".bed") == 1){
								
							int x = main.configuration.getInt("bedwars.teams." + i + ".x");
							int z = main.configuration.getInt("bedwars.teams." + i + ".z");
							int ori = main.configuration.getInt("bedwars.teams." + i + ".ori");
							
							
							Location bedLoc1 = new Location(Bukkit.getWorld("BedWars"), x, 100, z);
							Location bedLoc2 = new Location(Bukkit.getWorld("BedWars"), x, 100, z);
							
							if(ori == 1){
								
								bedLoc1.setZ(bedLoc1.getZ() + 9);
								bedLoc2.setZ(bedLoc1.getZ() + 1);
								
							}else if(ori == 2){
								
								bedLoc1.setX(bedLoc1.getX() - 9);
								bedLoc2.setX(bedLoc1.getX() - 1);
								
							}else if(ori == 3){
								
								bedLoc1.setZ(bedLoc1.getZ() - 9);
								bedLoc2.setZ(bedLoc1.getZ() - 1);
								
							}else{
								
								bedLoc1.setX(bedLoc1.getX() + 9);
								bedLoc2.setX(bedLoc1.getX() + 1);
								
							}
							
							if((e.getBlock().getLocation().getBlockX() == bedLoc1.getBlockX() && e.getBlock().getLocation().getBlockZ() == bedLoc1.getBlockZ()) || (e.getBlock().getLocation().getBlockX() == bedLoc2.getBlockX() && e.getBlock().getLocation().getBlockZ() == bedLoc2.getBlockZ())){
								
								int team = Cgetteam.getplayerteam(p);
								String teamName = Cgetteam.getTeamChatColor(i) + Cgetteam.getTeamStringColor(i);
								
								if(team != i){
									
									if(main.config.getInt("bedwars.list.status") != 0 && main.config.getInt("bedwars.list.status") != 5){
										if(main.config.getInt("bedwars.list.teams." + i + ".bed") == 1){
											
											p.sendMessage("§cVous venez de cassez le lit des " + teamName + " §6+40 points");
											main.config.set("bedwars.list.teams." + i + ".bed", 0);
											main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") + 40);
											
											e.setDropItems(false);
											
											for(Player players : Bukkit.getWorld("BedWars").getPlayers()){
												
												title.sendTitle(players, "§cLes " + teamName + " §cont perdus leurs lit", "§6Ils ne peuvent plus réaparaitre.", 60);
												
											}
										}
									}
									
								}else{
									p.sendMessage("§cVous ne pouvez pas détruire votre propre lit");
									e.setCancelled(true);
								}
								return;
								
							}
							
						}
					}
				}
			
			}else{
				
				Material material = e.getBlock().getType();
				
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
								
								e.setDropItems(false);
								e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), item);
								
								return;
							}
						}
					}
				}
			}
			
		}
		
	}
	
	@EventHandler
	public void onplace(BlockPlaceEvent e){
		
		Player p = e.getPlayer();
		
		if(p.getWorld() == Bukkit.getWorld("BedWars")){
			
			Location loc = e.getBlock().getLocation();
			
			if(loc.getBlockY() <= 85 || loc.getBlockY() >= 125){
				e.setCancelled(true);
				p.sendMessage("§cVous ne pouvez pas construire à cette hauteur");
				return;
			}
			
			if(e.getBlock().getType() == Material.TNT){
				
				loc.getBlock().setType(Material.AIR);
				loc.setX(loc.getX() + 0.5);
				loc.setZ(loc.getZ() + 0.5);
				loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
				
				
			}
			
		}
				
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMobspawnerplace(BlockPlaceEvent e){
		Block b = e.getBlock();
		if(b.getType() == Material.SPAWNER){
			
			if(!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().get(0).equalsIgnoreCase("cage")){
				List<String> lore = e.getPlayer().getItemInHand().getItemMeta().getLore();
				String type = lore.get(0);
				
				CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
				
				spawner.setSpawnedType(EntityType.fromName(type));
			}else{
				e.getPlayer().sendMessage("§cVeuillez crafter un spawner avec la cage du spawner");
				e.setCancelled(true);
			}
		}
		
	}
	@EventHandler
	public void blockForm(BlockFormEvent e){
		
		if(e.getBlock().getType() == Material.WATER){
			
			if(new Spawns().isInSpawn(e.getBlock().getLocation())){
				e.setCancelled(true);
			}
			
		}
		
	}
	
	@EventHandler
	public void onFade(BlockFadeEvent e){
		
		Spawns CSpawns = new Spawns();
		
		if(CSpawns.isInSpawn(e.getBlock().getLocation())){
			e.setCancelled(true);
		}
		
	}
	@EventHandler
	public void onGrow(StructureGrowEvent e){
		
		if(e.getWorld() == Bukkit.getWorld("world") || e.getWorld() == Bukkit.getWorld("world_nether") || e.getWorld() == Bukkit.getWorld("world_the_end")){

			GetZoneId CGetZoneId = new GetZoneId();
			int id = CGetZoneId.getIdOfPlayerZone(e.getLocation());
			
			int i = 0;
			for(BlockState block : e.getBlocks()){
				
				if(CGetZoneId.getIdOfPlayerZone(block.getLocation()) != id){
					System.out.println(block.getBlockData().getAsString());
						e.getBlocks().get(i).setType(Material.AIR);
				}
				
				i++;
			}
		}
		
	}
	
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent e) {
		
		
		//Material block = e.getBlock().getType();
		
			
		GetZoneId CGetZoneId = new GetZoneId();
		
		int id = CGetZoneId.getIdOfPlayerZone(e.getBlock().getLocation());
		int toId = CGetZoneId.getIdOfPlayerZone(e.getToBlock().getLocation());
		
		if(toId != id){
			e.setCancelled(true);
		}
			
		
	}
	
	

}









package fr.themsou.rp.claim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import fr.themsou.main.main;
import fr.themsou.methodes.realDate;

public class RPInteractListener{
	
	CanBuild CCanBuild = new CanBuild();
	
	@SuppressWarnings("deprecation")
	public boolean canGeneralInteract(Player p, Block block, Action action){
		
		Location loc = block.getLocation();
		Material mat = block.getType();
		
		if(!CCanBuild.canBuildMessage(p, loc)){
			
			if(p.getGameMode() != GameMode.CREATIVE){
				return false;
			}else{
				
				// --- LOG ---
				
				String debug = CCanBuild.canBuildDebug(p, loc);
				if(!debug.equals("Spawn")){
					
					Date rd = new realDate().getRealDate();
					String date = "[" + rd.getMonth() + "/" + rd.getDay() + " " + rd.getHours() + ":" + rd.getMinutes() + "] ";
					String blockName = "";
					if(action == Action.RIGHT_CLICK_BLOCK){
						if(p.getInventory().getItemInMainHand().getType().toString().equals("AIR")) return true;
						blockName = "posé le bloc \"" + p.getInventory().getItemInMainHand().getType().toString();
					}else{
						
						blockName = "cassé le bloc \"" + mat.toString();
					}
					try{
						FileWriter writer = new FileWriter(main.logblock.getAbsoluteFile(), true);
						BufferedWriter out = new BufferedWriter(writer);
						out.write(date + p.getName() + " a " + blockName + "\" dans le claim de " + debug);
						out.newLine();
						out.close();
					}catch(IOException e1){
						e1.printStackTrace();
					}
					
				}
				
			}
			
		}
		return true;
	}
	
	public boolean canInteractPlace(Player p, Block block, BlockFace face){
		
		Location loc = block.getLocation();
		Material mat = block.getType();
		
		if(!CCanBuild.canBuild(p, loc)){
			
			// DÉCALAGE PORTES
			if(CCanBuild.canBuild(p, loc.getBlock().getRelative(BlockFace.NORTH).getLocation())){
				return true;
			}else if(CCanBuild.canBuild(p, loc.getBlock().getRelative(BlockFace.SOUTH).getLocation())){
				return true;
			}else if(CCanBuild.canBuild(p, loc.getBlock().getRelative(BlockFace.EAST).getLocation())){
				return true;
			}else if(CCanBuild.canBuild(p, loc.getBlock().getRelative(BlockFace.WEST).getLocation())){
				return true;
			}else if(CCanBuild.canBuild(p, loc.getBlock().getRelative(BlockFace.UP).getLocation())){
				return true;
			}else if(CCanBuild.canBuild(p, loc.getBlock().getRelative(BlockFace.DOWN).getLocation())){
				return true;
			}
			
			
			// CIRCUIT BOURG_PALETTE
			if(mat == Material.PACKED_ICE && p.getInventory().getItemInMainHand() != null){
				if(loc.getBlockY() == 67){
					if(new GetZoneId().getIdOfPlayerZone(loc) == 0 && new Spawns().isInSpawn(loc)){
						Material mainMaterial = p.getInventory().getItemInMainHand().getType();
						if(mainMaterial == Material.OAK_BOAT || mainMaterial == Material.ACACIA_BOAT || mainMaterial == Material.BIRCH_BOAT || mainMaterial == Material.DARK_OAK_BOAT || mainMaterial == Material.JUNGLE_BOAT || mainMaterial == Material.SPRUCE_BOAT){
							return true;
						}
					}
				}
			}
			
			// MINECART
			if((mat == Material.RAIL || mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL || mat == Material.DETECTOR_RAIL) && p.getInventory().getItemInMainHand() != null){
				Material mainMaterial = p.getInventory().getItemInMainHand().getType();
				if(mainMaterial == Material.MINECART){
					return true;
				}
			}
			
			// OAK BTN EXCEPTION
			if(mat == Material.OAK_BUTTON) return true;
			
			// FINAL
			if(!CCanBuild.canBuildMessagewithEmployees(p, loc)){
				if(p.getGameMode() != GameMode.CREATIVE){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean canPlayerDoPhysical(Player p){
		
		if(!CCanBuild.canBuildMessagewithEmployees(p, p.getLocation())){
			if(p.getLocation().getBlock().getType() == Material.OAK_PRESSURE_PLATE){
				return true;
			}if(p.getGameMode() != GameMode.CREATIVE){
				return false;
			}
		}
		return true;
	}

}

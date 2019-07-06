package fr.themsou.rp.tools;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Tools{
	
	private static HashMap<String, BlockFace> faces = new HashMap<>();
	
	@SuppressWarnings("static-access")
	public void defineFace(Player p, BlockFace face){
		
		if(face != null){
			this.faces.put(p.getName(), face);
		}
		
	}
	
	public void playerInteract(Player p, Location loc, ItemStack item){
			
		if(p.getInventory().getItemInMainHand() != null){
			if(p.getInventory().getItemInMainHand().getItemMeta() != null){
				if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null){
					if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§3§lSuper Houe")){

						new Fermier(p).useAuto(loc);
					}
				}
			}
		}
		
	}
	
	public void playerBreak(Player p, Location loc, ItemStack item){
		
		if(p.getInventory().getItemInMainHand().getType() != Material.AIR){
			if(p.getInventory().getItemInMainHand().getItemMeta() != null){
				if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null){
					
					if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§3§lSuper Hache")){
						
						if(loc.getBlock().getType() == Material.OAK_LOG || loc.getBlock().getType() == Material.SPRUCE_LOG || loc.getBlock().getType() == Material.BIRCH_LOG || loc.getBlock().getType() == Material.ACACIA_LOG || loc.getBlock().getType() == Material.JUNGLE_LOG || loc.getBlock().getType() == Material.DARK_OAK_LOG){
							
							new Bucheron(p).useAuto(loc);
						}
						
					}if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§3§lSuper Pioche")){
						
						new Mineur(p).useAuto(loc, item, faces.get(p.getName()));
						
					}
				}
			}
		}
		
	}
}

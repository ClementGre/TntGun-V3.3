package fr.themsou.rp.ent;

import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.themsou.main.main;
import fr.themsou.methodes.SimpleItem;

public class Utils {
	
	
	public void playerInteractRightClick(Player p, Block block, BlockFace face){
		
		Material mat = block.getType();
		
		// ENT OPEN CHEST
		if(mat == Material.CHEST || mat == Material.TRAPPED_CHEST){
			Inventory inv = ((Chest) block.getState()).getInventory();
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
		
		// ENT PLACE SIGN
		if(p.getInventory().getItemInMainHand() != null){
			if(p.getInventory().getItemInMainHand().getType() == Material.OAK_SIGN){
				
				ItemStack item = p.getInventory().getItemInMainHand().clone();
				
				if(item.getItemMeta() != null){
					if(item.getItemMeta().getDisplayName() != null){
						if(item.getItemMeta().getDisplayName().contains("§r§bPancarte de vente (")){
							
							if(face == BlockFace.UP){ // normal sign
								new Sign().placeSign(block.getRelative(face), item, false, getDirection(p));
								
							}else if(face != BlockFace.DOWN){ // Wall sign
								new Sign().placeSign(block.getRelative(face), item, true, face);
							}
						}
					}
				}
			}
		}
		
	}
	
	public void log(Player p, HashMap<SimpleItem, Integer> items, boolean add){
		
		String ent = main.config.getString(p.getName() + ".rp.ent.name");
		String act = "remove";
		if(add) act = "add";
		
		String display = "";
		for(SimpleItem item : items.keySet()){
			display += "§b" + items.get(item) + " x §3" + item.getType() + "§7:" + item.getDamage() + "§c, ";
		}
		
		String current = "";
		if(main.config.contains("ent.list." + ent + ".log." + p.getName() + "." + act))
			current = main.config.getString("ent.list." + ent + ".log." + p.getName() + "." + act);
		
		main.config.set("ent.list." + ent + ".log." + p.getName() + "." + act, current + display);
		
	}
	
	
	public void payEntreprises(){
		
		ConfigurationSection entSection = main.config.getConfigurationSection("ent.list");
		
		for(String ent : entSection.getKeys(false)){
			if(main.config.contains("ent.list." + ent + ".buyers")){
				
				ConfigurationSection buyersSection = main.config.getConfigurationSection("ent.list." + ent + ".buyers");
				
				if(buyersSection.getKeys(false).size() >= 11){
					
					int[] buyersValue = new int[buyersSection.getKeys(false).size()]; int i = 0;
					for(String buyer : buyersSection.getKeys(false)){
						buyersValue[i] = main.config.getInt("ent.list." + ent + ".buyers." + buyer);
						i++;
					}
					Arrays.sort(buyersValue);
					
					int median = buyersValue[buyersValue.length / 2];
					
					int total = median * buyersSection.getKeys(false).size();
					
					main.config.set("ent.list." + ent + ".money", main.config.getInt("ent.list." + ent + ".buyers") + total); 
					main.config.set("ent.list." + ent + ".buyers", null); 
					
					String pdg = main.config.getString("ent.list." + ent + ".pdg");
					String notifs = main.config.getString(pdg + ".notifs");
					
					if(notifs == null) notifs = "";
					
					main.config.set(pdg + ".notifs", notifs + "," + "§6Votre entreprise a reçu §c" + total + "€§6 pour vous récompenser de vos ventes.");
				}else{
					String pdg = main.config.getString("ent.list." + ent + ".pdg");
					String notifs = main.config.getString(pdg + ".notifs");
					main.config.set(pdg + ".notifs", notifs + "," + "§6Votre entreprise a reçu §c0€§6 pour vous récompenser de vos ventes.");
				}
				
			}
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

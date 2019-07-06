package fr.themsou.inv;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class setwalls {
	
	public void setWalls(Inventory inv, int rangées){
		
		
		ItemStack u = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta uM = u.getItemMeta();
		uM.setDisplayName("§e");
		u.setItemMeta(uM);
		
		inv.setItem(0, u);
		inv.setItem(1, u);
		inv.setItem(2, u);
		inv.setItem(3, u);
		inv.setItem(4, u);
		inv.setItem(5, u);
		inv.setItem(6, u);
		inv.setItem(7, u);
		inv.setItem(8, u);
		
		if(rangées == 5){
			
			inv.setItem(9, u);
			inv.setItem(17, u);
			
			inv.setItem(18, u);
			inv.setItem(26, u);
			
			inv.setItem(27, u);
			inv.setItem(35, u);
			
			inv.setItem(36, u);
			inv.setItem(37, u);
			inv.setItem(38, u);
			inv.setItem(39, u);
			inv.setItem(40, u);
			inv.setItem(41, u);
			inv.setItem(42, u);
			inv.setItem(43, u);
			inv.setItem(44, u);
			
		}if(rangées == 6){
			
			inv.setItem(9, u);
			inv.setItem(17, u);
			
			inv.setItem(18, u);
			inv.setItem(26, u);
			
			inv.setItem(27, u);
			inv.setItem(35, u);
			
			inv.setItem(36, u);
			inv.setItem(44, u);
			
			inv.setItem(45, u);
			inv.setItem(46, u);
			inv.setItem(47, u);
			inv.setItem(48, u);
			inv.setItem(49, u);
			inv.setItem(50, u);
			inv.setItem(51, u);
			inv.setItem(52, u);
			inv.setItem(53, u);
			
		}
		
		
	}

}

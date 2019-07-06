package fr.themsou.inv;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.themsou.main.main;

public class menu {
	
	
	public void setMainInventory(){
		
		
		
		org.bukkit.inventory.Inventory menu = main.menu;
		
		setwalls Csetwalls = new setwalls();
		Csetwalls.setWalls(menu, 5);
		
		ItemStack a = new ItemStack(Material.BRICK, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setDisplayName("§3§lRolePlay");
		aM.setLore(Arrays.asList("§eExplorez un monde vaste,","§eachetez un terrain,","§evendez vos ressources.","§eEt devenez le plus riche !"));
		a.setItemMeta(aM);				
		menu.setItem(20, a);
		
		ItemStack b = new ItemStack(Material.TNT, 1);
		ItemMeta bM = b.getItemMeta();
		bM.setDisplayName("§3§lTntWars");
		bM.setLore(Arrays.asList("§eConstruisez un canon à TNT,","§eet tuez votre adversaire !"));
		b.setItemMeta(bM);				
		menu.setItem(22, b);
		
		ItemStack c = new ItemStack(Material.WHITE_BED, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§3§lBedWars");
		cM.setLore(Arrays.asList("§eComme dans le BedWars que","§evous connaissez, vous devez détruire","§eles lits des adversaires"));
		c.setItemMeta(cM);				
		menu.setItem(24, c);
		
		
		
	}
}

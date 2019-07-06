package fr.themsou.TntWars;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.themsou.main.main;

public class inv {

	public void setTntWarsInventory(){
		
	
		org.bukkit.inventory.Inventory TntWars = main.TntWars;
		
		
		ItemStack a = new ItemStack(Material.GRASS, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setDisplayName("§3§lMap Défaut");
		a.setItemMeta(aM);				
		TntWars.setItem(9, a);
		
		ItemStack b = new ItemStack(Material.SAND, 1);
		ItemMeta bM = b.getItemMeta();
		bM.setDisplayName("§3§lMap Désert");
		b.setItemMeta(bM);				
		TntWars.setItem(10, b);
		
		ItemStack c = new ItemStack(Material.NETHER_WART_BLOCK, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§3§lMap Aléatoire");
		c.setItemMeta(cM);				
		TntWars.setItem(17, c);
	
		
	}
	
}

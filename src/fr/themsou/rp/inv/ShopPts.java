package fr.themsou.rp.inv;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.themsou.inv.setwalls;

public class ShopPts {
	
	public void openInventory(Player p){
		
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4SHOP DE POINTS");
		new setwalls().setWalls(inv, 6);
		
		inv.setItem(20, getItem(Material.DIAMOND, "§fGrade VIP", Arrays.asList("§620 000 Points")));
		inv.setItem(22, getItem(Material.EMERALD, "§f1 000 €", Arrays.asList("§62 000 Points")));
		inv.setItem(24, getItem(Material.DIAMOND_CHESTPLATE, "§fFull P4U3", Arrays.asList("§68 000 Points")));
		
		inv.setItem(30, getItem(Material.ELYTRA, "§fElytra", Arrays.asList("§610 000 Points")));
		inv.setItem(32, getItem(Material.TOTEM_OF_UNDYING, "§fTotem d'imortalité", Arrays.asList("§65 000 Points")));
		
		inv.setItem(49, getItem(Material.ARROW, "§cRETOUR", Arrays.asList("")));
		
		p.openInventory(inv);
	}
	
	public ItemStack getItem(Material material, String name, List<String> description){
		
		ItemStack a = new ItemStack(material, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setDisplayName(name);
		aM.setLore(description);
		a.setItemMeta(aM);				
		
		return a;
		
	}

}

package fr.themsou.inv;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VipInv {
	
	public void openVipInventory(Player p){
		
		Inventory invip = Bukkit.createInventory(null, 6*9, "§4VIP");
		
		ItemStack a = new ItemStack(Material.EMERALD, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setDisplayName("§3§lACHETER LE GRADE §e§lVIP§3§l VRAIE VIE");
		aM.setLore(Arrays.asList("§bPour seulement 4€"));
		a.setItemMeta(aM);				
		invip.setItem(24, a);
		
		ItemStack b = new ItemStack(Material.BOOK, 1);
		ItemMeta bM = b.getItemMeta();
		bM.setDisplayName("§3§lVOIR LES AVANTAGES");
		b.setItemMeta(bM);				
		invip.setItem(22, b);
		
		ItemStack c = new ItemStack(Material.GRASS, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§3§lACHETER LE GRADE §e§lVIP§3§l EN JEUX");
		cM.setLore(Arrays.asList("§bPour seulement §e20 000§b points"));
		c.setItemMeta(cM);				
		invip.setItem(20, c);
		
		ItemStack h = new ItemStack(Material.ARROW, 1);
		ItemMeta hM = h.getItemMeta();
		hM.setDisplayName("§4Retour");
		h.setItemMeta(hM);				
		invip.setItem(40, h);
		
		ItemStack u = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta uM = u.getItemMeta();
		uM.setDisplayName("§e");
		u.setItemMeta(uM);
		invip.setItem(0, u);
		invip.setItem(1, u);
		invip.setItem(2, u);
		invip.setItem(3, u);
		invip.setItem(4, u);
		invip.setItem(5, u);
		invip.setItem(6, u);
		invip.setItem(7, u);
		invip.setItem(8, u);
		invip.setItem(9, u);
		invip.setItem(17, u);
		invip.setItem(18, u);
		invip.setItem(26, u);
		invip.setItem(27, u);
		invip.setItem(35, u);
		invip.setItem(36, u);
		invip.setItem(44, u);
		invip.setItem(45, u);
		invip.setItem(46, u);
		invip.setItem(47, u);
		invip.setItem(48, u);
		invip.setItem(49, u);
		invip.setItem(50, u);
		invip.setItem(51, u);
		invip.setItem(52, u);
		invip.setItem(53, u);
		invip.setItem(44, u);
		
		p.openInventory(invip);
		
		
		
	}

}

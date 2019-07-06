package fr.themsou.rp.inv;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class crafts {

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// MINEUR //////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openInventoryToMineur(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 3*9, "§4MINEUR - CRAFTS");
		
		ItemStack a = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		
		inv.setItem(0, a);
		inv.setItem(1, a);
		inv.setItem(2, a);
		inv.setItem(9, a);
		inv.setItem(10, a);
		inv.setItem(11, a);
		inv.setItem(18, a);
		inv.setItem(19, a);
		inv.setItem(20, a);
		
		ItemStack b = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1);
		
		inv.setItem(13, b);
		
		ItemStack c = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§3§lSuper Pioche");
		c.setItemMeta(cM);
		
		inv.setItem(15, c);
		
		ItemStack z = new ItemStack(Material.ARROW, 1);
		ItemMeta zM = z.getItemMeta();
		zM.setDisplayName("§4RETOUR");
		z.setItemMeta(zM);  
		inv.setItem(26, z);
		
		p.openInventory(inv);
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// BUCHERON ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openInventoryToBucheron(Player p){
			
		Inventory inv = Bukkit.createInventory(null, 3*9, "§4BUCHERON - CRAFTS");
		
		ItemStack a = new ItemStack(Material.DIAMOND_AXE, 1);
		
		inv.setItem(0, a);
		inv.setItem(1, a);
		inv.setItem(2, a);
		inv.setItem(9, a);
		inv.setItem(10, a);
		inv.setItem(11, a);
		inv.setItem(18, a);
		inv.setItem(19, a);
		inv.setItem(20, a);
		
		ItemStack b = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1);
		
		inv.setItem(13, b);
		
		ItemStack c = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§3§lSuper Hache");
		c.setItemMeta(cM);
		
		inv.setItem(15, c);
		
		ItemStack z = new ItemStack(Material.ARROW, 1);
		ItemMeta zM = z.getItemMeta();
		zM.setDisplayName("§4RETOUR");
		z.setItemMeta(zM);  
		inv.setItem(26, z);
		
		p.openInventory(inv);
			
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// FERMIER /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openInventoryToFermier(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 3*9, "§4FERMIER - CRAFTS");
		
		ItemStack a = new ItemStack(Material.DIAMOND_HOE, 1);
		
		inv.setItem(0, a);
		inv.setItem(1, a);
		inv.setItem(2, a);
		inv.setItem(9, a);
		inv.setItem(10, a);
		inv.setItem(11, a);
		inv.setItem(18, a);
		inv.setItem(19, a);
		inv.setItem(20, a);
		
		ItemStack b = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1);
		
		inv.setItem(13, b);
		
		ItemStack c = new ItemStack(Material.DIAMOND_HOE, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§3§lSuper Houe");
		c.setItemMeta(cM);
		
		inv.setItem(15, c);
		
		ItemStack z = new ItemStack(Material.ARROW, 1);
		ItemMeta zM = z.getItemMeta();
		zM.setDisplayName("§4RETOUR");
		z.setItemMeta(zM);  
		inv.setItem(26, z);
		
		p.openInventory(inv);
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// CHASSEUR ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openInventoryToChasseur(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4CHASSEUR - CRAFTS");
		
		ItemStack a = new ItemStack(Material.DIAMOND_BLOCK, 1);
		ItemStack d = new ItemStack(Material.IRON_BARS, 1);
		
		inv.setItem(0, d);
		inv.setItem(1, d);
		inv.setItem(2, d);
		inv.setItem(9, d);
		inv.setItem(10, a);
		inv.setItem(11, d);
		inv.setItem(18, d);
		inv.setItem(19, d);
		inv.setItem(20, d);
		
		ItemStack b = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1);
		
		inv.setItem(13, b);
		
		ItemStack c = new ItemStack(Material.SPAWNER, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§3§lCage de spawner");
		cM.setLore(Arrays.asList("cage"));
		c.setItemMeta(cM);
		
		inv.setItem(15, c);
		
		ItemStack e = new ItemStack(Material.ROTTEN_FLESH, 1);
		
		inv.setItem(27, e);
		inv.setItem(28, e);
		inv.setItem(29, e);
		inv.setItem(36, e);
		inv.setItem(37, c);
		inv.setItem(38, e);
		inv.setItem(45, e);
		inv.setItem(46, e);
		inv.setItem(47, e);
		
		inv.setItem(40, b);
		
		ItemStack f = new ItemStack(Material.SPAWNER, 1);
		ItemMeta fM = f.getItemMeta();
		fM.setDisplayName("§3§lSpawner de zombie");
		fM.setLore(Arrays.asList("zombie"));
		f.setItemMeta(fM);
		
		inv.setItem(42, f);
		
		ItemStack g = new ItemStack(Material.OAK_SIGN, 1);
		ItemMeta gM = g.getItemMeta();
		gM.setDisplayName("§3§lAUTRES SPAWNERS :");
		gM.setLore(Arrays.asList("§3Il vous suffit d'entourer","§3la cage du spawner avec des","§3loots de l'entité","§3ou sa nourriture."));
		g.setItemMeta(gM);
		
		inv.setItem(44, g);
		
		ItemStack z = new ItemStack(Material.ARROW, 1);
		ItemMeta zM = z.getItemMeta();
		zM.setDisplayName("§4RETOUR");
		z.setItemMeta(zM);  
		inv.setItem(53, z);
		
		p.openInventory(inv);
		
	}
///////////////////////////////////////////////////////////t/?/////////////////////////////////////////
////////////////////////////////////////////// ENCHANTEUR /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openInventoryToEnchanteur(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 3*9, "§4ENCHANTEUR - CRAFTS");
		
		ItemStack a = new ItemStack(Material.LAPIS_BLOCK, 1);
		ItemStack b = new ItemStack(Material.ROTTEN_FLESH, 1);
		ItemStack c = new ItemStack(Material.SPIDER_EYE, 1);
		ItemStack d = new ItemStack(Material.BLAZE_POWDER, 1);
		ItemStack e = new ItemStack(Material.BONE, 1);
		

		inv.setItem(1, b);
		
		inv.setItem(9, c);
		inv.setItem(10, a);
		inv.setItem(11, d);
		
		inv.setItem(19, e);
		
		ItemStack f = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1);
		
		inv.setItem(13, f);
		
		ItemStack g = new ItemStack(Material.EXPERIENCE_BOTTLE, 64);
		ItemMeta gM = g.getItemMeta();
		gM.setDisplayName("§3§lBouteille d'XP");
		g.setItemMeta(gM);
		
		inv.setItem(15, g);
		
		ItemStack z = new ItemStack(Material.ARROW, 1);
		ItemMeta zM = z.getItemMeta();
		zM.setDisplayName("§4RETOUR");
		z.setItemMeta(zM); 
		inv.setItem(26, z);
		
		p.openInventory(inv);
		
	}

}

package fr.themsou.rp.inv;

import java.math.BigDecimal;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.themsou.main.main;

public class shop {
	
	public void oppenshopinventory(Player p){
		
		Inventory shop = main.rpshop;
		
		if(shop.getItem(0) == null){
			
			addItem(shop, Material.COBBLESTONE, 20);
			
			addItem(shop, Material.OBSIDIAN, 12800);
			
			addItem(shop, Material.GRANITE, 300);
			
			addItem(shop, Material.DIORITE, 300);
			
			addItem(shop, Material.ANDESITE, 300);
			
			addItem(shop, Material.DIRT, 15);
			
			addItem(shop, Material.SAND, 20);
			
			addItem(shop, Material.GRAVEL, 300);
			
			addItem(shop, Material.NETHERRACK, 50);
			
			addItem(shop, Material.WHITE_WOOL, 150);
			
			addItem(shop, Material.QUARTZ_BLOCK, 750);
			
			addItem(shop, Material.CLAY, 450);
			
			addItem(shop, Material.SEA_LANTERN, 5000);
			
			
			
			addItem(shop, Material.OAK_LOG, 700);
			
			addItem(shop, Material.SPRUCE_LOG, 750);
			
			addItem(shop, Material.BIRCH_LOG, 750);
			
			addItem(shop, Material.JUNGLE_LOG, 750);
			
			addItem(shop, Material.ACACIA_LOG, 750);
			
			addItem(shop, Material.DARK_OAK_LOG, 750);
			
			addItem(shop, Material.OAK_LEAVES, 200);
			
			addItem(shop, Material.PUMPKIN, 750);
			
			addItem(shop, Material.CACTUS, 800);
			
			
			addItem(shop, Material.DIAMOND, 51000);
			
			addItem(shop, Material.GOLD_INGOT, 8000);
			
			addItem(shop, Material.IRON_INGOT, 5000);
			
			addItem(shop, Material.REDSTONE, 500);
			
			addItem(shop, Material.LAPIS_LAZULI, 100);
			
			addItem(shop, Material.COAL, 500);
			
			addItem(shop, Material.EMERALD, 100000);
			
			
			
			
			
			addItem(shop, Material.MELON, 250);
			
			addItem(shop, Material.CARROT, 1000);
			
			addItem(shop, Material.POTATO, 1000);
			
			addItem(shop, Material.WHEAT_SEEDS, 800);
			
			addItem(shop, Material.WHEAT, 1000);
			
			addItem(shop, Material.APPLE, 2000);
			
			addItem(shop, Material.BEETROOT, 100);
			
			addItem(shop, Material.SUGAR_CANE, 500);
			
			
			
			addItem(shop, Material.LEATHER, 400);
			
			addItem(shop, Material.EGG, 400);
			
			addItem(shop, Material.INK_SAC, 500);
			
			addItem(shop, Material.COOKED_BEEF, 400);
			
			addItem(shop, Material.COOKED_MUTTON, 400);
			
			addItem(shop, Material.COOKED_PORKCHOP, 400);
			
			addItem(shop, Material.COOKED_CHICKEN, 400);
			
			
			addItem(shop, Material.ARROW, 500);
			
			addItem(shop, Material.BONE, 500);
			
			addItem(shop, Material.ROTTEN_FLESH, 500);
			
			addItem(shop, Material.SPIDER_EYE, 500);
			
			addItem(shop, Material.STRING, 500);
			
			addItem(shop, Material.BLAZE_ROD, 1000);
			
		}
		
		p.openInventory(shop);
		
	}
	
	
	public void addItem(Inventory inv, Material material, double priceTo64){
		
		BigDecimal priceTo1 = new BigDecimal(priceTo64 / 64);
		priceTo1 = priceTo1.setScale(1, BigDecimal.ROUND_DOWN);
		
		BigDecimal sellPriceTo64 = new BigDecimal(priceTo64 / 10);
		sellPriceTo64 = sellPriceTo64.setScale(1, BigDecimal.ROUND_DOWN);
		
		BigDecimal sellPriceTo1 = new BigDecimal(sellPriceTo64.doubleValue() / 64);
		sellPriceTo1 = sellPriceTo1.setScale(1, BigDecimal.ROUND_DOWN);
		
		
		ItemStack a = new ItemStack(material, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setLore(Arrays.asList("§cACHETER X64 :§6 " + priceTo64 + " € §3CLIQUE DROIT","§cACHETER X1 :§6 " + priceTo1.doubleValue() + " € §3CLIQUE GAUCHE","§cVENDRE X64 :§6 " + sellPriceTo64.doubleValue() + " € §3CLIQUEZ DANS VOTRE INVENTAIRE","§cVENDRE X1 :§6 " + sellPriceTo1.doubleValue() + " € §3CLIQUEZ DANS VOTRE INVENTAIRE", priceTo64 + ""));
		a.setItemMeta(aM);				
		inv.addItem(a);
		
		main.config.set("shop.list." + a.getType().toString(), priceTo64);
		
		
	}

}

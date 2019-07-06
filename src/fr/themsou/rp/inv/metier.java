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
import fr.themsou.main.main;

public class metier {
	
	public void openMetierInv(Player p){
		
		
		Inventory shop = Bukkit.createInventory(null, 6*9, "§4COMPÉTENCES");
		
		setwalls CsetwSetwalls = new setwalls();
		CsetwSetwalls.setWalls(shop, 6);

/////////////// MINEUR
						
		shop.setItem(20, getItem(Material.DIAMOND_PICKAXE, "§3§lMINEUR",
				Arrays.asList("§aVOUS EN ETES A §2" + main.config.getDouble(p.getName() + ".metier.mineur") + "%"," ",
				"§7Gagner des % >§b Vous devez miner des minerais","§b de charbon, de redstone, lapis ou diamant."," ",
				"§7Avantages >§b Vous pourez débloquer une pioche",
				"§bqui permet de casser plusieurs blocs d'un coup :",
				"§7-50% >§b Casse en 1×2",
				"§7-60% >§b Casse en 1×3",
				"§7-70% >§b Casse en 3×3",
				"§7-85% >§b Casse en 3×3×2",
				"§7-100% >§b Casse en 5×3×2")));

////////////// BUCHERON
					
		shop.setItem(22, getItem(Material.DIAMOND_AXE, "§3§lBUCHERON",
				Arrays.asList("§aVOUS EN ETES A §2" + main.config.getDouble(p.getName() + ".metier.bucheron") + "%"," ",
				"§7Gagner des % >§b Vous devez casser des buches.", " ",
				"§7Avantages >§b Vous pourez débloquer une hache qui",
				"§bpermet de casser plusieurs buches d'un coup :",
				"§7-50% >§b Casse 2 buches",
				"§7-60% >§b Casse 4 buches",
				"§7-70% >§b Casse 6 buches",
				"§7-85% >§b Casse 11 buches",
				"§7-100% >§b Casse toutes les buches")));
		
////////////// FERMIER
					
		shop.setItem(24, getItem(Material.DIAMOND_HOE, "§3§lFERMIER",
				Arrays.asList("§aVOUS EN ETES A §2" + main.config.getDouble(p.getName() + ".metier.fermier") + "%"," ",
				"§7Gagner des % >§b Vous devez casser des plantes :", "§bpatates, carrotes, blé, melons et pastèques."," ",
				"§7Avantages >§b Vous pourez débloquer une houe qui", "§bpermet de houer, planter et casser plusieurs blocs.",
				"§7-50% >§b Houe en 3×3",
				"§7-60% >§b Houe et place en 3×3",
				"§7-70% >§b Houe, place et casse en 3×3",
				"§7-100% >§b Houe, place et casse en 5×5")));

////////////// CHASSEUR
				
		shop.setItem(30, getItem(Material.DIAMOND_SWORD, "§3§lCHASSEUR",
				Arrays.asList("§aVOUS EN ETES A §2" + main.config.getDouble(p.getName() + ".metier.chasseur") + "%"," ",
				"§7Gagner des % >§b Vous devez tuer des entités."," ",
				"§7Avantages >§b Vous pourez crafter des spawners",
				"§7-50% >§b Spawners à zombie et cochon",
				"§7-60% >§b + Spawners à araignée et vache",
				"§7-70% >§b + Spawners à squelette et mouton",
				"§7-85% >§b + Spawners à creeper et poule",
				"§7-100% >§b + Spawners à enderman et chevaux")));

////////////// ENCHANTEUR
					
		shop.setItem(32, getItem(Material.ENCHANTING_TABLE, "§3§lENCHANTEUR",
				Arrays.asList("§aVOUS EN ETES A §2" + main.config.getDouble(p.getName() + ".metier.enchanteur") + "%"," ",
				"§7Gagner des % >§b Vous devez enchanter des items."," ",
				"§7Avantages >§b Vous pourez crafter des fioles d'XP",
				"§7-[50%-100%] >§b Fiole d'XP × 1 à 64")));

///////////////////// RETOUR
		
		
		shop.setItem(49, getItem(Material.ARROW, "§4RETOUR", Arrays.asList("RETOUR")));
		
		
		p.openInventory(shop);
		
		
	}
	
	public ItemStack getItem(Material material, String name, List<String> lore){
		
		ItemStack a = new ItemStack(material, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setDisplayName(name);
		aM.setLore(lore);
		a.setItemMeta(aM);	
		
		return a;
		
	}
	
	

}

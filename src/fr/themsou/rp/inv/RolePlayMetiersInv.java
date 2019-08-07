package fr.themsou.rp.inv;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.themsou.inv.UtilsInv;
import fr.themsou.main.main;

public class RolePlayMetiersInv {

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// GLOBAL //////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openMainInv(Player p){
		
		
		Inventory shop = Bukkit.createInventory(null, 6*9, "§4COMPÉTENCES");
		new UtilsInv().setWalls(shop, 6);

/////////////// MINEUR
		shop.setItem(20, UtilsInv.makeItem(Material.DIAMOND_PICKAXE, "§3§lMINEUR",
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
		shop.setItem(22, UtilsInv.makeItem(Material.DIAMOND_AXE, "§3§lBUCHERON",
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
		shop.setItem(24, UtilsInv.makeItem(Material.DIAMOND_HOE, "§3§lFERMIER",
				Arrays.asList("§aVOUS EN ETES A §2" + main.config.getDouble(p.getName() + ".metier.fermier") + "%"," ",
				"§7Gagner des % >§b Vous devez casser des plantes :", "§bpatates, carrotes, blé, melons et pastèques."," ",
				"§7Avantages >§b Vous pourez débloquer une houe qui", "§bpermet de houer, planter et casser plusieurs blocs.",
				"§7-50% >§b Houe en 3×3",
				"§7-60% >§b Houe et place en 3×3",
				"§7-70% >§b Houe, place et casse en 3×3",
				"§7-100% >§b Houe, place et casse en 5×5")));

////////////// CHASSEUR
		shop.setItem(30, UtilsInv.makeItem(Material.DIAMOND_SWORD, "§3§lCHASSEUR",
				Arrays.asList("§aVOUS EN ETES A §2" + main.config.getDouble(p.getName() + ".metier.chasseur") + "%"," ",
				"§7Gagner des % >§b Vous devez tuer des entités."," ",
				"§7Avantages >§b Vous pourez crafter des spawners",
				"§7-50% >§b Spawners à zombie et cochon",
				"§7-60% >§b + Spawners à araignée et vache",
				"§7-70% >§b + Spawners à squelette et mouton",
				"§7-85% >§b + Spawners à creeper et poule",
				"§7-100% >§b + Spawners à enderman et chevaux")));

////////////// ENCHANTEUR
		shop.setItem(32, UtilsInv.makeItem(Material.ENCHANTING_TABLE, "§3§lENCHANTEUR",
				Arrays.asList("§aVOUS EN ETES A §2" + main.config.getDouble(p.getName() + ".metier.enchanteur") + "%"," ",
				"§7Gagner des % >§b Vous devez enchanter des items."," ",
				"§7Avantages >§b Vous pourez crafter des fioles d'XP",
				"§7-[50%-100%] >§b Fiole d'XP × 1 à 64")));

///////////////////// RETOUR
		shop.setItem(49, UtilsInv.makeItem(Material.ARROW, "§4RETOUR", Arrays.asList("RETOUR")));
		p.openInventory(shop);
	}
	public void itemMainClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE){
			if(main.config.getDouble(p.getName() + ".metier.mineur") >= 50.0) openMineurInv(p);
			else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
			
		}else if(e.getCurrentItem().getType() == Material.DIAMOND_AXE){
			if(main.config.getDouble(p.getName() + ".metier.bucheron") >= 50.0) openBucheronInv(p);
			else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
			
		}else if(e.getCurrentItem().getType() == Material.DIAMOND_HOE){
			if(main.config.getDouble(p.getName() + ".metier.fermier") >= 50.0) openFermierInv(p);
			else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
			
		}else if(e.getCurrentItem().getType() == Material.DIAMOND_SWORD){
			if(main.config.getDouble(p.getName() + ".metier.chasseur") >= 50.0) openChasseurInv(p);
			else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
			
		}else if(e.getCurrentItem().getType() == Material.ENCHANTING_TABLE){
			if(main.config.getDouble(p.getName() + ".metier.enchanteur") >= 50.0) openEnchanteurInv(p);
			else p.sendMessage("§cVous devez etre à §450% §cde cette compétence pour avoir accés aux avantages !");
			
		}else if(e.getCurrentItem().getType() == Material.ARROW){
			new RolePlayMainInv().openInv(p);
		}
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// MINEUR //////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openMineurInv(Player p){
		
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
		
		inv.setItem(13, new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1));
		inv.setItem(15, UtilsInv.makeItem(Material.DIAMOND_PICKAXE, "§3§lSuper Pioche", null));
		inv.setItem(26, UtilsInv.makeItem(Material.ARROW, "§4RETOUR", null));
		
		p.openInventory(inv);
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// BUCHERON ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openBucheronInv(Player p){
			
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
		
		inv.setItem(13, new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1));
		inv.setItem(15, UtilsInv.makeItem(Material.DIAMOND_AXE, "§3§lSuper Hache", null));
		inv.setItem(26, UtilsInv.makeItem(Material.ARROW, "§4RETOUR", null));
		
		p.openInventory(inv);
			
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// FERMIER /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openFermierInv(Player p){
		
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
		
		inv.setItem(13, new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1));
		inv.setItem(15, UtilsInv.makeItem(Material.DIAMOND_HOE, "§3§lSuper Houe", null));
		inv.setItem(26, UtilsInv.makeItem(Material.ARROW, "§4RETOUR", null));
		
		p.openInventory(inv);
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// CHASSEUR ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openChasseurInv(Player p){
		
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
		
		inv.setItem(13, new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1));
		inv.setItem(15, UtilsInv.makeItem(Material.SPAWNER, "§3§lCage de spawner", Arrays.asList("cage")));
		
		
		
		ItemStack e = new ItemStack(Material.ROTTEN_FLESH, 1);
		inv.setItem(27, e);
		inv.setItem(28, e);
		inv.setItem(29, e);
		inv.setItem(36, e);
		inv.setItem(37, UtilsInv.makeItem(Material.SPAWNER, "§3§lCage de spawner", Arrays.asList("cage")));
		inv.setItem(38, e);
		inv.setItem(45, e);
		inv.setItem(46, e);
		inv.setItem(47, e);
		
		inv.setItem(40, new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1));
		inv.setItem(42, UtilsInv.makeItem(Material.SPAWNER, "§3§lSpawner de zombie", Arrays.asList("zombie")));
		
		inv.setItem(44, UtilsInv.makeItem(Material.OAK_SIGN, "§3§lAUTRES SPAWNERS :", Arrays.asList("§3Il vous suffit d'entourer","§3la cage du spawner avec des","§3loots de l'entité","§3ou sa nourriture.")));
		inv.setItem(53, UtilsInv.makeItem(Material.ARROW, "§4RETOUR", null));
		
		p.openInventory(inv);
		
	}
///////////////////////////////////////////////////////////t/?/////////////////////////////////////////
////////////////////////////////////////////// ENCHANTEUR /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openEnchanteurInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 3*9, "§4ENCHANTEUR - CRAFTS");
		
		inv.setItem(1, new ItemStack(Material.LAPIS_BLOCK, 1));
		inv.setItem(9, new ItemStack(Material.ROTTEN_FLESH, 1));
		inv.setItem(10, new ItemStack(Material.SPIDER_EYE));
		inv.setItem(11, new ItemStack(Material.BLAZE_POWDER, 1));
		inv.setItem(19, new ItemStack(Material.BONE, 1));
		
		inv.setItem(13, new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1));
		inv.setItem(15, UtilsInv.makeItem(Material.EXPERIENCE_BOTTLE, "§3§lBouteille d'XP", null));
		inv.setItem(26, UtilsInv.makeItem(Material.ARROW, "§4RETOUR", null));
		
		p.openInventory(inv);
		
	}
	
	public void itemOtherClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.ARROW){
			p.closeInventory();
			openMainInv(p);
		}
		
	}
}

package fr.themsou.rp.inv;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.admin.AdminInv;
import fr.themsou.inv.CmdInv;
import fr.themsou.inv.UtilsInv;
import fr.themsou.inv.VipInv;
import fr.themsou.main.main;

public class RolePlayMainInv {
	
	public void openInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4MENU");
		new UtilsInv().setWalls(inv, 6);
		
///////////////////////////////// LINE 0
		
		inv.setItem(0, UtilsInv.makeItem(Material.OAK_SIGN, "§c§lBienvenue en §4Role Play",
				Arrays.asList("§eDiscord : https://discord.tntgun.fr/",
						"§eSite : https://tntgun.fr/",
						"§ee-mail : contact@tntgun.fr")));
		
		inv.setItem(8, UtilsInv.makeItem(Material.OAK_SIGN, "§eAchetez un terrain pour construire",
				Arrays.asList("§evotre maison et progrésser dans les",
						"§ecompétences. N'oubliez pas de vous",
						"§efaire embaucher dans une entreprise !")));
		
///////////////////////////////// LINE 1
		
		inv.setItem(20, UtilsInv.makeItem(Material.EMERALD, "§3§lSHOP", Arrays.asList("§eIci, c'est pour vendre ou acheter","§edes items avec le serveur")));
		
		int score = (main.config.getInt(p.getName() + ".metier.mineur") + main.config.getInt(p.getName() + ".metier.bucheron") + main.config.getInt(p.getName() + ".metier.fermier") + main.config.getInt(p.getName() + ".metier.enchanteur") + main.config.getInt(p.getName() + ".metier.chasseur")) / 5;
		inv.setItem(22, UtilsInv.makeItem(Material.DIAMOND_PICKAXE, "§3§lCOMPÉTENCES",
				Arrays.asList("§eVous pouvez voir ici votre avancement",
						"§edans différentes compétences qui vous",
						"§edébloqueront des outils spéciaux à 100%",
						"§6Votre moyenne : §c" + score + "%")));
		
		inv.setItem(24, UtilsInv.makeItem(Material.NETHER_STAR, "§3§lSHOP DE POINTS", Arrays.asList("§eAchetez des choses","§eavec vos points !")));
		
///////////////////////////////// LINE 2
		
		inv.setItem(30, UtilsInv.makeItem(Material.COMPARATOR, "§3§lCOMMANDES", Arrays.asList("§eListe de toutes les commandes Disponibles")));
		
		inv.setItem(32, UtilsInv.makeItem(Material.DIAMOND, "§3§lVIP", Arrays.asList("§eVoir les avantages vip")));
		
///////////////////////////////// LINE 3
		
		inv.setItem(40, UtilsInv.makeItem(Material.SWEET_BERRIES, "§3§lMINI-JEUX", Arrays.asList("§eVous pouvez ici jouer à","§eplusieurs mini-jeux en RolePlay")));
		
///////////////////////////////// LINE 4
		
		inv.setItem(49, UtilsInv.makeItem(Material.COMMAND_BLOCK, "§3§lESPACE ADMINISTRATEUR", Arrays.asList("§cCet espace est réservé aux administrateur")));
		
		
		
		p.openInventory(inv);
		
	}
	public void itemClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.COMMAND_BLOCK){
			p.closeInventory();
			if(p.isOp()){
				new AdminInv().oppenMainInv(p);
			}else p.sendMessage("§cVous n'êtes pas administrateur");
			
		}else if(e.getCurrentItem().getType() == Material.COMPARATOR){
			p.closeInventory();
			new CmdInv().openInv(p);
			
		}else if(e.getCurrentItem().getType() == Material.NETHER_STAR){
			p.closeInventory();
			new RolePlayShopInv().openPtsInventory(p);
			
		}else if(e.getCurrentItem().getType() == Material.EMERALD){
			p.closeInventory();
			new RolePlayShopInv().openEuroInv(p);
			
		}else if(e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE){
			p.closeInventory();
			new RolePlayMetiersInv().openMainInv(p);
			
		}else if(e.getCurrentItem().getType() == Material.DIAMOND){
			p.closeInventory();
			new VipInv().openInv(p);
			
		}else if(e.getCurrentItem().getType() == Material.SWEET_BERRIES){
			p.closeInventory();
			new RolePlayGamesInv().openInv(p);
			
		}
		
		
	}

}

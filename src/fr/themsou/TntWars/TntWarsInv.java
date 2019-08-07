package fr.themsou.TntWars;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.inv.UtilsInv;
import fr.themsou.main.main;

public class TntWarsInv {

	
	public void openJoinInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 3*9, "§4TntWars - Séléction de map §eVIP");
	
		inv.setItem(9, UtilsInv.makeItem(Material.GRASS_BLOCK, "§3§lMap Défaut", null));
		inv.setItem(10, UtilsInv.makeItem(Material.SAND, "§3§lMap Désert", null));
		inv.setItem(17, UtilsInv.makeItem(Material.NETHER_WART_BLOCK, "§3§lMap Aléatoire", null));
	
		p.openInventory(inv);
	}
	
	public void itemJoinClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.GRASS_BLOCK){

			if(!main.TntWarsCurrent.contains(p)){
				if(!main.TntWarsFille.contains(p) && !main.TntWarsFillea.contains(p) && !main.TntWarsFilleb.contains(p)){
					
					main.TntWarsFillea.add(p);
					System.out.println(p.getName() + " rejoint la fille d'attente du TntWars- a");
					p.sendMessage("§6Vous venez de rejoindre la fille d'attente du §eTntWars");
					p.closeInventory();
					
				}else p.sendMessage("§cVous etes déja dans la fille d'atente");
			}else p.sendMessage("§cVous etes déja dans une partie");
			
		}else if(e.getCurrentItem().getType() == Material.SAND){

			if(!main.TntWarsCurrent.contains(p)){
				if(!main.TntWarsFille.contains(p) && !main.TntWarsFillea.contains(p) && !main.TntWarsFilleb.contains(p)){
					
					main.TntWarsFilleb.add(p);
					System.out.println(p.getName() + " rejoint la fille d'attente du TntWars - b");
					p.sendMessage("§6Vous venez de rejoindre la fille d'attente du §eTntWars");
					p.closeInventory();
					
				}else p.sendMessage("§cVous etes déja dans la fille d'atente");
			}else p.sendMessage("§cVous etes déja dans une partie");
			
		}else if(e.getCurrentItem().getType() == Material.NETHER_WART_BLOCK){

			if(!main.TntWarsCurrent.contains(p)){
				if(!main.TntWarsFille.contains(p) && !main.TntWarsFillea.contains(p) && !main.TntWarsFilleb.contains(p)){
					
					main.TntWarsFille.add(p);
					System.out.println(p.getName() + " rejoint la fille d'attente du TntWars - r");
					p.sendMessage("§6Vous venez de rejoindre la fille d'attente du §eTntWars");
					p.closeInventory();
					
				}else p.sendMessage("§cVous etes déja dans la fille d'atente");
			}else p.sendMessage("§cVous etes déja dans une partie");
		}
		
		
	}
	
	
}

package fr.themsou.rp.inv;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.inv.UtilsInv;

public class RolePlayGamePvpBoxInv {
	
	public void openInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4MINI-JEUX - PVP Box");
		new UtilsInv().setWalls(inv, 6);
		
		inv.setItem(20, UtilsInv.makeItem(Material.SANDSTONE, "§3§lQuoted Sand", null));
		inv.setItem(29, UtilsInv.makeItem(Material.SANDSTONE_STAIRS, "§3§lQuoted Sand (Spectateur)", null));
		
		inv.setItem(21, UtilsInv.makeItem(Material.MAGMA_BLOCK, "§3§lAzRiHEll Arena", null));
		
		p.openInventory(inv);
		
	}
	public void itemClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.SANDSTONE){
			p.closeInventory();
			p.teleport(new Location(Bukkit.getWorld("world"), -219, 35, -1015));
			p.sendMessage("§3Vous venez de vous téléporter à l'arène de §3Quoted Sand.");
			
		}else if(e.getCurrentItem().getType() == Material.SANDSTONE_STAIRS){
			p.closeInventory();
			p.teleport(new Location(Bukkit.getWorld("world"), -219, 40, -998));
			p.sendMessage("§3Vous venez de vous téléporter à l'arène de §3Quoted Sand §ben tans que §3spectateur.");
			
		}else if(e.getCurrentItem().getType() == Material.MAGMA_BLOCK){
			p.closeInventory();
			p.teleport(new Location(Bukkit.getWorld("world"), 2697, 187, -415));
			p.sendMessage("§3Vous venez de vous téléporter à l'arène §3AzRiHEll Arena.");
			
		}
	}	

}

package fr.themsou.rp.inv;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.inv.UtilsInv;

public class RolePlayGamesInv {
	
	public void openInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§4MINI-JEUX");
		new UtilsInv().setWalls(inv, 5);
		
		inv.setItem(20, UtilsInv.makeItem(Material.MINECART, "§3§lMario Kart", Arrays.asList("§eVous pouvez faire une course","§ede bateau sur glace","§cIl faut un Admin/SM pour arbitrer")));
		
		inv.setItem(21, UtilsInv.makeItem(Material.GOLDEN_SWORD, "§3§lDuel (PVP)", Arrays.asList("§eVous vouvez faire un duel avec un joeur.","§eIl existe aussi un mode 2V2")));
		
		inv.setItem(22, UtilsInv.makeItem(Material.CROSSBOW, "§3§lPVP BOX (Octogone)", Arrays.asList("§eVous pouvez vous téléporter à une","§earène ouverte à n'importe qui")));
		
		p.openInventory(inv);
		
	}
	public void itemClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.MINECART){
			p.closeInventory();
			p.teleport(new Location(Bukkit.getWorld("world"), -570, 68, -587));
			p.sendMessage("§bVous venez de vous téléporter au §3circuit Mario Kart §bà §3Bourg Palette");
			p.sendMessage("§cVeuillez contacter un administrateur pour qu'il puisse arbitrer la course !");
			
		}else if(e.getCurrentItem().getType() == Material.GOLDEN_SWORD){
			p.closeInventory();
			new RolePlayGameDuelInv().openInv(p);
			
		}else if(e.getCurrentItem().getType() == Material.CROSSBOW){
			p.closeInventory();
			new RolePlayGamePvpBoxInv().openInv(p);
		}
	}

}

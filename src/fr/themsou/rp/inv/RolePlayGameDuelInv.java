package fr.themsou.rp.inv;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.inv.UtilsInv;
import fr.themsou.main.main;
import fr.themsou.rp.games.DuelGame;
import fr.themsou.rp.games.DuelGameEvents;

public class RolePlayGameDuelInv {
	
	
	
	public void openInv(Player p){
		
		p.openInventory(getInv());
		
	}
	public Inventory getInv(){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§4MINI-JEUX - Duel");
		new UtilsInv().setWalls(inv, 5);
		
		for(int i = 0; i < DuelGame.games.size(); i++){
			
			String status = "Attente de joueurs";
			if(DuelGame.games.get(i).isStarted) status = "En cours";
			
			inv.setItem(20+i, UtilsInv.makeItem(DuelGame.games.get(i).mapMat, "§6" + DuelGame.games.get(i).mapName, Arrays.asList(
					"§cStatus : §6" + status,
					DuelGame.games.get(i).getTeamsString())));
			
		}
		
		inv.setItem(40, UtilsInv.makeItem(Material.CLOCK, "§cActualiser", null));
		
		return inv;
		
	}
	public void itemClicked(main pl, InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.WHITE_STAINED_GLASS_PANE && e.getSlot() >= 20 && e.getSlot() <= 25){
			
			if(!DuelGame.games.get(e.getSlot() - 20).isStarted){
				DuelGame.games.get(e.getSlot() - 20).addPlayer(p);
				
				if(DuelGame.games.get(e.getSlot() - 20).hasEnoughPlayers()){
					new DuelGameEvents().startGame(pl, DuelGame.games.get(e.getSlot() - 20));
				}
				
				e.getInventory().setContents(getInv().getContents());
				
			}else p.sendMessage("§cCette partie a déjà commençée.");
			
		}else if(e.getCurrentItem().getType() == Material.CLOCK){
			
			e.getInventory().setContents(getInv().getContents());
		}
	}

}

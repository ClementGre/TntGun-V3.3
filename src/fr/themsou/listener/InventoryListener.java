package fr.themsou.listener;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

import fr.themsou.BedWars.BedWarsInv;
import fr.themsou.BedWars.BedWarsPNJInv;
import fr.themsou.TntWars.TntWarsInv;
import fr.themsou.admin.AdminInv;
import fr.themsou.inv.CmdInv;
import fr.themsou.inv.HubInv;
import fr.themsou.inv.VipInv;
import fr.themsou.main.main;
import fr.themsou.rp.inv.RolePlayGameDuelInv;
import fr.themsou.rp.inv.RolePlayGamePvpBoxInv;
import fr.themsou.rp.inv.RolePlayGamesInv;
import fr.themsou.rp.inv.RolePlayMainInv;
import fr.themsou.rp.inv.RolePlaySkillsInv;
import fr.themsou.rp.inv.RolePlayShopInv;

public class InventoryListener implements Listener {
	
	public main pl;
	
	public InventoryListener(main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onCloseInventaire(InventoryCloseEvent e){
		
		String titre = e.getView().getTitle();
		
		if(titre.startsWith("§4TntWars - Séléction de l'équipe §7id: ")){
			new TntWarsInv(pl).invListPlayersClosed(e);
			
		}
		
	}
	
	@EventHandler
	public void onCliqueInventaire(InventoryClickEvent e){
		
		Player  p = (Player) e.getWhoClicked();
		String titre = e.getView().getTitle();
		
		if(e.getCurrentItem() == null) return;
		if(e.getCurrentItem().getType() == Material.AIR) return;
		
		
///////////////////////////////////// SELF
	
		if(e.getInventory().getType() == InventoryType.PLAYER){
			
			if(p.getWorld() == Bukkit.getWorld("Bedwars")){
				if(e.getSlotType() == SlotType.ARMOR){
					e.setCancelled(true);
				}
			}
		}
////////////////////////////////////// HUB
		else if(titre.equalsIgnoreCase("§4MODES DE JEUX")){
			e.setCancelled(true);
			new HubInv().itemClicked(e, pl);
		}
////////////////////////////////////// BEDWARS
		else if(titre.equalsIgnoreCase("§4BedWars - Menu")){
			e.setCancelled(true);
			new BedWarsInv().itemMainClicked(e);
			
		}else if(titre.equalsIgnoreCase("§4BedWars - Rejoindre")){
			e.setCancelled(true);
			new BedWarsInv().itemJoinClicked(e, pl);
			
		}else if(titre.equalsIgnoreCase("§4BedWars - Améliorations")){
			e.setCancelled(true);
			new BedWarsPNJInv().itemUpgradesClicked(e);
			
				
		}else if(titre.equalsIgnoreCase("§4BedWars - Boutique")){
			e.setCancelled(true);
			new BedWarsPNJInv().itemShopClicked(e);
		}
////////////////////////////////////// TNTWARS
		else if(titre.equalsIgnoreCase("§4TntWars - Liste des games")){
			e.setCancelled(true);
			new TntWarsInv(pl).itemListGamesClicked(e);
			
		}else if(titre.equalsIgnoreCase("§4TntWars - Création d'une game")){
			e.setCancelled(true);
			new TntWarsInv(pl).itemMakeGamesClicked(e);
			
		}else if(titre.startsWith("§4TntWars - Séléction de l'équipe §7id: ")){
			e.setCancelled(true);
			new TntWarsInv(pl).itemListPlayersClicked(e);
		}
////////////////////////////////////// COMMANDES
		else if(titre.equalsIgnoreCase("§4COMMANDES")){
			e.setCancelled(true);
			new CmdInv().itemClicked(e);
		}
///////////////////////////////////// VIP		
		else if(titre.equalsIgnoreCase("§4VIP")){
			e.setCancelled(true);
			new VipInv().itemClicked(e);
		}	
////////////////////////////////////// ESPACE ADMIN
		else if(titre.contains("§cADMIN - §6")){
			e.setCancelled(true);
			new AdminInv().itemMainClicked(e);
			
		}else if(titre.contains("§cADMIN - Joueur §l§c")){
			e.setCancelled(true);
			new AdminInv().itemPlayerClicked(e);
				
		}else if(titre.contains("§4ADMIN - Sanctioner §l§c")){
			e.setCancelled(true);
			new AdminInv().itemPunishClicked(e);
		}
////////////////////////////////////// RP MAIN
		else if(titre.equalsIgnoreCase("§4MENU")){
			e.setCancelled(true);
			new RolePlayMainInv().itemClicked(e);
		}
////////////////////////////////////// COMPÉTENCES 
			
		else if(titre.equalsIgnoreCase("§4COMPÉTENCES")){
			e.setCancelled(true);
			new RolePlaySkillsInv().itemMainClicked(e);
			
		}else if(titre.equals("§4MINEUR - CRAFTS") || titre.equals("§4BUCHERON - CRAFTS") || titre.equals("§4FERMIER - CRAFTS") || titre.equals("§4CHASSEUR - CRAFTS") || titre.equals("§4ENCHANTEUR - CRAFTS")){
			e.setCancelled(true);
			new RolePlaySkillsInv().itemOtherClicked(e);
		}
//////////////////////////////////// SHOP
		else if(titre.equalsIgnoreCase("§4SHOP")){
			e.setCancelled(true);
			new RolePlayShopInv().itemEuroClicked(e);
			
		}else if(titre.equalsIgnoreCase("§4SHOP DE POINTS")){
			e.setCancelled(true);
			new RolePlayShopInv().itemPtsClicked(e);
		}
//////////////////////////////////////RP GAMES
		else if(titre.equalsIgnoreCase("§4MINI-JEUX")){
			e.setCancelled(true);
			new RolePlayGamesInv().itemClicked(e);
		}
//////////////////////////////////////RP GAMES - PVP BOX
		else if(titre.equalsIgnoreCase("§4MINI-JEUX - PVP Box")){
			e.setCancelled(true);
			new RolePlayGamePvpBoxInv().itemClicked(e);
		}
//////////////////////////////////////RP GAMES - DUEL
		else if(titre.equalsIgnoreCase("§4MINI-JEUX - Duel")){
			e.setCancelled(true);
			new RolePlayGameDuelInv().itemClicked(pl, e);
		}
		
		
	}
	
}

package fr.themsou.inv;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.BedWars.BedWarsInv;
import fr.themsou.methodes.PInfos;
import fr.themsou.rp.claim.Spawns;
import fr.themsou.rp.inv.RolePlayMainInv;

public class CmdInv {
	
	public void openInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4COMMANDES");
		new UtilsInv().setWalls(inv, 6);
		
		
		
		
		if(PInfos.getGame(p).equals("RP")){
			
			inv.setItem(20, UtilsInv.makeItem(Material.PLAYER_HEAD, "§3§lTÉLEPORTATION", Arrays.asList("§bApprenez a vous téléporter a d'autres joueurs...")));
			inv.setItem(22, UtilsInv.makeItem(Material.BOOK, "§3§lDISCUTIONS", Arrays.asList("§bApprenez a envoyer des Messages privées...")));
			inv.setItem(24, UtilsInv.makeItem(Material.EMERALD, "§3§lARGENT", Arrays.asList("§bApprenez a utiliser/récupérer de l'argent")));
			inv.setItem(30, UtilsInv.makeItem(Material.SHULKER_SHELL, "§3§lCLAIMS", Arrays.asList("§bApprenez a gérer vos claims")));
			inv.setItem(32, UtilsInv.makeItem(Material.NETHER_STAR, "§3§lENTREPRISES", Arrays.asList("§bApprenez a créer votre entreprise")));
			
		}else{
			
			inv.setItem(22, UtilsInv.makeItem(Material.BOOK, "§3§lDISCUTIONS", Arrays.asList("§bApprenez a envoyer des Messages privées...")));
			
		}
		
		inv.setItem(40, UtilsInv.makeItem(Material.ARROW, "§4Retour", null));
		
		p.openInventory(inv);
		
		
	}
	
	public void itemClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.PLAYER_HEAD){
			
			p.closeInventory();
			p.sendMessage("§6COMMANDES >§b Voici les commandes sur §3les téléportations");
			p.sendMessage("§c/spawn [" + new Spawns().getSpawns() + "] §b> §3Se 'tp' aux différentes villes");
			p.sendMessage("§c/hub §bou §c/lobby §b> §3Se rendre au hub (choix du mode de jeux)");
			p.sendMessage("§c/home <nom> §b> §3Aller à un de ces 'homes' définis auparavent");
			p.sendMessage("§c/sethome <nom> §b> §3Définir un 'home' §4\u21e7");
			p.sendMessage("§c/delhome <nom> §b> §3Supprimer un de ces homes");
			p.sendMessage("§c/tpa <Joueur> §b> §3Se 'tp' à un autre joueur");
			p.sendMessage("§c/tphere <Joueur> §b> §3'tp' un joueur a soi");
			
		}else if(e.getCurrentItem().getType() == Material.BOOK){
				
			p.closeInventory();
			p.sendMessage("§6COMMANDES >§b Voici les commandes sur §3les discutions");
			p.sendMessage("§c/msg §bou §c/m <joueur> §b> §3Envoyer un message privé");
			p.sendMessage("§c/r <joueur> §b> §3Répondre à un msg privé");
			p.sendMessage("§c/discord <pseudo> §b> §3Lier votre compte Minecraft et Discord");
			
			
		}else if(e.getCurrentItem().getType() == Material.EMERALD){
			
			p.closeInventory();
			p.sendMessage("§6COMMANDES >§b Voici les commandes sur §3l'argent");
			p.sendMessage("§c/money §b> §3Voir son argent");
			p.sendMessage("§c/pay <joueur>/ent:<entrprise> <argent> §b> §3Envoyer de l'argent");
			p.sendMessage("§c/ent §b> §3Gérer son entreprise");
			
			
		}else if(e.getCurrentItem().getType() == Material.SHULKER_SHELL){
			
			p.performCommand("claim");
			p.closeInventory();
			
		}else if(e.getCurrentItem().getType() == Material.NETHER_STAR){
			
			p.performCommand("ent");
			p.closeInventory();
			
		}else if(e.getCurrentItem().getType() == Material.ARROW){
		
			p.closeInventory();
			p.closeInventory();
			if(PInfos.getGame(p).equals("RP")){
				new RolePlayMainInv().openInv(p);
			}else if(PInfos.getGame(p).equals("BedWars")){
				new BedWarsInv().openMainInv(p);
			}else{
				new HubInv().openInv(p);
			}
			
			
		}
		
	}

}

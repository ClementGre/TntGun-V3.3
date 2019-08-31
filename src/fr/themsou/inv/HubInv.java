package fr.themsou.inv;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.BedWars.BedWarsInv;
import fr.themsou.BedWars.getteam;
import fr.themsou.BedWars.join;
import fr.themsou.TntWars.TntWarsGameEvents;
import fr.themsou.TntWars.TntWarsInv;
import fr.themsou.diffusion.api.roles;
import fr.themsou.main.main;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.Spawns;
import fr.themsou.rp.games.DuelGame;
import fr.themsou.rp.inv.RolePlayGamesInv;

public class HubInv {
	
	public void openInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4MODES DE JEUX");
		new UtilsInv().setWalls(inv, 6);
		
		
		inv.setItem(20, UtilsInv.makeItem(Material.TNT, "§3§lTntWars", Arrays.asList("§eConstruisez un canon à TNT,","§eet tuez votre adversaire !")));
		inv.setItem(22, UtilsInv.makeItem(Material.BRICKS, "§3§lRolePlay", Arrays.asList("§eExplorez un monde vaste,","§eachetez un terrain,","§evendez vos ressources.","§eEt devenez le plus riche !")));
		inv.setItem(24, UtilsInv.makeItem(Material.WHITE_BED, "§3§lBedWars", Arrays.asList("§eComme dans le BedWars que","§evous connaissez, vous devez détruire","§eles lits des adversaires")));
		
		inv.setItem(31, UtilsInv.makeItem(Material.SWEET_BERRIES, "§3§lMini-Jeux du RP", Arrays.asList("§eDécouvrez plusieurs mini-jeux,","§edisponibles en RolePlay")));
		
		p.openInventory(inv);
		
	}
	
	public void itemClicked(InventoryClickEvent e, main pl){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.BRICKS){
			
			main.config.set(p.getName() + ".rp.lastday", new realDate().getRealDate());
			if(main.config.contains(p.getName() + ".discord")){
				roles Croles = new roles();
				Croles.addRole("RolePlay Player", main.config.getString(p.getName() + ".discord"));
			}
			
			p.sendMessage("§6Bienvenue en RolePlay, ici, vous devrez trouver des ressources pour ensuite les vendre dans le menu principal (/?) ou les donner à votre patron pour les vendre via votre entreprise (/ent). Vous pourrez ensuite acheter des claims en cliquant sur les pancartes [Acheter] des spawn. Vous avez aussi comme défi d'arriver à 100% dans chacune des compétences afin de débloquer des outils spéciaux qui vous seront nécessaires.");
			
			new TntWarsGameEvents().PlayerLeave(p);
			if(new getteam().getplayerteam(p) != 0) new join().leaveBedWars(p);
			
			p.closeInventory();
			p.setGameMode(GameMode.SURVIVAL);
			
			Spawns CSpawns = new Spawns();
			p.teleport(CSpawns.getSpawnSpawn(CSpawns.getSpawns().split("/")[new Random().nextInt(CSpawns.getSpawns().split("/").length)]));
			p.sendMessage("§bTéléportation à un spawn §3aléatoire");
			p.sendMessage("§6Vous pouvez aussi vous téléporter aux autres villes avec §c/spawn <" + new Spawns().getSpawns() + ">");
			title.sendTitle(p, "§bBienvenue en§3 Role Play", "§6Faites /? pour ouvrir le menu", 60);
			
			
			p.setBedSpawnLocation(p.getLocation());
		
		}if(e.getCurrentItem().getType() == Material.SWEET_BERRIES){
			
			p.closeInventory();
			new RolePlayGamesInv().openInv(p);
		
		}if(e.getCurrentItem().getType() == Material.TNT){
		
			
			p.closeInventory();
			new TntWarsInv(pl).openListGamesInv(p);
			
			DuelGame.removePlayerInAllGames(p);
			if(new getteam().getplayerteam(p) != 0) new join().leaveBedWars(p);
			
		}if(e.getCurrentItem().getType() == Material.WHITE_BED){
			
			DuelGame.removePlayerInAllGames(p);
			new TntWarsGameEvents().PlayerLeave(p);
			
			getteam CGetteam = new getteam();
			join Cjoin = new join();
			
			if(CGetteam.getplayerteam(p) == 0 || main.config.getInt("bedwars.list.status") == 0 || main.config.getInt("bedwars.list.status") == 5){
				
				new BedWarsInv().openJoinInv(p);
				
			}else Cjoin.joinBedWars(p, pl);
			
			
			
			
		}
		
		
	}
}

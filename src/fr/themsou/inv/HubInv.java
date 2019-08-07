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
import fr.themsou.TntWars.RunParty;
import fr.themsou.TntWars.TntWarsInv;
import fr.themsou.commands.GradeCmd;
import fr.themsou.diffusion.api.roles;
import fr.themsou.main.main;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.Spawns;

public class HubInv {
	
	public void openInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§4MODES DE JEUX");
		new UtilsInv().setWalls(inv, 5);
		
		inv.setItem(20, UtilsInv.makeItem(Material.BRICKS, "§3§lRolePlay", Arrays.asList("§eExplorez un monde vaste,","§eachetez un terrain,","§evendez vos ressources.","§eEt devenez le plus riche !")));
		inv.setItem(22, UtilsInv.makeItem(Material.TNT, "§3§lTntWars", Arrays.asList("§eConstruisez un canon à TNT,","§eet tuez votre adversaire !")));
		inv.setItem(24, UtilsInv.makeItem(Material.WHITE_BED, "§3§lBedWars", Arrays.asList("§eComme dans le BedWars que","§evous connaissez, vous devez détruire","§eles lits des adversaires")));
		
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
			
			if(main.TntWarsFillea.contains(p)){
				main.TntWarsFillea.remove(p);
				p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
				System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
			}
			if(main.TntWarsFilleb.contains(p)){
				main.TntWarsFilleb.remove(p);
				p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
				System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
			}
			if(main.TntWarsFille.contains(p)){
				main.TntWarsFille.remove(p);
				p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
				System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
			}
			
			if(new getteam().getplayerteam(p) != 0) new join().leaveBedWars(p);
			
			p.closeInventory();
			p.setGameMode(GameMode.SURVIVAL);
			
			Spawns CSpawns = new Spawns();
			p.teleport(CSpawns.getSpawnSpawn(CSpawns.getSpawns().split("/")[new Random().nextInt(CSpawns.getSpawns().split("/").length)]));
			p.sendMessage("§bTéléportation à un spawn §3aléatoire");
			p.sendMessage("§6Vous pouvez aussi vous téléporter aux autres villes avec §c/spawn <" + new Spawns().getSpawns() + ">");
			title.sendTitle(p, "§bBienvenue en§3 Role Play", "§6Faites /? pour ouvrir le menu", 60);
			
			RunParty CRunParty = new RunParty();
			CRunParty.Playerquit(p);
			
			p.setBedSpawnLocation(p.getLocation());
		
		}if(e.getCurrentItem().getType() == Material.TNT){
		
			
			p.closeInventory();
			if(new GradeCmd().getPlayerPermition(p.getName()) >= 2){
				
				new TntWarsInv().openJoinInv(p);
				
			}else{
				
				if(!main.TntWarsCurrent.contains(p)){
					if(!main.TntWarsFille.contains(p) && !main.TntWarsFillea.contains(p) && !main.TntWarsFilleb.contains(p)){
						main.TntWarsFille.add(p);
						System.out.println(p.getName() + " rejoint la fille d'attente du TntWars - r");
						p.sendMessage("§6Vous venez de rejoindre la fille d'attente du §eTntWars");
					}else p.sendMessage("§cVous etes déja dans la fille d'atente");
				}else p.sendMessage("§cVous etes déja dans une partie");
			}
			
			if(new getteam().getplayerteam(p) != 0) new join().leaveBedWars(p);
			
		}if(e.getCurrentItem().getType() == Material.WHITE_BED){
			
			
			
			if(main.TntWarsFillea.contains(p)){
				main.TntWarsFillea.remove(p);
				p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
				System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
			}
			if(main.TntWarsFilleb.contains(p)){
				main.TntWarsFilleb.remove(p);
				p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
				System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
			}
			if(main.TntWarsFille.contains(p)){
				main.TntWarsFille.remove(p);
				p.sendMessage("§cVous venez de quitter la fille d'atende tu TntWars");
				System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
			}
			
			RunParty CRunParty = new RunParty();
			CRunParty.Playerquit(p);
			
			
			getteam CGetteam = new getteam();
			join Cjoin = new join();
			
			if(CGetteam.getplayerteam(p) == 0 || main.config.getInt("bedwars.list.status") == 0 || main.config.getInt("bedwars.list.status") == 5){
				
				new BedWarsInv().openJoinInv(p);
				
			}else Cjoin.joinBedWars(p, pl);
			
			
			
			
		}
		
		
	}
}

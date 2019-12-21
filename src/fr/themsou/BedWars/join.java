package fr.themsou.BedWars;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import fr.themsou.main.main;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.title;

public class join {

	public void leaveBedWars(Player p){
		
		if(main.config.getInt("bedwars.list.status") == 0 || main.config.getInt("bedwars.list.status") == 5){
			
			int team = new getteam().getplayerteam(p);
			
			if(main.config.getString("bedwars.list.teams." + team + ".owners").split(",").length == 1){
				main.config.set("bedwars.list.teams." + team + ".owners", null);
			}else{
				main.config.set("bedwars.list.teams." + team + ".owners", main.config.getString("bedwars.list.teams." + team + ".owners").replace(p.getName() + ",", "").replace("," + p.getName(), ""));
			}
			
			p.sendMessage("§6Vous venez de quitter la file d'atentte du §eBedWars.");
			
		}
		
		
		
	}
	
	
	@SuppressWarnings("deprecation")
	public void joinBedWars(Player p, main pl){
		
		main.config.set(p.getName() + ".bedwars.lastday", new realDate().getRealDate().getDate());
		if(main.config.contains(p.getName() + ".discord")){
			Member member = main.guild.getMemberByTag(main.config.getString(p.getName() + ".discord"));
			if(member != null){
				Role role = main.guild.getRolesByName("BedWars Player", false).get(0);
				main.guild.addRoleToMember(member, role).queue();
			};
		}
		
		
		
		getteam Cgetteam = new getteam();
		int team = Cgetteam.getplayerteam(p);
		
		if(team != 0){
			
			int x = main.configuration.getInt("bedwars.teams." + team + ".x");
			int z = main.configuration.getInt("bedwars.teams." + team + ".z");
			
			if(main.config.getInt("bedwars.list.teams." + team + ".bed") == 1){
				
					
				System.out.println(p.getName() + " rejoint le BedWars (équipe n°" + team + ")");
				p.teleport(new Location(Bukkit.getWorld("BedWars"), x, 100, z));
				p.sendMessage("§6Bienvenue en BedWars !!");
				title.sendTitle(p, "§cBienvenue en BedWars !", "§6Protégez votre lit.", 60);
				
				p.getInventory().clear();
				p.setGameMode(GameMode.SURVIVAL);
				
			}else{
				
				System.out.println(p.getName() + " rejoint le BedWars en spectateur (équipe n°" + team + ")");
				p.teleport(new Location(Bukkit.getWorld("BedWars"), 0, 110, 0));
				title.sendTitle(p, "§cVous n'avez plus de lit !", "§6Regardez la partie.", 60);
				
				p.getInventory().clear();
				p.setGameMode(GameMode.SPECTATOR);
					
			}
			
		}
			
		
		
	}
	
	@SuppressWarnings("deprecation")
	public void joinFirstBedWars(Player p, main pl, int team){
		
		main.config.set(p.getName() + ".bedwars.lastday", new realDate().getRealDate().getDate());
		if(main.config.contains(p.getName() + ".discord")){
			Member member = main.guild.getMemberByTag(main.config.getString(p.getName() + ".discord"));
			if(member != null){
				Role role = main.guild.getRolesByName("BedWars Player", false).get(0);
				main.guild.addRoleToMember(member, role).queue();
			};
		}
		
		int cteam = new getteam().getplayerteam(p);
		if(cteam != 0){
			
			if(main.config.getString("bedwars.list.teams." + cteam + ".owners").split(",").length == 1){
				main.config.set("bedwars.list.teams." + cteam + ".owners", null);
			}else{
				main.config.set("bedwars.list.teams." + cteam + ".owners", main.config.getString("bedwars.list.teams." + cteam + ".owners").replace(p.getName() + ",", "").replace("," + p.getName(), ""));
			}
			
			p.sendMessage("§6Vous venez de quitter la file d'atentte du §eBedWars.");
		}
		
		
		if(team != 0){
			
			if(main.config.getString("bedwars.list.teams." + team + ".owners") != null){
				if(main.config.getString("bedwars.list.teams." + team + ".owners").split(",").length >= 2)  return;
			}
			
			
			int x = main.configuration.getInt("bedwars.teams." + team + ".x");
			int z = main.configuration.getInt("bedwars.teams." + team + ".z");
			
			if(main.config.getString("bedwars.list.teams." + team + ".owners") == null){
				main.config.set("bedwars.list.teams." + team + ".owners", p.getName());
			}else{
				main.config.set("bedwars.list.teams." + team + ".owners", main.config.getString("bedwars.list.teams." + team + ".owners") + "," + p.getName());
			}
			
			
			if(main.config.getInt("bedwars.list.status") != 0 && main.config.getInt("bedwars.list.status") != 5){
				
				if(main.config.getInt("bedwars.list.teams." + team + ".bed") == 1){
					
					System.out.println(p.getName() + " rejoint le BedWars Pour la première fois dans l'équipe n°" + team);
					
					p.teleport(new Location(Bukkit.getWorld("BedWars"), x, 100, z));
					p.sendMessage("§6Bienvenue en BedWars !!");
					title.sendTitle(p, "§bBienvenue en BedWars !", "§6Protégez votre lit.", 60);
					
					p.setGameMode(GameMode.SURVIVAL);
					
				}else p.sendMessage("§cCette équipe n'a plus de lit !");
				
				
			}
			
		}else{
			
			if(main.config.getInt("bedwars.list.status") != 0 && main.config.getInt("bedwars.list.status") != 5){
				
				System.out.println(p.getName() + " rejoint le BedWars Pour la première fois en spectateur (choix)");
				p.teleport(new Location(Bukkit.getWorld("BedWars"), 0, 110, 0));
				
		        p.setGameMode(GameMode.SPECTATOR);
				
			}
			
			
		}
		
		
		
	}
}
		

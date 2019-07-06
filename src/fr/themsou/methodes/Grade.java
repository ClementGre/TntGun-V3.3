package fr.themsou.methodes;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.themsou.discord.Roles;
import fr.themsou.main.main;

public class Grade{

	
	// 1 - Joueur
	// 2 - VIP - IRL - YT
	// 3 - Modo - DEV-WEB
	// 4 - Constructeur
	// 5 - Super-Modo
	// 6 - Admin / Dev-MC
	
	public void changePlayerGrade(Player sender, String cible, String grade){
		
		if(getPlayerPermition(sender.getName()) != 6){
			sender.sendMessage("§cVous n'avez pas la permission de modifier les grades !");
			return;
		}
		
		String grades = "";
		
		Set<String> section = main.configuration.getConfigurationSection("grades").getKeys(false);	
		String item[] = section.toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
		for(int i = 1; i <= section.size(); i++){
			int number = i - 1;
			
			grades += item[number] + "/";
			
			if(grade.equalsIgnoreCase(item[number])){
				
				
				main.config.set(cible + ".grade", item[number]);
				Bukkit.broadcastMessage("§3" + cible + "§b a bien obtenu son grade " + main.configuration.getString("grades." + item[number] + ".gradecolor") + item[number]);
				
				if(main.config.contains(cible + ".discord")){
					new Roles().removeRole(main.config.getString(cible + ".discord"));
					if(!item[number].equals("Joueur")) new Roles().setRole(cible);
				}
				int perm = main.configuration.getInt("grades." + item[number] + ".perm");
				if(perm >= 4){
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "op " + cible);
				}else{
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "deop " + cible);
				}
				
				return;
			}
			
		}
		
		sender.sendMessage("§c/grade <Joueur> <" + grades + ">");
		
		
		
			
	}
	
	public void changePlayerGradeWithConsole(String cible, String grade){
		
		
		String grades = "";
		
		Set<String> section = main.configuration.getConfigurationSection("grades").getKeys(false);	
		String item[] = section.toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
		for(int i = 1; i <= section.size(); i++){
			int number = i - 1;
			
			grades += item[number] + "/";
			
			if(grade.equalsIgnoreCase(item[number])){
				
				
				main.config.set(cible + ".grade", item[number]);
				
				Bukkit.broadcastMessage("§3" + cible + "§b a bien obtenu son grade " + main.configuration.getString("grades." + item[number] + ".gradecolor") + item[number]);
				
				if(main.config.contains(cible + ".discord")){
					new Roles().removeRole(main.config.getString(cible + ".discord"));
					new Roles().setRole(cible);
				}
				int perm = main.configuration.getInt("grades." + item[number] + ".perm");
				if(perm >= 4){
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "op " + cible);
				}else{
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "deop " + cible);
				}
				
				return;
			}
			
		}
		
		Bukkit.getConsoleSender().sendMessage("§c/grade <Joueur> <" + grades + ">");
		
	}
	
	public int getPlayerPermition(String playerName){
		
		String playerGrade = main.config.getString(playerName + ".grade");
		return main.configuration.getInt("grades." + playerGrade + ".perm");
		
	}
	
}

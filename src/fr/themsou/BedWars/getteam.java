package fr.themsou.BedWars;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.themsou.main.main;

public class getteam {
	
	public int getplayerteam(Player p){
		
		
		int i = 4;
		for(i = 4; i >= 1; i--){
			
			if(main.config.getString("bedwars.list.teams." + i + ".owners") != null){
				if(main.config.getString("bedwars.list.teams." + i + ".owners").split(",")[0].equals(p.getName())){
					return i;
				}else if(main.config.getString("bedwars.list.teams." + i + ".owners").split(",").length >= 2){
					if(main.config.getString("bedwars.list.teams." + i + ".owners").split(",")[1].equals(p.getName())){
						return i;
					}
				}
			}
		}
		
		return 0;
	}

	public Color getTeamColor(Player p) {
		
		return getTeamColor(getplayerteam(p));
	}
	
	public Color getTeamColor(int team) {
		
		
		if(team != 0){
			
			if(team == 1){
				return Color.RED;
			}else if(team == 2){
				return Color.BLUE;
			}else if(team == 3){
				return Color.GREEN;
			}else{
				return Color.YELLOW;
			}
			
		}else return null;
	}

	public ChatColor getTeamChatColor(int team) {
		
		if(team != 0){
			
			if(team == 1){
				return ChatColor.RED;
			}else if(team == 2){
				return ChatColor.BLUE;
			}else if(team == 3){
				return ChatColor.DARK_GREEN;
			}else{
				return ChatColor.YELLOW;
			}
			
		}else return null;
	}
	
	public Material getTeamWoolColor(int team) {
		
		if(team != 0){
			
			if(team == 1){
				return Material.RED_WOOL;
			}else if(team == 2){
				return Material.BLUE_WOOL;
			}else if(team == 3){
				return Material.GREEN_WOOL;
			}else{
				return Material.YELLOW_WOOL;
			}
			
		}else return Material.WHITE_WOOL;
		
	}public Material getTeamConcreteColor(int team) {
		
		if(team != 0){
			
			if(team == 1){
				return Material.RED_CONCRETE;
			}else if(team == 2){
				return Material.BLUE_CONCRETE;
			}else if(team == 3){
				return Material.GREEN_CONCRETE;
			}else{
				return Material.YELLOW_CONCRETE;
			}
			
		}else return Material.WHITE_WOOL;
		
	}public Material getTeamPaneColor(int team) {
		
		if(team != 0){
			
			if(team == 1){
				return Material.RED_STAINED_GLASS_PANE;
			}else if(team == 2){
				return Material.BLUE_STAINED_GLASS_PANE;
			}else if(team == 3){
				return Material.GREEN_STAINED_GLASS_PANE;
			}else{
				return Material.YELLOW_STAINED_GLASS_PANE;
			}
			
		}else return Material.WHITE_WOOL;
	}
	
	public String getTeamStringColor(int team) {
		
		if(team != 0){
			
			if(team == 1){
				return "Rouge";
			}else if(team == 2){
				return "Bleu";
			}else if(team == 3){
				return "Vert";
			}else{
				return "Jaune";
			}
			
		}else return null;
	}
	public String getTeamEnglishColor(int team) {
		
		if(team != 0){
			
			if(team == 1){
				return "red";
			}else if(team == 2){
				return "blue";
			}else if(team == 3){
				return "green";
			}else{
				return "yellow";
			}
			
		}else return null;
	}

}

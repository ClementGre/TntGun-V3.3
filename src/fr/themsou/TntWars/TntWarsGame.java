package fr.themsou.TntWars;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import fr.themsou.main.main;

public class TntWarsGame {
	
	public static ArrayList<TntWarsGame> games = new ArrayList<>();
	public static BossBar bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SEGMENTED_20, new BarFlag[0]);
	public static float bossBarGames = 0;
	
	public Player team1Player1 = null;
	public Player team1Player2 = null;
	public Player team2Player1 = null;
	public Player team2Player2 = null;
	public int map = -1;
	public int id = new Random().nextInt(1000);
	public boolean isDouble = false;
	public boolean isStarted = false;
	public Location team1Spawn = null;
	public Location team2Spawn = null;
	
	public TntWarsGame(int map, boolean isDouble){
		this.map = map;
		this.isDouble = isDouble;
		games.add(this);
		new TntWarsGameEvents().updateBar();
	}
	
	public String getTeamString(int team){
		
		String toReturn = "";
		
		if(team == 1){
			
			if(team1Player1 == null){
				toReturn += "§7Aucun joueur";
			}else toReturn += "§c" + team1Player1.getName();
			
			if(isDouble){
				toReturn += " §6& ";
				
				if(team1Player2 == null){
					toReturn += "§7Aucun joueur";
				}else toReturn += "§c" + team1Player2.getName();
			}
			
		}else{
			
			if(team2Player1 == null){
				toReturn += "§7Aucun joueur";
			}else toReturn += "§c" + team2Player1.getName();
			
			if(isDouble){
				toReturn += " §6& ";
				
				if(team2Player2 == null){
					toReturn += "§7Aucun joueur";
				}else toReturn += "§c" + team2Player2.getName();
			}
			
		}
		return toReturn;
		
	}
	
	public static TntWarsGame getInstanceViaID(int id){
		
		for(int i = 0; i < TntWarsGame.games.size(); i++){
			
			if(TntWarsGame.games.get(i).id == id){
				return TntWarsGame.games.get(i);
			}
		}
		return null;
		
	}
	
	public static TntWarsGame getInstanceViaPlayer(Player p){
		
		for(int i = 0; i < TntWarsGame.games.size(); i++){
			
			if(TntWarsGame.games.get(i).team1Player1 != null){
				if(TntWarsGame.games.get(i).team1Player1.getName().equals(p.getName())){
					return TntWarsGame.games.get(i);
				}
			}
			if(TntWarsGame.games.get(i).team1Player2 != null){
				if(TntWarsGame.games.get(i).team1Player2.getName().equals(p.getName())){
					return TntWarsGame.games.get(i);
				}
			}
			if(TntWarsGame.games.get(i).team2Player1 != null){
				if(TntWarsGame.games.get(i).team2Player1.getName().equals(p.getName())){
					return TntWarsGame.games.get(i);
				}
			}
			if(TntWarsGame.games.get(i).team2Player2 != null){
				if(TntWarsGame.games.get(i).team2Player2.getName().equals(p.getName())){
					return TntWarsGame.games.get(i);
				}
			}
		}
		
		return null;
		
	}
	
	public String getColorStringPlayer(int team, int player){
		
		Player p = null;
		
		if(team == 1){
			if(player == 1)
				p = team1Player1;
			else
				p = team1Player2;
		}else{
			if(player == 1)
				p = team2Player1;
			else
				p = team2Player2;
		}
		
		return getColorStringPlayer(p);
		
	}
	
	public String getColorStringPlayer(Player p){
		
		if(p == null){
			return "§7Place libre";
		}else{
			return "§c" + p.getName();
		}
		
	}
	
	public ChatColor getTeamColor(Player p){
		
		int team = 0;
		
		if(team1Player1 != null){
			if(team1Player1.getName() == p.getName())
				team = 1;
		}if(team1Player2 != null){
			if(team1Player2.getName() == p.getName())
				team = 1;
		}if(team2Player1 != null){
			if(team2Player1.getName() == p.getName())
				team = 2;
		}if(team2Player2 != null){
			if(team2Player2.getName() == p.getName())
				team = 2;
		}
		
		if(team == 1){
			return ChatColor.DARK_AQUA;
		}else if(team == 2){
			return ChatColor.RED;
		}
		
		return ChatColor.GRAY;
		
	}
	
	public Material getMapMaterial(){
		
		if(map == -1){
			return Material.HOPPER;
		}
		
		return Material.valueOf(main.configuration.getString("tntwars.maps." + map + ".item"));
		
	}
	public String getMapName(){
		
		if(map == -1){
			return "Aléatoire";
		}
		
		return main.configuration.getString("tntwars.maps." + map + ".name");
		
	}
	public String getMapWall(){
		
		if(map == -1){
			return "Inconnue";
		}
		
		return main.configuration.getString("tntwars.maps." + map + ".wall");
		
	}
	public String getGameMode() {
		
		if(isDouble){
			return "2V2";
		}
		return "1V1";
	}
	
	public void addTeam1Player(Player p){
		
		if(getInstanceViaPlayer(p) != null){
			removePlayerInAllGames(p);
			return;
		}
		
		removePlayerInAllGames(p);
		
		if(team1Player1 == null){
			team1Player1 = p;
			p.sendMessage("§6Vous venez de §cRejoindre §6une file d'attente du §cTntWars");
		}else if(team1Player2 == null && isDouble){
			team1Player2 = p;
			p.sendMessage("§6Vous venez de §cRejoindre §6une file d'attente du §cTntWars");
		}
	}
	public void addTeam2Player(Player p){
		
		if(getInstanceViaPlayer(p) != null){
			removePlayerInAllGames(p);
			return;
		}
		
		removePlayerInAllGames(p);
		
		if(team2Player1 == null){
			team2Player1 = p;
			p.sendMessage("§6Vous venez de §cRejoindre §6une file d'attente du §cTntWars");
		}else if(team2Player2 == null && isDouble){
			team2Player2 = p;
			p.sendMessage("§6Vous venez de §cRejoindre §6une file d'attente du §cTntWars");
		}
	}
	
	public static void removePlayerInAllGames(Player p){
		
		while(getInstanceViaPlayer(p) != null){
			getInstanceViaPlayer(p).removePlayer(p);
			p.sendMessage("§6Vous venez de §cQuitter §6une file d'attente du §cTntWars");
		}
		
	}
	public void removePlayer(Player p){
		
		if(team1Player1 != null){
			if(team1Player1.getName() == p.getName()){
				team1Player1 = null;
			}
		}
		if(team1Player2 != null){
			if(team1Player2.getName() == p.getName()){
				team1Player2 = null;
			}
		}
		if(team2Player1 != null){
			if(team2Player1.getName() == p.getName()){
				team2Player1 = null;
			}
		}
		if(team2Player2 != null){
			if(team2Player2.getName() == p.getName()){
				team2Player2 = null;
			}
		}
		
	}
	
	public void destroyIfCan(){
		
		if(team1Player1 == null && team1Player2 == null && team2Player1 == null && team2Player2 == null){
			destroy();
		}
		
	}
	
	public boolean canStart(){
		
		if(team1Player1 != null && team2Player1 != null){
			
			if(isDouble){
				if(team1Player2 != null && team2Player2 != null){
					return true;
				}
			}else return true;
		}
		return false;
	}
	public boolean canContinue(){
		
		if((team1Player1 != null || team1Player2 != null) && (team2Player1 != null || team2Player2 != null)){
			return true;
		}
		return false;
	}
	public boolean canContinueWiout(Player p){
		
		boolean toReturn = false;
		
		if(getInstanceViaPlayer(p) != null){
			if(getInstanceViaPlayer(p).id == id){
				
				int team = getPlayerTeam(p);
				removePlayer(p);
				
				if((team1Player1 != null || team1Player2 != null) && (team2Player1 != null || team2Player2 != null)){
					toReturn = true;
				}
				
				if(team == 1){
					if(team1Player1 == null){
						team1Player1 = p;
					}else if(team1Player2 == null && isDouble){
						team1Player2 = p;
					}
				}else{
					if(team2Player1 == null){
						team2Player1 = p;
					}else if(team2Player2 == null && isDouble){
						team2Player2 = p;
					}
				}
				
				return toReturn;
				
			}
		}
		
		
		return toReturn;
	}
	
	public Player[] getPlayers(){
		
		if(isDouble){
			Player[] players = {team1Player1, team1Player2, team2Player1, team2Player2};
			return players;
		}else{
			Player[] players = {team1Player1, team2Player1};
			return players;
		}
	}
	public Player[] getPlayersTeam1(){
		
		if(isDouble){
			Player[] players = {team1Player1, team1Player2};
			return players;
		}else{
			Player[] players = {team1Player1};
			return players;
		}
	}
	public Player[] getPlayersTeam2(){
	
		if(isDouble){
			Player[] players = {team2Player1, team2Player2};
			return players;
		}else{
			Player[] players = {team2Player1};
			return players;
		}
	}
	public int getPlayerTeam(Player p){
		
		if(team1Player1 != null){
			if(team1Player1.getName() == p.getName()){
				return 1;
			}
		}
		if(team1Player2 != null){
			if(team1Player2.getName() == p.getName()){
				return 1;
			}
		}
		if(team2Player1 != null){
			if(team2Player1.getName() == p.getName()){
				return 2;
			}
		}
		if(team2Player2 != null){
			if(team2Player2.getName() == p.getName()){
				return 2;
			}
		}
		return 0;
	}
	
	public void destroy(){
		
		for(int i = 0; i < TntWarsGame.games.size(); i++){
			
			if(TntWarsGame.games.get(i).id == id){
				TntWarsGame.games.remove(i);
			}
		}
		new TntWarsGameEvents().updateBar();
	}
	
}

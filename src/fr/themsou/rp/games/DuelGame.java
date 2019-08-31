package fr.themsou.rp.games;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DuelGame {
	
	public static final DuelGame ARVENIA_1 = new DuelGame("Arvenia", Material.CAMPFIRE, new Location(Bukkit.getWorld("world"), 145, 73, -119), new Location(Bukkit.getWorld("world"), 145, 73, -119));
	public static List<DuelGame> games = Arrays.asList(ARVENIA_1);
	
	public Location loc;
	public Location specLoc;
	public String mapName;
	public Material mapMat;
	
	public Player player1 = null;
	public Player player2 = null;
	
	public int id = new Random().nextInt(1000);
	public boolean isStarted = false;
	
	private DuelGame(String name, Material material, Location loc, Location specLoc){
		this.loc = loc;
		this.specLoc = specLoc;
		this.mapName = name;
		this.mapMat = material;
	}
	
	
	public static DuelGame getInstanceViaID(int id){
		
		for(int i = 0; i < DuelGame.games.size(); i++){
			if(DuelGame.games.get(i).id == id){
				return DuelGame.games.get(i);
			}
		}
		return null;
		
	}
	
	public static DuelGame getStartedInstanceViaPlayer(Player p){
		
		for(int i = 0; i < DuelGame.games.size(); i++){
			
			if(!DuelGame.games.get(i).isStarted) continue;
			
			if(DuelGame.games.get(i).player1 != null){
				if(DuelGame.games.get(i).player1.getName().equals(p.getName())){
					return DuelGame.games.get(i);
				}
			}
			if(DuelGame.games.get(i).player2 != null){
				if(DuelGame.games.get(i).player2.getName().equals(p.getName())){
					return DuelGame.games.get(i);
				}
			}
		}
		return null;
		
	}
	
	public static void removePlayerInAllStartedGames(Player p){
		
		while(getStartedInstanceViaPlayer(p) != null){
			getStartedInstanceViaPlayer(p).removePlayer(p);
		}
	}
	public static DuelGame getInstanceViaPlayer(Player p){
		
		for(int i = 0; i < DuelGame.games.size(); i++){
			
			if(DuelGame.games.get(i).player1 != null){
				if(DuelGame.games.get(i).player1.getName().equals(p.getName())){
					return DuelGame.games.get(i);
				}
			}
			if(DuelGame.games.get(i).player2 != null){
				if(DuelGame.games.get(i).player2.getName().equals(p.getName())){
					return DuelGame.games.get(i);
				}
			}
		}
		return null;
		
	}
	public static void removePlayerInAllGames(Player p){
		
		while(getInstanceViaPlayer(p) != null){
			getInstanceViaPlayer(p).removePlayer(p);
			p.sendMessage("§6Vous venez de §cQuitter §6une file d'attente du §cDuel");
		}
		
	}
	
	public String getTeamsString(){
		
		String toReturn = "";
		
		if(player1 != null) toReturn += "§c" + player1.getName();
		else toReturn += "§7Aucun joueur";
		
		toReturn += " §7VS ";
		
		if(player2 != null) toReturn += "§c" + player2.getName();
		else toReturn += "§7Aucun joueur";
		
		return toReturn;
		
	}
	
	public void addPlayer(Player p) {
		
		removePlayerInAllGames(p);
		
		if(player1 == null){
			player1 = p;
			
		}else if(player2 == null){
			player2 = p;
		}
		p.sendMessage("§6Vous venez de §cRejoindre §6une file d'attente du §cDuel");
	}
	
	public void removePlayer(Player p){
		
		if(player1 != null){
			if(player1.getName() == p.getName()){
				player1 = null;
			}
		}
		if(player2 != null){
			if(player2.getName() == p.getName()){
				player2 = null;
			}
		}
		
	}
	
	public boolean hasEnoughPlayers(){
		
		if(player1 != null && player2 != null){
			return true;
		}
		return false;
	}
	public int getPlayerTeam(Player p){
		
		if(player1 != null){
			if(player1.getName() == p.getName()){
				return 1;
			}
		}
		if(player2 != null){
			if(player2.getName() == p.getName()){
				return 2;
			}
		}
		return 0;
	}

	
	
	public void resset(){
		
		player1 = null;
		player2 = null;
		isStarted = false;
	}

}

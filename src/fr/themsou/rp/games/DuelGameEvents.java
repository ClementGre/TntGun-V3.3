package fr.themsou.rp.games;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.themsou.TntWars.TntWarsGameEvents;
import fr.themsou.main.main;
import fr.themsou.nms.title;

public class DuelGameEvents {
	
	public void startGame(main pl, DuelGame game){
		
		Location p1Spawn = game.loc;
		p1Spawn.setZ(game.loc.getZ() - 7);
		
		Location p2Spawn = game.loc;
		p1Spawn.setZ(game.loc.getZ() + 7);
		
		new BukkitRunnable(){
            int i = 16;
            @Override
            public void run(){
				if(game.hasEnoughPlayers()){
					i--;
				    
				    if(i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2 || i == 1){
				    	
				    	title.sendTitle(game.player1, "§c" + i + " s", "§6Le Duel commence...", 20);
				    	title.sendTitle(game.player2, "§c" + i + " s", "§6Le Duel commence...", 20);
				    	
				    }
				    
					if(i <= 0){
						game.isStarted = true;
						new TntWarsGameEvents().updateBar();
						System.out.println("Le duel n°" + game.id + " viens de démarer.");
						
						game.player1.teleport(p1Spawn);
						game.player2.teleport(p2Spawn);
						game.player1.setGameMode(GameMode.SURVIVAL);
						game.player2.setGameMode(GameMode.SURVIVAL);
						
						this.cancel();
						return;
						
					}
						
				}else{
					this.cancel();
					return;
				}
				
				
			}
		}.runTaskTimer(pl, 0, 20);
		
		
		
	}
	
	public void PlayerLeave(main pl, Player p){
		
		DuelGame game = DuelGame.getStartedInstanceViaPlayer(p);
		
		if(game != null && game.isStarted){
			
			p.performCommand("spawn");
			title.sendTitle(p, "§cVous avez perdu", "§eR.I.P.", 60);
			DuelGame.removePlayerInAllStartedGames(p);
			
			Player p2 = game.player1;
			if(p2 == null) p2 = game.player2;
			
			
			title.sendTitle(p2, "§cVous avez gagné !", "§eGG", 60);
			p2.sendMessage("§7Téléportation au spawn dans 10 secondes...");
			p2.setHealth(20);
			p2.setFoodLevel(20);
			
			game.resset();
			
			final Player p22 = p2;
			System.out.println("Le duel n°" + game.id + " viens de se terminer.");
			
			new BukkitRunnable(){
	            @Override
	            public void run(){
					p22.performCommand("spawn");
				}
			}.runTaskLater(pl, 20*10);
			
		}
		
	}

}

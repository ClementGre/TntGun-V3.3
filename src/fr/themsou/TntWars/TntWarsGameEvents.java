package fr.themsou.TntWars;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.themsou.commands.GradeCmd;
import fr.themsou.diffusion.api.roles;
import fr.themsou.main.main;
import fr.themsou.methodes.Schematics;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.title;

public class TntWarsGameEvents {
	
	public void startGame(TntWarsGame game, main pl){
		
		Random r = new Random();
		if(game.map == -1) game.map = r.nextInt(main.configuration.getConfigurationSection("tntwars.maps").getKeys(false).size());
		
		int x = r.nextInt(1000000);
		int y = 100;
		int z = r.nextInt(1000000);
		
		Location loc = new Location(Bukkit.getWorld("TntWars"), x, y, z);
		game.team1Spawn =  new Location(Bukkit.getWorld("TntWars"), x, y + 1, z + 0.5);
		game.team2Spawn = new Location(Bukkit.getWorld("TntWars"), x, y + 2, z - 0.5 + main.configuration.getInt("tntwars.maps." + game.map + ".redspawn"));
		
		new BukkitRunnable(){
            int i = 16;
            @Override
            public void run(){
				if(game.canStart()){
						
					i--;
				    
				    if(i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2 || i == 1){
				    	for(Player p : game.getPlayers()){
				    		title.sendTitle(p, "§c" + i + " s", "§6La partie de TntWars commence...", 20);
				    	}
				    }
				    
				    if(i == 15){
				    	if(!new Schematics().loadSchematic(loc, "TntWars-" + game.getMapName() + "_1")){
				    		this.cancel();
				    		return;
				    	}
				    	
				    }if(i == 10){
				    	if(!new Schematics().loadSchematic(loc, "TntWars-" + game.getMapName() + "_2")){
				    		this.cancel();
				    		return;
				    	}
				    	
				    }if(i == 5){
				    	if(!new Schematics().loadSchematic(loc, "TntWars-" + game.getMapName() + "_3")){
				    		this.cancel();
				    		return;
				    	}
				    }
				    
					
					if(i <= 0){
						game.isStarted = true;
						new TntWarsGameEvents().updateBar();
						System.out.println("La game n°" + game.id + " viens de démarer.");
						
						for(Player p : game.getPlayersTeam1()){
							p.teleport(game.team1Spawn);
						}
						for(Player p : game.getPlayersTeam2()){
							p.teleport(game.team2Spawn);
						}
						for(Player p : game.getPlayers()){
							
							p.setGameMode(GameMode.SURVIVAL);
							p.getInventory().clear();
							p.sendMessage("§6La partie de §eTntWars§6 commence !");
							
							if(new GradeCmd().getPlayerPermition(p.getName()) >= 2){
								
								p.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE, 1));
								p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 64));
								p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, 1));
								
								p.getInventory().addItem(new ItemStack(Material.LADDER, 64));
								p.getInventory().addItem(new ItemStack(Material.DISPENSER, 64));
								p.getInventory().addItem(new ItemStack(Material.LEVER, 64));
								p.getInventory().addItem(new ItemStack(Material.REDSTONE, 64));
								
								p.getInventory().addItem(new ItemStack(Material.STONE_BRICKS, 64));
								p.getInventory().addItem(new ItemStack(Material.STONE_BRICKS, 64));
								
								for(int i = 0; i < 27; i++){
									p.getInventory().addItem(new ItemStack(Material.TNT, 64));
								}
								
							}
							
							
							
							
							
						}
						
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
	
	
	
	@SuppressWarnings("deprecation")
	public void PlayerLeave(Player p){
		
		TntWarsGame game = TntWarsGame.getInstanceViaPlayer(p);
		if(game == null) return;
		if(!game.isStarted){
			
			TntWarsGame.removePlayerInAllGames(p);
			game.destroyIfCan();
			return;
		}
		
		for(Player ps : Bukkit.getWorld("TntWars").getPlayers()){
			ps.sendMessage(game.getTeamColor(p) + p.getName() + " §6est mort !");
		}
		
		if(game.canContinueWiout(p)){
			
			game.removePlayer(p);
			title.sendTitle(p, "§cVous êtes mort \u2639", "§6+20 points", 60);
			main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") + 20);
			
			p.teleport(new Location(Bukkit.getWorld("hub"), 0.5, 50, 0.5));
			p.setGameMode(GameMode.SURVIVAL);
			
			
		}else{
			
			int pTeam = game.getPlayerTeam(p);
			
			for(Player ps : game.getPlayers()){
				
				if(ps == null){
					continue;
				}
				
				ps.teleport(ps);
				
				main.config.set(ps.getName() + ".tntwars.lastday", new realDate().getRealDate().getDate());
				if(main.config.contains(ps.getName() + ".discord")){
					new roles().addRole("TntWars Player", main.config.getString(ps.getName() + ".discord"));
				}
				
				ps.teleport(new Location(Bukkit.getWorld("hub"), 0.5, 50, 0.5));
				ps.setGameMode(GameMode.SURVIVAL);
				
				if(game.getPlayerTeam(ps) != pTeam){
					title.sendTitle(ps, "§2Vous avez gagnée \u2639", "§6+50 points", 60);
					main.config.set(ps.getName() + ".points", main.config.getInt(ps.getName() + ".points") + 50);
				}else{
					title.sendTitle(ps, "§cVous avez perdu \u2639", "§6+20 points", 60);
					main.config.set(ps.getName() + ".points", main.config.getInt(ps.getName() + ".points") + 20);
				}
				
			}
			
			game.destroy();
			System.out.println("La game de TntWars n°" + game.id + " viens de se terminer.");
			
		}
		
		
	}
	
	public void removeBar(){
		
		TntWarsGame.bossBarGames = 0;
		
		for(Player p : Bukkit.getOnlinePlayers()){
			TntWarsGame.bossBar.removePlayer(p);
		}
		
	}
	
	public void addBar(int games){
		
		TntWarsGame.bossBar.setTitle("§c" + games + " §6game de §cTntWars §6en attente de joueurs...");
		TntWarsGame.bossBarGames = games;
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(!TntWarsGame.bossBar.getPlayers().contains(p)){
				TntWarsGame.bossBar.addPlayer(p);
			}
		}
		
	}
	
	public void updateBar(){
		
		int games = 0;
		
		for(int i = 0; i < TntWarsGame.games.size(); i++){
			if(!TntWarsGame.games.get(i).isStarted){
				games ++;
			}
		}
		
		if(games == 0 && TntWarsGame.bossBarGames != 0){
			removeBar();
			
		}else if(games != 0 && TntWarsGame.bossBarGames == 0){
			addBar(games);
			
		}else if(games != TntWarsGame.bossBarGames){
			TntWarsGame.bossBar.setTitle("§c" + games + " §6game de §cTntWars §6en attente de joueurs...");
			TntWarsGame.bossBarGames = games;
		}
		
		
		
	}
	

}

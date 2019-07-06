package fr.themsou.TntWars;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.themsou.diffusion.api.roles;
import fr.themsou.main.main;
import fr.themsou.methodes.Schematics;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.title;

public class RunParty {
	
	@SuppressWarnings("deprecation")
	public void Playerquit(Player p1){
		
		if(main.TntWarsCurrent.contains(p1)){
			
			Player p2 = Bukkit.getPlayerExact(main.config.getString(p1.getName() + ".tntwars.joueur"));
			
			p1.setGameMode(GameMode.SURVIVAL);
			p2.setGameMode(GameMode.SURVIVAL);
			
			p1.teleport(new Location(Bukkit.getWorld("hub"), 0.5, 50, 0.5));
			p2.teleport(new Location(Bukkit.getWorld("hub"), 0.5, 50, 0.5));
			
			
			title.sendTitle(p1, "§3Vous avez perdu \u2639", "§6+20 points", 60);
			title.sendTitle(p2, "§3Vous avez gagnée \u2639", "§6+50 points", 60);
			main.config.set(p1.getName() + ".points", main.config.getInt(p1.getName() + ".points") + 20);
			main.config.set(p2.getName() + ".points", main.config.getInt(p2.getName() + ".points") + 50);
			System.out.println(p1.getName() + " et " + p2.getName() + " finissent une partie de TntWars");
			
			main.config.set(p1.getName() + ".tntwars", null);
			main.config.set(p2.getName() + ".tntwars", null);
			
			main.TntWarsCurrent.remove(p1);
			main.TntWarsCurrent.remove(p2);
			
			main.config.set(p1.getName() + ".tntwars.lastday", new realDate().getRealDate().getDate());
			main.config.set(p2.getName() + ".tntwars.lastday", new realDate().getRealDate().getDate());
			if(main.config.contains(p1.getName() + ".discord")){
				roles Croles = new roles();
				Croles.addRole("TntWars Player", main.config.getString(p1.getName() + ".discord"));
			}
			if(main.config.contains(p2.getName() + ".discord")){
				roles Croles = new roles();
				Croles.addRole("TntWars Player", main.config.getString(p2.getName() + ".discord"));
			}
			
			
		}
		
		
	}
	public void startpartya(main pl){
		
		Schematics CSchematics = new Schematics();
		
		
		Player p1 = main.TntWarsFillea.get(0);
		main.TntWarsFillea.remove(0);
		Player p2 = main.TntWarsFillea.get(0);
		main.TntWarsFillea.remove(0);
		
		main.TntWarsCurrent.add(p1);
		main.TntWarsCurrent.add(p2);
		
		
		
		/*
		axe           : Z
		+ tntwars-a-3 : +88
		+ respawn 1   : +15
		+ respawn 3   : -15
		*/
		
		Random r = new Random();
		int x = r.nextInt(1000000);
		int y = 100;
		int z = r.nextInt(1000000);
		
		Location spawn = new Location(Bukkit.getWorld("TntWars"), x, y, z);
		Location p1spawn = new Location(Bukkit.getWorld("TntWars"), x + 1, y, z);
		Location p2spawn = new Location(Bukkit.getWorld("TntWars"), x + 1, y, z + 88);
		
		
		
		
		main.config.set(p1.getName() + ".tntwars.joueur", p2.getName());
		main.config.set(p1.getName() + ".tntwars.team", "red");
		main.config.set(p1.getName() + ".tntwars.loc.x", p1spawn.getBlockX());
		main.config.set(p1.getName() + ".tntwars.loc.z", p1spawn.getBlockZ());
		
		main.config.set(p2.getName() + ".tntwars.joueur", p1.getName());
		main.config.set(p2.getName() + ".tntwars.team", "blue");
		main.config.set(p2.getName() + ".tntwars.loc.x", p2spawn.getBlockX());
		main.config.set(p2.getName() + ".tntwars.loc.z", p2spawn.getBlockZ());
		
		
		new BukkitRunnable(){
            int i = 16;
            @Override
            public void run(){
				if(main.TntWarsCurrent.contains(p1)){
					if(main.TntWarsCurrent.contains(p2)){
						
						i--;
					    
					    if(i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2 || i == 1){
					    	title.sendTitle(p1, "§3"+i+" s", "La partie de TntWars commence ...", 20);
							title.sendTitle(p2, "§3"+i+" s", "La partie de TntWars commence ...", 20);
					    }
					    
					    if(i == 15){
					    	CSchematics.loadSchematic(spawn, "tntwars-b-1");
					    }if(i == 10){
					    	CSchematics.loadSchematic(spawn, "tntwars-b-2");
					    }if(i == 5){
					    	CSchematics.loadSchematic(spawn, "tntwars-b-3");
					    	
					    }
					    
						
						if(i <= 0){
							
							System.out.println(p1.getName() + " et " + p2.getName() + " commencent une partie de TntWars");
							
							
							p1.sendMessage("§6La partie de §eTntWars§6 commence");
							p2.sendMessage("§6La partie de §eTntWars§6 commence");
							
							p1.setGameMode(GameMode.SURVIVAL);
							p2.setGameMode(GameMode.SURVIVAL);
							
							p1.teleport(p1spawn);
							p2.teleport(p2spawn);
							
							p1.getInventory().clear();
							p2.getInventory().clear();
							
							
							this.cancel();
							return;
							
						}
						
					}else this.cancel(); return;
				}else this.cancel(); return;
				
				
			}
		}.runTaskTimer(pl, 0, 20);
		
	}
	
	public void startpartyb(main pl){
		Schematics CSchematics = new Schematics();
		
		
		Player p1 = main.TntWarsFilleb.get(0);
		main.TntWarsFilleb.remove(0);
		Player p2 = main.TntWarsFilleb.get(0);
		main.TntWarsFilleb.remove(0);
		
		
		main.TntWarsCurrent.add(p1);
		main.TntWarsCurrent.add(p2);
		
		/*
		axe           : Z
		+ tntwars-a-3 : +88
		+ respawn 1   : +15
		+ respawn 3   : -15
		*/
		
		Random r = new Random();
		int x = r.nextInt(1000000);
		int y = 100;
		int z = r.nextInt(1000000);
		
		Location spawn = new Location(Bukkit.getWorld("TntWars"), x, y, z);
		Location p1spawn = new Location(Bukkit.getWorld("TntWars"), x + 1, y, z);
		Location p2spawn = new Location(Bukkit.getWorld("TntWars"), x + 1, y, z + 88);
		
		
		
		
		main.config.set(p1.getName() + ".tntwars.joueur", p2.getName());
		main.config.set(p1.getName() + ".tntwars.team", "red");
		main.config.set(p1.getName() + ".tntwars.loc.x", p1spawn.getBlockX());
		main.config.set(p1.getName() + ".tntwars.loc.z", p1spawn.getBlockZ());
		
		main.config.set(p2.getName() + ".tntwars.joueur", p1.getName());
		main.config.set(p2.getName() + ".tntwars.team", "blue");
		main.config.set(p2.getName() + ".tntwars.loc.x", p2spawn.getBlockX());
		main.config.set(p2.getName() + ".tntwars.loc.z", p2spawn.getBlockZ());
		
		
		
		
		
		new BukkitRunnable(){
            int i = 16;
            @Override
            public void run(){
				if(main.TntWarsCurrent.contains(p1)){
					if(main.TntWarsCurrent.contains(p2)){
						
						i--;
					    
					    if(i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2 || i == 1){
					    	title.sendTitle(p1, "§3"+i+" s", "La partie de TntWars commence ...", 20);
							title.sendTitle(p2, "§3"+i+" s", "La partie de TntWars commence ...", 20);
					    }
					    
					    if(i == 15){
					    	CSchematics.loadSchematic(spawn, "tntwars-b-1");
					    }if(i == 10){
					    	CSchematics.loadSchematic(spawn, "tntwars-b-2");
					    }if(i == 5){
					    	CSchematics.loadSchematic(spawn, "tntwars-b-3");
					    	
					    }
					    
						
						if(i <= 0){
							
							System.out.println(p1.getName() + " et " + p2.getName() + " commencent une partie de TntWars");
							
							
							p1.sendMessage("§6La partie de §eTntWars§6 commence");
							p2.sendMessage("§6La partie de §eTntWars§6 commence");
							
							p1.setGameMode(GameMode.SURVIVAL);
							p2.setGameMode(GameMode.SURVIVAL);
							
							p1.teleport(p1spawn);
							p2.teleport(p2spawn);
							
							p1.getInventory().clear();
							p2.getInventory().clear();
							
							
							
							this.cancel();
							return;
							
						}
						
					}else this.cancel(); return;
				}else this.cancel(); return;
				
				
			}
		}.runTaskTimer(pl, 0, 20);
		
	}

}

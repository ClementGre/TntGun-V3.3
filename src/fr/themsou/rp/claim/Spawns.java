package fr.themsou.rp.claim;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.themsou.main.main;

public class Spawns{
	
	public boolean isInSpawn(Location loc){
		
		if(!loc.getWorld().getName().equalsIgnoreCase("world")) return false;

		int px = loc.getBlockX();
		int pz = loc.getBlockZ();
		
		for(String spawn : Claim.getSpawns()){

			int minx = main.config.getInt("claim.list." + spawn + ".minx");
			int maxx = main.config.getInt("claim.list." + spawn + ".maxx");
			int minz = main.config.getInt("claim.list." + spawn + ".minz");
			int maxz = main.config.getInt("claim.list." + spawn + ".maxz");
			
			if(px <= maxx && px >= minx && pz <= maxz && pz >= minz) return true;

		}
		return false;
	}
	
	public String getSpawnNameWithId(int id){
		
		for(String spawn : Claim.getSpawns()){
			if(main.config.contains("claim.list." + spawn + "." + id)) return spawn;
		}
		return null;
	}
	
	public String getSpawnNameWithLoc(Location loc){
		
		if(!loc.getWorld().getName().equalsIgnoreCase("world")) return null;
		
		int px = loc.getBlockX();
		int pz = loc.getBlockZ();
		
		for(String spawn : Claim.getSpawns()){

			int minx = main.config.getInt("claim.list." + spawn + ".minx");
			int maxx = main.config.getInt("claim.list." + spawn + ".maxx");
			int minz = main.config.getInt("claim.list." + spawn + ".minz");
			int maxz = main.config.getInt("claim.list." + spawn + ".maxz");
			
			if(px <= maxx && px >= minx && pz <= maxz && pz >= minz) return spawn;
		}
		return null;
	}
	
	public String getSpawns(){

		String items = "";
		for(String spawn : Claim.getSpawns()){
			
			if(main.config.contains("claim.list." + spawn + ".x")){
				if(items.isEmpty()) items = spawn;
				else items += "/" + spawn;
			}
		}
		return items;
	}
	
	public Location getSpawnSpawn(String spawn){
		
		int x = main.config.getInt("claim.list." + spawn + ".x");
		int y = main.config.getInt("claim.list." + spawn + ".y");
		int z = main.config.getInt("claim.list." + spawn + ".z");

		return new Location(Bukkit.getWorld("world"), x, y, z);
	}
	
	public void setSpawn(Player p, String name, boolean realSpawn, Location spawn, Location loc1, Location loc2){

		main.config.set("claim.list." + name + ".minx", loc1.getBlockX());
		main.config.set("claim.list." + name + ".minz", loc1.getBlockZ());
		main.config.set("claim.list." + name + ".maxx", loc2.getBlockX());
		main.config.set("claim.list." + name + ".maxz", loc2.getBlockZ());

		if(realSpawn){
			main.config.set("claim.list." + name + ".x", spawn.getX());
			main.config.set("claim.list." + name + ".y", spawn.getY());
			main.config.set("claim.list." + name + ".z", spawn.getZ());
		}
		p.sendMessage("§bLa ville §3" + name + "§b a bien été créé !");

	}
			
			

}

package fr.themsou.rp.claim;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.themsou.main.main;

public class Spawns{
	
	public boolean isInSpawn(Location loc){
		
		if(!loc.getWorld().getName().equalsIgnoreCase("world")) return false;
		
		Set<String> section = main.config.getConfigurationSection("claim.list").getKeys(false);	
		int size = section.size();
		String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
		String[] item = items.split(",");
		
		int px = loc.getBlockX();
		int pz = loc.getBlockZ();
		
		for(int i = 1; i <= size; i++){
			
			int number = i - 1;
			
			int minx = main.config.getInt("claim.list." + item[number] + ".minx");
			int maxx = main.config.getInt("claim.list." + item[number] + ".maxx");
			int minz = main.config.getInt("claim.list." + item[number] + ".minz");
			int maxz = main.config.getInt("claim.list." + item[number] + ".maxz");
			
			if(px <= maxx && px >= minx && pz <= maxz && pz >= minz) return true;
			
			
		}
		return false;
	}
	
	public String getSpawnNameWithId(int id){
		
		
		Set<String> section = main.config.getConfigurationSection("claim.list").getKeys(false);	
		int size = section.size();
		String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
		String[] item = items.split(",");
		
		
		for(int i = 1; i <= size; i++){
			
			int number = i - 1;
			
			if(main.config.contains("claim.list." + item[number] + "." + id)) return item[number];
			
			
		}
		return null;
		
		/*int x = main.config.getInt("claim.list." + id + ".x1");
		int z = main.config.getInt("claim.list." + id + ".z1");
		
		return IsInSpawnName(new Location(Bukkit.getWorld("world"), x, 64, z));*/
		
		
	}
	
	public String getSpawnNameWithLoc(Location loc){
		
		if(!loc.getWorld().getName().equalsIgnoreCase("world")) return null;
		
		Set<String> section = main.config.getConfigurationSection("claim.list").getKeys(false);	
		int size = section.size();
		String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
		String[] item = items.split(",");
		
		int px = loc.getBlockX();
		int pz = loc.getBlockZ();
		
		for(int i = 1; i <= size; i++){
			
			int number = i - 1;
			
			int minx = main.config.getInt("claim.list." + item[number] + ".minx");
			int maxx = main.config.getInt("claim.list." + item[number] + ".maxx");
			int minz = main.config.getInt("claim.list." + item[number] + ".minz");
			int maxz = main.config.getInt("claim.list." + item[number] + ".maxz");
			
			if(px <= maxx && px >= minx && pz <= maxz && pz >= minz) return item[number];
			
			
		}
		return null;
	}
	
	public String getSpawns(){
		
		Set<String> section = main.config.getConfigurationSection("claim.list").getKeys(false);
		
		String items = "";
		for(String item : section){
			
			if(main.config.contains("claim.list." + item + ".x")){
				if(items.isEmpty()) items = item;
				else items += "/" + item;
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

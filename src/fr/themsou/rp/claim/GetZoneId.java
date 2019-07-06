package fr.themsou.rp.claim;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.themsou.main.main;

public class GetZoneId {
	
	public int getIdOfPlayerZone(Location loc){
		
		int id = 0;
		Spawns CSpawns = new Spawns();
		String spawn = CSpawns.getSpawnNameWithLoc(loc);
		
		if(spawn == null) return 0;
		
		Set<String> section = main.config.getConfigurationSection("claim.list." + spawn).getKeys(false);	
		int size = section.size();
		String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
		String[] item = items.split(",");
		int xp = (int) loc.getX();
		int zp = (int) loc.getZ();
		int yp = (int) loc.getY();
		
		
		if(loc.getWorld() == Bukkit.getWorld("world")){
			for(int i = 1; i <= size; i++){
				
				int number = i - 1;
				
				if(!item[number].equals("minx") && !item[number].equals("maxx") && !item[number].equals("minz") && !item[number].equals("maxz") && !item[number].equals("app") && !item[number].equals("x") && !item[number].equals("y") && !item[number].equals("z")){
					
					int x1 = main.config.getInt("claim.list." + spawn + "." + item[number] + ".x1");
					int x2 = main.config.getInt("claim.list." + spawn + "." + item[number] + ".x2");
					int z1 = main.config.getInt("claim.list." + spawn + "." + item[number] + ".z1");
					int z2 = main.config.getInt("claim.list." + spawn + "." + item[number] + ".z2");
					
					int y1 = main.config.getInt("claim.list." + spawn + "." + item[number] + ".y1");
					int y2 = main.config.getInt("claim.list." + spawn + "." + item[number] + ".y2");
					
					if(y1 != 0 && y2 != 0){
					
//				3D
						
						if(x1 > x2){
							if(xp <= x1 && xp >= x2){
								if(z1 > z2){
									if(zp <= z1 && zp >= z2){
										if(y1 > y2){
											if(yp <= y1 && yp >= y2){
												return Integer.parseInt(item[number]);
											}
										}else{
											if(yp >= y1 && yp <= y2){
												return Integer.parseInt(item[number]);
											}
										}
									}
								}else{
									if(zp >= z1 && zp <= z2){
										return Integer.parseInt(item[number]);
									}
								}
							}
						}else{
							if(xp >= x1 && xp <= x2){
								if(z1 > z2){
									if(zp <= z1 && zp >= z2){
										if(y1 > y2){
											if(yp <= y1 && yp >= y2){
												return Integer.parseInt(item[number]);
											}
										}else{
											if(yp >= y1 && yp <= y2){
												return Integer.parseInt(item[number]);
											}
										}
									}
								}else{
									if(zp >= z1 && zp <= z2){
										if(y1 > y2){
											if(yp <= y1 && yp >= y2){
												return Integer.parseInt(item[number]);
											}
										}else{
											if(yp >= y1 && yp <= y2){
												return Integer.parseInt(item[number]);
											}
										}
									}
								}
							}
						}
						
//				NO 	3D			
							
							
						}else{
							if(x1 > x2){
								if(xp <= x1 && xp >= x2){
									if(z1 > z2){
										if(zp <= z1 && zp >= z2){
											return Integer.parseInt(item[number]);
										}
									}else{
										if(zp >= z1 && zp <= z2){
											return Integer.parseInt(item[number]);
										}
									}
								}
							}else{
								if(xp >= x1 && xp <= x2){
									if(z1 > z2){
										if(zp <= z1 && zp >= z2){
											return Integer.parseInt(item[number]);
										}
									}else{
										if(zp >= z1 && zp <= z2){
											return Integer.parseInt(item[number]);
										}
									}
								}
							}
						}
					}
					
				}
			}
			
			return id;
		
	}

}

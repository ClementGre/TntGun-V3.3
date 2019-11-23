package fr.themsou.rp.claim;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import fr.themsou.main.main;

public class GetZoneId {

	public int getIdOfPlayerZone(Location loc){

		if(loc.getWorld() == Bukkit.getWorld("world")) {

			String spawn = new Spawns().getSpawnNameWithLoc(loc);
			if (spawn == null) return 0;

			int xp = loc.getBlockX();
			int zp = loc.getBlockZ();
			int yp = loc.getBlockY();

			for (Integer id : Claim.getSpawnClaims(spawn)) {

				int x1 = main.config.getInt("claim.list." + spawn + "." + id + ".x1");
				int x2 = main.config.getInt("claim.list." + spawn + "." + id + ".x2");
				int z1 = main.config.getInt("claim.list." + spawn + "." + id + ".z1");
				int z2 = main.config.getInt("claim.list." + spawn + "." + id + ".z2");
				int y1 = main.config.getInt("claim.list." + spawn + "." + id + ".y1");
				int y2 = main.config.getInt("claim.list." + spawn + "." + id + ".y2");

				if(y1 != 0 && y2 != 0){
//				3D
					if(x1 > x2){
						if(xp <= x1 && xp >= x2){
							if(z1 > z2){
								if(zp <= z1 && zp >= z2){
									if(y1 > y2){
										if(yp <= y1 && yp >= y2){
											return id;
										}
									}else{
										if(yp >= y1 && yp <= y2){
											return id;
										}
									}
								}
							}else{
								if(zp >= z1 && zp <= z2){
									return id;
								}
							}
						}
					}else{
						if(xp >= x1 && xp <= x2){
							if(z1 > z2){
								if(zp <= z1 && zp >= z2){
									if(y1 > y2){
										if(yp <= y1 && yp >= y2){
											return id;
										}
									}else{
										if(yp >= y1 && yp <= y2){
											return id;
										}
									}
								}
							}else{
								if(zp >= z1 && zp <= z2){
									if(y1 > y2){
										if(yp <= y1 && yp >= y2){
											return id;
										}
									}else{
										if(yp >= y1 && yp <= y2){
											return id;
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
									return id;
								}
							}else{
								if(zp >= z1 && zp <= z2){
									return id;
								}
							}
						}
					}else{
						if(xp >= x1 && xp <= x2){
							if(z1 > z2){
								if(zp <= z1 && zp >= z2){
									return id;
								}
							}else{
								if(zp >= z1 && zp <= z2){
									return id;
								}
							}
						}
					}
				}


			}
		}
		return 0;
	}
}

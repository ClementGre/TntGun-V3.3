package fr.themsou.rp.claim;

import org.bukkit.Location;

public class CalculSuperficie {
	
	public int calculSuperficieOfZone(Location loc1, Location loc2){
		
		int x1 = loc1.getBlockX();
		int x2 = loc2.getBlockX();
		int z1 = loc1.getBlockZ();
		int z2 = loc2.getBlockZ();
		
		int x;
		int z;
		
		if(x1 > x2){ x = x1-x2; }
		else{x = x2-x1; }
			
		if(z1 > z2){ z = z1-z2; }
		else{ z = z2-z1; }
		
		int superficie = (x + 1) * (z + 1);
		
		return superficie;
		
	}

}

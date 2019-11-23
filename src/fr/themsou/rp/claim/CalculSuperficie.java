package fr.themsou.rp.claim;

import org.bukkit.Location;

public class CalculSuperficie {
	
	public int calculSuperficieOfZone(Location loc1, Location loc2){
		
		int x1 = loc1.getBlockX();
		int x2 = loc2.getBlockX();
		int z1 = loc1.getBlockZ();
		int z2 = loc2.getBlockZ();

		int x = x2-x1;
		int z = z2-z1;

		int superficie = (x + 1) * (z + 1);
		
		return superficie;
		
	}

}

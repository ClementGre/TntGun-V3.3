package fr.themsou.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import fr.themsou.rp.claim.GetZoneId;
import fr.themsou.rp.claim.Spawns;

public class CustomEvent {

	public void second(){
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
        for(Entity entity : Bukkit.getWorld("world").getEntities()){
            if(!(entity instanceof Player)){

                if(CGetZoneId.getIdOfPlayerZone(entity.getLocation()) == 0 && CSpawns.isInSpawn(entity.getLocation())){
                	if(entity instanceof Monster){
                		entity.remove();
                	}
                }
            }
        }
        
	}
	
}

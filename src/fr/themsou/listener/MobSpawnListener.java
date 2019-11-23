package fr.themsou.listener;

import fr.themsou.rp.claim.Claim;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import fr.themsou.main.main;
import fr.themsou.rp.claim.GetZoneId;
import fr.themsou.rp.claim.Spawns;

public class MobSpawnListener implements Listener{
	
	
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e){
		
		if(e.getEntity() instanceof LivingEntity){
			
			if(e.getEntity().getLocation().getWorld() == Bukkit.getWorld("world")){

				Claim claim = new Claim(e.getEntity().getLocation());
				if(claim.getSpawn() != null){
					if(!claim.exist() || claim.getPureOwner() == null || claim.isEnt()){
						if(e.getEntityType() != EntityType.ARMOR_STAND && e.getEntity() instanceof Monster){
							e.setCancelled(true);
						}
					}
				}
				
			}else if(e.getEntity().getLocation().getWorld() == Bukkit.getWorld("BedWars")){
				
				if(e.getEntityType() != EntityType.VILLAGER && e.getEntityType() != EntityType.ARMOR_STAND){
					e.setCancelled(true);
				}
				
				
			}
			
			
			
			
		}
		
		
	}

}

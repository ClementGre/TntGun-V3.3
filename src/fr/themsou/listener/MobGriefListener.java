package fr.themsou.listener;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import fr.themsou.rp.claim.Spawns;

public class MobGriefListener implements Listener{
	
	@SuppressWarnings("rawtypes")
	@EventHandler
	public void oncreep(EntityExplodeEvent e){
			
		
		Entity entity = e.getEntity();
		Location loc = entity.getLocation();
		
		if(loc.getWorld() == Bukkit.getWorld("world")){
			
			if(new Spawns().isInSpawn(loc)){
				e.setCancelled(true);
			}
			
		}else if(loc.getWorld() == Bukkit.getWorld("BedWars")){
			
			e.setYield(0.1F);
			List destroyed = e.blockList();
		    Iterator it = destroyed.iterator();
		    while (it.hasNext()) {
			    Block block = (Block) it.next();
			    
		    	if(block.getType() != Material.RED_WOOL && block.getType() != Material.BLUE_WOOL && block.getType() != Material.GREEN_WOOL && block.getType() != Material.YELLOW_WOOL &&
		    		block.getType() != Material.RED_CONCRETE && block.getType() != Material.BLUE_CONCRETE && block.getType() != Material.GREEN_CONCRETE && block.getType() != Material.YELLOW_CONCRETE &&
		    		block.getType() != Material.END_STONE && block.getType() != Material.OBSIDIAN){
		    		
					it.remove();
				}
		    }
		}else if(loc.getWorld() == Bukkit.getWorld("TntWars")){
			
			List destroyed = e.blockList();
		    Iterator it = destroyed.iterator();
		    Random r = new Random();
		    while(it.hasNext()){
		    	it.next();
		    	if(r.nextInt(5) == 0){
		    		it.remove();
				}
		    }
		}
	}
	
	@EventHandler
	public void onExplode(ExplosionPrimeEvent e){
			
		Entity entity = e.getEntity();
		Location loc = entity.getLocation();
		
		if(loc.getWorld() == Bukkit.getWorld("BedWars")){
			
			e.setRadius(2);
			e.setFire(false);
			
		}if(loc.getWorld() == Bukkit.getWorld("TntWars")){
			
			e.setRadius(4);
			e.setFire(false);
			
		}
			
	}

}

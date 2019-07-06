package fr.themsou.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import fr.themsou.rp.tools.oncraft;

public class CraftListener implements Listener{
	
	@EventHandler
	public void craft(CraftItemEvent e){
		
		oncraft Concraft = new oncraft();
		
		if(e.getClickedInventory().getLocation().getWorld() == Bukkit.getWorld("world") || e.getClickedInventory().getLocation().getWorld() == Bukkit.getWorld("world_nether") || e.getClickedInventory().getLocation().getWorld() == Bukkit.getWorld("world_the_end")){
			Concraft.craft(e);
		}else if(e.getClickedInventory().getLocation().getWorld() == Bukkit.getWorld("BedWars")){
			e.setCancelled(true);
		}
			
		
		
		
		
	}

}

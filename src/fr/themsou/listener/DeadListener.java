package fr.themsou.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import fr.themsou.rp.tools.Chasseur;

public class DeadListener implements Listener{

	@EventHandler
	public void OnDied(EntityDeathEvent e){
		
		Entity victim = e.getEntity();
		
		if(victim.getLocation().getWorld() == Bukkit.getWorld("world") || victim.getLocation().getWorld() == Bukkit.getWorld("world_nether") || victim.getLocation().getWorld() == Bukkit.getWorld("world_the_end")){
			if(!(victim instanceof Player)){
				
				if(e.getEntity().getKiller() instanceof Player){
					
					Player p = (Player) e.getEntity().getKiller();
					if(p.getGameMode() == GameMode.SURVIVAL){
						
						new Chasseur(p).addPCAuto();
						
					}
				}
			}else{
				Player p = (Player) victim;
			}
		}
		
	}
	
}

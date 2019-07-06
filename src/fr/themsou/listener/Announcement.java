package fr.themsou.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class Announcement implements Listener{
	
	
	@EventHandler
	public void onAnnounce(PlayerAdvancementDoneEvent e){
		
		
	}
	
	@EventHandler
	public void onDead(PlayerDeathEvent e){
		
		e.setDeathMessage("§3" + e.getEntity().getName() + " §best mort  §7...RIP");
		
	}

}

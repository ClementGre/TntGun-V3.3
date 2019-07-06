package fr.themsou.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import fr.themsou.rp.tools.Enchanteur;

public class EnchantListener implements Listener{
	
	
	@EventHandler
	public void OnEnchat(EnchantItemEvent e){
		
		Player p = e.getEnchanter();
		
		if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
			if(p.getGameMode() == GameMode.SURVIVAL){
				
				new Enchanteur(p).addPCAuto(e.getExpLevelCost());
				
			}
		}
		
		
	}
	

}

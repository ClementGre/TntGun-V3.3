package fr.themsou.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import fr.themsou.methodes.Inventory;
import fr.themsou.methodes.PInfos;

public class ChangeWorldListener implements Listener{
	
	
	@EventHandler
	public void onGamemodeChange(PlayerGameModeChangeEvent e){
		
		Player p = e.getPlayer();
		
		Inventory CInventory = new Inventory();
		
		if(e.getNewGameMode() == GameMode.SURVIVAL){
			
			CInventory.savePlayerInventory(p, "crea");
			
			if(PInfos.getGame(p).equals("RP")){
				CInventory.loadPlayerInventory(p, "rp");
			}else if(p.getWorld().getName().equals("hub")){
				
				p.getInventory().clear();
				p.setHealth(20);
				p.setFoodLevel(20);
				
				ItemStack a = new ItemStack(Material.DIAMOND, 1);
				ItemMeta aM = a.getItemMeta();
				aM.setDisplayName("§3§lVIP");
				a.setItemMeta(aM);
				p.getInventory().setItem(0, a);
				
				ItemStack b = new ItemStack(Material.COMPASS, 1);
				ItemMeta bM = b.getItemMeta();
				bM.setDisplayName("§3§lMENU");
				b.setItemMeta(bM);
				p.getInventory().setItem(4, b);
				
			}else{
				p.getInventory().clear();
			}
			
			
			
			
		}else if(p.getGameMode() == GameMode.SURVIVAL){
			
			if(PInfos.getGame(p).equals("RP")){
				CInventory.savePlayerInventory(p, "rp");
			}
			
			CInventory.loadPlayerInventory(p, "crea");
			
		}
		
	}
	
	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent e){
		
		Player p = e.getPlayer();
		
		if(getGame(p.getWorld()).equals("RP") && (!getGame(e.getFrom()).equals("RP"))){
			
			if(p.getGameMode() == GameMode.SURVIVAL){
				new Inventory().loadPlayerInventory(p, "rp");
			}
			
		}if((!getGame(p.getWorld()).equals("RP")) && getGame(e.getFrom()).equals("RP")){
			
			if(p.getGameMode() == GameMode.SURVIVAL){
				new Inventory().savePlayerInventory(p, "rp");
				for(PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
				p.setHealth(20);
				p.setFoodLevel(20);
				p.getInventory().clear();
			}
			
		}if(getGame(p.getWorld()).equals("HUB")){
			
			if(p.getGameMode() == GameMode.SURVIVAL){
				p.getInventory().clear();
				p.setHealth(20);
				p.setFoodLevel(20);
				
				ItemStack a = new ItemStack(Material.DIAMOND, 1);
				ItemMeta aM = a.getItemMeta();
				aM.setDisplayName("§3§lVIP");
				a.setItemMeta(aM);
				p.getInventory().setItem(0, a);
				
				ItemStack b = new ItemStack(Material.COMPASS, 1);
				ItemMeta bM = b.getItemMeta();
				bM.setDisplayName("§3§lMENU");
				b.setItemMeta(bM);
				p.getInventory().setItem(4, b);
				
			}
			
		}
	}
	
	private String getGame(World world){
		
		if(world == Bukkit.getWorld("world") || world == Bukkit.getWorld("world_nether") || world == Bukkit.getWorld("world_the_end")){
			return "RP";
		}
		if(world == Bukkit.getWorld("HUB")){
			return "HUB";
		}
		
		return "other";
				
	}

}

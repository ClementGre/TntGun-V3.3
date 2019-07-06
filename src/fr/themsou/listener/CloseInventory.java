package fr.themsou.listener;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import fr.themsou.main.main;
import fr.themsou.methodes.Inventory;
import fr.themsou.methodes.PInfos;
import fr.themsou.methodes.SimpleItem;
import fr.themsou.rp.ent.Utils;

public class CloseInventory implements Listener{
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e){
		
		Player p = (Player) e.getPlayer();
		
		if(e.getInventory().getType() == InventoryType.ENDER_CHEST){
			
			if(PInfos.getGame(p).equals("RP") && p.getGameMode() == GameMode.SURVIVAL){
				
				e.getInventory().clear();
				e.getInventory().setContents(new Inventory().loadInventory(p.getName(), "rp-ec"));
				
			}else if(PInfos.getGame(p).equals("BedWars") && p.getGameMode() == GameMode.SURVIVAL){
				
				e.getInventory().clear();
				e.getInventory().setContents(new Inventory().loadInventory(p.getName(), "bw-ec"));
				
			}else if(p.getGameMode() == GameMode.CREATIVE){
				
				e.getInventory().clear();
				e.getInventory().setContents(new Inventory().loadInventory(p.getName(), "crea-ec"));
				
			}else{
				p.sendMessage("§cVous ne pouvez pas ouvrir d'enderchest dans ce mode de jeu !");
				e.setCancelled(true);
			}
			
		}
		
	}
	
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e){
		
		Player p = (Player) e.getPlayer();
		
		if(e.getInventory().getType() == InventoryType.CHEST){
			
			if(p.getWorld() == Bukkit.getWorld("world")){
				if(main.entLog.containsKey(p.getName())){
					ItemStack[] beforeInv = main.entLog.get(p.getName());
					ItemStack[] afterInv = e.getInventory().getContents();
					main.entLog.remove(p.getName());
					
					
					HashMap<SimpleItem, Integer> beforeItemsStacked = stackItems(beforeInv);
					HashMap<SimpleItem, Integer> afterItemsStacked = stackItems(afterInv);
					
					HashMap<SimpleItem, Integer> removedItems = substractInventory(beforeItemsStacked, afterItemsStacked);
					HashMap<SimpleItem, Integer> addedItems = substractInventory(afterItemsStacked, beforeItemsStacked);
					
					
					if(addedItems.size() >= 1){
						new Utils().log((Player) p, addedItems, true);
					}
					if(removedItems.size() >= 1){
						new Utils().log((Player) p, removedItems, false);
					}
					
				}
			}
		}else if(e.getInventory().getType() == InventoryType.ENDER_CHEST){
			
			if(PInfos.getGame(p).equals("RP") && p.getGameMode() == GameMode.SURVIVAL){
				
				new Inventory().saveInventory(p.getName(), e.getInventory().getContents(), "rp-ec");
				
			}else if(PInfos.getGame(p).equals("BedWars") && p.getGameMode() == GameMode.SURVIVAL){
				
				new Inventory().saveInventory(p.getName(), e.getInventory().getContents(), "bw-ec");
				
			}else if(p.getGameMode() == GameMode.CREATIVE){
				
				new Inventory().saveInventory(p.getName(), e.getInventory().getContents(), "crea-ec");
			}
			
		}
	}
	
	public HashMap<SimpleItem, Integer> stackItems(ItemStack[] inv){
		
		HashMap<SimpleItem, Integer> stacked = new HashMap<>();
		
		for(ItemStack item : inv){
			if(item != null){
				if(item.getType() != Material.AIR){
					
					@SuppressWarnings("deprecation")
					SimpleItem simpleItem = new SimpleItem(item.getType(), (byte) item.getDurability(), item.getEnchantments());
					if(simpleItem.containsInHashMap(stacked)){
						
						int number = stacked.get(simpleItem.getKeyEqualsInHashMap(stacked));
						stacked.remove(simpleItem.getKeyEqualsInHashMap(stacked));
						stacked.put(simpleItem, item.getAmount() + number);
						
					}else{
						stacked.put(simpleItem, item.getAmount());
					}
				}
			}
		}
		
		return stacked;
	}
	
	
	public HashMap<SimpleItem, Integer> substractInventory(HashMap<SimpleItem, Integer> defaultInv, HashMap<SimpleItem, Integer> toSubInv){

		HashMap<SimpleItem, Integer> returnValue = new HashMap<>();
		
		
		for(SimpleItem key : defaultInv.keySet()){
			
			int defaultItem = defaultInv.get(key);
			
			if(key.containsInHashMap(toSubInv)){
				
				int toSubItem = toSubInv.get(key.getKeyEqualsInHashMap(toSubInv));
				if(defaultItem > toSubItem) returnValue.put(key, defaultItem - toSubItem);
					
			}else{
				returnValue.put(key, defaultItem);
			}
		}
		
		return returnValue;
		
	}
	
}

package fr.themsou.BedWars;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.inv.CmdInv;
import fr.themsou.inv.UtilsInv;
import fr.themsou.inv.VipInv;
import fr.themsou.main.main;

public class BedWarsInv {
	
	public void openMainInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§4BedWars - Menu");
		new UtilsInv().setWalls(inv, 5);
		
		inv.setItem(21, UtilsInv.makeItem(Material.COMPARATOR, "§3§lCOMMANDES", Arrays.asList("§eListe de toutes les commandes Disponibles")));
		inv.setItem(23, UtilsInv.makeItem(Material.DIAMOND, "§3§lVIP", Arrays.asList("§eVoir les avantages vip")));
		
		p.openInventory(inv);
	}
	public void itemMainClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.COMPARATOR){
			
			p.closeInventory();
			new CmdInv().openInv(p);
			
		}else if(e.getCurrentItem().getType() == Material.DIAMOND){
			
			p.closeInventory();
			new VipInv().openInv(p);
		}
		
	}

	public void openJoinInv(Player p){
		
		p.openInventory(getJoinInv(p));
		
	}
	public Inventory getJoinInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§4BedWars - Rejoindre");
		new UtilsInv().setWalls(inv, 5);
		getteam Cgetteam = new getteam();
		
		int slot = 20;
		for(int i = 1; i <= 4; i++){
			List<String> lore = null;
			
			if(main.config.getString("bedwars.list.teams." + i + ".owners") == null){
				lore = Arrays.asList("§7Place libre", "§7Place libre");
				
			}else{
				String[] owners = main.config.getString("bedwars.list.teams." + i + ".owners").split(",");
				if(owners.length == 1){
					lore = Arrays.asList("§c" + owners[0], "§7Place libre");
				}else{
					lore = Arrays.asList("§c" + owners[0], "§c" + owners[1]);
				}
			}
			
			inv.setItem(slot, UtilsInv.makeItem(Cgetteam.getTeamWoolColor(i), Cgetteam.getTeamChatColor(i)+Cgetteam.getTeamStringColor(i), lore));
			slot ++;
		}
		
		
		if(main.config.getInt("bedwars.list.status") == 0 || main.config.getInt("bedwars.list.status") == 5){
			inv.setItem(slot, UtilsInv.makeItem(Material.CLOCK, "§6Il doit y avoir au moin 2 équipes pleines", null));
		}else{
			inv.setItem(slot, UtilsInv.makeItem(Material.PLAYER_HEAD, "§6Rejoindre comme spectateur", null));
		}
		
		return inv;
		
	}
	public void itemJoinClicked(InventoryClickEvent e, main pl){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.RED_WOOL || e.getCurrentItem().getType() == Material.BLUE_WOOL || e.getCurrentItem().getType() == Material.GREEN_WOOL || e.getCurrentItem().getType() == Material.YELLOW_WOOL){
			
			int team = e.getSlot() - 19;
			new join().joinFirstBedWars(p, pl, team);
			e.getInventory().setContents(getJoinInv(p).getContents());
			
		}else if(e.getCurrentItem().getType() == Material.SKELETON_SKULL){
			
			new join().joinFirstBedWars(p, pl, 0);
			
		}
		
	}
	

}

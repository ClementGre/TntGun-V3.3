package fr.themsou.TntWars;


import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.commands.GradeCmd;
import fr.themsou.inv.UtilsInv;
import fr.themsou.main.main;

public class TntWarsInv {

	private main pl;
	
	public TntWarsInv(main pl) {
		this.pl = pl;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// LIST GAMES //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openListGamesInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§4TntWars - Liste des games");
		new UtilsInv().setWalls(inv, 5);
		
		for(int i = 0; i < TntWarsGame.games.size(); i++){
			
			Material material = Material.GLASS;
			String team1 = TntWarsGame.games.get(i).getTeamString(1);
			String team2 = TntWarsGame.games.get(i).getTeamString(2);
			String map = TntWarsGame.games.get(i).getMapName();
			
			String name = "§3Partie Solo - ";
			if(TntWarsGame.games.get(i).isDouble) name = "§3Partie en Duo - ";
			
			
			if(TntWarsGame.games.get(i).isStarted){
				name += "§cDéjà commençée";
			}else{
				name += "§2Attente de joueurs";
				material = TntWarsGame.games.get(i).getMapMaterial();
			}
			
			inv.setItem(19 + i, UtilsInv.makeItem(material, name, Arrays.asList("§cMap : §6" + map, team1, "§6VS", team2, "§7id: " + TntWarsGame.games.get(i).id)));
			
		}
		
		inv.setItem(25, UtilsInv.makeItem(Material.DRIED_KELP_BLOCK, "§3§lNouvelle partie", null));
	
		p.openInventory(inv);
	}
	public void itemListGamesClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.WHITE_STAINED_GLASS_PANE) return;
		
		if(e.getSlot() == 25){
			
			p.closeInventory();
			openMakeGameInv(p);
			
		}else{
			
			if(e.getCurrentItem().hasItemMeta()){
				if(e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().hasLore()){
					if(e.getCurrentItem().getLore().size() == 5){
						
						int id = Integer.parseInt(e.getCurrentItem().getLore().get(4).split(" ")[1]);
						if(TntWarsGame.getInstanceViaID(id) != null){
							
							if(!TntWarsGame.getInstanceViaID(id).isStarted){
								
								p.closeInventory();
								openListPlayersInv(p, id);
								
							}else p.sendMessage("§cCette partie a déjà commençée");
							
						}
					}
				}
			}
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// MAKE GAME ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openMakeGameInv(Player p){
		
		p.openInventory(getMakeGameInv(p, 0, -1));
	}
	public Inventory getMakeGameInv(Player p, int mode, int map){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4TntWars - Création d'une game");
		new UtilsInv().setWalls(inv, 6);
	
		for(String strId : main.configuration.getConfigurationSection("tntwars.maps").getKeys(false)){
			int id = Integer.parseInt(strId);
			
			inv.setItem(47 + id, UtilsInv.makeItem(Material.valueOf(main.configuration.getString("tntwars.maps." + id + ".item")),
							"§3§lMap " + main.configuration.getString("tntwars.maps." + id + ".name"),
							Arrays.asList("§6Mur §e" + main.configuration.getString("tntwars.maps." + id + ".wall"), "§bCliquez pour séléxioner","§cCette option est réservé aux §eVIP","§7id: " + strId)));
		}
		
		if(mode == 0){
			inv.setItem(21, UtilsInv.makeItem(Material.GLASS, "§3§lMode 1V1", Arrays.asList("§bCliquez pour passer en §c2V2","§7id: " + mode)));
		}else{
			inv.setItem(21, UtilsInv.makeItem(Material.GLASS, "§3§lMode 2V2", Arrays.asList("§bCliquez pour passer en §c1V1","§7id: " + mode)));
		}
				
		
		List<String> mapLore = Arrays.asList("§bCliquez pour switcher entre les maps","§cCette option est réservé aux §eVIP","§7id: " + map);
		if(map != -1){
			inv.setItem(23, UtilsInv.makeItem(Material.valueOf(main.configuration.getString("tntwars.maps." + map + ".item")), "§3§lMap " + main.configuration.getString("tntwars.maps." + map + ".name"), mapLore));
		}else{
			inv.setItem(23, UtilsInv.makeItem(Material.HOPPER, "§3§lMap Aléatoire", mapLore));
		}
		
		inv.setItem(31, UtilsInv.makeItem(Material.DRIED_KELP_BLOCK, "§3§lCréer la game", null));
		
		return inv;
	}
	public void itemMakeGamesClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.WHITE_STAINED_GLASS_PANE) return;
		
		int mode = Integer.parseInt(e.getInventory().getItem(21).getItemMeta().getLore().get(1).split(" ")[1]);
		int map = Integer.parseInt(e.getInventory().getItem(23).getItemMeta().getLore().get(2).split(" ")[1]);
		
		if(e.getSlot() == 21){
			
			if(mode == 0) mode = 1; else mode = 0;
			e.getInventory().setContents(getMakeGameInv(p, mode, map).getContents());
			
		}else if(e.getSlot() == 23){
			
			if(new GradeCmd().getPlayerPermition(p.getName()) >= 2){
				
				map++;
				if(map >= main.configuration.getConfigurationSection("tntwars.maps").getKeys(false).size()) map = -1;
				e.getInventory().setContents(getMakeGameInv(p, mode, map).getContents());
				
			}else{
				p.sendMessage("§cCette option est réservée aux §eVIP");
			}
			
			
		}else if(e.getSlot() == 31){
			
			boolean isPrivate = true;
			if(mode == 0) isPrivate = false;
			
			if(TntWarsGame.getInstanceViaPlayer(p) != null){
				int id = TntWarsGame.getInstanceViaPlayer(p).id;
				TntWarsGame.removePlayerInAllGames(p);
				TntWarsGame.getInstanceViaID(id).destroyIfCan();
			}
			
			TntWarsGame game = new TntWarsGame(map, isPrivate);
			openListPlayersInv(p, game.id);
		}else{
			
			if(e.getCurrentItem().hasItemMeta()){
				if(e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().hasLore()){
					if(e.getCurrentItem().getLore().size() == 4 && e.getSlot() > 46 && e.getSlot() < 54){
						
						if(new GradeCmd().getPlayerPermition(p.getName()) >= 2){
							
							int id = Integer.parseInt(e.getCurrentItem().getLore().get(3).split(" ")[1]);
							
							map = id;
							e.getInventory().setContents(getMakeGameInv(p, mode, map).getContents());
							
						}else p.sendMessage("§cCette option est réservée aux §eVIP");
						
							
					}
				}
			}
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// LIST PLAYERS ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openListPlayersInv(Player p, int id){
		
		p.openInventory(getListPlayersInv(p, id));
	}
	public Inventory getListPlayersInv(Player p, int id){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§4TntWars - Séléction de l'équipe §7id: " + id);
		new UtilsInv().setWalls(inv, 5);
		TntWarsGame game = TntWarsGame.getInstanceViaID(id);
		
		if(game.isDouble){
			inv.setItem(21, UtilsInv.makeItem(Material.BLUE_CONCRETE, "§3§lÉquipe Bleu", Arrays.asList(game.getColorStringPlayer(1, 1), game.getColorStringPlayer(1, 2))));
			inv.setItem(23, UtilsInv.makeItem(Material.RED_CONCRETE, "§c§lÉquipe Rouge", Arrays.asList(game.getColorStringPlayer(2, 1), game.getColorStringPlayer(2, 2))));
		}else{
			inv.setItem(21, UtilsInv.makeItem(Material.BLUE_CONCRETE, "§3§lÉquipe Bleu", Arrays.asList(game.getColorStringPlayer(1, 1))));
			inv.setItem(23, UtilsInv.makeItem(Material.RED_CONCRETE, "§c§lÉquipe Rouge", Arrays.asList(game.getColorStringPlayer(2, 1))));
		}
		
		inv.setItem(40, UtilsInv.makeItem(game.getMapMaterial(), "§6Map §e" + game.getMapName(), Arrays.asList("§6Mur §e" + game.getMapWall(), "§6Mode §e" + game.getGameMode(), "§cCliquez pour actualiser")));
		
		return inv;
	}
	public void itemListPlayersClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		String id = e.getView().getTitle().replace("§4TntWars - Séléction de l'équipe §7id: ", "");
		TntWarsGame game = TntWarsGame.getInstanceViaID(Integer.parseInt(id));
		
		if(e.getCurrentItem().getType() == Material.WHITE_STAINED_GLASS_PANE) return;
		
		if(e.getSlot() == 21){
			
			if(!game.isStarted){
				game.addTeam1Player(p);
				e.getInventory().setContents(getListPlayersInv(p, Integer.parseInt(id)).getContents());
				if(game.canStart()) new TntWarsGameEvents().startGame(game, pl);
			}
			
			
		}else if(e.getSlot() == 23){
			
			if(!game.isStarted){
				game.addTeam2Player(p);
				e.getInventory().setContents(getListPlayersInv(p, Integer.parseInt(id)).getContents());
				if(game.canStart()) new TntWarsGameEvents().startGame(game, pl);
			}
			
			
		
		}else if(e.getSlot() == 40){
			
			e.getInventory().setContents(getListPlayersInv(p, Integer.parseInt(id)).getContents());
		}
	}
	
	public void invListPlayersClosed(InventoryCloseEvent e){
		
		String id = e.getView().getTitle().replace("§4TntWars - Séléction de l'équipe §7id: ", "");
		
		if(TntWarsGame.getInstanceViaID(Integer.parseInt(id)) != null){
			TntWarsGame.getInstanceViaID(Integer.parseInt(id)).destroyIfCan();
		}
	}
	
}

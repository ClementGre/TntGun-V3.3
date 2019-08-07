package fr.themsou.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import fr.themsou.main.main;
import fr.themsou.methodes.PInfos;
import fr.themsou.nms.title;

public class AdminInv {
	
	@SuppressWarnings("deprecation")
	public void oppenMainInv(Player p){
			
		Inventory inv = Bukkit.createInventory(null, 6*9, "§cADMIN - §6" + Bukkit.getOnlinePlayers().size() + "/" + main.config.getConfigurationSection("").getKeys(false).size());
		
		for(Player p2 : Bukkit.getOnlinePlayers()){
			
			String pName = p2.getName();
			String timeInfo = "§6Hors ligne depuis : §7" + PInfos.getOfflineTime(pName);
					
			if(Bukkit.getPlayerExact(pName) != null){
				timeInfo = "§6Connecté depuis : §2" + PInfos.getConnectTime(Bukkit.getPlayerExact(pName));
			}
			
			ItemStack a = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta aM = (SkullMeta) a.getItemMeta();
			aM.setOwner(pName);
			aM.setDisplayName("§3§l" + pName);
			aM.setLore(Arrays.asList("§cCliquez pour gérer",
					"§6IP : §3" + PInfos.getIP(pName),
					"§6Double compte : §3" + PInfos.getDoublesComptesPrint(pName),
					"§6Discord : §3" + PInfos.getDiscordLinked(pName),
					"§6Temps de jeu : §3" + PInfos.getTotalTime(pName) + "h",
					"§6Jeux : §b" + PInfos.getGame(p2),
					timeInfo));
			a.setItemMeta(aM);
			inv.addItem(a);
			
		}
		
		
		p.openInventory(inv);
		
	}
	@SuppressWarnings("deprecation")
	public void oppenMainAllInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§cADMIN - §6" + Bukkit.getOnlinePlayers().size() + "/" + main.config.getConfigurationSection("").getKeys(false).size());
		
		for(String pName : main.config.getConfigurationSection("").getKeys(false)){
			
			if(main.config.contains(pName + ".stat.last")){
					
				String timeInfo = "§6Hors ligne depuis : §7" + PInfos.getOfflineTime(pName);
				
				if(Bukkit.getPlayerExact(pName) != null){
					timeInfo = "§6Connecté depuis : §2" + PInfos.getConnectTime(Bukkit.getPlayerExact(pName));
				}
				
				ItemStack a = new ItemStack(Material.PLAYER_HEAD, 1);
				SkullMeta aM = (SkullMeta) a.getItemMeta();
				aM.setOwner(pName);
				aM.setDisplayName("§3§l" + pName);
				aM.setLore(Arrays.asList("§cCliquez pour gérer",
						"§6IP : §3" + PInfos.getIP(pName),
						"§6Double compte : §3" + PInfos.getDoublesComptesPrint(pName),
						"§6Discord : §3" + PInfos.getDiscordLinked(pName),
						"§6Temps de jeu : §3" + PInfos.getTotalTime(pName) + "h",
						timeInfo));
				a.setItemMeta(aM);
				inv.addItem(a);
				
			}
		}
		
		
		p.openInventory(inv);
		
	}
	public void itemMainClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getItemMeta() != null){
			if(e.getCurrentItem().getItemMeta().getDisplayName() != null){
				if(e.getCurrentItem().getItemMeta().getLore() != null){
					if(e.getCurrentItem().getItemMeta().getLore().get(1) != null){
						
						String playerName = e.getCurrentItem().getItemMeta().getDisplayName().replace("§3§l", "");
						openPlayerInv(playerName, p);
						
					}
				}
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void openPlayerInv(String pName, Player admin){
		
		Inventory inv = Bukkit.createInventory(null, 3*9, "§cADMIN - Joueur §l§c" + pName);
		
		String timeInfo = "§6Hors ligne depuis : §7" + PInfos.getOfflineTime(pName);
				
		if(Bukkit.getPlayerExact(pName) != null){
			timeInfo = "§6Connecté depuis : §2" + PInfos.getConnectTime(Bukkit.getPlayerExact(pName));
		}
		
		ItemStack a = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta aM = (SkullMeta) a.getItemMeta();
		aM.setOwner(pName);
		aM.setDisplayName("§3§l" + pName);
		aM.setLore(Arrays.asList("§cCliquez pour gérer",
				"§6IP : §3" + PInfos.getIP(pName),
				"§6Double compte : §3" + PInfos.getDoublesComptesPrint(pName),
				"§6Discord : §3" + PInfos.getDiscordLinked(pName),
				"§6Temps de jeu : §3" + PInfos.getTotalTime(pName) + "h",
				timeInfo));
		a.setItemMeta(aM);
		inv.setItem(4, a);
		
		ItemStack b = new ItemStack(Material.MAGMA_CREAM, 1);
		ItemMeta bM = b.getItemMeta();
		bM.setDisplayName("§c§lSANCTIONER");
		b.setItemMeta(bM);
		inv.setItem(2, b);
		
		ItemStack c = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§c§lAVERTIR");
		c.setItemMeta(cM);
		inv.setItem(6, c);
		
		
		admin.openInventory(inv);
		
	}
	public void itemPlayerClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		String playerName = e.getView().getTitle().replace("§cADMIN - Joueur §l§c", "");
		
		
		if(e.getCurrentItem().getType() == Material.MAGMA_CREAM){
			
			openPunishInv(playerName, p);
			
		}else if(e.getCurrentItem().getType() == Material.NETHER_STAR){
			
			Player player = Bukkit.getPlayerExact(playerName);
			
			if(player != null){
				
				title.sendTitle(player, "§cVotre comportement est inadapté !", "§6Vous risquez un ban...", 60);
				
				p.sendMessage("§c" + playerName + "§6 a été avertis");
				p.closeInventory();
			
			}else{
				p.sendMessage("§c" + playerName + "§6 n'est pas connecté");
				p.closeInventory();
			}
		}
		
	}
	
	public void openPunishInv(String p, Player admin){
		
		Inventory inv = Bukkit.createInventory(null, 3*9, "§4ADMIN - Sanctioner §l§c" + p);
		
		Set<String> section = main.configuration.getConfigurationSection("punish").getKeys(false);	
		String item[] = section.toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
		for(int i = 1; i <= section.size(); i++){
			int number = i - 1;
			
			Material material = Material.getMaterial(main.configuration.getString("punish." + item[number] + ".item"));
			if(material == null) material = Material.BARRIER;
			
			ItemStack a = new ItemStack(material, 1);
			ItemMeta aM = a.getItemMeta();
			aM.setDisplayName("§c" + item[number]);
			
			int level = main.configuration.getInt("punish." + item[number] + ".level");
			List<String> levels = null;
			switch (level){
				case 1: levels = Arrays.asList("Mute 1 Mn","Mute 15 Mn","Mute 60 Mn");
				break;
				case 2: levels = Arrays.asList("Mute 15 Mn","Mute 30 Mn","Mute 60 Mn");
				break;
				case 3: levels = Arrays.asList("Ban 1 J","Ban 5 J","Ban 30 J");
				break;
				case 4: levels = Arrays.asList("Ban 5 J","Ban 30 J","Ban 90 J");
				break;
				case 5: levels = Arrays.asList("Ban 15 J","Ban 50 J","Ban 500 J");
				break;
				case 6: levels = Arrays.asList("Ban 30 J","Ban 90 J","Ban 500 J");
				break;
			}
			
			aM.setLore(levels);
			a.setItemMeta(aM);
			inv.addItem(a);
			
			
		}
		admin.openInventory(inv);
		
		
	}
	public void itemPunishClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getItemMeta() != null){
			if(e.getCurrentItem().getItemMeta().getDisplayName() != null){
				
				Punish CPunish = new Punish();
				String playerName = e.getView().getTitle().replace("§4ADMIN - Sanctioner §l§c", "");
				String punishName = e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", "");
				List<String> lore = e.getCurrentItem().getItemMeta().getLore();
				
				CPunish.punishPlayerProgress(playerName, p.getName(), punishName, lore);
				p.closeInventory();
				
			}
		}
		
	}
}

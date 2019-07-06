package fr.themsou.BedWars;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import fr.themsou.inv.setwalls;
import fr.themsou.main.main;

public class menu {
	
	
	public void setInventory(Inventory menu){
		
		setwalls Csetwalls = new setwalls();
		Csetwalls.setWalls(menu, 6);
		
		ItemStack c = new ItemStack(Material.COMPARATOR, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§3§lCOMMANDES");
		cM.setLore(Arrays.asList("§eListe de toutes les commandes Disponibles"));
		c.setItemMeta(cM);				
		menu.setItem(30, c);
		
		ItemStack d = new ItemStack(Material.DIAMOND, 1);
		ItemMeta dM = d.getItemMeta();
		dM.setDisplayName("§3§lVIP");
		dM.setLore(Arrays.asList("§eVoir les avantages vip"));
		d.setItemMeta(dM);				
		menu.setItem(32, d);
		
		
		
	}
	
	public void setCmdInventory(Inventory menu){
		
		setwalls Csetwalls = new setwalls();
		Csetwalls.setWalls(menu, 6);
		
		ItemStack b = new ItemStack(Material.BOOK, 1);
		ItemMeta bM = b.getItemMeta();
		bM.setDisplayName("§3§lDISCUTIONS");
		bM.setLore(Arrays.asList("§bApprenez a envoyer des messages privées..."));
		b.setItemMeta(bM);				
		menu.setItem(22, b);
		
		ItemStack h = new ItemStack(Material.ARROW, 1);
		ItemMeta hM = h.getItemMeta();
		hM.setDisplayName("§4Retour");
		h.setItemMeta(hM);				
		menu.setItem(40, h);
		
		
		
	}
	
	public Inventory getTeamsInventory(Player p){
		
		Inventory menu = Bukkit.createInventory(null, 5*9, "§4CHOIX DE L'ÉQUIPE");
		
		setwalls Csetwalls = new setwalls();
		Csetwalls.setWalls(menu, 5);
		
		getteam Cgetteam = new getteam();
		
		
		int loc = 20;
		for(int i = 1; i <= 4; i++){
			
			if(main.config.getString("bedwars.list.teams." + i + ".owners") == null){
				
				ItemStack b = new ItemStack(Cgetteam.getTeamWoolColor(i), 1);
				ItemMeta bM = b.getItemMeta();
				bM.setDisplayName(Cgetteam.getTeamChatColor(i) + Cgetteam.getTeamStringColor(i));
				bM.setLore(Arrays.asList("§7Place libre", "§7Place libre"));
				b.setItemMeta(bM);				
				menu.setItem(loc, b);
				
			}else{
				
				String[] owners = main.config.getString("bedwars.list.teams." + i + ".owners").split(",");
				
				if(owners.length == 1){
					
					ItemStack b = new ItemStack(Cgetteam.getTeamWoolColor(i), 1);
					ItemMeta bM = b.getItemMeta();
					bM.setDisplayName(Cgetteam.getTeamChatColor(i) + Cgetteam.getTeamStringColor(i));
					bM.setLore(Arrays.asList("§c" + owners[0], "§7Place libre"));
					b.setItemMeta(bM);				
					menu.setItem(loc, b);
					
				}else{
					
					ItemStack b = new ItemStack(Cgetteam.getTeamWoolColor(i), 1);
					ItemMeta bM = b.getItemMeta();
					bM.setDisplayName(Cgetteam.getTeamChatColor(i) + Cgetteam.getTeamStringColor(i));
					bM.setLore(Arrays.asList("§c" + owners[0], "§c" + owners[1]));
					b.setItemMeta(bM);				
					menu.setItem(loc, b);
					
				}
				
			}
			
			loc ++;
		}
		
		if(main.config.getInt("bedwars.list.status") == 0 || main.config.getInt("bedwars.list.status") == 5){
			
			ItemStack b = new ItemStack(Material.CLOCK, 1);
			ItemMeta bM = b.getItemMeta();
			bM.setDisplayName("§6Il doit y avoir au moin 2 équipes pleines");
			b.setItemMeta(bM);				
			menu.setItem(loc, b);
			
		}else{
			
			ItemStack b = new ItemStack(Material.SKELETON_SKULL, 1);
			ItemMeta bM = b.getItemMeta();
			bM.setDisplayName("§6Rejoindre comme spectateur");
			b.setItemMeta(bM);				
			menu.setItem(loc, b);
			
		}
		
		
		return menu;
		
		
		
		
	}
	public Inventory getShopInventory(Player p, int tabLevel){
		
		Inventory menu = Bukkit.createInventory(null, 6*9, "§cBOUTIQUE");
		
		setwalls Csetwalls = new setwalls();
		Csetwalls.setWalls(menu, 6);
		
		ConfigurationSection sectionShop = main.configuration.getConfigurationSection("bedwars.shop");
		String[] Tabs = sectionShop.getKeys(false).toString().replace("[", "").replace("]", "").split(", ");
		ConfigurationSection sectionTab = sectionShop.getConfigurationSection(Tabs[tabLevel]);
		
		int x = 1;
		for(String tab : sectionShop.getKeys(false)){
			ItemStack item;
			if(tab.equalsIgnoreCase(Tabs[tabLevel])){
				item = createItemStack(p, Material.getMaterial(sectionShop.getString(tab + ".item")), 1, 0, "§6" + tab, Arrays.asList("§cPage courante"));
			}else{
				item = createItemStack(p, Material.getMaterial(sectionShop.getString(tab + ".item")), 1, 0, "§6" + tab, null);
			}
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			menu.setItem(x, item);
			x++;
		}
		
		for(int i = 10; i <= 16; i++){
			menu.setItem(i, createItemStack(p, Material.WHITE_STAINED_GLASS_PANE, 1, 0, " ", null));
		}
		
		int y = 29;
		for(String item : sectionTab.getKeys(false)){
			if(!item.equals("item")){
				
				int amount = sectionTab.getInt(item + ".amount");
				if(amount == 0) amount = 1;
				
				String money = sectionTab.getString(item + ".money");
				char moneyColor = "7".charAt(0);
				if(money.equals("Or")) moneyColor = "6".charAt(0);
				else if(money.equals("Emeraude")) moneyColor = "2".charAt(0);
				String price = "§" + moneyColor + sectionTab.getInt(item + ".price") + " " + money;
				
				if(sectionTab.contains(item + ".data")){
					menu.setItem(y, createItemStack(p, Material.getMaterial(sectionTab.getString(item + ".item")), amount, sectionTab.getInt(item + ".data"), "§r" + item, Arrays.asList(price)));
				}else{
					menu.setItem(y, createItemStack(p, Material.getMaterial(sectionTab.getString(item + ".item")), amount, 0, "§r" + item, Arrays.asList(price)));
				}
				
				if(sectionTab.getKeys(false).toString().replace("[", "").replace("]", "").split(", ").length >= 7){
					if(y <= 28){
						y = y + 10;
					}else{
						y = y - 9;
					}
				}else y++;
				
			}
		}
		
		return menu;
	}
	public Inventory getUpgradesInventory(Player p){
		
		Inventory menu = Bukkit.createInventory(null, 5*9, "§cAMELIORATIONS");
		
		setwalls Csetwalls = new setwalls();
		Csetwalls.setWalls(menu, 5);
		
		ConfigurationSection section = main.configuration.getConfigurationSection("bedwars.upgrades");
		int i = 20;
		for(String upgrade : section.getKeys(false)){
			
			ConfigurationSection levelsSection = section.getConfigurationSection(upgrade + ".levels");
			ArrayList<String> levelsArray = new ArrayList<String>();
			int actLevel = main.config.getInt("bedwars.list.teams." + new getteam().getplayerteam(p) + ".upgrades." + upgrade);
			for(String level : levelsSection.getKeys(false)){
				
				if(actLevel >= 1){
					levelsArray.add("§2" + level + " : §3" + levelsSection.getInt(level));
				}else{
					levelsArray.add("§6" + level + " : §3" + levelsSection.getInt(level));
				}
				
				actLevel--;
				
				
			}
			menu.setItem(i, createItemStack(p, Material.getMaterial(section.getString(upgrade + ".item")), 1, 0, "§c" + upgrade, ((List<String>)levelsArray)));
			
			i++;
		}
		
		return menu;
		
	}
	
	private ItemStack createItemStack(Player p, Material material, int quantité, int data, String name, java.util.List<String> lore){
		
		ItemStack b = new ItemStack(material, quantité);
		
		
		if(material == Material.POTION){
			
			b = new ItemStack(material, quantité);
			
			PotionEffectType potionType = PotionEffectType.INVISIBILITY;
				
			if(data == 1) potionType = PotionEffectType.SPEED;
			else if(data == 2) potionType = PotionEffectType.JUMP;
				
			PotionMeta potion = (PotionMeta) b.getItemMeta();
			potion.addCustomEffect(new PotionEffect(potionType, 900, 3), true);
			b.setItemMeta(potion);
			
		}else if(material == Material.BOW){
			
			b = new ItemStack(material, quantité);
			
			if(data == 1){
				b.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
				b.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
			}
			
		}else if(material == Material.WHITE_WOOL){
			
			b = new ItemStack(new getteam().getTeamWoolColor(new getteam().getplayerteam(p)), quantité);
			
		}else if(material == Material.WHITE_CONCRETE){
			
			b = new ItemStack(new getteam().getTeamConcreteColor(new getteam().getplayerteam(p)), quantité);
			
		}else if(material == Material.WHITE_STAINED_GLASS_PANE){
			
			b = new ItemStack(new getteam().getTeamPaneColor(new getteam().getplayerteam(p)), quantité);
			
		}else if(material == Material.IRON_SWORD || material == Material.DIAMOND_SWORD){
			
			int swordLevel = main.config.getInt("bedwars.list.teams." + new getteam().getplayerteam(p) + ".upgrades.Tranchant");
			if(swordLevel != 0) b.addEnchantment(Enchantment.DAMAGE_ALL, swordLevel);
			
		}else if(material == Material.DIAMOND_CHESTPLATE || material == Material.IRON_CHESTPLATE || material == Material.CHAINMAIL_CHESTPLATE){
			
			int armorLevel = main.config.getInt("bedwars.list.teams." + new getteam().getplayerteam(p) + ".upgrades.Protection");
			if(armorLevel != 0) b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, armorLevel);
			
		}else if(material == Material.IRON_AXE || material == Material.DIAMOND_AXE || material == Material.IRON_PICKAXE || material == Material.DIAMOND_PICKAXE){
			
			int toolLevel = main.config.getInt("bedwars.list.teams." + new getteam().getplayerteam(p) + ".upgrades.Efficacité");
			if(toolLevel != 0) b.addEnchantment(Enchantment.DIG_SPEED, toolLevel);
			
		}
		
		ItemMeta bM = b.getItemMeta();
		bM.setDisplayName(name);
		if(lore != null) bM.setLore(lore);
		b.setItemMeta(bM);		
		
		
		
		
		return b;
		
	}
	

}

package fr.themsou.BedWars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import fr.themsou.inv.UtilsInv;
import fr.themsou.main.main;

public class BedWarsPNJInv {
	
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// SHOP ////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openShopInv(Player p, int tabLevel){
		
		p.openInventory(getShopInv(p, tabLevel));
	}
	public Inventory getShopInv(Player p, int tabLevel){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4BedWars - Boutique");
		new UtilsInv().setWalls(inv, 6);
		
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
			inv.setItem(x, item);
			x++;
		}
		
		for(int i = 10; i <= 16; i++){
			inv.setItem(i, createItemStack(p, Material.WHITE_STAINED_GLASS_PANE, 1, 0, "§e", null));
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
					inv.setItem(y, createItemStack(p, Material.getMaterial(sectionTab.getString(item + ".item")), amount, sectionTab.getInt(item + ".data"), "§r" + item, Arrays.asList(price)));
				}else{
					inv.setItem(y, createItemStack(p, Material.getMaterial(sectionTab.getString(item + ".item")), amount, 0, "§r" + item, Arrays.asList(price)));
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
		
		return inv;
	}
	public void itemShopClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() != Material.WHITE_STAINED_GLASS_PANE || e.getCurrentItem().getType() != Material.RED_STAINED_GLASS_PANE || e.getCurrentItem().getType() != Material.BLUE_STAINED_GLASS_PANE || e.getCurrentItem().getType() != Material.GREEN_STAINED_GLASS_PANE || e.getCurrentItem().getType() != Material.YELLOW_STAINED_GLASS_PANE){
			
			String itemName = e.getCurrentItem().getItemMeta().getDisplayName();

			if(itemName.contains("§6")){
				
				int tab = e.getSlot() - 1;
				e.getInventory().setContents(getShopInv(p, tab).getContents());
				
			}else if(!itemName.equals("§e")){
				
				ItemStack item = e.getCurrentItem();
				
				int price = Integer.parseInt(item.getItemMeta().getLore().get(0).split(" ")[0].replace("§7", "").replace("§6", "").replace("§2", ""));
				String moneyName = item.getItemMeta().getLore().get(0).split(" ")[1];
				String moneyColor = item.getItemMeta().getLore().get(0).split(" ")[0].replace(price + "", "");
				Material moneyMaterial = Material.IRON_INGOT;
						
				if(moneyName.equalsIgnoreCase("Or")) moneyMaterial = Material.GOLD_INGOT;
				else if(moneyName.equalsIgnoreCase("Emeraude")) moneyMaterial = Material.EMERALD;
				
				if(p.getInventory().containsAtLeast(new ItemStack(moneyMaterial), price)){
					
					if(item.getType() == Material.STONE_SWORD || item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD){
						if(p.getInventory().contains(Material.WOODEN_SWORD)){
							p.getInventory().remove(Material.WOODEN_SWORD);
						}
					}
					
					if(item.getType() != Material.CHAINMAIL_CHESTPLATE && item.getType() != Material.IRON_CHESTPLATE && item.getType() != Material.DIAMOND_CHESTPLATE){
						
						p.getInventory().removeItem(new ItemStack(moneyMaterial, price));
						ItemStack itemToGive = item.clone();
						ItemMeta itemToGiveM = itemToGive.getItemMeta();
						itemToGiveM.setLore(null);
						itemToGive.setItemMeta(itemToGiveM);
						p.getInventory().addItem(itemToGive);
						p.sendMessage("§6Vous venez d'acheter §c" + item.getAmount() + " " + itemName.replace("§r", "§c"));
						
					}else{
						int team = new getteam().getplayerteam(p);
						if(item.getType() == Material.CHAINMAIL_CHESTPLATE){
							if(main.config.getInt("bedwars.list.teams." + team + ".armor." + p.getName()) <= 0){
								p.getInventory().removeItem(new ItemStack(moneyMaterial, price));
								main.config.set("bedwars.list.teams." + team + ".armor." + p.getName(), 1);
								p.sendMessage("§6Vous venez d'acheter §c" + item.getAmount() + " " + itemName.replace("§r", "§c"));
							}
							else p.sendMessage("§cVous avez déjà acheté ce niveau d'armure !");
						}
						else if(item.getType() == Material.IRON_CHESTPLATE){
							if(main.config.getInt("bedwars.list.teams." + team + ".armor." + p.getName()) <= 1){
								p.getInventory().removeItem(new ItemStack(moneyMaterial, price));
								main.config.set("bedwars.list.teams." + team + ".armor." + p.getName(), 2);
								p.sendMessage("§6Vous venez d'acheter §c" + item.getAmount() + " " + itemName.replace("§r", "§c"));
							}
							else p.sendMessage("§cVous avez déjà acheté ce niveau d'armure !");
						}
						else if(item.getType() == Material.DIAMOND_CHESTPLATE){
							if(main.config.getInt("bedwars.list.teams." + team + ".armor." + p.getName()) <= 2){
								p.getInventory().removeItem(new ItemStack(moneyMaterial, price));
								main.config.set("bedwars.list.teams." + team + ".armor." + p.getName(), 3);
								p.sendMessage("§6Vous venez d'acheter §c" + item.getAmount() + " " + itemName.replace("§r", "§c"));
							}
							else p.sendMessage("§cVous avez déjà acheté ce niveau d'armure !");
								
							
						}
					}
					
				}else p.sendMessage("§cVous devez avoir " + moneyColor + price + " " + moneyName);
				
				
			}
			
		}
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// UPGRADES ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openUpgradesInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 5*9, "§4BedWars - Améliorations");
		new UtilsInv().setWalls(inv, 5);
		
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
			inv.setItem(i, createItemStack(p, Material.getMaterial(section.getString(upgrade + ".item")), 1, 0, "§c" + upgrade, ((List<String>)levelsArray)));
			
			i++;
		}
		
		p.openInventory(inv);
		
	}
	public void itemUpgradesClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getItemMeta() != null){
			
			String name = e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", "");
			
			if(main.configuration.contains("bedwars.upgrades." + name)){
				
				getteam Cgetteam = new getteam();
				int team = Cgetteam.getplayerteam(p);
				String[] levels = main.configuration.getConfigurationSection("bedwars.upgrades." + name + ".levels").getKeys(false).toString().replace("[", "").replace("]", "").split(", ");
				int actLevel = main.config.getInt("bedwars.list.teams." + team + ".upgrades." + name);

				if(actLevel < levels.length){
					
					int diamond = main.configuration.getInt("bedwars.upgrades." + name + ".levels." + levels[actLevel]);
					if(p.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND, 1), diamond)){
						p.getInventory().removeItem(new ItemStack(Material.DIAMOND, diamond));
						for(Player players : Bukkit.getWorld("BedWars").getPlayers()){
							if(Cgetteam.getplayerteam(players) == team){
								players.sendMessage("§c" + p.getName() + " §6vient d'acheter l'amélioration §c" + levels[actLevel]);
							}
						}
						main.config.set("bedwars.list.teams." + team + ".upgrades." + name, actLevel + 1);
						p.closeInventory();
						openUpgradesInv(p);
						
					}else{
						p.closeInventory();
						p.sendMessage("§cVous devez avoir §6" + diamond + " §Cdiamant");
					}
					
				}else p.sendMessage("§cVous avez déjà amélioré cette compétence au maximumm !");
				
			}
		}
		
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

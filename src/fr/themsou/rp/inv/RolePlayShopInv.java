package fr.themsou.rp.inv;

import java.math.BigDecimal;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.themsou.commands.GradeCmd;
import fr.themsou.inv.HubInv;
import fr.themsou.inv.UtilsInv;
import fr.themsou.main.main;

public class RolePlayShopInv {

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// EURO SHOP ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void openEuroInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4SHOP");
		
		for(String strMaterial : main.configuration.getConfigurationSection("roleplay.shop").getKeys(false)){
			
			addItem(inv, Material.valueOf(strMaterial), main.configuration.getInt("roleplay.shop." + strMaterial));
			
		}
		p.openInventory(inv);
		
	}

	@SuppressWarnings("deprecation")
	public void itemEuroClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(p.getGameMode() == GameMode.SURVIVAL){
			if(e.getCurrentItem().hasItemMeta()){
				if(e.getCurrentItem().getItemMeta().hasLore()){
					
					if(e.getCurrentItem().getItemMeta().getLore().get(0).equals("RETOUR")){
						new HubInv().openInv(p);
					}
					
///////////////////////////////// ACHAT X1
					
					else if(e.getAction() == InventoryAction.PICKUP_ALL){

						double priceTo64 = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(4));
						
						BigDecimal priceTo1 = new BigDecimal(priceTo64 / 64);
						priceTo1 = priceTo1.setScale(1, BigDecimal.ROUND_DOWN);

						String name = e.getCurrentItem().getType() + "";
						ItemStack item = new ItemStack(e.getCurrentItem().getType(), 1);
						item.setData(e.getCurrentItem().getData());

						if(main.economy.getBalance(p) >= priceTo1.doubleValue()){

							main.economy.withdrawPlayer(p.getName(), priceTo1.doubleValue());
							p.sendMessage("§bVous venez d'acheter§3 1 " + name + "§b pour §3" + priceTo1.doubleValue());
							p.getInventory().addItem(item);

						}else p.sendMessage("§cVous devez avoir §4" + priceTo1.doubleValue() + " € §c pour pouvoir acheter§4 1 " + name);
					}
/////////////////////////////////// ACHAT X64

					else if(e.getAction() == InventoryAction.PICKUP_HALF){
						
						double priceTo64 = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(4));
						
						String name = e.getCurrentItem().getType() + "";
						ItemStack item = new ItemStack(e.getCurrentItem().getType() , 64);
						item.setData(e.getCurrentItem().getData());

						if(main.economy.getBalance(p) >= priceTo64){

							main.economy.withdrawPlayer(p.getName(), priceTo64);
							p.sendMessage("§bVous venez d'acheter§3 64 " + name + "§b pour §3" + priceTo64);
							p.getInventory().addItem(item);

						}else p.sendMessage("§cVous devez avoir §4" + priceTo64 + " € §c pour pouvoir acheter§4 1 " + name);

					}
				}
//////////////////////////////////// VENTE

			}else{
				
				String name = e.getCurrentItem().getType() + "";
				ItemStack item = e.getCurrentItem();
				
				if(main.config.contains("shop.list." + item.getType().toString())){
					
					double priceTo64 = main.config.getDouble("shop.list." + item.getType().toString());
					
					BigDecimal priceTo1 = new BigDecimal(priceTo64 / 64);
					priceTo1 = priceTo1.setScale(1, BigDecimal.ROUND_DOWN);
					
					BigDecimal priceToX = new BigDecimal(priceTo1.doubleValue() / 10 * item.getAmount());
					priceToX = priceToX.setScale(1, BigDecimal.ROUND_DOWN);

					main.economy.depositPlayer(p, priceToX.doubleValue());
					p.sendMessage("§bVous venez de vendre §3" + e.getCurrentItem().getAmount() + " " + e.getCurrentItem().getType() +"§b pour §3" + priceToX + " €");
					
					p.getInventory().removeItem(item);

					
				}else p.sendMessage("§cLes §4" + name + "§c ne sont pas à vendre");

			}
		}else p.sendMessage("§cveuillez passer en mode survie");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// PTS SHOP ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openPtsInventory(Player p){
		
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4SHOP DE POINTS");
		new UtilsInv().setWalls(inv, 6);
		
		inv.setItem(20, UtilsInv.makeItem(Material.DIAMOND, "§fGrade VIP", Arrays.asList("§620 000 Points")));
		inv.setItem(22, UtilsInv.makeItem(Material.EMERALD, "§f1 000 €", Arrays.asList("§62 000 Points")));
		inv.setItem(24, UtilsInv.makeItem(Material.DIAMOND_CHESTPLATE, "§fFull P4U3", Arrays.asList("§68 000 Points")));
		
		inv.setItem(30, UtilsInv.makeItem(Material.ELYTRA, "§fElytra", Arrays.asList("§610 000 Points")));
		inv.setItem(32, UtilsInv.makeItem(Material.TOTEM_OF_UNDYING, "§fTotem d'imortalité", Arrays.asList("§65 000 Points")));
		
		inv.setItem(49, UtilsInv.makeItem(Material.ARROW, "§cRETOUR", Arrays.asList("")));
		
		p.openInventory(inv);
	}
	public void itemPtsClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.DIAMOND){
			
			p.closeInventory();
			if(main.config.getInt(p.getName() + ".points") >= 20000){
				if(new GradeCmd().getPlayerPermition(p.getName()) == 1){
					
					main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 20000);
					p.sendMessage("§bVous venez de dépencer §620 000 pts§b pour un garde §3VIP");
					new GradeCmd().changePlayerGradeWithConsole(p.getName(), "Vip");
					
				}else p.sendMessage("§cVous Avez déja un grade");
			}else p.sendMessage("§cVous n'avez pas assez de points");
			
		}else if(e.getCurrentItem().getType() == Material.EMERALD){
			
			p.closeInventory();
			if(main.config.getInt(p.getName() + ".points") >= 2000){
					
				main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 2000);
				p.sendMessage("§bVous venez de dépencer §62 000 pts§b pour §31 000€");
				main.economy.depositPlayer(p, 1000);
					
			}else p.sendMessage("§cVous n'avez pas assez de points");
			
		}else if(e.getCurrentItem().getType() == Material.DIAMOND_CHESTPLATE){
			
			p.closeInventory();
			if(main.config.getInt(p.getName() + ".points") >= 8000){
					
				main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 8000);
				p.sendMessage("§bVous venez de dépencer §68 000 pts§b pour un §3Stuff Full P4U3");
				ItemStack a = new ItemStack(Material.DIAMOND_HELMET, 1); 
				a.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				a.addEnchantment(Enchantment.DURABILITY, 3);
				p.getInventory().addItem(a); a.setType(Material.DIAMOND_CHESTPLATE);
				p.getInventory().addItem(a); a.setType(Material.DIAMOND_LEGGINGS);
				p.getInventory().addItem(a); a.setType(Material.DIAMOND_BOOTS);
				p.getInventory().addItem(a);
				
			}else p.sendMessage("§cVous n'avez pas assez de points");
			
		}else if(e.getCurrentItem().getType() == Material.ELYTRA){
			
			p.closeInventory();
			if(main.config.getInt(p.getName() + ".points") >= 10000){
					
				main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 10000);
				p.sendMessage("§bVous venez de dépencer §610 000 pts§b pour des §3Elytra");
				p.getInventory().addItem(new ItemStack(Material.ELYTRA, 1));
				
			}else p.sendMessage("§cVous n'avez pas assez de points");
			
		}else if(e.getCurrentItem().getType() == Material.TOTEM_OF_UNDYING){
			
			p.closeInventory();
			if(main.config.getInt(p.getName() + ".points") >= 5000){
					
				main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 5000);
				p.sendMessage("§bVous venez de dépencer §65 000 pts§b pour un §3Totem d'immortalité");
				p.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
					
			}else p.sendMessage("§cVous n'avez pas assez de points");
			
		}else if(e.getCurrentItem().getType() == Material.ARROW){
		
			p.closeInventory();
			new RolePlayMainInv().openInv(p);
			
		}
		
	}
	
	
	
	public void addItem(Inventory inv, Material material, double priceTo64){
		
		BigDecimal priceTo1 = new BigDecimal(priceTo64 / 64);
		priceTo1 = priceTo1.setScale(1, BigDecimal.ROUND_DOWN);
		
		BigDecimal sellPriceTo64 = new BigDecimal(priceTo64 / 10);
		sellPriceTo64 = sellPriceTo64.setScale(1, BigDecimal.ROUND_DOWN);
		
		BigDecimal sellPriceTo1 = new BigDecimal(sellPriceTo64.doubleValue() / 64);
		sellPriceTo1 = sellPriceTo1.setScale(1, BigDecimal.ROUND_DOWN);
		
		
		ItemStack a = new ItemStack(material, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setLore(Arrays.asList("§cACHETER X64 :§6 " + priceTo64 + " € §3CLIQUE DROIT","§cACHETER X1 :§6 " + priceTo1.doubleValue() + " € §3CLIQUE GAUCHE","§cVENDRE X64 :§6 " + sellPriceTo64.doubleValue() + " € §3CLIQUEZ DANS VOTRE INVENTAIRE","§cVENDRE X1 :§6 " + sellPriceTo1.doubleValue() + " € §3CLIQUEZ DANS VOTRE INVENTAIRE", priceTo64 + ""));
		a.setItemMeta(aM);				
		inv.addItem(a);
	}

}

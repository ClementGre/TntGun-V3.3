package fr.themsou.rp.ent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.themsou.main.main;
import fr.themsou.methodes.SimpleItem;
import fr.themsou.rp.claim.GetZoneId;
import fr.themsou.rp.claim.Spawns;

@SuppressWarnings("deprecation")
public class Sign {
	
	public ItemStack makeSign(int price, ItemStack itemToSell, String desc){
		
		ItemStack item = new ItemStack(Material.OAK_SIGN, 1);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName("§r§bPancarte de vente (" + desc + ")");
		int id = new Random().nextInt(9999999);
		itemM.setLore(Arrays.asList(desc, price + "-" + itemToSell.getAmount() + "-" + id, itemToSell.getType().toString()));
		main.config.set("items.list." + id, itemToSell.getType().toString());
		item.setItemMeta(itemM);
		
		return item;
		
	}
	
	public void placeSign(Block block, ItemStack item, boolean wallSign, BlockFace direction){
		
		if(block.getType() != Material.AIR) return;
		
		String desc = item.getItemMeta().getLore().get(0);
		String price = item.getItemMeta().getLore().get(1).split("-")[0];
		String amount = item.getItemMeta().getLore().get(1).split("-")[1];
		String id = item.getItemMeta().getLore().get(1).split("-")[2];
		String material = item.getItemMeta().getLore().get(2);
		
		if(wallSign) block.setType(Material.OAK_WALL_SIGN);
		else block.setType(Material.OAK_SIGN);
		
		org.bukkit.block.Sign sign = (org.bukkit.block.Sign) block.getState();
		
		sign.setLine(0, "§3[Acheter]");
		sign.setLine(1, "§4" + desc);
		sign.setLine(2, "§4" + price + "€ pour " + amount);
		sign.setLine(3, id + " - " + material);
		
		if(wallSign){
			
			String dataString = "minecraft:oak_wall_sign[facing=" + direction.toString().toLowerCase() + ",waterlogged=false]";
			BlockData data = Bukkit.createBlockData(dataString);
			sign.setBlockData(data);
			
			
		}else{
			
			org.bukkit.material.Sign data = (org.bukkit.material.Sign) block.getState().getData();
			data.setFacingDirection(direction.getOppositeFace());
			sign.setData(data);
		}
		
		sign.update();
		
	}
	
	public void clickSign(Player p, BlockState block){
		
		org.bukkit.block.Sign sign = (org.bukkit.block.Sign) block;
		
		
		if(sign.getLine(0).equals("§3[Acheter]")){
			
			if(p.getGameMode() == GameMode.SURVIVAL){
				
				int claim = new GetZoneId().getIdOfPlayerZone(block.getLocation());
				String spawn = new Spawns().getSpawnNameWithId(claim);
				
				if(claim != 0){
					if(!main.config.getString("claim.list." + spawn + "." + claim + ".owner").equals("l'etat") && main.config.getString("claim.list." + spawn + "." + claim + ".type").equals("ins")){
						
						String ent = main.config.getString("claim.list." + spawn + "." + claim + ".owner").split(",")[0];
						String desc = sign.getLine(1).replace("§4", "");
						
						int price = Integer.parseInt(sign.getLine(2).split(" ")[0].replace("€", "").replace("§4", ""));
						int amount = Integer.parseInt(sign.getLine(2).split(" ")[2]);
						int itemId = Integer.parseInt(sign.getLine(3).split(" - ")[0]);
							
						ItemStack toSell = new ItemStack(Material.matchMaterial(main.config.getString("items.list." + itemId)), amount);
						
						
						if(main.economy.getBalance(p) >= price){
								
							int x = sign.getLocation().getBlockX();
							int y = sign.getLocation().getBlockY();
							int z = sign.getLocation().getBlockZ();
							World world = Bukkit.getWorld("world");
							Location src = null;
							Chest chest = null;
							
							x += 1;
							if(new Location(world, x, y, z).getBlock().getType() == Material.CHEST || new Location(world, x, y, z).getBlock().getType() == Material.TRAPPED_CHEST){
								src = new Location(world, x, y, z);
							}
							x -= 2;
							if(new Location(world, x, y, z).getBlock().getType() == Material.CHEST || new Location(world, x, y, z).getBlock().getType() == Material.TRAPPED_CHEST){
								src = new Location(world, x, y, z);
							}
							z += 1;
							x += 1;
							if(new Location(world, x, y, z).getBlock().getType() == Material.CHEST || new Location(world, x, y, z).getBlock().getType() == Material.TRAPPED_CHEST){
								src = new Location(world, x, y, z);
							}
							z -= 2;
							if(new Location(world, x, y, z).getBlock().getType() == Material.CHEST || new Location(world, x, y, z).getBlock().getType() == Material.TRAPPED_CHEST){
								src = new Location(world, x, y, z);
							}
							
							if(src != null){
								
								chest = (Chest) src.getBlock().getState();
								
							}else if(main.config.contains("ent.list." + ent + ".src")){
								
								ConfigurationSection srcs = main.config.getConfigurationSection("ent.list." + ent + ".src");
								
								for(String id : srcs.getKeys(false)){
									
									Location chestLoc = new Location(world,
											main.config.getInt("ent.list." + ent + ".src." + id + ".x"),
											main.config.getInt("ent.list." + ent + ".src." + id + ".y"),
											main.config.getInt("ent.list." + ent + ".src." + id + ".z"));
									
									if(chestLoc.getBlock().getType() == Material.CHEST || chestLoc.getBlock().getType() == Material.TRAPPED_CHEST){
										
										if(contains(((Chest) chestLoc.getBlock().getState()).getInventory(), toSell)){
											chest = (Chest) chestLoc.getBlock().getState();
										}
										
									}
									
								}
								
								if(chest == null){
									if(!main.config.contains(p.getName() + ".rp.ent.name"))
										p.sendMessage("§cCet item n'est plus en stock.");
									else if(main.config.getString(p.getName() + ".rp.ent.name").equals(ent))
										p.sendMessage("§cAucun coffre de ressource détecté, contenant cet item, reremplissez le coffre.");
									else p.sendMessage("§cCet item n'est plus en stock.");
									return;
								}
								
								
							}else{
								if(!main.config.contains(p.getName() + ".rp.ent.name"))
									p.sendMessage("§cUne erreur est survenue, impossible d'acheter cet item.");
								else if(main.config.getString(p.getName() + ".rp.ent.name").equals(ent))
									p.sendMessage("§cAucun coffre de ressource détecté, placez un coffre à coté de la pancarte ou définissez un coffre de ressources.");
								else p.sendMessage("§cUne erreur est survenue, impossible d'acheter cet item.");
								return;
							}
							
							
							if(contains(chest.getInventory(), toSell)){
								
								main.config.set("ent.list." + ent + ".money", main.config.getInt("ent.list." + ent + ".money") + price);
								main.economy.withdrawPlayer(p, price);
								p.sendMessage("§6Vous venez d'acheter §c" + amount + " " + desc + "§6 à §c" + ent + "§6 pour §c" + price + "€");
								
								if(toSell.getMaxStackSize() == 1 || toSell.getType() == Material.WRITTEN_BOOK){
									int slot = chest.getInventory().first(toSell.getType());
									p.getInventory().addItem(chest.getInventory().getItem(slot));
									chest.getInventory().setItem(slot, null);
								}else{
									chest.getInventory().removeItem(toSell);
									p.getInventory().addItem(toSell);
								}
								
								if(!main.config.contains(p.getName() + ".rp.ent.name")){
									main.config.set("ent.list." + ent + ".buyers." + p.getName(), main.config.getInt("ent.list." + ent + ".buyers." + p.getName()) + price);
								}else{
									if(!main.config.getString(p.getName() + ".rp.ent.name").equals(ent))
										main.config.set("ent.list." + ent + ".buyers." + p.getName(), main.config.getInt("ent.list." + ent + ".buyers." + p.getName()) + price);
								}
									
							}else p.sendMessage("§cCet item n'est plus en stock.");
							
							
						}else p.sendMessage("§cVous devez avoir §4" + price + " € §cpour acheter cet item");
						
					}else p.sendMessage("§cCette pancarte de vente ne se situe pas dans un claim d'entreprise (industrie)");
				}else p.sendMessage("§cCette pancarte de vente ne se situe pas dans un claim d'entreprise (industrie)");
				
			}else{
				p.sendMessage("§cVeuillez acheter cet item en mode survie");
			}
		}
		
	}
	
	public boolean contains(Inventory inv, ItemStack item){
		
		HashMap<SimpleItem, Integer> items = stackItems(inv.getContents());
		
		for(SimpleItem key : items.keySet()){
			
			if(key.getType() == item.getType()){
				if(key.getDamage() == item.getDurability()){
					if(items.get(key) >= item.getAmount()){
						return true;
					}
				}
			}
			
		}
		
		return false;
		
	}
	
	public HashMap<SimpleItem, Integer> stackItems(ItemStack[] inv){
	    
	    HashMap<SimpleItem, Integer> stacked = new HashMap<>();
	    
	    for(ItemStack item : inv){
	        if(item != null){
	            if(item.getType() != Material.AIR){
	                
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

}

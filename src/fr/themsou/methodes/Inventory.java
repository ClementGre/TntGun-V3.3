package fr.themsou.methodes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.dynmap.snakeyaml.external.biz.base64Coder.Base64Coder;

import fr.themsou.main.main;
public class Inventory {
	
	public Inventory(){
		
	}
	
	public void savePlayerInventory(Player player, String key){
		
		main.config.set(player.getName() + ".inv." + key + ".items", toBase64(player.getInventory().getContents()));
		main.config.set(player.getName() + ".inv." + key + ".health", player.getHealth());
		main.config.set(player.getName() + ".inv." + key + ".food", player.getFoodLevel());
		
		
	}
	public void loadPlayerInventory(Player player, String key){
		
		if(main.config.contains(player.getName() + ".inv." + key)){
			
			try{
				player.getInventory().setContents(fromBase64(main.config.getString(player.getName() + ".inv." + key + ".items")));
			}catch(IllegalArgumentException | IOException e){
				e.printStackTrace();
			}
			
			player.setHealth(main.config.getInt(player.getName() + ".inv." + key + ".health"));
			player.setFoodLevel(main.config.getInt(player.getName() + ".inv." + key + ".food"));
			
		}else{
			player.getInventory().clear();
		}
		
	}
	
	public void saveInventory(String p, ItemStack[] itemStacks, String key){
		
		main.config.set(p + ".inv." + key + ".items", toBase64(itemStacks));
		
		System.out.println("L'inventaire de " + p + " a bien été sauvegardé avec la clé : " + key);
		
	}
	public ItemStack[] loadInventory(String p, String key){
		
		try{
			
			if(main.config.contains(p + ".inv." + key + ".items")){
				return fromBase64(main.config.getString(p + ".inv." + key + ".items"));
			}
				
		}catch(IllegalArgumentException | IOException e){
			e.printStackTrace();
		}
		
		return new ItemStack[0];
	}
	
	public String toBase64(ItemStack[] itemStacks) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
       
            dataOutput.writeInt(itemStacks.length);
            
            // Save every element in the list
            for (int i = 0; i < itemStacks.length; i++) {
                dataOutput.writeObject(itemStacks[i]);
            }
       
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }    
    }

    public ItemStack[] fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            
            ItemStack[] itemStacks = new ItemStack[dataInput.readInt()];
            
            // Read the serialized inventory
            for(int i = 0; i < itemStacks.length; i++){
            	
            	Object item = dataInput.readObject();
            	
            	if(((ItemStack) item) != null){
            		 itemStacks[i] = (ItemStack) item;
            	}else{
            		itemStacks[i] = new ItemStack(Material.AIR);
            	}
            }
            dataInput.close();
            return itemStacks;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
	
	/*public void saveInventory(Player player, String key){
		
		System.out.println("L'inventaire de " + player.getName() + " a bien été sauvegardé avec la clé : " + key);

		int slot = 0;
		SlotType type = SlotType.CONTAINER;
		System.out.println(player.getInventory().getContents().length);
		for(ItemStack item : player.getInventory().getContents()){
			
			System.out.println(item.getType());
			main.config.set(player.getName() + ".inv." + key + "." + type.toString() + "." + slot + ".type", item.getType().toString());
			main.config.set(player.getName() + ".inv." + key + "." + type.toString() + "." + slot + ".amount", item.getAmount());
			
			if(item.getItemMeta() != null){
				if(item.getItemMeta().getDisplayName() != null){
					main.config.set(player.getName() + ".inv." + key + "." + type.toString() + "." + slot + ".name", item.getItemMeta().getDisplayName());
				}
				if(item.getItemMeta().getLore() != null){
					int i = 0;
					for(String lore : item.getItemMeta().getLore()){
						main.config.set(player.getName() + ".inv." + key + "." + type.toString() + "." + slot + ".lore." + i, lore);
						i++;
					}
				}
			}
			
			if(item.getItemFlags() != null){
				int i = 0;
				for(ItemFlag flag : item.getItemFlags()){
					main.config.set(player.getName() + ".inv." + key + "." + type.toString() + "." + slot + ".nbt." + i, flag.toString());
					i++;
				}
			}
			
			if(item.getEnchantments() != null){
				int i = 0;
				for(Enchantment enchant : item.getEnchantments().keySet()){
					main.config.set(player.getName() + ".inv." + key + "." + type.toString() + "." + slot + ".enchant." + i + ".enc", enchant.toString());
					main.config.set(player.getName() + ".inv." + key + "." + type.toString() + "." + slot + ".enchant." + i + ".lvl", item.getEnchantments().get(enchant));
					i++;
				}
			}
			
			System.out.println(slot);
			slot ++;
		}
		
		
		
	}
	
	@SuppressWarnings({ "null", "deprecation" })
	public void loadInventory(Player player, String key){
		
		System.out.println("L'inventaire de " + player.getName() + " a bien été chargé avec la clé : " + key);
		
		player.getInventory().clear();
		
		if(main.config.contains(player.getName() + ".inv." + key)){
			
			for(String type : main.config.getConfigurationSection(player.getName() + ".inv." + key).getKeys(false)){
				
				for(String slot : main.config.getConfigurationSection(player.getName() + ".inv." + key + "." + type).getKeys(false)){
					
					ItemStack item = new ItemStack(Material.valueOf(main.config.getString(player.getName() + ".inv." + key + "." + type + "." + slot + ".type")));
					item.setAmount(main.config.getInt(player.getName() + ".inv." + key + "." + type + "." + slot + ".amount"));
					ItemMeta meta = item.getItemMeta();
					
					if(main.config.contains(player.getName() + ".inv." + key + "." + type + "." + slot + ".name")){
						meta.setDisplayName(main.config.getString(player.getName() + ".inv." + key + "." + type + "." + slot + ".name"));
					}
					
					if(main.config.contains(player.getName() + ".inv." + key + "." + type + "." + slot + ".lore")){
						
						List<String> lores = null;
						for(String i : main.config.getConfigurationSection(player.getName() + ".inv." + key + "." + type + "." + slot + ".lore").getKeys(false)){
							lores.add(main.config.getString(player.getName() + ".inv." + key + "." + type + "." + slot + ".lore." + i));
						}
						meta.setLore(lores);
					}
					
					if(main.config.contains(player.getName() + ".inv." + key + "." + type + "." + slot + ".lore")){
						
						String[] nbts = new String[20];
						for(String i : main.config.getConfigurationSection(player.getName() + ".inv." + key + "." + type + "." + slot + ".nbt").getKeys(false)){
							nbts[Integer.parseInt(i)] = main.config.getString(player.getName() + ".inv." + key + "." + type + "." + slot + ".nbt." + i);

						}
						
						
					}
					
					if(main.config.contains(player.getName() + ".inv." + key + "." + type + "." + slot + ".enchant")){
						
						for(String i : main.config.getConfigurationSection(player.getName() + ".inv." + key + "." + type + "." + slot + ".enchant").getKeys(false)){
							
							item.addEnchantment(
									Enchantment.getByName(main.config.getString(player.getName() + ".inv." + key + "." + type + "." + slot + ".enchant." + i + ".enc")),
									main.config.getInt(player.getName() + ".inv." + key + "." + type + "." + slot + ".enchant." + i + ".lvl"));
							
						}
						
					}
					
				}
			}
			
			
			
		}
		
	}*/

}

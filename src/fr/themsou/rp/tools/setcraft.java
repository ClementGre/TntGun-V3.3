package fr.themsou.rp.tools;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import fr.themsou.main.main;

public class setcraft {
	
	private main pl;
	ItemStack m = new ItemStack(Material.DIAMOND_AXE, 1);
	public setcraft(main pl){
		this.pl = pl;
	}
	
	public void setcrafts(){
		Server server = pl.getServer();
		
		ItemStack m = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta mM = m.getItemMeta();
		mM.setDisplayName("§3§lSuper Hache");
		m.setItemMeta(mM);
		
		ItemStack m1 = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta m1M = m1.getItemMeta();
		m1M.setDisplayName("§3§lSuper Pioche");
		m1.setItemMeta(m1M);
		
		ItemStack m3 = new ItemStack(Material.DIAMOND_HOE, 1);
		ItemMeta m3M = m3.getItemMeta();
		m3M.setDisplayName("§3§lSuper Houe");
		m3.setItemMeta(m3M);
		
		ItemStack m4 = new ItemStack(Material.EXPERIENCE_BOTTLE, 64);
		ItemMeta m4M = m4.getItemMeta();
		m4M.setDisplayName("§3§lBouteille d'XP");
		m4.setItemMeta(m4M);
		
		NamespacedKey key1 = new NamespacedKey(pl, "super_axe");
		ShapedRecipe r1  = new ShapedRecipe(key1, m);
		r1.shape(new String[] {"AAA","AAA","AAA"});
		r1.setIngredient('A', Material.DIAMOND_AXE);
		server.addRecipe(r1);
		
		NamespacedKey key2 = new NamespacedKey(pl, "super_pickaxe");
		ShapedRecipe r2  = new ShapedRecipe(key2, m1);
		r2.shape(new String[] {"AAA","AAA","AAA"});
		r2.setIngredient('A', Material.DIAMOND_PICKAXE);
		server.addRecipe(r2);
		
		NamespacedKey key3 = new NamespacedKey(pl, "super_hoe");
		ShapedRecipe r3  = new ShapedRecipe(key3, m3);
		r3.shape(new String[] {"AAA","AAA","AAA"});
		r3.setIngredient('A', Material.DIAMOND_HOE);
		server.addRecipe(r3);
		
		NamespacedKey key4 = new NamespacedKey(pl, "bottle_xp");
		ShapedRecipe r4  = new ShapedRecipe(key4, m4);
		r4.shape(new String[] {" A ","BEC"," D "});
		r4.setIngredient('E', Material.LAPIS_BLOCK);
		r4.setIngredient('A', Material.ROTTEN_FLESH);
		r4.setIngredient('B', Material.SPIDER_EYE);
		r4.setIngredient('C', Material.BLAZE_POWDER);
		r4.setIngredient('D', Material.BONE);
		server.addRecipe(r4);
		
		NamespacedKey key5 = new NamespacedKey(pl, "spawner_cage");
		ShapedRecipe r5  = new ShapedRecipe(key5, getItem(Material.SPAWNER, "§3§lCage de spawner", Arrays.asList("cage")));
		r5.shape(new String[] {"BBB","BAB","BBB"});
		r5.setIngredient('A', Material.DIAMOND_BLOCK);
		r5.setIngredient('B', Material.IRON_BARS);
		server.addRecipe(r5);
		
		NamespacedKey key6 = new NamespacedKey(pl, "spawner_50");
		ShapedRecipe r6  = new ShapedRecipe(key6, getItem(Material.SPAWNER, "§3§lSpawner de zombie", Arrays.asList("zombie","1")));
		r6.shape(new String[] {"BBB","BAB","BBB"});
		r6.setIngredient('A', Material.SPAWNER);
		r6.setIngredient('B', Material.ROTTEN_FLESH);
		server.addRecipe(r6);
		
		NamespacedKey key7 = new NamespacedKey(pl, "spawner_50-1");
		ShapedRecipe r7  = new ShapedRecipe(key7, getItem(Material.SPAWNER, "§3§lSpawner de cochon", Arrays.asList("pig","1")));
		r7.shape(new String[] {"BBB","BAB","BBB"});
		r7.setIngredient('A', Material.SPAWNER);
		r7.setIngredient('B', Material.PORKCHOP);
		server.addRecipe(r7);
		
		NamespacedKey key8 = new NamespacedKey(pl, "spawner_60");
		ShapedRecipe r8  = new ShapedRecipe(key8, getItem(Material.SPAWNER, "§3§lSpawner d'araignée", Arrays.asList("spider","2")));
		r8.shape(new String[] {"BBB","BAB","BBB"});
		r8.setIngredient('A', Material.SPAWNER);
		r8.setIngredient('B', Material.STRING);
		server.addRecipe(r8);
		
		NamespacedKey key9 = new NamespacedKey(pl, "spawner_60-1");
		ShapedRecipe r9  = new ShapedRecipe(key9, getItem(Material.SPAWNER, "§3§lSpawner de vache", Arrays.asList("cow","2")));
		r9.shape(new String[] {"BBB","BAB","BBB"});
		r9.setIngredient('A', Material.SPAWNER);
		r9.setIngredient('B', Material.BEEF);
		server.addRecipe(r9);
		
		NamespacedKey key10 = new NamespacedKey(pl, "spawner_70");
		ShapedRecipe r10  = new ShapedRecipe(key10, getItem(Material.SPAWNER, "§3§lSpawner de squelette", Arrays.asList("skeleton","3")));
		r10.shape(new String[] {"BBB","BAB","BBB"});
		r10.setIngredient('A', Material.SPAWNER);
		r10.setIngredient('B', Material.BONE);
		server.addRecipe(r10);
		
		NamespacedKey key11 = new NamespacedKey(pl, "spawner_70-1");
		ShapedRecipe r11  = new ShapedRecipe(key11, getItem(Material.SPAWNER, "§3§lSpawner de mouton", Arrays.asList("sheep","3")));
		r11.shape(new String[] {"BBB","BAB","BBB"});
		r11.setIngredient('A', Material.SPAWNER);
		r11.setIngredient('B', Material.WHITE_WOOL);
		server.addRecipe(r11);
		
		NamespacedKey key12 = new NamespacedKey(pl, "spawner_80");
		ShapedRecipe r12  = new ShapedRecipe(key12, getItem(Material.SPAWNER, "§3§lSpawner de creeper", Arrays.asList("creeper","4")));
		r12.shape(new String[] {"BBB","BAB","BBB"});
		r12.setIngredient('A', Material.SPAWNER);
		r12.setIngredient('B', Material.GUNPOWDER);
		server.addRecipe(r12);
		
		NamespacedKey key13 = new NamespacedKey(pl, "spawner_80-1");
		ShapedRecipe r13  = new ShapedRecipe(key13, getItem(Material.SPAWNER, "§3§lSpawner de poule", Arrays.asList("chicken","4")));
		r13.shape(new String[] {"BBB","BAB","BBB"});
		r13.setIngredient('A', Material.SPAWNER);
		r13.setIngredient('B', Material.CHICKEN);
		server.addRecipe(r13);
		
		NamespacedKey key14 = new NamespacedKey(pl, "spawner_100");
		ShapedRecipe r14  = new ShapedRecipe(key14, getItem(Material.SPAWNER, "§3§lSpawner d'enderman", Arrays.asList("enderman","5")));
		r14.shape(new String[] {"BBB","BAB","BBB"});
		r14.setIngredient('A', Material.SPAWNER);
		r14.setIngredient('B', Material.ENDER_PEARL);
		server.addRecipe(r14);
		
		NamespacedKey key15 = new NamespacedKey(pl, "spawner_100-1");
		ShapedRecipe r15  = new ShapedRecipe(key15, getItem(Material.SPAWNER, "§3§lSpawner de chevaux", Arrays.asList("horse","5")));
		r15.shape(new String[] {"BBB","BAB","BBB"});
		r15.setIngredient('A', Material.SPAWNER);
		r15.setIngredient('B', Material.GOLDEN_CARROT);
		server.addRecipe(r15);
		
	}
	
	public ItemStack getItem(Material material, String name, java.util.List<String> lore){
		
		ItemStack a = new ItemStack(material, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setDisplayName(name);
		aM.setLore(lore);
		a.setItemMeta(aM);				
		
		return a;
		
		
	}

}

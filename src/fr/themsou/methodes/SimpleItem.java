package fr.themsou.methodes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class SimpleItem {
	
	private Material type;
	private byte damage;
	private Map<Enchantment, Integer> enchantments;
	
	
	
	public SimpleItem(Material type, byte damage, Map<Enchantment, Integer> enchantments){
		this.setType(type);
		this.setDamage(damage);
		this.setEnchantments(enchantments);
	}
	
	
	public boolean equals(SimpleItem item){
		
		if(item.getType().equals(type) && item.getDamage() == damage){
			return true;
		}
		return false;
		
	}
	
	public boolean containsInHashMap(HashMap<SimpleItem, Integer> hashMap){
		
		for(Object key : hashMap.keySet()){
			if(equals((SimpleItem) key)){
				return true;
			}
		}
		return false;
		
	}
	public SimpleItem getKeyEqualsInHashMap(HashMap<SimpleItem, Integer> hashMap){
		
		for(Object key : hashMap.keySet()){
			if(equals((SimpleItem) key)){
				return (SimpleItem) key;
			}
		}
		return null;
		
	}


	public byte getDamage() {
		return damage;
	}


	public void setDamage(byte damage) {
		this.damage = damage;
	}


	public Material getType() {
		return type;
	}


	public void setType(Material type) {
		this.type = type;
	}


	public Map<Enchantment, Integer> getEnchantments() {
		return enchantments;
	}


	public void setEnchantments(Map<Enchantment, Integer> enchantments) {
		this.enchantments = enchantments;
	}

}

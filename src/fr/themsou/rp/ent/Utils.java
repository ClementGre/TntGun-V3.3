package fr.themsou.rp.ent;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import fr.themsou.main.main;
import fr.themsou.methodes.SimpleItem;

public class Utils {
	
	
	public void log(Player p, HashMap<SimpleItem, Integer> items, boolean add){
		
		String ent = main.config.getString(p.getName() + ".rp.ent.name");
		String act = "remove";
		if(add) act = "add";
		
		String display = "";
		for(SimpleItem item : items.keySet()){
			display += "§b" + items.get(item) + " x §3" + item.getType() + "§7:" + item.getDamage() + "§c, ";
		}
		
		String current = "";
		if(main.config.contains("ent.list." + ent + ".log." + p.getName() + "." + act))
			current = main.config.getString("ent.list." + ent + ".log." + p.getName() + "." + act);
		
		main.config.set("ent.list." + ent + ".log." + p.getName() + "." + act, current + display);
		
	}
	
	
	public void payEntreprises(){
		
		ConfigurationSection entSection = main.config.getConfigurationSection("ent.list");
		
		for(String ent : entSection.getKeys(false)){
			if(main.config.contains("ent.list." + ent + ".buyers")){
				
				ConfigurationSection buyersSection = main.config.getConfigurationSection("ent.list." + ent + ".buyers");
				
				if(buyersSection.getKeys(false).size() >= 11){
					
					int[] buyersValue = new int[buyersSection.getKeys(false).size()]; int i = 0;
					for(String buyer : buyersSection.getKeys(false)){
						buyersValue[i] = main.config.getInt("ent.list." + ent + ".buyers." + buyer);
						i++;
					}
					Arrays.sort(buyersValue);
					
					int median = buyersValue[buyersValue.length / 2];
					
					int total = median * buyersSection.getKeys(false).size();
					
					main.config.set("ent.list." + ent + ".money", main.config.getInt("ent.list." + ent + ".buyers") + total); 
					main.config.set("ent.list." + ent + ".buyers", null); 
					
					String pdg = main.config.getString("ent.list." + ent + ".pdg");
					String notifs = main.config.getString(pdg + ".notifs");
					
					if(notifs == null) notifs = "";
					
					main.config.set(pdg + ".notifs", notifs + "," + "§6Votre entreprise a reçu §c" + total + "€§6 pour vous récompenser de vos ventes.");
				}else{
					String pdg = main.config.getString("ent.list." + ent + ".pdg");
					String notifs = main.config.getString(pdg + ".notifs");
					main.config.set(pdg + ".notifs", notifs + "," + "§6Votre entreprise a reçu §c0€§6 pour vous récompenser de vos ventes.");
				}
				
			}
		}
		
	}

}

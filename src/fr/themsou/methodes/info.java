package fr.themsou.methodes;

import java.util.Random;

import org.bukkit.entity.Player;

import fr.themsou.main.main;
import fr.themsou.nms.message;
import fr.themsou.nms.title;

public class info{
	
	public void sendPlayerInfo(Player p, String key){
		
		if(key != null){
			if(main.configuration.contains("info." + key)){
				
				if(new Random().nextInt(3) != 1){
					int size = main.configuration.getConfigurationSection("info." + key).getKeys(false).size() - 1;
					
					int id = new Random().nextInt(size);
					if(!main.config.contains(p.getName() + ".info." + key + "." + id)){
						String msg = main.configuration.getString("info." + key + "." + id);
						message.sendNmsMessageInfo(p, msg, "/infoview " + id + " " + key);
						return;
					}
				}
				
				
			}
		}
		
		int size = main.configuration.getConfigurationSection("info.all").getKeys(false).size() - 1;
		
		int id = new Random().nextInt(size);
		if(!main.config.contains(p.getName() + ".info.all." + id)){
			String msg = main.configuration.getString("info.all." + id);
			message.sendNmsMessageInfo(p, msg, "/infoview " + id + " all");
			return;
		}
		
	}
	
	public void viewPlayerInfo(Player p, String id, String key){
		
		main.config.set(p.getName() + ".info." + key + "." + id, true);
		title.sendActionBar(p, "§bCe message ne vous sera plus envoyé !");
		
	}

}

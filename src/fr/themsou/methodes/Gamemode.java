package fr.themsou.methodes;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import fr.themsou.main.main;
import fr.themsou.nms.message;

public class Gamemode {

	public void gamemode(Player p) {
		
		if(p.getGameMode() == GameMode.CREATIVE){
			p.setGameMode(GameMode.SURVIVAL);
			System.out.println(p.getName() + " est passé gamemode Survie");
			
			p.sendMessage("§6Bienvenue en §cSurvie§6 ne vous givez pas de stuff ;)");
			
		}else{
			
			p.setGameMode(GameMode.CREATIVE);
			System.out.println(p.getName() + " est passé gamemode Créatif");
			
			p.sendMessage("§6Bienvenue en §cCréatif - §3INFOS :");
			
			for(String spawn : main.config.getConfigurationSection("claim.list").getKeys(false)){
				for(String id : main.config.getConfigurationSection("claim.list." + spawn).getKeys(false)){
					
					if(id.equals("minx") || id.equals("maxx") || id.equals("minz") || id.equals("maxz") || id.equals("app") || id.equals("x") || id.equals("y") || id.equals("z")){
						continue;
					}
					
					if(!main.config.getBoolean("claim.list." + spawn + "." + id + ".needsetup")) continue;
					
					if(main.config.getString("claim.list." + spawn + "." + id + ".type").equals("ins")){
						if(main.config.contains("ent.list." + main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",")[0])) continue;
					}else{
						if(main.config.contains(main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",")[0])) continue;
					}
					
					int minx = main.config.getInt("claim.list." + spawn + "." + id + ".x1");
					int minz = main.config.getInt("claim.list." + spawn + "." + id + ".z1");
					int maxx = main.config.getInt("claim.list." + spawn + "." + id + ".x2");
					int maxz = main.config.getInt("claim.list." + spawn + "." + id + ".z2");
					int x = minx + (maxx - minx) / 2;
					int z = minz + (maxz - minz) / 2;
					int y = main.config.getInt("claim.list." + spawn + "." + id + ".y1");
					if(y == 0) y = Bukkit.getWorld("world").getHighestBlockYAt(x, z) + 1;
					
					message.sendNmsMessageCmd(p, "§c- §4" + id + " §c est un claim à setup, vous devez le supprimer", "§3Cliquez pour vous téléporter", "/tp " + x + " " + y + " " + z);
				
				}
				
			}
			
			
		}
		
		
	}

}

package fr.themsou.commands;

import fr.themsou.rp.claim.Claim;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.themsou.main.main;
import fr.themsou.nms.message;

public class GamemodeCmd implements CommandExecutor{


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(p.getGameMode() == GameMode.CREATIVE){
				p.setGameMode(GameMode.SURVIVAL);
				System.out.println(p.getName() + " est passé gamemode Survie");
				
				p.sendMessage("§6Bienvenue en §cSurvie§6 ne vous givez pas de stuff ;)");
				
			}else{
				
				p.setGameMode(GameMode.CREATIVE);
				System.out.println(p.getName() + " est passé gamemode Créatif");
				
				p.sendMessage("§6Bienvenue en §cCréatif - §3INFOS :");
				
				for(Integer id : Claim.getAllClaims()){

					Claim claim = new Claim(id);


					if(!claim.getNeedSetup()) continue;

					if(claim.isEnt()){
						if(main.config.contains("ent.list." + claim.getPureOwner())) continue;
					}else{
						if(main.config.contains(claim.getOwner())) continue;
					}

					int minx = claim.getX1();
					int minz = claim.getZ1();
					int maxx = claim.getX2();
					int maxz = claim.getZ2();
					int x = minx + (maxx - minx) / 2;
					int z = minz + (maxz - minz) / 2;
					int y = claim.getY1();
					if(y == 0) y = Bukkit.getWorld("world").getHighestBlockYAt(x, z) + 2;

					message.sendNmsMessageCmd(p, "§c- §4" + id + " §c est un claim à setup, vous devez le supprimer", "§3Cliquez pour vous téléporter", "/tp " + x + " " + y + " " + z);

				}
			}
		}
		
		return false;
	}

}

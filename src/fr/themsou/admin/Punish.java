package fr.themsou.admin;

import java.util.List;

import org.bukkit.Bukkit;

import fr.themsou.main.main;

public class Punish {
	
	
	public void punishPlayer(String playerName, String punisher, String punishType, int punishTime, String punishReason){
		
		
		if(punishType.equalsIgnoreCase("ban")){
			
			main.config.set(playerName + ".punish.ban.day", punishTime);
			main.config.set(playerName + ".punish.ban.reason", punishReason);
			main.config.set(playerName + ".punish.ban.punisher", punisher);
			
			Bukkit.broadcastMessage("§6" + punisher  + "§c à bannis §6" + playerName + "§c pour §6" + punishTime + "§c jours et pour raison : §6" + punishReason);
			
			main.CSQLConnexion.refreshPlayer(playerName, 2);
			
			if(Bukkit.getPlayerExact(playerName) != null){
				Bukkit.getPlayerExact(playerName).kickPlayer("§cVOUS ÊTES BANNIS !\n\n§cPour raison §6" + punishReason + "\n§cPour §6" + punishTime + "§c Jours\n§cPar §6" + punisher + "\n§3Discord : §bhttps://discord.tntgun.fr/");
			}
			
		}else if(punishType.equalsIgnoreCase("kick")){
			
			Bukkit.broadcastMessage("§6" + punisher  + "§c à expulsé §6" + playerName + "§c pour raison : §6" + punishReason);
			
			if(Bukkit.getPlayerExact(playerName) != null){
				Bukkit.getPlayerExact(playerName).kickPlayer("§cVOUS AVEZ ÉTÉ EXPULSÉ !\n\n§cPour raison §6" + punishReason + "\n§cPar §6" + punisher);
			}
			
		}else if(punishType.equalsIgnoreCase("mute")){
			
			main.config.set(playerName + ".punish.mute.minutes", punishTime);
			main.config.set(playerName + ".punish.mute.reason", punishReason);
			main.config.set(playerName + ".punish.mute.punisher", punisher);
			
			Bukkit.broadcastMessage("§6" + punisher + "§c à rendu muet §6" + playerName + "§c pour §6" + punishTime + "§c minutes et pour raison : §6" + punishReason);
			
		}
		
	}
	
	public void pardonPlayer(String playerName, String pardoner, String pardonType, String pardonReason){
		
		if(pardonType.equalsIgnoreCase("ban")){
			
			main.config.set(playerName + ".punish.ban.day", 0);
			main.config.set(playerName + ".punish.ban.reason", pardonReason);
			main.config.set(playerName + ".punish.ban.punisher", pardoner);
			
			Bukkit.broadcastMessage("§6" + pardoner + "§c à dé-bannis §6" + playerName + "§c pour raison : §6" + pardonReason);
			
			main.CSQLConnexion.refreshPlayer(playerName, 2);
			
			
		}else if(pardonType.equalsIgnoreCase("mute")){
			
			main.config.set(playerName + ".punish.mute.minutes", 0);
			main.config.set(playerName + ".punish.mute.reason", pardonReason);
			main.config.set(playerName + ".punish.mute.punisher", pardoner);
			
			Bukkit.broadcastMessage("§6" + pardoner + "§c à re-rendu muet §6" + playerName + "§c pour raison : §6" + pardonReason);
			
			
		}
		
	}
	
	
	public void punishPlayerProgress(String player, String punisher, String punishName, List<String> lore){
		
		
		int level = main.config.getInt(player + ".punish." + punishName);
		if(level >= 3) level = 2;
		String[] sanction = lore.get(level).split(" ");
		
		
		if(sanction[0].equalsIgnoreCase("ban")){
			
			main.config.set(player + ".punish.ban.day", Integer.parseInt(sanction[1]));
			main.config.set(player + ".punish.ban.reason", punishName);
			main.config.set(player + ".punish.ban.punisher", punisher);
			
			Bukkit.broadcastMessage("§6" + punisher  + "§c à bannis §6" + player + "§c pour §6" + sanction[1] + "§c jours et pour raison : §6" + punishName);
			
			main.CSQLConnexion.refreshPlayer(player, 2);
			
			if(Bukkit.getPlayerExact(player) != null){
				Bukkit.getPlayerExact(player).kickPlayer("§cVOUS ÊTES BANNIS !\n\n§cPour raison §6" + punishName + "\n§cPour §6" + sanction[1] + "§c Jours\n§cPar §6" + punisher + "\n§3Discord : §bhttps://discord.tntgun.fr/");
			}
			
			main.config.set(player + ".punish." + punishName, level + 1);
			
		}else{
			
			main.config.set(player + ".punish.mute.minutes", Integer.parseInt(sanction[1]));
			main.config.set(player + ".punish.mute.reason", punishName);
			main.config.set(player + ".punish.mute.punisher", punisher);
			
			Bukkit.broadcastMessage("§6" + punisher + "§c à rendu muet §6" + player + "§c pour §6" + sanction[1] + "§c minutes et pour raison : §6" + punishName);
			
			main.config.set(player + ".punish." + punishName, level + 1);
			
		}
		
		
		
		
		
		
	}
	
	
	

}

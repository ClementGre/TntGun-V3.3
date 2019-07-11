package fr.themsou.discord;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import fr.themsou.commands.GradeCmd;
import fr.themsou.diffusion.api.messages;
import fr.themsou.diffusion.api.roles;
import fr.themsou.diffusion.api.user;
import fr.themsou.main.main;

public class Chat {
	
	public static long channelId = 452810136319295498L;
	
	public void userSendMessage(String userName, String message, String avatarUrl){
		
		
		String userNick = new user().getPlayerNickName(userName);
		messages Cmessages = new messages();
		roles Croles = new roles();
		
		String prefix = ""; String chatColor = "§7"; String overlay = "";
		
		Cmessages.clearEmbed();
		Cmessages.setColor(Color.CYAN);
		Cmessages.setAuthor(userNick, avatarUrl, avatarUrl);
		Cmessages.setTitle(message + "");
		Cmessages.setFooter("Depuis Discord", "http://icons.iconarchive.com/icons/papirus-team/papirus-apps/256/discord-icon.png");
		Cmessages.sendEmbed(channelId);
		Cmessages.clearEmbed();
		
		if(!Croles.getRoles(userName).contains("Joueur")){
			prefix = "§f[Discord]§7 [Membre] " + userNick + " : ";
			
		}else{
			for(String grade : main.configuration.getConfigurationSection("grades").getKeys(false)){
				
				if(Croles.getRoles(userName).contains(grade)){
					chatColor = main.configuration.getString("grades." + grade + ".chatcolor");
					prefix = "§f[Discord]" + main.configuration.getString("grades." + grade + ".gradecolor") + " [" + grade + "] "
							+ main.configuration.getString("grades." + grade + ".playercolor") + userNick + " : " + chatColor;
					
				}
			}
		}
		
		if(!userName.equals(userNick)) overlay = "§cPseudo Original : §4" + userName;
		
		for(Player players : Bukkit.getOnlinePlayers()){
			
			if(new GradeCmd().getPlayerPermition(players.getName()) >= 3) fr.themsou.nms.message.sendNmsMessage(players, prefix, overlay, message, chatColor);
				
			else fr.themsou.nms.message.sendNmsMessage(players, prefix, "", message, chatColor);
			
		}
		
		System.out.println("[Discord] " + prefix.split(" ")[1] + userName + " : " + message);
		
	}
	
	public void sendMessage(String userName, String message){
		
		messages Cmessages = new messages();
		
		Cmessages.clearEmbed();
		Cmessages.setAuthor(userName, "https://minotar.net/avatar/" + userName + "/32.png", "https://minotar.net/avatar/" + userName + "/32.png");
		Cmessages.setDescription(message);
		
		Cmessages.setColor(Color.GREEN);
		
		Cmessages.sendEmbed(channelId);
		Cmessages.clearEmbed();
		
	}

}

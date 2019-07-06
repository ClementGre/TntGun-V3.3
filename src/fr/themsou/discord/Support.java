package fr.themsou.discord;

import java.awt.Color;

import fr.themsou.diffusion.api.messages;
import fr.themsou.diffusion.api.roles;
import fr.themsou.diffusion.api.user;
import fr.themsou.main.main;

public class Support {
	
	public static long channelId = 484994724835622915L;
	
	public void userSendMessage(String userName, String message){
		
		String userNick = new user().getPlayerNickName(userName);
		
		if(new roles().getRoles(userName).contains("Joueur")) main.CSQLConnexion.sendPost(userNick, message, true);
		else main.CSQLConnexion.sendPost(userNick, message, false);
		
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

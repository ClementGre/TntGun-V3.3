package fr.themsou.discord;

import java.awt.Color;
import fr.themsou.main.main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Support {
	
	public static long channelId = 484994724835622915L;
	
	public void userSendMessage(MessageReceivedEvent e){
		
		String userNick = (e.getMember().getNickname() == null) ? e.getAuthor().getName() : e.getMember().getNickname();

		Role joueur = main.jda.getRolesByName("Joueur", false).get(0);
		if(e.getMember().getRoles().contains(joueur)) main.CSQLConnexion.sendPost(userNick, e.getMessage().getContentDisplay(), true);
		else main.CSQLConnexion.sendPost(userNick, e.getMessage().getContentDisplay(), false);
		
	}
	
	public void sendMessage(String userName, String message){

		EmbedBuilder embed = new EmbedBuilder();
		embed.setAuthor(userName, "https://minotar.net/avatar/" + userName + "/32.png", "https://minotar.net/avatar/" + userName + "/32.png");
		embed.setDescription(message);
		
		embed.setColor(Color.GREEN);
		main.guild.getTextChannelById(channelId).sendMessage(embed.build()).queue();
		
	}

}

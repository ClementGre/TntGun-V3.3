package fr.themsou.discord;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;

import fr.themsou.discord.tools.Tools;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import fr.themsou.commands.GradeCmd;
import fr.themsou.main.main;

public class Chat {
	
	public static long channelId = 452810136319295498L;
	
	public void userSendMessage(MessageReceivedEvent e){


		ArrayList<String> roles = Tools.getMemberStringRoles(e.getMember());
		String userNick = (e.getMember().getNickname() == null) ? e.getAuthor().getName() : e.getMember().getNickname();
		String userName = e.getAuthor().getName();
		String prefix = ""; String chatColor = "§7"; String overlay = "";

		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.CYAN);
		embed.setAuthor(userNick, e.getAuthor().getAvatarUrl(), e.getAuthor().getAvatarUrl());
		embed.setDescription("**" + e.getMessage().getContentDisplay() + "**");
		embed.setFooter("Depuis Discord", "http://icons.iconarchive.com/icons/papirus-team/papirus-apps/256/discord-icon.png");
		embed.setTimestamp(Instant.now());
		main.guild.getTextChannelById(channelId).sendMessage(embed.build()).queue();

		if(!roles.contains("Joueur")){
			prefix = "§f[Discord]§7 [Membre] " + userNick + " : ";
			
		}else{
			for(String grade : main.configuration.getConfigurationSection("grades").getKeys(false)){

				if(roles.contains(grade)){
					chatColor = main.configuration.getString("grades." + grade + ".chatcolor");
					prefix = "§f[Discord]" + main.configuration.getString("grades." + grade + ".gradecolor") + " [" + grade + "] "
							+ main.configuration.getString("grades." + grade + ".playercolor") + userNick + " : " + chatColor;
					break;
				}
			}
		}
		
		if(!userName.equals(userNick)) overlay = "§cPseudo Original : §4" + userName;
		
		for(Player players : Bukkit.getOnlinePlayers()){
			
			if(new GradeCmd().getPlayerPermition(players.getName()) >= 3) fr.themsou.nms.message.sendNmsMessage(players, prefix, overlay, e.getMessage().getContentDisplay(), chatColor);
				
			else fr.themsou.nms.message.sendNmsMessage(players, prefix, "", e.getMessage().getContentDisplay(), chatColor);
			
		}
		
		System.out.println("[Discord] " + prefix.split(" ")[1] + " " + userName + " : " + e.getMessage().getContentDisplay());

		e.getMessage().delete().queue();
		
	}
	
	public void sendMessage(String userName, String message){

		EmbedBuilder embed = new EmbedBuilder();
		embed.setAuthor(userName, "https://minotar.net/avatar/" + userName + "/32.png", "https://minotar.net/avatar/" + userName + "/32.png");
		embed.setDescription("**" + message + "**");
		embed.setFooter("Depuis Minecraft", "https://vignette.wikia.nocookie.net/minecraftproject/images/3/31/311.png/revision/latest?cb=20120726083051");
		embed.setTimestamp(Instant.now());

		embed.setColor(Color.GREEN);

		main.guild.getTextChannelById(channelId).sendMessage(embed.build()).queue();
		
	}

}

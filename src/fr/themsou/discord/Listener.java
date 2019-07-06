package fr.themsou.discord;

import java.awt.Color;
import java.io.File;

import fr.themsou.diffusion.api.messages;
import fr.themsou.diffusion.api.roles;
import fr.themsou.diffusion.api.user;
import fr.themsou.discord.vocal.Music;
import fr.themsou.main.main;

public class Listener{
	
	//	MESSAGES
	
	public void userReact(String reaction, String userName, Long channelId, String channelName, Long messageId, String avatarUrl){
		
		if(channelId == Music.channelId){
			new Music().userReact(reaction, userName, channelId, messageId);
			
		}else if(channelId == 532139944140079104L && messageId == 586206292042055681L){ // Règles lues
			
			if(!new roles().getRoles(userName).contains("Membre")){
				new roles().addRole("Membre", userName);
				new messages().sendFile(new File("plugins/tntgun/files/bienvenue.gif"), ":gift: Bienvenue <@" + new user().getIdByPlayer(userName) + "> sur TntGun !", 414143183996452864L);
				new Counter().refreshCounters();
			}
			
		}else if(channelId == Roles.channelId && messageId == Roles.messageId){
			
			new Roles().onReact(userName, reaction);
			
		}
		
	}public void userUnreact(String reaction, String userName, Long channelId, String channelName, Long messageId, String avatarUrl){
		
		if(channelId == Roles.channelId && messageId == Roles.messageId){
			
			new Roles().onUnreact(userName, reaction);
			
		}
		
	}public void reciveMessage(String message, String userName, Long channelId, String channelName, Long messageId, String avatarUrl){
		
		main.config.set("discord.list.users." + userName + ".msgsend", main.config.getInt("discord.list.users." + userName + ".msgsend") + 1);
		
		if(channelId == Support.channelId){
			new Support().userSendMessage(userName, message);
		
		}else if(channelId == Chat.channelId){
			new Chat().userSendMessage(userName, message, avatarUrl);
			
		}else if(channelId == Music.channelId){
			new Music().userSendMessage(userName, message, messageId);
			
		}else if(channelId == 584732396352569360L){
			
			new messages().addReact(channelId, messageId, "❌");
			new messages().addReact(channelId, messageId, "✅");
			
		}else if(channelId == 584732175363211274L){
			
			new messages().addReact(channelId, messageId, "👍");
			new messages().addReact(channelId, messageId, "👎");
			
		}else if(channelId == 457198130811764737L){
			
			new messages().addReact(channelId, messageId, "❤");
			new messages().addReact(channelId, messageId, "💔");
		}
		
	}public void recivePrivateMessage(String message, String userName){
		
		new Link().userSendPrivateMessage(userName, message);
		
	}
	
	//	CHANGE NAME/NICK
	
	public void userChangeNick(String OldUserNick, String NewUserNick, String userName){
			
		new Link().userChangeNick(OldUserNick, NewUserNick, userName);	
		
	}public void userChangeName(String OldUserName, String NewUserName){
		
		new Link().userChangeName(OldUserName, NewUserName);
		
	}
	
	// JOIN/LEAVE
	
	public void PlayerJoin(String userName){
		
		messages Cmessages = new messages();
		Cmessages.clearEmbed(); Cmessages.setColor(Color.RED);
		Cmessages.setTitle("Bienvenue sur TntGun !");
		Cmessages.addfield("Ce serveur Discord est liée à un serveur Minecraft.", "Vous pouvez voir les infos du serveur dans le salon ℹ-infos-ℹ", false);
		Cmessages.addfield("Vous pouvez transférer vos grades sur Discord en liant vos 2 comptes.", "Pour cela, vous devez entrer la commande /discord en jeu.", false);
		Cmessages.addfield("Le serveur TntGun vous souhaite de très bon moments sur le serveur.", "Nous vous remercions d'avoir rejoins notre communauté.", false);
		Cmessages.sendPrivateEmbed(userName); Cmessages.clearEmbed();
		
		Cmessages.sendMessage("Bienvenue <@" + new user().getIdByPlayer(userName) + ">, pour accéder au discord complet, vous devez accepter le règlement dans <#532139944140079104> (Réagissez avec :white_check_mark:)", 585009111201087488L);
		Cmessages.sendPrivateMessage("Pour accéder au discord complet, vous devez accepter le règlement dans #⚠-règles-⚠  (Réagissez avec :white_check_mark:)", userName);
		
		new Counter().refreshCounters();
		
	}public void PlayerLeave(String userName){
		
		main.config.set("discord.list.users." + userName, null);
		new Link().userLeaveServer(userName);
		
		new Counter().refreshCounters();
	}
	
	// VOCAL
	
	public void playerVocalDisconect(String userName, Long channelId, int lastUsers){
		
		if(channelId == Music.vocalId){
			new Music().playerVocalDisconect(lastUsers);
		}
		
	}


}

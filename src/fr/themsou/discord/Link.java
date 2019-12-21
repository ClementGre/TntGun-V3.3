package fr.themsou.discord;

import fr.themsou.main.main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;

import java.awt.*;
import java.util.function.Consumer;

public class Link {

	
	public void userSendPrivateMessage(PrivateMessageReceivedEvent e){

		if(main.guild.getMember(e.getAuthor()) == null){
			e.getChannel().sendMessage("Vous n'etes pas dans le serveur TntGun !\nhttp://discord.gg/zAEAYnN/").queue();
			return;
		}

		Member member = main.guild.getMember(e.getAuthor());
		if(member == null) return;

		String userNick = (member.getNickname() == null) ? e.getAuthor().getName() : member.getNickname();

		String action = main.config.getString("discord.list.users." + e.getAuthor().getAsTag() + ".ac");
		String actionParam = main.config.getString("discord.list.users." + e.getAuthor().getAsTag() + ".acp");
		
		if(action == null) return;
		
		if(e.getMessage().getContentRaw().contains("n") || e.getMessage().getContentRaw().contains("N")){
			
			if(main.config.getString(userNick + ".discord") != null){
				if(main.config.getString(userNick + ".discord").equals(e.getAuthor().getAsTag())){
					
					if(action.equals("ipchange")){

						e.getChannel().sendMessage("Daccord, nous ne touchons à rien !").queue();
						
						main.config.set("discord.list.users." + e.getAuthor().getAsTag() + ".ac", null);
						main.config.set("discord.list.users." + e.getAuthor().getAsTag() + ".acp", null);
					}
					
				}	
			}
			if(action.equals("link")){

				e.getChannel().sendMessage("Daccord, nous ne touchons à rien !").queue();
				
				main.config.set("discord.list.users." + e.getAuthor().getAsTag() + ".ac", null);
				main.config.set("discord.list.users." + e.getAuthor().getAsTag() + ".acp", null);
			} 
				
			
		}else{
			if(main.config.getString(userNick + ".discord") != null){
				if(main.config.getString(userNick + ".discord").equals(e.getAuthor().getAsTag())){
					
					if(action.equals("ipchange")){

						e.getChannel().sendMessage("Vottre nouvelle ip est maintenant : " + actionParam).queue();
						main.config.set(userNick + ".ip", actionParam);
						
						
						main.config.set("discord.list.users." + e.getAuthor().getAsTag() + ".ac", null);
						main.config.set("discord.list.users." + e.getAuthor().getAsTag() + ".acp", null);
						
					}
					
				}
			}
			if(action.equals("link")){

				Roles CRoles = new Roles();

				String lastLinkedUserTag = main.config.getString(actionParam + ".discord");
				if(lastLinkedUserTag != null){
					main.config.set(actionParam + ".discord", null);

					if(!lastLinkedUserTag.equals(e.getAuthor().getAsTag())){
						Member lastLinkedMember = main.guild.getMemberByTag(lastLinkedUserTag);
						if(lastLinkedMember != null){

							PrivateChannel privateChannel = lastLinkedMember.getUser().openPrivateChannel().complete();
							privateChannel.sendMessage("Votre compte à été dé-lié car **" + actionParam + "** s'est lié à un autre compte").queue(new Consumer<Message>(){public void accept(Message message){}}, new Consumer<Throwable>(){public void accept(Throwable throwable){}});

							CRoles.removeStatusRoles(lastLinkedMember);
							CRoles.removeRole(lastLinkedMember, (lastLinkedMember.getNickname() == null) ? lastLinkedMember.getUser().getName() : lastLinkedMember.getNickname());

							if(lastLinkedMember.getNickname() != null) {
								new Thread(new Runnable() {
									public void run() {
										try{ Thread.sleep(2000); }catch(InterruptedException ex){ ex.printStackTrace(); }
										main.guild.modifyNickname(lastLinkedMember, null).queue();
									}
								}, "Remove Nickname").start();
							}

						}
					}
				}
				if(!((member.getNickname() == null) ? e.getAuthor().getName() : member.getNickname()).equals(actionParam)){
					main.guild.modifyNickname(member, actionParam).queue();
				}

				e.getChannel().sendMessage("Vos deux comptes sont maintenant liés !").queue();
				
				main.config.set(actionParam + ".discord", e.getAuthor().getAsTag());
				CRoles.setRole(actionParam);
				
				main.config.set("discord.list.users." + e.getAuthor().getAsTag() + ".ac", null);
				main.config.set("discord.list.users." + e.getAuthor().getAsTag() + ".acp", null);
				
				new Counter().refreshCounters();
				
				
			}
				
				
		}
		
	}
	
	
	public void linkPlayer(String playerName, String userTag) {

		try{

			Member member = main.guild.getMemberByTag(userTag);
			if(member != null){
				PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();
				privateChannel.sendMessage(playerName + " souhaite lier son compte Minecraft avec votre compte Discord.\nEst-ce que c'est bien vous ? (oui/non)").queue(new Consumer<Message>(){public void accept(Message message){
					main.config.set("discord.list.users." + userTag + ".ac", "link");
					main.config.set("discord.list.users." + userTag + ".acp", playerName);
				}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});

			}

		}catch (IllegalArgumentException ignored){}

	}
	
	public void changePlayerIP(String playerName, String userTag, String newIP) {

		Member member = main.guild.getMemberByTag(userTag);
		if(member != null){
			PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();
			String newIPHide = newIP.subSequence(0, 8) + ".***";
			main.config.set("discord.list.users." + userTag + ".ac", "ipchange");
			main.config.set("discord.list.users." + userTag + ".acp", newIP);
			privateChannel.sendMessage(playerName + " souhaite modifier son ip par défaut de " + main.config.getString(playerName + ".ip") + " en " + newIPHide + "\nEst-ce que c'est bien vous ? (oui/non)").queue(new Consumer<Message>(){public void accept(Message message){ }},new Consumer<Throwable>(){public void accept(Throwable throwable){}});
		}
	}
	
	public void userChangeNick(GuildMemberUpdateNicknameEvent e){

		if(e.getEntity().getUser().isBot()) return;

		String oldNickName = e.getOldNickname() == null ? e.getUser().getName() : e.getOldNickname();
		String newNickName = e.getNewNickname() == null ? e.getUser().getName() : e.getNewNickname();

		if(main.config.contains(oldNickName + ".discord")){
			if(main.config.getString(oldNickName + ".discord").equals(e.getUser().getAsTag())){
				PrivateChannel privateChannel = e.getUser().openPrivateChannel().complete();
				privateChannel.sendMessage("Vous venez de changer votre surnom de **" + oldNickName + "** en **" + newNickName + "** Votre compte Discord est donc dé-lié a votre compte Minecraft-TntGun").queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});

				main.config.set(oldNickName + ".discord", null);

				new Roles().removeStatusRoles(e.getMember());
				new Roles().removeRole(e.getMember(), oldNickName);
			}
		}
		
		new Counter().refreshCounters();
		
	}
	
	public void userChangeName(UserUpdateNameEvent e){

		Member member = main.guild.getMember(e.getUser());
		if(member == null) return;

		String oldNickName = (member.getNickname() == null) ? e.getOldName() : member.getNickname();
		String newNickName = (member.getNickname() == null) ? e.getOldName() : member.getNickname();

		if(main.config.contains(oldNickName + ".discord")){

			main.config.set(oldNickName + ".discord", e.getUser().getAsTag());

			if(!oldNickName.equals(newNickName)){
				main.guild.modifyNickname(member, oldNickName);
			}

		}

	}
	
	public void userLeaveServer(User user){
		

		for(String playerName : main.config.getConfigurationSection("").getKeys(false)){
			
			if(main.config.contains(playerName + ".discord")){
				if(main.config.getString(playerName + ".discord").equals(user.getAsTag())){
					main.config.set(playerName + ".discord", null);
				}
			}
		}


		
	}

}

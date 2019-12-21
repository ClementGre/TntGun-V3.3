package fr.themsou.discord;

import fr.themsou.main.main;
import fr.themsou.methodes.realDate;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.util.function.Consumer;

public class Roles {
	
	static public long channelId = 414143640995102720L;
	static public long messageId = 588381014985015328L;
	
	public void removeStatusRoles(Member member){

		Role joueur = main.jda.getRolesByName("Joueur", false).get(0);
		Role joueurActif = main.jda.getRolesByName("Joueur actif", false).get(0);
		Role rolePlayPlayer = main.jda.getRolesByName("RolePlay Player", false).get(0);
		Role bedWarsPlayer = main.jda.getRolesByName("BedWars Player", false).get(0);
		Role tntWarsPlayer = main.jda.getRolesByName("TntWars Player", false).get(0);

		PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();

		if(member.getRoles().contains(joueur)){
			main.guild.removeRoleFromMember(member, joueur).queue();
			privateChannel.sendMessage("Votre Grade **Joueur** vous a bien été retiré sur Discord").queue(new Consumer<Message>(){public void accept(Message message){}}, new Consumer<Throwable>(){public void accept(Throwable throwable){}});
			
		}if(member.getRoles().contains(joueurActif)){
			main.guild.removeRoleFromMember(member, joueurActif).queue();
			privateChannel.sendMessage("Votre Grade **Joueur actif** vous a bien été retiré sur Discord").queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});
			
		}if(member.getRoles().contains(rolePlayPlayer)){
			main.guild.removeRoleFromMember(member, rolePlayPlayer).queue();
			privateChannel.sendMessage("Votre Grade **RolePlay Player** vous a bien été retiré sur Discord").queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});
			
		}if(member.getRoles().contains(bedWarsPlayer)){
			main.guild.removeRoleFromMember(member, bedWarsPlayer).queue();
			privateChannel.sendMessage("Votre Grade **BedWars Player** vous a bien été retiré sur Discord").queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});
			
		}if(member.getRoles().contains(tntWarsPlayer)){
			main.guild.removeRoleFromMember(member, tntWarsPlayer).queue();
			privateChannel.sendMessage("Votre Grade **TntWars Player** vous a bien été retiré sur Discord").queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});
			
		}
	}
	public void removeRole(Member member, String nickname){

		String grade = main.config.getString(nickname + ".grade");
		if(grade == null) return;

		main.guild.removeRoleFromMember(member, main.jda.getRolesByName(grade, false).get(0)).queue();
		main.guild.removeRoleFromMember(member, main.jda.getRolesByName("Joueur actif", false).get(0)).queue();

		PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();
		privateChannel.sendMessage("Votre Grade **" + grade + "** vous a bien été retiré sur Discord").queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});
	}
	public void setRole(String playerName){
		
		if(!main.config.contains(playerName + ".discord")) return;
		
		String discordTag = main.config.getString(playerName + ".discord");
		User user = main.jda.getUserByTag(discordTag);
		Member member = main.guild.getMember(user);

		if(member == null) return;
		String grade = main.config.getString(playerName + ".grade");

		main.guild.addRoleToMember(member, main.jda.getRolesByName(grade, false).get(0)).queue();
		main.guild.addRoleToMember(member, main.jda.getRolesByName("Joueur actif", false).get(0)).queue();

		PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();
		privateChannel.sendMessage("Votre Grade **" + grade + "** a bien été transféré sur Discord").queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});
		
	}
	
	public void onDay(){

		Role memberRole = main.jda.getRolesByName("Membre", false).get(0);
		for(Member member : main.guild.getMembers()){
			if(!member.getRoles().contains(memberRole)){

				PrivateChannel privateChannel = member.getUser().openPrivateChannel().complete();
				privateChannel.sendMessage(":warning: **Vous n'avez pas accepté le règlement **:warning: \n"
							+ "Veuillez vous rendre dans le salon #:warning:-règles-:warning: "
							+ "et réagir avec :white_check_mark: pour accéder à l'entièreté du serveur.").queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});


			}
		}
		
	}
	
	public void onPlayerDay(String p){

		if(!main.config.contains(p + ".discord")) return;

		@SuppressWarnings("deprecation")
		int currentDay = new realDate().getRealDate().getDate();
		String discordName = main.config.getString(p + ".discord");
		User user = main.jda.getUserByTag(discordName);
		Member member = main.guild.getMember(user);

		if(member == null){
			return;
		}
		
		int lastDay = main.config.getInt(p + ".rp.lastday");
		if(currentDay < lastDay) currentDay = currentDay + 30;
		
		if(lastDay == 0 || currentDay - lastDay >= 7){
			main.guild.removeRoleFromMember(member, main.jda.getRolesByName("RolePlay Player", false).get(0)).queue();
		}
		
		lastDay = main.config.getInt(p + ".tntwars.lastday");
		if(currentDay < lastDay) currentDay = currentDay + 30;
		
		if(lastDay == 0 || currentDay - lastDay >= 7){
			main.guild.removeRoleFromMember(member, main.jda.getRolesByName("TntWars Player", false).get(0)).queue();
		}
		
		lastDay = main.config.getInt(p + ".bedwars.lastday");
		if(currentDay < lastDay) currentDay = currentDay + 30;
		
		if(lastDay == 0 || currentDay - lastDay >= 7){
			main.guild.removeRoleFromMember(member, main.jda.getRolesByName("BedWars Player", false).get(0)).queue();
		}
		
		lastDay = main.config.getInt(p + ".lastday");
		if(currentDay < lastDay) currentDay = currentDay + 30;
		
		if(lastDay == 0 || currentDay - lastDay >= 7){
			main.guild.removeRoleFromMember(member, main.jda.getRolesByName("Joueur actif", false).get(0)).queue();
		}
		
	}
	
	public void onWeek(){

		for(Member member : main.guild.getMembers()){
				
			if(main.config.getInt("discord.list.users." + member.getUser().getAsTag() + ".msgsend") >= 20){
				main.guild.addRoleToMember(member, main.jda.getRolesByName("Membre actif", false).get(0)).queue();
			}else{
				main.guild.removeRoleFromMember(member, main.jda.getRolesByName("Membre actif", false).get(0)).queue();
			}
			main.config.set("discord.list.users." + member.getUser().getAsTag(), null);
		}
	}
	
	public void onReact(MessageReactionAddEvent e){
		
		if(e.getReactionEmote().getName().equals("🆕")){
			main.guild.addRoleToMember(e.getMember(), main.jda.getRolesByName("Notifs changelog", false).get(0)).queue();
		}else if(e.getReactionEmote().getName().equals("⏏")){
			main.guild.addRoleToMember(e.getMember(), main.jda.getRolesByName("Notifs connexions", false).get(0)).queue();
		}
		
	}
	public void onUnreact(MessageReactionRemoveEvent e){
		
		if(e.getReactionEmote().getName().equals("🆕")){
			main.guild.removeRoleFromMember(e.getMember(), main.jda.getRolesByName("Notifs changelog", false).get(0)).queue();
		}else if(e.getReactionEmote().getName().equals("⏏")){
			main.guild.removeRoleFromMember(e.getMember(), main.jda.getRolesByName("Notifs connexions", false).get(0)).queue();
		}
		
	}

}

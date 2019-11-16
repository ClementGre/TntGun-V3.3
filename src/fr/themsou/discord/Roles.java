package fr.themsou.discord;

import java.util.ArrayList;
import java.util.Set;

import fr.themsou.diffusion.api.messages;
import fr.themsou.diffusion.api.roles;
import fr.themsou.diffusion.api.user;
import fr.themsou.main.main;
import fr.themsou.methodes.realDate;

public class Roles {
	
	static public long channelId = 414143640995102720L;
	static public long messageId = 588381014985015328L;
	
	public void removeStatusRoles(String userName){
		
		messages Cmessages = new messages();
		roles Croles = new roles();
		ArrayList<String> PlayerRoles = Croles.getRoles(userName);
		
		if(PlayerRoles.contains("Joueur")){
			Croles.removeRole("Joueur", userName);
			Cmessages.sendPrivateMessage("Votre Grade **Joueur** vous a bien été retiré sur Discord", userName);
			
		}if(PlayerRoles.contains("Joueur actif")){
			Croles.removeRole("Joueur actif", userName);
			Cmessages.sendPrivateMessage("Votre Grade **Joueur actif** vous a bien été retiré sur Discord", userName);
			
		}if(PlayerRoles.contains("RolePlay Player")){
			Croles.removeRole("RolePlay Player", userName);
			Cmessages.sendPrivateMessage("Votre Grade **RolePlay Player** vous a bien été retiré sur Discord", userName);
			
		}if(PlayerRoles.contains("LapWars Player")){
			Croles.removeRole("LapWars Player", userName);
			Cmessages.sendPrivateMessage("Votre Grade **LapWars Player** vous a bien été retiré sur Discord", userName);
			
		}if(PlayerRoles.contains("TntWars Player")){
			Croles.removeRole("TntWars Player", userName);
			Cmessages.sendPrivateMessage("Votre Grade **TntWars Player** vous a bien été retiré sur Discord", userName);
			
		}
	}
	public void removeRole(String userName){
		
		messages Cmessages = new messages();
		roles Croles = new roles();
		ArrayList<String> PlayerRoles = Croles.getRoles(userName);
		
		Set<String> section = main.configuration.getConfigurationSection("grades").getKeys(false);	
		String item[] = section.toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
		for(int i = 1; i <= section.size(); i++){
			int number = i - 1;
			
			if(PlayerRoles.contains(item[number])){
				if(!item[number].equals("Joueur")){
					Croles.removeRole(item[number], userName);
					Cmessages.sendPrivateMessage("Votre Grade **" + item[number] + "** vous a bien été retiré sur Discord", userName);
				}
				
			}
		}
	}
	public void setRole(String playerName){
		
		if(!main.config.contains(playerName + ".discord")) return;
		
		String userName = main.config.getString(playerName + ".discord");
		messages Cmessages = new messages();
		roles Croles = new roles();
		String grade = main.config.getString(playerName + ".grade");
		
		Croles.addRole(grade, userName);
		Croles.addRole("Joueur actif", userName);
		Cmessages.sendPrivateMessage("Votre Grade **" + grade + "** a bien été transféré sur Discord", userName);
		
	}
	
	public void onDay(){
		
		roles CRoles = new roles();
		user CUser = new user();
		messages CMessages = new messages();
		
		ArrayList<String> users = CUser.getUsers();
		
		for(int i = 0; i < users.size(); i++){
			String user = users.get(i);
			if(!CRoles.getRoles(user).contains("Membre")){
				CMessages.sendPrivateMessage(":warning: **Vous n'avez pas accepté le règlement **:warning: \n"
						+ "Veuillez vous rendre dans le salon #:warning:-règles-:warning: "
						+ "et réagir avec :white_check_mark: pour accéder à l'entièreté du serveur.", user);
			}
		}
		
	}
	
	public void onPlayerDay(String p){
		
		@SuppressWarnings("deprecation")
		int currentDay = new realDate().getRealDate().getDate();
		String DiscordName = main.config.getString(p + ".discord");
		
		int lastDay = main.config.getInt(p + ".rp.lastday");
		if(currentDay < lastDay) currentDay = currentDay + 30;
		
		if(lastDay == 0 || currentDay - lastDay >= 7){
			roles Croles = new roles();
			if(Croles.getRoles(DiscordName).contains("RolePlay Player")) Croles.removeRole("RolePlay Player", DiscordName);
		}
		
		lastDay = main.config.getInt(p + ".tntwars.lastday");
		if(currentDay < lastDay) currentDay = currentDay + 30;
		
		if(lastDay == 0 || currentDay - lastDay >= 7){
			roles Croles = new roles();
			if(Croles.getRoles(DiscordName).contains("TntWars Player")) Croles.removeRole("TntWars Player", DiscordName);
		}
		
		lastDay = main.config.getInt(p + ".bedwars.lastday");
		if(currentDay < lastDay) currentDay = currentDay + 30;
		
		if(lastDay == 0 || currentDay - lastDay >= 7){
			roles Croles = new roles();
			if(Croles.getRoles(DiscordName).contains("BedWars Player")) Croles.removeRole("BedWars Player", DiscordName);
		}
		
		lastDay = main.config.getInt(p + ".lastday");
		if(currentDay < lastDay) currentDay = currentDay + 30;
		
		if(lastDay == 0 || currentDay - lastDay >= 7){
			roles Croles = new roles();
			if(Croles.getRoles(DiscordName).contains("Joueur actif")) Croles.removeRole("Joueur actif", DiscordName);
		}
		
	}
	
	public void onWeek(){
		
		roles Croles = new roles();
		for(String userName : new user().getUsers()){
				
			if(main.config.getInt("discord.list.users." + userName + ".msgsend") >= 20){
				Croles.addRole("Membre actif", userName);
			}else{
				Croles.removeRole("Membre actif", userName);
			}
			main.config.set("discord.list.users." + userName, null);
					
			
		}
		
	}
	
	public void onReact(String userName, String reaction){
		
		if(reaction.equals("🆕")){
			new roles().addRole("Notifs changelog", userName);
		}else if(reaction.equals("⏏")){
			new roles().addRole("Notifs connexions", userName);
		}
		
	}
	public void onUnreact(String userName, String reaction){
		
		if(reaction.equals("🆕")){
			new roles().removeRole("Notifs changelog", userName);
		}else if(reaction.equals("⏏")){
			new roles().removeRole("Notifs connexions", userName);
		}
		
	}

}

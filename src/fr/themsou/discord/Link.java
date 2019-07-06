package fr.themsou.discord;

import fr.themsou.diffusion.api.messages;
import fr.themsou.diffusion.api.user;
import fr.themsou.main.main;

public class Link {
	
	messages Cmessages = new messages();
	
	public void userSendPrivateMessage(String userName, String message){
		
		user Cuser = new user();
		
		if(!Cuser.isInTntGun(userName)){ Cmessages.sendPrivateMessage("Vous n'etes pas dans le serveur TntGun !\nhttp://discord.gg/zAEAYnN/", userName); return; }
		
		String userNick = Cuser.getPlayerNickName(userName);
		String action = main.config.getString("discord.list.users." + userName + ".ac");
		String actionParam = main.config.getString("discord.list.users." + userName + ".acp");
		
		if(action == null) return;
		
		if(message.contains("n") || message.contains("N")){
			
			if(main.config.getString(userNick + ".discord") != null){
				if(main.config.getString(userNick + ".discord").equals(userName)){
					
					if(action.equals("ipchange")){
						
						Cmessages.sendPrivateMessage("Daccord, nous ne touchons à rien !", userName);
						
						main.config.set("discord.list.users." + userName + ".ac", null);
						main.config.set("discord.list.users." + userName + ".acp", null);
					}
					
				}	
			}
			if(action.equals("link")){
				
				Cmessages.sendPrivateMessage("Daccord, nous ne touchons à rien !", userName);
				
				main.config.set("discord.list.users." + userName + ".ac", null);
				main.config.set("discord.list.users." + userName + ".acp", null);
			} 
				
			
		}else{
			if(main.config.getString(userNick + ".discord") != null){
				if(main.config.getString(userNick + ".discord").equals(userName)){
					
					if(action.equals("ipchange")){
						
						Cmessages.sendPrivateMessage("Vottre nouvelle ip est maintenant : " + actionParam, userName);
						main.config.set(userNick + ".ip", actionParam);
						
						
						main.config.set("discord.list.users." + userName + ".ac", null);
						main.config.set("discord.list.users." + userName + ".acp", null);
						
					}
					
				}
			}
			if(action.equals("link")){	
				
				Roles CRoles = new Roles();
				
				String userCoresp = main.config.getString(userNick + ".discord");
				if(userCoresp != null){
					main.config.set(userNick + ".discord", null);
					if(!userCoresp.equals(userName)){
						Cuser.setUserNick(userCoresp, null);
					}
					
					
					Cmessages.sendPrivateMessage("Votre compte à été dé-lié car **" + userNick + "** s'est lié à un autre compte", userCoresp);
					CRoles.removeStatusRoles(userCoresp);
					CRoles.removeRole(userCoresp);
					
				}
			
				Cuser.setUserNick(userName, actionParam);
				
				Cmessages.sendPrivateMessage("Vos deux comptes sont maintenant liés !", userName);
				
				main.config.set(actionParam + ".discord", userName);
				CRoles.setRole(actionParam);
				
				main.config.set("discord.list.users." + userName + ".ac", null);
				main.config.set("discord.list.users." + userName + ".acp", null);
				
				new Counter().refreshCounters();
			
				
				
			}
				
				
		}
		
	}
	
	
	public void linkPlayer(String playerName, String userName) {
		
		user Cuser = new user();
		if(!Cuser.isInTntGun(userName)) return;
		
		Cmessages.sendPrivateMessage(playerName + " souhaite lier son compte Minecraft avec votre compte Discord.\nEst-ce que c'est bien vous ? (oui/non)", userName);
		
		main.config.set("discord.list.users." + userName + ".ac", "link");
		main.config.set("discord.list.users." + userName + ".acp", playerName);
		
		
	}
	
	public void changePlayerIP(String playerName, String userName, String newIP) {
		
		String newIPHide = newIP.subSequence(0, 8) + ".***";
				
		Cmessages.sendPrivateMessage(playerName + " souhaite modifier son ip par défaut de " + main.config.getString(playerName + ".ip") + " en " + newIPHide + "\nEst-ce que c'est bien vous ? (oui/non)", userName);
		
		main.config.set("discord.list.users." + userName + ".ac", "ipchange");
		main.config.set("discord.list.users." + userName + ".acp", newIP);
		
		
		
	}
	
	public void userChangeNick(String OldUserNick, String NewUserNick, String userName){
		
		if(main.config.contains(OldUserNick + ".discord")){
			
			Cmessages.sendPrivateMessage("Vous venez de changer votre surnom de **" + OldUserNick + "** en **" + NewUserNick + "** Votre compte Discord est donc dé-lié a votre compte Minecraft-TntGun", userName);
			
			main.config.set(OldUserNick + ".discord", null);
			
			new Roles().removeStatusRoles(userName);
			new Roles().removeRole(userName);
		}
		
		new Counter().refreshCounters();
		
	}
	
	public void userChangeName(String OldUserName, String NewUserName){
		
		user Cuser = new user();

		for(String playerName : main.config.getConfigurationSection("").getKeys(false)){
			
			if(main.config.contains(playerName + ".discord")){
				
				if(main.config.getString(playerName + ".discord").equals(OldUserName)){
					
					main.config.set(playerName + ".discord", NewUserName);
					Cuser.setUserNick(NewUserName, playerName);
				}
			}
		}
		
	}
	
	public void userLeaveServer(String userName){
		

		for(String playerName : main.config.getConfigurationSection("").getKeys(false)){
			
			if(main.config.contains(playerName + ".discord")){
				
				if(main.config.getString(playerName + ".discord").equals(userName)){
					
					main.config.set(playerName + ".discord", null);
					
				}
			}
		}
		
	}

}

package fr.themsou.discord.vocal;

import java.awt.Color;

import fr.themsou.diffusion.api.messages;
import fr.themsou.diffusion.api.user;
import fr.themsou.diffusion.api.vocal;
import fr.themsou.main.main;

public class Music {

	public static long channelId = 554358119262584853L;
	public static long vocalId = 414907026800640042L;
	
	public static vocal vocal = null;
	public static long vocalControler = 0;
	public static String radio = "";
	messages Cmessages = new messages();
	
	public void userSendMessage(String userName, String message, long messageId){
		
		
		String[] args = message.split(" ");
		
		Cmessages.deleteMessage(channelId, messageId);
		if(vocal == null) vocal = new vocal();
		
		if(args[0].equalsIgnoreCase("/play") && args.length == 2){
			
			if(new user().getConectedVocalChannel(userName) == vocalId){
				
				vocal.joinChannel(vocalId);
				try{
					Thread.sleep(250);
				}catch(InterruptedException e1){ e1.printStackTrace(); }
				
				if(!radio.isEmpty() && vocalControler != 0){
					new messages().deleteMessage(channelId, vocalControler);
					vocalControler = 0;
					radio = "";
					vocal.clearWhiout();
				}
				
				vocal.playFirst(args[1]);
				
			}else{
				sendSimpleEmbed("Vous devez être connecté dans le salon vocal 📻 Musique 📻", Color.RED, 15);
			}
				
		}else if(args[0].equalsIgnoreCase("/playadd") && args.length == 2){
			
			if(new user().getConectedVocalChannel(userName) == vocalId){
				
				vocal.joinChannel(vocalId);
				try{
					Thread.sleep(250);
				}catch(InterruptedException e1){ e1.printStackTrace(); }
					
				if(!radio.isEmpty() && vocalControler != 0){
					new messages().deleteMessage(channelId, vocalControler);
					vocalControler = 0;
					radio = "";
					vocal.clearWhiout();
				}
				
				vocal.playLast(args[1]);
				
			}else{
				sendSimpleEmbed("Vous devez être connecté dans le salon vocal 📻 Musique 📻", Color.RED, 15);
			}
			
		}else if(args[0].equalsIgnoreCase("/radio")){
			
			if(args.length >= 2 && args.length <= 4){
				if(new user().getConectedVocalChannel(userName) == vocalId){
					
					if(args.length == 3) args[1] = args[1] + " " + args[2];
					if(args.length == 4) args[1] = args[1] + " " + args[2] + " " + args[3];
					String radios = "";
					
					for(String section : main.configuration.getConfigurationSection("discord.radio").getKeys(false)){
						radios += section + ", ";
						if(args[1].equalsIgnoreCase(section)){
							
							vocal.joinChannel(vocalId);
							try{
								Thread.sleep(250);
							}catch(InterruptedException e1){ e1.printStackTrace(); }
							
							if(radio.isEmpty() && vocalControler != 0){
								new messages().deleteMessage(channelId, vocalControler);
								vocalControler = 0;
							}
							
							vocal.clearWhiout();
							vocal.playLast(main.configuration.getString("discord.radio." + section));
							radio = section;
							return;
						}
					}
					
					Cmessages.clearEmbed();
					Cmessages.setColor(Color.RED);
					Cmessages.addfield("Différentes radios : ", radios, false);
					sendEmbedTimeAndAutoComplete(45);
					
				}else{
					sendSimpleEmbed("Vous devez être connecté dans le salon vocal 📻 Musique 📻", Color.RED, 15);
				}
			}else{
				String radios = "";
				
				for(String section : main.configuration.getConfigurationSection("discord.radio").getKeys(false)){
					radios += section + ", ";
				}
				
				Cmessages.clearEmbed();
				Cmessages.setColor(Color.RED);
				Cmessages.addfield("Différentes radios : ", radios, false);
				sendEmbedTimeAndAutoComplete(45);
			}
		
		}else if(args[0].equalsIgnoreCase("/playlist")){
			
			if(args.length >= 2 && args.length <= 4){
				if(new user().getConectedVocalChannel(userName) == vocalId){
					
					if(args.length == 3) args[1] = args[1] + " " + args[2];
					if(args.length == 4) args[1] = args[1] + " " + args[2] + " " + args[3];
					String playlist = "";
					String tntplaylist = "";
					String ytplaylist = "";
					
					for(String section : main.config.getConfigurationSection("discord.list.playlist").getKeys(false)){
						playlist += section + ", ";
						if(args[1].equalsIgnoreCase(section)){
							vocal.joinChannel(vocalId);
							try{
								Thread.sleep(250);
							}catch(InterruptedException e1){ e1.printStackTrace(); }
							
							if(!radio.isEmpty() && vocalControler != 0){
								new messages().deleteMessage(channelId, vocalControler);
								vocalControler = 0;
								radio = "";
							}
							vocal.clearWhiout();
							
							for(String playlistId : main.config.getConfigurationSection("discord.list.playlist." + section).getKeys(false)){
								vocal.playLast(main.config.getString("discord.list.playlist." + section + "." + playlistId));
							}
							
							return;
						}
					}
					for(String section : main.configuration.getConfigurationSection("discord.tntplaylist").getKeys(false)){
						tntplaylist += section + ", ";
						if(args[1].equalsIgnoreCase(section)){
							vocal.joinChannel(vocalId);
							try{
								Thread.sleep(250);
							}catch(InterruptedException e1){ e1.printStackTrace(); }
							
							if(!radio.isEmpty() && vocalControler != 0){
								new messages().deleteMessage(channelId, vocalControler);
								vocalControler = 0;
								radio = "";
							}
							vocal.clearWhiout();
							
							vocal.playLast(main.configuration.getString("discord.tntplaylist." + section));
							
							return;
						}
					}
					for(String section : main.configuration.getConfigurationSection("discord.ytplaylist").getKeys(false)){
						ytplaylist += section + ", ";
						if(args[1].equalsIgnoreCase(section)){
							vocal.joinChannel(vocalId);
							try{
								Thread.sleep(250);
							}catch(InterruptedException e1){ e1.printStackTrace(); }
							
							if(!radio.isEmpty() && vocalControler != 0){
								new messages().deleteMessage(channelId, vocalControler);
								vocalControler = 0;
								radio = "";
							}
							vocal.clearWhiout();
							
							vocal.playLast(main.configuration.getString("discord.ytplaylist." + section));
							
							return;
						}
					}
					Cmessages.clearEmbed();
					Cmessages.setColor(Color.RED);
					Cmessages.addfield("Playlist du serveur : ", tntplaylist, false);
					Cmessages.addfield("Playlist YouTube : ", ytplaylist, false);
					Cmessages.addfield("Playlist des utilisateurs : ", playlist, false);
					sendEmbedTimeAndAutoComplete(45);
					
				}else{
					sendSimpleEmbed("Vous devez être connecté dans le salon vocal 📻 Musique 📻", Color.RED, 15);
				}
			}else{
				String playlist = "";
				String tntplaylist = "";
				String ytplaylist = "";
				
				for(String section : main.config.getConfigurationSection("discord.list.playlist").getKeys(false)){
					playlist += section + ", ";
				}
				for(String section : main.configuration.getConfigurationSection("discord.tntplaylist").getKeys(false)){
					tntplaylist += section + ", ";
				}
				for(String section : main.configuration.getConfigurationSection("discord.ytplaylist").getKeys(false)){
					ytplaylist += section + ", ";
				}
				Cmessages.clearEmbed();
				Cmessages.setColor(Color.RED);
				Cmessages.addfield("Playlist du serveur : ", tntplaylist, false);
				Cmessages.addfield("Playlist YouTube : ", ytplaylist, false);
				Cmessages.addfield("Playlist des utilisateurs : ", playlist, false);
				sendEmbedTimeAndAutoComplete(45);
			}
		
		}else if(args[0].equalsIgnoreCase("/addplaylist") && args.length >= 2 && args.length <= 4){
			
			if(new user().getConectedVocalChannel(userName) == vocalId){
				
				if(args.length == 3) args[1] = args[1] + " " + args[2];
				if(args.length == 4) args[1] = args[1] + " " + args[2] + " " + args[3];
				
				String[] lastMusics = vocal.getLastPlayListUrl();
				String currentMusic = vocal.getCurrentTrackUrl();
				String[] afterMusic = vocal.getPlayListUrl();
				
				if(lastMusics.length == 0 && afterMusic.length == 0 && currentMusic == null){
					sendSimpleEmbed("Vous ne pouvez pas sauvegarder une playlist vide.", Color.RED, 15);
					return;
					
				}if(main.config.contains("discord.list.playlist." + args[1]) || main.configuration.contains("discord.tntplaylist." + args[1]) || main.configuration.contains("discord.ytplaylist." + args[1])){
					sendSimpleEmbed("Ce nom est déjà utilisé.", Color.RED, 15);
					return;
					
				}if(!radio.isEmpty()){
					sendSimpleEmbed("Vous ne pouvez pas sauvegarder une radio.", Color.RED, 15);
					return;
				}
				
				int i = 0;
				for(String url : lastMusics){
					main.config.set("discord.list.playlist." + args[1] + "." + i, url);
					i++;
				}
				if(currentMusic != null){
					main.config.set("discord.list.playlist." + args[1] + "." + i, currentMusic);
					i++;
				}
				for(String url : afterMusic){
					main.config.set("discord.list.playlist." + args[1] + "." + i, url);
					i++;
				}
				
				sendSimpleEmbed("Votre playlist a bien été enregistré avec le nom : **" + args[1] + "**", Color.GREEN, 15);
				
				
			}else{
				sendSimpleEmbed("Vous devez être connecté dans le salon vocal 📻 Musique 📻", Color.RED, 15);
			}
			
		}else{
			if(!vocal.isInChannel(vocalId)){
				
				Cmessages.clearEmbed();
				Cmessages.addfield(":loud_sound:  **/play <lien>**", "Ajouter une musique au début de la playlist.", false);
				Cmessages.addfield(":loud_sound:  **/playadd <lien>**", "Ajouter une musique à la fin de la playlist.", false);
				Cmessages.addfield(":radio:  **/radio <nom>**", "Écouter une radio.", false);
				Cmessages.addfield(":repeat:  **/playlist <nom>**", "Lancer une playlist.", false);
				Cmessages.addfield(":repeat_one:  **/addplaylist <nom>**", "Sauvegarder la playlist.", false);
				
				sendEmbedTimeAndAutoComplete(30);
			}
		}
		
	}
	
	public void sendSimpleEmbed(String content, Color color, int time){
		
		Cmessages.clearEmbed();
		Cmessages.setColor(color);
		Cmessages.setDescription(content);
		
		sendEmbedTimeAndAutoComplete(time);
		
	}
	
	public void sendEmbedTimeAndAutoComplete(int time){
		
		Cmessages.setAuthor("Musique", "https://tntgun.fr/", "https://tntgun.fr/img/icon.png");
		Cmessages.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");
		
		
		long id = Cmessages.sendEmbed(channelId);
		Cmessages.clearEmbed();
		
		new Thread(new Runnable(){
            public void run() {
            	try{ Thread.sleep(time * 1000); }catch(InterruptedException e1){ e1.printStackTrace(); }
            	Cmessages.deleteMessage(channelId, id);
            }
        }).start();
		
	}
	
	// AUTRES LISTENERS
	
	public void playerVocalDisconect(int lastUsers){
		
		if(lastUsers == 0){
			vocal.leaveChannel();
		}
		
	}
	
	public void userReact(String reaction, String userName, Long channelId, Long messageId){
		
		if(messageId == vocalControler){
			
			if(new user().getConectedVocalChannel(userName) != vocalId){
				Cmessages.removeReact(channelId, messageId, reaction, userName);
				return;
			}
			
			if(reaction.equals("⏯")){
				Cmessages.removeReact(channelId, messageId, reaction, userName);
				vocal.pause();
				
			}else if(reaction.equals("⏮")){
				Cmessages.removeReact(channelId, messageId, reaction, userName);
				vocal.last();
				
			}else if(reaction.equals("⏭")){
				Cmessages.removeReact(channelId, messageId, reaction, userName);
				vocal.next();
				
			}else if(reaction.equals("❌")){
				Cmessages.removeReact(channelId, messageId, reaction, userName);
				vocal.clearCurrent();
				
			}else if(reaction.equals("🚫")){
				Cmessages.removeReact(channelId, messageId, reaction, userName);
				vocal.clear();
				
			}else if(reaction.equals("🛑")){
				vocal.leaveChannel();
			}
		}
	}
	
	
}

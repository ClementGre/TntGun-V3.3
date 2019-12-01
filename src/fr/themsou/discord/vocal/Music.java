package fr.themsou.discord.vocal;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fr.themsou.diffusion.api.messages;
import fr.themsou.diffusion.api.roles;
import fr.themsou.diffusion.api.user;
import fr.themsou.diffusion.api.vocal;
import fr.themsou.main.main;
import fr.themsou.methodes.realDate;

public class Music {

	public static long channelId = 554358119262584853L;
	public static long vocalId = 414907026800640042L;
	
	public static vocal vocal = null;
	public static long vocalControler = 0;
	public static String radio = "";

	public static boolean djMode = false;
	public static ArrayList<String> logs = new ArrayList<>();
	public static long logMessage = 648129999513059328L;

	messages Cmessages = new messages();
	
	public void userSendMessage(String userName, String message, long messageId){

		String[] args = message.split(" ");
		
		Cmessages.deleteMessage(channelId, messageId);
		if(vocal == null) vocal = new vocal();

		if(!vocal.isInChannel(vocalId)){
			djMode = false;
		}

		if(djMode){
			if(!new roles().getRoles(userName).contains("DJ")){
				Cmessages.sendPrivateMessage("Le mode DJ est activé : seul les membres ayant le rôle @DJ peuvent gérer la musique.", userName);
				return;
			}
		}

		if(args[0].equalsIgnoreCase("/play") && args.length == 2){
			if(new user().getConectedVocalChannel(userName) == vocalId){

				addLog(userName, "Éxécute la commande " + message + " (Valide)");
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
				addLog(userName, "Éxécute la commande " + message + " (Invalide)");
				sendSimpleEmbed("Vous devez être connecté dans le salon vocal 📻 Musique 📻", Color.RED, 15);
			}
				
		}else if(args[0].equalsIgnoreCase("/playadd") && args.length == 2){
			if(new user().getConectedVocalChannel(userName) == vocalId){

				addLog(userName, "Éxécute la commande " + message + " (Valide)");

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
				addLog(userName, "Éxécute la commande " + message + " (Invalide)");
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

							addLog(userName, "Éxécute la commande " + message + " (Valide)");

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

					addLog(userName, "Éxécute la commande " + message + " (Invalide)");
					Cmessages.clearEmbed();
					Cmessages.setColor(Color.RED);
					Cmessages.addfield("Différentes radios : ", radios, false);
					sendEmbedTimeAndAutoComplete(45);
					
				}else{
					addLog(userName, "Éxécute la commande " + message + " (Invalide)");
					sendSimpleEmbed("Vous devez être connecté dans le salon vocal 📻 Musique 📻", Color.RED, 15);
				}
			}else{
				addLog(userName, "Éxécute la commande " + message + " (Invalide)");
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

							addLog(userName, "Éxécute la commande " + message + " (Valide)");
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

							addLog(userName, "Éxécute la commande " + message + " (Valide)");
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

							addLog(userName, "Éxécute la commande " + message + " (Valide)");
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
					addLog(userName, "Éxécute la commande " + message + " (Invalide)");
					Cmessages.clearEmbed();
					Cmessages.setColor(Color.RED);
					Cmessages.addfield("Playlist du serveur : ", tntplaylist, false);
					Cmessages.addfield("Playlist YouTube : ", ytplaylist, false);
					Cmessages.addfield("Playlist des utilisateurs : ", playlist, false);
					sendEmbedTimeAndAutoComplete(45);
					
				}else{
					addLog(userName, "Éxécute la commande " + message + " (Invalide)");
					sendSimpleEmbed("Vous devez être connecté dans le salon vocal 📻 Musique 📻", Color.RED, 15);
				}
			}else{
				addLog(userName, "Éxécute la commande " + message + " (Invalide)");
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
					addLog(userName, "Éxécute la commande " + message + " (Invalide)");
					return;
					
				}if(main.config.contains("discord.list.playlist." + args[1]) || main.configuration.contains("discord.tntplaylist." + args[1]) || main.configuration.contains("discord.ytplaylist." + args[1])){
					sendSimpleEmbed("Ce nom est déjà utilisé.", Color.RED, 15);
					addLog(userName, "Éxécute la commande " + message + " (Invalide)");
					return;
					
				}if(!radio.isEmpty()){
					sendSimpleEmbed("Vous ne pouvez pas sauvegarder une radio.", Color.RED, 15);
					addLog(userName, "Éxécute la commande " + message + " (Invalide)");
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
				addLog(userName, "Éxécute la commande " + message + " (Valide)");
				sendSimpleEmbed("Votre playlist a bien été enregistré avec le nom : **" + args[1] + "**", Color.GREEN, 15);
				
				
			}else{
				addLog(userName, "Éxécute la commande " + message + " (Invalide)");
				sendSimpleEmbed("Vous devez être connecté dans le salon vocal 📻 Musique 📻", Color.RED, 15);
			}
			
		}else{
			if(args[0].equalsIgnoreCase("/play") || args[0].equalsIgnoreCase("/playadd") || args[0].equalsIgnoreCase("/radio") || args[0].equalsIgnoreCase("/playlist") || args[0].equalsIgnoreCase("/addplaylist")){
				addLog(userName, "Éxécute la commande " + message + " (Inalide)");
			}else{
				addLog(userName, "Éxécute une commande inconnue");
			}

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
			addLog("TntGun-BOT", "Quitte le salon vocal : Personne de connecté.");
		}
		
	}
	
	public void userReact(String reaction, String userName, Long channelId, Long messageId){
		
		if(messageId == vocalControler){

			Cmessages.removeReact(channelId, messageId, reaction, userName);

			if(!vocal.isInChannel(vocalId)){
				djMode = false;
			}
			if(new user().getConectedVocalChannel(userName) != vocalId){
				Cmessages.sendPrivateMessage("Vous devez être connecté dans le salon vocal \uD83D\uDCFBmusique\uD83D\uDCFB pour pouvoir gérer la musique.", userName);
				return;
			}
			if(reaction.equals("\uD83C\uDFA4")){
				if(!new roles().getRoles(userName).contains("DJ") || new roles().getRoles(userName).contains("Admin") || new roles().getRoles(userName).contains("Super-Modo")){
					djMode = !djMode;
					addLog(userName, "Réaction :microphone:, mode DJ " + (djMode ? "activé" : "désactivé"));
					new VocalEvents().refreshVocalControler();
				}else{
					addLog(userName, "Réaction :microphone:, mode DJ (Invalide)");
					Cmessages.sendPrivateMessage("Vous devez être dans le staff ou être DJ pour activer / désactiver le mode DJ.", userName);
				}
				return;
			}
			if(djMode){
				if(!new roles().getRoles(userName).contains("DJ")){
					Cmessages.sendPrivateMessage("Le mode DJ est activé : seul les membres ayant le rôle @DJ peuvent gérer la musique.", userName);
					return;
				}
			}
			
			if(reaction.equals("⏯")){
				addLog(userName, "Réaction :play_pause:, pause");
				vocal.pause();
				
			}else if(reaction.equals("⏮")){
				addLog(userName, "Réaction :track_previous:, musique préçédente : " + (vocal.getLastPlayList().length >= 1 ? vocal.getLastPlayList()[vocal.getLastPlayList().length - 1] : "Aucune piste"));
				vocal.last();
				
			}else if(reaction.equals("⏭")){
				addLog(userName, "Réaction :track_next:, musique suivante : " + (vocal.getPlayList().length >= 1 ? vocal.getPlayList()[0] : "Aucune piste"));
				vocal.next();
				
			}else if(reaction.equals("❌")){
				addLog(userName, "Réaction :x:, supprimé la musique " + vocal.getCurrentTrack());
				vocal.clearCurrent();
				
			}else if(reaction.equals("🚫")){
				addLog(userName, "Réaction :no_entry_sign:, tout retiré");
				vocal.clear();
				
			}else if(reaction.equals("🛑")){
				addLog(userName, "Réaction :octagonal_sign:, tout arrété");
				vocal.leaveChannel();
			}
		}
	}

	public void addLog(String user, String message){

		Date date = new realDate().getRealDate();
		String strDate = date.getDate() + "/" + date.getMonth() + " - " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

		logs.add(0, "[" + strDate + "] *" + user + "* : " + message);

		if(logs.size() > 20){
			logs.remove(logs.size() - 1);
		}
		updateLogMessage();

	}
	public void updateLogMessage(){

		Cmessages.clearEmbed();
		Cmessages.setColor(Color.ORANGE);
		Cmessages.setAuthor("Logs", "https://tntgun.fr/", "https://tntgun.fr/img/icon.png");
		Cmessages.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");

		String desc = "";
		for(int i = logs.size()-1; i >= 0; i--){
			desc += "\n" + logs.get(i);
		}
		Cmessages.setDescription(desc);

		Cmessages.EditEmbed(channelId, logMessage);
		Cmessages.clearEmbed();
	}
	
	
}

package fr.themsou.discord.vocal;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

import fr.themsou.diffusion.music.Vocal;
import fr.themsou.discord.tools.Tools;
import fr.themsou.main.main;
import fr.themsou.methodes.realDate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class Music {

	public static long channelId = 554358119262584853L;
	public static long vocalId = 414907026800640042L;
	
	public static Vocal vocal = null;
	public static Message vocalControler = null;
	public static String radio = "";

	public static boolean djMode = false;
	public static ArrayList<String> logs = new ArrayList<>();
	public static long logMessage = 648129999513059328L;

	
	public void userSendMessage(MessageReceivedEvent e){

		String msg = e.getMessage().getContentRaw();
		String[] args = msg.split(" ");

		User user = e.getAuthor();
		Member member = e.getMember();

		e.getMessage().delete().queue();
		if(vocal == null) vocal = new Vocal();

		if(!vocal.isInChannel(vocalId)){
			djMode = false;
		}

		if(djMode){
			Role role = main.jda.getRolesByName("DJ", false).get(0);
			if(!member.getRoles().contains(role)){
				sendAutoMessage("Le mode DJ est activé : seul les membres ayant le rôle @DJ peuvent gérer la musique.", Color.RED, user, false);
				addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Invalide)");
				return;
			}
		}

		if(Tools.getConectedVoiceChannel(member) != vocalId){
			sendAutoMessage("Vous devez être connecté dans le salon Vocal 📻 Musique 📻", Color.RED, user, false);
			addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Invalide)");
			return;
		}

		if(args[0].equalsIgnoreCase("/play") && args.length == 2){

			addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Valide)");
			vocal.joinChannel(vocalId);
			try{
				Thread.sleep(250);
			}catch(InterruptedException e1){ e1.printStackTrace(); }

			if(!radio.isEmpty() && vocalControler != null){
				vocalControler.delete().queue();
				vocalControler = null;
				radio = "";
				vocal.clearWhiout();
			}

			vocal.playFirst(args[1]);
				
		}else if(args[0].equalsIgnoreCase("/playadd") && args.length == 2){

				addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Valide)");

				vocal.joinChannel(vocalId);
				try{
					Thread.sleep(250);
				}catch(InterruptedException e1){ e1.printStackTrace(); }
					
				if(!radio.isEmpty() && vocalControler != null){
					vocalControler.delete().queue();
					vocalControler = null;
					radio = "";
					vocal.clearWhiout();
				}
				
				vocal.playLast(args[1]);
			
		}else if(args[0].equalsIgnoreCase("/radio")){
			if(args.length >= 2 && args.length <= 4){

				if(args.length == 3) args[1] = args[1] + " " + args[2];
				if(args.length == 4) args[1] = args[1] + " " + args[2] + " " + args[3];
				String radios = "";

				for(String section : main.configuration.getConfigurationSection("discord.radio").getKeys(false)){
					radios += section + ", ";
					if(args[1].equalsIgnoreCase(section)){

						addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Valide)");

						vocal.joinChannel(vocalId);
						try{
							Thread.sleep(250);
						}catch(InterruptedException e1){ e1.printStackTrace(); }

						if(radio.isEmpty() && vocalControler != null){
							vocalControler.delete().queue();
							vocalControler = null;
						}

						vocal.clearWhiout();
						vocal.playLast(main.configuration.getString("discord.radio." + section));
						radio = section;
						return;
					}
				}

				addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Invalide)");
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.RED);
				embed.addField("Différentes radios : ", radios, false);
				sendEmbedTimeAndAutoComplete(embed, 45);

			}else{
				addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Invalide)");
				String radios = "";
				
				for(String section : main.configuration.getConfigurationSection("discord.radio").getKeys(false)){
					radios += section + ", ";
				}

				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.RED);
				embed.addField("Différentes radios : ", radios, false);
				sendEmbedTimeAndAutoComplete(embed, 45);
			}
		
		}else if(args[0].equalsIgnoreCase("/playlist")){
			if(args.length >= 2 && args.length <= 4){
					
				if(args.length == 3) args[1] = args[1] + " " + args[2];
				if(args.length == 4) args[1] = args[1] + " " + args[2] + " " + args[3];
				String playlist = "";
				String tntplaylist = "";
				String ytplaylist = "";

				for(String section : main.config.getConfigurationSection("discord.list.playlist").getKeys(false)){
					playlist += section + ", ";
					if(args[1].equalsIgnoreCase(section)){

						addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Valide)");
						vocal.joinChannel(vocalId);
						try{
							Thread.sleep(250);
						}catch(InterruptedException e1){ e1.printStackTrace(); }

						if(!radio.isEmpty() && vocalControler != null){
							vocalControler.delete().queue();
							vocalControler = null;
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

						addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Valide)");
						vocal.joinChannel(vocalId);
						try{
							Thread.sleep(250);
						}catch(InterruptedException e1){ e1.printStackTrace(); }

						if(!radio.isEmpty() && vocalControler != null){
							vocalControler.delete().queue();
							vocalControler = null;
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

						addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Valide)");
						vocal.joinChannel(vocalId);
						try{
							Thread.sleep(250);
						}catch(InterruptedException e1){ e1.printStackTrace(); }

						if(!radio.isEmpty() && vocalControler != null){
							vocalControler.delete().queue();
							vocalControler = null;
							radio = "";
						}
						vocal.clearWhiout();

						vocal.playLast(main.configuration.getString("discord.ytplaylist." + section));

						return;
					}
				}
				addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Invalide)");
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.RED);
				embed.addField("Playlist du serveur : ", tntplaylist, false);
				embed.addField("Playlist YouTube : ", ytplaylist, false);
				embed.addField("Playlist des utilisateurs : ", playlist, false);
				sendEmbedTimeAndAutoComplete(embed, 45);

			}else{
				addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Invalide)");
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
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.RED);
				embed.addField("Playlist du serveur : ", tntplaylist, false);
				embed.addField("Playlist YouTube : ", ytplaylist, false);
				embed.addField("Playlist des utilisateurs : ", playlist, false);
				sendEmbedTimeAndAutoComplete(embed, 45);
			}
		
		}else if(args[0].equalsIgnoreCase("/addplaylist") && args.length >= 2 && args.length <= 4){

				if(args.length == 3) args[1] = args[1] + " " + args[2];
				if(args.length == 4) args[1] = args[1] + " " + args[2] + " " + args[3];
				
				String[] lastMusics = vocal.getLastPlayListUrl();
				String currentMusic = vocal.getCurrentTrackUrl();
				String[] afterMusic = vocal.getPlayListUrl();
				
				if(lastMusics.length == 0 && afterMusic.length == 0 && currentMusic == null){
					sendAutoMessage("Vous ne pouvez pas sauvegarder une playlist vide.", Color.RED, user, true);
					addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Invalide)");
					return;
					
				}if(main.config.contains("discord.list.playlist." + args[1]) || main.configuration.contains("discord.tntplaylist." + args[1]) || main.configuration.contains("discord.ytplaylist." + args[1])){
					sendAutoMessage("Ce nom est déjà utilisé.", Color.RED, user, true);
					addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Invalide)");
					return;
					
				}if(!radio.isEmpty()){
					sendAutoMessage("Vous ne pouvez pas sauvegarder une radio.", Color.RED, user, true);
					addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Invalide)");
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
				addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Valide)");
				sendAutoMessage("Votre playlist a bien été enregistré avec le nom : **" + args[1] + "**", Color.GREEN, user, true);

			
		}else{
			if(args[0].equalsIgnoreCase("/play") || args[0].equalsIgnoreCase("/playadd") || args[0].equalsIgnoreCase("/radio") || args[0].equalsIgnoreCase("/playlist") || args[0].equalsIgnoreCase("/addplaylist")){
				addLog(user.getAsTag(), "Éxécute la commande " + msg + " (Inalide)");
			}else{
				addLog(user.getAsTag(), "Éxécute une commande inconnue");
			}

			if(!vocal.isInChannel(vocalId)){

				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.CYAN);
				embed.addField(":loud_sound:  **/play <lien>**", "Ajouter une musique au début de la playlist.", false);
				embed.addField(":loud_sound:  **/playadd <lien>**", "Ajouter une musique à la fin de la playlist.", false);
				embed.addField(":radio:  **/radio <nom>**", "Écouter une radio.", false);
				embed.addField(":repeat:  **/playlist <nom>**", "Lancer une playlist.", false);
				embed.addField(":repeat_one:  **/addplaylist <nom>**", "Sauvegarder la playlist.", false);

				sendEmbedTimeAndAutoComplete(embed, 30);
			}
		}
	}
	
	public void sendAutoMessage(String msg, Color color, User user, boolean forceOnServer){

		if(vocalControler != null && !forceOnServer){
			PrivateChannel privateChannel = user.openPrivateChannel().complete();
			privateChannel.sendMessage(msg).queue(new Consumer<Message>(){public void accept(Message message){}}, new Consumer<Throwable>(){public void accept(Throwable throwable){}});
		}

		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(color);
		embed.setDescription(msg);
		
		sendEmbedTimeAndAutoComplete(embed, 15);
		
	}
	
	public void sendEmbedTimeAndAutoComplete(EmbedBuilder embed, int time){
		
		embed.setAuthor("Musique", "https://tntgun.fr/", "https://tntgun.fr/img/icon.png");
		embed.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");
		embed.setTimestamp(Instant.now());

		Message msg = main.guild.getTextChannelById(channelId).sendMessage(embed.build()).complete();
		new Thread(new Runnable(){
            public void run() {
            	try{ Thread.sleep(time * 1000); }catch(InterruptedException e1){ e1.printStackTrace(); }
            	msg.delete().queue();
            }
        }).start();
		
	}
	
	// AUTRES LISTENERS
	
	public void playerVocalDisconect(int lastUsers){
		
		if(lastUsers == 0){
			vocal.leaveChannel();
			addLog("TntGun-BOT", "Quitte le salon Vocal : Personne de connecté.");
		}
		
	}
	
	public void userReact(MessageReactionAddEvent e){
		
		if(e.getMessageIdLong() == vocalControler.getIdLong()){

			e.getReaction().removeReaction(e.getUser()).complete();

			if(!vocal.isInChannel(vocalId)){
				djMode = false;
			}
			if(Tools.getConectedVoiceChannel(e.getMember()) != vocalId){
				sendAutoMessage("Vous devez être connecté dans le salon Vocal \uD83D\uDCFBmusique\uD83D\uDCFB pour pouvoir gérer la musique.", Color.RED, e.getUser(), false);
				return;
			}
			if(e.getReactionEmote().getName().equals("\uD83C\uDFA4")){
				Role djRole = main.jda.getRolesByName("DJ", false).get(0);
				Role adminRole = main.jda.getRolesByName("DJ", false).get(0);
				Role smRole = main.jda.getRolesByName("DJ", false).get(0);
				if(e.getMember().getRoles().contains(djRole) || e.getMember().getRoles().contains(adminRole) || e.getMember().getRoles().contains(smRole)){
					djMode = !djMode;
					addLog(e.getUser().getAsTag(), "Réaction :microphone:, mode DJ " + (djMode ? "activé" : "désactivé"));
					new VocalEvents().refreshVocalControler();
				}else{
					addLog(e.getUser().getAsTag(), "Réaction :microphone:, mode DJ (Invalide)");
					sendAutoMessage("Vous devez être dans le staff ou être DJ pour activer / désactiver le mode DJ.", Color.RED, e.getUser(), false);
				}
				return;
			}
			if(djMode){
				Role djRole = main.jda.getRolesByName("DJ", false).get(0);
				if(!e.getMember().getRoles().contains(djRole)){
					sendAutoMessage("Le mode DJ est activé : seul les membres ayant le rôle @DJ peuvent gérer la musique.", Color.RED, e.getUser(), false);
					return;
				}
			}
			
			if(e.getReactionEmote().getName().equals("⏯")){
				addLog(e.getUser().getAsTag(), "Réaction :play_pause:, pause");
				vocal.pause();
				
			}else if(e.getReactionEmote().getName().equals("⏮")){
				addLog(e.getUser().getAsTag(), "Réaction :track_previous:, musique préçédente : " + (vocal.getLastPlayList().length >= 1 ? vocal.getLastPlayList()[vocal.getLastPlayList().length - 1] : "Aucune piste"));
				vocal.last();
				
			}else if(e.getReactionEmote().getName().equals("⏭")){
				addLog(e.getUser().getAsTag(), "Réaction :track_next:, musique suivante : " + (vocal.getPlayList().length >= 1 ? vocal.getPlayList()[0] : "Aucune piste"));
				vocal.next();
				
			}else if(e.getReactionEmote().getName().equals("❌")){
				addLog(e.getUser().getAsTag(), "Réaction :x:, supprimé la musique " + vocal.getCurrentTrack());
				vocal.clearCurrent();
				
			}else if(e.getReactionEmote().getName().equals("🚫")){
				addLog(e.getUser().getAsTag(), "Réaction :no_entry_sign:, tout retiré");
				vocal.clear();
				
			}else if(e.getReactionEmote().getName().equals("🛑")){
				addLog(e.getUser().getAsTag(), "Réaction :octagonal_sign:, tout arrété");
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

		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.ORANGE);
		embed.setAuthor("Logs", "https://tntgun.fr/", "https://tntgun.fr/img/icon.png");
		embed.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");
		embed.setTimestamp(Instant.now());

		String desc = "";
		for(int i = logs.size()-1; i >= 0; i--){
			desc += "\n" + logs.get(i);
		}
		embed.setDescription(desc);

		main.jda.getTextChannelById(channelId).editMessageById(logMessage, embed.build()).queue();
	}
}
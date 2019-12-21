package fr.themsou.discord.vocal;

import fr.themsou.main.main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.Color;
import java.time.Instant;

public class VocalEvents {
	
	public void vocalErrorLoad(String error){
		
		sendSimpleEmbed("Impossible de charger la musique : " + error, Color.RED, 15);
		if(Music.vocal.getCurrentTrack() == null) playlistEnd();
		
	}public void vocalErrorOther(String error){
		
		sendSimpleEmbed("Une erreur est survenue : " + error, Color.RED, 15);
		playlistEnd();
		
	}
	
	public void playlistEnd(){
		if(Music.vocalControler != null){
			Music.vocalControler.delete().queue();
			Music.vocalControler = null;
			Music.radio = "";
		}
		
	}
	public void nextTrack(){
		Music.radio = "";
		refreshVocalControler();
	}
	public void vocalStartMusic(){
		if(Music.vocalControler == null){
			Music.vocalControler = sendVocalControler();
		}else refreshVocalControler();
		
	}
	public void vocalAddMusic(){
		if(Music.vocalControler == null){
			Music.vocalControler = sendVocalControler();
		}else refreshVocalControler();
	}
	
	public Message sendVocalControler(){

		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.ORANGE);
		embed.setAuthor("Musique" + (Music.djMode ? " *Mode DJ activé*" : ""), "https://tntgun.fr/", "https://tntgun.fr/img/icon.png");
		embed.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");
		embed.setTimestamp(Instant.now());
		
		if(!Music.radio.isEmpty()){

			embed.addField(":loud_sound: Radio en cours :", Music.radio, false);

			Message msg = main.guild.getTextChannelById(Music.channelId).sendMessage(embed.build()).complete();

			msg.addReaction("⏯").queue();
			msg.addReaction("🛑").queue();
			
			return msg;
		}
		
		String[] playlist = Music.vocal.getLastPlayList();
		String playlistShow = "";
		
		if(playlist.length >= 1){
			if(playlist.length > 5){
				playlistShow = playlistShow + (playlist.length - 5) + " autres pistes...";
				for(int i = playlist.length - 5; i < playlist.length; i++){
					playlistShow = playlistShow + "\n" + playlist[i];
				}
			}else{
				for(String play : playlist){
					playlistShow = playlistShow + play + "\n";
				}
			}
			
		}else playlistShow = "Aucune piste.";
		embed.addField(":arrow_double_down: Musiques précédentes :", playlistShow, false);
		embed.addField(":loud_sound: Musique en cours :", Music.vocal.getCurrentTrack(), false);
		
		playlist = Music.vocal.getPlayList();
		playlistShow = "";
		if(playlist.length >= 1){
			int i = 0;
			for(String play : playlist){
				if(i == 5){
					playlistShow = playlistShow + (playlist.length - 5) + " autres pistes...";
				}else if(i < 5) playlistShow = playlistShow + play + "\n";
				i++;
			}
		}else playlistShow = "Aucune piste.";
		embed.addField(":arrow_double_down: Musiques à suivre :", playlistShow, false);

		Message msg = main.guild.getTextChannelById(Music.channelId).sendMessage(embed.build()).complete();

		msg.addReaction("⏯").queue();
		msg.addReaction("⏮").queue();
		msg.addReaction("⏭").queue();
		msg.addReaction("❌").queue();
		msg.addReaction("🚫").queue();
		msg.addReaction("🛑").queue(); // Tout arrêter
		msg.addReaction("\uD83C\uDFA4").queue(); // Micro
		
		return msg;
		
	}
	public void refreshVocalControler(){

		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.ORANGE);
		embed.setAuthor("Musique" + (Music.djMode ? " *Mode DJ activé*" : ""), "https://tntgun.fr/", "https://tntgun.fr/img/icon.png");
		embed.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");
		embed.setTimestamp(Instant.now());

		if(!Music.radio.isEmpty()){

			embed.addField(":loud_sound: Radio en cours :", Music.radio, false);

			main.guild.getTextChannelById(Music.channelId).editMessageById(Music.vocalControler.getIdLong(), embed.build()).queue();

		}

		String[] playlist = Music.vocal.getLastPlayList();
		String playlistShow = "";

		if(playlist.length >= 1){
			if(playlist.length > 5){
				playlistShow = playlistShow + (playlist.length - 5) + " autres pistes...";
				for(int i = playlist.length - 5; i < playlist.length; i++){
					playlistShow = playlistShow + "\n" + playlist[i];
				}
			}else{
				for(String play : playlist){
					playlistShow = playlistShow + play + "\n";
				}
			}

		}else playlistShow = "Aucune piste.";
		embed.addField(":arrow_double_down: Musiques précédentes :", playlistShow, false);
		embed.addField(":loud_sound: Musique en cours :", Music.vocal.getCurrentTrack(), false);

		playlist = Music.vocal.getPlayList();
		playlistShow = "";
		if(playlist.length >= 1){
			int i = 0;
			for(String play : playlist){
				if(i == 5){
					playlistShow = playlistShow + (playlist.length - 5) + " autres pistes...";
				}else if(i < 5) playlistShow = playlistShow + play + "\n";
				i++;
			}
		}else playlistShow = "Aucune piste.";
		embed.addField(":arrow_double_down: Musiques à suivre :", playlistShow, false);

		main.guild.getTextChannelById(Music.channelId).editMessageById(Music.vocalControler.getIdLong(), embed.build()).queue();
		
	}
	
	public void sendSimpleEmbed(String content, Color color, int time){

		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(color);
		embed.setDescription(content);
		sendEmbedTimeAndAutoComplete(embed, time);
		
	}public void sendEmbedTimeAndAutoComplete(EmbedBuilder embed, int time){
		
		embed.setAuthor("Musique");
		embed.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");
		embed.setTimestamp(Instant.now());
		
		Message msg = main.guild.getTextChannelById(Music.channelId).sendMessage(embed.build()).complete();
		new Thread(new Runnable(){
            public void run() {
            	try{ Thread.sleep(time * 1000); }catch(InterruptedException e1){ e1.printStackTrace(); }
            	msg.delete();
            }
        }).start();
		
	}

}

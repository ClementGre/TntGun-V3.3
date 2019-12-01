package fr.themsou.discord.vocal;

import java.awt.Color;
import fr.themsou.diffusion.api.messages;

public class VocalEvents {
	messages Cmessages = new messages();
	
	public void vocalErrorLoad(String error){
		
		sendSimpleEmbed("Impossible de charger la musique : " + error, Color.RED, 15);
		if(Music.vocal.getCurrentTrack() == null) playlistEnd();
		
	}public void vocalErrorOther(String error){
		
		sendSimpleEmbed("Une erreur est survenue : " + error, Color.RED, 15);
		playlistEnd();
		
	}
	
	public void playlistEnd(){
		if(Music.vocalControler != 0){
			new messages().deleteMessage(Music.channelId, Music.vocalControler);
			Music.vocalControler = 0;
			Music.radio = "";
		}
		
	}
	public void nextTrack(){
		Music.radio = "";
		refreshVocalControler();
	}
	public void vocalStartMusic(){
		if(Music.vocalControler == 0){
			Music.vocalControler = sendVocalControler();
		}else refreshVocalControler();
		
	}
	public void vocalAddMusic(){
		if(Music.vocalControler == 0){
			Music.vocalControler = sendVocalControler();
		}else refreshVocalControler();
	}
	
	public long sendVocalControler(){
		
		Cmessages.clearEmbed();
		Cmessages.setColor(Color.ORANGE);
		Cmessages.setAuthor("Musique" + (Music.djMode ? " *Mode DJ activé*" : ""), "https://tntgun.fr/", "https://tntgun.fr/img/icon.png");
		Cmessages.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");
		
		if(!Music.radio.isEmpty()){
			
			Cmessages.addfield(":loud_sound: Radio en cours :", Music.radio, false);
			long id = Cmessages.sendEmbed(Music.channelId);
			Cmessages.clearEmbed();
			
			Cmessages.addReact(Music.channelId, id, "⏯");
			Cmessages.addReact(Music.channelId, id, "🛑");
			
			return id;
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
		Cmessages.addfield(":arrow_double_down: Musiques précédentes :", playlistShow, false);
		Cmessages.addfield(":loud_sound: Musique en cours :", Music.vocal.getCurrentTrack(), false);
		
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
		Cmessages.addfield(":arrow_double_down: Musiques à suivre :", playlistShow, false);
		
		long id = Cmessages.sendEmbed(Music.channelId);
		Cmessages.clearEmbed();
		
		Cmessages.addReact(Music.channelId, id, "⏯");
		Cmessages.addReact(Music.channelId, id, "⏮");
		Cmessages.addReact(Music.channelId, id, "⏭");
		Cmessages.addReact(Music.channelId, id, "❌");
		Cmessages.addReact(Music.channelId, id, "🚫");
		Cmessages.addReact(Music.channelId, id, "🛑"); // Tout arrêter
		Cmessages.addReact(Music.channelId, id, "\uD83C\uDFA4"); // Micro
		
		return id;
		
	}
	public long refreshVocalControler(){
		
		Cmessages.clearEmbed();
		Cmessages.setColor(Color.ORANGE);
		Cmessages.setAuthor("Musique" + (Music.djMode ? " *Mode DJ activé*" : ""), "https://tntgun.fr/", "https://tntgun.fr/img/icon.png");
		Cmessages.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");
		
		if(!Music.radio.isEmpty()){
			
			Cmessages.addfield(":loud_sound: Radio en cours :", Music.radio, false);
			Cmessages.EditEmbed(Music.channelId, Music.vocalControler);
			Cmessages.clearEmbed();
			
			Cmessages.addReact(Music.channelId, Music.vocalControler, "⏯");
			Cmessages.addReact(Music.channelId, Music.vocalControler, "🛑");
			
			return Music.vocalControler;
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
		
		Cmessages.addfield(":arrow_double_down: Musiques précédentes :", playlistShow, false);
		Cmessages.addfield(":loud_sound: Musique en cours :", Music.vocal.getCurrentTrack(), false);
		
		playlist = Music.vocal.getPlayList();
		playlistShow = "";
		
		if(playlist.length >= 1){
			int i = 0;
			for(String play : playlist){
				if(i == 5){
					playlistShow = playlistShow + (playlist.length - 5) + " autres pistes.";
				}else if(i < 5) playlistShow = playlistShow + play + "\n";
				i++;
			}
		}else playlistShow = "Aucune piste.";
		Cmessages.addfield(":arrow_double_down: Musiques à suivre :", playlistShow, false);
		
		Cmessages.EditEmbed(Music.channelId, Music.vocalControler);
		Cmessages.clearEmbed();
		
		return Music.vocalControler;
		
	}
	
	public void sendSimpleEmbed(String content, Color color, int time){
		
		Cmessages.clearEmbed();
		Cmessages.setColor(color);
		Cmessages.setDescription(content);
		sendEmbedTimeAndAutoComplete(time);
		
	}public void sendEmbedTimeAndAutoComplete(int time){
		
		Cmessages.setAuthor("Musique" + (Music.djMode ? " *Mode DJ activé*" : ""), "https://tntgun.fr/", "https://tntgun.fr/img/icon.png");
		Cmessages.setFooter("Service musical de TntGun", "https://tntgun.fr/img/icon.png");
		
		long id = Cmessages.sendEmbed(Music.channelId);
		Cmessages.clearEmbed();
		
		new Thread(new Runnable(){
            public void run() {
            	try{ Thread.sleep(time * 1000); }catch(InterruptedException e1){ e1.printStackTrace(); }
            	Cmessages.deleteMessage(Music.channelId, id);
            }
        }).start();
		
	}

}

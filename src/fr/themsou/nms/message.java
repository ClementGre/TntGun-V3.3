package fr.themsou.nms;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class message implements Listener {

	public message() {
		
	}
	
	public static void sendNmsMessageCmd(Player player, String message, String hover, String commande){
		
		
		TextComponent m = new TextComponent(message);
		m.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create() ) );
		m.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, commande));
		player.spigot().sendMessage( m );
		
		
	}public static void sendNmsMessageLink(Player player, String message, String hover, String link){
		
		
		TextComponent m = new TextComponent(message);
		m.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create() ) );
		m.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, link));
		
		player.spigot().sendMessage( m );
		
		
	}public static void sendNmsMessageInfo(Player player, String message, String cmd){
		
		String newmsg = "";
		String URL = "";
		for(String i : message.split(" ")){
			newmsg = newmsg + "§6" + i + " ";
			if(i.contains("http")) URL = i;
		}
		TextComponent m = new TextComponent(newmsg);
		
		if(!URL.isEmpty()){
			
			m.setClickEvent(new ClickEvent(Action.OPEN_URL, URL));
			m.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cCliquez pour ouvrir le lien").create()));
		}
		
		TextComponent l = new TextComponent(" §c[§cLu§c]");
		l.setClickEvent(new ClickEvent(Action.RUN_COMMAND, cmd));
		l.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cCliquez pour ne plus recevoir ce message").create()));
		
		
		player.spigot().sendMessage(m);
		player.spigot().sendMessage(l);
		
		
	}public static void sendNmsMessage(Player player, String message, String hover, String nxtmsg, String color){
		
		
		
		String newmessage[] = nxtmsg.split(" ");
		String newmsg = "";
		String URL = "";
		for(String i : newmessage){
			newmsg = newmsg + color + i + " ";
			
			if(i.contains("http")) URL = i;
		}
		
		TextComponent m = new TextComponent(message + newmsg);
		
		
		if(!URL.isEmpty()){
			
			m.setClickEvent(new ClickEvent(Action.OPEN_URL, URL));
			
			if(!hover.isEmpty()) m.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover + "\n§cCliquez pour ouvrir le lien").create()));
			
			else m.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cCliquez pour ouvrir le lien").create()));
			
		}else if(!hover.isEmpty()){
			m.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
		}
			
		
		player.spigot().sendMessage(m);
		
		
	}

}

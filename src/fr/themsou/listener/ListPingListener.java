package fr.themsou.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import fr.themsou.main.main;

public class ListPingListener implements Listener {
	
	@EventHandler
	public void onListPing(ServerListPingEvent e){

		e.setMaxPlayers(15);
		
		String line1 = "                   §4§l\u226b\u226b §6TntGun V3 §4§l\u226a\u226a   §d[1.14.2]";
		String line2 = "      §c§l\u25b6 §2Serveur §aRolePlay §2et §aMini-Jeux §4§l\u25c0";
		
		if(main.ddos != 0){
			line2 = "§cPatientez encore §4" + main.ddos + "s §cavant de vous connecter.";
		}
		
		e.setMotd(line1 + "\n" + line2);
	}

}

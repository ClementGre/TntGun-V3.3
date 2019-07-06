package fr.themsou.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import fr.themsou.main.main;

public class ListPingListener implements Listener {
	
	@EventHandler
	public void onListPing(ServerListPingEvent e){

		e.setMaxPlayers(15);
		
		String line1 = "§cServeur en maintenance (passage en 1.14)";
		String line2 = "§3RDV sur discord pour plus d'infos";
		
		if(main.ddos != 0){
			line2 = "§cPatientez encore §4" + main.ddos + "s §cavant de vous connecter.";
		}
		
		e.setMotd(line1 + "\n" + line2);
	}

}

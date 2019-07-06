package fr.themsou.discord;

import org.bukkit.Bukkit;

import fr.themsou.diffusion.api.Channels;
import fr.themsou.diffusion.api.roles;
import fr.themsou.main.main;

public class Counter {

	public void refreshCounters(){
		refreshCounters(Bukkit.getOnlinePlayers().size());
	
	}public void refreshCountersM1(){
		refreshCounters(Bukkit.getOnlinePlayers().size() - 1);
	}
	
	public void refreshCounters(int playersAct){
		
		Channels CChannels = new Channels();
		roles CRole = new roles();
		
		int total = 0;
		int membres = 0; int membresAct = 0;
		int joueurs = 0; int joueursAct = 0;
		int players = 0;
		
		for(String user : new fr.themsou.diffusion.api.user().getUsers()){
			
			total++;
			if(CRole.getRoles(user).contains("Membre")){
				membres ++;
			}if(CRole.getRoles(user).contains("Membre actif")){
				membresAct ++;
			}if(CRole.getRoles(user).contains("Joueur")){
				joueurs ++;
			}if(CRole.getRoles(user).contains("Joueur actif")){
				joueursAct ++;
			}
			
		}
		
		for(String user : main.config.getConfigurationSection("").getKeys(false)){
			if(main.config.contains(user + ".mdp")){
				players++;
			}
		}
		
		CChannels.renameVocalChannel(591541556305985549L, "Membres actifs : " + membresAct + "/" + membres);
		CChannels.renameVocalChannel(591541769796059165L, "Joueurs actifs : " + joueursAct + "/" + joueurs);
		CChannels.renameVocalChannel(591859439867527171L, "En ligne : " + playersAct + "/" + players);
		CChannels.renameVocalChannel(591541955511451659L, "Total : " + total);
		
	}

}

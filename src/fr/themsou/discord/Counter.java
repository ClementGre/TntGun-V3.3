package fr.themsou.discord;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;
import fr.themsou.main.main;

public class Counter {

	public void refreshCounters(){
		refreshCounters(Bukkit.getOnlinePlayers().size(), false);
	
	}public void refreshCountersM1(){
		refreshCounters(Bukkit.getOnlinePlayers().size() - 1, false);
	}
	
	public void refreshCounters(int playersAct, boolean offline){

		int total = 0;
		int membres = 0; int membresAct = 0;
		int joueurs = 0; int joueursAct = 0;
		int players = 0;

		Role membreGrd = main.jda.getRolesByName("Membre", false).get(0);
		Role membreActifGrd = main.jda.getRolesByName("Membre actif", false).get(0);
		Role joueurGrd = main.jda.getRolesByName("Joueur", false).get(0);
		Role joueurActifGrd = main.jda.getRolesByName("Joueur actif", false).get(0);

		
		for(Member member : main.guild.getMembers()){
			
			total++;
			if(member.getRoles().contains(membreGrd)){
				membres ++;
			}if(member.getRoles().contains(membreActifGrd)){
				membresAct ++;
			}if(member.getRoles().contains(joueurGrd)){
				joueurs ++;
			}if(member.getRoles().contains(joueurActifGrd)){
				joueursAct ++;
			}
			
		}
		
		for(String user : main.config.getConfigurationSection("").getKeys(false)){
			if(main.config.contains(user + ".mdp")){
				players++;
			}
		}

		if(offline){
			main.guild.getCategoryById(591542107856961537L).getManager().setName("STATISTIQUES (❌ HORS LIGNE ❌ )").queue();
		}else{
			main.guild.getCategoryById(591542107856961537L).getManager().setName("STATISTIQUES (" + playersAct + " \uD83D\uDCBB - " + membres + " \uD83D\uDCF1)").queue();
		}


		main.guild.getVoiceChannelById(591541556305985549L).getManager().setName("Membres actifs : " + membresAct + "/" + membres).queue();
		main.guild.getVoiceChannelById(591541769796059165L).getManager().setName("Joueurs actifs : " + joueursAct + "/" + joueurs).queue();
		main.guild.getVoiceChannelById(591859439867527171L).getManager().setName("En ligne : " + playersAct + "/" + players).queue();
		main.guild.getVoiceChannelById(591541955511451659L).getManager().setName("Total : " + total).queue();
		
	}

	public void setOfflineCounters(){
		refreshCounters(0, true);
	}
}

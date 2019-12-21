package fr.themsou.discord.tools;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;

public class Tools {
	
	public static long getConectedVoiceChannel(Member member){
		return member.getVoiceState().inVoiceChannel() ? member.getVoiceState().getChannel().getIdLong() : 0;
	}

	public static ArrayList<String> getMemberStringRoles(Member member){

		ArrayList<String> roles = new ArrayList<>();
		for(Role role : member.getRoles()){
			roles.add(role.getName());
		}
		return  roles;
	}
}

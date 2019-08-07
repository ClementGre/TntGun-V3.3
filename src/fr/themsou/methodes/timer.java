package fr.themsou.methodes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.themsou.diffusion.api.roles;
import fr.themsou.discord.Counter;
import fr.themsou.discord.Roles;
import fr.themsou.main.main;
import fr.themsou.rp.claim.dynmap;
import fr.themsou.rp.ent.Utils;

public class timer {
	
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////6S ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	public void pear1S(main mainclass){
		
		if(main.ddos > 0) main.ddos --;
		
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// 6S ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public void pear6S(main mainclass){
		
		Scoreboards CScoreboards = new Scoreboards();
		Date date = new realDate().getRealDate();
		main.CSQLConnexion.updatePost();
		
/////////////////////////////////////////// DATABASE, UNMUTE, INFO
		if(main.timerMinuts == 0){
			
			main.timerMinuts = 60;
			
			Set<String> section = main.config.getConfigurationSection("").getKeys(false);	
			String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
			String[] item = items.split(",");
			for(int i = 1; i <= section.size(); i++){
				int number = i - 1;
				
				if(main.config.getInt(item[number] + ".punish.mute.minutes") > 0){
					main.config.set(item[number] + ".punish.mute.minutes", main.config.getInt(item[number] + ".punish.mute.minutes") - 1);
				}
				
				if(main.config.contains(item[number] + ".stat.last") && main.config.contains(item[number] + ".mdp")){
					if(main.config.getInt(item[number] + ".punish.ban.day") > 0){
						main.CSQLConnexion.refreshPlayer(item[number], 3);
					}else{
						if(Bukkit.getPlayerExact(item[number]) != null){
							main.CSQLConnexion.refreshPlayer(item[number], 1);
						}else{
							main.CSQLConnexion.refreshPlayer(item[number], 2);
						}
					}
				}
			}
			
			
				
		}
		main.timerMinuts --;
		
		
		for(Player players : Bukkit.getOnlinePlayers()){
			
			String game = PInfos.getGame(players);
			
			if(new Random().nextInt(3) == 1){
				String key = "nulle";
				if(game.equals("RP")){
					
					key = "rp";
					
				}else if(game.equals("TntWars")){
					
					key = "tw";
					
				}else if(game.equals("BedWars")){
					
					key = "bw";
					
				}
				info Cinfo = new info();
				Cinfo.sendPlayerInfo(players, key);
			}
			
/////////////////////////////////////////// NOTIFS
			
			String notifs = main.config.getString(players.getName() + ".notifs");
			
			if(notifs != null){
				
				String[] notif = notifs.split(",");
				players.sendMessage("§c§lNOTIFICATIONS :");
				
				for(String n : notif){
					
					players.sendMessage(n);
					
				}
				main.config.set(players.getName() + ".notifs", null);
			}
/////////////////////////////////////////// STATS
			
			main.config.set(players.getName() + ".stat.weektime", main.config.getDouble(players.getName() + ".stat.weektime") + 0.1);
			main.config.set(players.getName() + ".stat.daytime", main.config.getDouble(players.getName() + ".stat.daytime") + 0.1);
			main.config.set(players.getName() + ".time.connect", main.config.getDouble(players.getName() + ".time.connect") + 0.1);
			
			main.config.set("stat.list.time", main.config.getDouble("stat.list.time") + 0.1);
			main.config.set("stat.list.day.time", main.config.getDouble("stat.list.day.time") + 0.1);
			main.config.set("stat.list.week.time", main.config.getDouble("stat.list.week.time") + 0.1);
			main.config.set("stat.list.day.quart.total", main.config.getDouble("stat.list.day.quart.total") + 0.1);
			if(!game.equals("HUB")) main.config.set("stat.list.day.quart." + game.toLowerCase(), main.config.getDouble("stat.list.day.quart." + game.toLowerCase()) + 0.1);
			if(players.isOp()) main.config.set("stat.list.day.quart.staff", main.config.getDouble("stat.list.day.quart.staff") + 0.1);
			
			int weekday = date.getDay(); if(weekday == 0) weekday = 7;
			main.config.set("stat.list.week.daytime." + weekday, main.config.getDouble("stat.list.week.daytime." + weekday) + 0.1);
			
			main.config.set(players.getName() + ".time." + game.toLowerCase(), main.config.getDouble(players.getName() + ".time." + game.toLowerCase()) + 0.1);
				
/////////////////////////////////////////// POINTS
			
			Random r = new Random();
			if(r.nextInt(4) == 1){
				main.config.set(players.getName() + ".points", main.config.getInt(players.getName() + ".points") + 1);
			}

			if(!game.equals("BedWars")){
				CScoreboards.sendPlayersScoreboard();
			}
			
		
		}
		
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// QUART //////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

	public void pearQuart(main mainclass){
	
		mainclass.conf = main.config;
		mainclass.saveConfig();
		
		PInfos.putStats();
		
		fr.themsou.methodes.Inventory CInventory = new fr.themsou.methodes.Inventory();
		for(Player p : Bukkit.getOnlinePlayers()){
			if(PInfos.getGame(p).equals("RP") && p.getGameMode() == GameMode.SURVIVAL){
				CInventory.savePlayerInventory(p, "rp");
			}
		}
		
		new dynmap().refreshMarkerArea();
		
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// HOUR //////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	
	public void pearHour(main mainclass){
		
		new Counter().refreshCounters();
		
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// DAY ///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public void pearDay(main mainclass){
		
		new SendStat().sendDay();
		
		try{
			FileWriter writer = new FileWriter(main.logblock.getAbsoluteFile(), true);
			BufferedWriter out = new BufferedWriter(writer);
			out.write("--------------------------------------------------");
			out.newLine();
			out.write("----------------- NOUVEAU JOUR -------------------");
			out.newLine();
			out.write("--------------------------------------------------");
			out.newLine();
			out.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
		
		Set<String> section = main.config.getConfigurationSection("").getKeys(false);	
		String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
		String[] item = items.split(",");

		for(int i = 1; i <= section.size(); i++){
			int number = i - 1;
			
			if(main.config.getInt(item[number] + ".punish.ban.day") >= 1){
				
				main.config.set(item[number] + ".punish.ban.day", main.config.getInt(item[number] + ".punish.ban.day") - 1);
				
				if(main.config.getInt(item[number] + ".punish.ban.day") == 0){
					main.CSQLConnexion.refreshPlayer(item[number], 2);
				}
				
			}
			
			if(main.config.contains(item[number] + ".discord")){
				
				int currentDay = new realDate().getRealDate().getDate();
				String DiscordName = main.config.getString(item[number] + ".discord");
				
				int lastDay = main.config.getInt(item[number] + ".rp.lastday");
				if(currentDay < lastDay) currentDay = currentDay + 30;
				
				if(lastDay == 0 || currentDay - lastDay >= 7){
					roles Croles = new roles();
					if(Croles.getRoles(DiscordName).contains("RolePlay Player")) Croles.removeRole("RolePlay Player", DiscordName);
				}
				
				lastDay = main.config.getInt(item[number] + ".tntwars.lastday");
				if(currentDay < lastDay) currentDay = currentDay + 30;
				
				if(lastDay == 0 || currentDay - lastDay >= 7){
					roles Croles = new roles();
					if(Croles.getRoles(DiscordName).contains("TntWars Player")) Croles.removeRole("TntWars Player", DiscordName);
				}
				
				lastDay = main.config.getInt(item[number] + ".bedwars.lastday");
				if(currentDay < lastDay) currentDay = currentDay + 30;
				
				if(lastDay == 0 || currentDay - lastDay >= 7){
					roles Croles = new roles();
					if(Croles.getRoles(DiscordName).contains("BedWars Player")) Croles.removeRole("BedWars Player", DiscordName);
				}
				
				lastDay = main.config.getInt(item[number] + ".lastday");
				if(currentDay < lastDay) currentDay = currentDay + 30;
				
				if(lastDay == 0 || currentDay - lastDay >= 7){
					roles Croles = new roles();
					if(Croles.getRoles(DiscordName).contains("Joueur actif")) Croles.removeRole("Joueur actif", DiscordName);
				}
				
				
				
				
			}
			
			
			
		}
		
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// WEEK //////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	public void pearWeek(main mainclass){
		
		new SendStat().sendWeek();
		new Utils().payEntreprises();
		new Roles().onWeek();
		
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// MOUNTH ////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public void pearMounth(main mainclass){
		
		Set<String> section = main.config.getConfigurationSection("").getKeys(false);	
		String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
		String[] item = items.split(",");

		for(int i = 1; i <= section.size(); i++){
			int number = i - 1;
			
			if(main.config.contains(item[number] + ".claim.note")){
				
				int money = main.config.getInt(item[number] + ".claim.note");
				money = money * 1000;
				
				
				if(money != 0){
					System.out.println("Deposit player: +"+ money);
					main.economy.depositPlayer(item[number], money);
					main.config.set(item[number] + ".claim.note", null);
					
					String notifs = main.config.getString(item[number] + ".notifs");
					notifs = notifs + "," + "§6Vous avez gagné §c" + money + "€ §6pour vous récompenser de vos builds.";
					main.config.set(item[number] + ".notifs", notifs);
					
				}
			}
			
			
			
		}
		
	}
	

}

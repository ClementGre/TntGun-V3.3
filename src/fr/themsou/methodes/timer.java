package fr.themsou.methodes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import fr.themsou.BedWars.BedWars;
import fr.themsou.listener.CustomEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import fr.themsou.discord.Counter;
import fr.themsou.discord.Roles;
import fr.themsou.main.main;
import fr.themsou.rp.claim.dynmap;
import fr.themsou.rp.ent.Utils;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class timer {
	
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// 1S ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	public void timer1S(main mainclass){

		new CustomEvent().second();

		if(main.ddos > 0) main.ddos --;

		new Boss().tick();
		new BedWars().run(mainclass);
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// 6S ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public void timer6S(main mainclass){
		
		Scoreboards CScoreboards = new Scoreboards();
		Date date = new realDate().getRealDate();
		main.CSQLConnexion.updatePost();


/////////////////////////////////////////// DATABASE, UNMUTE, INFO
		if(main.timerMinuts == 0){
			
			main.timerMinuts = 60;

			BukkitRunnable timer = new BukkitRunnable(){

				int i = 0;
				Set<String> section = main.config.getConfigurationSection("").getKeys(false);
				String[] players = section.toArray(new String[section.size()]);

				@Override
				public void run() {

					String player = players[i];

					if(main.config.getInt(player + ".punish.mute.minutes") > 0){
						main.config.set(player + ".punish.mute.minutes", main.config.getInt(player + ".punish.mute.minutes") - 1);
					}

					if(main.config.contains(player + ".stat.last") && main.config.contains(player + ".mdp")){
						if(main.config.getInt(player + ".punish.ban.day") > 0){
							main.CSQLConnexion.refreshPlayer(player, 3);
						}else{
							if(Bukkit.getPlayerExact(player) != null){
								main.CSQLConnexion.refreshPlayer(player, 1);
							}else{
								main.CSQLConnexion.refreshPlayer(player, 2);
							}
						}
					}

					i++;
					if(i >= section.size()){
						cancel();
					}
				}
			};
			timer.runTaskTimer(mainclass, 10, 10);


		}
		main.timerMinuts --;
		
		
		for(Player players : Bukkit.getOnlinePlayers()){

/////////////////////////////////////////// ANTI AFK

			PlayerInfo playersInfo = main.playersInfos.get(players);
			if(playersInfo != null){
				playersInfo.addOneToLastViewVelChange();
				if(playersInfo.getLastViewVelChange() == 30){
					Bukkit.broadcastMessage("§5" + players.getName() + " semble AFK depuis 3 minutes");
				}
				if(playersInfo.getLastViewVelChange() >= 150){
					Bukkit.broadcastMessage("§5" + players.getName() + " a été expulsé du serveur pour AFK (15min)");
					players.kickPlayer("§cVous avez été expulsé du serveur pour une innactivitée supérieur à 15 min");
				}
			}

/////////////////////////////////////////// INFOS

			String game = PInfos.getGame(players);
			if(new Random().nextInt(3) == 1){

				String key = null;
				if(game.equals("RP")) key = "rp";
				else if(game.equals("TntWars")) key = "tw";
				else if(game.equals("BedWars")) key = "bw";

				new info().sendPlayerInfo(players, key);
			}
			
/////////////////////////////////////////// NOTIFS
			
			String notif = main.config.getString(players.getName() + ".notifs");
			
			if(notif != null){
				
				String[] notifs = notif.split(",");
				players.sendMessage("§c§lNOTIFICATIONS :");
				
				for(String n : notifs){
					if(!n.isEmpty()) players.sendMessage(n);
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

	public void timerQuart(main mainclass){
	
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
	
	public void timerHour(main mainclass){
		
		new Counter().refreshCounters();
		
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// DAY ///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	public void timerDay(main mainclass){
		
		new SendStat().sendDay();
		new Roles().onDay();
		
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
				
				new Roles().onPlayerDay(item[number]);
				
			}
			
			
			
		}
		
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// WEEK //////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	public void timerWeek(main mainclass){
		
		new SendStat().sendWeek();
		new Utils().payEntreprises();
		new Roles().onWeek();
		
	}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////// MOUNTH ////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public void timerMounth(main mainclass){
		
		for(String player : main.config.getConfigurationSection("").getKeys(false)){
			
			if(main.config.contains(player + ".claim.note")){
				
				int money = main.config.getInt(player + ".claim.note") * 1000;
				if(money != 0){
					main.economy.depositPlayer(player, money);
					main.config.set(player + ".claim.note", null);
					
					String notifs = main.config.getString(player + ".notifs") + "," + "§6Vous avez gagné §c" + money + "€ §6pour vous récompenser de vos builds.";
					main.config.set(player + ".notifs", notifs);
				}
			}
		}
		
	}
	

}

package fr.themsou.methodes;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import fr.themsou.main.main;
import fr.themsou.rp.games.DuelGame;

public class PInfos {
	
	public static double getComp(Player p, String compName){
		
		return main.config.getDouble(p.getName() + ".metier." + compName);
	}
	public static int getComps(Player p){
		
		return (main.config.getInt(p.getName() + ".metier.mineur") + main.config.getInt(p.getName() + ".metier.bucheron") + main.config.getInt(p.getName() + ".metier.fermier") + main.config.getInt(p.getName() + ".metier.enchanteur") + main.config.getInt(p.getName() + ".metier.chasseur")) / 5;
	}
	
	public static String getGame(Entity p){
		
		if(p.getWorld() == Bukkit.getWorld("BedWars") || p.getWorld() == Bukkit.getWorld("BedWars-ressources")) return "BedWars";
		else if(p.getWorld() == Bukkit.getWorld("World") || p.getWorld() == Bukkit.getWorld("World_nether") || p.getWorld() == Bukkit.getWorld("World_the_end")) return "RP";
		else if(p.getWorld() == Bukkit.getWorld("TntWars")) return "TntWars";
		else return "HUB";
	}
	
	public static String getPreciseGame(Entity e){
		
		if(e.getWorld() == Bukkit.getWorld("BedWars") || e.getWorld() == Bukkit.getWorld("BedWars-ressources")) return "BedWars";
		else if(e.getWorld() == Bukkit.getWorld("World") || e.getWorld() == Bukkit.getWorld("World_nether") || e.getWorld() == Bukkit.getWorld("World_the_end")){
			if(e instanceof Player){
				if(DuelGame.getStartedInstanceViaPlayer((Player) e) != null){
					return "Duel";
				}
			}
			
			if(e instanceof Arrow){
				if(((Arrow) e).getShooter() instanceof Player){
					if(DuelGame.getStartedInstanceViaPlayer((Player) ((Arrow) e).getShooter()) != null){
						return "Duel";
					}
				}
				
			}
			
			return "RP";
		}
		else if(e.getWorld() == Bukkit.getWorld("TntWars")) return "TntWars";
		else return "HUB";
	}

	public static Player getPlayerByEntity(Entity e){

		if(e instanceof Player){
			return (Player) e;

		}else if(e instanceof Arrow){
			if(((Arrow) e).getShooter() instanceof Player){
				return (Player) ((Arrow) e).getShooter();
			}
		}else if(e instanceof Trident){
			if(((Trident) e).getShooter() instanceof Player){
				return (Player) ((Trident) e).getShooter();
			}
		}
		return null;

	}
	
	
	public static String getTotalTime(String pName){
		
		int totalMinuts = main.config.getInt(pName + ".time.hub") + main.config.getInt(pName + ".time.tntwars") + main.config.getInt(pName + ".time.rp") + main.config.getInt(pName + ".time.bedwars");
		return totalMinuts/60 + "h" + totalMinuts%60;
		
	}public static int getTotalTimeMin(String pName){
		
		int totalMinuts = main.config.getInt(pName + ".time.hub") + main.config.getInt(pName + ".time.tntwars") + main.config.getInt(pName + ".time.rp") + main.config.getInt(pName + ".time.bedwars");
		return totalMinuts;
		
	}
	public static String getTime(String pName, String game){
		
		int totalMinuts = main.config.getInt(pName + ".time." + game.toLowerCase());
		return totalMinuts/60 + "h" + totalMinuts%60;
		
	}public static int getTimeMin(String pName, String game){
		
		int totalMinuts = main.config.getInt(pName + ".time." + game.toLowerCase());
		return totalMinuts;
		
	}
	
	public static String getConnectTime(Player p){
		
		int connectTime = main.config.getInt(p.getName() + ".time.connect");
		return connectTime/60 + "h" + connectTime%60;
		
	}public static int getConnectTimeMin(Player p){
		
		int connectTime = main.config.getInt(p.getName() + ".time.connect");
		return connectTime;
		
	}
	
	public static String getOfflineTime(String pName){
		
		int lastTotal = new realDate().getMinutesSpace(main.config.getString(pName + ".stat.last"));
		
		int lastDay = lastTotal/ 60 / 24;
		int lastHours = lastTotal / 60 % 24;
		int lastMinuts = lastTotal % 60;
		
		return lastDay + "J " + lastHours + "h" + lastMinuts;
		
	}public static int getOfflineTimeDays(String pName){
		
		int lastTotal = new realDate().getMinutesSpace(main.config.getString(pName + ".stat.last"));
		int lastDay = lastTotal/ 60 / 24;
		
		return lastDay;
		
	}public static int getOfflineTimeHours(String pName){
		
		int lastTotal = new realDate().getMinutesSpace(main.config.getString(pName + ".stat.last"));
		int lastHours = lastTotal / 60 % 24;
		
		return lastHours;
		
	}
	
	public static String getDiscordLinked(String pName){
		
		String discord = "§4pas lié";
		if(main.config.contains(pName + ".discord")) discord = main.config.getString(pName + ".discord");
		
		return discord;
		
	}
	
	
	public static String getIP(Player p){
		
		return main.config.getString(p.getName() + ".ip");
		
	}public static String getIP(String pName){
		
		return main.config.getString(pName + ".ip");
	}
	
	@SuppressWarnings("deprecation")
	public static String getDatePrint(){
		
		Date date = new realDate().getRealDate();
		String minuts = date.getMinutes() + "";
		if(date.getMinutes() <= 9) minuts = date.getMinutes() + "0";
		return date.getHours() + ":" + minuts + " - " + date.getDate() + "/" + (date.getMonth() + 1);
	}
	
	public static String getDoublesComptesPrint(String pName){
		String dc = "§4aucun";
		String ip = getIP(pName);
		
		int i = 0;
		for(String dcs : main.config.getConfigurationSection("").getKeys(false)){
			if(main.config.contains(dcs + ".ip")){
				
				if(main.config.getString(dcs + ".ip").equals(ip) && !dcs.equals(pName)){
					if(i == 0) dc = dcs;
					else dc += ", " + dcs;
					i++;
				}
			}
		}
		
		return dc;
	}
	
	public static void putStat(int minuts, double value, String type){
		main.config.set("stat.list.day.graph." + type.toLowerCase() + "." + minuts, value);
	}
	
	@SuppressWarnings("deprecation")
	public static int getQuartMinuts(){
		
		Date date = new realDate().getRealDate();
		
		int minuts =  date.getMinutes();
		if(minuts < 8) minuts = 0;
		else if(minuts < 23) minuts = 15;
		else if(minuts < 38) minuts = 30;
		else if(minuts < 53) minuts = 45;
		else minuts = 0;
			
		return date.getHours() * 60 + minuts;
	}
	public static void putStats(){
		putStats(getQuartMinuts());
	}
	public static void putStats(int minuts) {
		
		double total = main.config.getDouble("stat.list.day.quart.total") / 15.0;
		double staff = main.config.getDouble("stat.list.day.quart.staff") / 15.0;
		double roleplay = main.config.getDouble("stat.list.day.quart.rp") / 15.0;
		double bedwars = main.config.getDouble("stat.list.day.quart.bedwars") / 15.0;
		double tntwars = main.config.getDouble("stat.list.day.quart.tntwars") / 15.0;
		
		PInfos.putStat(minuts, total, "total");
		PInfos.putStat(minuts, staff, "staff");
		PInfos.putStat(minuts, roleplay, "roleplay");
		PInfos.putStat(minuts, bedwars, "bedwars");
		PInfos.putStat(minuts, tntwars, "tntwars");
		
		main.config.set("stat.list.day.quart", null);
		
	}
	public static void putStatsNone() {
		
		int minuts = getQuartMinuts();
		double total = main.config.getDouble("stat.list.day.quart.total") / 15.0;
		double staff = main.config.getDouble("stat.list.day.quart.staff") / 15.0;
		double roleplay = main.config.getDouble("stat.list.day.quart.rp") / 15.0;
		double bedwars = main.config.getDouble("stat.list.day.quart.bedwars") / 15.0;
		double tntwars = main.config.getDouble("stat.list.day.quart.tntwars") / 15.0;
		
		PInfos.putStat(minuts, total, "total");
		PInfos.putStat(minuts, staff, "staff");
		PInfos.putStat(minuts, roleplay, "roleplay");
		PInfos.putStat(minuts, bedwars, "bedwars");
		PInfos.putStat(minuts, tntwars, "tntwars");
		
	}
	public static void putStatsValue(int value){
		putStats(getQuartMinuts(), value);
	}
	public static void putStats(int minuts, int value) {
		
		PInfos.putStat(minuts, value, "total");
		PInfos.putStat(minuts, value, "staff");
		PInfos.putStat(minuts, value, "roleplay");
		PInfos.putStat(minuts, value, "bedwars");
		PInfos.putStat(minuts, value, "tntwars");
		
	}
	
	public static int getOnlineOperators(){
		int i = 0;
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.isOp()) i++;
		}
		return i;
	}
	public static int getOnlineGame(String game){
		int i = 0;
		for(Player p : Bukkit.getOnlinePlayers()){
			if(getGame(p).equalsIgnoreCase(game)) i++;
		}
		return i;
	}
	

}

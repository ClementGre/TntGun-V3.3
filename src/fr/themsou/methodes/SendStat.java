package fr.themsou.methodes;

import java.awt.Color;
import java.util.Date;
import fr.themsou.main.main;

public class SendStat {
	
	@SuppressWarnings("deprecation")
	public void sendDay(){
		
		Date Date = new realDate().getRealDate();
		statLine Cstats = new statLine(1440, "h", true, false, "J", false, 24, 15);
		math Cmath = new math();
		
		String PlayersStats = "";
		for(String Pname : main.config.getString("stat.list.day.players").split(",")){
			PlayersStats = PlayersStats + Pname + ": ." + Cmath.getTimestampWithMinutes(main.config.getInt(Pname + ".stat.daytime"), "H") + "\n";
			main.config.set(Pname + ".stat.daytime", 0);
		}
		PlayersStats = PlayersStats + "\nTOTAL: ." + Cmath.getTimestampWithMinutes(main.config.getInt("stat.list.day.time"), "H") + "\n";
		
		int size = main.configuration.getConfigurationSection("stat").getKeys(false).size();
		String[] names = new String[size];
		Color[] colors = new Color[size];
		
		////
		int i = 0;
		for(String statName : main.configuration.getConfigurationSection("stat").getKeys(false)){
			
			names[i] = statName;
			Color color = new Color(main.configuration.getInt("stat." + statName + ".color.r"), main.configuration.getInt("stat." + statName + ".color.g"), main.configuration.getInt("stat." + statName + ".color.b"));
			colors[i] = color;
			
			int stroke = main.configuration.getInt("stat." + statName + ".stroke");
			int shift = main.configuration.getInt("stat." + statName + ".shift");
			boolean m1 = main.configuration.getBoolean("stat." + statName + ".m1");
			
			statName = statName.toLowerCase();
			if(main.config.contains("stat.list.day.graph." + statName.toLowerCase())){

				Cstats.newLine(color, stroke, shift, m1);
				
				for(String strMin : main.config.getConfigurationSection("stat.list.day.graph." + statName).getKeys(false)){
					Cstats.addvalue(Integer.parseInt(strMin), main.config.getDouble("stat.list.day.graph." + statName + "." + strMin));	
				}
				
			}
			i++;
			main.config.set("stat.list.day.graph." + statName, null);
		}
		Cstats.newLine(Color.WHITE, 2, 0, true);
		////
		
		Cstats.setLegend(names, colors);
		
		Cstats.save("DayStat-" + Date.getDate() + "-" + Date.getMonth());
		Cstats.send("DayStat-" + Date.getDate() + "-" + Date.getMonth(), 487911374274428929L, "**```css\n[Nombre de joueurs qui ont rejoins le serveur pour chaque heure d'Hier]\n\n" + PlayersStats + "```**");
		
		
		main.config.set("stat.list.day.time", null);
		main.config.set("stat.list.day.players", "");
	}
	
	@SuppressWarnings("deprecation")
	public void sendWeek(){
		
		Date Date = new realDate().getRealDate();
		stats Cstats = new stats(7, "", false, false, 30, "H", true);
		math Cmath = new math();
		
		
		String PlayersStats = "";
		for(String Pname : main.config.getString("stat.list.week.players").split(",")){
			PlayersStats = PlayersStats + Pname + ": ." + Cmath.getTimestampWithMinutes(main.config.getInt(Pname + ".stat.weektime"), "H") + "\n";
			main.config.set(Pname + ".stat.weektime", 0);
		}
		PlayersStats = PlayersStats + "\nTOTAL: ." + Cmath.getTimestampWithMinutes(main.config.getInt("stat.list.week.time"), "H") + "\n";
		
		for(int a = 1; a <= 7; a++){
			Cstats.addvalue((int) (main.config.getInt("stat.list.week.daytime." + a) / 60));
			
		}
		Cstats.save("WeekStat-" + Date.getDate() + "-" + Date.getMonth());
		Cstats.send("WeekStat-" + Date.getDate() + "-" + Date.getMonth(), 487911374274428929L, "**```css\n[Temps total passé sur le serveur chaque jour de la semaine précédente]\n\n" + PlayersStats + "```**");
		
		
		
		main.config.set("stat.list.week.time", null);
		main.config.set("stat.list.week.players", "");
		main.config.set("stat.list.week.daytime", null);
	}

	@SuppressWarnings("deprecation")
	public void sendDayNoClear(){
		
		Date Date = new realDate().getRealDate();
		statLine Cstats = new statLine(1440, "h", true, false, "J", false, 24, 15);
		math Cmath = new math();
		
		String PlayersStats = "";
		for(String Pname : main.config.getString("stat.list.day.players").split(",")){
			PlayersStats = PlayersStats + Pname + ": ." + Cmath.getTimestampWithMinutes(main.config.getInt(Pname + ".stat.daytime"), "H") + "\n";
		}
		PlayersStats = PlayersStats + "\nTOTAL: ." + Cmath.getTimestampWithMinutes(main.config.getInt("stat.list.day.time"), "H") + "\n";
		
		int size = main.configuration.getConfigurationSection("stat").getKeys(false).size();
		String[] names = new String[size];
		Color[] colors = new Color[size];
		
		////
		int i = 0;
		for(String statName : main.configuration.getConfigurationSection("stat").getKeys(false)){
			
			names[i] = statName;
			Color color = new Color(main.configuration.getInt("stat." + statName + ".color.r"), main.configuration.getInt("stat." + statName + ".color.g"), main.configuration.getInt("stat." + statName + ".color.b"));
			colors[i] = color;
			
			int stroke = main.configuration.getInt("stat." + statName + ".stroke");
			int shift = main.configuration.getInt("stat." + statName + ".shift");
			boolean m1 = main.configuration.getBoolean("stat." + statName + ".m1");
			
			statName = statName.toLowerCase();
			if(main.config.contains("stat.list.day.graph." + statName.toLowerCase())){
				Cstats.newLine(color, stroke, shift, m1);
				
				for(String strMin : main.config.getConfigurationSection("stat.list.day.graph." + statName).getKeys(false)){
					Cstats.addvalue(Integer.parseInt(strMin), main.config.getDouble("stat.list.day.graph." + statName + "." + strMin));	
				}
				
			}
			i++;
		}
		Cstats.newLine(Color.WHITE, 2, 0, true);
		////
		
		Cstats.setLegend(names, colors);
		
		Cstats.save("DayStat-" + Date.getDate() + "-" + Date.getMonth());
		Cstats.send("DayStat-" + Date.getDate() + "-" + Date.getMonth(), 487911374274428929L, "**```css\n[Nombre de joueurs qui ont rejoins le serveur pour chaque heure d'Hier]\n\n" + PlayersStats + "```**");
		
		
	}
	
	
}

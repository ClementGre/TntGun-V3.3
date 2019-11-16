package fr.themsou.listener;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import fr.themsou.methodes.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.themsou.BedWars.getteam;
import fr.themsou.commands.GradeCmd;
import fr.themsou.diffusion.api.messages;
import fr.themsou.main.main;
import fr.themsou.methodes.PInfos;

public class ChatListener implements Listener{
	
	@SuppressWarnings("unused")
	private main pl;
	
	public ChatListener(main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){

		e.setCancelled(true);
		Player p = e.getPlayer();
		PlayerInfo pInfo = main.playersInfos.get(p);
		String message = "";

		if(!pInfo.isLoggin()) return;
		if(e.getMessage().contains("I joined using ChatCraft")) e.setMessage("J'ai rejoins depuis mon téléphone");
		if(main.config.getDouble(p.getName() + ".punish.mute.minutes") > 0){
			p.sendMessage("§cVous etes mute, il vous reste §6" + main.config.getInt(p.getName() + ".punish.mute.minutes") + "§c Minutes de mute !"); return;
		}

		pInfo.setLastViewVelChange(0);

		if(new GradeCmd().getPlayerPermition(p.getName()) <= 2){
			if(e.getMessage().equalsIgnoreCase(pInfo.getLastMsgText())
					|| System.currentTimeMillis()-pInfo.getLastMsgTime() < 2000){

				return;
			}
			if(e.getMessage().chars().filter((s)->Character.isUpperCase(s)).count() > e.getMessage().length()/2 && e.getMessage().chars().filter((s)->Character.isUpperCase(s)).count() >= 5){
				e.setMessage(e.getMessage().toLowerCase());
			}
		}
		pInfo.setLastMsgTime(System.currentTimeMillis());
		pInfo.setLastMsgText(e.getMessage());

		for(String word : e.getMessage().split(" ")){
			
			if(word.equals("ez")){
				message += "gg ";
			}else if(word.endsWith("=")){
				word = word.substring(0, word.length()-1);
				if(isCalcul(word)){
					try{
						ScriptEngineManager mgr = new ScriptEngineManager();
						ScriptEngine engine = mgr.getEngineByName("JavaScript");
						String myJSCode = word;
						String result = "§o=" + engine.eval(myJSCode);
						result = result.replaceFirst("\\.", ",");
						if(result.split(",").length == 2){
							if(result.split(",")[1].length() >= 4){
								result = result.split(",")[0].replace("=", "≈") + "," + result.split(",")[1].substring(0, 3);
							}
						}
						message += word + result;

					}catch(ScriptException | NumberFormatException e1){
						message += word + " ";
					}
				}else message += word + "= ";
			}else message += word + " ";

			
			
		}
		// VARIABLES
		
		GradeCmd CGrade = new GradeCmd();
		String pName = p.getName();
		String game = PInfos.getGame(p);
		String playerGrade = main.config.getString(pName + ".grade");
		String staffOverlay = "";
		String entIcon = "";
		String date = PInfos.getDatePrint();
		
		// DISCORD
		
		if(!p.getDisplayName().contains(pName)){
			staffOverlay = "§cPseudo Original : §4" + p.getName();
			playerGrade = "Joueur";
			pName = p.getDisplayName();
			SendDiscordMsg(message, pName.replaceFirst("§r", ""));
			
		}else SendDiscordMsg(message, pName);
		
		// GRADE
		
		String gradeColor = main.configuration.getString("grades." + playerGrade + ".gradecolor");
		String playerColor = main.configuration.getString("grades." + playerGrade + ".playercolor");
		String chatColor = main.configuration.getString("grades." + playerGrade + ".chatcolor");
		String overlay = "§6Connecté depuis : §3" + PInfos.getConnectTime(p)
				+ "\n§6Date d'envoi : §3" + date
				+ "\n§6Temps / Total : §3" + PInfos.getTimeMin(p.getName(), game) / 60 + "h/" + PInfos.getTotalTime(p.getName()) + "h";
		
		if(p.getGameMode() == GameMode.SURVIVAL && !chatColor.equals("§7")){
			chatColor = "§f";
			playerColor = "§7";
		}
		
		// OVERLAY
		
		if(game.equals("RP")){
			overlay += "\n§6Compétences : §3" + PInfos.getComps(p) + "%";
			
			if(main.config.contains(p.getName() + ".rp.ent.name")){
				
				overlay += "\n§6Entreprise : §3" + main.config.getString(p.getName() + ".rp.ent.name");
				int grade = main.config.getInt(p.getName() + ".rp.ent.role");
				if(grade == 0) entIcon = "§e§l\u24c2 "; else if(grade == 1) entIcon = "§6§l\u24c2 "; else entIcon = "§c§l\u24c2 ";
				
			}else{
				overlay += "\n§6Entreprise : §cAucune";
				entIcon = "§7§l\u24c2 ";
			}
			
		}else if(game.equals("BedWars")){
			
			getteam Cgetteam = new getteam();
			int team = Cgetteam.getplayerteam(p);
			if(team != 0){
				gradeColor = Cgetteam.getTeamChatColor(team) + "";
				playerGrade = Cgetteam.getTeamStringColor(team);
			}
		}
		
		// ENVOIS
		
		for(Player players : Bukkit.getOnlinePlayers()){
			if(CGrade.getPlayerPermition(players.getName()) >= 3){
				
				if(!staffOverlay.isEmpty()) staffOverlay += "\n";
				fr.themsou.nms.message.sendNmsMessage(players, "[" + game + "] §r" + entIcon + "§r" + gradeColor + "[" + playerGrade + "] §r" + playerColor + pName + " : §r", staffOverlay + overlay, message, chatColor);
				
			}else{
				fr.themsou.nms.message.sendNmsMessage(players, "[" + game + "] §r" + entIcon + "§r" + gradeColor + "[" + playerGrade + "] §r" + playerColor + pName + " : §r", overlay, message, chatColor);
			}
		}
		
		System.out.println("[" + game + "] [" + playerGrade + "] " + p.getName() + " : " + message);
		
	}
	
	
	public void SendDiscordMsg(String message, String PlayerName) {
		
		messages Cmessages = new messages();
		Cmessages.clearEmbed();
		Cmessages.setColor(Color.CYAN);
		Cmessages.setAuthor(PlayerName, "https://minotar.net/avatar/"+PlayerName+"/32.png", "https://minotar.net/avatar/"+PlayerName+"/32.png");
		Cmessages.setTitle(message);
		Cmessages.setFooter("Depuis Minecraft", "https://vignette.wikia.nocookie.net/minecraftproject/images/3/31/311.png/revision/latest?cb=20120726083051");

		Cmessages.sendEmbed(452810136319295498L);
		Cmessages.clearEmbed();
		
	}
	
	private static boolean isCalcul(String text){
		
		if(text.contains("+") || text.contains("-") || text.contains("*") || text.contains("/")){
			if(text.contains("0") || text.contains("1") || text.contains("2") || text.contains("3") || text.contains("4") || text.contains("5") || text.contains("6") || text.contains("7") || text.contains("8") || text.contains("9")){
				
				text = text.replace("+", "0").replace("-", "0").replace("*", "0").replace("/", "0");

				Pattern p = Pattern.compile("[0-9]*");
				Matcher m = p.matcher(text);
				
				return m.matches();
				
			}
		}
		return false;
		
	}

}

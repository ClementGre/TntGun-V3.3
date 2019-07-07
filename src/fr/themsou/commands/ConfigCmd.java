package fr.themsou.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.themsou.main.main;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.message;
import fr.themsou.rp.claim.Spawns;

public class ConfigCmd implements TabCompleter, CommandExecutor{
	
	
	private void set(Player p, String section, String toSet){
		
		try{
			main.config.set(section, Integer.parseInt(toSet));
			toSet += " §b(int)";
		}catch (NumberFormatException e){
			try{
				main.config.set(section, Double.parseDouble(toSet));
				toSet += " §b(double)";
			}catch (NumberFormatException e1){
				
				if(toSet.equalsIgnoreCase("null")){
					main.config.set(section, null);
					toSet = "§cnull";
				}else{
					main.config.set(section, toSet);
				}
			}
			
		}
		
		p.sendMessage("§bLa valeur de §3" + section + "§b a été mise à §3" + toSet);
		
	}
	private void get(Player p, String section, boolean simple){
		
		if(main.config.isConfigurationSection(section)){
			
			String result = "§3" + section + "§b est une ConfigurationSection, voici ses valeurs :§3 ";
			
			if(simple){
				for(String arg : main.config.getConfigurationSection(section).getKeys(false)){
					result += arg + "§b, §3";
				}
				p.sendMessage(result);
				
			}else{
				p.sendMessage(result);
				
				tree(p, section, 0);
			}
			
			
			
			
		}else{
			if(main.config.contains(section)){
				p.sendMessage("§bLa valeur de §3" + section + "§b est à §3" + main.config.getString(section));
			}else p.sendMessage("§cLa valeur recherchée n'existe pas.");
		}
		
	}
	
	private void tree(Player p, String section, int level){
		
		
		String prefix = "";
		while(level != 0){
			prefix += "  ";
			level --;
		}
		
		for(String arg : main.config.getConfigurationSection(section).getKeys(false)){
			
			String sec = section + "." + arg;
			
			if(main.config.isConfigurationSection(sec)){
				
				p.sendMessage("§3" + prefix + arg + "§b:");
				tree(p, sec, prefix.length() / 2 + 1);
				
			}else{
				p.sendMessage("§3" + prefix + arg + "§b: §3" + main.config.getString(sec));
			}
			
		}
	}
	
	public void clearConfig(Player p){
		
		Set<String> section = main.config.getConfigurationSection("").getKeys(false);	
		String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
		String[] item = items.split(",");
	
		for(int id = 1; id <= section.size(); id++){
			int number = id - 1;
			
			if(!main.config.contains(item[number] + ".list")){
				
				
				if(!main.config.contains(item[number] + ".mdp")){
					main.config.set(item[number], null);
					Bukkit.broadcastMessage("§cSupression du compte : §6" + item[number] + "§e (non enregistré)");
					
				}else{
					
					double time = main.config.getInt(item[number] + ".time.rp") + main.config.getInt(item[number] + ".time.tntwars") + main.config.getInt(item[number] + ".time.bedwars");
					double space = new realDate().getMinutesSpace(main.config.getString(item[number] + ".stat.last"));
					if(time == 0) time = 1;
					
					if((space / 60.0 / 24.0) / (time / 60.0) > 10){
				
					
						Bukkit.broadcastMessage("§cSupression du compte : §6" + item[number] + "§e (innactivitée)");
						
						if(main.config.contains(item[number] + ".claim")){
							for(String claimId : main.config.getConfigurationSection(item[number] + ".claim").getKeys(false)){
								
								String spawn = new Spawns().getSpawnNameWithId(Integer.parseInt(claimId));
								
								int minx = main.config.getInt("claim.list." + spawn + "." + claimId + ".x1");
								int minz = main.config.getInt("claim.list." + spawn + "." + claimId + ".z1");
								int maxx = main.config.getInt("claim.list." + spawn + "." + claimId + ".x2");
								int maxz = main.config.getInt("claim.list." + spawn + "." + claimId + ".z2");
								int x = minx + (maxx - minx) / 2;
								int z = minz + (maxz - minz) / 2;
								int y = main.config.getInt("claim.list." + spawn + "." + claimId + ".y1");
								
								message.sendNmsMessageCmd(p, "§4" + item[number] + " §c avait un claim, vous devez le supprimer", "§3Cliquez pour vous téléporter", "/tp " + x + " " + y + " " + z);
							}
						}
						
						main.config.set(item[number], null);
					}
				}
				
			}
		}
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg1, String[] argss) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(new GradeCmd().getPlayerPermition(p.getName()) < 6){
				p.sendMessage("§Cette commande n'existe pas ou ne vous est pas autorisée !");
				return false;
			}
			
			
			if(argss.length == 0){
				p.sendMessage("§c/config [-MFirstSet/-MFirstGet] < chemin > <valeur>");
				p.sendMessage("§c/config [-simple] <chemin> < valeur >");
				p.sendMessage("§c/config clear");
				return false;
			}
			
			if(argss.length == 1 && argss[0].equalsIgnoreCase("clear")){
				clearConfig(p);
				return true;
			}
			
			String param = "-";
			String[] args = new String[argss.length - 1];
			
			if(argss[0].startsWith("-")){
				param = argss[0];
				
				int i = -1;
				for(String arg : argss){
					if(i != -1) args[i] = arg;
					i++;
				}
			}else args = argss;
			
			if(args.length == 0){
				p.sendMessage("§c/config [-MFirstSet/-MFirstGet] < chemin > [valeur]");
				p.sendMessage("§c/config [-simple] <chemin> [ valeur ]");
				return false;
			}
			
			
			if(param.equalsIgnoreCase("-MFirstSet")){
				
				if(args.length >= 2){
					
					String section = "";
					String toSet = "";
					int x = args.length;
					for(String arg : args){
						if(x == args.length) section += arg;
						else if(x != 1) section += " " + arg;
						else toSet = arg;
						x--;
					}
					
					set(p, section, toSet);
					
				}else p.sendMessage("§c/config -MFirstSet < chemin > <valeur>");
				
				
				
			}else if(param.equalsIgnoreCase("-MFirstGet")){
				
				String section = "";
				int x = args.length;
				for(String arg : args){
					if(x == args.length) section += arg;
					else section += " " + arg;
					x--;
				}
				
				get(p, section, false);
				
			}else if(param.equalsIgnoreCase("-simple")){
				
				String section = args[0];
				
				if(args.length >= 2){
					
					String toSet = "";
					int x = -1;
					for(String arg : args){
						if(x == 0) toSet += arg;
						else if(x >= 1) toSet += " " + arg;
						x++;
					}
					
					set(p, section, toSet);
					
				}else{
					
					get(p, section, true);
					
				}
				
			}else if(param.equalsIgnoreCase("-")){
				
				String section = args[0];
				
				if(args.length >= 2){
					
					String toSet = "";
					int x = -1;
					for(String arg : args){
						if(x == 0) toSet += arg;
						else if(x >= 1) toSet += " " + arg;
						x++;
					}
					
					set(p, section, toSet);
					
				}else{
					
					get(p, section, false);
					
				}
				
			}else{
				p.sendMessage("§c/config [-MFirstSet/-MFirstGet] < chemin > <valeur>");
				p.sendMessage("§c/config [-simple] <chemin> < valeur >");
				p.sendMessage("§c/config clear");
			}
			return true;
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String ar, String[] args){
		
		if(args.length == 1){
			
			if(args[0].isEmpty()){
				return Arrays.asList("-Simple","-MFirstSet","-MFirstGet","clear");
			}else{
				
				String search = "";
				String[] searchTab = args[0].replace(".", "a-a").split("a-a");
				
				if(searchTab.length >= 2 || args[0].endsWith(".")){
					if(!args[0].endsWith(".")){
						searchTab[searchTab.length - 1] = "";
						for(String searchPart : searchTab){
							if(!searchPart.isEmpty()) search += searchPart + ".";
						}
					}else{
						search = args[0];
					}
				}
				if(main.config.contains(search)){
					ArrayList<String> values = new ArrayList<>();
					for(String value : main.config.getConfigurationSection(search).getKeys(false)){
						String section = search + value;
						if(section.startsWith(args[0])){
							values.add(section);
						}
					}
					return values;
				}
				
			}
				
		}
		if(args.length == 2 && args[0].equalsIgnoreCase("-simple")){
			
			String search = "";
			String[] searchTab = args[1].replace(".", "a-a").split("a-a");
			
			if(searchTab.length >= 2 || args[1].endsWith(".")){
				if(!args[1].endsWith(".")){
					searchTab[searchTab.length - 1] = "";
					for(String searchPart : searchTab){
						if(!searchPart.isEmpty()) search += searchPart + ".";
					}
				}else{
					search = args[1];
				}
			}
			if(main.config.contains(search)){
				ArrayList<String> values = new ArrayList<>();
				for(String value : main.config.getConfigurationSection(search).getKeys(false)){
					String section = search + value;
					if(section.startsWith(args[1])){
						values.add(section);
					}
				}
				return values;
			}
			
			
		}
		if(args.length >= 2){
			
			 if(args[0].equalsIgnoreCase("-MFirstSet")){
				 return Arrays.asList("<valeur>");
				 
			 }if(args[0].equalsIgnoreCase("-simple") && args.length == 3){
				 return Arrays.asList("<valeur>");
				 
			 }else if(args[0].equalsIgnoreCase("-MFirstGet")){
				 
				String arg0 = ""; int x = 0;
				for(String arg : args){
					if(x == 0) arg0 += arg;
					else if(x >= 1) arg0 += " " + arg;
					x++;
				}
				
				String search = "";
				String[] searchTab = arg0.replace(".", "a-a").split("a-a");
				
				if(searchTab.length >= 2 || arg0.endsWith(".")){
					if(!arg0.endsWith(".")){
						searchTab[searchTab.length - 1] = "";
						for(String searchPart : searchTab){
							if(!searchPart.isEmpty()) search += searchPart + ".";
						}
					}else{
						search = arg0;
					}
				}
				
				
				if(main.config.contains(search)){
					ArrayList<String> values = new ArrayList<>();
					for(String value : main.config.getConfigurationSection(search).getKeys(false)){
						String section = search + value;
						if(section.startsWith(arg0)){
							values.add(section);
						}
					}
					return values;
				}
				
				 
			}else if(args.length == 2){
				return Arrays.asList("<valeur>");
			}
		}
		
		return Arrays.asList("");
		
	}

}

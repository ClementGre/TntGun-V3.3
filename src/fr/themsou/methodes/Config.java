package fr.themsou.methodes;

import org.bukkit.entity.Player;

import fr.themsou.main.main;

public class Config {
	
	public void config(String[] argss, Player p){
		
		if(new Grade().getPlayerPermition(p.getName()) < 6){
			p.sendMessage("§Cette commande n'existe pas ou ne vous est pas autorisée !");
			return;
		}
		
		
		if(argss.length == 0){
			p.sendMessage("§c/config [-MFirstSet/-MFirstGet] < chemin > <valeur>");
			p.sendMessage("§c/config [-simple] <chemin> < valeur >");
			return;
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
			return;
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
		}
		
		
	}
	
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
	

}

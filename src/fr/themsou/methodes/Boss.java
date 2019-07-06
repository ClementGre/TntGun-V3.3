package fr.themsou.methodes;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import fr.themsou.main.main;

public class Boss {

	public void createBar(String name, String color, String frag, String title, Player p) {
		
		if(main.config.contains("boss.list." + name)){
			p.sendMessage("§cLe nom §4" + name + "§c est déjà utilisé !");
			return;
		}
		
		BarStyle barStyle;
		if(frag.equals("none")) barStyle = BarStyle.SOLID;
		else if(frag.equals("6")) barStyle = BarStyle.SEGMENTED_6;
		else if(frag.equals("10")) barStyle = BarStyle.SEGMENTED_10;
		else if(frag.equals("12")) barStyle = BarStyle.SEGMENTED_12;
		else if(frag.equals("20")) barStyle = BarStyle.SEGMENTED_20;
		else{
			p.sendMessage("§c/boss add <nom> <blanc/bleu/vert/rose/violet/rouge/jaune/aléatoire> <6/10/12/20/none> <texte");
			return;
		}
		
		main.config.set("boss.list." + name + ".color", color);
		main.config.set("boss.list." + name + ".frag", barStyle.name());
		main.config.set("boss.list." + name + ".title", title);
		
		p.sendMessage("§bune boss bar avec le nom §3" + name + "§b vient d'être créé !");
		
	}

	public void removeBar(String name, Player p) {
		
		if(main.config.contains("boss.list." + name)){
			main.config.set("boss.list." + name, null);
			BossBar bossBar = main.bars.get(name);
			
			for(Player players : Bukkit.getOnlinePlayers()){
				bossBar.removePlayer(players);
			}
			main.bars.remove(name);
			p.sendMessage("§bLa bossBar §3" + name + "§b a bien été supprimé.");
		}else p.sendMessage("§cAucune bossBar trouvé avec ce nom.");
		
		
		
	}

	public void list(Player p) {
		
		ConfigurationSection section = main.config.getConfigurationSection("boss.list");
		p.sendMessage("§3Boss bars :");
		for(String name : section.getKeys(false)){
			p.sendMessage("  §b- " + name);
		}
		
	}
	public void removeAll(){
		ConfigurationSection section = main.config.getConfigurationSection("boss.list");
		for(String name : section.getKeys(false)){
			for(Player players : Bukkit.getOnlinePlayers()){
				main.bars.get(name).removePlayer(players);
			}
		}
	}
	
	public void tick(){
		ConfigurationSection section = main.config.getConfigurationSection("boss.list");
		for(String name : section.getKeys(false)){
			
			String title = main.config.getString("boss.list." + name + ".title");
			String color = main.config.getString("boss.list." + name + ".color");
			String frag = main.config.getString("boss.list." + name + ".frag");
			
			if(main.bars.containsKey(name)){
				BossBar bossBar = main.bars.get(name);
				
				if(color.equalsIgnoreCase("aléatoire")){
					ArrayList<BarColor> barColors = new ArrayList<>();
					barColors.add(BarColor.WHITE); barColors.add(BarColor.BLUE); barColors.add(BarColor.GREEN); barColors.add(BarColor.PINK);
					barColors.add(BarColor.PURPLE); barColors.add(BarColor.RED); barColors.add(BarColor.YELLOW);
					
					bossBar.setColor(barColors.get(new Random().nextInt(barColors.size())));
				}
				
				for(Player players : Bukkit.getOnlinePlayers()){
					if(!bossBar.getPlayers().contains(players)){
						bossBar.addPlayer(players);
					}
				}
				
			}else{
				BossBar bossBar = Bukkit.createBossBar(title, BarColor.WHITE, BarStyle.valueOf(frag), new BarFlag[0]);
				
				if(color.equalsIgnoreCase("aléatoire")){
					ArrayList<BarColor> barColors = new ArrayList<>();
					barColors.add(BarColor.WHITE); barColors.add(BarColor.BLUE); barColors.add(BarColor.GREEN); barColors.add(BarColor.PINK);
					barColors.add(BarColor.PURPLE); barColors.add(BarColor.RED); barColors.add(BarColor.YELLOW);
					
					bossBar.setColor(barColors.get(new Random().nextInt(barColors.size())));
				}else{
					BarColor barColor = BarColor.WHITE;
					if(color.equalsIgnoreCase("bleu")) barColor = BarColor.BLUE; else if(color.equalsIgnoreCase("vert")) barColor = BarColor.GREEN;
					else if(color.equalsIgnoreCase("rose")) barColor = BarColor.PINK; else if(color.equalsIgnoreCase("violet")) barColor = BarColor.PURPLE;
					else if(color.equalsIgnoreCase("rouge")) barColor = BarColor.RED; else if(color.equalsIgnoreCase("jaune")) barColor = BarColor.YELLOW;
					
					bossBar.setColor(barColor);
				}
				
				for(Player players : Bukkit.getOnlinePlayers()){
					bossBar.addPlayer(players);
				}
				main.bars.put(name, bossBar);
			}
			
		}
		
	}

}

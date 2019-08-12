package fr.themsou.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.themsou.main.main;

public class EconomyCmd implements Listener, TabCompleter, CommandExecutor{


	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg1, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("setmoney")){
				
				if(args.length == 2){
					
					main.economy.withdrawPlayer(args[0], main.economy.getBalance(args[0]));
					main.economy.depositPlayer(args[0], Double.parseDouble(args[1]));
					p.sendMessage("§3" + args[0] + "§b à maintenant §3" + main.economy.getBalance(args[0]) + "€");
					
				}else p.sendMessage("§c/setmoney <joueur> <quantité>");
				
			}else if(cmd.getName().equalsIgnoreCase("money")){
				
				if(args.length == 1){
					
					if(args[0].equalsIgnoreCase("top")){
						
						HashMap<Integer, String> players = new HashMap<>();
						List<Integer> moneys = new ArrayList<>();
						for(String player : main.config.getConfigurationSection("").getKeys(false)){
							if(!main.config.contains(player + ".list")){
								int money = (int) main.economy.getBalance(player);
								if(money > 10000){
									players.put(money, player);
									moneys.add(money);
								}
							}
						}
						Collections.sort(moneys);
						
						int iterations = moneys.size() - 20;
						if(iterations < 0){
							iterations = 0;
						}
						for(int i = (moneys.size()-1); i >= iterations; i--){
							String player = players.get(moneys.get(i));
							p.sendMessage("§c" + (moneys.size() - i) + "> §6" + player + " : " + moneys.get(i) + "€");
						}
						
					}else if(main.config.contains(args[0] + ".mdp")){
						p.sendMessage("§3" + args[0] + "§b a §3" + main.economy.getBalance(args[0]) + " €");
					}else{
						p.sendMessage("§cAucun joueur trouvé avec le nom §6" + args[0]);
					}
					
				}else{
					p.sendMessage("§bVous avez §3" + main.economy.getBalance(p.getName()) + " §3€");
				}
			}
			return true;
		}
		return false;
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		
		Player p = e.getPlayer();
		String[] args = e.getMessage().split(" ");
		
		if(args[0].equalsIgnoreCase("/pay")){
			e.setCancelled(true);
			double prix = 0;
			
			if(args.length == 3){
				
				try{
					prix = Double.parseDouble(args[2]);
				}catch (NumberFormatException e2){
					p.sendMessage("§c/pay <joueur>/ent:<entreprise> <argent>");
					return;
			    }
				
				if(prix < 0){
					p.sendMessage("§cHahaha petit malin, tu croyais vraiment pouvoir arnaquer les gens comme ça !!!  XD");
					return;
				}
				
				if(main.economy.getBalance(p) >= prix){
					if(args[1].split(":")[0].equals("ent")){
						args[1] = args[1].replace("ent:", "");
						
						if(main.config.contains("ent.list." + args[1])){
							
							main.economy.withdrawPlayer(p, prix);
							main.config.set("ent.list." + args[1] + ".money", main.config.getInt("ent.list." + args[1] + ".money") + prix);
							p.sendMessage("§c" + prix + "€§6 ont bien été envoyés à l'entreprise §c" + args[1]);
							
							String pdg = main.config.getString("ent.list." + args[1] + ".pdg");
							if(Bukkit.getOfflinePlayer(pdg).isOnline()) Bukkit.getPlayerExact(pdg).sendMessage("§c" + p.getName() + " §6a envoyé §c" + prix + "€§6 à votre entreprise");
							
						}else p.sendMessage("§cIl n'existe pas d'entreprises avec le nom §6" + args[1]);
						
					}else{
						
						if(main.config.contains(p.getName())){
							
							main.economy.withdrawPlayer(p, prix);
							main.economy.depositPlayer(Bukkit.getOfflinePlayer(args[1]), prix);
							p.sendMessage("§c" + prix + "€§6 ont bien été envoyés à §c" + args[1]);
							if(Bukkit.getOfflinePlayer(args[1]).isOnline()) Bukkit.getPlayerExact(args[1]).sendMessage("§c" + p.getName() + " §6vous a envoyé §c" + prix + "€");
						
						}else p.sendMessage("§cAucun joueur trouvé avec le nom §6" + p.getName());
					
					}
				}else p.sendMessage("§cVous devez avoir §4" + prix + "€");
				
			}else p.sendMessage("§c/pay <joueur>/ent:<entreprise> <argent>");
		}
		
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg, String[] args){
		
		if(sender instanceof Player){
			
			if(cmd.getName().equalsIgnoreCase("pay")){
				if(args.length == 1){
					
					ArrayList<String> returns = new ArrayList<>();
					
					for(Player players : Bukkit.getOnlinePlayers()){
						if(players.getName().toLowerCase().startsWith(args[0].toLowerCase())){
							returns.add(players.getName());
						}
					}
					for(String ent : main.config.getConfigurationSection("ent.list").getKeys(false)){
						if(("ent:" + ent).toLowerCase().startsWith(args[0].toLowerCase()) || ent.toLowerCase().startsWith(args[0].toLowerCase())){
							returns.add("ent:" + ent);
						}
					}
					return returns;
						
					
				}else if(args.length == 2){
					
					return Arrays.asList("<prix>");
					
				}
			}else if(cmd.getName().equalsIgnoreCase("money")){
				
				if(args.length == 1){
					
					ArrayList<String> returns = new ArrayList<>();
					
					for(Player players : Bukkit.getOnlinePlayers()){
						if(players.getName().toLowerCase().startsWith(args[0].toLowerCase())){
							returns.add(players.getName());
						}
					}
					returns.add("top");
					return returns;
						
				}
				
			}
		}
		
		return Arrays.asList("");
	}

}

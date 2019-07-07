package fr.themsou.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.themsou.main.main;
import fr.themsou.rp.ent.EntUnderCmd;

public class EntCmd implements TabCompleter, CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg1, String[] args) {
		
		if(sender instanceof Player){
			
			Player p = (Player) sender;
			EntUnderCmd CEntUnderCmd = new EntUnderCmd(p);
			
			if(args.length >= 1){
				
				if(args[0].equalsIgnoreCase("create")){ // Créer
					if(args.length == 2){
						CEntUnderCmd.create(args[1]);
					}else p.sendMessage("§c/ent create <nom>");
					
				}else if(args[0].equalsIgnoreCase("leave")){ // Quitter
					CEntUnderCmd.leave();
					
				}else if(args[0].equalsIgnoreCase("list")){ // List
					CEntUnderCmd.list();
					
				}else if(args[0].equalsIgnoreCase("info")){ // List
					if(args.length == 2){
						CEntUnderCmd.info(args[1]);
					}else p.sendMessage("§c/ent info <entreprise>");
					
				}else if(args[0].equalsIgnoreCase("join")){ // Join
					CEntUnderCmd.join();
					
				}else if(args[0].equalsIgnoreCase("hire")){ // Embaucher
					if(args.length == 3 && (args[2].equalsIgnoreCase("aucun") || args[2].equalsIgnoreCase("salarié") || args[2].equalsIgnoreCase("manager"))){
						int role = 0;
						if(args[2].equalsIgnoreCase("salarié")) role = 1;
						else if(args[2].equalsIgnoreCase("manager")) role = 2;
						CEntUnderCmd.hire(Bukkit.getOfflinePlayer(args[1]), role);
					}else p.sendMessage("§c/ent hire <joueur> <aucun/salarié/manager>");
					
				}else if(args[0].equalsIgnoreCase("money")){ // Money
					CEntUnderCmd.money();
					
				}else if(args[0].equalsIgnoreCase("pay")){ // Payer
					if(args.length == 3){
						if(Integer.parseInt(args[2]) >= 1){
							CEntUnderCmd.pay(args[1], Integer.parseInt(args[2]));
						}else p.sendMessage("§cVous devez entrer un nombre supèrieur à 0");
					}else p.sendMessage("§c/ent pay <joueur>/ent:<entrprise> <argent>");
					
				}else if(args[0].equalsIgnoreCase("sign")){ // Pancarte
					if(args.length >= 3){
						if(Integer.parseInt(args[1]) >= 1){
							
							String desc = ""; int argP = 0;
							for(String dscP : args){
								if(argP == 2) desc = dscP;
								else if(argP >= 2) desc = desc + " " + dscP;
								argP ++;
							}
							
							CEntUnderCmd.sign(Integer.parseInt(args[1]), desc);
						}else p.sendMessage("§cVous devez entrer des nombres supèrieurs à 0");
					}else p.sendMessage("§c/ent sign <prix> <description");
					
					
				}else if(args[0].equalsIgnoreCase("src")){ // Ressource
					if(args.length == 2){
						if(args[1].equalsIgnoreCase("clear")) CEntUnderCmd.srcRemove();
						else p.sendMessage("/ent src clear");
					}else if(args.length == 1) CEntUnderCmd.src();
					else p.sendMessage("/ent src clear");
				}else if(args[0].equalsIgnoreCase("log")){ // Logs
					if(args.length == 2){
						CEntUnderCmd.log(args[1]);
					}else p.sendMessage("§c/ent log <joueur>");
					
				}else if(args[0].equalsIgnoreCase("buyers")){ // Logs
					if(args.length == 1){
						CEntUnderCmd.buyers();
					}else p.sendMessage("§c/ent buyers");
					
				}else if(args[0].equalsIgnoreCase("rename")){ // Logs
					if(args.length == 2){
						CEntUnderCmd.rename(args[1]);
					}else p.sendMessage("§c/ent rename <nom>");
					
				}else{
					CEntUnderCmd.infoMessage();
				}
			}else{
				CEntUnderCmd.infoMessage();
			}
			return true;
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			String ent = "";
			if(main.config.contains(p.getName() + ".rp.ent.name")){
				ent = main.config.getString(p.getName() + ".rp.ent.name");
			}
			int role = main.config.getInt(p.getName() + ".rp.ent.role");
			
			if(args.length == 1){
				
				if(ent == null || role == 0){
					
					return GeneralCmd.matchTab(Arrays.asList("create","leave","info","money"), args[0]);
					
				}else if(role == 1){
					
					return GeneralCmd.matchTab(Arrays.asList("create","leave","info","money","hire","pay","sign","src","log","buyers"), args[0]);
					
				}else if(role == 2){
					
					return GeneralCmd.matchTab(Arrays.asList("create","leave","info","money","hire","pay","sign","src","log","buyers","rename"), args[0]);
					
				}
				
			}else if(args.length >= 2){
				
				if(args[0].equalsIgnoreCase("info") && args.length == 2){
					
					return new ArrayList<>(main.config.getConfigurationSection("ent.list").getKeys(false));
					
				}if(args[0].equalsIgnoreCase("create") && args.length == 2){
					
					return Arrays.asList("<nom>");
					
				}if(args[0].equalsIgnoreCase("pay") && args.length == 2){
					
					ArrayList<String> returns = new ArrayList<>();
					for(Player players : Bukkit.getOnlinePlayers()){
						if(players.getName().startsWith(args[0])) returns.add(players.getName());
					}
					for(String ents : main.config.getConfigurationSection("ent.list").getKeys(false)){
						if(("ent:" + ents).startsWith(args[0]) || ents.startsWith(args[0])) returns.add("ent:" + ents);
					}
					return returns;
					
				}if(args[0].equalsIgnoreCase("pay") && args.length == 3){
					
					return Arrays.asList("<Prix>");
					
				}if(args[0].equalsIgnoreCase("rename") && args.length == 2){
					
					return Arrays.asList("<nouveau nom>");
					
				}if(args[0].equalsIgnoreCase("hire") && args.length == 3){
					
					return GeneralCmd.matchTab(Arrays.asList("aucun","salarié","manager"), args[2]);
					
				}if(args[0].equalsIgnoreCase("sign") && args.length == 2){
					
					return Arrays.asList("<prix>");
					
				}if(args[0].equalsIgnoreCase("sign") && args.length == 3){
					
					return Arrays.asList("<description");
					
				}if(args[0].equalsIgnoreCase("src") && args.length == 2){
					
					return GeneralCmd.matchTab(Arrays.asList("clear"), args[1]);
					
				}if(args[0].equalsIgnoreCase("log") && args.length == 2){
					
					if(main.config.getConfigurationSection("ent.list." + ent + ".log").getKeys(false).size() != 0){
						return GeneralCmd.matchTab(new ArrayList<>(main.config.getConfigurationSection("ent.list." + ent + ".log").getKeys(false)), args[1]);
					}
					return Arrays.asList("aucun log.","-Vous n'avez");
					
				}
				
			}
		}
		
		return Arrays.asList("");
		
	}
	
}

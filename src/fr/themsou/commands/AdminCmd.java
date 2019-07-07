package fr.themsou.commands;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import fr.themsou.admin.AdminInv;
import fr.themsou.admin.Punish;

public class AdminCmd implements TabCompleter, CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg1, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			int perm = new GradeCmd().getPlayerPermition(p.getName());
			if(perm >= 3){
				
				Punish CPunish = new Punish();
				
				if(args.length == 0) new AdminInv().oppenadmininventory(p);
				else{
					if(perm == 3 || perm == 4){ // MODO - BUILDER
							
						if(args[0].equalsIgnoreCase("mute")){
							
							if(args.length == 4) CPunish.punishPlayer(args[1], p.getName(), "mute", Integer.parseInt(args[2]), args[3]);
							else p.sendMessage("§c/a mute <Joueur> <Minutes> <Raison>");
							
						}else if(args[0].equalsIgnoreCase("unmute")){
							
							if(args.length == 3) CPunish.pardonPlayer(args[1], p.getName(), "mute", args[2]);
							else p.sendMessage("§c/a unmute <Joueur> <Raison>");
							
						}else if(args[0].equalsIgnoreCase("kick")){
							
							if(args.length == 3) CPunish.punishPlayer(args[1], p.getName(), "kick", 0, args[2]);
							else p.sendMessage("§c/a kick <Joueur> <Raison>");
							
						}else if(args[0].equalsIgnoreCase("all")) new AdminInv().oppenadminallinventory(p);
							
						else p.sendMessage("§c/a [mute/unmute/kick/all]");
						
					}else if(perm >= 5){ // SM - ADMIN
						
						if(args[0].equalsIgnoreCase("ban")){
							
							if(args.length == 4) CPunish.punishPlayer(args[1], p.getName(), "ban", Integer.parseInt(args[2]), args[3]);
							else p.sendMessage("§c/a ban <Joueur> <Jours> <Raison>");
							
						}else if(args[0].equalsIgnoreCase("unban")){
							
							if(args.length == 3) CPunish.pardonPlayer(args[1], p.getName(), "ban", args[2]);
							else p.sendMessage("§c/a unban <Joueur> <Raison>");
							
						}else if(args[0].equalsIgnoreCase("mute")){
							
							if(args.length == 4) CPunish.punishPlayer(args[1], p.getName(), "mute", Integer.parseInt(args[2]), args[3]);
							else p.sendMessage("§c/a mute <Joueur> <Minutes> <Raison>");
							
						}else if(args[0].equalsIgnoreCase("unmute")){
							
							if(args.length == 3) CPunish.pardonPlayer(args[1], p.getName(), "mute", args[2]);
							else p.sendMessage("§c/a unmute <Joueur> <Raison>");
							
						}else if(args[0].equalsIgnoreCase("kick")){
							
							if(args.length == 3) CPunish.punishPlayer(args[1], p.getName(), "kick", 0, args[2]);
							else p.sendMessage("§c/a kick <Joueur> <Raison>");
							
						}else if(args[0].equalsIgnoreCase("all")){
							new AdminInv().oppenadminallinventory(p);
						
						}else p.sendMessage("§c/a [ban/mute/unban/unmute/kick/all]");
					}
				}
			}
		}else{
			
			Punish CPunish = new Punish();
			
			if(args[0].equalsIgnoreCase("ban")){
				
				if(args.length == 4) CPunish.punishPlayer(args[1], "Console", "ban", Integer.parseInt(args[2]), args[3]);
				else Bukkit.getConsoleSender().sendMessage("§c/a ban <Joueur> <Jours> <Raison>");
				
			}else if(args[0].equalsIgnoreCase("unban")){
				
				if(args.length == 3) CPunish.pardonPlayer(args[1], "Console", "ban", args[2]);
				else Bukkit.getConsoleSender().sendMessage("§c/a unban <Joueur> <Raison>");
				
			}else if(args[0].equalsIgnoreCase("mute")){
				
				if(args.length == 4) CPunish.punishPlayer(args[1], "Console", "mute", Integer.parseInt(args[2]), args[3]);
				else Bukkit.getConsoleSender().sendMessage("§c/a mute <Joueur> <Minutes> <Raison>");
				
			}else if(args[0].equalsIgnoreCase("unmute")){
				
				if(args.length == 3) CPunish.pardonPlayer(args[1], "Console", "mute", args[2]);
				else Bukkit.getConsoleSender().sendMessage("§c/a unmute <Joueur> <Raison>");
				
			}else if(args[0].equalsIgnoreCase("kick")){
				
				if(args.length == 3) CPunish.punishPlayer(args[1], "Console", "kick", 0, args[2]);
				else Bukkit.getConsoleSender().sendMessage("§c/a kick <Joueur> <Raison>");
				
			}else Bukkit.getConsoleSender().sendMessage("§c/a [ban/mute/unban/unmute/kick]");
			
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg, String[] args){
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(args.length == 1){
				int perm = new GradeCmd().getPlayerPermition(p.getName());
				
				if(perm == 3 || perm == 4){
					return GeneralCmd.matchTab(Arrays.asList("mute","unmute","kick","all"), args[0]);
					
				}else if(perm >= 5){
					return GeneralCmd.matchTab(Arrays.asList("ban","mute","unban","unmute","kick","all"), args[0]);
				}	
			}else if(args.length == 3){
				
				if(args[0].equals("ban")){
					return Arrays.asList("<Jours>");
					
				}else if(args[0].equals("mute")){
					return Arrays.asList("<Minutes>");
					
				}if(args[0].equals("unban")){
					return Arrays.asList("<Raison>");
					
				}else if(args[0].equals("unmute")){
					return Arrays.asList("<Raison>");
					
				}else if(args[0].equals("kick")){
					return Arrays.asList("<Raison>");
				}
				
			}else if(args.length == 4){
				if(args[0].equals("ban")){
					return Arrays.asList("<Raison>");
					
				}else if(args[0].equals("mute")){
					return Arrays.asList("<Raison>");
					
				}
			}
		}
		
		return Arrays.asList("");
	}

}

package fr.themsou.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.themsou.discord.Link;
import fr.themsou.main.main;
import fr.themsou.methodes.login;
import fr.themsou.rp.inv.Inventory;

public class GeneralCmd implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg1, String[] args){
		
		if(sender instanceof Player){
			Player p = (Player) sender;
//
//	DISCORD
//
			if(cmd.getName().equalsIgnoreCase("discord")){
				if(args.length == 1){
					new Link().linkPlayer(p.getName(), args[0]);
					p.sendMessage("§3Un message de confirmation vous a été envoyé sur discord");
				}
				else p.sendMessage("§c/discord <pseudo>");
			}
//
//	MENU GUI
//
			else if(cmd.getName().equalsIgnoreCase("?") || cmd.getName().equalsIgnoreCase("help")){
				
				if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
					new Inventory().openMainInventory(p);
				}else if(p.getWorld() == Bukkit.getWorld("BedWars") || p.getWorld() == Bukkit.getWorld("BedWars-ressources")){
					p.openInventory(main.Lapmenu);
				}else{
					p.openInventory(main.menu);
				}
				
			}
			
			
//
//	LOGIN
//		
			else if(cmd.getName().equalsIgnoreCase("l") || cmd.getName().equalsIgnoreCase("login")) new login().loginPlayer(p, args);
			else if(cmd.getName().equalsIgnoreCase("reg") || cmd.getName().equalsIgnoreCase("register")) new login().RegisterPlayer(p, args);
			
		}
		return false;
	}
	
	public static List<String> matchTab(List<String> tabs, String toMatch){
		
		if(toMatch.isEmpty()) return tabs;
		ArrayList<String> matches = new ArrayList<>();
		
		for(int i = 0; i < tabs.size(); i++){
			if(tabs.get(i).startsWith(toMatch)){
				matches.add(tabs.get(i));
			}
		}
		
		return matches;
		
	}

}

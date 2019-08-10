package fr.themsou.commands;

import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class OtherCmd implements CommandExecutor, TabCompleter {

	

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg0, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(args.length == 0){
				p.sendMessage("§c/pack <music1/music2>");
				return true;
			}
				
			if(args[0].equalsIgnoreCase("music1")){
				
			
				try{
					p.setResourcePack("http://download1929.mediafire.com/ucckdaazzfyg/9z3g8qoy3agy82r/Meme+Music+Pack+by+tibo.zip");
					p.sendMessage("§6Vous venez bien de charger le pack §cMusique n°1");
				}catch(IllegalArgumentException a){
					p.sendMessage("§cUne erreur est survenue, impossible de charger le texture pack.");
				}
				
			}else if(args[0].equalsIgnoreCase("music2")){
				
				try{
					p.setResourcePack("https://download2266.mediafire.com/3sfrwno1hmsg/snovp9kxheda336/Meme+Music+Pack+by+tibo+%232.zip");
					p.sendMessage("§6Vous venez bien de charger le pack §cMusique n°2");
				}catch(IllegalArgumentException a){
					p.sendMessage("§cUne erreur est survenue, impossible de charger le texture pack.");
				}
				
			}
		}
		
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg0, String[] args) {
		
		if(sender instanceof Player){
			
			if(args.length == 1){
				return GeneralCmd.matchTab(Arrays.asList("music1","music2","off"), args[0]);
			}
		}
		
		return Arrays.asList("");
	}

}

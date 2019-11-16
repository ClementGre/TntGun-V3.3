package fr.themsou.commands;

import java.util.Random;

import fr.themsou.methodes.PInfos;
import fr.themsou.methodes.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.themsou.main.main;
import fr.themsou.nms.message;
import fr.themsou.rp.claim.Spawns;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TeleportCmd implements Listener, CommandExecutor{

	
//                                    TPA
	@EventHandler
	public void onCommandesHelp(PlayerCommandPreprocessEvent e){

		Player p = e.getPlayer();
		String msg = e.getMessage();
		String[] args = msg.split(" ");
		PlayerInfo pInfo = main.playersInfos.get(p);

		if(!e.isCancelled() && pInfo.isLoggin()){
			if(args[0].equalsIgnoreCase("/tpa")){
				e.setCancelled(true);
				if(args.length == 2){
					
					
					Player p2 = Bukkit.getPlayerExact(args[1]);
					String pn = p.getName();
					if(p2 != null){
						String p2n = p2.getName();
						PlayerInfo p2Info = main.playersInfos.get(p2);

						if(p2n != pn){
							message.sendNmsMessageCmd(p2, "§6TELEPORTATION > §3" + pn + " §bsouhaite se téléporter a vous §2\u2714 §bou §4\u2716", "§3Cliquez pour accepter", "/tpayes");
							p2Info.setTpa(p);
							p.sendMessage("§6TELEPORTATION > §bVous venez de demander à §3"+p2n+"§b de vous téléporter à lui");

						}else{
							p.sendMessage("§6TELEPORTATION > §bVous ne pouvez pas vous téléporter a vous meme");
						}
					}else{
						p.sendMessage("§6TELEPORTATION > §3" + args[1] + " §bn'est pas conecté");
					}
				}else{
					p.sendMessage("§6COMMANDES > §bFaites /tpa <joueur>");
				}
			}else if(args[0].equalsIgnoreCase("/tpayes")){
				
				e.setCancelled(true);
				Player p2 = pInfo.getTpa();

				if(p2 != null){
					if(p2.isOnline()){

						if(PInfos.getGame(p).equals(PInfos.getGame(p2))){
							p.sendMessage("§6TELEPORTATION > §bVous venez de téléporter §3" + p2.getName() + " §bà vous");
							p2.teleport(p.getLocation());
						}else{
							p.sendMessage("§cLe joueur n'est pas dans le meme mode de jeux que vous !");
						}
					}else{
						p.sendMessage("§6TELEPORTATION > §bCe joueur n'est plus conecté");
					}
					pInfo.setTpa(null);
				}else{
					p.sendMessage("§6TELEPORTATION > §bPersone n'as demandé de se téléporter a vous");
				}
				
				
//	                                        TPHERE
				
			}else if(args[0].equalsIgnoreCase("/tphere")){
				e.setCancelled(true);
				if(args.length == 2){

					String pn = p.getName();
					Player p2 = Bukkit.getPlayerExact(args[1]);

					if(p2 != null){
						String p2n = p2.getName();
						PlayerInfo p2Info = main.playersInfos.get(p2);

						if(p2n != pn){
							TextComponent m = new TextComponent( "§6TELEPORTATION > §3" + pn + " §bsouhaite vous téléporter a lui §2\u2714 §bou §4\u2716" );
							m.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3Cliquez pour accepter").create() ) );
							m.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tphereyes" ) );
							p2.spigot().sendMessage(m);
							p2Info.setTphere(p);
							p.sendMessage("§6TELEPORTATION > §bVous venez de demander à §3"+p2n+"§b de se téléporter a vous");
						}else{
							p.sendMessage("§6TELEPORTATION > §bVous ne pouvez pas vous téléporter a vous meme");
						}
					}else{
						p.sendMessage("§6TELEPORTATION > §3" + args[1] + " §bn'est pas conecté");
					}
				}else{
					p.sendMessage("§6COMMANDES > §bFaites /tphere <joueur>");
				}
				
				
			}else if(args[0].equalsIgnoreCase("/tphereyes")){

				e.setCancelled(true);
				Player p2 = pInfo.getTphere();

				if(p2 != null){
					if(p2.isOnline()){

						if(PInfos.getGame(p).equals(PInfos.getGame(p2))){
							p.sendMessage("§6TELEPORTATION > §bVous venez de vous téléporter à §3" + p2.getName());
							p.teleport(p2.getLocation());
						}else{
							p.sendMessage("§cLe joueur n'est pas dans le meme mode de jeux que vous !");
						}
					}else{
						p.sendMessage("§6TELEPORTATION > §bCe joueur n'est plus conecté");
					}
					pInfo.setTphere(null);
				}else{
					p.sendMessage("§6TELEPORTATION > §bPersone n'as demandé de vous téléporter a lui");
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg0, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("rtp")){
				
				Location loc = null;
				Random r = new Random();
				
				while(loc == null){
					
					int x = r.nextInt(2000) - 1000; int z = r.nextInt(2000) - 1000; int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z);
					Location tempLoc = new Location(Bukkit.getWorld("world"), x, y, z);
					
					if(tempLoc.getBlock().getType() != Material.WATER && tempLoc.getBlock().getType() != Material.LAVA){
						if(!new Spawns().isInSpawn(tempLoc)){
							tempLoc.setY(tempLoc.getY() + 2);
							loc = tempLoc;
						}
					}
					
				}
				
				p.sendMessage("§bTéléportation à une localisation aléatoire (dans 1000 blocs de rayon)");
				p.teleport(loc);
			}
			
		}
		
		return false;
	}

	
}

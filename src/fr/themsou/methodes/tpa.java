package fr.themsou.methodes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.themsou.main.main;
import fr.themsou.nms.message;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class tpa implements Listener {

	
	public tpa() {
	}
	
//                                    TPA
	@EventHandler
	public void onCommandesHelp(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		String msg = e.getMessage();
		String[] args = msg.split(" ");
		if(!e.isCancelled()){
			
			
			if(args[0].equalsIgnoreCase("/tpa")){
				e.setCancelled(true);
				if(args.length == 2){
					
					
					Player p2 = Bukkit.getPlayerExact(args[1]);
					String pn = p.getName();
					
					
					if(p2 != null){
						String p2n = p2.getName();
						if(p2n != pn){
							
							
							message.sendNmsMessageCmd(p2, "§6TELEPORTATION > §3" + pn + " §bsouhaite se téléporter a vous §2\u2714 §bou §4\u2716", "§3Cliquez pour accepter", "/tpayes");
							
							main.config.set(p2n + ".tpa", pn);	
							
							p.sendMessage("§6TELEPORTATION > §bVous venez de demander à §3"+p2n+"§b de vous téléporter à lui");
							
							
						}else{
							p.sendMessage("§6TELEPORTATION > §bVous ne pouvez pas vous téléporter a vous meme");
						}
					}
					else{
						p.sendMessage("§6TELEPORTATION > §3" + args[1] + " §bn'est pas conecté");
					}
				}
				else{
					p.sendMessage("§6COMMANDES > §bFaites /tpa <joueur>");
				}
				
				
				
				
				
				
				
			}if(args[0].equalsIgnoreCase("/tpayes")){
				
				e.setCancelled(true);
				String pn = p.getName();
				
				
				
				
				if(main.config.getString(pn + ".tpa") != ""){
					if(Bukkit.getPlayerExact(main.config.getString(pn + ".tpa")) != null){
						Player p2 = Bukkit.getPlayerExact(main.config.getString(pn + ".tpa"));

						String pworld = p.getWorld().getName().replace("_nether", "").replace("_the_end", "");
						String p2world = p2.getWorld().getName().replace("_nether", "").replace("_the_end", "");
						
						if(pworld.equals(p2world)){
							p.sendMessage("§6TELEPORTATION > §bVous venez de téléporter §3" + main.config.getString(pn + ".tpa") + " §bà vous");
							p2.teleport(p.getLocation());
						}else p.sendMessage("§cLe joueur n'est pas dans le meme mode de jeux que vous !");
						
						main.config.set(pn + ".tpa", "");
						
					}else{
						p.sendMessage("§6TELEPORTATION > §bCe joueur n'est plus conecté");
					}	
				}else if(main.config.getString(pn + ".tpa") == ""){
					p.sendMessage("§6TELEPORTATION > §bPersone n'as demandé de se téléporter a vous");
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
//	                                        TPHERE
				
			}if(args[0].equalsIgnoreCase("/tphere")){
				e.setCancelled(true);
				if(args.length == 2){
					
					
					Player p2 = Bukkit.getPlayerExact(args[1]);
					
					String pn = p.getName();
					
					
					
					if(p2 != null){
						String p2n = p2.getName();
						if(p2n != pn){
							TextComponent m = new TextComponent( "§6TELEPORTATION > §3" + pn + " §bsouhaite vous téléporter a lui §2\u2714 §bou §4\u2716" );
							m.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3Cliquez pour accepter").create() ) );
							m.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tphereyes" ) );
							p2.spigot().sendMessage( m );
							main.config.set(p2n + ".tphere", pn);	
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
				
				
			}if(args[0].equalsIgnoreCase("/tphereyes") && main.config.getInt(p.getName() + ".status") == 1){
				
				e.setCancelled(true);
				String pn = p.getName();
				
				
				
				
				if(main.config.getString(pn + ".tphere") != ""){
					if(Bukkit.getPlayerExact(main.config.getString(pn + ".tphere")) != null){
						Player p2 = Bukkit.getPlayerExact(main.config.getString(pn + ".tphere"));
					
						String pworld = p.getWorld().getName().replace("_nether", "").replace("_the_end", "");
						String p2world = p2.getWorld().getName().replace("_nether", "").replace("_the_end", "");
						
						if(pworld.equals(p2world)){
							p.sendMessage("§6TELEPORTATION > §bVous venez de vous téléporter à §3" + main.config.getString(pn + ".tphere"));
							p.teleport(p2.getLocation());
						}else p.sendMessage("§cLe joueur n'est pas dans le meme mode de jeux que vous !");
						
						main.config.set(pn + ".tphere", "");
						
						
					}else{
						p.sendMessage("§6TELEPORTATION > §bCe joueur n'est plus conecté");
					}
				}else if(main.config.getString(pn + ".tphere") == ""){
					p.sendMessage("§6TELEPORTATION > §bPersone n'as demandé de vous téléporter a lui");
				}
			}
			
			
		}
	}
}

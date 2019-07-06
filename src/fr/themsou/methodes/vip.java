package fr.themsou.methodes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import fr.themsou.main.main;

public class vip implements Listener {

	@SuppressWarnings("unused")
	private main pl;
	public vip(main pl){
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCommandesHelp(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		String msg = e.getMessage();
		String[] args = msg.split(" ");
		Grade CGrade = new Grade();
		int i = CGrade.getPlayerPermition(p.getName());
		
		if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
			
			if(args[0].equalsIgnoreCase("/ec")){
				e.setCancelled(true);
				if(i != 1){
					p.openInventory(p.getEnderChest());
					p.sendMessage("§6VIP >§bVous avez ouvert votre §3EnderChest");
				}else{
					p.sendMessage("§6VIP > §cVous devez etre §eVIP§c pour avoir ces droits");
				}
				
			}if(args[0].equalsIgnoreCase("/craft")){
				if(i != 1){
					
					p.sendMessage("§6VIP > §bVous avez ouvert une §3Table de craft");
					
				}else{
					p.sendMessage("§6VIP > §cVous devez etre §eVIP§c pour avoir ces droits");
					e.setCancelled(true);
				}
			}
			if(args[0].equalsIgnoreCase("/tete")){
				e.setCancelled(true);
				if(i != 1){
					
					if(args.length != 2){
						
						p.sendMessage("§6VIP > §bFaites §3/tete <Joueur>");	
						
					}else{
						String tete = args[1];
						
						p.sendMessage("§6VIP > §bVoici la tete de §3" + tete);
						
						ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
						SkullMeta meta = (SkullMeta) skull.getItemMeta();
						meta.setOwner(tete);
						skull.setItemMeta(meta);
						p.getInventory().addItem(skull);
					}
				
				}else{
					p.sendMessage("§6VIP > §cVous devez etre §eVIP§c pour avoir ces droits");
				}	
			}
		}
		
		if(args[0].equalsIgnoreCase("/nick")){
			if(i != 1){
				if(args.length == 2){
					
					/*MySkin myskin = (MySkin) Bukkit.getPluginManager().getPlugin("MySkin");
					
					UUID skin = myskin.getCache().loadUUID(args[1]);
					
					myskin.getCache().saveSkinOfPlayer(p.getUniqueId(), skin);
					myskin.getHandler().update(p);*/
					
				}else{
					
					e.setCancelled(true);
					p.sendMessage("§6VIP > §bFaites §3/nick <Pseudo>");	
					
				}
			}else{
				p.sendMessage("§6VIP > §cVous devez etre §eVIP§c pour avoir ces droits");
				e.setCancelled(true);
			}
		}
	}
}

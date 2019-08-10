package fr.themsou.inv;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.themsou.BedWars.BedWarsInv;
import fr.themsou.commands.GradeCmd;
import fr.themsou.main.main;
import fr.themsou.methodes.PInfos;
import fr.themsou.rp.inv.RolePlayMainInv;
import fr.themsou.rp.inv.RolePlayShopInv;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class VipInv {
	
	public void openInv(Player p){
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§4VIP");
		new UtilsInv().setWalls(inv, 6);
		
		inv.setItem(24, UtilsInv.makeItem(Material.EMERALD, "§3§lACHETER LE GRADE §e§lVIP§3§l VRAIE VIE", Arrays.asList("§bPour seulement 4€")));
		inv.setItem(22, UtilsInv.makeItem(Material.BOOK, "§3§lVOIR LES AVANTAGES", null));
		inv.setItem(20, UtilsInv.makeItem(Material.GRASS_BLOCK, "§3§lACHETER LE GRADE §e§lVIP§3§l EN JEUX", Arrays.asList("§bPour seulement §e20 000§b points")));
		inv.setItem(40, UtilsInv.makeItem(Material.ARROW, "§4Retour", Arrays.asList("")));
		
		p.openInventory(inv);
		
	}
	
	public void itemClicked(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem().getType() == Material.ARROW){
			
			p.closeInventory();
			if(PInfos.getGame(p).equals("RP")){
				new RolePlayMainInv().openInv(p);
			}else if(PInfos.getGame(p).equals("BedWars")){
				new BedWarsInv().openMainInv(p);
			}else{
				new HubInv().openInv(p);
			}
			
		}
		
		if(e.getCurrentItem().getType() == Material.EMERALD){
			
			p.closeInventory();
			
			TextComponent m = new TextComponent( "§6VIP >§b Envoyez-nous un e-mail à tntgun@free.fr" );
			m.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3tntgun@free.fr").create()));
			p.spigot().sendMessage(m);
			
			TextComponent m2 = new TextComponent( "§6VIP >§b Ou contactez nous sur discord" );
			m2.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3Cliquez pour ouvrir le lien").create()));
			m2.setClickEvent( new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/EyPmvuZ"));
			p.spigot().sendMessage(m2);
			
		}if(e.getCurrentItem().getType() == Material.BOOK){
				
			p.closeInventory();
			
			p.sendMessage("§6VIP >§b LISTE DES COMMANDES VIP :");
			p.sendMessage("§b  ROLEPLAY :");
			p.sendMessage("§c    /ec §b> §3Ouvrir son enderchest");
			p.sendMessage("§c    /craft §b> §3Ouvrir une table de craft virtuelle");
			p.sendMessage("§c    /tete <pseudo> §b> §3Obtenir la tete d'un joueur");
			p.sendMessage("§c    /feed §b> §3Se nourrir");
			p.sendMessage("§c    /night §b> §3Désactiver ou activer la vision nocturne");
			p.sendMessage("§b  TNTWARS :");
			p.sendMessage("§3    Choix de la map");
			p.sendMessage("§3    Inventaire pré-remplis");
			p.sendMessage("§b  BedWARS :");
			p.sendMessage("§c    /ec §b> §3Ouvrir son enderchest");
			p.sendMessage("§b  SERVEUR :");
			p.sendMessage("§c    /nick §b> §3Changer son pseudo");
			
			
		}if(e.getCurrentItem().getType() == Material.GRASS_BLOCK){
			
			p.closeInventory();
			
			if(!PInfos.getGame(p).equalsIgnoreCase("RP")){
				
				if(main.config.getInt(p.getName() + ".points") >= 20000){
					if(new GradeCmd().getPlayerPermition(p.getName()) == 1){
						
						main.config.set(p.getName() + ".points", main.config.getInt(p.getName() + ".points") - 20000);
						p.sendMessage("§bVous venez de dépencer §620 000 pts§b pour un garde §3VIP");
						new GradeCmd().changePlayerGradeWithConsole(p.getName(), "Vip");
						
					}else p.sendMessage("§cVous Avez déja un grade");
				}else p.sendMessage("§cVous n'avez pas assez de points");
				
			}else new RolePlayShopInv().openPtsInventory(p);
		}
		
		
	}
	
	

}

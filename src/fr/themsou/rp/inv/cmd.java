package fr.themsou.rp.inv;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class cmd {
	
	public void openCmdInventory(Player p){
		
		
		
		Inventory invcommandes = Bukkit.createInventory(null, 6*9, "§4COMMANDES");
		
		ItemStack a = new ItemStack(Material.PLAYER_HEAD, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setDisplayName("§3§lTÉLEPORTATION");
		aM.setLore(Arrays.asList("§bApprenez a vous téléporter a d'autres joueurs..."));
		a.setItemMeta(aM);				
		invcommandes.setItem(20, a);
		
		ItemStack b = new ItemStack(Material.BOOK, 1);
		ItemMeta bM = b.getItemMeta();
		bM.setDisplayName("§3§lDISCUTIONS");
		bM.setLore(Arrays.asList("§bApprenez a envoyer des messages privées..."));
		b.setItemMeta(bM);				
		invcommandes.setItem(22, b);
		
		ItemStack c = new ItemStack(Material.EMERALD, 1);
		ItemMeta cM = c.getItemMeta();
		cM.setDisplayName("§3§lARGENT");
		cM.setLore(Arrays.asList("§bApprenez a utiliser/récupérer de l'argent"));
		c.setItemMeta(cM);				
		invcommandes.setItem(24, c);
		
		ItemStack d = new ItemStack(Material.SHULKER_SHELL, 1);
		ItemMeta dM = d.getItemMeta();
		dM.setDisplayName("§3§lCLAIMS");
		dM.setLore(Arrays.asList("§bApprenez a gérer vos claims"));
		d.setItemMeta(dM);				
		invcommandes.setItem(30, d);
		
		ItemStack e = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta eM = e.getItemMeta();
		eM.setDisplayName("§3§lENTREPRISES");
		eM.setLore(Arrays.asList("§bApprenez a créer votre entreprise"));
		e.setItemMeta(eM);				
		invcommandes.setItem(32, e);
		
		ItemStack h = new ItemStack(Material.ARROW, 1);
		ItemMeta hM = h.getItemMeta();
		hM.setDisplayName("§4Retour");
		h.setItemMeta(hM);				
		invcommandes.setItem(40, h);
		
		ItemStack u = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta uM = u.getItemMeta();
		uM.setDisplayName("§e");
		u.setItemMeta(uM);
		invcommandes.setItem(0, u);
		invcommandes.setItem(1, u);
		invcommandes.setItem(2, u);
		invcommandes.setItem(3, u);
		invcommandes.setItem(4, u);
		invcommandes.setItem(5, u);
		invcommandes.setItem(6, u);
		invcommandes.setItem(7, u);
		invcommandes.setItem(8, u);
		invcommandes.setItem(9, u);
		invcommandes.setItem(17, u);
		invcommandes.setItem(18, u);
		invcommandes.setItem(26, u);
		invcommandes.setItem(27, u);
		invcommandes.setItem(35, u);
		invcommandes.setItem(36, u);
		invcommandes.setItem(44, u);
		invcommandes.setItem(45, u);
		invcommandes.setItem(46, u);
		invcommandes.setItem(47, u);
		invcommandes.setItem(48, u);
		invcommandes.setItem(49, u);
		invcommandes.setItem(50, u);
		invcommandes.setItem(51, u);
		invcommandes.setItem(52, u);
		invcommandes.setItem(53, u);
		invcommandes.setItem(44, u);

		p.openInventory(invcommandes);
		
		
		
	}

}

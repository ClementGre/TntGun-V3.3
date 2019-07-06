package fr.themsou.rp.inv;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.themsou.main.main;

public class Inventory {
	
	public void openMainInventory(Player p){
		
		org.bukkit.inventory.Inventory menu = main.rpmenu;
		
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// COLONE 1 ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
		
		ItemStack c1a = new ItemStack(Material.EMERALD, 1);
		ItemMeta c1aM = c1a.getItemMeta();
		c1aM.setDisplayName("§3§lSHOP");
		c1aM.setLore(Arrays.asList("§eIci, c'est pour vendre ou acheter","§edes items avec le serveur"));
		c1a.setItemMeta(c1aM);				
		menu.setItem(20, c1a);
		
		ItemStack c1b = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta c1bM = c1b.getItemMeta();
		c1bM.setDisplayName("§3§lCOMPÉTENCES");
		int score = (main.config.getInt(p.getName() + ".metier.mineur") + main.config.getInt(p.getName() + ".metier.bucheron") + main.config.getInt(p.getName() + ".metier.fermier") + main.config.getInt(p.getName() + ".metier.enchanteur") + main.config.getInt(p.getName() + ".metier.chasseur")) / 5;
		c1bM.setLore(Arrays.asList("§eVous pouvez voir ici votre avancement","§edans différentes compétences qui vous","§edébloqueront des outils spéciaux à 100%","§6Votre moyenne : §c" + score + "%"));
		c1b.setItemMeta(c1bM);				
		menu.setItem(22, c1b);
		
		ItemStack c1c = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta c1cM = c1c.getItemMeta();
		c1cM.setDisplayName("§3§lSHOP DE POINTS");
		c1cM.setLore(Arrays.asList("§eAchetez des choses","§eavec vos points !"));
		c1c.setItemMeta(c1cM);				
		menu.setItem(24, c1c);
		
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// COLONE 2 ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////		

		ItemStack c2a = new ItemStack(Material.COMPARATOR, 1);
		ItemMeta c2aM = c2a.getItemMeta();
		c2aM.setDisplayName("§3§lCOMMANDES");
		c2aM.setLore(Arrays.asList("§eListe de toutes les commandes Disponibles","§e(sauf les commandes de claim et d'entreprises)"));
		c2a.setItemMeta(c2aM);				
		menu.setItem(30, c2a);
		
		
		ItemStack c2b = new ItemStack(Material.DIAMOND, 1);
		ItemMeta c2bM = c2b.getItemMeta();
		c2bM.setDisplayName("§3§lVIP");
		c2bM.setLore(Arrays.asList("§eVoir les avantages vip"));
		c2b.setItemMeta(c2bM);				
		menu.setItem(32, c2b);
		
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// COLONE 3 ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		ItemStack g = new ItemStack(Material.COMMAND_BLOCK, 1);
		ItemMeta gM = g.getItemMeta();
		gM.setDisplayName("§3§lESPACE ADMINISTRATEUR");
		gM.setLore(Arrays.asList("§cCette espace est réservé aux administrateur"));
		g.setItemMeta(gM);				
		menu.setItem(49, g);
		
		ItemStack u = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta uM = u.getItemMeta();
		uM.setDisplayName("§e");
		u.setItemMeta(uM);				
		menu.setItem(1, u);
		menu.setItem(2, u);
		menu.setItem(3, u);
		menu.setItem(4, u);
		menu.setItem(5, u);
		menu.setItem(6, u);
		menu.setItem(7, u);
		menu.setItem(9, u);
		menu.setItem(17, u);
		menu.setItem(18, u);
		menu.setItem(26, u);
		menu.setItem(27, u);
		menu.setItem(35, u);
		menu.setItem(36, u);
		menu.setItem(44, u);
		menu.setItem(45, u);
		menu.setItem(46, u);
		menu.setItem(47, u);
		menu.setItem(48, u);
		menu.setItem(50, u);
		menu.setItem(51, u);
		menu.setItem(52, u);
		menu.setItem(53, u);
		menu.setItem(44, u);
		
		ItemStack l = new ItemStack(Material.OAK_SIGN, 1);
		ItemMeta lM = l.getItemMeta();
		lM.setDisplayName("§c§lBienvenue en §4Role Play");
		lM.setLore(Arrays.asList("§eDiscord : https://discord.tntgun.fr/","§eSite : https://tntgun.fr/","§ee-mail : themsou@tntgun.fr"));
		l.setItemMeta(lM);				
		menu.setItem(0, l);
		
		ItemStack m = new ItemStack(Material.OAK_SIGN, 1);
		ItemMeta mM = m.getItemMeta();
		mM.setDisplayName("§c§lVous pouvez acheter un terrain pour");
		mM.setLore(Arrays.asList("§econstruire votre maison et progrésser","§edans différentes compétences.", "§eSans oublier de vous faire", "embaucher dans une entreprise !"));
		m.setItemMeta(mM);	
		menu.setItem(8, m);
		
		p.openInventory(menu);
		
		
		
		
		
	}

}

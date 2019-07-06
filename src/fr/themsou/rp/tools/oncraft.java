package fr.themsou.rp.tools;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;

import fr.themsou.main.main;

public class oncraft{
	
	
	public void craft(CraftItemEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		
		if(e.getCurrentItem() != null){
			if(e.getCurrentItem().getItemMeta() != null){
				if(e.getCurrentItem().getItemMeta().getDisplayName() != null){
					String name = e.getCurrentItem().getItemMeta().getDisplayName();
					if(p.getGameMode() == GameMode.SURVIVAL){
						if(name.equals("§3§lSuper Pioche")){
							if(!(main.config.getDouble(p.getName() + ".metier.mineur") >= 50.0)){

								e.setCancelled(true);
								p.sendMessage("§cVous devez etre au moins à §450% §cde cette compétence pour avoir accés à ce craft !");
								
							}
						}else if(name.equals("§3§lSuper Houe")){
							if(!(main.config.getDouble(p.getName() + ".metier.fermier") >= 50.0)){
								
								e.setCancelled(true);
								p.sendMessage("§cVous devez etre au moins à §450% §cde cette compétence pour avoir accés à ce craft !");
								
							}
						}else if(name.equals("§3§lSuper Hache")){
							if(!(main.config.getDouble(p.getName() + ".metier.bucheron") >= 50.0)){

								e.setCancelled(true);
								p.sendMessage("§cVous devez etre au moins à §450% §cde cette compétence pour avoir accés à ce craft !");
								
							}
						}else if(name.equals("§3§lBouteille d'XP")){
							if(!(main.config.getDouble(p.getName() + ".metier.enchanteur") >= 50.0)){
								
								e.setCancelled(true);
								p.sendMessage("§cVous devez etre au moins à §450% §cde ce cette compétence pour avoir accés à ce craft !");
								
							}else{
								double xp = new Enchanteur(p).getPC(); if(xp == 0) xp = 1;
								e.getCurrentItem().setAmount((int) ((64.0 / 50.0) * (xp - 50.0)));
							}
						}else if(name.equals("§3§lCage de spawner")){
							if(!(main.config.getDouble(p.getName() + ".metier.chasseur") >= 50.0)){

								e.setCancelled(true);
								p.sendMessage("§cVous devez etre au moins à §450% §cde cette compétence pour avoir accés à ce craft !");
								
							}
						}else if(name.contains("§3§lSpawner")){
							int levels = Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(1));
							
							if(levels == 1){
								if(!(main.config.getDouble(p.getName() + ".metier.chasseur") >= 50.0)){
									e.setCancelled(true);
									p.sendMessage("§cVous devez etre au moins à §450% §cde cette compétence pour avoir accés à ce craft !");
								}
							}else if(levels == 2){
								if(!(main.config.getDouble(p.getName() + ".metier.chasseur") >= 60.0)){
									e.setCancelled(true);
									p.sendMessage("§cVous devez etre au moins à §460% §cde cette compétence pour avoir accés à ce craft !");
								}
							}else if(levels == 3){
								if(!(main.config.getDouble(p.getName() + ".metier.chasseur") >= 70.0)){
									e.setCancelled(true);
									p.sendMessage("§cVous devez etre au moins à §470% §cde cette compétence pour avoir accés à ce craft !");
								}
							}else if(levels == 4){
								if(!(main.config.getDouble(p.getName() + ".metier.chasseur") >= 85.0)){
									e.setCancelled(true);
									p.sendMessage("§cVous devez etre au moins à §485% §cde cette compétence pour avoir accés à ce craft !");
								}
							}else if(levels == 5){
								if(!(main.config.getDouble(p.getName() + ".metier.chasseur") == 100.0)){
									e.setCancelled(true);
									p.sendMessage("§cVous devez etre au moins à §4100% §cde cette compétence pour avoir accés à ce craft !");
								}
							}
							
							
						}
					}
				}
			}
		}
	}
}

package fr.themsou.rp.claim;

import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.themsou.main.main;
import fr.themsou.methodes.Schematics;

public class ClaimAdminUnderCmd {
	

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// NOTE CLAIM //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void noteclaim(Player p, int note) {
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
		String spawn = CSpawns.getSpawnNameWithId(id);
		
		if(id != 0){
			
			String owner = main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",")[0];
			
			int newnote = note + main.config.getInt(owner + ".claim.note");	
			if(newnote > 5) newnote = 5;
			
			main.config.set(owner + ".claim.note", newnote);
			
			p.sendMessage("§bLe claim de §3" + owner + "§b a bien obtenus la note §3" + note + "§b pour un total de §3" + newnote);
			
				
			
			
		}else p.sendMessage("§cceci n'est pas un terrain");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// SET CLAIM OWNER /////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setclaimowner(Player p, String owner) {
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
		String spawn = CSpawns.getSpawnNameWithId(id);
		String type = main.config.getString("claim.list." + spawn + "." + id + ".type");
		String[] owners = main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",");
		
		if(id != 0){
			
			if(type.equals("ins")){
				if(main.config.contains("ent.list." + owner) || owner.equals("l'etat")){
					
					if(!owners[0].equals("l'etat")){
						
						Set<String> section = main.config.getConfigurationSection("").getKeys(false);	
						String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
						String[] item = items.split(",");

						for(int i = 1; i <= section.size(); i++){
							int number = i - 1;
							if(main.config.contains(item[number] + ".claim." + id)){
								main.config.set(item[number] + ".claim." + id, null);
							}
						}
						
						main.config.set("ent.list." + owners[0] + ".claim", main.config.getString("ent.list." + owners[0] + ".claim").replace("," + id, "").replace(id + "", ""));
						
					}
					
					if(!owner.equals("l'etat")){
						
						if(main.config.getString("ent.list." + owner + ".claim").equals("")){
							 main.config.set("ent.list." + owner + ".claim", id);
						}else main.config.set("ent.list." + owner + ".claim", main.config.getString("ent.list." + owner + ".claim") + "," + id);
						main.config.set("claim.list." + spawn + "." + id + ".sell", false);
						
					}
					
					main.config.set("claim.list." + spawn + "." + id + ".owner", owner);
					p.sendMessage("§bLe propriétaire du claim d'id §3" + id + "§b est maintenant §3" + owner);
					
				}else p.sendMessage("§cAucune entreprise trouvée avec le nom §4" + owner);

			}else{
				
				if(main.config.contains(owner) || owner.equals("l'etat")){
					
					Set<String> section = main.config.getConfigurationSection("").getKeys(false);	
					String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
					String[] item = items.split(",");

					for(int i = 1; i <= section.size(); i++){
						int number = i - 1;
						
						if(main.config.contains(item[number] + ".claim." + id)){
							main.config.set(item[number] + ".claim." + id, null);
						}
						
					}
					
					
					main.config.set("claim.list." + spawn + "." + id + ".owner", owner);
					main.config.set(owner + ".claim." + id, id);
					main.config.set("claim.list." + spawn + "." + id + ".sell", false);
					p.sendMessage("§bLe propriétaire du claim d'id §3" + id + "§b est maintenant §3" + owner);
					
					
				}else p.sendMessage("§cAucun joueur trouvé avec le nom §4" + owner);
				
			}
			
		}else p.sendMessage("§cCeci n'est pas un terrain");
		
		
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// DELETE CLAIM ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void deleteclaim(Player p) {
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
		String spawn = CSpawns.getSpawnNameWithId(id);
		String type = main.config.getString("claim.list." + spawn + "." + id + ".type");
		
		if(id != 0){
			
			String[] owners = main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",");
			main.config.set("claim.list." + spawn + "." + id , null);
			
			for(String owner : owners){
				if(main.config.contains(owner + ".claim." + id)) main.config.set(owner + ".claim." + id, null);
			}
			
			if(type.equals("ins")){
				if(main.config.contains("ent.list." + owners[0]) && !owners[0].equals("l'etat")){
					
					main.config.set("ent.list." + owners[0] + ".claim", main.config.getString("ent.list." + owners[0] + ".claim").replace("," + id, "").replace(id + "", ""));
					
				}

			}
			
			p.sendMessage("§BLe claim d'id §3" + id + "§b à bien été supprimé !");
			
		}else p.sendMessage("§cceci n'est pas un terrain");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// SET APP ZONE ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setAppZone(Player p) {
		
		Schematics CSchematics = new Schematics();
		Spawns CSpawns = new Spawns();
		if(CSchematics.getSelection1(p) != null || CSchematics.getSelection2(p) != null){
			
			if(p.getLocation().getWorld() == Bukkit.getWorld("world")){
				
				int x1 = CSchematics.getSelection1(p).getBlockX();
				int z1 = CSchematics.getSelection1(p).getBlockZ();
				
				int x2 = CSchematics.getSelection2(p).getBlockX();
				int z2 = CSchematics.getSelection2(p).getBlockZ();
				String ville = CSpawns.getSpawnNameWithLoc(CSchematics.getSelection1(p));
				
				int minx;
				int maxx;
				int minz;
				int maxz;
				
				if(x1 > x2){
					minx = x2;
					maxx = x1;
				}else{
					minx = x1;
					maxx = x2;
				}if(z1 > z2){
					minz = z2;
					maxz = z1;
				}else{
					minz = z1;
					maxz = z2;
				}
				
				int id = new Random().nextInt(100000);
				p.sendMessage("§bVous venez de créer la zone d'appartements n°§3" + id);
				
				main.config.set("claim.list." + ville + ".app." + id + ".minx", minx);
				main.config.set("claim.list." + ville + ".app." + id + ".maxx", maxx);
				main.config.set("claim.list." + ville + ".app." + id + ".minz", minz);
				main.config.set("claim.list." + ville + ".app." + id + ".maxz", maxz);
				
			}else p.sendMessage("§cVous ne pouvez pas créer de terrain en dehors du RP");
			
		}else p.sendMessage("§cVous devez d'abord selexioner la zone");
		
	}
	
	public void setSpawn(Player p, String spawn, boolean visible){
		
		if(p.getWorld() == Bukkit.getWorld("world") || p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
			
			Schematics CSchematics = new Schematics();
			
			 if(CSchematics.getSelection1(p) != null || CSchematics.getSelection2(p) != null){
				
				Location loc1 = CSchematics.getSelection1(p);
				Location loc2 = CSchematics.getSelection2(p);
				
				new Spawns().setSpawn(p, spawn, visible, p.getLocation(), loc1, loc2);
				
			}else p.sendMessage("§cVous devez d'abord selexioner la zone");
			
		}else p.sendMessage("§cVous ne pouvez pas créer de terrain en dehors du RP");
		
		
	}
	


}

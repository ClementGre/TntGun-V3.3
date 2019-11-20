package fr.themsou.rp.claim;

import java.util.ArrayList;
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
		
		Claim claim = new Claim(p.getLocation());
		if(claim.exist() && claim.getOwner() != null){

			int newnote = note + main.config.getInt(claim.getOwner() + ".claim.note");
			if(newnote > 5) newnote = 5;
			
			main.config.set(claim.getOwner() + ".rp.claim.note", newnote);
			
			p.sendMessage("§bLe claim de §3" + claim.getOwner() + "§b a bien obtenus la note §3" + note + "§b pour un total de §3" + newnote);

		}else p.sendMessage("§cCe terrain n'appartiens à personne");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// SET CLAIM OWNER /////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setclaimowner(Player p, String owner) {

		Claim claim = new Claim(p.getLocation());
		if(claim.exist() && claim.getOwner() != null){

			if(owner.equals("l'etat")){
				claim.setOwner(null);
				claim.setGuests(new ArrayList<>());
				p.sendMessage("§bIl n'y a maintenant plus de propriétaire pour le claim d'id §3" + claim.getId());
			}

			if(claim.isEnt()){
				if(!main.config.contains("ent.list." + owner)){
					p.sendMessage("§cAucune entreprise trouvée avec le nom §4" + owner);
					return;
				}
			}else{
				if(!main.config.contains(owner + ".mdp")){
					p.sendMessage("§cAucun joueur trouvé avec le nom §4" + owner);
					return;
				}
			}

			claim.setOwner(owner);
			p.sendMessage("§bLe propriétaire du claim d'id §3" + claim.getId() + "§b est maintenant §3" + owner);
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// DELETE CLAIM ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void deleteclaim(Player p) {

		Claim claim = new Claim(p.getLocation());
		if(claim.exist() && claim.getOwner() != null){

			claim.setOwner(null);
			claim.setGuests(new ArrayList<>());

			main.config.set("claim.list." + claim.getSpawn() + "." + claim.getId(), null);
			p.sendMessage("§BLe claim d'id §3" + claim.getId() + "§b à bien été supprimé !");
			
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
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// SET SPAWN ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
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

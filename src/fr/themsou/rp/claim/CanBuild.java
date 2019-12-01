package fr.themsou.rp.claim;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.themsou.main.main;
import fr.themsou.nms.title;

public class CanBuild {

	public boolean canBuildMessage(Player p, Location loc){
		Claim claim = new Claim(loc);
		if(!claim.exist()){
			if(new Spawns().isInSpawn(loc)){
				title.sendActionBar(p, "§cÉLOIGNEZ VOUS DU SPAWN POUR POUVOIR CONSTRUIRE !");
				return false;
			}else{
				return true;
			}
		}else{
			if(claim.getOwner() == null){
				title.sendActionBar(p, "§cCE TERRAIN EST §4À VENDRE");
				return false;
			}else if(claim.getOwner().equals(p.getName()) || claim.getGuests().contains(p.getName()) || claim.getEntManagers().contains(p.getName())) {
				return true;
			}else{
				if(claim.getEntSalaries().contains(p.getName())){
					title.sendActionBar(p, "§cVOUS NE POUVEZ PAS FAIRE CETTE ACTION EN TANT QUE SALARIÉ");
				}else if(claim.isSell() && claim.isEnt()){
					title.sendActionBar(p, "§cCE TERRAIN APARTIENS À L'ENTREPRISE §4" + claim.getOwner() + "§c ET EST §4À VENDRE");
				}else if(claim.isSell()){
					title.sendActionBar(p, "§cCE TERRAIN APARTIENS À §4" + claim.getOwner() + "§c ET EST §4À VENDRE");
				}else if(claim.isEnt()) {
					title.sendActionBar(p, "§cCE TERRAIN APARTIENS À L'ENTREPRISE §4" + claim.getOwner());
				}else{
					title.sendActionBar(p, "§cCE TERRAIN APARTIENS À §4" + claim.getOwner());
				}
				return false;
			}
		}
	}
	public boolean canBuildMessagewithEmployees(Player p, Location loc){

		Claim claim = new Claim(loc);
		if(!claim.exist()){
			if(new Spawns().isInSpawn(loc)){
				title.sendActionBar(p, "§cÉLOIGNEZ VOUS DU SPAWN POUR POUVOIR CONSTRUIRE !");
				return false;
			}else{
				return true;
			}
		}else{
			if(claim.getOwner() == null){
				title.sendActionBar(p, "§cCE TERRAIN EST §4À VENDRE");
				return false;
			}else if(claim.getOwner().equals(p.getName()) || claim.getGuests().contains(p.getName()) || claim.getEntOwners().contains(p.getName())){
				return true;
			}else{
				if(claim.isSell() && claim.isEnt()){
					title.sendActionBar(p, "§cCE TERRAIN APARTIENS À L'ENTREPRISE §4" + claim.getPureOwner() + "§c ET EST §4À VENDRE");
				}else if(claim.isSell()){
					title.sendActionBar(p, "§cCE TERRAIN APARTIENS À §4" + claim.getPureOwner() + "§c ET EST §4À VENDRE");
				}else if(claim.isEnt()) {
					title.sendActionBar(p, "§cCE TERRAIN APARTIENS À L'ENTREPRISE §4" + claim.getPureOwner());
				}else{
					title.sendActionBar(p, "§cCE TERRAIN APARTIENS À §4" + claim.getPureOwner());
				}
				return false;
			}
		}
	}
	public String canBuildDebug(Player p, Location loc){
		
		Claim claim = new Claim(loc);
		if(!claim.exist()){
			return "Spawn";
		}else{
			return claim.getPureOwner();
		}
	}
	public boolean canBuild(Player p, Location loc){

		Claim claim = new Claim(loc);
		if(!claim.exist()){
			if(new Spawns().isInSpawn(loc)) return false;
			else return true;

		}else{
			if(claim.getOwner() == null){
				return false;
			}else return claim.getOwner().equals(p.getName()) || claim.getGuests().contains(p.getName()) || claim.getEntOwners().contains(p.getName());

		}
	}

}

package fr.themsou.rp.claim;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.themsou.main.main;
import fr.themsou.nms.title;

public class CanBuild {
	
	public boolean canBuildMessage(Player p, Location loc){
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(loc);
		if(id == 0){
			if(CSpawns.isInSpawn(loc)){
				title.sendActionBar(p, "§cÉLOIGNEZ VOUS DU SPAWN POUR POUVOIR CONSTRUIRE !");
				return false;
			}else{
				return true;
			}
		}else{
			if(!main.config.contains(p.getName() + ".claim." + id)){
				String owners = main.config.getString("claim.list." + CSpawns.getSpawnNameWithId(id) + "." + id + ".owner");
				String ownersDisplay = owners.replaceAll(",", " §cet§4 ");
				boolean isSell = main.config.getBoolean("claim.list." + CSpawns.getSpawnNameWithId(id) + "." + id + ".sell");
					
				if(owners.equals("l'etat")){
					title.sendActionBar(p, "§cCE TERRAIN EST §4À VENDRE");
				}else if(main.config.getString("claim.list." + CSpawns.getSpawnNameWithId(id) + "." + id + ".type").equals("ins") && !owners.split(",")[0].equals("l'etat")){
					String ent = owners.split(",")[0];
					
					if(main.config.contains(p.getName() + ".rp.ent.name")){
						if(main.config.getString(p.getName() + ".rp.ent.name").equals(ent)){
							if(main.config.getInt(p.getName() + ".rp.ent.role") >= 1){
								return true;
							}else{
								title.sendActionBar(p, "§cVOUS NE POUVEZ PAS FAIRE CETTE ACTION EN TANT QUE SALARIÉ");
							}
						}
					}
					
					title.sendActionBar(p, "§cCE TERRAIN APARTIENS À L'ENTREPRISE §4" + ent);
					
				}else{
					if(isSell){
						title.sendActionBar(p, "§cCE TERRAIN APARTIENS À §4" + ownersDisplay + "§c ET EST §4À VENDRE");
					}else{
						title.sendActionBar(p, "§cCE TERRAIN APARTIENS À §4" + ownersDisplay);
					}
				}
				return false;
				
			}else{
				return true;
			}
		}
	}
	public boolean canBuildMessagewithEmployees(Player p, Location loc){
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(loc);
		if(id == 0){
			if(CSpawns.isInSpawn(loc)){
				title.sendActionBar(p, "§cÉLOIGNEZ VOUS DU SPAWN POUR POUVOIR CONSTRUIRE !");
				return false;
			}else{
				return true;
			}
		}else{
			if(!main.config.contains(p.getName() + ".claim." + id)){
				String owners = main.config.getString("claim.list." + CSpawns.getSpawnNameWithId(id) + "." + id + ".owner");
				String ownersDisplay = owners.replaceAll(",", " §cet§4 ");
				boolean isSell = main.config.getBoolean("claim.list." + CSpawns.getSpawnNameWithId(id) + "." + id + ".sell");
					
				if(owners.equals("l'etat")){
					title.sendActionBar(p, "§cCE TERRAIN EST §4À VENDRE");
				}else if(main.config.getString("claim.list." + CSpawns.getSpawnNameWithId(id) + "." + id + ".type").equals("ins") && !owners.split(",")[0].equals("l'etat")){
					String ent = owners.split(",")[0];
					
					if(main.config.contains(p.getName() + ".rp.ent.name")){
						if(main.config.getString(p.getName() + ".rp.ent.name").equals(ent)){
							title.sendActionBar(p, "§c");
							return true;
						}
					}
					if(isSell){
						title.sendActionBar(p, "§cCE TERRAIN APARTIENS À L'ENTREPRISE §4" + ownersDisplay + "§c ET EST §4À VENDRE");
					}else{
						title.sendActionBar(p, "§cCE TERRAIN APARTIENS À L'ENTREPRISE §4" + ownersDisplay);
					}
					
				}else{
					if(isSell){
						title.sendActionBar(p, "§cCE TERRAIN APARTIENS À §4" + ownersDisplay + "§c ET EST §4À VENDRE");
					}else{
						title.sendActionBar(p, "§cCE TERRAIN APARTIENS À §4" + ownersDisplay);
					}
				}
				return false;
				
			}else{
				return true;
			}
		}
	}
	public String canBuildDebug(Player p, Location loc){
		
		GetZoneId CGetZoneId = new GetZoneId();
		
		int id = CGetZoneId.getIdOfPlayerZone(loc);
		if(id == 0){
			
			return "Spawn";

		}else{
			
			Spawns CSpawns = new Spawns();
			String owners = main.config.getString("claim.list." + CSpawns.getSpawnNameWithId(id) + "." + id + ".owner");
			return owners;
			
		}
	}
	public boolean canBuild(Player p, Location loc){
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(loc);
		if(id == 0){
			if(CSpawns.isInSpawn(loc)){
				
				return false;
			}else{
				return true;
			}
		}else{
			if(!main.config.contains(p.getName() + ".claim." + id)){
				String owners = main.config.getString("claim.list." + CSpawns.getSpawnNameWithId(id) + "." + id + ".owner");
					
				if(main.config.getString("claim.list." + CSpawns.getSpawnNameWithId(id) + "." + id + ".type").equals("ins") && !owners.split(",")[0].equals("l'etat")){
					String ent = owners.split(",")[0];
					
					if(main.config.contains(p.getName() + ".rp.ent.name")){
						if(main.config.getString(p.getName() + ".rp.ent.name").equals(ent)){
							if(main.config.getInt(p.getName() + ".rp.ent.role") >= 1){
								title.sendActionBar(p, "§c");
								return true;
							}
						}
					}
					return false;
					
				}else{
					return false;
				}
			}else{
				return true;
			}
		}
	}

}

package fr.themsou.rp.claim;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import fr.themsou.main.main;
import fr.themsou.nms.message;

public class ClaimUserUnderCmd {
	

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// SELL CLAIM //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public void sellPlayerClaim(Player p, int price){
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
		String spawn = CSpawns.getSpawnNameWithId(id);
			
		if(id != 0){
			String[] owners = main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",");
			String type = main.config.getString("claim.list." + spawn + "." + id + ".type");
			if((!type.equals("ins") && owners[0].equals(p.getName())) || (type.equals("ins") && owners[0].equals(main.config.getString(p.getName() + ".rp.ent.name")) && main.config.getInt(p.getName() + ".rp.ent.role") == 2)){
					
				if(price == -1) price = main.config.getInt("claim.list." + spawn + "." + id + ".defprice");
				
				Location loc1 = new Location(Bukkit.getWorld("world"), main.config.getInt("claim.list." + spawn + "." + id + ".x1"), main.config.getInt("claim.list." + spawn + "." + id + ".y1"), main.config.getInt("claim.list." + spawn + "." + id + ".z1"));
				Location loc2 = new Location(Bukkit.getWorld("world"), main.config.getInt("claim.list." + spawn + "." + id + ".x2"), main.config.getInt("claim.list." + spawn + "." + id + ".y2"), main.config.getInt("claim.list." + spawn + "." + id + ".z2"));
				int CalculSuperficie = new CalculSuperficie().calculSuperficieOfZone(loc1, loc2);
				main.config.set("claim.list." + spawn + "." + id + ".price", price);
				main.config.set("claim.list." + spawn + "." + id + ".sell", true);
				
				if(type.equals("claim")) type = "Claim libre";
				if(type.equals("app")) type = "Appartement";
				if(type.equals("agr")) type = "Agricolle";
				if(type.equals("ins")) type = "Industrie";
				p.getLocation().getBlock().setType(Material.OAK_SIGN);
				Sign sign = (Sign) p.getLocation().getBlock().getState();
				sign.setLine(0, "§b[Acheter]");
				sign.setLine(1, "§4" + price + " €");
				sign.setLine(2, "§9" + type);
				if(loc1.getBlockY() == 0 || type.equals("Agricolle")){
					sign.setLine(3, "§9" + CalculSuperficie + "m² " + (loc2.getBlockX() - loc1.getBlockX() + 1) + "×" + (loc2.getBlockZ() - loc1.getBlockZ() + 1));
				}else{
					sign.setLine(3, "§9" + CalculSuperficie + "m² " + (loc2.getBlockX() - loc1.getBlockX() + 1) + "×" + (loc2.getBlockZ() - loc1.getBlockZ() + 1) + "×" + (loc2.getBlockY() - loc1.getBlockY() + 1));
				}
				
				org.bukkit.material.Sign signdata = (org.bukkit.material.Sign) sign.getBlock().getState().getData();
				signdata.setFacingDirection(getDirection(p));
				sign.setData(signdata);
				
				sign.update();
				
				p.sendMessage("§3Votre terrain est maintenant en vente !");
					
			}else p.sendMessage("§cCe terrain ne vous apartiens pas");
			
		}else p.sendMessage("§cCe terrain ne vous apartiens pas");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// INVITE PLAYER ///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void invitePlayer(Player p, String player) {
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
		String spawn = CSpawns.getSpawnNameWithId(id);
		
		if(id != 0){
			String[] owners = main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",");
			String type = main.config.getString("claim.list." + spawn + "." + id + ".type");
			if((!type.equals("ins") && owners[0].equals(p.getName())) || (type.equals("ins") && owners[0].equals(main.config.getString(p.getName() + ".rp.ent.name")) && main.config.getInt(p.getName() + ".rp.ent.role") == 2)){
				
				if(Bukkit.getPlayerExact(player) != null){
					Player p2 = Bukkit.getPlayerExact(player);
					if(!main.config.getString("claim.list." + spawn + "." + id + ".owner").contains(player)){
						
						main.config.set(player + ".claimjoin", id);
						message.sendNmsMessageCmd(p2, "§3" + p.getDisplayName() + "§b vous invite dans son claim §2\u2714 §bou §4\u2716", "§6Cliquez pour accepter", "/claim join");
						p.sendMessage("§bUne invitation a été envoyé à §3" + player);
						
					}else p.sendMessage("§cCe joueur fais déjà partit du claim");
				}else p.sendMessage("§cJoueur introuvable");
				
				
			}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
			
		}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// QUIT CLAIM //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void quitclaim(Player p) {
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
		String spawn = CSpawns.getSpawnNameWithId(id);
		
		if(id != 0){
			if(main.config.contains(p.getName() + ".claim." + id)){
				
				String owner = main.config.getString("claim.list." + spawn + "." + id + ".owner");
				String[] owners = main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",");
				
				if(!owners[0].equals(p.getName())){
					
					if(owner.contains(p.getName())){
						
						main.config.set("claim.list." + spawn + "." + id + ".owner", owner.replace("," + p.getName(), ""));
						main.config.set(p.getName() + ".claim." + id, null);
						
						p.sendMessage("§bVous venez de quitter ce claim");
						
					}else p.sendMessage("§cVous ne faites pas partie de ce claim");
					
				}else p.sendMessage("§cVous ne pouvez pas quitter votre propre terrain");
				
			}else p.sendMessage("§cVous ne faites pas partie de ce claim");
			
		}else p.sendMessage("§cVous ne faites pas partie de ce claim");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// KICK CLAIM //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void kickclaim(Player p) {
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
		String spawn = CSpawns.getSpawnNameWithId(id);
		
		if(id != 0){
			String[] owners = main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",");
			
			if(main.config.getString("claim.list." + spawn + "." + id + ".type").equals("ins")){
				
				if(owners[0].equals(main.config.getString(p.getName() + ".rp.ent.name")) && main.config.getInt(p.getName() + ".rp.ent.role") == 2){
					
					main.config.set("claim.list." + spawn + "." + id + ".owner", main.config.getString(p.getName() + ".rp.ent.name"));
					p.sendMessage("§bLes invitées ont bien été explulsées");
					
					for(String owner : owners){
						
						if(!owner.equals(main.config.getString(p.getName() + ".rp.ent.name"))){
							main.config.set(owner + ".claim." + id, null);
						}
					}
					
				}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
				
			}else{
				
				if(owners[0].equals(p.getName())){
					
					main.config.set("claim.list." + spawn + "." + id + ".owner", p.getName());
					p.sendMessage("§bLes invitées ont bien été explulsées");
					
					for(String owner : owners){
						if(!owner.equals(p.getName())){
							main.config.set(owner + ".claim." + id, null);
						}
					}
					
				}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
			}
			
			
			
		}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
		
	}
	public void joinclaim(Player p) {
		
		if(main.config.contains(p.getName() + ".claimjoin")){
			Spawns CSpawns = new Spawns();
			int id = main.config.getInt(p.getName() + ".claimjoin");
			String ville = CSpawns.getSpawnNameWithId(id);
			
			if(!main.config.contains(p.getName() + ".claim")){
				
				main.config.set(p.getName() + ".claimjoin", null);
				main.config.set("claim.list." + ville + "." + id + ".owner", main.config.getString("claim.list." + ville + "." + id + ".owner") + "," + p.getName());
				main.config.set(p.getName() + ".claim." + id, id);
				p.sendMessage("§bVous venez de rejoindre un claim");
				
			}else{
				
				main.config.set("claim.list." + ville + "." + id + ".owner", main.config.getString("claim.list." + ville + "." + id + ".owner") + "," + p.getName());
				main.config.set(p.getName() + ".claim." + id, id);
				p.sendMessage("§bVous venez de rejoindre un claim");
					
			}
		}else p.sendMessage("§cVous n'avez reçu aucune invitation");
		
		main.config.set(p.getName() + ".claimjoin", null);
		
	}
	public void sellCountry(Player p) {
		
		
		int id = new GetZoneId().getIdOfPlayerZone(p.getLocation());
		String spawn = new Spawns().getSpawnNameWithId(id);
		int price = (int) (main.config.getInt("claim.list." + spawn + "." + id + ".defprice") * 0.75);
		String type = main.config.getString("claim.list." + spawn + "." + id + ".type");
		String[] owners = main.config.getString("claim.list." + spawn + "." + id + ".owner").split(",");
		
		if(type.equals("ins")){
				
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
					
				
			main.config.set("claim.list." + spawn + "." + id + ".owner", "l'etat");
			main.config.set("claim.list." + spawn + "." + id + ".sell", true);
			main.config.set("claim.list." + spawn + "." + id + ".needsetup", true);
			p.sendMessage("§bVous venez de vendre votre claim pour §3" + price + "€");
			
			main.economy.depositPlayer(p, price);
				

		}else{
			
			Set<String> section = main.config.getConfigurationSection("").getKeys(false);	
			String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
			String[] item = items.split(",");

			for(int i = 1; i <= section.size(); i++){
				int number = i - 1;
				
				if(main.config.contains(item[number] + ".claim." + id)){
					main.config.set(item[number] + ".claim." + id, null);
				}
				
			}
			
			
			main.config.set("claim.list." + spawn + "." + id + ".owner", "l'etat");
			main.config.set("claim.list." + spawn + "." + id + ".sell", true);
			main.config.set("claim.list." + spawn + "." + id + ".needsetup", true);
			
			main.economy.depositPlayer(p, price);
			
			p.sendMessage("§bVous venez de vendre votre claim pour §3" + price + "€");
			
		}
	}
	
	private BlockFace getDirection(Player player) {
    	
    	double rot = player.getLocation().getYaw();
    	
    	if(rot <= 22.5) return BlockFace.SOUTH;
    	if(rot <= 67.5) return BlockFace.SOUTH_WEST;
        else if (rot <= 112.5) return BlockFace.WEST;
    	if(rot <= 157.5) return BlockFace.NORTH_WEST;
        else if (rot <= 202.5) return BlockFace.NORTH;
    	if(rot <= 247.5) return BlockFace.NORTH_EAST;
        else if (rot <= 292.5) return BlockFace.EAST;
    	if(rot <= 337.5) return BlockFace.SOUTH_EAST;
        else if (rot <= 360) return BlockFace.SOUTH;
        
        else return null;
        
    }

}

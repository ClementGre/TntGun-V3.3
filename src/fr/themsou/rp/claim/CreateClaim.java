package fr.themsou.rp.claim;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import fr.themsou.main.main;
import fr.themsou.methodes.Schematics;

public class CreateClaim {
	
	private CalculSuperficie CCalculSuperficie = new CalculSuperficie();
	private GetZoneId CGetZoneId = new GetZoneId();
	private Schematics CSchematics = new Schematics();
	private Spawns CSpawns = new Spawns();
	
	public void set2DClaim(Player p, int prix, String type, boolean is2d){
		if(CSchematics.getSelection1(p) != null || CSchematics.getSelection2(p) != null){
			
			if(p.getLocation().getWorld() == Bukkit.getWorld("world")){
				
				Location loc1 = CSchematics.getSelection1(p);
				Location loc2 = CSchematics.getSelection2(p);
				String ville = CSpawns.getSpawnNameWithLoc(loc1);
				
				int id = new Random().nextInt(1000000);
				if(!is2d){
					main.config.set("claim.list." + ville + "." + id + ".y1", loc1.getBlockY());
					main.config.set("claim.list." + ville + "." + id + ".y2", loc2.getBlockY());
				}
				
				main.config.set("claim.list." + ville + "." + id + ".owner", "l'etat");
				main.config.set("claim.list." + ville + "." + id + ".x1", loc1.getBlockX());
				main.config.set("claim.list." + ville + "." + id + ".x2", loc2.getBlockX());
				main.config.set("claim.list." + ville + "." + id + ".z1", loc1.getBlockZ());
				main.config.set("claim.list." + ville + "." + id + ".z2", loc2.getBlockZ());
				main.config.set("claim.list." + ville + "." + id + ".type", type);
				
				p.sendMessage("§bVous venez de créer la zone n°§3" + id);
				setClaimSign(p, prix);
				
			}else p.sendMessage("§cVous ne pouvez pas créer de terrain en dehors du RP");
			
		}else p.sendMessage("§cVous devez d'abord selexioner la zone");
			
	}
	@SuppressWarnings("deprecation")
	public void setClaimSign(Player p, int prix){
		
		int id = CGetZoneId.getIdOfPlayerZone(p.getLocation().getBlock().getLocation());
		if(id == 0){ p.sendMessage("§cVous devez être dans le claim pour pouvoir poser sa pancarte"); return; }
		String ville = CSpawns.getSpawnNameWithLoc(p.getLocation());
		
		Location loc1 = new Location(Bukkit.getWorld("world"), main.config.getInt("claim.list." + ville + "." + id + ".x1"), main.config.getInt("claim.list." + ville + "." + id + ".y1"), main.config.getInt("claim.list." + ville + "." + id + ".z1"));
		Location loc2 = new Location(Bukkit.getWorld("world"), main.config.getInt("claim.list." + ville + "." + id + ".x2"), main.config.getInt("claim.list." + ville + "." + id + ".y2"), main.config.getInt("claim.list." + ville + "." + id + ".z2"));
		int CalculSuperficie = CCalculSuperficie.calculSuperficieOfZone(loc1, loc2);
		String type = main.config.getString("claim.list." + ville + "." + id + ".type");
		
		if(prix == -1){
			if(type.equals("agr")) prix = CalculSuperficie * 10;
			else if(loc1.getBlockY() != 0) prix = CalculSuperficie * (loc2.getBlockY() - loc1.getBlockY() + 1) * 5;
			else prix = CalculSuperficie * 40;
		}
		
		main.config.set("claim.list." + ville + "." + id + ".defprice", prix);
		main.config.set("claim.list." + ville + "." + id + ".price", prix);
		main.config.set("claim.list." + ville + "." + id + ".sell", true);
		
		if(type.equals("claim")) type = "Claim libre";
		if(type.equals("app")) type = "Appartement";
		if(type.equals("agr")) type = "Agricolle";
		if(type.equals("ins")) type = "Industrie";
		p.getLocation().getBlock().setType(Material.OAK_SIGN);
		Sign sign = (Sign) p.getLocation().getBlock().getState();
		sign.setLine(0, "§b[Acheter]");
		sign.setLine(1, "§4" + prix + " €");
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
		
		p.sendMessage("§bVous venez de poser la pancarte d'achat du claim d'id §3" + id);
		
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

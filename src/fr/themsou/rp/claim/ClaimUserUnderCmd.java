package fr.themsou.rp.claim;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.themsou.methodes.PlayerInfo;
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

		Claim claim = new Claim(p.getLocation());
		if(claim.exist() && claim.getOwner() != null){

			if(claim.getOwner().equals(p.getName()) || claim.getEntManagers().contains(p.getName())){
					
				if(price == -1) price = claim.getDefPrice();
				int CalculSuperficie = new CalculSuperficie().calculSuperficieOfZone(claim.getFirstLoc(), claim.getSecondLoc());
				claim.setPrice(price);
				claim.setSell(true);

				p.getLocation().getBlock().setType(Material.OAK_SIGN);
				Sign sign = (Sign) p.getLocation().getBlock().getState();
				sign.setLine(0, "§b[Acheter]");
				sign.setLine(1, "§4" + price + " €");
				sign.setLine(2, "§9" + claim.getType().toUserString());

				if(!claim.is3D() || claim.getType().equals(ClaimType.FIELD)){
					sign.setLine(3, "§9" + CalculSuperficie + "m² " + (claim.getX2() - claim.getX1() + 1) + "×" + (claim.getZ2() - claim.getZ1() + 1));
				}else{
					sign.setLine(3, "§9" + CalculSuperficie + "m² " + (claim.getX2() - claim.getX1() + 1) + "×" + (claim.getZ2() - claim.getZ1() + 1) + "×" + (claim.getY2() - claim.getY1() + 1));
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
	public void invitePlayer(Player p, String guest) {

		Claim claim = new Claim(p.getLocation());
		if(claim.exist() && claim.getOwner() != null){
			if(claim.getOwner().equals(p.getName()) || claim.getEntManagers().contains(p.getName())){

				Player p2 = Bukkit.getPlayerExact(guest);
				if(p2 != null){
					if(!claim.getGuests().contains(guest)){

						main.playersInfos.get(p2).setClaimToJoin(claim.getId());
						message.sendNmsMessageCmd(p2, "§3" + p.getDisplayName() + "§b vous invite dans son claim §2\u2714 §bou §4\u2716", "§6Cliquez pour accepter", "/claim join");
						p.sendMessage("§bUne invitation a été envoyé à §3" + guest);
						
					}else p.sendMessage("§cCe joueur fais déjà partit du claim");
				}else p.sendMessage("§cJoueur introuvable");
			}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
		}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// QUIT CLAIM //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void quitclaim(Player p) {

		Claim claim = new Claim(p.getLocation());
		if(claim.exist() && claim.getOwner() != null){
			if(Claim.hasPlayerClaim(p.getName(), claim.getId())){

				if(claim.getGuests().contains(p.getName())){

					List<String> guests = claim.getGuests();
					guests.remove(p.getName());
					claim.setGuests(guests);

					p.sendMessage("§bVous venez de quitter ce claim");

				}else p.sendMessage("§cVous n'avez pas été invités dans ce terrain'");
			}else p.sendMessage("§cVous ne faites pas partie de ce terrain");
		}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// KICK CLAIM //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void kickclaim(Player p) {

		Claim claim = new Claim(p.getLocation());
		if(claim.exist() && claim.getOwner() != null){

			if(claim.getOwner().equals(p.getName()) || claim.getEntManagers().contains(p.getName())){

				claim.setGuests(new ArrayList<>());
				p.sendMessage("§bLes invitées ont bien été explulsées");

			}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
		}else p.sendMessage("§cvous n'etes pas le propriétaire de ce terrain");
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// JOIN CLAIM //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void joinclaim(Player p) {

		PlayerInfo pInfo = main.playersInfos.get(p);

		Claim claim = new Claim(pInfo.getClaimToJoin());
		if(claim.exist() && claim.getOwner() != null){

			pInfo.setClaimToJoin(0);

			List<String> guests = claim.getGuests();
			guests.add(p.getName());
			claim.setGuests(guests);

			p.sendMessage("§bVous venez de rejoindre un claim");

		}else p.sendMessage("§cVous n'avez reçu aucune invitation");

	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// SELL COUNTRY ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void sellCountry(Player p) {

		Claim claim = new Claim(p.getLocation());
		if(claim.exist() && claim.getOwner() != null){

			int sellPrice = (int) (claim.getDefPrice() * 0.75);

			claim.setOwner(null);
			claim.setGuests(new ArrayList<>());
			claim.setSell(true);
			claim.setNeedSetup(false);

			p.sendMessage("§bVous venez de vendre votre claim pour §3" + sellPrice + "€");
			main.economy.depositPlayer(p, sellPrice);
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// OTHER ///////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
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

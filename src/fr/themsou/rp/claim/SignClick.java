package fr.themsou.rp.claim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.themsou.main.main;

public class SignClick {
	
	@SuppressWarnings("deprecation")
	public void signclick(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		if(e.getClickedBlock() != null){
			if(e.getClickedBlock().getState() != null){
				BlockState block = e.getClickedBlock().getState();
				if(block instanceof Sign){
					Sign sign = (Sign)block;
					
					new fr.themsou.rp.ent.Sign().clickSign(p, block);
					
					if(sign.getLine(0).equals("§b[Acheter]")){
						if(p.getGameMode() == GameMode.SURVIVAL){
							Spawns CSpawns = new Spawns();

							int prix = Integer.parseInt(sign.getLine(1).replace(" €", "").replace("§4", ""));
							int id = new GetZoneId().getIdOfPlayerZone(sign.getBlock().getLocation());

							Claim claim = new Claim(id);
							if(claim.exist()){
								if(claim.isEnt()){
									String lastEnt = claim.getPureOwner();
									if(lastEnt == null) lastEnt = "";

									if(main.config.contains(p.getName() + ".rp.ent.name")){
										if(main.config.getInt(p.getName() + ".rp.ent.role") == 2){
											String pEnt = main.config.getString(p.getName() + ".rp.ent.name");
											if(!lastEnt.equals(pEnt)){
												if(main.config.getInt("ent.list." + pEnt + ".money") >= prix){

													if(!lastEnt.equals("")){
														main.config.set("ent.list." + lastEnt + ".money", main.config.getInt("ent.list." + lastEnt + ".money") + prix);
													}
													main.config.set("ent.list." + pEnt + ".money", main.config.getInt("ent.list." + pEnt + ".money") - prix);

													claim.setOwner(pEnt);
													p.sendMessage("§bVous venez d'acheter un terrain à §3" + prix + " €§b d'id §3" + id);

													sign.getBlock().setType(Material.AIR);
													claim.setSell(false);

												}else p.sendMessage("§cVous devez avoir §4" + prix + "€ §cpour acheter ce terain");
											}else p.sendMessage("§cCe terrain appartient déjà a votre entreprise.");
										}else p.sendMessage("§cVous devez être PDG de votre entreprise pour pouvoir acheter des terrains.");
									}else p.sendMessage("§cVous ne pouvez pas acheter de terrain d'industrie car vous n'êtes pas dans une entreprise.");

								}else if(claim.getOwner() == null ? true : !claim.getOwner().equals(p.getName())){

									if(main.economy.getBalance(p) >= prix){

										// CLAIMS LIMIT

										List<Integer> claims = Claim.getPlayerClaims(p.getName());
										claims = claims.stream().filter(playerClaim -> new Claim(playerClaim).getOwner().equals(p.getName())).collect(Collectors.toList());

										if(claim.getType().equals(ClaimType.FREE)){
											int freeClaims = claims.stream().filter(playerClaim -> new Claim(playerClaim).getType().equals(ClaimType.FREE)).collect(Collectors.toList()).size();
											if(freeClaims >= 3){ p.sendMessage("§bVous ne pouvez pas avoir plus de 3 §3Terrains libres§b, faites §3/claim sell §bpour vendre votre terrain"); return; }

										}else if(claim.getType().equals(ClaimType.APARTMENT)){
											int apartmentClaims = claims.stream().filter(playerClaim -> new Claim(playerClaim).getType().equals(ClaimType.APARTMENT)).collect(Collectors.toList()).size();
											if(apartmentClaims >= 3){ p.sendMessage("§bVous ne pouvez pas avoir plus de 3 §3Appartements§b, faites §3/claim sell §bpour vendre votre terrain"); return; }

										}else if(claim.getType().equals(ClaimType.FIELD)){
											int fieldClaims = claims.stream().filter(playerClaim -> new Claim(playerClaim).getType().equals(ClaimType.FIELD)).collect(Collectors.toList()).size();
											if(fieldClaims >= 3){ p.sendMessage("§bVous ne pouvez pas avoir plus de 3 terrains d'§3Agriculture§b, faites §3/claim sell §bpour vendre votre terrain"); return; }
										}

										// SELLING
										if(claim.getOwner() != null){
											main.economy.depositPlayer(claim.getOwner(), prix);
										}
										main.economy.withdrawPlayer(p, prix);

										claim.setOwner(p.getName());
										p.sendMessage("§bVous venez d'acheter un terrain à §3" + prix + " €§b d'id §3" + id);

										sign.getBlock().setType(Material.AIR);
										claim.setSell(false);

									}else{
										p.sendMessage("§cVous devez avoir §4" + prix + "€ §cpour acheter ce terain");
									}

								}else p.sendMessage("§cCe terrain vous apartiens déjà !");
							}else p.sendMessage("§cLe terrain a été supprimé entre temps, vous ne pouvez plus l'acheter");
						}else p.sendMessage("§cVeuillez acheter ce terrain en mode survie");
					}
					
				}
			}
		}
	}

}

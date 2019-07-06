package fr.themsou.rp.claim;

import java.util.Set;

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
							
							String ville = CSpawns.getSpawnNameWithId(id);
							String[] owners = main.config.getString("claim.list." + ville + "." + id + ".owner").split(",");
							String type = main.config.getString("claim.list." + ville + "." + id + ".type");
							
							if(type.equals("ins")){
								
								String ent = owners[0];
								
								if(main.config.contains(p.getName() + ".rp.ent.name")){
									if(main.config.getInt(p.getName() + ".rp.ent.role") == 2){
										String pEnt = main.config.getString(p.getName() + ".rp.ent.name");
										if(!ent.equals(pEnt)){
											if(main.config.getInt("ent.list." + pEnt + ".money") >= prix){

												if(!ent.equals("l'etat")){
													Set<String> section = main.config.getConfigurationSection("").getKeys(false);	
													String items = section.toString().replace("[", "").replace("]", "").replace(" ", "");
													String[] item = items.split(",");

													for(int i = 1; i <= section.size(); i++){
														int number = i - 1;
														if(main.config.contains(item[number] + ".claim." + id)){
															main.config.set(item[number] + ".claim." + id, null);
														}
													}
													
													main.config.set("ent.list." + ent + ".money", main.config.getInt("ent.list." + ent + ".money") + prix);
													main.config.set("ent.list." + ent + ".claim", main.config.getString("ent.list." + ent + ".claim").replace("," + id, "").replace(id + "", ""));
														
												}
												
												main.config.set("ent.list." + pEnt + ".money", main.config.getInt("ent.list." + pEnt + ".money") - prix);
												main.config.set("claim.list." + ville + "." + id + ".owner", pEnt);
												
												if(main.config.getString("ent.list." + pEnt + ".claim").equals("")){
													main.config.set("ent.list." + pEnt + ".claim", id);
												}else main.config.set("ent.list." + pEnt + ".claim", main.config.getString("ent.list." + pEnt + ".claim") + "," + id);
													
												p.sendMessage("§bVous venez d'acheter un terrain à §3" + prix + " €§b d'id §3" + id);
												
												sign.getBlock().setType(Material.AIR);
												main.config.set("claim.list." + ville + "." + id + ".sell", false);
													
												
											}else p.sendMessage("§cVous devez avoir §4" + prix + "€ §cpour acheter ce terain");
										}else p.sendMessage("§cCe terrain appartient déjà a votre entreprise.");
									}else p.sendMessage("§cVous devez être PDG de votre entreprise pour pouvoir acheter des terrains.");
								}else p.sendMessage("§cVous ne pouvez pas acheder de terrain d'industrie car vous n'êtes pas dans une entreprise.");
								
							}else if(!owners[0].equals(p.getName())){
								if(main.economy.getBalance(p) >= prix){
									if(!main.config.contains(p.getName() + ".claim")){
										for(String players : main.config.getConfigurationSection("").getKeys(false)){
											if(main.config.contains(players + ".claim." + id)){
												main.config.set(players + ".claim." + id, null);
											}
										}
										p.sendMessage("§bVous venez d'acheter un terrain à §3" + prix + " €§b d'id §3" + id);
										main.economy.depositPlayer(owners[0], prix);
										main.economy.withdrawPlayer(p, prix);
										main.config.set("claim.list." + ville + "." + id + ".owner", p.getName());
										main.config.set(p.getName() + ".claim." + id, id);
										
										sign.getBlock().setType(Material.AIR);
										main.config.set("claim.list." + ville + "." + id + ".sell", false);
										
									}else{
										int app = 0; int claim = 0; int agr = 0;
										
										for(String idsString : main.config.getConfigurationSection(p.getName() + ".claim").getKeys(false)){
											
											if(!idsString.equalsIgnoreCase("note")){
												
												int ids = Integer.parseInt(idsString);
												String spawn = CSpawns.getSpawnNameWithId(ids);
												
												if(main.config.getString("claim.list." + spawn + "." + ids + ".owner").split(",")[0].equals(p.getName())){
													if(main.config.getString("claim.list." + spawn + "." + ids + ".type").equalsIgnoreCase("agr")) agr ++;
													else if(main.config.getString("claim.list." + spawn + "." + ids + ".type").equalsIgnoreCase("app")) app ++;
													else if(main.config.getString("claim.list." + spawn + "." + ids + ".type").equalsIgnoreCase("claim")) claim ++;
												}
											}
										}
										
										if(type.equals("agr")){
											if(agr >= 3){ p.sendMessage("§bVous ne pouvez pas avoir plus de 3 terrains d'§3Agriculture§b, faites §3/claim sell §bpour vendre votre terrain"); return; }
										}else if(type.equals("app")){
											if(app >= 3){ p.sendMessage("§bVous ne pouvez pas avoir plus de 3 §3Appartements§b, faites §3/claim sell §bpour vendre votre terrain"); return; }
										}else if(type.equals("claim")){
											if(claim >= 3){ p.sendMessage("§bVous ne pouvez pas avoir plus de 3 §3Terrain libres§b, faites §3/claim sell §bpour vendre votre terrain"); return; }
										}
										
										for(String players : main.config.getConfigurationSection("").getKeys(false)){
											if(main.config.contains(players + ".claim." + id)){
												main.config.set(players + ".claim." + id, null);
											}
										}
										
										p.sendMessage("§bVous venez d'acheter un terrain à §3" + prix + " €§b d'id §3" + id);
										main.economy.depositPlayer(owners[0], prix);
										main.economy.withdrawPlayer(p, prix);
										main.config.set("claim.list." + ville + "." + id + ".owner", p.getName());
										main.config.set(p.getName() + ".claim." + id, id);
											
										sign.getBlock().setType(Material.AIR);
										main.config.set("claim.list." + ville + "." + id + ".sell", false);
										
									}
								}else{
									p.sendMessage("§cVous devez avoir §4" + prix + "€ §cpour acheter ce terain");
								}
								
							}else p.sendMessage("§cCe terrain vous apartiens déjà !");
						}else p.sendMessage("§cVeuillez acheter ce terrain en mode survie");
					}
					
				}
			}
		}
	}

}

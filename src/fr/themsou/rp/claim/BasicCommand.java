package fr.themsou.rp.claim;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.themsou.main.main;
import fr.themsou.nms.message;

public class BasicCommand {
	
	Player p;
	
	public BasicCommand(Player p){
		this.p = p;
	}
	
	public void command(String[] args){
		
		CreateClaim CCreateClaim = new CreateClaim();
		UserCommand CUserCommand = new UserCommand();
		AdminCommand CAdminCommand = new AdminCommand();
		GetZoneId CGetZoneId = new GetZoneId();
		
		if(args.length == 0){
			help();
			return;
		}
		
		if(p.isOp() && p.getGameMode() == GameMode.CREATIVE){
			if(args[0].equalsIgnoreCase("create")){
				if(args.length == 4){
					if(args[2].equalsIgnoreCase("agr") || args[2].equalsIgnoreCase("app") || args[2].equalsIgnoreCase("claim") || args[2].equalsIgnoreCase("ins")){
						
						int prix = -1;
						
						try{
							prix = Integer.parseInt(args[3]);
						}catch(NumberFormatException e){}
							
						
						if(args[1].equalsIgnoreCase("3d")){
							CCreateClaim.set2DClaim(p, prix, args[2], false);
						}else if(args[1].equalsIgnoreCase("2d")){
							CCreateClaim.set2DClaim(p, prix, args[2], true);
						}else p.sendMessage("§c/claim create <2d/3d> <agr/app/claim/ins> <prix/auto>");
					
					}else p.sendMessage("§c/claim create " + args[1] + " <agr/app/claim/ins> <prix/auto>");
				}else p.sendMessage("§c/claim create <2d/3d> <agr/app/claim/ins> <prix/auto>");
				
			}
			else if(args[0].equalsIgnoreCase("settype")){
				if(args.length == 2){
					
					if(args[1].equalsIgnoreCase("agr") || args[1].equalsIgnoreCase("app") || args[1].equalsIgnoreCase("claim") || args[1].equalsIgnoreCase("ins")){
						
						
						int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
						if(id != 0){
							String spawn = new Spawns().getSpawnNameWithId(id);
							main.config.set("claim.list." + spawn + "." + id + ".type", args[1]);
							p.sendMessage("§bLe claim d'id §3" + id + "§b est désormais du type §3" + args[1]);
						}else p.sendMessage("§cVous devez être dans un claim.");
						
					}else p.sendMessage("§c/claim settype <agr/app/claim/ins>");
					
				}else p.sendMessage("§c/claim settype <agr/app/claim/ins>");
				
			}
			else if(args[0].equalsIgnoreCase("setsign")){
				if(args.length == 2){
					
					int prix = -1;
					try{
						prix = Integer.parseInt(args[1]);
					}catch(NumberFormatException e){}
					
					CCreateClaim.setClaimSign(p, prix);
					
				}else p.sendMessage("§c/claim setsign <prix/auto>");
				
			}
			else if(args[0].equalsIgnoreCase("note")){
				
				if(args.length == 2){
					
					int note = -1;
					try{
						note = Integer.parseInt(args[1]);
					}catch(NumberFormatException e){ p.sendMessage("§c/claim note <1-5>");}
					
					CAdminCommand.noteclaim(p, note);
				}else p.sendMessage("§c/claim note <1-5>");
				
			}
			else if(args[0].equalsIgnoreCase("setowner")){
				
				if(args.length == 2) CAdminCommand.setclaimowner(p, args[1]);
				else p.sendMessage("§c/claim setowner <Joueur/l'etat>");
				
			}else if(args[0].equalsIgnoreCase("delete")){
				CAdminCommand.deleteclaim(p);
			}else if(args[0].equalsIgnoreCase("setappzone")){
				CAdminCommand.setAppZone(p);
			}else if(args[0].equalsIgnoreCase("createspawn")){
				
				if(args.length == 3){
					
					boolean visible = true;
					if(args[2].equalsIgnoreCase("false")) visible = false;
					
					CAdminCommand.setSpawn(p, args[1], visible);
					
				}else p.sendMessage("§c/claim createspawn <nom> <true/false (visible)>");
				
			}else if(args[0].equalsIgnoreCase("setup")){
				
				int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
				String spawn = new Spawns().getSpawnNameWithId(id);
				
				if(id != 0){
					main.config.set("claim.list." + spawn + "." + id + ".needsetup", false);
					p.sendMessage("§6Le claim d'id §4" + id + "§6 a bien été setup !");
				}else p.sendMessage("§cVous devez être dans un claim");
				
			}else{
				
				help();
				
			}
		}else{
			
			if(args[0].equalsIgnoreCase("sell")){
				
				if(args.length == 2){
					
					int prix = -1;
					try{
						prix = Integer.parseInt(args[1]);
					}catch (NumberFormatException e){}
					
					CUserCommand.sellPlayerClaim(p, prix);
					
				}else p.sendMessage("§c/claim sell <prix/auto>");
				
			}
			else if(args[0].equalsIgnoreCase("sellCountry")){
				
				int id = CGetZoneId.getIdOfPlayerZone(p.getLocation());
				String spawn = new Spawns().getSpawnNameWithId(id);
				int price = (int) (main.config.getInt("claim.list." + spawn + "." + id + ".defprice") * 0.75);
				
				message.sendNmsMessageCmd(p, "§3Êtes vous sur de vouloir vendre votre terain pour une valeur de §b" + price + "€ §2\u2714", "§6Cliquez pour accepter", "/claim confirmsell");
				
			}
			else if(args[0].equalsIgnoreCase("invite")){
				if(args.length == 2){
					CUserCommand.invitePlayer(p, args[1]);
				}else p.sendMessage("§c/claim invite <Joueur>");
			}
			else if(args[0].equalsIgnoreCase("quit")){
				CUserCommand.quitclaim(p);
			}
			else if(args[0].equalsIgnoreCase("kick")){
				CUserCommand.kickclaim(p);
			}
			else if(args[0].equalsIgnoreCase("join")){
				CUserCommand.joinclaim(p);
			}else if(args[0].equalsIgnoreCase("confirmsell")){
				CUserCommand.sellCountry(p);
			}else{
				
				help();
				
			}
			
		}
	
		
	}
	
	private void help(){
		
		
		
		if(p.isOp() && p.getGameMode() == GameMode.CREATIVE){
			p.sendMessage("§6==========§c COMMANDES DES CLAIMS §6==========");
			p.sendMessage("");
			p.sendMessage("§6  => Commandes Admins");
			p.sendMessage("§c/claim create <2d/3d> <agr/app/claim/ins> <prix/auto> §3Créer un claim");
			p.sendMessage("§c/claim setsign <prix/auto> §3Poser la pancarte d'un claim");
			p.sendMessage("§c/claim settype <agr/app/claim/ins> §3Définire le type d'un claim");
			p.sendMessage("§c/claim setowner <Joueur/l'etat> §3Définire le propiétaire d'un claim");
			p.sendMessage("§c/claim note <1-5> §3Noter un claim");
			p.sendMessage("§c/claim setup §3Affirmer que le claim est correct apprès une demande de vérification");
			p.sendMessage("§c/claim createspawn <nom> <true/false (visible)> §3Créer un spawn");
			p.sendMessage("§c/claim delete §3Supprimer le claim sur lequel vous êtes");
			p.sendMessage("§c/claim setappzone §3Définire une zone d'appartement (Dynmap)");
			p.sendMessage("§6Commandes joueurs en gamemode 0");
		}else{
			p.sendMessage("§6==========§c COMMANDES DES CLAIMS §6==========");
			p.sendMessage("");
			p.sendMessage("§c/claim sell <prix/auto> §3Vendre son terrain");
			p.sendMessage("§c/claim sellCountry §3QVendre son terrain à l'état");
			p.sendMessage("§c/claim invite <Joueur> §3Inviter un joueur dans son terrain");
			p.sendMessage("§c/claim kick §3Expulser un joueur de son terrain");
			p.sendMessage("§c/claim quit §3Quitter un claim dans lequel on a été invité");
		}
		
	}

}

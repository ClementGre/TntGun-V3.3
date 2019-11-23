package fr.themsou.methodes;

import fr.themsou.rp.claim.Claim;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import fr.themsou.BedWars.getteam;
import fr.themsou.TntWars.TntWarsGame;
import fr.themsou.main.main;
import fr.themsou.rp.claim.GetZoneId;
import fr.themsou.rp.claim.Spawns;

public class Scoreboards {
	
	@SuppressWarnings("deprecation")
	public void sendPlayersScoreboard(){
		
		GetZoneId CGetZoneId = new GetZoneId();
		Spawns CSpawns = new Spawns();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			
			Objective obj = board.registerNewObjective("TntBoard", "dummy");
			
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName("§6TntGun v3");
				
			Score joueur = obj.getScore("§7Joueurs: §6" + Bukkit.getOnlinePlayers().size());
			joueur.setScore(15);
			
			int points = main.config.getInt(p.getName() + ".points");
			Score point = obj.getScore("§7Points : §6" + points + "§b");
			point.setScore(14);
			
			Score space = obj.getScore("§k");
			space.setScore(13);
			
			if(!PInfos.getGame(p).equals("HUB")){
				Score timeo = obj.getScore("§bPrésence : §3" + PInfos.getTime(p.getName(), PInfos.getGame(p)));
				timeo.setScore(12);
			}
			
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// ROLE PLAY  //////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			if(PInfos.getGame(p).equals("RP")){
				
				int Heure = (int) p.getWorld().getTime() / 1000 + 6; if(Heure > 24) Heure = Heure - 24;
				Score heure = obj.getScore("§bHeure : §3" + Heure + " H");
				heure.setScore(11);
				
				Score esp = obj.getScore("§f   ");
				esp.setScore(10);
				
				int Money = (int) main.economy.getBalance(p);
				if(Money == 0){
					Score money = obj.getScore("§bArgent :§c 0€");
					money.setScore(9);
				}else{
					Score money = obj.getScore("§bArgent : §3" + Money + "€");
					money.setScore(9);
				}
				
				// CLAIMS
				
				Claim claim = new Claim(p.getLocation());
				if(!claim.exist()){
					String ville = CSpawns.getSpawnNameWithLoc(p.getLocation());
					if(ville != null){
						Score reg = obj.getScore("§bClaim : §6" + ville);
						reg.setScore(8);
					}else{
						Score reg = obj.getScore("§bClaim : §cAucun");
						reg.setScore(8);
					}
				}else{
					if(claim.getOwner() == null){
						Score reg = obj.getScore("§bClaim : §2À vendre");
						reg.setScore(8);
					}else{
						if(claim.isSell()){
							Score reg = obj.getScore("§bClaim : §2" + claim.getOwner());
							reg.setScore(8);
						}else{
							Score reg = obj.getScore("§bClaim : §3" + claim.getOwner());
							reg.setScore(8);
						}
					}
				}
				Score esp2 = obj.getScore("§1");
				esp2.setScore(7);
				
				// ENT
				if(main.config.contains(p.getName() + ".rp.ent.name")){
					Score ent = obj.getScore("§bEntreprise : §3" + main.config.getString(p.getName() + ".rp.ent.name"));
					ent.setScore(6);
					if(main.config.getInt(p.getName() + ".rp.ent.role") == 2){
						int entMoneyInt = (int) main.config.getInt("ent.list." + main.config.getString(p.getName() + ".rp.ent.name") + ".money");
						if(entMoneyInt == 0){
							Score entMoney = obj.getScore("§b\u25b6 Argent :§c 0€");
							entMoney.setScore(5);
						}else{
							Score entMoney = obj.getScore("§b\u25b6 Argent : §3" + entMoneyInt + "€");
							entMoney.setScore(5);
						}
					}
				}else{
					Score ent = obj.getScore("§bEntreprise : §cAucune");
					ent.setScore(6);
				}
				
				
				
			}
////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////  TntWars  ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(PInfos.getGame(p).equals("TntWars")){

				
				TntWarsGame game = TntWarsGame.getInstanceViaPlayer(p);
				if(game != null){
					
					Score map = obj.getScore("§bmap : §3" + game.getMapName());
					map.setScore(11);
					Score esp = obj.getScore("§e");
					esp.setScore(10);
					
					if(game.isDouble){
						
						String team1Players = game.team1Player1.getName() + " & " + game.team1Player2.getName();
						if(team1Players.length() > 20)
							team1Players = team1Players.substring(0, 20) + "...";
						
						Score team1 = obj.getScore("§3Bleu : §7" + team1Players);
						team1.setScore(9);
						
						String team2Players = game.team2Player1.getName() + " & " + game.team2Player2.getName();
						if(team2Players.length() > 20)
							team2Players = team1Players.substring(0, 20) + "...";
						
						Score team2 = obj.getScore("§cRouge : §7" + team2Players);
						team2.setScore(8);
						
					}else{
						
						Score team1 = obj.getScore("§3Bleu : §7" + game.team1Player1.getName());
						team1.setScore(9);
						
						Score team2 = obj.getScore("§cRouge : §7" + game.team2Player1.getName());
						team2.setScore(8);
						
					}
					
					
					
				}
				
				
				
				
			}
////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////  BedWars  ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(PInfos.getGame(p).equals("BedWars")){
				
				int status = main.config.getInt("bedwars.list.status");
				int timer = main.config.getInt("bedwars.list.timer");
				String timerS = "";
				String statusS = "";
				if(timer >= 60){
					timerS = ((int)timer / 60) + ":" + ((int)timer % 60);
				}else{
					timerS = "0:" + timer;
				}
				if(status == 1){
					statusS = "§bSpawn x1.5";
				}else if(status == 2){
					statusS = "§bSpawn x2";
				}else if(status == 3){
					statusS = "§bSpawn x4";
				}else if(status == 4){
					statusS = "§cFin";
				}
				
				Score mode = obj.getScore(statusS + " : §3" + timerS);
				mode.setScore(11);
				
				getteam Cgetteam = new getteam();
				Score esp = obj.getScore("§k");
				esp.setScore(10);
				
				for(int i = 1; i <= 4; i++){
					if(main.config.getString("bedwars.list.teams." + i + ".owners") != null){
						
						String color = Cgetteam.getTeamChatColor(i) + "";
						String name = Cgetteam.getTeamStringColor(i);
						String[] owners = main.config.getString("bedwars.list.teams." + i + ".owners").split(",");
						int count = 0;
						
						if(owners.length == 1){
							if(Bukkit.getPlayerExact(owners[0]) != null){
								if(Bukkit.getPlayerExact(owners[0]).getWorld() == Bukkit.getWorld("BedWars")){
									if(Bukkit.getPlayerExact(owners[0]).getGameMode() == GameMode.SURVIVAL){
										count = 1;
									}
								}
							}
						}else if(owners.length == 2){
							if(Bukkit.getPlayerExact(owners[1]) != null){
								if(Bukkit.getPlayerExact(owners[1]).getWorld() == Bukkit.getWorld("BedWars")){
									if(Bukkit.getPlayerExact(owners[1]).getGameMode() == GameMode.SURVIVAL){
										count = 1;
									}
								}
							}
							if(Bukkit.getPlayerExact(owners[0]) != null){
								if(Bukkit.getPlayerExact(owners[0]).getWorld() == Bukkit.getWorld("BedWars")){
									if(Bukkit.getPlayerExact(owners[0]).getGameMode() == GameMode.SURVIVAL){
										count = 1;
									}
								}
							}
							if(Bukkit.getPlayerExact(owners[0]) != null && Bukkit.getPlayerExact(owners[1]) != null){
								if(Bukkit.getPlayerExact(owners[0]).getWorld() == Bukkit.getWorld("BedWars") && Bukkit.getPlayerExact(owners[1]).getWorld() == Bukkit.getWorld("BedWars")){
									if(Bukkit.getPlayerExact(owners[0]).getGameMode() == GameMode.SURVIVAL && Bukkit.getPlayerExact(owners[1]).getGameMode() == GameMode.SURVIVAL){
										count = 2;
									}
								}
							}
						}
						
						if(main.config.getInt("bedwars.list.teams." + i + ".bed") == 1){
							Score team = obj.getScore("§2\u2714 " + color + name + " §7" + count + "/" + owners.length);
							team.setScore(10 - i);
						}else{
							Score team = obj.getScore("§c\u2716 " + color + name + " §7" + count + "/" + owners.length);
							team.setScore(10 - i);
						}
					}
				}
				
				Objective o = board.registerNewObjective(p.getName().replace("test", "tast"), "health");
				
				o.setDisplaySlot(DisplaySlot.BELOW_NAME);
				o.setDisplayName("§c\u2764");
				
			}
////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////  HUB  ///////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			else{
				

				Score esp3 = obj.getScore("§3Présence :");
				esp3.setScore(4);
				
				Score timeo = obj.getScore("§b   Total : §3" + PInfos.getTotalTime(p.getName()));
				timeo.setScore(3);
				
				Score timea = obj.getScore("§b   RolePlay : §3" + PInfos.getTime(p.getName(), "RP"));
				timea.setScore(2);
				
				Score timeb = obj.getScore("§b   TntWars : §3" + PInfos.getTime(p.getName(), "TntWars"));
				timeb.setScore(1);
				
				Score timec = obj.getScore("§b   BedWars : §3" + PInfos.getTime(p.getName(), "BedWars"));
				timec.setScore(0);
				
				
			}
			
			
////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////// TAB LIST & NAME TAG ///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
			
			for(Player players : Bukkit.getOnlinePlayers()){
				
				String grade = main.config.getString(players.getName() + ".grade");
				if(!players.getDisplayName().contains(players.getName())) grade = "Joueur";
				
				String playercolor = main.configuration.getString("grades." + grade + ".playercolor");
				String gradecolor = main.configuration.getString("grades." + grade + ".gradecolor");
				
				if(playercolor == null) playercolor = "§7";
				if(gradecolor == null) gradecolor = "§7";
				if(players.getGameMode() == GameMode.SURVIVAL) playercolor = "§7";

				if(!board.getTeams().contains(players.getName())){
					board.registerNewTeam(players.getName());
				}
				
				if(PInfos.getGame(players).equals("RP")){
					
					players.setPlayerListName("[RP] " + gradecolor + "[" + grade + "] " + playercolor + players.getDisplayName());
					if(players.getGameMode() == GameMode.SURVIVAL){
						board.getTeam(players.getName()).setPrefix("§6" + PInfos.getComps(players) + " % §8- " + playercolor);
					}else{
						board.getTeam(players.getName()).setPrefix("§6[STAFF] §8- " + playercolor);
					}
					
				
				}else if(PInfos.getGame(players).equals("BedWars")){
					if(players.getGameMode() == GameMode.SURVIVAL){
						getteam Cgetteam = new getteam();
						board.getTeam(players.getName()).setPrefix(Cgetteam.getTeamChatColor(Cgetteam.getplayerteam(players)) + "[" + Cgetteam.getTeamStringColor(Cgetteam.getplayerteam(players)) + "] §8- ");
						players.setPlayerListName("[BedWars] " + Cgetteam.getTeamChatColor(Cgetteam.getplayerteam(players)) + "[" + Cgetteam.getTeamStringColor(Cgetteam.getplayerteam(players)) + "] " + playercolor + players.getDisplayName());
					}else{
						board.getTeam(players.getName()).setPrefix(playercolor);
						players.setPlayerListName("[BedWars] §7[SPECTATEUR] " + playercolor + players.getDisplayName());	
					}
				
				}else if(PInfos.getGame(players).equals("TntWars")){
					
					if(players.getGameMode() != GameMode.SURVIVAL && players.isOp()){
						
						board.getTeam(players.getName()).setPrefix("§6[STAFF] §8- " + playercolor);
						players.setPlayerListName("[TntWars] §7[SPECTATEUR] " + playercolor + players.getDisplayName());
						
					}else{
						
						TntWarsGame game = TntWarsGame.getInstanceViaPlayer(players);
						if(game != null){
							
							if(game.getPlayerTeam(players) == 1){
								
								board.getTeam(players.getName()).setPrefix("§3[BLEU] " + playercolor);
								players.setPlayerListName("[TntWars] §7<" + game.id + "> §3[BLEU] " + playercolor + players.getDisplayName());
								
							}else{
								
								board.getTeam(players.getName()).setPrefix("§c[ROUGE] " + playercolor);
								players.setPlayerListName("[TntWars] §7<" + game.id + "> §c[ROUGE] " + playercolor + players.getDisplayName());
								
							}
							
						}else{
							board.getTeam(players.getName()).setPrefix(playercolor);
							players.setPlayerListName("[TntWars] §7[SPECTATEUR] " + playercolor + players.getDisplayName());
						}
						
						
						
					}
					
						
						
						
					
					
					
					
				}else{
					board.getTeam(players.getName()).setPrefix(playercolor);
					players.setPlayerListName("[HUB] " + gradecolor + "[" + grade + "] " + playercolor + players.getDisplayName());
				}
				
				
				board.getTeam(players.getName()).setAllowFriendlyFire(true);
				board.getTeam(players.getName()).addPlayer(players);
				
			}
				
			p.setScoreboard(board);
			
			
		}
	}

}

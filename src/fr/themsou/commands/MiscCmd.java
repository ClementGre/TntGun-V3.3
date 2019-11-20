package fr.themsou.commands;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.meta.ItemMeta;
import fr.themsou.BedWars.BedWars;
import fr.themsou.discord.Roles;
import fr.themsou.main.main;
import fr.themsou.methodes.Boss;
import fr.themsou.methodes.Schematics;
import fr.themsou.methodes.SendStat;
import fr.themsou.methodes.realDate;
import fr.themsou.rp.ent.Utils;

public class MiscCmd implements TabCompleter, CommandExecutor{
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg1, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(args.length == 0){
				p.sendMessage("§c/misc <getstat/loadschematic/date/sendstats/bedwars/renameitem/discordweek/tag>");
				return true;
			}
				
			if(args[0].equalsIgnoreCase("getstat")){
				
				if(args.length == 3){
					p.sendMessage("§c" + args[1] + "§6 a miné §c" + Bukkit.getPlayer(args[1]).getStatistic(Statistic.MINE_BLOCK, Material.valueOf(args[2])) + " " + args[2]);
				}
				else p.sendMessage("§c/misc getstat <joueur> <bloc>");
				
			}else if(args[0].equalsIgnoreCase("loadschematic")){
				
				if(args.length == 2){
					new Schematics().loadSchematic(p.getLocation(), args[1]);
					p.sendMessage("§bLa Schematic a bien été chargé à votre location !");
				}else p.sendMessage("§c/misc loadschematic <nom>");
				
			}else if(args[0].equalsIgnoreCase("date")){
				
				Date date = new Date();
				p.sendMessage("§cDATE : §bJour de la semaine:§3 " + date.getDay() + " §bHeure:§3 " + date.getHours() + ":" + date.getMinutes() + " §bJour du mois:§3 " + date.getDate());
				Date realDate = new realDate().getRealDate();
				p.sendMessage("§cDATE RÈELLE: §bJour de la semaine:§3 " + realDate.getDay() + " §bHeure:§3 " + realDate.getHours() + ":" + realDate.getMinutes() + " §bJour du mois:§3 " + realDate.getDate());
			
			}else if(args[0].equalsIgnoreCase("sendstats")){
				
				if(args.length == 2){
					
					if(args[1].equalsIgnoreCase("day")){
						new SendStat().sendDay();
					
					}else if(args[1].equalsIgnoreCase("daynoclear")){
						new SendStat().sendDayNoClear();
					
					}else if(args[1].equalsIgnoreCase("week")){
						new SendStat().sendWeek();
					
					}else p.sendMessage("§c/misc sendstat <week/day/daynoclear>");
					
				}else p.sendMessage("§c/misc sendstat <week/day/daynoclear>");
				
			}else if(args[0].equalsIgnoreCase("bedwars")){
				
				if(args.length == 2){
					
					if(args[1].equalsIgnoreCase("newgame")){
						
						new BedWars().endGame(0);
					
					}else if(args[1].equalsIgnoreCase("pnjupgrade")){
						
						Villager villager = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
						villager.setCollidable(false);
						villager.setCustomNameVisible(true);
						villager.setCustomName("§cAMELIORATIONS");
						villager.setAI(false);
						villager.setSilent(true);
						villager.setProfession(Profession.BUTCHER);
						
					}else if(args[1].equalsIgnoreCase("pnjshop")){
						
						Villager villager = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
						villager.setCollidable(false);
						villager.setCustomNameVisible(true);
						villager.setCustomName("§cBOUTIQUE");
						villager.setAI(false);
						villager.setSilent(true);
						villager.setVelocity(p.getVelocity());
						villager.setProfession(Profession.LIBRARIAN);
						
					}else if(args[1].equalsIgnoreCase("pnjremove")){
						
						for(Entity entity : p.getLocation().getChunk().getEntities()){
					    	if(entity instanceof Villager){
					    		entity.remove();
					    	}
					    }
						
					}else p.sendMessage("§c/misc bedwars <newgame/pnjshop/pnjupgrade/pnjremove>");
					
				}else p.sendMessage("§c/misc bedwars <newgame/pnjshop/pnjupgrade/pnjremove>");
				
			}else if(args[0].equalsIgnoreCase("renameitem")){
				
				ItemMeta itemMeta = p.getInventory().getItemInMainHand().getItemMeta();
				itemMeta.setDisplayName(args[1].replace("&", "§"));
				p.getInventory().getItemInMainHand().setItemMeta(itemMeta);
				
			}else if(args[0].equalsIgnoreCase("discordweek")){
				new Roles().onWeek();
				p.sendMessage("§6Le code qui s'éxécute toutes les semaines pour discord viens d'être exécuté.");
				
			}else if(args[0].equalsIgnoreCase("entweek")){
				new Utils().payEntreprises();
				p.sendMessage("§6Le code qui s'éxécute toutes les semaines pour les entreprises viens d'être exécuté.");
				
			}else if(args[0].equalsIgnoreCase("notemounth")){
				
				for(String player : main.config.getConfigurationSection("").getKeys(false)){
					
					if(main.config.contains(player + ".claim.note")){
						
						int money = main.config.getInt(player + ".claim.note") * 1000;
						if(money != 0){
							main.economy.depositPlayer(player, money);
							main.config.set(player + ".claim.note", null);
							
							String notifs = main.config.getString(player + ".notifs") + "," + "§6Vous avez gagné §c" + money + "€ §6pour vous récompenser de vos builds.";
							main.config.set(player + ".notifs", notifs);
						}
					}
				}
				p.sendMessage("§6Le code qui s'éxécute tous les mois pour noter les claims viens d'être exécuté.");
				
			}else if(args[0].equalsIgnoreCase("tag")){
				if(args.length != 1){
					if(args[1].equals("remove")){
						for (Entity entity : p.getLocation().getChunk().getEntities()){
					    	if(entity instanceof ArmorStand){
					    		entity.remove();
					    	}
					    }
						p.sendMessage("§btag du chunck suprimée !");
					}else{
						String texte = "";
						int i = 0;
						for(String text : args){
							if(i == 1) texte = texte + text.replaceAll("&", "§");
							else if(i >= 2) texte = texte + " " + text.replaceAll("&", "§");
							i++;
						}
						
						ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
						stand.setCustomNameVisible(true);
						stand.setCustomName(texte);
						stand.setVisible(false);
						stand.setCollidable(false);
						p.sendMessage("§bTag placée");
					}
				}else p.sendMessage("§c/tag <Texte> /tag remove");
				
			}else if(args[0].equalsIgnoreCase("boss")){
				if(args.length >= 2){
					if(args[1].equals("add")){	
						if(args.length >= 6){
							String texte = ""; int skip = 5;
							for(String text : args){
								if(skip == 0) texte = texte + text.replaceAll("&", "§") + " "; else skip --;
							}
							new Boss().createBar(args[2], args[3], args[4], texte, p);
						}else p.sendMessage("§c/misc boss add <nom> <blanc/bleu/vert/rose/violet/rouge/jaune/aléatoire> <6/10/12/20/none> <texte");
					}else if(args[1].equals("remove")){	
						if(args.length >= 2){
							new Boss().removeBar(args[2], p);
						}else p.sendMessage("§c/misc boss remove <nom>");
					}else if(args[1].equals("list")){
						new Boss().list(p);
					}
				}else p.sendMessage("§c/misc boss <add/list/remove>");
				
			}else{
				p.sendMessage("§c/misc <getstat/loadschematic/date/sendstats/bedwars/renameitem/discordweek/tag>");
			}
			
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg, String[] args){
		
		if(sender instanceof Player){
			
			
			if(args.length == 1){
				
				return GeneralCmd.matchTab(Arrays.asList("getstat","entweek","loadschematic","date","sendstats","bedwars","renameitem","discordweek","notemounth","tag","boss"), args[0]);
				
			}else if(args.length == 2){
				
				if(args[0].equalsIgnoreCase("loadschematic")){
					return Arrays.asList("<Nom>");
				}
				if(args[0].equalsIgnoreCase("getstat")){
					return null;
				}
				if(args[0].equalsIgnoreCase("renameitem")){
					return Arrays.asList("<Nom>");
				}
				if(args[0].equalsIgnoreCase("sendstats")){
					return GeneralCmd.matchTab(Arrays.asList("week","day","daynoclear"), args[1]);
				}
				if(args[0].equalsIgnoreCase("bedwars")){
					return GeneralCmd.matchTab(Arrays.asList("newgame","pnjshop","pnjupgrade","pnjremove"), args[1]);
				}
				if(args[0].equalsIgnoreCase("tag")){
					return Arrays.asList("<Texte>","remove");
				}
				if(args[0].equalsIgnoreCase("boss")){
					return GeneralCmd.matchTab(Arrays.asList("add","remove","list"), args[1]);
				}
			}else if(args.length == 3){
				
				if(args[0].equalsIgnoreCase("boss")){
					if(args[1].equalsIgnoreCase("add")){
						return Arrays.asList("<Nom>");
					}
					if(args[1].equalsIgnoreCase("remove")){
						return Arrays.asList("<Nom>");
					}
				}
				if(args[1].equalsIgnoreCase("getstat")){
					return Arrays.asList("<Bloc>");
				}
				
			}else if(args.length == 4){
				
				if(args[0].equalsIgnoreCase("boss")){
					if(args[1].equalsIgnoreCase("add")){
						return GeneralCmd.matchTab(Arrays.asList("blanc","bleu","vert","rose","violet","rouge","jaune","aléatoire"), args[3]);
						
					}
				}
			}else if(args.length == 5){
				
				if(args[0].equalsIgnoreCase("boss")){
					if(args[1].equalsIgnoreCase("add")){
						return GeneralCmd.matchTab(Arrays.asList("6","10","12","20","none"), args[4]);
					}
				}
			}else if(args.length >= 6){
				
				if(args[0].equalsIgnoreCase("boss")){
					if(args[1].equalsIgnoreCase("add")){
						return Arrays.asList("< Texte >");
					}
				}
			}
		}
		
		return Arrays.asList("");
	}

}

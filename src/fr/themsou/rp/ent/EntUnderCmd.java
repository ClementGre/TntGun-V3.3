package fr.themsou.rp.ent;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.themsou.main.main;
import fr.themsou.nms.message;
import fr.themsou.rp.claim.CanBuild;
import fr.themsou.rp.claim.Spawns;

public class EntUnderCmd {
	
	Player p;
	String ent = null;
	int role = 0;
	
	
	public EntUnderCmd(Player p) {
		this.p = p;
		
		if(main.config.contains(p.getName() + ".rp.ent.name")){
			ent = main.config.getString(p.getName() + ".rp.ent.name");
		}
		
		role = main.config.getInt(p.getName() + ".rp.ent.role");
	}
	
/*     CREATE     */
	
	public void create(String entName){
	
		if(ent == null){
			
			if(entName.length() <= 16){
				
				if(!entName.contains(".") && !entName.contains(",") && !entName.contains("\\") && !entName.contains("'")){
					
					if(!main.config.contains("ent.list." + entName)){
						
						if(main.economy.getBalance(p) >= 30000){
							
							main.economy.withdrawPlayer(p, 30000);
							
							main.config.set("ent.list." + entName + ".pdg", p.getName());
							main.config.set("ent.list." + entName + ".manager", "");
							main.config.set("ent.list." + entName + ".salarié", "");
							main.config.set("ent.list." + entName + ".money", 5000);
							main.config.set("ent.list." + entName + ".claim", "");
							
							main.config.set(p.getName() + ".rp.ent.name", entName);
							main.config.set(p.getName() + ".rp.ent.role", 2);
							
							p.sendMessage("§6Vous venez de créer l'entreprise §c" + entName);
							
						}else p.sendMessage("§cVous devez avoir 30 000€ pour créer une entreprise. (25 000 pour les assurances et 5 000 pour le compte de votre entreprise)");
						
					}else p.sendMessage("§cCette entreprise existe déjà.");
					
				}else p.sendMessage("§cVotre nom d'entreprine ne doit pas contenir de . de , ou de caractère spéciaux.");
				
			}else p.sendMessage("§cVotre nom d'entreprine ne doit pas dépasser 16 caractères.");
			
		}else p.sendMessage("§cVous devez quitter votre entreprise pour en créer une.");
		
		
	}
	
/*     LEAVE     */
	
	public void leave(){
		
		if(ent != null){
			
			if(role == 2){
				main.config.set(p.getName() + ".rp.ent", null);
				
				for(String manager : main.config.getString("ent.list." + ent + ".manager").split(",")){
					main.config.set(manager + ".rp.ent", null);
				}
				for(String salarié : main.config.getString("ent.list." + ent + ".salarié").split(",")){
					main.config.set(salarié + ".rp.ent", null);
				}
				
				main.economy.withdrawPlayer(p, main.config.getInt("ent.list." + ent + ".money"));
				
				if(main.config.contains("ent.list." + ent + ".claim")){
					for(String id : main.config.getString("ent.list." + ent + ".claim").split(",")){
						String[] owners = main.config.getString("claim.list." + new Spawns().getSpawnNameWithId(Integer.parseInt(id)) + "." + id + ".owner").split(",");

						for(String owner : owners){
							if(!owner.equals(main.config.getString(p.getName() + ".rp.ent.name"))){
								main.config.set(owner + ".claim." + id, null);
							}
						}
						
						main.config.set("claim.list." + new Spawns().getSpawnNameWithId(Integer.parseInt(id)) + "." + id + ".owner", "l'etat");
						main.config.set("claim.list." + new Spawns().getSpawnNameWithId(Integer.parseInt(id)) + "." + id + ".needsetup", true);
					}
				}
				
				main.config.set("ent.list." + ent, null);
				p.sendMessage("§6Vous venez de dissoudre votre entreprise avec tout les claims qu'elle posédait.");
				
			}else if(role == 1){
				main.config.set(p.getName() + ".rp.ent", null);
				main.config.set("ent.list." + ent + ".manager", main.config.getString("ent.list." + ent + ".manager").replace("," + p.getName(), "").replace(p.getName() + ",", "").replace(p.getName(), ""));
				p.sendMessage("§6Vous venez de quitter l'entreprise §c" + ent);
				
			}else if(role == 0){
				main.config.set(p.getName() + ".rp.ent", null);
				main.config.set("ent.list." + ent + ".salarié", main.config.getString("ent.list." + ent + ".salarié").replace("," + p.getName(), "").replace(p.getName() + ",", "").replace(p.getName(), ""));
				p.sendMessage("§6Vous venez de quitter l'entreprise §c" + ent);
			}
			
			
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
		
	}
	
/*     LIST     */
	
	public void list(){
		
		ConfigurationSection section = main.config.getConfigurationSection("ent.list");
		
		p.sendMessage("§cVoici la liste des différentes entreprises du serveur");
		
		for(String entName : section.getKeys(false)){
			int money = main.config.getInt("ent.list." + entName + ".money");
			String salarié = main.config.getString("ent.list." + entName + ".salarié");
			String manager = main.config.getString("ent.list." + entName + ".manager");
			String pdg = main.config.getString("ent.list." + entName + ".pdg");
			
			
			int salariés = salarié.split(",").length;
			int managers = manager.split(",").length;
			
			if(manager.isEmpty()) managers = 0; 
			if(salarié.isEmpty()) salariés = 0; 
			
			p.sendMessage("§6" + entName + " §c>>§6 " + money + "€ §c-- §3PDG : §b" + pdg + " §3Salariés : §b" + salariés + " §3Manager : §b" + managers);
		}
		
	}
	
/*     INFO     */
	
	public void info(String entName){
		
		if(main.config.contains("ent.list." + entName)){
			
			int money = main.config.getInt("ent.list." + entName + ".money");
			String salarié = main.config.getString("ent.list." + entName + ".salarié");
			String manager = main.config.getString("ent.list." + entName + ".manager");
			String pdg = main.config.getString("ent.list." + entName + ".pdg");
			
			int salariés = manager.split(",").length;
			int managers = salarié.split(",").length;
			
			if(manager.isEmpty()) managers = 0; 
			if(salarié.isEmpty()) salariés = 0; 
			
			p.sendMessage("§cInformations sur l'entreprise §6" + entName);
			p.sendMessage("§3PDG : §b" + pdg);
			p.sendMessage("§3Manager (" + managers + ") : §b" + manager);
			p.sendMessage("§3Salariés (" + salariés + ") : §b" + salarié);
			p.sendMessage("§3Argent : §b" + money);
			
		}else p.sendMessage("§cL'entreprise §6" + entName + "§c n'existe pas.");
		
		
	}
	
/*     JOIN     */
	
	public void join(){
		
		if(ent == null){
			
			if(main.config.contains(p.getName() + ".rp.ent.join")){
				
				String entName = main.config.getString(p.getName() + ".rp.ent.join");
				
				if(main.config.getString("ent.list." + entName + ".salarié").isEmpty()) main.config.set("ent.list." + entName + ".salarié", p.getName());
				else main.config.set("ent.list." + entName + ".salarié", main.config.getString("ent.list." + entName + ".salarié") + "," + p.getName());
				
				main.config.set(p.getName() + ".rp.ent.name", entName);
				main.config.set(p.getName() + ".rp.ent.role", 0);
				
				p.sendMessage("§6Vous venez de rejoindre l'entreprise §c" + entName);
				
			}else p.sendMessage("§cVous n'avez reçu aucune invitation.");
			
		}else p.sendMessage("§cVous êtes déjà dans une entreprise.");
		
		main.config.set(p.getName() + ".rp.ent.join", null);
		
	}
	
/*     EMBAUCHER     */
	
	public void hire(OfflinePlayer p2, int setRole){
		
		String p2Ent = main.config.getString(p2.getName() + ".rp.ent.name");
		int p2Role = main.config.getInt(p2.getName() + ".rp.ent.role");
		if(p2Ent == null) p2Ent = "";
		
		if(p.getName().equals(p2.getName())){
			p.sendMessage("§cVous ne pouvez pas modifier votre propre grade.");
			return;
		}
		
		if(ent != null){
			if(role >= 1){
				if(p2Ent.isEmpty() || p2Ent.equals(ent)){
					
					if(setRole == 0){						// SET NONE
						if(p2Ent.equals(ent)){
							if(p2Role == 1){
								if(role == 2){
									main.config.set(p2.getName() + ".rp.ent", null);
									main.config.set("ent.list." + ent + ".manager", main.config.getString("ent.list." + ent + ".manager").replace("," + p2.getName(), "").replace(p2.getName() + ",", "").replace(p2.getName(), ""));
									if(p2.isOnline()) p2.getPlayer().sendMessage("§6Vous venez d'être licencié l'entreprise §c" + ent);
									p.sendMessage("§6Vous venez de licencier §c" + p2.getName() + "§6 de l'entreprise");
								}else p.sendMessage("§cVous ne pouvez pas licencier un manager.");
								
							}else if(p2Role == 0){
								main.config.set(p2.getName() + ".rp.ent", null);
								main.config.set("ent.list." + ent + ".salarié", main.config.getString("ent.list." + ent + ".salarié").replace("," + p2.getName(), "").replace(p2.getName() + ",", "").replace(p2.getName(), ""));
								if(p2.isOnline()) p2.getPlayer().sendMessage("§6Vous venez d'être licencié l'entreprise §c" + ent);
								p.sendMessage("§6Vous venez de licencier §c" + p2.getName() + "§6 de l'entreprise");
							}
						}else p.sendMessage("§6" + p2.getName() + "§c n'est pas dans votre entreprise.");
						
					}else if(setRole == 1){						// SET SALARIÉ
						if(p2Ent.equals(ent)){
							if(role == 2){
								main.config.set("ent.list." + ent + ".manager", main.config.getString("ent.list." + ent + ".manager").replace("," + p2.getName(), "").replace(p2.getName() + ",", "").replace(p2.getName(), ""));
								if(main.config.getString("ent.list." + ent + ".salarié").isEmpty()) main.config.set("ent.list." + ent + ".salarié", p2.getName());
								else main.config.set("ent.list." + ent + ".salarié", main.config.getString("ent.list." + ent + ".salarié") + "," + p2.getName());
								main.config.set(p2.getName() + ".rp.ent.role", 0);
								if(p2.isOnline()) p2.getPlayer().sendMessage("§6Vous venez d'être rétrogradé §csalarié §6dans l'entreprise §c" + ent);
								p.sendMessage("§6Vous venez de rétrograder §c" + p2.getName() + " salarié");
							}else p.sendMessage("§cVous ne pouvez pas rétrograder un manager.");
							
						}else if(p2.isOnline()){
							main.config.set(p2.getName() + ".rp.ent.join", ent);
							message.sendNmsMessageCmd(p2.getPlayer(), "§c" + p.getName() + "§6 viens de vous embaucher dans son entreprise, cliquez pour accepter", "§bEntrer dans l'entreprise §3" + ent, "/ent join");
							p.sendMessage("§6Vous venez d'inviter §c" + p2.getName() + "§6 dans votre entreprise.");
						}else p.sendMessage("§6" + p2.getName() + "§c n'est pas conecté.");
						
					}else if(setRole == 2){						// SET MANAGER
						if(p2Ent.equals(ent)){
							if(role == 2){
								main.config.set("ent.list." + ent + ".salarié", main.config.getString("ent.list." + ent + ".salarié").replace("," + p2.getName(), "").replace(p2.getName() + ",", "").replace(p2.getName(), ""));
								if(main.config.getString("ent.list." + ent + ".manager").isEmpty()) main.config.set("ent.list." + ent + ".manager", p2.getName());
								else main.config.set("ent.list." + ent + ".manager", main.config.getString("ent.list." + ent + ".manager") + "," + p2.getName());
								main.config.set(p2.getName() + ".rp.ent.role", 1);
								if(p2.isOnline()) p2.getPlayer().sendMessage("§6Vous venez d'être promu §cmanager §6dans l'entreprise §c" + ent);
								p.sendMessage("§6Vous venez de promouvoir §c" + p2.getName() + " manager");
							}else p.sendMessage("§cVous ne pouvez pas promouvor quelqu'un en manager.");
						}else p.sendMessage("§cVous devez déjà inviter §6" + p2.getName() + "§c en tan que salarié.");
					}
					
				}else p.sendMessage("§6" + p2.getName() + "§c est déjà dans une entreprise.");
			
			}else p.sendMessage("§cVous devez être manager ou PDG pour embaucher/licencier un joueur.");
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
			
	}
	
/*     MONEY     */
	
	public void money(){
		
		if(ent != null){
			
			p.sendMessage("§6Votre entreprise a §c" + main.config.getInt("ent.list." + ent + ".money") + "€");
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
		
	}
	
/*     PAYER     */
	
	@SuppressWarnings("deprecation")
	public void pay(String p2, int amount){
	
		if(ent != null){
			
			if(role >= 1){
				
				if(main.config.getInt("ent.list." + ent + ".money") >= amount){
					
					if(p2.split(":")[0].equals("ent")){
						p2 = p2.replace("ent:", "");
						
						if(main.config.contains("ent.list." + p2)){
							
							main.config.set("ent.list." + ent + ".money", main.config.getInt("ent.list." + ent + ".money") - amount);
							main.config.set("ent.list." + p2 + ".money", main.config.getInt("ent.list." + p2 + ".money") + amount);
							p.sendMessage("§c" + amount + "€§6 ont bien été envoyés à l'entreprise §c" + p2);
							
							String pdg = main.config.getString("ent.list." + ent + ".pdg");
							if(Bukkit.getOfflinePlayer(pdg).isOnline()) Bukkit.getPlayerExact(pdg).sendMessage("§6L'entreprise §c" + ent + " §6a envoyé §c" + amount + "€§6 à votre entreprise");
							
							
						}else p.sendMessage("§cIl n'existe pas d'entreprises avec le nom §6" + p2);
						
					}else{
						
						if(main.config.contains(p2)){
							
							main.config.set("ent.list." + ent + ".money", main.config.getInt("ent.list." + ent + ".money") - amount);
							main.economy.depositPlayer(Bukkit.getOfflinePlayer(p2), amount);
							p.sendMessage("§c" + amount + "€§6 ont bien été envoyés à §c" + p2);
							
							if(Bukkit.getOfflinePlayer(p2).isOnline()) Bukkit.getPlayerExact(p2).sendMessage("§6L'entreprise §c" + ent + " §6vous a envoyé §c" + amount + "€");
							
						}else p.sendMessage("§cAucun joueur trouvé avec le nom §6" + p2);
						
					}
					
				}else p.sendMessage("§cVous n'avez pas assez d'argent §6(" + main.config.getInt("ent.list." + ent + ".money") + "/" + amount + ")");
					
			}else p.sendMessage("§cVous devez être manager ou PDG pour éffectuer un virement.");
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
		
	}
	
/*     SIGN     */
	
	public void sign(int price, String desc){
		
		if(ent != null){
			
			if(role >= 1){
				
				if(p.getInventory().getItemInMainHand().getType() != Material.AIR){
					
					ItemStack item = p.getInventory().getItemInMainHand();
					
					p.getInventory().addItem(new Sign().makeSign(price, item, desc));
					
					p.sendMessage("§6Voici une pancarte pour vendre des §c" + item.getType().toString());
					
					p.sendMessage("§bVous pouvez placer cette pancarte dans votre terrain d'entreprise (industrie)");
					p.sendMessage("§3Soit un coffre§b pour vendre le contenu du coffre (s'il corespond)");
					p.sendMessage("§3Soit n'importe où§b pour vendre le contenu du coffre de ressource (faire /ent src pour le définire)");
					
				}else p.sendMessage("§cVous devez avoir un objet à la main.");
				
			}else p.sendMessage("§cVous devez être manager ou PDG pour créer une pancarte de vente.");
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
		
	}
	
/*     RESSOURCE     */
	
	public void srcRemove(){
		
		if(ent != null){
			
			if(role >= 1){
				
				main.config.set("ent.list." + ent + ".src", null);
				p.sendMessage("§6Vous venez de supprimer tous les coffres de ressources de l'entreprise.");
				
			}else p.sendMessage("§cVous devez être manager ou PDG pour définire le coffre de ressource.");
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
		
	}
	
	public void src(){
		
		if(ent != null){
			
			if(role >= 1){
				
				int x = p.getLocation().getBlockX();
				int y = p.getLocation().getBlockY();
				int z = p.getLocation().getBlockZ();
				World world = Bukkit.getWorld("world");
				Location src = null;
				
				x += 1;
				if(new Location(world, x, y, z).getBlock().getType() == Material.CHEST || new Location(world, x, y, z).getBlock().getType() == Material.TRAPPED_CHEST){
					src = new Location(world, x, y, z);
				}
				x -= 2;
				if(new Location(world, x, y, z).getBlock().getType() == Material.CHEST || new Location(world, x, y, z).getBlock().getType() == Material.TRAPPED_CHEST){
					src = new Location(world, x, y, z);
				}
				z += 1;
				x += 1;
				if(new Location(world, x, y, z).getBlock().getType() == Material.CHEST || new Location(world, x, y, z).getBlock().getType() == Material.TRAPPED_CHEST){
					src = new Location(world, x, y, z);
				}
				z -= 2;
				if(new Location(world, x, y, z).getBlock().getType() == Material.CHEST || new Location(world, x, y, z).getBlock().getType() == Material.TRAPPED_CHEST){
					src = new Location(world, x, y, z);
				}
				
				if(src != null){
					
					if(new CanBuild().canBuild(p, src)){
						
						int id = new Random().nextInt(1000);
						
						main.config.set("ent.list." + ent + ".src." + id + ".x", src.getBlockX());
						main.config.set("ent.list." + ent + ".src." + id + ".y", src.getBlockY());
						main.config.set("ent.list." + ent + ".src." + id + ".z", src.getBlockZ());
						
						p.sendMessage("§6Un coffre de ressource vient d'être ajouté.");
						
					}else p.sendMessage("§cLe coffre désigné n'est pas dans un de vos claims.");
					
				}else p.sendMessage("§cIl n'y a aucun coffre a coté de vous.");
				
			}else p.sendMessage("§cVous devez être manager ou PDG pour définire le coffre de ressource.");
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
		
	}
	
/*     LOG     */
	
	public void log(String p2){
		
		if(ent != null){
			
			if(role >= 1){
				
				if(!main.config.contains("ent.list." + ent + ".log." + p2 + ".remove") && !main.config.contains("ent.list." + ent + ".log." + p2 + ".add")){
					p.sendMessage("§cIl n'existe aucun log sur le joueur §6" + p2);
					return;
				}
				
				
				p.sendMessage("§6Logs du salarié §c" + p2 + "§6 depuis le dernier /ent log " + p2);
				
				if(main.config.contains("ent.list." + ent + ".log." + p2 + ".add")){
					String log = main.config.getString("ent.list." + ent + ".log." + p2 + ".add");
					p.sendMessage("§c" + p2 + "§c a apporté :");
					p.sendMessage(log);
				}
				if(main.config.contains("ent.list." + ent + ".log." + p2 + ".remove")){
					String log = main.config.getString("ent.list." + ent + ".log." + p2 + ".remove");
					p.sendMessage("§c" + p2 + "§c a  emporté :");
					p.sendMessage(log);
				}
				
				main.config.set("ent.list." + ent + ".log." + p2, null);
				
			}else p.sendMessage("§cVous devez être manager ou PDG pour consulter les logs.");
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
		
	}
	
/*     BUYERS     */
	
	public void buyers(){
		
		if(ent != null){
			
			if(role >= 1){
				
				if(main.config.contains("ent.list." + ent + ".buyers")){
					
					ConfigurationSection buyersSection = main.config.getConfigurationSection("ent.list." + ent + ".buyers");
					
					p.sendMessage("§c--- §6§l" + buyersSection.getKeys(false).size() + " ventes depuis la dernière récompense §r§c---");
				
					int[] buyersValue = new int[buyersSection.getKeys(false).size()];
					int total = 0;
					int i = 0;
					for(String buyer : buyersSection.getKeys(false)){
						p.sendMessage("§3" + buyer + " : §b" + main.config.getInt("ent.list." + ent + ".buyers." + buyer) + "€");
						buyersValue[i] = main.config.getInt("ent.list." + ent + ".buyers." + buyer);
						total += main.config.getInt("ent.list." + ent + ".buyers." + buyer);
						i++;
					}
					
					if(p.isOp()){
						
						Arrays.sort(buyersValue);
						int median = buyersValue[buyersValue.length / 2];
						int totMedian = median * buyersSection.getKeys(false).size();
						
						p.sendMessage("§6Médiane : §e" + median + "§6 = §e" + totMedian);
						p.sendMessage("§6Moyenne : §e" + total / buyersValue.length);
						p.sendMessage("§6Total : §e" + total);
						
						
						
					}
					
				}else p.sendMessage("§cVous n'avez vendu aucun item.");
				
			}else p.sendMessage("§cVous devez être manager ou PDG pour consulter les logs l'entreprise.");
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
		
	}
	
/*     RENAME     */
	
	public void rename(String entName){
		
		if(ent != null){
			
			if(role == 2){
				
				if(entName.length() <= 16){
					
					if(!entName.contains(".") && !entName.contains(",") && !entName.contains("\\") && !entName.contains("'")){
						
						if(!main.config.contains("ent.list." + entName)){
							
							copyConfigSection(main.config, "ent.list." + ent, "ent.list." + entName);
							
							main.config.set(p.getName() + ".rp.ent.name", entName);
							
							for(String manager : main.config.getString("ent.list." + ent + ".manager").split(",")){
								main.config.set(manager + ".rp.ent.name", entName);
							}
							for(String salarié : main.config.getString("ent.list." + ent + ".salarié").split(",")){
								main.config.set(salarié + ".rp.ent.name", entName);
							}
							
							main.config.set("ent.list." + ent, null);
							ent = entName;
							
							if(main.config.contains("ent.list." + ent + ".claim")){
								for(String id : main.config.getString("ent.list." + ent + ".claim").split(","))
									main.config.set("claim.list." + new Spawns().getSpawnNameWithId(Integer.parseInt(id)) + "." + id + ".owner", ent);
							}
							
							p.sendMessage("§6Votre entreprise a bien été renomé en §c" + entName);
							
						}else p.sendMessage("§cCette entreprise existe déjà.");
						
					}else p.sendMessage("§cVotre nom d'entreprine ne doit pas contenir de . de , ou de caractère spéciaux.");
					
				}else p.sendMessage("§cVotre nom d'entreprine ne doit pas dépasser 16 caractères.");
				
			}else p.sendMessage("§cVous devez être PDG pour renomer l'entreprise.");
			
		}else p.sendMessage("§cVous devez être dans une entreprise.");
		
	}
	
/*     ERROR     */
	
	public void infoMessage(){
		
		p.sendMessage("§6==========§c COMMANDES DES ENTREPRISES §6==========");
		p.sendMessage("");
		p.sendMessage("§6\u21d2 Commandes pour les particuliers");
		p.sendMessage("§c/ent create <nom> §3Créer son entreprise");
		p.sendMessage("§c/ent leave §3Quitter son entreprise");
		p.sendMessage("§c/ent list §3Voir la liste des entreprises");
		p.sendMessage("§c/ent info <entreprise> §3Avoir des informations sur une entreprise");
		p.sendMessage("§c/ent money §3Voir l'argent de l'entreprise");
		p.sendMessage("");
		p.sendMessage("§6\u21d2 Commandes pour les manager & PDG");
		p.sendMessage("§c/ent hire <joueur> <aucun/salarié/manager> §3embaucher/licencier un joueur");
		p.sendMessage("§c/ent pay <joueur>/ent:<entrprise> <argent> §3Payer avec l'argent de l'entreprise");
		p.sendMessage("§c/ent sign <prix> <description §3Créer une pancarte pour vendre l'objet de votre main");
		p.sendMessage("§c/ent src [clear] §3Ajouter un coffre de ressource (ou supprimer)");
		p.sendMessage("§c/ent log <joueur> §3Voir les logs d'un joueur");
		p.sendMessage("§c/ent buyers §3Voir les acheteurs de l'entreprise");
		p.sendMessage("§c/ent rename <nom> §3Renomer son entreprise");
		
	}
	
	
/*     UTILS     */
	
	public void copyConfigSection(Configuration config, String fromPath, String toPath){
		
		ConfigurationSection section = main.config.getConfigurationSection(fromPath);
		System.out.println("Start boucle for " + fromPath + " to " + toPath);
		for(String value : section.getKeys(false)){
			if(main.config.isConfigurationSection(fromPath + "." + value)){
				
				copyConfigSection(config, fromPath + "." + value, toPath + "." + value);
				
			}else{
				System.out.println("Set " + fromPath + "." + value + " to " + fromPath + "." + value);
				main.config.set(toPath + "." + value, main.config.get(fromPath + "." + value));
				
			}
		}
	}

	

}

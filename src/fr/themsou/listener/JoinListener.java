package fr.themsou.listener;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.themsou.methodes.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.blackfire62.myskin.bukkit.MySkin;
import fr.themsou.diffusion.api.messages;
import fr.themsou.diffusion.api.roles;
import fr.themsou.discord.Counter;
import fr.themsou.discord.Link;
import fr.themsou.main.main;
import fr.themsou.methodes.Inventory;
import fr.themsou.methodes.Scoreboards;
import fr.themsou.methodes.realDate;
import fr.themsou.nms.title;

@SuppressWarnings("deprecation")
public class JoinListener implements Listener{
	
	@SuppressWarnings("unused")
	private main pl;
	
	public JoinListener(main pl) {
		this.pl = pl;
	}

	private boolean secureLogin = false;
	
	
	@EventHandler
	public void onPlayerPreLogin(PlayerPreLoginEvent e){
		
		String p = e.getName();
		
//-------------------- DÉJÀ CONNECTÉ --------------------

		for(Player players : Bukkit.getOnlinePlayers()){
			
			if(players.getName().equalsIgnoreCase(p)){
				
				e.setKickMessage("§c" + p + " est déjà connecté !");
				e.setResult(Result.KICK_OTHER);
				return;
			}
		
		}
		
		
	}
	
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e){
	
		Player p = e.getPlayer();
		
//-------------------- ALPHAN UMERIQUE --------------------
		
		if(!isAlphaNum(p.getName())){
			e.setKickMessage("§cVous devez avoir un pseudo alphanumérique\n§e(lettres de a à z en majuscule ou pas, chiffres de 1 à 9, underscore (_))");
			e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
		}
				
//-------------------- DDOS --------------------
		
		if(main.ddos > 0){
			
			main.ddos = 2;
			
			e.setKickMessage("§cNotre protection contre les attaque par denis de service vous à détecté...\n§6Réessayez de vous connecter dans 5 secondes.");
			e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			return;
		}
		main.ddos = 2;
		
//-------------------- BAN DIRECT --------------------
		
		if(main.config.getInt(p.getName() + ".punish.ban.day") >= 1){
			
			int punishTime = main.config.getInt(p.getName() + ".punish.ban.day");
			String punishReason = main.config.getString(p.getName() + ".punish.ban.reason");
			String punisher = main.config.getString(p.getName() + ".punish.ban.punisher");
			
			e.setKickMessage("§cVOUS ÊTES BANNIS !\n\n§cPour raison §6" + punishReason + "\n§cPour §6" + punishTime + "§c Jours\n§cPar §6" + punisher + "\n§3Discord : §bhttps://discord.tntgun.fr/");e.setKickMessage("§cVOUS ÊTES BANNIS !\n\n§cPour raison §6" + punishReason + "\n§cPour §6" + punishTime + "§c Jours\n§cPar §6" + punisher + "\n§3Discord : §bhttps://discord.tntgun.fr/");
			e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			return;
			
//-------------------- BAN IP || MAX ACCOUNT--------------------			
		}else{
			
			int accounts = 0;
			String accountsPlayers = "";
			if(!main.config.contains(p.getName())) accounts ++;
			
			for(String player : main.config.getConfigurationSection("").getKeys(false)){
				
				if(!main.config.contains(player + ".list")){
					
					String ipNumber = main.config.getString(player + ".ip");
					if(ipNumber == null) ipNumber = "0.0.0.0";
					
					if(ipNumber.equals(e.getAddress().getHostAddress())){
						
						
						if(!Bukkit.getOperators().contains(Bukkit.getOfflinePlayer(player)) && !player.equals(p.getName())){
							accounts ++;
						}
						accountsPlayers += player + ", ";
						
						if(main.config.getInt(player + ".punish.ban.day") >= 1){
							
							int punishTime = main.config.getInt(player + ".punish.ban.day");
							String punishReason = main.config.getString(player + ".punish.ban.reason");
							String punisher = main.config.getString(player + ".punish.ban.punisher");
							
							e.setKickMessage("§cVOUS ÊTES BANNIS !\n\n§cPour raison §6" + punishReason + "\n§cPour §6" + punishTime + "§c Jours\n§cPar §6" + punisher + "\n§3Discord : §bhttps://discord.tntgun.fr/");
							e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
							return;
						}
						
					}
					
				}
			}
			
			if(accounts > 3){
				e.setKickMessage("§cVous ne vouvez pas avoir plus de 3 comptes\n§e(" + accountsPlayers + ")");
				e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
				return;
			}
			
		}
//-------------------- SET IP --------------------
		
		if(!main.config.contains(p.getName() + ".ip")){
			main.config.set(p.getName() + ".ip", e.getAddress().getHostAddress());
		}
		
//-------------------- CHANGE IP --------------------	
		if(!main.config.getString(p.getName() + ".ip").equals(e.getAddress().getHostAddress())){
			
			if(main.config.contains(p.getName() + ".discord")){
				
				new Link().changePlayerIP(p.getName(), main.config.getString(p.getName() + ".discord"), e.getAddress().getHostAddress());
				
				e.setKickMessage("§cVous ne vous etes pas connecté avec votre ordinateur habituel.\n§6Un message de confirmation vous à été envoyé sur Discord");
				e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
				return;
				
			}else{
				secureLogin = true;
			}
		}
	}
	
	@EventHandler
	public void OnJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();

		PlayerInfo pInfo = new PlayerInfo(p);
		main.playersInfos.put(p, pInfo);
		pInfo.setSecureLogin(secureLogin);

		p.setGameMode(GameMode.SURVIVAL);
		
		new Counter().refreshCounters();
		
		Date date = new realDate().getRealDate();
		main.config.set(p.getName() + ".stat.last", date.getDay() + "-" + date.getDate() + "/" + date.getMonth() + " " + date.getHours() + ":" + date.getMinutes());
		main.config.set(p.getName() + ".stat.lastday", date.getDate());
		main.config.set(p.getName() + ".lastday", date.getDate());

/////////////////////////////////////////// INITIALISE
		
		try{
			Date rd = new realDate().getRealDate();
			String dateLog = "[" + rd.getMonth() + "/" + rd.getDay() + " " + rd.getHours() + ":" + rd.getMinutes() + "] ";
			FileWriter writer = new FileWriter(main.logblock.getAbsoluteFile(), true);
			BufferedWriter out = new BufferedWriter(writer);
			out.write(dateLog + p.getName() + " À REJOINT LE SERVEUR");
			out.newLine();
			out.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
		
		MySkin myskin = (MySkin) Bukkit.getPluginManager().getPlugin("MySkin");
		
		UUID skin = myskin.getCache().loadSkinOfPlayer(p.getUniqueId());
		if(skin != null){
			myskin.getCache().saveSkinOfPlayer(p.getUniqueId(), skin);
			myskin.getHandler().update(p);
		}
		
		
		
		p.sendMessage("§6Bienvenue sur §3TntGun V3.3 §bServeur §3Role Play §bet §3Mini-Jeux");
		Scoreboards CScoreboards = new Scoreboards();
		CScoreboards.sendPlayersScoreboard();
		
		p.setPlayerListHeader("§bBienvenue sur §3TntGun V3.3\n§6Faites §c/? §6Pour ouvrir le menu\n ");
		p.setPlayerListFooter("\n§2Site : §7https://tntgun.fr/\n§2Discord : §7https://discord.tntgun.fr/");
		
		/*if(isUsernamePremium(p.getName())){
			pInfos.setLoggin(true);
			title.sendTitle(p, "§bBienvenue sur§3 TntGun V3", "§6Vous êtes connecté avec un compte premium.", 60);
		}else{*/
			if(!main.config.contains(p.getName() + ".mdp")){
				
				main.config.set(p.getName() + ".grade", "Joueur");
				title.sendTitle(p, "§bBienvenue sur§3 TntGun V3", "§6Faites /reg <mot de passe>", 60);
				p.sendMessage("§cVotre mot de passe n'est pas stocké dans une base de donnée sécurisé, choisissez un mot de passe différents de vos autres mot de passe.");
				
				String dc = "§cAucun";
				for(String dcs : main.config.getConfigurationSection("").getKeys(false)){
					if(main.config.contains(dcs + ".ip")){
						if(main.config.getString(dcs + ".ip").equals(main.config.getString(p.getName() + ".ip")) && !dcs.equals(p.getName())){
							dc += dcs + ", ";
						}
					}
				}
				
				for(Player players : Bukkit.getOnlinePlayers()){
					if(!players.getName().equals(p.getName())){
						players.sendMessage("§5" + p.getName() + "§d vient de rejoindre le serveur pour la première fois, souhaitez lui la bienvenue !");
						if(players.isOp()){
							players.sendMessage("§6Doubles comptes : §e" + dc);
						}
					}
					
				}	
				
			}else{
				if(!pInfo.isSecureLogin()) title.sendTitle(p, "§bBienvenue sur§3 TntGun V3", "§6Faites /l <mot de passe>", 60);
				else{
					title.sendTitle(p, "§bBienvenue sur§3 TntGun V3", "§6Faites /l <mot de passe> <mot de passe>", 60);
					p.sendMessage("§cVous ne vous étes pas connecté depuis votre ordianteut habituel, veuillez saisir votre mot de passe deux fois");
				}
				
			}
		//}
		
		
		
/////////////////////////////////////////// PERMS
		
		/*HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
		PermissionAttachment attachment = p.addAttachment(this.pl);
		perms.put(p.getUniqueId(), attachment);
		PermissionAttachment pperms = perms.get(p.getUniqueId());
		
		
		pperms.setPermission("crazyauctions.access", true);
		pperms.setPermission("crazyauctions.sell", true);
		pperms.setPermission("crazyauctions.sell.20", true);
		
		pperms.setPermission("shopchest.create", true);
		pperms.setPermission("shopchest.buy", true);
		pperms.setPermission("shopchest.sell", true);*/
		
/////////////////////////////////////////// TP / INV
		new Inventory().loadPlayerInventory(p, "rp");
		p.teleport(new Location(Bukkit.getWorld("hub"), 0.5, 50, 0.5));
		
		p.getInventory().clear();
		
		ItemStack a = new ItemStack(Material.DIAMOND, 1);
		ItemMeta aM = a.getItemMeta();
		aM.setDisplayName("§3§lVIP");
		a.setItemMeta(aM);
		
		ItemStack b = new ItemStack(Material.COMPASS, 1);
		ItemMeta bM = b.getItemMeta();
		bM.setDisplayName("§3§lMENU");
		b.setItemMeta(bM);
		
		p.getInventory().setItem(0, a);
		p.getInventory().setItem(4, b);
		
/////////////////////////////////////////// DISCORD
		
		messages Cmessages = new messages();
		
		Cmessages.clearEmbed();
		Cmessages.setColor(Color.GREEN);
		Cmessages.setAuthor(e.getPlayer().getName(), "https://minotar.net/avatar/"+e.getPlayer().getName()+"/32.png", "https://minotar.net/avatar/"+e.getPlayer().getName()+"/32.png");
		Cmessages.setTitle(e.getPlayer().getName() + " a rejoint le serveur");
		Cmessages.setFooter("Depuis Minecraft", "https://tntgun.fr/img/icon.png");
		
		Cmessages.sendEmbed(452810136319295498L);
		Cmessages.clearEmbed();
		
		if(Bukkit.getOnlinePlayers().size() <= 1) Cmessages.sendMessage(p.getName() + " vient de rejoindre le serveur " + new roles().getRoleMention("Notifs connexions"), 452810136319295498L);
		
		Cmessages.setColor(Color.GREEN);
		Cmessages.setTitle("INFOS MOMENTANÉES :");
		String players = "";
		for(Player p2 : Bukkit.getOnlinePlayers()){
			
			players = players + p2.getName() + ", ";
			
		}
		Cmessages.addfield("Joueurs connectés : " + (Bukkit.getOnlinePlayers().size()), players, false);
		
		
		Cmessages.setFooter("Service d'informations de TntGun", "https://tntgun.fr/img/icon.png");
		
		Cmessages.EditEmbed(414143640995102720L, 464808979219087360L);
		Cmessages.clearEmbed();
		
		
		
		
	}
	
	public boolean isAlphaNum(String text) {
        String regExpression = "[a-zA-Z_0-9]*";

        Pattern p = Pattern.compile(regExpression);
        Matcher m = p.matcher(text);
        return m.matches();

    }
	
	public boolean isUsernamePremium(String username){
		
		try{
			
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        
	        String line;
	        StringBuilder result = new StringBuilder();
	        
	        while((line = in.readLine()) != null){
	            result.append(line);
	        }
	        
	        return !result.toString().equals("");
			
		}catch(IOException e){ e.printStackTrace(); }
		
		return false;
		
        
    }
	
	public boolean isClientPremium(Player p){
		
		
		try{
			
			URL url = new URL("http://session.minecraft.net/game/checkserver.jsp?user=" + p.getName() + "&serverId=" + 0);
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        
	        String line;
	        StringBuilder result = new StringBuilder();
	        
	        while((line = in.readLine()) != null){
	            result.append(line);
	        }
	        
	        return !result.toString().equals("NO");
			
			
			
		}catch(Exception e){ e.printStackTrace(); }
		
		return false;
		
    }
}

package fr.themsou.listener;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import fr.themsou.BedWars.getteam;
import fr.themsou.BedWars.join;
import fr.themsou.TntWars.RunParty;
import fr.themsou.diffusion.api.messages;
import fr.themsou.discord.Counter;
import fr.themsou.main.main;
import fr.themsou.methodes.Inventory;
import fr.themsou.methodes.PInfos;
import fr.themsou.methodes.realDate;

public class QuitListener implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		new Counter().refreshCountersM1();
		
		Player p = e.getPlayer();
		
		Date date = new realDate().getRealDate();
		main.config.set(p.getName() + ".stat.last", date.getDay() + "-" + date.getDate() + "/" + date.getMonth() + " " + date.getHours() + ":" + date.getMinutes());
		main.config.set(p.getName() + ".stat.lastday", date.getDate());
		main.config.set(p.getName() + ".lastday", date.getDate());
		
		/*PInfos.putStat(Bukkit.getOnlinePlayers().size(), "all");
		PInfos.putStat(Bukkit.getOnlinePlayers().size() - 1, "all");
		if(p.isOp()){
			PInfos.putStat(PInfos.getOnlineOperators(), "admin");
			PInfos.putStat(PInfos.getOnlineOperators() - 1, "admin");
		}*/
		
		
		main.config.set(p.getName() + ".time.connect", 0);
		
		if(main.config.getInt(p.getName() + ".punish.ban.day") > 0){
			main.CSQLConnexion.refreshPlayer(p.getName(), 3);
		}else{
			main.CSQLConnexion.refreshPlayer(p.getName(), 2);
		}
		
		if(PInfos.getGame(p).equals("RP") && p.getGameMode() == GameMode.SURVIVAL){
			new Inventory().savePlayerInventory(p, "rp");
		}
		
		
		if(p.getWorld() == Bukkit.getWorld("BedWars")){
			
			if(p.getGameMode() == GameMode.SURVIVAL && main.config.getInt("lapwars.list.status") == 1){
				for(ItemStack item : p.getInventory().getContents()){
					if(item != null){
						if(item.getType() != Material.LEATHER_BOOTS && item.getType() != Material.LEATHER_LEGGINGS){
							p.getWorld().dropItem(p.getLocation(), item);
							p.getInventory().remove(item);
						}
					}
					
				}
			}
			
		}
		
		try{
			Date rd = new realDate().getRealDate();
			String rddate = "[" + rd.getMonth() + "/" + rd.getDay() + " " + rd.getHours() + ":" + rd.getMinutes() + "] ";
			FileWriter writer = new FileWriter(main.logblock.getAbsoluteFile(), true);
			BufferedWriter out = new BufferedWriter(writer);
			out.write(rddate + p.getName() + " À QUITTÉ LE SERVEUR");
			out.newLine();
			out.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
		
		if(main.TntWarsFillea.contains(p)){
			main.TntWarsFillea.remove(p);
			System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
		}
		if(main.TntWarsFilleb.contains(p)){
			main.TntWarsFilleb.remove(p);
			System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
		}
		if(main.TntWarsFille.contains(p)){
			main.TntWarsFille.remove(p);
			System.out.println(p.getName() + " quitte la fille d'attente du TntWars");
		}
		
		RunParty CRunParty = new RunParty();
		CRunParty.Playerquit(p);
		
		if(new getteam().getplayerteam(p) != 0) new join().leaveBedWars(p);
		
		messages Cmessages = new messages();
		
		Cmessages.clearEmbed();
		Cmessages.setColor(Color.RED);
		Cmessages.setAuthor(e.getPlayer().getName(), "https://minotar.net/avatar/"+e.getPlayer().getName()+"/32.png", "https://minotar.net/avatar/"+e.getPlayer().getName()+"/32.png");
		Cmessages.setTitle(e.getPlayer().getName() + " a quitté le serveur");
		Cmessages.setFooter("Depuis Minecraft", "https://vignette.wikia.nocookie.net/minecraftproject/images/3/31/311.png/revision/latest?cb=20120726083051");
		
		Cmessages.sendEmbed(452810136319295498L);
		
		Cmessages.clearEmbed();
		
		Cmessages.setColor(Color.GREEN);
		Cmessages.setTitle("INFOS MOMENTANÉES :");
		String players = "";
		for(Player p2 : Bukkit.getOnlinePlayers()){
			if(!e.getPlayer().getName().equals(p2.getName())){
				players = players + p2.getName() + ", ";
			}
		}
		Cmessages.addfield("Joueurs connectées: " + (Bukkit.getOnlinePlayers().size() - 1), players, false);
		
		
		Cmessages.setFooter("Service d'informations de TntGun", "https://lh3.googleusercontent.com/a-/ACSszfGsjQF2ipqu2U0e3kC8Ev09mOCJZKoIApjWEw=s176-c-k-c0x00ffffff-no-rj-mo");
		
		Cmessages.EditEmbed(414143640995102720L, 464808979219087360L);
		
		Cmessages.clearEmbed();
		
		
		
	}

}

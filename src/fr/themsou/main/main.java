package fr.themsou.main;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import fr.themsou.methodes.*;
import fr.themsou.rp.claim.Claim;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.WorldCreator;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.themsou.BedWars.BedWars;
import fr.themsou.commands.AdminCmd;
import fr.themsou.commands.ClaimCmd;
import fr.themsou.commands.CommandListener;
import fr.themsou.commands.ConfigCmd;
import fr.themsou.commands.EconomyCmd;
import fr.themsou.commands.EntCmd;
import fr.themsou.commands.GamemodeCmd;
import fr.themsou.commands.GeneralCmd;
import fr.themsou.commands.GradeCmd;
import fr.themsou.commands.MiscCmd;
import fr.themsou.commands.OtherCmd;
import fr.themsou.commands.SpawnCmd;
import fr.themsou.commands.TeleportCmd;
import fr.themsou.commands.VipCmd;
import fr.themsou.discord.vocal.VocalEvents;
import fr.themsou.listener.BreakListener;
import fr.themsou.listener.ChangeWorldListener;
import fr.themsou.listener.ChatListener;
import fr.themsou.listener.CloseInventory;
import fr.themsou.listener.CraftListener;
import fr.themsou.listener.CustomEvent;
import fr.themsou.listener.DamageListener;
import fr.themsou.listener.DeadListener;
import fr.themsou.listener.EnchantListener;
import fr.themsou.listener.InventoryListener;
import fr.themsou.listener.JoinListener;
import fr.themsou.listener.ListPingListener;
import fr.themsou.listener.MobGriefListener;
import fr.themsou.listener.MobSpawnListener;
import fr.themsou.listener.MoveListener;
import fr.themsou.listener.QuitListener;
import fr.themsou.listener.furnaceListener;
import fr.themsou.listener.interactListener;
import fr.themsou.rp.tools.setcraft;
import net.milkbowl.vault.economy.Economy;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

public class main extends JavaPlugin implements Listener {

	public static boolean DISCORD_SERVICES = false;

	public FileConfiguration conf = getConfig();
	public static FileConfiguration config;
	public static FileConfiguration configuration;
	public static FileConfiguration passwords;
	public static WorldEditPlugin worldEditPlugin = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
	public static DynmapAPI dynmap = ((DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap"));
	public static Economy economy = null;
	public static SQLConnexion CSQLConnexion;
	public static ArrayList<Player> TntWarsFille = new ArrayList<>();
	public static ArrayList<Player> TntWarsFillea = new ArrayList<>();
	public static ArrayList<Player> TntWarsFilleb = new ArrayList<>();
	public static int bedWarsEmerald = 90;
	public static int bedWarsDiamond = 60;
	public static int timerMinuts = 0;
	public static int ddos = 2;
	public static File logblock = new File("plugins/TntGun/logblock.yml");
	public main mainclass = this;
	public static HashMap<String, ItemStack[]> entLog = new HashMap<>();
	public static HashMap<String, BossBar> bars = new HashMap<>();
	public static HashMap<Player, PlayerInfo> playersInfos = new HashMap<>();
	
	public main getinstance(){
		return this;
	}
	
	@Override
	public void onLoad() {
		super.onLoad();

	}
	

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable(){

		/*File toUnzip = new File("aaaworld.tar");
		File dir = new File("extracted");

		try {
			boolean exist = toUnzip.exists();
			if(exist){
				System.out.println("---> start unzipping...");
				Archiver archiver = ArchiverFactory.createArchiver(toUnzip);
				archiver.extract(toUnzip, dir);
				System.out.println("---> finishing unzipping...");
			}else{
				System.out.println("---> file does not exist, skip unzipping");
			}

		}catch(IOException e) {
			System.out.println("---> error when unzipping");
			e.printStackTrace();
		}*/

		if(!logblock.exists()){
			try{
				logblock.createNewFile();
			}catch(IOException e){ e.printStackTrace(); }
		}
		
		File configFile = new File("plugins/TntGun/configuration.yml");
		if(!configFile.exists()){
			try{
				configFile.createNewFile();
			}catch(IOException e){ e.printStackTrace(); }
		}
		configuration = YamlConfiguration.loadConfiguration(configFile);
		
		configFile = new File("plugins/TntGun/passwords.yml");
		if(!configFile.exists()){
			try{
				configFile.createNewFile();
			}catch(IOException e){ e.printStackTrace(); }
		}
		passwords = YamlConfiguration.loadConfiguration(configFile);
		
		
		super.onEnable();
		System.out.println("Plugin TntGun Activé !");
		
		Bukkit.getServer().getWorlds().add(Bukkit.getWorld("BedWars"));
		Bukkit.getServer().getWorlds().add(Bukkit.getWorld("hub"));
		Bukkit.getServer().getWorlds().add(Bukkit.getWorld("TntWars"));
		
		if(Bukkit.getOnlinePlayers().size() == 0){
			new WorldCreator("BedWars").createWorld();
			new WorldCreator("hub").createWorld();
			new WorldCreator("TntWars").createWorld();
			Bukkit.getServer().getWorlds().add(Bukkit.getWorld("BedWars"));
			Bukkit.getServer().getWorlds().add(Bukkit.getWorld("hub"));
			Bukkit.getServer().getWorlds().add(Bukkit.getWorld("TntWars"));
		}
		
		
		config = conf;
		
		CSQLConnexion = new SQLConnexion("jdbc:mysql://", main.passwords.getString("mysql.url"), main.passwords.getString("mysql.user"), main.passwords.getString("mysql.user"), main.passwords.getString("mysql.mdp"));
		
		CSQLConnexion.connexion();
		
		
		sixSecondsTimer();
		oneSecondTimer();
		
		if(main.config.getInt("bedwars.list.status") == 2){
			main.config.set("bedwars.list.status", 0);
		}
		
		if(!setupEconomy()) Bukkit.shutdown();
		
		
		getCommand("grade").setExecutor(new GradeCmd());
		getCommand("grade").setTabCompleter(new GradeCmd());
		
		getCommand("g").setExecutor(new GamemodeCmd());
		
		getCommand("setmoney").setExecutor(new EconomyCmd());
		getCommand("setmoney").setTabCompleter(new EconomyCmd());
		
		getCommand("config").setExecutor(new ConfigCmd());
		getCommand("config").setTabCompleter(new ConfigCmd());
		
		getCommand("misc").setExecutor(new MiscCmd());
		getCommand("misc").setTabCompleter(new MiscCmd());
		
		getCommand("a").setExecutor(new AdminCmd());
		getCommand("a").setTabCompleter(new AdminCmd());
		
		
		getCommand("discord").setExecutor(new GeneralCmd());
		getCommand("?").setExecutor(new GeneralCmd());
		getCommand("help").setExecutor(new GeneralCmd());
		getCommand("l").setExecutor(new GeneralCmd());
		getCommand("login").setExecutor(new GeneralCmd());
		getCommand("reg").setExecutor(new GeneralCmd());
		getCommand("register").setExecutor(new GeneralCmd());
		
		getCommand("rtp").setExecutor(new TeleportCmd());
		
		getCommand("money").setExecutor(new EconomyCmd());
		getCommand("money").setTabCompleter(new EconomyCmd());
		getCommand("pay").setTabCompleter(new EconomyCmd());
		
		getCommand("spawn").setExecutor(new SpawnCmd());
		getCommand("hub").setExecutor(new SpawnCmd());
		getCommand("lobby").setExecutor(new SpawnCmd());
		getCommand("spawn").setTabCompleter(new SpawnCmd());
		getCommand("hub").setTabCompleter(new SpawnCmd());
		getCommand("lobby").setTabCompleter(new SpawnCmd());
		getCommand("world").setExecutor(new SpawnCmd());
		getCommand("world").setExecutor(new SpawnCmd());
		
		getCommand("ent").setExecutor(new EntCmd());
		getCommand("ent").setTabCompleter(new EntCmd());
		
		getCommand("claim").setExecutor(new ClaimCmd());
		getCommand("claim").setTabCompleter(new ClaimCmd());
		
		getCommand("pack").setExecutor(new OtherCmd());
		getCommand("pack").setTabCompleter(new OtherCmd());
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ListPingListener(), this);
		pm.registerEvents(new JoinListener(this), this);
		pm.registerEvents(new InventoryListener(this), this);
		pm.registerEvents(new EnchantListener(), this);
		pm.registerEvents(new BreakListener(), this);
		pm.registerEvents(new DamageListener(this), this);
		pm.registerEvents(new DeadListener(), this);
		pm.registerEvents(new CommandListener(this), this);
		pm.registerEvents(new MobGriefListener(), this);
		pm.registerEvents(new furnaceListener(), this);
		pm.registerEvents(new QuitListener(this), this);
		pm.registerEvents(new MoveListener(this), this);
		pm.registerEvents(new CraftListener(), this);
		pm.registerEvents(new ChangeWorldListener(this), this);
		pm.registerEvents(new ChatListener(this), this);
		pm.registerEvents(new interactListener(this), this);
		pm.registerEvents(new MobSpawnListener(), this);
		pm.registerEvents(new CloseInventory(), this);
		
		pm.registerEvents(new TeleportCmd(), this);
		pm.registerEvents(new VipCmd(), this);
		pm.registerEvents(new EconomyCmd(), this);
		
		new setcraft(this).setcrafts();
		
		
		Date date = new realDate().getRealDate();
		String stopDate = main.config.getString("stat.list.server.stoptime");
		int hours = Integer.parseInt(stopDate.split("-")[1].split(":")[0]);
		int minut = Integer.parseInt(stopDate.split(":")[1]);
		
		if(date.getHours() != hours || date.getMinutes() > (minut + 15)){
			int stopMinuts = hours * 60 + minut;
			PInfos.putStats(stopMinuts, -1);
			PInfos.putStats(date.getHours() * 60 + date.getMinutes() - 15, -1);
			PInfos.putStats(stopMinuts, 0);
		}else{
			PInfos.putStatsNone();
		}
		
		Bukkit.getScheduler().runTaskLater(this, new Runnable(){
			
			@Override
			public void run(){
				
				new fr.themsou.rp.claim.dynmap().refreshMarkerArea();
				
			}
		}, 200);

		// New claim system converter
		for(Integer id : Claim.getAllClaims()){

			Claim claim = new Claim(id);
			String owner = main.config.getString("claim.list." + claim.getSpawn() + "." + id + ".owner").split(",")[0];

			List<String> guests = new ArrayList<>(); int i = 0;
			for(String guest : main.config.getString("claim.list." + claim.getSpawn() + "." + id + ".owner").split(",")){
				if(i != 0) guests.add(guest);
				i++;
			}

			if(owner.equals("l'etat")) owner = null;

			claim.setOwner(owner);
			claim.setGuests(guests);
		}
		for(String player : main.config.getConfigurationSection("").getKeys(false)){
			if(main.config.contains(player + ".claim")){
				main.config.set(player + ".claim", null);
			}
		}
		for(String ent : main.config.getConfigurationSection("ent.list").getKeys(false)){
			main.config.set("ent.list." + ent + ".claim", null);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		Date date = new realDate().getRealDate();
		
		main.config.set("stat.list.server.stoptime", date.getDate() + "-" + date.getHours() + ":" + date.getMinutes());
		Bukkit.broadcastMessage("§6Le serveur est en train de redémarer !");
		Bukkit.broadcastMessage("§cIl se peut que vous ayez un \"RollBack\"");
		
		fr.themsou.methodes.Inventory CInventory = new fr.themsou.methodes.Inventory();
		for(Player p : Bukkit.getOnlinePlayers()){
			if(PInfos.getGame(p).equals("RP") && p.getGameMode() == GameMode.SURVIVAL){
				CInventory.savePlayerInventory(p, "rp");
			}
		}
		
		
		conf = config;
		saveConfig();
		
		new Boss().removeAll();
		new VocalEvents().playlistEnd();
		CSQLConnexion.disconect();
		
		System.out.println("Plugin TntGun Désactivé !");
		super.onDisable();
		
	}
	
	private void sixSecondsTimer(){
		
		Bukkit.getScheduler().runTaskTimer(this, () -> {

			Date date = new realDate().getRealDate();
			timer Ctimer = new timer();

			Ctimer.timer6S(mainclass);

			if(date.getMinutes() == 0 && main.config.getBoolean("stat.list.issend.hour") == false){ // HOUR
				main.config.set("stat.list.issend.hour", true);
				Ctimer.timerHour(getinstance());

				if(date.getHours() == 0 && main.config.getBoolean("stat.list.issend.day") == false){ // DAY
					main.config.set("stat.list.issend.day", true);
					Ctimer.timerDay(getinstance());

					if(date.getDay() == 1 && main.config.getBoolean("stat.list.issend.week") == false){ // WEEK
						main.config.set("stat.list.issend.week", true);
						Ctimer.timerWeek(getinstance());

					}if(date.getDate() == 0 && main.config.getBoolean("stat.list.issend.mounth") == false){ // MOUNTH
						main.config.set("stat.list.issend.mounth", true);
						Ctimer.timerMounth(getinstance());

					}


				}
			}

			if((date.getMinutes() == 15 || date.getMinutes() == 30 || date.getMinutes() == 45 || date.getMinutes() == 0) && main.config.getBoolean("stat.list.issend.quart") == false){
				main.config.set("stat.list.issend.quart", true);
				Ctimer.timerQuart(getinstance());
			}
			if(date.getMinutes() == 16 || date.getMinutes() == 31 || date.getMinutes() == 46 || date.getMinutes() == 1){ // HOUR

				main.config.set("stat.list.issend.hour", false);
				main.config.set("stat.list.issend.quart", false);

				if(date.getHours() == 1){ // DAY
					main.config.set("stat.list.issend.day", false);

					if(date.getDay() == 2){ // WEEK
						main.config.set("stat.list.issend.week", false);

					}if(date.getDate() == 1){ // MOUNTH
						main.config.set("stat.list.issend.mounth", false);

					}
				}
			}
		},120, 120);
	}

	private void oneSecondTimer() {
		Bukkit.getScheduler().runTaskTimer(this, () -> new timer().timer1S(getinstance()),20, 20);
	}
	
	private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
}

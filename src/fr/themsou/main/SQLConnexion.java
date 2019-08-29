package fr.themsou.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import fr.themsou.discord.Support;
import fr.themsou.methodes.realDate;

public class SQLConnexion {

	
	private Connection connexion;
	private String urlbase,host,database,user,pass;
	
	public SQLConnexion(String urlbase, String host, String database, String user, String pass) {
		
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
		
	}
	//main.CSQLConnexion.setDouble("joueurs", "pseudo", player.getName(), "mdp", args[0]);
	public void connexion() {
		if(!isConnected()){
			try{
				connexion = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
				System.out.println("Connection a la base de données réussie !");
			}catch(SQLException e){
				e.printStackTrace();
			}
		}	
	}
	public void disconect() {
		if(isConnected()){
			try {
				connexion.close();
				System.out.println("Deconnexion de la base de données !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public boolean isConnected(){
		return connexion != null;
	}
	
	public void refreshPlayer(String playerName, int status){ // 1 = connecté    2 = Hors Ligne    3 = Bannis
		
		if(!isConnected()){
			connexion();
			return;
		}
		
		String password = main.config.getString(playerName + ".mdp");
		String role = main.config.getString(playerName + ".grade");
		if(role == null){
			role = "Joueur";
			main.config.set(playerName, null);
		}
		int statusValue = 0;
		if(status == 1) statusValue = main.config.getInt(playerName + ".time.connect");
		else if(status == 2) statusValue = new realDate().getMinutesSpace(main.config.getString(playerName + ".stat.last"));
		else if(status == 3) statusValue = main.config.getInt(playerName + ".punish.ban.day");
		String last = main.config.getString(playerName + ".stat.last");
		int dayTime = main.config.getInt(playerName + ".stat.daytime");
		int weekTime = main.config.getInt(playerName + ".stat.weektime");
		int totalTime = main.config.getInt(playerName + ".time.hub") + main.config.getInt(playerName + ".time.tntwars") + main.config.getInt(playerName + ".time.rp") + main.config.getInt(playerName + ".time.bedwars");
		
		if(password == null) return;
		
		if(main.CSQLConnexion.exist("joueurs", "pseudo", playerName)){
			
			try{
				
				PreparedStatement q = connexion.prepareStatement("UPDATE `joueurs` SET mdp = ?, grade = ?, status = ?, statusvalue = ?, last = ?, daytime = ?, weektime = ?,totaltime = ? WHERE pseudo = ?");
				q.setString(1, password);
				q.setString(2, role);
				q.setInt(3, status);
				q.setInt(4, statusValue);
				q.setString(5, last);
				q.setInt(6, dayTime);
				q.setInt(7, weekTime);
				q.setInt(8, totalTime);
				q.setString(9, playerName);
				q.execute();
				q.close();
			
			}catch(SQLException e){ e.printStackTrace(); }
			
		}else{
			
			try{
				
				PreparedStatement q = connexion.prepareStatement("INSERT INTO joueurs (pseudo, mdp, grade, status, statusvalue, last, daytime, weektime, totaltime) VALUES (?,?,?,?,?,?,?,?,?)");
				q.setString(1, playerName);
				q.setString(2, password);
				q.setString(3, role);
				q.setInt(4, status);
				q.setInt(5, statusValue);
				q.setString(6, last);
				q.setInt(7, dayTime);
				q.setInt(8, weekTime);
				q.setInt(9, totalTime);
				q.execute();
				System.out.println("add player " + playerName + " into the DataBase");
				q.close();
				
			}catch(SQLException e){ e.printStackTrace(); }
			
		}
		
		
	}
	
	public void sendPost(String name, String message, boolean verify){
		
		if(!isConnected()){
			connexion();
			return;
		}
		
		try {
			PreparedStatement q = connexion.prepareStatement("DELETE FROM post WHERE date NOT IN (SELECT date FROM (SELECT date FROM post ORDER BY date DESC LIMIT 50) AS V2)");
			q.execute();
			q.close();
			
			q = connexion.prepareStatement("INSERT INTO post(joueur, text, verify, send) VALUES  (?,?,?,?)");
			q.setString(1, name);
			q.setString(2, message);
			q.setBoolean(3, verify);
			q.setInt(4, 1);
			
			
			q.execute();
			q.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
				
		
		
		
	}
	
	public void updatePost(){
		
		if(!isConnected()){
			connexion();
			return;
		}
		
		try {
			
			PreparedStatement q = connexion.prepareStatement("SELECT joueur, text, verify, date FROM post WHERE send = 0");
			
			ResultSet rs = q.executeQuery();
			
			while (rs.next()){
				
				new Support().sendMessage(rs.getString("joueur"), rs.getString("text"));
				
			}
			q.close();
			
			
			PreparedStatement q2 = connexion.prepareStatement("UPDATE post SET send = 1 WHERE send = 0");
			q2.execute();
			q2.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	public boolean exist(String table, String column, String equals){
		
		if(!isConnected()) return false;
		
		try {
			PreparedStatement q = connexion.prepareStatement("SELECT * FROM " + table + " WHERE " + column + " = ?");
			q.setString(1, equals);
			ResultSet resultat = q.executeQuery();
			boolean existe = resultat.next();
			q.close();
			return existe;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}	
}
















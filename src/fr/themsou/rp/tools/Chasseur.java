package fr.themsou.rp.tools;

import java.math.BigDecimal;
import org.bukkit.entity.Player;

import fr.themsou.main.main;
import fr.themsou.nms.title;

public class Chasseur {
	
	private Player p;
	private String name = "chasseur";
	private double adding = 0.08;
	
	public Chasseur(Player p){
		this.p = p;
	}
	
	
	public void addPCAuto(){
		
		if(getPC() < 100){
			addPC(adding);
			title.sendActionBar(p, "§3+" + adding + "% §bpour la compétence §3" + name + " §4" + getPC() + "/100");
			
		}else{
			setPC(100.0);
			title.sendActionBar(p, "§bVous êtes à §3100% §bde la compétence §3" + name + " !");
		}
	}
	
	public double getPC(){
		
		return main.config.getDouble(p.getName() + ".metier." + name);
		
	}public void setPC(double pc){
		
		main.config.set(p.getName() + ".metier." + name, pc);
		
	}public void addPC(double pc){
		
		BigDecimal xp = new BigDecimal(getPC() + pc).setScale(2, BigDecimal.ROUND_DOWN);
		setPC(xp.doubleValue());
		
	}

}

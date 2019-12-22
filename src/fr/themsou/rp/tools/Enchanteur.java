package fr.themsou.rp.tools;

import java.math.BigDecimal;
import org.bukkit.entity.Player;
import fr.themsou.main.main;
import fr.themsou.nms.title;

public class Enchanteur {
	
	private Player p;
	private String name = "enchanteur";
	
	public Enchanteur(Player p){
		this.p = p;
	}
	
	
	public void addPCAuto(double xp){
		
		xp /= 10;

		if(getPC() >= 50){
			// D = [50 ; 100] | Di = [1 ; 4]
			xp /= -1.9 + getPC() / 16.7;
			xp = new BigDecimal(xp).setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
		}
		
		if(getPC() < 100){
			addPC(xp);
			title.sendActionBar(p, "§3+" + xp + "% §bpour la compétence §3" + name + " §4" + getPC() + "/100");
			
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
		
		BigDecimal xp = new BigDecimal(getPC() + pc).setScale(1, BigDecimal.ROUND_DOWN);
		setPC(xp.doubleValue());
		
	}
	

}

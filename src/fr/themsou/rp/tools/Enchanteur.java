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
	
	
	public void addPCAuto(int xp){
		
		xp /= 5;
		
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

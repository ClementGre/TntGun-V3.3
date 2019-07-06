package fr.themsou.rp.tools;

import java.math.BigDecimal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import fr.themsou.main.main;
import fr.themsou.nms.title;

public class Bucheron {
	
	private Player p;
	private String name = "bucheron";
	private double adding = 0.01;
	
	
	public Bucheron(Player p){
		this.p = p;
	}
	
	public void useAuto(Location loc){
		
		int pc = (int) getPC();
		
		if(pc >= 100){
			breakBlocks(loc, 256);
		}else if(pc >= 85){
			breakBlocks(loc, 10);
		}else if(pc >= 70){
			breakBlocks(loc, 5);
		}else if(pc >= 60){
			breakBlocks(loc, 3);
		}else{
			breakBlocks(loc, 1);
		}
		
	}
	
	
	public void breakBlocks(Location loc, int size){
		
		Block block = loc.getBlock().getRelative(BlockFace.UP);
		
		while(block.getType() == Material.OAK_LOG || block.getType() == Material.SPRUCE_LOG || block.getType() == Material.BIRCH_LOG || block.getType() == Material.JUNGLE_LOG || block.getType() == Material.ACACIA_LOG || block.getType() == Material.DARK_OAK_LOG){
			
			if(size == 0) return;
			
			block.breakNaturally();
			block = block.getRelative(BlockFace.UP);
			size --;
			
		}
		
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
		
		BigDecimal xp = new BigDecimal(getPC() + pc).setScale(3, BigDecimal.ROUND_DOWN);
		setPC(xp.doubleValue());
		
	}

}

package fr.themsou.rp.tools;

import java.math.BigDecimal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.themsou.main.main;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.Spawns;

public class Mineur {
	
	private Player p;
	private String name = "mineur";
	private double adding = 0.025;
	private BlockFace face = BlockFace.NORTH;
	private ItemStack item;
	
	public Mineur(Player p){
		this.p = p;
	}
	
	
	public void useAuto(Location loc, ItemStack item, BlockFace face){
		
		this.face = face;
		this.item = item;
		
		if(!new Spawns().isInSpawn(p.getLocation())){
			int pc = (int) getPC();
			
			if(pc >= 100){
				useLvl5(loc);
			}else if(pc >= 85){
				useLvl4(loc);
			}else if(pc >= 70){
				useLvl3(loc);
			}else if(pc >= 60){
				useLvl2(loc);
			}else{
				useLvl1(loc);
			}
			
		}else title.sendActionBar(p, "§cVous ne pouvez pas utiliser la super pioche dans le spawn");
		
	}
	
	public void useLvl1(Location loc){
		breakBlock(loc.getBlock().getRelative(getSouthFace()));
		
	}public void useLvl2(Location loc){
		useLvl1(loc);
		
		breakBlock(loc.getBlock().getRelative(getNorthFace()));
		
	}public void useLvl3(Location loc){
		useLvl2(loc);
		
		breakBlock(loc.getBlock());
		
		breakBlock(loc.getBlock().getRelative(getEastFace()));
		breakBlock(loc.getBlock().getRelative(getEastFace()).getRelative(getNorthFace()));
		breakBlock(loc.getBlock().getRelative(getEastFace()).getRelative(getSouthFace()));
		
		breakBlock(loc.getBlock().getRelative(getWestFace()));
		breakBlock(loc.getBlock().getRelative(getWestFace()).getRelative(getNorthFace()));
		breakBlock(loc.getBlock().getRelative(getWestFace()).getRelative(getSouthFace()));
		
	}public void useLvl4(Location loc){
		useLvl3(loc);
		
		useLvl3(loc.getBlock().getRelative(face.getOppositeFace()).getLocation());
		
		
	}public void useLvl5(Location loc){
		
		useLvl4(loc);
		
		useLvl52(loc);
		useLvl52(loc.getBlock().getRelative(face.getOppositeFace()).getLocation());
		
	}public void useLvl52(Location loc){
		
		loc = loc.getBlock().getRelative(getEastFace()).getLocation();
		
		breakBlock(loc.getBlock().getRelative(getEastFace()));
		breakBlock(loc.getBlock().getRelative(getEastFace()).getRelative(getNorthFace()));
		breakBlock(loc.getBlock().getRelative(getEastFace()).getRelative(getSouthFace()));
		
		loc = loc.getBlock().getRelative(getWestFace()).getRelative(getWestFace()).getLocation();
		
		breakBlock(loc.getBlock().getRelative(getWestFace()));
		breakBlock(loc.getBlock().getRelative(getWestFace()).getRelative(getNorthFace()));
		breakBlock(loc.getBlock().getRelative(getWestFace()).getRelative(getSouthFace()));
		
	}
	
	public BlockFace getNorthFace(){
		
		if(face == BlockFace.DOWN || face == BlockFace.UP) return BlockFace.NORTH;
		if(face == BlockFace.SOUTH || face == BlockFace.NORTH) return BlockFace.UP;
		if(face == BlockFace.EAST || face == BlockFace.WEST) return BlockFace.UP;
		return null;
		
	}public BlockFace getSouthFace(){
		
		if(face == BlockFace.DOWN || face == BlockFace.UP) return BlockFace.SOUTH;
		if(face == BlockFace.SOUTH || face == BlockFace.NORTH) return BlockFace.DOWN;
		if(face == BlockFace.EAST || face == BlockFace.WEST) return BlockFace.DOWN;
		return null;
		
	}public BlockFace getEastFace(){
		
		if(face == BlockFace.DOWN || face == BlockFace.UP) return BlockFace.EAST;
		if(face == BlockFace.SOUTH || face == BlockFace.NORTH) return BlockFace.EAST;
		if(face == BlockFace.EAST || face == BlockFace.WEST) return BlockFace.NORTH;
		return null;
		
	}public BlockFace getWestFace(){
		
		if(face == BlockFace.DOWN || face == BlockFace.UP) return BlockFace.WEST;
		if(face == BlockFace.SOUTH || face == BlockFace.NORTH) return BlockFace.WEST;
		if(face == BlockFace.EAST || face == BlockFace.WEST) return BlockFace.SOUTH;
		return null;
		
	}
	
	
	public void breakBlock(Block block){
		
		if(block.getType() == Material.STONE || block.getType() == Material.COBBLESTONE || block.getType() == Material.GOLD_ORE ||
			block.getType() == Material.IRON_ORE || block.getType() == Material.COAL_ORE || block.getType() == Material.REDSTONE_ORE ||
			block.getType() == Material.LAPIS_ORE || block.getType() == Material.NETHERRACK || block.getType() == Material.IRON_ORE){
			
			block.breakNaturally(item);
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

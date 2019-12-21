package fr.themsou.rp.tools;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.themsou.main.main;
import fr.themsou.nms.title;
import fr.themsou.rp.claim.GetZoneId;

public class Fermier {
	
	private Player p;
	private String name = "fermier";
	private double adding = 0.023;
	private int id = 0;
	
	public Fermier(Player p){
		this.p = p;
	}
	
	@SuppressWarnings("deprecation")
	public void useAuto(Location loc){
		
		id = new GetZoneId().getIdOfPlayerZone(loc);
		if(getType(loc.getBlock().getType()) == 0){
			return;
		}else if(getType(loc.getBlock().getType()) == 2){
			loc = loc.getBlock().getRelative(BlockFace.DOWN).getLocation();
		}
		
		
		p.getInventory().getItemInMainHand().setDurability((short) (p.getInventory().getItemInMainHand().getDurability() + 1));
		
		if(p.getInventory().getItemInMainHand().getDurability() >= 1561){
			p.getInventory().removeItem(p.getInventory().getItemInMainHand());
		}
		
		int pc = (int) getPC();
		
		if(pc >= 100){
			
			use(get33(loc), false);
			use(get33(loc.getBlock().getRelative(BlockFace.UP).getLocation()), true);
			use(get33(loc.getBlock().getRelative(BlockFace.UP).getLocation()), false);
			
			use(get55(loc), false);
			use(get55(loc.getBlock().getRelative(BlockFace.UP).getLocation()), true);
			use(get55(loc.getBlock().getRelative(BlockFace.UP).getLocation()), false);
		}else if(pc >= 70){
			use(get33(loc), false);
			use(get33(loc.getBlock().getRelative(BlockFace.UP).getLocation()), true);
			use(get33(loc.getBlock().getRelative(BlockFace.UP).getLocation()), false);
		}else if(pc >= 60){
			use(get33(loc), false);
			use(get33(loc.getBlock().getRelative(BlockFace.UP).getLocation()), false);
		}else{
			use(get33(loc), false);
		}
		
	}
	
	public void use(ArrayList<Block> blocks, boolean doBreak){
		
		for(int i = 0; i < blocks.size(); i++){
			if(getType(blocks.get(i).getType()) == 1){
				hoe(blocks.get(i));
			}else if(getType(blocks.get(i).getType()) == 2){
				if(doBreak) remove(blocks.get(i));
			}else{
				if(blocks.get(i).getType() == Material.AIR && blocks.get(i).getRelative(BlockFace.DOWN).getType() == Material.FARMLAND){
					place(blocks.get(i));
				}
			}
		}
		
	}
	public void hoe(Block block){
		block.setType(Material.FARMLAND);
	}
	public void place(Block block){
		if(p.getInventory().containsAtLeast(new ItemStack(Material.WHEAT_SEEDS, 1), 1)){
			block.setType(Material.WHEAT); p.getInventory().removeItem(new ItemStack(Material.WHEAT_SEEDS));
			
		}else if(p.getInventory().containsAtLeast(new ItemStack(Material.POTATO, 1), 1)){
			block.setType(Material.POTATOES); p.getInventory().removeItem(new ItemStack(Material.POTATO));
			
		}else if(p.getInventory().containsAtLeast(new ItemStack(Material.CARROT, 1), 1)){
			block.setType(Material.CARROTS); p.getInventory().removeItem(new ItemStack(Material.CARROT));
		}
	}
	@SuppressWarnings("deprecation")
	public void remove(Block block){
		if(block.getData() == (byte) 7) block.breakNaturally();
	}
	
	public ArrayList<Block> get33(Location loc){
		
		ArrayList<Block> blocks = new ArrayList<>();
		
		blocks.add(loc.getBlock());
		
		blocks.add(loc.getBlock().getRelative(BlockFace.NORTH)); blocks.add(loc.getBlock().getRelative(BlockFace.SOUTH));
		blocks.add(loc.getBlock().getRelative(BlockFace.EAST)); blocks.add(loc.getBlock().getRelative(BlockFace.WEST));
		
		blocks.add(loc.getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST)); blocks.add(loc.getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST));
		blocks.add(loc.getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.WEST)); blocks.add(loc.getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST));
		
		addPC(adding / 5);
		
		return blocks;
		
	}
	
	public ArrayList<Block> get55(Location loc){
		
		ArrayList<Block> blocks = new ArrayList<>();
		
		Block north = loc.getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH);
		Block south =  loc.getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH);
		Block east = loc.getBlock().getRelative(BlockFace.EAST).getRelative(BlockFace.EAST);
		Block west =  loc.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.WEST);
		
		blocks.add(north);
		blocks.add(north.getRelative(BlockFace.EAST)); blocks.add(north.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
		blocks.add(north.getRelative(BlockFace.WEST)); blocks.add(north.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
		
		blocks.add(south);
		blocks.add(south.getRelative(BlockFace.EAST)); blocks.add(south.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST));
		blocks.add(south.getRelative(BlockFace.WEST)); blocks.add(south.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST));
		
		blocks.add(east);
		blocks.add(east.getRelative(BlockFace.NORTH)); blocks.add(east.getRelative(BlockFace.SOUTH));
		
		blocks.add(west);
		blocks.add(west.getRelative(BlockFace.NORTH)); blocks.add(west.getRelative(BlockFace.SOUTH));
		
		for(int i = 0; i < blocks.size(); i++){
			if(id != new GetZoneId().getIdOfPlayerZone(blocks.get(i).getLocation())){
				blocks.remove(i);
			}
		}
		
		
		return blocks;
		
	}
	
	public int getType(Material m){
	
		if(m == Material.GRASS || m == Material.DIRT){
			return 1;
		}if(m == Material.POTATO || m == Material.CARROT || m == Material.WHEAT){
			return 2;
		}if(m == Material.FARMLAND){
			return 3;
		}
		return 0;
		
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

package fr.themsou.methodes;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockTypes;

import fr.themsou.main.main;

public class Schematics {
	
	public void replaceBwBlocks(Location loc1, Location loc2){
		
		
		
		com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(loc1.getWorld());
    	Region region = new CuboidRegion(BlockVector3.at(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ()), BlockVector3.at(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ()));
    	EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);
    	
    	Set<BaseBlock> set = new HashSet<>();
    	set.add(new BlockPattern(BlockTypes.WHITE_WOOL.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.RED_WOOL.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.BLUE_WOOL.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.GREEN_WOOL.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.YELLOW_WOOL.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.WHITE_CONCRETE.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.RED_CONCRETE.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.BLUE_CONCRETE.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.GREEN_CONCRETE.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.YELLOW_CONCRETE.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.OAK_PLANKS.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.END_STONE.getDefaultState()).getBlock());
    	set.add(new BlockPattern(BlockTypes.OBSIDIAN.getDefaultState()).getBlock());
    	
        try{
        	
            es.replaceBlocks(region, set, new BlockPattern(BlockTypes.AIR.getDefaultState()));
            es.flushSession();
            
        }catch(Exception e){  e.printStackTrace(); }
		
	}
	
	
	public void loadSchematic(Location loc, String name){
		
	    
	    File file = new File("plugins" + File.separator + "TntGun" + File.separator + "schematics" + File.separator + name + ".schem");

	    try{
	    	
	    	ClipboardFormat format = ClipboardFormats.findByFile(file);
	    	ClipboardReader reader = format.getReader(new FileInputStream(file));
	    	Clipboard clipboard = reader.read();
	    	
	    	com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(loc.getWorld());
	    	EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);
	    	
	    	Operation operation = new ClipboardHolder(clipboard)
	                .createPaste(editSession)
	                .to(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))
	                .ignoreAirBlocks(true)
	                .build();
	        Operations.complete(operation);
	        editSession.flushSession();
	    	
	    }catch(IOException | WorldEditException e){
	    	Bukkit.broadcastMessage("§cUne erreur est survenue lors du chargement de la map...");
	    	e.printStackTrace();
		}
	    
	}
	
	
	public Location getSelection1(Player p){
		
		try{
		
			Region sl = main.worldEditPlugin.getSession(p).getSelection(main.worldEditPlugin.getSession(p).getSelectionWorld());
			
			
			if(sl == null) return null;
			
			else if(sl.getMinimumPoint() == null) return null;
			
			else{
				
				Location loc = new Location(p.getWorld(), sl.getMinimumPoint().getBlockX(), sl.getMinimumPoint().getBlockY(), sl.getMinimumPoint().getBlockZ());
				return loc;
			}
			
		}catch(IncompleteRegionException e){
			e.printStackTrace();
		}
		return null;
		
	}
	public Location getSelection2(Player p){
		
		try{
			
			Region sl = main.worldEditPlugin.getSession(p).getSelection(main.worldEditPlugin.getSession(p).getSelectionWorld());
			
			if(sl == null) return null;
			
			else if(sl.getMaximumPoint() == null) return null;
			
			else{
				
				Location loc = new Location(p.getWorld(), sl.getMaximumPoint().getBlockX(), sl.getMaximumPoint().getBlockY(), sl.getMaximumPoint().getBlockZ());
				return loc;
			}
			
		}catch(IncompleteRegionException e){
			e.printStackTrace();
		}
		return null;
		
	}
	
}

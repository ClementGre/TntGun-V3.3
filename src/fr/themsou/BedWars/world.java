package fr.themsou.BedWars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

public class world {
	
	public void copyworld(String copy, String paste){
		System.out.println("copy world");
		File copyfile = new File(copy);
		File pastefile = new File(paste);
		
		Bukkit.unloadWorld(Bukkit.getWorld(paste), true);
		deleteFile(pastefile);
		
		try{
			copyFile(copyfile, pastefile);
		}catch (IOException e){
			e.printStackTrace();
		}
		
		
		Bukkit.getServer().getWorlds().add(Bukkit.getWorld(paste));
		new WorldCreator("BedWars").createWorld();
		new WorldCreator("hub").createWorld();
		new WorldCreator("TntWars").createWorld();
		Bukkit.reload();
		
	}
	
	public void regenworld(String world){
		
		File file = new File(world);
		
		Bukkit.unloadWorld(Bukkit.getWorld(world), true);
		deleteFile(file);
		WorldCreator monde = new WorldCreator(world);
		Bukkit.getServer().createWorld(monde);
		Bukkit.reload();
		
	}
	
	public void deleteFile(File file){
		System.out.println("delete file");
		if(file.exists()){
			
			File files[] = file.listFiles();
			
			for(int i = 0; i < files.length; i++){
				
				if(files[i].isDirectory()){
					deleteFile(files[i]);
				}else{
					files[i].delete();
				}
			}
		}
	}
	
	public void copyFile(File copy, File paste) throws IOException{

		if(copy.isDirectory()){
			System.out.println("copy directory");
			if(!paste.exists()){
				paste.mkdir();
			}
			
			String copys[] = copy.list();
			
			for(String file : copys){
				
				File copyFile = new File(copy, file);
				File pasteFile = new File(paste, file);
				copyFile(copyFile, pasteFile);
				
				
			}
			
		}else{
			System.out.println("copy file");
			InputStream in = new FileInputStream(copy);
			OutputStream out = new FileOutputStream(paste);
			
			if(!copy.getName().equals("uid.dat")){
				
				if(!copy.getName().equals("uid.dat")){
					byte[] b = new byte[1024];
					
					int lenght;
					
					while((lenght = in.read(b)) > 0){
						out.write(b, 0, lenght);
					}
				}else System.out.println("No copy file");
				
				
				
				in.close();
				out.close();
				
			}
			
			
		}
	}

}

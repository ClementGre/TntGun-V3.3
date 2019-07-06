package fr.themsou.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class setname {
	
	
	
	public void setPlayerName(Player p, String name){
		
		
		for(Player pl : Bukkit.getServer().getOnlinePlayers()){
			
			if(pl == p) continue;
			
			//REMOVES THE PLAYER
			/*((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)p).getHandle()));
			
			//CHANGES THE PLAYER'S GAME PROFILE
			GameProfile gp = ((CraftPlayer)p).getProfile();
			try {
			Field nameField = GameProfile.class.getDeclaredField("name");
			nameField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);

			nameField.set(gp, name);
			} catch (IllegalAccessException | NoSuchFieldException ex) {
			throw new IllegalStateException(ex);
			}
			
			
			//ADDS THE PLAYER
			((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)p).getHandle()));
			((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(p.getEntityId()));
			((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)p).getHandle()));*/
			
		}
		
		
	}

}

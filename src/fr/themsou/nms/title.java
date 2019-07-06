package fr.themsou.nms;

import org.bukkit.entity.Player;

public class title{
		

	public static void sendTitle(Player player, String msgTitle, String msgSubTitle, int ticks){
		
		
		player.sendTitle(msgTitle, msgSubTitle, 10, 10, ticks);
		
		/*IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + msgTitle + "\"}");
		IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + msgSubTitle + "\"}");
		PacketPlayOutTitle p = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle p2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(p);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(p2);
		sendTime(player, ticks);*/
		
		
	}
	  
	/*private static void sendTime(Player player, int ticks){
		
		PacketPlayOutTitle p = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, ticks, 20);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(p);
		
	}*/
		
	public static void sendActionBar(Player player, String message){
		player.sendActionBar(message);
	}

}	

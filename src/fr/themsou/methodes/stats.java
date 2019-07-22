	package fr.themsou.methodes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.themsou.diffusion.api.messages;

public class stats {			// 560 / 400
	
	
	private Graphics2D g;
	private BufferedImage Bimg;
	
	private int margingLeft = 40;
	private int margingBottom = 30;
	
	private int valueStart = margingLeft + 10;
	private int valueWidth;
	private int valueHeight;
	
	private int valuesMaxLenght;
	private int valuesMaxHeight;
	
	public stats(int longeur, String downSuffix, boolean downAs0, boolean down1s2, int hauteur, String leftSuffix, boolean left1s2){
		
		try{
			
			Image img = ImageIO.read(new File("plugins/TntGun/statistiques/save.png"));
			
			Bimg = (BufferedImage) img;
			g = Bimg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setFont(new Font("Calibri", 0, 18));
			
		}catch(IOException e){ e.printStackTrace(); }
		
		
		valuesMaxLenght = longeur;
		valuesMaxHeight = hauteur;
		
		if(downAs0) valuesMaxLenght++;
		
		valueWidth = (560 - margingLeft - 20) / valuesMaxLenght;
		valueHeight = (400 - margingBottom - 20) / valuesMaxHeight;
		
		setIndcatorLeft(leftSuffix, left1s2);
		setIndcatorDown(downSuffix, downAs0, down1s2);
		
	}
	
	
	public void addvalue(int value){
		
		g.setColor(new Color(76, 202, 226));
		
		int y = 400 - margingBottom - value * valueHeight;
		
		g.fillRect(valueStart, y, valueWidth - 10, value * valueHeight - 1);
		
		valueStart = valueStart + valueWidth;
		
		
	}


	
	private void setIndcatorDown(String suffix, boolean as0, boolean a1s2){
		
		boolean aff = as0;
		
		
		
		for(int a = 0; a <= valuesMaxLenght - 1; a++){
			
			if(aff == true || !a1s2){
				
				int minx = margingLeft + 10 + a * valueWidth;
				int maxx = margingLeft + 10 + a * valueWidth + valueWidth - 10;
				
				if(as0) centerString(g, minx, maxx, 400, 400 - margingBottom, a + 0 + suffix, g.getFont());
				else    centerString(g, minx, maxx, 400, 400 - margingBottom, a + 1 + suffix, g.getFont());
				aff = false;
				
			}else aff = true;
			
			
		}
		
		
		
		
	}
	
	private void setIndcatorLeft(String suffix, boolean a1s2){
		
		boolean aff = true;
		
		for(int a = 0; a <= valuesMaxHeight; a++){
			
			int y = 400 - a * valueHeight - 1 - margingBottom;
			
			g.setColor(Color.WHITE);
			g.fillRect(margingLeft, y, 560, 1);
			
			if(aff == true || !a1s2){
				
				centerString(g, 0, margingLeft, y - 10, y + 10, a + suffix, g.getFont());
				
				aff = false;
				
			}else aff = true;
			
			
		}
		
		
	}
	
	public void save(String name){
		
		g.dispose();
		
		File outputfile = new File("plugins/TntGun/statistiques/" + name + ".png");
		
		try {
			
			ImageIO.write((RenderedImage) Bimg, "png", outputfile);
			
		} catch (IOException e) {e.printStackTrace();}
		
		
	}
	
	public void send(String name, Long channelId, String message){
		
		messages Cmessages = new messages();
		
		File f = new File("plugins/TntGun/statistiques/" + name + ".png");
		Cmessages.sendFile(f, message, channelId);
		
		
	}
	
	public void centerString(Graphics g, int minX, int maxX, int minY, int maxY, String s, Font font) {
		
		
	    FontRenderContext frc = new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rX = (int) Math.round(r2D.getX());
	    int rY = (int) Math.round(r2D.getY());

	    int a = ((maxX - minX) / 2) - (rWidth / 2) - rX;
	    int b = ((maxY - minY) / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, minX + a, minY + b);
	}
	
	
	

}

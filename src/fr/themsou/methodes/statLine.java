package fr.themsou.methodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import fr.themsou.main.main;

public class statLine{			// 1000 / 562
	
	
	private Graphics2D g;
	private int lastX = 40;
	private int lastY = 532;
	private int shift = 0;
	private int downInfosSize;
	private int leftInfosSize;
	private int width;
	private boolean ansM1 = false;
	private boolean redM1 = false;
	private Color lineColor = Color.WHITE;
	private final BufferedImage Bimg = new BufferedImage(1000, 562, BufferedImage.TYPE_INT_ARGB);
	
	public statLine(int width, String downSuffix, boolean downAs0, boolean down1s2, String leftSuffix, boolean left1s2, int downInfosSize, int leftInfosSize){
		
		g = Bimg.createGraphics();
		g.setColor(new Color(54, 57, 63));
		g.fillRect(0, 0, 1000, 562);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setFont(new Font("Arial", 0, 18));
		g.setStroke(new BasicStroke(3));
			
		
		this.downInfosSize = downInfosSize;
		this.leftInfosSize = leftInfosSize;
		this.width = width;
		
		setIndcatorDown(downSuffix, downAs0, down1s2);
		setIndcatorLeft(leftSuffix, left1s2);
		
	}
	
	public void setLegend(String[] names, Color[] colors){
		
		int i = 0;
		int size = 900 / names.length;
		for(String name : names){
			
			int level = 50 + size * i;
			
			g.setColor(colors[i]);
			g.fillRoundRect(level, 15, 20, 20, 5, 5);
			g.setColor(Color.WHITE);
			
			g.drawString(name, level + 27, 32);
			
			i++;
		}
		
	}
	
	public void addvalue(int x, double y){
		
		g.setColor(lineColor);
		
		if(redM1){
			if(y == -1 && ansM1){
				g.setColor(new Color(220, 32, 32));
			}
			if(y == -1){
				ansM1 = true; y = 0;
			}else ansM1 = false;
		}else{
			if(y == 0){
				if(ansM1){
					g.setColor(new Color(255, 255, 255, 0));
				}
				ansM1 = true;	
			}else ansM1 = false;
		}
		
		int space = 480 / (leftInfosSize + 1);
		int newX = (int) (((double) x) / ((double) width) * 960 + 40);
		int newY =  (int) (532 - (y * ((double) space)));
		
		
		newY += shift;
		newX += shift;
		
		if(newY >= 532) newY = 532;
		if(newX <= 0) newX = 40;
		
		g.draw(new Line2D.Float(lastX, lastY, newX, newY));
		
		lastX = newX;
		lastY = newY;
		
	}


	
	private void setIndcatorDown(String suffix, boolean as0, boolean a1s2){
		
		boolean aff = as0;
		int space = 960 / (downInfosSize);
		g.setColor(Color.WHITE);
		
		for(int a = 0; a <= downInfosSize - 1; a++){
			
			if(aff == true || !a1s2){
				
				int minx = a * space + 40;
				int maxx = a * space + space + 40;
				
				
				g.fillRect(minx, 0, 1, 532);
				g.fillRect(minx, 0, 1, 532);
				
				g.setColor(Color.WHITE);
				if(as0) centerString(g, minx, maxx, 535, 562, a + 0 + suffix, g.getFont());
				else    centerString(g, minx, maxx, 535, 562, a + 1 + suffix, g.getFont());
				aff = false;
				g.setColor(Color.GRAY);
				
			}else aff = true;
			
			
		}
		
	}
	
	private void setIndcatorLeft(String suffix, boolean a1s2){
		
		boolean aff = true;
		int space = 480 / (leftInfosSize + 1);
		g.setColor(Color.WHITE);
		
		for(int a = 0; a <= leftInfosSize; a++){
			
			int y = 532 - a * space;
			
			
			g.fillRect(40, y, 1000, 1);
			
			
			if(aff == true || !a1s2){
				g.setColor(Color.GRAY);
				g.fillRect(0, y, 40, 1);
				g.setColor(Color.WHITE);
				centerString(g, 0, 40, y - 20, y, a + suffix, g.getFont());
				
				aff = false;
				
			}else aff = true;
			
			if(a == leftInfosSize){
				g.setColor(new Color(54, 57, 63));
				g.fillRect(0, 0, 1000, y - space);
				
				g.setColor(Color.WHITE);
				g.fillRect(0, y - space, 1000, 1);
			}
			g.setColor(Color.GRAY);
			
		}
		
		
	}
	
	public void save(String name){
		
		g.dispose();
		
		File outputfile = new File("plugins/TntGun/statistiques/" + name + ".png");
		
		try {
			
			ImageIO.write((RenderedImage) Bimg, "png", outputfile);
			
		} catch (IOException e) {e.printStackTrace();}
		
		
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
	
	public void send(String name, Long channelId, String message){

		File f = new File("plugins/TntGun/statistiques/" + name + ".png");
		main.guild.getTextChannelById(channelId).sendMessage(message).addFile(f).queue();
		
		
	}
	
	public void newLine(Color lineColor, int thikness, int shift, boolean redM1) {
		
		if(lastY != 532 && lastX != 40){
			addvalue(width, 0);
		}
		
		if(!redM1) ansM1 = true;
		else ansM1 = false;
		
		this.redM1 = redM1;
		this.lastX = 40 + shift;
		this.lastY = 532 + shift;
		this.shift = shift;
		this.lineColor = lineColor;
		
		g.setStroke(new BasicStroke(thikness));
	}
	
	
	

}

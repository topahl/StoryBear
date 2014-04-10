package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.debug.DebugSettings;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class DummyRenderer extends Renderer implements IRenderer{
	private Imagelib il = Imagelib.getInstance();
	private int lastTile = 0;
	private int panelnum = 0;
	
	public DummyRenderer(){
	}
	
	private BufferedImage getNextMapElement(){
		Integer[] following = il.getFollowingTiles(lastTile, Imagelib.QUERY_FOREGROUND);
		int next = following[(int)(Math.random()*following.length)];
		lastTile = next;
		return il.loadLandscapeTile(next , Imagelib.QUERY_FOREGROUND);
	}

	
	@Override
	public void getNextViewPart(JLabel pane) {
		panelnum++;
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		for(int i=0;i*Ressources.RASTERSIZE<Ressources.WINDOW.width;i++){
			g.drawImage(getNextMapElement(),i*Ressources.RASTERSIZE,0,null);
			if(DebugSettings.vg1tilenum)
				renderText(g,25, lastTile+"", (i*Ressources.RASTERSIZE)+20,100);
			if(DebugSettings.vg1panelborder)
				g.drawRect(i*Ressources.RASTERSIZE, 0, Ressources.RASTERSIZE, Ressources.WINDOW.height);
		}
		if(DebugSettings.vg1panelnum)
			renderText(g,50, panelnum+"", 20, 40);
		
		
		pane.setIcon(new ImageIcon(image));
	}
}

package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.debug.DebugSettings;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.res.StoryBearRandom;

public class BackgroundRenderer extends Renderer implements IRenderer {
	private Imagelib il = Imagelib.getInstance();
	private StoryBearRandom rand = StoryBearRandom.getInstance();
	private int lastTile = 0;
	private int panelnum = 0;
	
	public BackgroundRenderer(){
		
	}
	
	
	@Override
	public void getNextViewPart(JLabel pane) {
		panelnum++;
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		for(int i=0;i*Ressources.RASTERSIZE*4<Ressources.WINDOW.width;i++){
			g.drawImage(getNextMapElement(),i*Ressources.RASTERSIZE*4,0,null);
			if(DebugSettings.bgtilenum)
				renderText(g,((float) (Ressources.STORYTEXTSIZE/Ressources.SCALE)), lastTile+"", (i*4*Ressources.RASTERSIZE)+20,100);
			if(DebugSettings.bgpanelborder)
				g.drawRect(i*4*Ressources.RASTERSIZE, 0, Ressources.RASTERSIZE*4, Ressources.WINDOW.height);
		}
		pane.setIcon(new ImageIcon(image));
		if(DebugSettings.bgtilenum)
			renderText(g,50, panelnum+"", 20, 40);
	}
	
	private BufferedImage getNextMapElement(){
		Integer[] following = il.getFollowingTiles(lastTile, Imagelib.QUERY_BACKGROUND);
		int next = following[rand.nextInt(following.length)];
		lastTile = next;
		return il.loadLandscapeTile(next , Imagelib.QUERY_BACKGROUND, null);
		
	}

}

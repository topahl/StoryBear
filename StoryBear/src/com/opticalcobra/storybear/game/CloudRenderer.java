package com.opticalcobra.storybear.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.debug.DebugSettings;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.res.StoryBearRandom;

public class CloudRenderer extends Renderer implements IRenderer{
	private Imagelib il = Imagelib.getInstance();
	private Random rand = new Random();
	private int lastTile = 0;
	private int panelnum = 0;
	
	@Override
	public void getNextViewPart(JLabel pane) {
		panelnum++;
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Ressources.SKYCOLOR);
		g.fillRect(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		
		for(int i=0;i*Ressources.RASTERSIZE*4<Ressources.WINDOW.width;i++){
			if(rand.nextInt(4)==1)
				g.drawImage(getNextMapElement(),i*Ressources.RASTERSIZE*4,60,null);
			if(DebugSettings.cloudtilenum)
				renderText(g,((float) (Ressources.STORYTEXTSIZE/Ressources.SCALE)), lastTile+"", (i*Ressources.RASTERSIZE)+20,100);
			if(DebugSettings.cloudpanelborder)
				g.drawRect(i*Ressources.RASTERSIZE, 0, Ressources.RASTERSIZE, Ressources.WINDOW.height);
		}
		pane.setIcon(new ImageIcon(image));
		if(DebugSettings.cloudtilenum)
			renderText(g,50, panelnum+"", 20, 40);
	}
	
	
	private BufferedImage getNextMapElement(){
		return il.loadLandscapeTile(0 , Imagelib.QUERY_CLOUDS, null);
	}

}

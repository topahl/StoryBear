package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class BackgroundRenderer implements IRenderer {
	private Imagelib il = Imagelib.getInstance();
	private int lastTile = 0;
	private ArrayList<int[]>dependencies;
	
	public BackgroundRenderer(){
		dependencies = new ArrayList<int[]>();
		dependencies.add(new int[]{0,1,4,7});	//0
		dependencies.add(new int[]{2});						
		dependencies.add(new int[]{2,3});
		dependencies.add(new int[]{0,7});
		dependencies.add(new int[]{5});
		dependencies.add(new int[]{5,6,9});
		dependencies.add(new int[]{0,7});
		dependencies.add(new int[]{8,10});
		dependencies.add(new int[]{5,9});
		dependencies.add(new int[]{10});
		dependencies.add(new int[]{0,7});
	}
	
	
	@Override
	public void getNextViewPart(JLabel pane) {
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		for(int i=0;i*Ressources.RASTERSIZE*4<Ressources.WINDOW.width;i++){
			g.drawImage(getNextMapElement(),i*Ressources.RASTERSIZE*4,0,null);
		}
		pane.setIcon(new ImageIcon(image));
	}
	
	private BufferedImage getNextMapElement(){
		int next =dependencies.get(lastTile)[((int)((Math.random()) * dependencies.get(lastTile).length))];
		lastTile = next;
		return il.loadBackgroundTile("images\\layer_slice_bg.png", next);
		
	}

}

package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.debug.DebugSettings;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class DummyRendererMG extends Renderer implements IRenderer {
	private Imagelib il = Imagelib.getInstance();
	private int lastTile = 0;
	private int panelnum = 0;
	private ArrayList<int[]>dependencies;
	
	public DummyRendererMG(){
		dependencies = new ArrayList<int[]>();
		dependencies.add(new int[]{0,1,4,7,14});	//0
		dependencies.add(new int[]{2});						
		dependencies.add(new int[]{2,3});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{5});
		dependencies.add(new int[]{5,6});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{8});
		dependencies.add(new int[]{9,12});
		dependencies.add(new int[]{10});
		dependencies.add(new int[]{10,11});
		dependencies.add(new int[]{12,9});
		dependencies.add(new int[]{8,13});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{15});
		dependencies.add(new int[]{15,16});
		dependencies.add(new int[]{0});
	}
	
	
	@Override
	public void getNextViewPart(JLabel pane) {
		panelnum++;
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		for(int i=0;i*Ressources.RASTERSIZE*4<Ressources.WINDOW.width;i++){
			g.drawImage(getNextMapElement(),i*Ressources.RASTERSIZE*4,0,null);
			if(DebugSettings.mgtilenum)
				renderText(g,25, lastTile+"", (i*4*Ressources.RASTERSIZE)+20,100);
			if(DebugSettings.mgpanelborder)
				g.drawRect(i*4*Ressources.RASTERSIZE, 0, Ressources.RASTERSIZE*4, Ressources.WINDOW.height);
		}
		pane.setIcon(new ImageIcon(image));
		if(DebugSettings.mgpanelnum)
			renderText(g,50, panelnum+"", 20, 40);
	}
	
	private BufferedImage getNextMapElement(){
		int next =dependencies.get(lastTile)[((int)((Math.random()) * dependencies.get(lastTile).length))];
		lastTile = next;
		return il.loadMiddlegroundTile("images\\layer_slice_mg.png", next);
		
	}

}

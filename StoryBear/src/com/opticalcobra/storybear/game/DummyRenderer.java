package com.opticalcobra.storybear.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class DummyRenderer implements IRenderer{
	private Imagelib il = Imagelib.getInstance();
	private int lastTile = 0;
	private ArrayList<int[]>dependencies;
	
	public DummyRenderer(){
		dependencies = new ArrayList<int[]>();
		dependencies.add(new int[]{0,1,3,5,6,7,12,15,18,23,26});	//0
		dependencies.add(new int[]{2,8,11});						
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{4});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{2,8,11});
		dependencies.add(new int[]{9});
		dependencies.add(new int[]{9,10});
		dependencies.add(new int[]{2,8,11});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{13});
		dependencies.add(new int[]{13,14});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{16});
		dependencies.add(new int[]{16,17});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{19});
		dependencies.add(new int[]{20});
		dependencies.add(new int[]{20,21});
		dependencies.add(new int[]{22});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{24,25});
		dependencies.add(new int[]{25});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{27,28});
		dependencies.add(new int[]{28});
		dependencies.add(new int[]{0});
	}
	
	private BufferedImage getNextMapElement(){
		int next =dependencies.get(lastTile)[((int)((Math.random()) * dependencies.get(lastTile).length))];
		lastTile = next;
		return il.loadLandscapeTile("images\\layer_slice_vg1.png", next);
		
	}

	
	@Override
	public void getNextViewPart(JLabel pane) {
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		for(int i=0;i*Ressources.RASTERSIZE<Ressources.WINDOW.width;i++){
			g.drawImage(getNextMapElement(),i*Ressources.RASTERSIZE,0,null);
		}
		pane.setIcon(new ImageIcon(image));
	}
}

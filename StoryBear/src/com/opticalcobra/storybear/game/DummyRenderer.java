package com.opticalcobra.storybear.game;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class DummyRenderer {
	private Imagelib il = Imagelib.getInstance();
	private int lastTile = 0;
	public BufferedImage getNextMapElement(){
		return il.loadLandscapeTile("images\\slice_ls_front.png", (int)((Math.random()) * 29));
	}
	
	public JLayeredPane getNextWindow(){
		JLayeredPane result = new JLayeredPane();
		int i=0;
		result.setSize(Ressources.SCREEN.width, Ressources.SCREEN.height);
		while(i < Ressources.SCREEN.width){
			JLabel tile = new JLabel();
			tile.setIcon(new ImageIcon(getNextMapElement()));
			tile.setBounds(i, 0, 120, Ressources.SCREEN.height);
			result.add(tile);
			i+=120;
		}
		return result;
	}
}

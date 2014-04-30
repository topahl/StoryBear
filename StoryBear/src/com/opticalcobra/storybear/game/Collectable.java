package com.opticalcobra.storybear.game;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class Collectable implements ILevelAppearance {
	
	private int block;
	private int image_id;
	private static transient Imagelib il = Imagelib.getInstance();
	private static transient Database db = new Database();
	
	public Collectable(int block, int image_id){
		this.block  = block;
		this.image_id = image_id;
	}

	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g, int tileTypeId, int layerID, Component toBeNamed) {
		if (layerID == Ressources.LAYERINTERACTION){
			if (tileTypeId <18 || tileTypeId >22){
			Point position = db.getObjectPosForeground(tileTypeId, Ressources.CONTAINERCOLLECTABLEID);
			
			BufferedImage image = il.loadObjectPic(image_id, "col");
			((JLabel)(toBeNamed)).setIcon(new ImageIcon(image));
			
			//g.drawImage(image, (((block*Ressources.RASTERSIZE))% Ressources.WINDOW.width)+position.x,position.y, null);
			//TODO: generisch machen
			toBeNamed.setBounds((((block*Ressources.RASTERSIZE))% Ressources.WINDOW.width)+position.x,position.y, Ressources.CONTAINERCOLLECTABLE, Ressources.CONTAINERCOLLECTABLE);
			}
		}
	}

}

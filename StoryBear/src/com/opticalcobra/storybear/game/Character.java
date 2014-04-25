package com.opticalcobra.storybear.game;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class Character implements ILevelAppearance {
	private static final long serialVersionUID = -915741575980665387L;
	private int block;
	private int image_id;
	private static transient Imagelib il = Imagelib.getInstance();
	private static transient Database db = new Database();
	
	public Character(int block, int image_id){
		this.block = block;
		this.image_id=image_id;
	}

	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g, int tileTypeId, int layerID, Component toBeNamed) {
		if (layerID == Ressources.LAYERINTERACTION){
			Point position = db.getObjectPos(tileTypeId, 0);
			
			BufferedImage image = il.loadObjectPic(image_id, "Cha");
			g.drawImage(image,(((block*Ressources.RASTERSIZE))% Ressources.WINDOW.width)+position.x,position.y, null);
		}	
	}



}

package com.opticalcobra.storybear.game;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class IllustrationSmall implements ILevelAppearance {

	private static final long serialVersionUID = 1132575478424167628L;
	
	private int block;
	private int image_id;
	private static transient Imagelib il = Imagelib.getInstance();
	private static transient Database db = new Database();
	
	public IllustrationSmall(int block, int image_id){
		this.block = block;
		this.image_id=image_id;
	}

	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g, int tileTypeId, int layerID, Component toBeNamed) {
		if (layerID == Ressources.LAYERFOREGROUNDTWO){
			if ((tileTypeId!=5 && tileTypeId < 18) || tileTypeId == 27){
				Point position = db.getObjectPosForeground(tileTypeId, Ressources.CONTAINERILLUSTRATIONSMALLID);
				
				BufferedImage image = il.loadObjectPic(image_id, "ils");
				g.drawImage(image,(((block*Ressources.RASTERSIZE))% Ressources.WINDOW.width)+position.x,position.y, null);
			}
		}	
	}



}

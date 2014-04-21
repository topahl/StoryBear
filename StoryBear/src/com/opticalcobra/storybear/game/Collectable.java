package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class Collectable implements ILevelAppearance {
	
	private int block;
	private int image_id;
	private static Imagelib il = Imagelib.getInstance();
	
	public Collectable(int block, int image_id){
		this.block  = block;
		this.image_id = image_id;
	}

	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g, int tileTypeId) {
		BufferedImage image = il.loadObjectPic(image_id, "Col");
		g.drawImage(image, (((block*Ressources.RASTERSIZE))% Ressources.WINDOW.width)+1,1, null);
	}

//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//
//

}

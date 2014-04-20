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
		this.block = block;
		this.image_id=image_id;
	}

	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g) {
		BufferedImage image = il.loadCharacterObjectPic(image_id);
		g.drawImage(image, ((block*Ressources.RASTERSIZE))% Ressources.WINDOW.width,Ressources.WINDOW.height/2, null);
	}




}

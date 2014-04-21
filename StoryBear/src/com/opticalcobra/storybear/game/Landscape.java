package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.Imagelib;

public class Landscape implements ILevelAppearance {
	
	private int block;
	private int image_id;
	private static transient Imagelib il = Imagelib.getInstance();
	private static transient Database db = new Database();
	
	public Landscape(int block){
		this.block = block;
	}

	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g, int tileTypeId) {
		// TODO Auto-generated method stub
		
	}



}

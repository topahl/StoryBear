package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.opticalcobra.storybear.main.ILevelAppearance;

public class Character implements ILevelAppearance {
	private static final long serialVersionUID = -915741575980665387L;
	private int block;
	private int image_id;
	
	public Character(int block, int image_id){
		this.block = block;
		this.image_id=image_id;
	}

	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}



}

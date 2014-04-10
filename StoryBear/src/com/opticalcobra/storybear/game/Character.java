package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.opticalcobra.storybear.main.ILevelAppearance;

public class Character implements ILevelAppearance {
	
	private int block;
	
	public Character(int block){
		this.block = block;
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

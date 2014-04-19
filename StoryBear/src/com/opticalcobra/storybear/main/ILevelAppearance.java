package com.opticalcobra.storybear.main;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public interface ILevelAppearance extends Serializable{
	
	public int getBlock();

	public void render(Graphics2D g);
	
	//public getRenderHint();
	
}

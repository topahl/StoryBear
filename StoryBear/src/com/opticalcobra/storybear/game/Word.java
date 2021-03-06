package com.opticalcobra.storybear.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.Ressources;

public class Word extends Renderer implements ILevelAppearance {

	private static final long serialVersionUID = 5483759707356825209L;
	private int block;
	private String text;
	
	public Word(String word,int block){
		this.block = block;
		this.text = word;
		
	}

	@Override
	public int getBlock() {
		return this.block;
	}
	
	public String getWord() {
		return this.text;
	}
	
	public void setBlock(int block) {
		this.block = block;
	}

	@Override
	public void render(Graphics2D g, int tileTypeId, int layerID, Component toBeNamed) {
		if (layerID == Ressources.LAYERFOREGROUNDTWO){
			g.setColor(Color.BLACK);
			renderText(g,((float) (Ressources.STORYTEXTSIZE)), text, ((block*Ressources.RASTERSIZE))% Ressources.WINDOW.width,Ressources.WINDOW.height-20);
		}
	}

	public void renderPreviousLostWord(Graphics2D g, int startTile) {
		g.setColor(Color.BLACK);
		renderText(g,((float) (Ressources.STORYTEXTSIZE)), text, (startTile*Ressources.RASTERSIZE),Ressources.WINDOW.height-20);
	}


}

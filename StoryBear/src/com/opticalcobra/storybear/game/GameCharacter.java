package com.opticalcobra.storybear.game;

import javax.swing.JLabel;

import com.opticalcobra.storybear.res.Ressources;


public class GameCharacter {
	private int height;
	private int width;
	//private int spawnPositionX;
	//private int spawnPositionY;
	private int positionX;
	private int positionY;
	private int currentLevel;	//shows in which height the character has to return after a jump
	
	/**
	 * @author Miriam
	 */
	public GameCharacter(){
		this.setHeight(Ressources.CHARACTERHEIGHT);
		this.setWidth(Ressources.CHARACTERWIDTH);
		//this.spawnPositionX = Ressources.CHARACTERSPAWNPOSITIONX;
		//this.spawnPositionY = Ressources.CHARACTERSPAWNPOSITIONY;
		this.setPositionX(Ressources.CHARACTERSPAWNPOSITIONX);
		this.setPositionY(Ressources.CHARACTERSPAWNPOSITIONY);
		this.setCurrentLevel(Ressources.CHARACTERSPAWNPOSITIONY);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	
	
}

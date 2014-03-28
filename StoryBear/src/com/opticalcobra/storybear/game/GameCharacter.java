package com.opticalcobra.storybear.game;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.res.Ressources;


public class GameCharacter extends JLabel{
	private int height;
	private int width;
	//private int spawnPositionX;
	//private int spawnPositionY;
	private int positionX;
	private int positionY;
	private int currentLevel;	//shows in which height the character has to return after a jump
	
	private int jumpCounter = 0;
	
	//private JLabel characterLabel = new JLabel();
	//private JLayeredPane baseLayer;
	
	/**
	 * @author Miriam
	 */
	public GameCharacter(){
		this.setHeight(Ressources.CHARACTERHEIGHT);
		this.setWidth(Ressources.CHARACTERWIDTH);
		this.setPositionX(Ressources.CHARACTERSPAWNPOSITIONX);
		this.setPositionY(Ressources.CHARACTERSPAWNPOSITIONY);
		this.setCurrentLevel(Ressources.CHARACTERSPAWNPOSITIONY);
		
		this.characterSpawns();
	}
	
	/**
	 * @author Miriam
	 * the game character spawns in the game
	 */
	private void characterSpawns(){
		this.setText("Jump");
		this.setBounds(this.positionX,this.positionY,this.height,this.width);
	}
	
	/**
	 * @author Miriam
	 */
	public void run(char direction){
		double runConstant = Ressources.RUNCONTANT / Ressources.SCALE;
		if(direction == 'l' && this.positionX > 0)
			this.positionX -= (int) runConstant;
		else if(direction == 'r' && this.positionX < (Ressources.WINDOW.width - this.width))
			this.positionX += (int) runConstant;	
		
		this.setBounds(this.positionX, this.positionY, this.height,this.width);
	}
	
	/**
	 * @author Miriam
	 * TODO: �berpr�fen, ob auch eine rechts oder links Taste gedr�ckt ist, damit dann auch in die Richtung gesprungen wird
	 */
	public boolean jump(char jumpDirectionX){
		double newPositionY;
		double jumpConstantX = Ressources.JUMPCONSTANTX / Ressources.SCALE;
		double jumpConstantY = Ressources.JUMPCONSTANTY / Ressources.SCALE;
		float time = this.jumpCounter / ((float) Ressources.GAMESPEED);
		
		//calculate the height of the current jump position
		newPositionY = jumpConstantY * time * time - jumpConstantY * time;
		newPositionY = this.currentLevel + newPositionY * Ressources.GAMESPEED;
		this.positionY = ((int) (newPositionY));
		
		//Rangecheck --> don't run out of window
		if (this.positionY < 0)
			this.positionY = 0;
		else if (this.positionY > (Ressources.WINDOW.height - this.height))
			this.positionY = Ressources.WINDOW.height - this.height;
		
		//calculate the X value of the jump 
		if(jumpDirectionX == 'r')
			this.positionX += jumpConstantX;
		else if(jumpDirectionX == 'l')
			this.positionX -= jumpConstantX;
		
		//Rangecheck --> don't run out of window
		if (this.positionX < 0)
			this.positionX = 0;
		else if (this.positionX > (Ressources.WINDOW.width - this.width))
			this.positionX = Ressources.WINDOW.width - this.width;
		
		this.setBounds(this.positionX,this.positionY,this.height,this.width);
		
		if(this.jumpCounter == 10){
			this.jumpCounter = 0;
			return false;
		}
		else{
			this.jumpCounter++;
			return true;
		}
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

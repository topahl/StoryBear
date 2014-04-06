package com.opticalcobra.storybear.game;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.res.Ressources;


public class Hero extends JLabel{
	private int height;
	private int width;
	//private int spawnPositionX;
	//private int spawnPositionY;
	private int positionX;
	private int positionY;
	private int currentLevel;	//shows in which height the character has to return after a jump
	
	private int jumpCntForFirstJump = 0;
	private int jumpCntForSecondJump = 0;
	private int levelForFirstJump = 0;
	private int levelForSecondJump = 0;
	private int jumpCounter = 0;
	
	//private JLabel characterLabel = new JLabel();
	//private JLayeredPane baseLayer;
	
	/**
	 * @author Miriam
	 */
	public Hero(){
		this.setHeight(Ressources.CHARACTERHEIGHT);
		this.setWidth(Ressources.CHARACTERWIDTH);
		this.setPositionX(Ressources.CHARACTERSPAWNPOSITIONX);
		this.setPositionY(Ressources.CHARACTERSPAWNPOSITIONY);
		this.setCurrentLevel(Ressources.CHARACTERSPAWNPOSITIONY);
		
		this.heroSpawns();
	}
	
	/**
	 * @author Miriam
	 * the game character spawns in the game
	 */
	private void heroSpawns(){
		this.setText("Forrest");
		this.setBounds(this.positionX,this.positionY,this.height,this.width);
	}
	
	/**
	 * @author Miriam
	 */
	public void run(char direction){
		double runConstant = Ressources.RUNCONSTANT / Ressources.SCALE;
		if(direction == 'l' && this.positionX > 0)
			this.positionX -= (int) runConstant;
		else if(direction == 'r' && this.positionX < (Ressources.WINDOW.width - this.width))
			this.positionX += (int) runConstant;	
		
		this.setBounds(this.positionX, this.positionY, this.height,this.width);
	}
	
	/**
	 * @author Miriam
	 * this method decides which kind of jump is executed: a normal jump or a double jump
	 */
	public boolean letHeroJump(boolean doubleJump, char jumpDirectionX){
		//before anything happend I save the level where I have to come back in the end
		if(this.jumpCntForFirstJump == 0)
			this.levelForFirstJump = this.currentLevel;
		
		//decide if it's a double jump or a normal jump
		if(!doubleJump){
			this.jump(this.jumpCntForFirstJump,jumpDirectionX,this.levelForFirstJump);
			
			//check if jump is finished
			if(this.jumpCntForFirstJump == 10){
				this.jumpCntForFirstJump = 0;
				return false;
			}
			else{
				this.jumpCntForFirstJump++;
				return true;
			}		
		}
		else{			
			//if a second jump starts we have to change the "zerolevel" of the character
			if(this.jumpCntForSecondJump == 0)
				this.levelForSecondJump = this.positionY;
			
			this.jump(this.jumpCntForSecondJump,jumpDirectionX,this.levelForSecondJump);
			
			//check if jump is finished
			if(this.jumpCntForSecondJump == 10){
				this.jumpCntForSecondJump = 0;
				
				//change the jumpCntForFirstJump, because otherwise it can happen that the character jumps up again
				if(this.jumpCntForFirstJump < 1)
					this.jumpCntForFirstJump = 10 - this.jumpCntForFirstJump;
				else if(this.jumpCntForFirstJump == 1)
					this.jumpCntForFirstJump = 10 - this.jumpCntForFirstJump + 1;
				else if(this.jumpCntForFirstJump < 6)
					this.jumpCntForFirstJump = 10 - this.jumpCntForFirstJump + 2;
			}
			else
				this.jumpCntForSecondJump++;
			return true;
		}
			
	}
	
	/**
	 * @author Miriam
	 * execution of the jump
	 * TODO: wird vermutlich nicht mehr gebraucht werden --> irgendwann löschen
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
	
	/**
	 * @author Miriam
	 * execution of the jump
	 */
	private void jump(int jumpCounter, char jumpDirectionX, int zeroLevel){
		double newPositionY;
		double jumpConstantX = Ressources.JUMPCONSTANTX / Ressources.SCALE;
		double jumpConstantY = Ressources.JUMPCONSTANTY / Ressources.SCALE;
		float time = jumpCounter / ((float) Ressources.GAMESPEED);
		
		//calculate the height of the current jump position
		newPositionY = jumpConstantY * time * time - jumpConstantY * time;
		newPositionY = zeroLevel + newPositionY * Ressources.GAMESPEED;
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
	}
	
	/**
	 * @author Miriam
	 * checks if Character is in a double jump currently
	 */
	public boolean inADoubleJump(){
		if(this.jumpCntForSecondJump == 0)
			return false;
		else
			return true;
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

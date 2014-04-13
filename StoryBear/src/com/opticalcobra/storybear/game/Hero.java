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

	private int jumpCntForSecondJump = 0;
	private int jumpSpeed = 0;
	private char doubleJumpInitiator = '0';

	
	/**
	 * @author Miriam
	 */
	public Hero(){
		this.height = Ressources.CHARACTERHEIGHT;
		this.width = Ressources.CHARACTERWIDTH;
		this.positionX = Ressources.CHARACTERSPAWNPOSITIONX;
		this.positionY = Ressources.WINDOW.height - Ressources.CHARACTERHEIGHT;
		
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
		
		if(this.positionY >= Ressources.WINDOW.height - this.height){
			this.jumpSpeed = Ressources.SPEEDCONSTANT;
		}
		else if(doubleJump && this.doubleJumpInitiator == '0'){
			this.jumpSpeed = Ressources.SPEEDCONSTANT;
			this.doubleJumpInitiator = '1';
		}
		
		this.jump(jumpDirectionX);
		
		if(this.positionY >= Ressources.WINDOW.height - this.height){
			this.doubleJumpInitiator = '0';
			return false;
		}
		else
			return true;
			
	}
	
	/**
	 * @author Miriam
	 * execution of the jump
	 */
	private void jump(char jumpDirectionX){
		double jumpConstantX = Ressources.JUMPCONSTANTX / Ressources.SCALE;
		double jumpConstantY = Ressources.JUMPCONSTANTY / Ressources.SCALE;
		this.jumpSpeed -= jumpConstantY;
		double newPositionY = this.positionY - this.jumpSpeed;
		
		this.positionY = (int) (newPositionY);
		
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
		
		//TODO: Kollisionskontrolle
		
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
	
	
}

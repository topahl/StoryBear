package com.opticalcobra.storybear.game;

import javax.swing.JLabel;

import com.opticalcobra.storybear.res.Ressources;


public class Hero extends JLabel{
	private int positionX;
	private int positionY;

	private int jumpSpeed = 0;
	private char doubleJumpInitiator = '0';

	
	/**
	 * @author Miriam
	 */
	public Hero(){
		this.positionX = Ressources.CHARACTERSPAWNPOSITIONX;
		this.positionY = Ressources.WINDOW.height - Ressources.CHARACTERHEIGHT;
		
		this.setText("Forrest");
		this.setBounds(Ressources.CHARACTERSPAWNPOSITIONX,
				Ressources.WINDOW.height - Ressources.CHARACTERHEIGHT,
				Ressources.CHARACTERWIDTH,Ressources.CHARACTERHEIGHT);
	}
	
	
	/**
	 * @author Miriam
	 */
	public void run(char direction){
		double runConstant = Ressources.RUNCONSTANT / Ressources.SCALE;
		if(direction == 'l' && this.positionX > 0)
			this.positionX -= (int) runConstant;
		else if(direction == 'r' && this.positionX < (Ressources.WINDOW.width - this.getSize().width))
			this.positionX += (int) runConstant;	
		
		this.setLocation(this.positionX, this.positionY);
	}
	
	/**
	 * @author Miriam
	 * this method decides which kind of jump is executed: a normal jump or a double jump
	 */
	public boolean letHeroJump(boolean doubleJump, char jumpDirectionX){
		
		if(this.positionY >= Ressources.WINDOW.height - this.getSize().height){
			this.jumpSpeed = Ressources.SPEEDCONSTANT;
		}
		else if(doubleJump && this.doubleJumpInitiator == '0'){
			this.jumpSpeed = Ressources.SPEEDCONSTANT;
			this.doubleJumpInitiator = '1';
		}
		
		this.jump(jumpDirectionX);
		
		if(this.positionY >= Ressources.WINDOW.height - this.getSize().height){
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
		else if (this.positionY > (Ressources.WINDOW.height - this.getSize().height))
			this.positionY = Ressources.WINDOW.height - this.getSize().height;
		
		//calculate the X value of the jump 
		if(jumpDirectionX == 'r')
			this.positionX += jumpConstantX;
		else if(jumpDirectionX == 'l')
			this.positionX -= jumpConstantX;
		
		//Rangecheck --> don't run out of window
		if (this.positionX < 0)
			this.positionX = 0;
		else if (this.positionX > (Ressources.WINDOW.width - this.getSize().width))
			this.positionX = Ressources.WINDOW.width - this.getSize().width;
		
		//TODO: Kollisionskontrolle
		
		this.setLocation(this.positionX, this.positionY);
	}
	
	
	
}

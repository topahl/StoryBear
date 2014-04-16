package com.opticalcobra.storybear.game;

import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.exceptions.ImageNotFoundException;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;


public class Hero extends JLabel{

	private int jumpSpeed = 0;
	private char doubleJumpInitiator = '0';

	
	/**
	 * @author Miriam
	 * @throws SQLException 
	 * @throws ImageNotFoundException 
	 */
	public Hero() {	
		Imagelib imageLib = Imagelib.getInstance();
		try {
			this.setIcon(new ImageIcon(imageLib.loadHeroPic('n', 'b')));
		} catch (ImageNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		this.setBounds(Ressources.CHARACTERSPAWNPOSITIONX,
				Ressources.WINDOW.height - Ressources.CHARACTERHEIGHT,
				Ressources.CHARACTERWIDTH,Ressources.CHARACTERHEIGHT);
	}
	
	
	/**
	 * @author Miriam
	 */
	public void run(char direction){
		int posX = this.getLocation().x;
		double runConstant = Ressources.RUNCONSTANT / Ressources.SCALE;
		
		if(direction == 'l' && posX > 0)
			posX -= (int) runConstant;
		//Hero darf nur sich nur zwischen 1. und 3. siebtel bewegen
		else if(direction == 'r' && posX < (Ressources.WINDOW.width/7)*2)
			posX += (int) runConstant;	
		
		this.setLocation(posX, this.getLocation().y);
	}
	
	/**
	 * @author Miriam
	 * this method decides which kind of jump is executed: a normal jump or a double jump
	 */
	public boolean letHeroJump(boolean doubleJump, char jumpDirectionX){
		
		if(this.getLocation().y >= Ressources.WINDOW.height - this.getSize().height){
			this.jumpSpeed = Ressources.SPEEDCONSTANT;
		}
		else if(doubleJump && this.doubleJumpInitiator == '0'){
			this.jumpSpeed = Ressources.SPEEDCONSTANT;
			this.doubleJumpInitiator = '1';
		}
		
		this.jump(jumpDirectionX);
		
		if(this.getLocation().y >= Ressources.WINDOW.height - this.getSize().height){
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
		int posX = this.getLocation().x;
		int posY = this.getLocation().y;
		double jumpConstantY = Ressources.JUMPCONSTANTY / Ressources.SCALE;
		this.jumpSpeed -= jumpConstantY;
		double newPositionY = posY - this.jumpSpeed;
		
		posY = (int) (newPositionY);
		
		//Rangecheck --> don't run out of window
		if (posY < 0)
			posY = 0;
		else if (posY > (Ressources.WINDOW.height - this.getSize().height))
			posY = Ressources.WINDOW.height - this.getSize().height;
		
//		//calculate the X value of the jump 
////		if(jumpDirectionX == 'r')
////			posX += jumpConstantX;
////		else if(jumpDirectionX == 'l')
////			posX -= jumpConstantX;
//		
//		//Rangecheck --> don't run out of window
//		if (posX < 0)
//			posX = 0;
//		else if (posX > (Ressources.WINDOW.width - this.getSize().width))
//			posX = Ressources.WINDOW.width - this.getSize().width;
//		
		//TODO: Kollisionskontrolle
		
		this.setLocation(posX, posY);
	}
	
	
	
}

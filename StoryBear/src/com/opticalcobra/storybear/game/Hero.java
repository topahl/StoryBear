package com.opticalcobra.storybear.game;

import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.exceptions.ImageNotFoundException;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.main.Ringbuffer;
import com.opticalcobra.storybear.res.Ressources;


public class Hero extends JLabel{

	private double jumpSpeed = Ressources.SPEEDCONSTANT;
	private char doubleJumpInitiator = '0';
	private Ringbuffer<Integer> ringbuffer = new Ringbuffer<Integer>(3*17);
	private Imagelib imageLib = Imagelib.getInstance();
	private char type; 		//shows which kind of hero it is, eg. bear, ...
	
	/**
	 * @author Miriam
	 */
	public Hero(char type) {	
		try {
			this.setIcon(new ImageIcon(this.imageLib.loadHeroPic('n', type)));
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
		double runConstant = Ressources.RUNCONSTANT;
		ImageIcon image; 
		
		if(direction == 'l' && posX > 0){
			posX -= (int) runConstant;
			//TODO: reinkommentieren, wenn Links-Geh-Bild vom Bär da ist
			/*try {
				image = new ImageIcon(this.imageLib.loadHeroPic('l', this.type));
				this.setIcon(image);
			} catch (ImageNotFoundException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}*/
		}
		//Hero darf nur sich nur zwischen 1. und 3. siebtel bewegen
		else if(direction == 'r' && posX < Ressources.RASTERSIZE*5){
			posX += (int) runConstant;	
			//TODO: reinkommentieren, wenn Links-Geh-Bild vom Bär da ist
			/*try {
				image = new ImageIcon(this.imageLib.loadHeroPic('r', this.type));
				this.setIcon(image);
			} catch (ImageNotFoundException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}*/
		}
		
		this.setLocation(posX, this.getLocation().y);
		
		if ((getLocation().x-(getLocation().x / Ressources.RASTERSIZE)*Ressources.RASTERSIZE) - runConstant < 0 && getLocation().x < Ressources.RASTERSIZE*5){
			setLocation(getLocation().x, getLocation().y-5);
		}
	}
	
	
	public void runFreazing(int currentCounterStep){
		if(currentCounterStep % Ressources.RASTERSIZE == 0){
			setLocation(getLocation().x, getLocation().y-5);
		}
	}
	
	
	/**
	 * @author Miriam
	 * this method decides which kind of jump is executed: a normal jump or a double jump
	 */
	public boolean letHeroJump(boolean doubleJump){
		
		if(doubleJump && this.doubleJumpInitiator == '0'){
			this.jumpSpeed = Ressources.SPEEDCONSTANT;
			this.doubleJumpInitiator = '1';
		}
		
		this.jump();
		
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
	private void jump(){
		int posX = this.getLocation().x;
		int posY = this.getLocation().y;
		double jumpConstantY = Ressources.JUMPCONSTANTY;
		this.jumpSpeed -= jumpConstantY;
		double newPositionY = posY - this.jumpSpeed;
		
		posY = (int) (newPositionY);
		
		//Rangecheck --> don't run out of window
		if (posY < 0)
			posY = 0;
		else if (posY > (Ressources.WINDOW.height - this.getSize().height)){
			posY = Ressources.WINDOW.height - this.getSize().height;
			this.jumpSpeed = Ressources.SPEEDCONSTANT;
		}
			
		//TODO: Kollisionskontrolle
		
		this.setLocation(posX, posY);
	}

	public void setRingbuffer(Ringbuffer<Integer> ringbuffer) {
		this.ringbuffer = ringbuffer;
	}
}

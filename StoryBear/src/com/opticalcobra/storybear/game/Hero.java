package com.opticalcobra.storybear.game;

import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.db.TileResult;
import com.opticalcobra.storybear.exceptions.ImageNotFoundException;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.main.Ringbuffer;
import com.opticalcobra.storybear.res.Ressources;

/**
 * 
 * SINGLETON
 *
 */
public class Hero extends JLabel{

	private static Hero hero = null;
	
	private Ringbuffer<TileResult> ringbuffer = new Ringbuffer<TileResult>(3*17);
	private Imagelib imageLib = Imagelib.getInstance();
	private Database db = new Database();
	
	private char type; 		//shows which kind of hero it is, eg. bear, ...
	
	//Jump Attributes
	private boolean inADoubleJump = false;
	private double jumpSpeed = 0;

	//Run attributes
	private char runDirection = 'n';
	private int ringbufferCounter = 1;
	
	
	private Hero(){
	}

	public static Hero getInstance(){
		if (hero == null){
			hero = new Hero();
		}
		return hero;
	}
	
	public void initHero(char type){
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
				ringbuffer.top().getTileHeight() - Ressources.CHARACTERHEIGHT,
				Ressources.CHARACTERWIDTH,
				Ressources.CHARACTERHEIGHT);
	}
	
	
	/**
	 * let hero do, whatever he has to do
	 * @author Martika & Tobias
	 */
	public void heroStep(){
		
		if (isInAJump()){
			jump();
		}
		if(runDirection != 'n'){
			run();
		}
	}
	
	

	/**
	 * starts the jump of hero by pressing keys
	 * @author Martika & Tobias
	 */
	public void startJump(){
		if (!inADoubleJump){
			if (isInAJump()){
				inADoubleJump = true;
			}
			jumpSpeed = Ressources.SPEEDCONSTANT;
		}
	}
	
	
	
	/**
	 * @author Miriam
	 * execution of the jump
	 */
	private void jump(){
		int posX = getLocation().x;
		int posY = getLocation().y;
		
		jumpSpeed = jumpSpeed - Ressources.JUMPCONSTANTY;
		posY = (int) (posY - jumpSpeed);
		
		//Rangecheck --> don't run out of window
		if (posY < 0)
			posY = 0;
		else if (posY > (Ressources.WINDOW.height - Ressources.CHARACTERHEIGHT)){
			posY = Ressources.WINDOW.height - Ressources.CHARACTERHEIGHT;
			jumpSpeed = 0;
			inADoubleJump = false;
		}
		
		setLocation(posX, posY);
		
		//Reaching the current Levelheight
		if(getLocation().y >= ringbuffer.top(ringbufferCounter).getTileHeight() - Ressources.CHARACTERHEIGHT){
			setLocation(getLocation().x, ringbuffer.top(ringbufferCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
			jumpSpeed = 0;
			inADoubleJump = false;
		}
	}
	
	
	
	/**
	 * @author Miriam
	 */
	public void run(){
		int posX = this.getLocation().x;
		double runConstant = Ressources.RUNCONSTANT;
		ImageIcon image; 
		
		if(runDirection == 'l' && posX > 0){
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
		else if(runDirection == 'r' && posX < Ressources.RASTERSIZE*5){
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
		
		if ((getLocation().x-(getLocation().x / Ressources.RASTERSIZE)*Ressources.RASTERSIZE) - runConstant < 0 && 
				getLocation().x < Ressources.RASTERSIZE*5 && getLocation().x != 0){
			if (runDirection == 'r'){
				if (ringbufferCounter>5){
					ringbuffer.read();
				}
				else{
					ringbufferCounter++;
				}
			}
			if (runDirection == 'l' && ringbufferCounter>0){
				ringbufferCounter--;
			}
			if (!isInAJump()){
				setLocation(getLocation().x, ringbuffer.top(ringbufferCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
			} 
		}
	}
	
	
	/**
	 * it looks like hero is running
	 * @author Martika
	 * @param currentCounterStep
	 * @param direction 
	 */
	public void runFreazing(int currentCounterStep){
		if(currentCounterStep % Ressources.RASTERSIZE == 0){
			if (runDirection == 'r'){
				if (ringbufferCounter>5){
					ringbuffer.read();
				} 
				else{
					ringbufferCounter++;
				}
			}
			if (runDirection == 'l'){
				ringbufferCounter--;
			}
			if (!isInAJump()){
				setLocation(getLocation().x, ringbuffer.top(ringbufferCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
			}
		}
	}
	
	

	/**
	 * is hero in a jump or not
	 * @author Martika & Tobias
	 * @return
	 */
	public boolean isInAJump(){
		
		if(jumpSpeed > 0){
			//jump up
			return true;
		}
		if (getLocation().y == ringbuffer.top(ringbufferCounter).getTileHeight() - Ressources.CHARACTERHEIGHT){
			//current hero position is current level height
			return false;
		}
		else {
			//falling down
			return true;
		}
	}
	
	public void setRingbuffer(Ringbuffer<TileResult> ringbuffer) {
		this.ringbuffer = ringbuffer;
	}

	public char getRunDirection() {
		return runDirection;
	}

	public void setRunDirection(char runDirection) {
		this.runDirection = runDirection;
	}
}

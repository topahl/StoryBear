package com.opticalcobra.storybear.game;

import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
	
	private char type; 		//shows which kind of hero it is, eg. bear, ...
	
	//Jump Attributes
	private boolean inADoubleJump = false;
	private double jumpSpeed = 0;

	//Run attributes
	private char runDirection = 'n';
	private int ringbufferCounter = 2;    //Es sind auf dem Screen immer 5-6 Kacheln zur freien Bewegung verf�gbar
	
	private int highscore = 0;
	
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
	public void heroStep(int stepCounterLayer){
		
		if (isInAJump()){
			jump();
			if(runDirection != 'n'){
				run(stepCounterLayer);
			}
		} else{
			if(runDirection == 'r' && ringbuffer.top(ringbufferCounter).isWalkableRight()  ||  runDirection == 'l' && ringbuffer.top(ringbufferCounter).isWalkableLeft()){
				run(stepCounterLayer);
			}
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
	public void run(int stepCounterLayer){
		int posX = this.getLocation().x;
		double runConstant = Ressources.RUNCONSTANT;
		ImageIcon image; 
		
		if(runDirection == 'l'){
			if (posX > 0){
				posX -= (int) runConstant;
				//TODO: reinkommentieren, wenn Links-Geh-Bild vom B�r da ist
				/*try {
					image = new ImageIcon(this.imageLib.loadHeroPic('l', this.type));
					this.setIcon(image);
				} catch (ImageNotFoundException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
					e7.printStackTrace();
				}*/
			}
			if (checkIfHeroReachsANewTileByWalkingLeft(stepCounterLayer, runConstant) && !isInAJump()){
				if(ringbuffer.top(ringbufferCounter).isWalkableLeft()){
					setLocation(getLocation().x, ringbuffer.top(ringbufferCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
				}
			}
		}

		else if(runDirection == 'r'){
			if (posX < Ressources.RASTERSIZE*5){
				posX += (int) runConstant;	
				//TODO: reinkommentieren, wenn Links-Geh-Bild vom B�r da ist
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
			if (checkIfHeroReachsANewTileByWalkingRight(stepCounterLayer, runConstant) && !isInAJump()){
				if(ringbuffer.top(ringbufferCounter).isWalkableRight()){
					setLocation(getLocation().x, ringbuffer.top(ringbufferCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
				}
			}
		}
		this.setLocation(posX, this.getLocation().y);
	}
	
	
	/**
	 * requires that hero runs to the left
	 * checks if hero reaches a new Tile. If yes, the height will be adjusted and the Ringbuffer refreshed
	 * @author Martika
	 * @param stepCounterLayer
	 * @param runConstant
	 */
	public boolean checkIfHeroReachsANewTileByWalkingLeft(int stepCounterLayer, double runConstant){
		//Ringbuffer f�r die Tiles aktuallisieren   
		if (getLocation().x + (Ressources.CHARACTERWIDTH / 2) < Ressources.RASTERSIZE*5  ){
			
			//Wenn sich der Foreground um eine ganze Kachel oder noch gar nicht verschoben hat
			if ((stepCounterLayer % Ressources.RASTERSIZE) == 0){
				
				//Befindet sich Hero genau auf einer Kachelgrenze?
				if ((Ressources.RASTERSIZE - ((getLocation().x + (Ressources.CHARACTERWIDTH / 2)) % Ressources.RASTERSIZE)) - runConstant  <= 0 ){
					if (ringbufferCounter>0){
						ringbufferCounter--;
					}
					return true;
				}
			}
			
			//Der Foreground hat sich um keine ganze Kachel verschoben
			 else{ 
				 
				//Befindet sich Hero genau auf einer Kachelgrenze?
				if (((Ressources.RASTERSIZE - ((getLocation().x + (stepCounterLayer % Ressources.RASTERSIZE)) + (Ressources.CHARACTERWIDTH / 2)) % Ressources.RASTERSIZE))  - runConstant  <= 0 ){
						ringbufferCounter--;
						return true;
				}
			}
		} 
		return false;
	}
	
	
	/**
	 * requires that hero runs to the right
	 * checks if hero reaches a new Tile. If yes, the height will be adjusted and the Ringbuffer refreshed
	 * @author Martika
	 * @param stepCounterLayer
	 * @param runConstant
	 */
	public boolean checkIfHeroReachsANewTileByWalkingRight(int stepCounterLayer, double runConstant){
		//Ringbuffer f�r die Tiles aktuallisieren    
		if (getLocation().x + (Ressources.CHARACTERWIDTH / 2) < Ressources.RASTERSIZE*5){
			
			//Wenn sich der Foreground um eine ganze Kachel oder noch gar nicht verschoben hat
			if ((stepCounterLayer % Ressources.RASTERSIZE) == 0){
				
				//Befindet sich Hero genau auf einer Kachelgrenze?
				if (((getLocation().x + (Ressources.CHARACTERWIDTH / 2)) % Ressources.RASTERSIZE) - runConstant < 0){
					if (ringbufferCounter != 6){
						ringbufferCounter++;
					}
					return true;
				}
			}	
			
			//Der Foreground hat sich um keine ganze Kachel verschoben
			 else{
				 
				//Befindet sich Hero genau auf einer Kachelgrenze?
				if (((getLocation().x + (stepCounterLayer % Ressources.RASTERSIZE) + 
						(Ressources.CHARACTERWIDTH / 2)) % Ressources.RASTERSIZE) - runConstant < 0){
					if (ringbufferCounter == 6){
						ringbuffer.read();
					}
					else{
						ringbufferCounter++;
					}
					return true;
				}
			}
		} 
		return false;
	}
	
	
	/**
	 * it looks like hero is running
	 * @author Martika
	 * @param currentCounterStep
	 * @param direction 
	 */
	public void runFreazing(int currentCounterStep){
		if((currentCounterStep + (Ressources.CHARACTERWIDTH/2)) % Ressources.RASTERSIZE == 0 || currentCounterStep ==0){
			
			//Da bis zu 6 Kacheln erreichbar sind, darf erst danach aus dem Ringbuffer gelesen werden
			if (ringbufferCounter==6){
				ringbuffer.read();
				this.highscore += Ressources.SCOREPOINTSFORRUNNING;
			} 
			if (!isInAJump()){
				setLocation(getLocation().x, ringbuffer.top(ringbufferCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
			}
		}
	}
	
	/**
	 * @author Martika
	 * Is Tile walkable?
	 * @return
	 */
	public boolean isHeroAllowedToWalk(){
		
		if (isInAJump() && runDirection != 'n'){
			return true;
		}
		
		//Nur rechts notwendig?
		if (!isInAJump() && (runDirection == 'r' && ringbuffer.top(ringbufferCounter).isWalkableRight() || runDirection == 'l' && ringbuffer.top(ringbufferCounter).isWalkableLeft())){
			return true;
		}
		this.highscore -= Ressources.SCOREDECREASEAFTERMISTAKE;
		if(this.highscore < 0)
			this.highscore = 0;
		return false;
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

	public int getHighscore() {
		return highscore;
	}

	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}
}

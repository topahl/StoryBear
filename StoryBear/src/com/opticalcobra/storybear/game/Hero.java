package com.opticalcobra.storybear.game;

import java.awt.Point;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.db.DBConstants;
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
	private int width = 0;
	
	private LinkedList<TileResult> tileQue;
	private Imagelib imageLib = Imagelib.getInstance();
	
	private char type; 		//shows which kind of hero it is, eg. bear, ...
	
	//Jump Attributes
	private boolean inADoubleJump = false;
	private double jumpSpeed = 0;
	
	
	
	//Run attributes
	private char runDirection = 'n';
	private int queCounter = 0;    //Es sind auf dem Screen immer 5-6 Kacheln zur freien Bewegung verfügbar
	private int annimation=0;

	private int highscore = 0;
	private boolean walkedAtSomething = false;  
	
	private Hero(){
	}

	public static Hero getInstance(){
		if (hero == null){
			hero = new Hero();
		}
		return hero;
	}
	
	public void initHero(char type){
		this.type = type;
		try {
			this.setIcon(new ImageIcon(this.imageLib.loadHeroPic("n", this.type)));
		} catch (ImageNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		if (type == 'f'){
			width = Ressources.CHARACTERWIDTHFAIRY;
		} else{
			width = Ressources.CHARACTERWIDTH;
		}
		
		this.setBounds(Ressources.CHARACTERSPAWNPOSITIONX,
				DBConstants.LEVELHEIGHTZERO - Ressources.CHARACTERHEIGHT,
				width,
				Ressources.CHARACTERHEIGHT);
	}
	
	
	/**
	 * let hero do, whatever he has to do
	 * @author Martika & Tobias
	 */
	public void heroStep(int stepCounterLayer){
		ImageIcon image;
		
		//bear looks in a direction
		try {
			if(!isInAJump()&&runDirection != 'n'){
				image = new ImageIcon(this.imageLib.loadHeroPic(runDirection+(Math.abs(annimation/2)+""), this.type));
			}
			else if(runDirection == 'n'){
				image = new ImageIcon(this.imageLib.loadHeroPic(isInAJump()?"j":"n", this.type));
			}
			else {
				image = new ImageIcon(this.imageLib.loadHeroPic("j"+runDirection, this.type));
			}
			
			this.setIcon(image);
			annimation=annimation>=8?-6:annimation+1;
			
			
		} catch (ImageNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		if (isInAJump()){
			jump();
			if(runDirection != 'n'){
				run(stepCounterLayer);
			}
		} else{
			if(runDirection == 'r' && tileQue.get(queCounter).isWalkable()  ||  runDirection == 'l' && tileQue.get(queCounter).isWalkable()){
				run(stepCounterLayer);
			}
		}
		
		if (!tileQue.get(queCounter).isWalkable() && !isInAJump()){
			spawnBack();
		}
		
	}
	
	/**
	 * @author Martika
	 */
	private void spawnBack() {
		
		int counter = 0;
		
		while (!tileQue.get(queCounter).isWalkable()){
			queCounter--;
			counter ++;
		}
		if (super.getLocation().x - counter * Ressources.RASTERSIZE < 0){
			queCounter = queCounter + counter;
			counter = 0;		
			while (!tileQue.get(queCounter).isWalkable()){
				queCounter++;
				counter ++;
			}
			setLocation(super.getLocation().x + counter * Ressources.RASTERSIZE, tileQue.get(queCounter).getTileHeight());
			
		} else{
			setLocation(super.getLocation().x - counter * Ressources.RASTERSIZE, tileQue.get(queCounter).getTileHeight());
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
		int posX = super.getLocation().x;
		int posY = super.getLocation().y;
		
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
		if(getLocation().y >= tileQue.get(queCounter).getTileHeight() - Ressources.CHARACTERHEIGHT){
			setLocation(super.getLocation().x, tileQue.get(queCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
			jumpSpeed = 0;
			inADoubleJump = false;
		}
	}
	
	
	/**
	 * @author Miriam
	 */
	public void run(int stepCounterLayer){
		int posX = super.getLocation().x;
		double runConstant = Ressources.RUNCONSTANT;
		ImageIcon image; 
		
		if(runDirection == 'l'){
			if (posX > 0){
				posX -= (int) runConstant;
			}
			if (checkIfHeroReachsANewTileByWalkingLeft(stepCounterLayer, runConstant)){
				if(this.tileQue.get(queCounter).getInteractionObjectLabel() != null){
					this.tileQue.get(queCounter).getInteractionObjectLabel().setVisible(false);
				}
				//this.highscore
				
				if(!isInAJump()){
					if(tileQue.get(queCounter).isWalkable()){
						setLocation(super.getLocation().x, tileQue.get(queCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
					}
				}
			}
		}

		else if(runDirection == 'r'){
			if (posX < Ressources.RASTERSIZE*5){
				posX += (int) runConstant;	
			}
			if (checkIfHeroReachsANewTileByWalkingRight(stepCounterLayer, runConstant)){
				if(this.tileQue.get(queCounter).getInteractionObjectLabel() != null){
					this.tileQue.get(queCounter).getInteractionObjectLabel().setVisible(false);
				}
				if(!isInAJump()){
					if(tileQue.get(queCounter).isWalkable()){
						setLocation(super.getLocation().x, tileQue.get(queCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
					}
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
		//Ringbuffer für die Tiles aktuallisieren   
		if (getLocation().x < Ressources.RASTERSIZE*5  ){
			
			//Wenn sich der Foreground um eine ganze Kachel oder noch gar nicht verschoben hat
			if ((stepCounterLayer % Ressources.RASTERSIZE) == 0){
				
				//Befindet sich Hero genau auf einer Kachelgrenze?
				if ((Ressources.RASTERSIZE - (getLocation().x % Ressources.RASTERSIZE)) - runConstant  <= 0 ){
					if (queCounter>0){
						queCounter--;
					}
					return true;
				}
			}
			
			//Der Foreground hat sich um keine ganze Kachel verschoben
			 else{ 
				 
				//Befindet sich Hero genau auf einer Kachelgrenze?
				if (((Ressources.RASTERSIZE - (getLocation().x + (stepCounterLayer % Ressources.RASTERSIZE)) % Ressources.RASTERSIZE))  - runConstant  <= 0 ){
						queCounter--;
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
		//Ringbuffer für die Tiles aktuallisieren    
		if (getLocation().x < Ressources.RASTERSIZE*5){
			
			//Wenn sich der Foreground um eine ganze Kachel oder noch gar nicht verschoben hat
			if ((stepCounterLayer % Ressources.RASTERSIZE) == 0){
				
				//Befindet sich Hero genau auf einer Kachelgrenze?
				if ((getLocation().x % Ressources.RASTERSIZE) - runConstant < 0){
					queCounter++;
					return true;
				}
			}	
			
			//Der Foreground hat sich um keine ganze Kachel verschoben
			 else{
				 
				//Befindet sich Hero genau auf einer Kachelgrenze?
				if (((getLocation().x + (stepCounterLayer % Ressources.RASTERSIZE)) % Ressources.RASTERSIZE) - runConstant < 0){
					queCounter++;
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
			
			queCounter++;
			this.highscore += Ressources.SCOREPOINTSFORRUNNING;
			if (!isInAJump()){
				setLocation(super.getLocation().x, tileQue.get(queCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
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
			this.walkedAtSomething = false;
			return true;
		}
		
		//Nur rechts notwendig?
		if (!isInAJump() && (runDirection == 'r' && tileQue.get(queCounter).isWalkable() || 
				runDirection == 'l' && tileQue.get(queCounter).isWalkable())){
			this.walkedAtSomething = false;
			return true;
		}
		
		//don't decrease score just because you are permanently running against a wall :-D
		if(this.walkedAtSomething == false){
			this.highscore -= Ressources.SCOREDECREASEAFTERMISTAKE;
			if(this.highscore < 0)
				this.highscore = 0;
			this.walkedAtSomething = true;
		}
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
		if (getLocation().y == tileQue.get(queCounter).getTileHeight() - Ressources.CHARACTERHEIGHT){
			//current hero position is current level height
			return false;
		}
		else {
			//falling down
			return true;
		}
	}
	
	@Override
	public Point getLocation(){
		Point pnt = super.getLocation();
		pnt.setLocation(pnt.x+(Ressources.CHARACTERWIDTH/2), pnt.y);
		return pnt;
	}
	
	
	public void setTileQue(LinkedList<TileResult> tileQue) {
		this.tileQue = tileQue;
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
	
	public int getQueCounter() {
		return queCounter;
	}

	public void setQueCounter(int queCounter) {
		this.queCounter = queCounter;
	}
}

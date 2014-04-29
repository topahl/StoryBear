package com.opticalcobra.storybear.game;

import java.awt.Point;
import java.sql.SQLException;
import java.util.LinkedList;
import java.awt.Component;

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
	public LinkedList<TileResult> getTileQue() {
		return tileQue;
	}

	private Imagelib imageLib = Imagelib.getInstance();
	
	private char type; 		//shows which kind of hero it is, eg. bear, ...
	
	//Jump Attributes
	private boolean inADoubleJump = false;
	private double jumpSpeed = 0;
	private boolean inRunFreazing = false;
	private boolean justSpawned = false;
	
	
	//Run attributes
	private char runDirection = 'n';
	private int queCounter = 0;    //Es sind auf dem Screen immer 5-6 Kacheln zur freien Bewegung verfügbar
	private int annimation=0;

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
				justSpawned = false;
				run(stepCounterLayer);
			}
		} else{
			if(runDirection == 'r' && tileQue.get(queCounter).isWalkable()  ||  runDirection == 'l' && tileQue.get(queCounter).isWalkable()){
				run(stepCounterLayer);
				justSpawned = false;
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
		
		//decrese the score when you make a mistake
		this.highscore -= Ressources.SCOREDECREASEAFTERMISTAKE;
		if(this.highscore < 0)
			this.highscore = 0;
		
		inRunFreazing = false;
		
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
			setLocation(super.getLocation().x + (counter * Ressources.RASTERSIZE), tileQue.get(queCounter).getTileHeight()-Ressources.CHARACTERHEIGHT);
		} else{
			setLocation(super.getLocation().x - (counter * Ressources.RASTERSIZE), tileQue.get(queCounter).getTileHeight()-Ressources.CHARACTERHEIGHT);
		}
		justSpawned = true;
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
		
		//Collision with collectables
		if(this.tileQue.get(queCounter).getInteractionObjectLabel() != null){
			if((this.tileQue.get(queCounter).getInteractionObjectLabel().getLocationOnScreen().x <= this.getLocation().x) &&
					(this.tileQue.get(queCounter).getInteractionObjectLabel().getLocationOnScreen().x + Ressources.CONTAINERCOLLECTABLE >= this.getLocation().x) && 
					(this.tileQue.get(queCounter).getInteractionObjectLabel().getLocationOnScreen().y <= this.getLocation().y + this.getHeight()/2) &&
					(this.tileQue.get(queCounter).getInteractionObjectLabel().getLocationOnScreen().y + Ressources.CONTAINERCOLLECTABLE >= this.getLocation().y + this.getHeight()/2)){
				this.tileQue.get(queCounter).getInteractionObjectLabel().setVisible(false);
				this.tileQue.get(queCounter).setInteractionObjectLabel(null);
				this.highscore += Ressources.SCOREPERCOLLECTABLE;
			}
		}
		
		if(runDirection == 'l'){
			setInRunFreazing(false);
			if (posX  > 0){
				posX -= (int) runConstant;
			}
			if (checkIfHeroReachsANewTileByWalkingLeft(stepCounterLayer, runConstant)){
				if(!isInAJump()){
					if(tileQue.get(queCounter).isWalkable()){
						setLocation(super.getLocation().x, tileQue.get(queCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
					}
				}
			}
		}

		else if(runDirection == 'r'){
			if (posX + (width/2) < Ressources.RASTERSIZE*5){
				posX += (int) runConstant;	
			}
			if (!inRunFreazing){
				if (checkIfHeroReachsANewTileByWalkingRight(stepCounterLayer, runConstant)){
					if(!isInAJump()){
						if(tileQue.get(queCounter).isWalkable()){
							setLocation(super.getLocation().x, tileQue.get(queCounter).getTileHeight() - Ressources.CHARACTERHEIGHT);
						}
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
				 
			//Befindet sich Hero genau auf einer Kachelgrenze?
			if (((Ressources.RASTERSIZE - (getLocation().x + (stepCounterLayer % Ressources.RASTERSIZE)) % Ressources.RASTERSIZE))  - runConstant  <= 0 ){
					queCounter--;
					return true;
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

		if (getLocation().x < Ressources.RASTERSIZE*5){
	
			if ((((getLocation().x + (stepCounterLayer % Ressources.RASTERSIZE)) % Ressources.RASTERSIZE) - runConstant < 0 ||
					(Ressources.RASTERSIZE*5 - getLocation().x < runConstant && stepCounterLayer% Ressources.RASTERSIZE != 0)) &&
					justSpawned == false){ 
				
					queCounter++;
				return true;
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
		if((currentCounterStep) % Ressources.RASTERSIZE == 0 || currentCounterStep ==0){
			
			queCounter++;

			//Collision with collectables
			if(this.tileQue.get(queCounter).getInteractionObjectLabel() != null){
				if((this.tileQue.get(queCounter).getInteractionObjectLabel().getLocationOnScreen().x <= this.getLocation().x) &&
						(this.tileQue.get(queCounter).getInteractionObjectLabel().getLocationOnScreen().x + Ressources.CONTAINERCOLLECTABLE >= this.getLocation().x) && 
						(this.tileQue.get(queCounter).getInteractionObjectLabel().getLocationOnScreen().y <= this.getLocation().y + this.getHeight()/2) &&
						(this.tileQue.get(queCounter).getInteractionObjectLabel().getLocationOnScreen().y + Ressources.CONTAINERCOLLECTABLE >= this.getLocation().y + this.getHeight()/2)){
					this.tileQue.get(queCounter).getInteractionObjectLabel().setVisible(false);
					this.tileQue.get(queCounter).setInteractionObjectLabel(null);
					this.highscore += Ressources.SCOREPERCOLLECTABLE;
				}
			}
			
			this.highscore += Ressources.SCOREPOINTSFORRUNNING;
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
		if (!isInAJump() && (runDirection == 'r' && tileQue.get(queCounter).isWalkable() || 
				runDirection == 'l' && tileQue.get(queCounter).isWalkable())){
			return true;
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
			if (jumpSpeed == 0 && getLocation().y == tileQue.get(queCounter-1).getTileHeight() - Ressources.CHARACTERHEIGHT){
				return false;
			}
			//falling down
			return true;
		}
	}
	
	@Override
	public Point getLocation(){
		Point pnt = super.getLocationOnScreen();
		pnt.setLocation(pnt.x+(width/2), pnt.y);
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
	
	public int getWidth() {
		return width;
	}

	public boolean isInRunFreazing() {
		return inRunFreazing;
	}

	public void setInRunFreazing(boolean inRunFreazing) {
		this.inRunFreazing = inRunFreazing;
	}
}

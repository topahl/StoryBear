package com.opticalcobra.storybear.db;

import javax.swing.JLabel;


/**
 * 
 * @author Martika
 *
 */
public class TileResult {

	private int tileType;
	private int tileHeight;
	private boolean walkable;
	private transient JLabel interactionObjectLabel;
	
	/**
	 * 
	 * @param tileType
	 * @param tileHeight
	 */
	public TileResult(int tileType, int tileHeight, boolean walkable, JLabel label){
		this.tileType = tileType;
		this.tileHeight = tileHeight;
		this.walkable = walkable;
		if(label != null){
			this.interactionObjectLabel = label;
		}
	}


	public int getTileType() {
		return tileType;
	}


	public void setTileType(int tileType) {
		this.tileType = tileType;
	}


	public int getTileHeight() {
		return tileHeight;
	}


	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}


	public boolean isWalkable() {
		return walkable;
	}


	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}


	public JLabel getInteractionObjectLabel() {
		return interactionObjectLabel;
	}


	public void setInteractionObjectLabel(JLabel interactionObjectLabel) {
		this.interactionObjectLabel = interactionObjectLabel;
	}
}

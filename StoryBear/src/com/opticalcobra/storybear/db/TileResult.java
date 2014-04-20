package com.opticalcobra.storybear.db;


/**
 * 
 * @author Martika
 *
 */
public class TileResult {

	private int tileType;
	private int tileHeight;
	private boolean walkableLeft;
	private boolean walkableRight;
	
	/**
	 * 
	 * @param tileType
	 * @param tileHeight
	 */
	public TileResult(int tileType, int tileHeight, boolean walkableLeft, boolean walkableRight){
		this.tileType = tileType;
		this.tileHeight = tileHeight;
		this.walkableLeft = walkableLeft;
		this.walkableRight = walkableRight;
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


	public boolean isWalkableLeft() {
		return walkableLeft;
	}

	public void setWalkableLeft(boolean walkableLeft) {
		this.walkableLeft = walkableLeft;
	}
	
	public boolean isWalkableRight() {
		return walkableRight;
	}

	public void setWalkableRight(boolean walkableRight) {
		this.walkableRight = walkableRight;
	}
}

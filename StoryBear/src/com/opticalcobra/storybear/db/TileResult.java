package com.opticalcobra.storybear.db;


/**
 * 
 * @author Martika
 *
 */
public class TileResult {

	private int tileType;
	private int tileHeight;
	private boolean walkable;
	
	/**
	 * 
	 * @param tileType
	 * @param tileHeight
	 */
	public TileResult(int tileType, int tileHeight, boolean walkable){
		this.tileType = tileType;
		this.tileHeight = tileHeight;
		this.walkable = walkable;
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
	
}

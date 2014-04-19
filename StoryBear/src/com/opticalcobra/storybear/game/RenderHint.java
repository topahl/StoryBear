package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;

import com.opticalcobra.storybear.main.ILevelAppearance;

public class RenderHint implements ILevelAppearance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3391484487924232956L;
	private int block;
	private int importance; 	//if the same group comes several times, the importance increases
	private int renderHint;
	
	public static final int RENDERHINT_NONE = -1;
	public static final int RENDERHINT_WATER = 0;
	public static final int RENDERHINT_MOUNTAINS = 1;
	public static final int RENDERHINT_CITY = 2;
	public static final int RENDERHINT_WINTER = 3;
	
	
	public static final int WORDGROUP_LENGTH = 3;
	public static final String[] WORDGROUP_WATER = {"Wasser","Fluss","See"};
	public static final String[] WORDGROUP_MOUNTAINS = {"Gebirge","Berg"};
	public static final String[] WORDGROUP_CITY = {"Stadt","Hof","Dorf"};
	public static final String[] WORDGROUP_WINTER = {"Winter","Weihnachten"};
	

	public RenderHint(int block, int hint, int importance){
		this.block = block;
		this.renderHint = hint;
		this.setImportance(importance);
	}
	
	
	public int getRenderHint(){
		return this.renderHint;
	}
	
	
	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}


	public int getImportance() {
		return importance;
	}


	public void setImportance(int importance) {
		this.importance = importance;
	}

}

package com.opticalcobra.storybear.game;

import java.awt.Component;
import java.awt.Graphics2D;

import com.opticalcobra.storybear.main.ILevelAppearance;

public class RenderHint implements ILevelAppearance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3391484487924232956L;
	private int block;
	private int occurance; 	//if the same group comes several times, the importance increases
	private int renderHint;
	
	public static final int RENDERHINT_NONE = -1;
	public static final int RENDERHINT_FOREST = 0;
	public static final int RENDERHINT_CITY = 1;
	public static final int RENDERHINT_VILLAGE = 2;	
	public static final int RENDERHINT_CASTLE = 3;
	public static final int RENDERHINT_LANDSCAPE = 4;
	
	
	public static final int WORDGROUP_LENGTH = 3;
	public static final String[] WORDGROUP_CITY = {"Stadt"};
	public static final String[] WORDGROUP_VILLAGE = {"Hof","Dorf"};
	public static final String[] WORDGROUP_CASTLE = {"Schloss","Burg"};
	public static final String[] WORDGROUP_FOREST = {"Wald","Baum"};
	public static final String[] WORDGROUP_LANDSCAPE = {"Land","Feld","Wiese"};
	

	public RenderHint(int block, int hint, int importance){
		this.block = block;
		this.renderHint = hint;
		this.occurance = importance;
	}
	
	
	public int getRenderHint(){
		return this.renderHint;
	}
	
	public void setBlock(int block){
		this.block = block;
	}
	
	
	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g, int tileTypeId, int layerID, Component toBeNamed) {
		// TODO Auto-generated method stub

	}


	public int getImportance() {
		return occurance;
	}


	public void setImportance(int importance) {
		this.occurance = importance;
	}

}

package com.opticalcobra.storybear.game;

import com.opticalcobra.storybear.main.ILevelAppearance;

public class Character implements ILevelAppearance {
	
	private int block;
	
	public Character(int block){
		this.block = block;
	}

	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void setBlock(int block) {
		this.block = block;
	}

}

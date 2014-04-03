package com.opticalcobra.storybear.game;

import com.opticalcobra.storybear.main.ILevelAppearance;

public class Collectable implements ILevelAppearance {
	
	private int block;
	
	public Collectable(int block){
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

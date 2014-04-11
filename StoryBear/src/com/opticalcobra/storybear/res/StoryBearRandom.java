package com.opticalcobra.storybear.res;

import java.util.Random;

public class StoryBearRandom extends Random {
	
	private static StoryBearRandom instance;
	
	private StoryBearRandom(){
	}
	
	public static StoryBearRandom getInstance() {
		if(instance == null)
			instance = new StoryBearRandom();
		return instance;
	}
	
}

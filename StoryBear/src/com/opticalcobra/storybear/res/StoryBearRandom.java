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
	public static int hash(String text){
		int hash=7;
		for (int i=0; i < text.length(); i++) {
		    hash = hash*31+text.charAt(i);
		}
		return hash;
	}
	
}

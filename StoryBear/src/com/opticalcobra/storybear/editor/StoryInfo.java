package com.opticalcobra.storybear.editor;

import java.util.ArrayList;
import com.opticalcobra.storybear.main.ILevelAppearance;

public class StoryInfo {
	
	private int numberOfBlocks = 0;
	private Story story;
	private int hash;
	private ArrayList<ILevelAppearance> elements;
	
	
	public StoryInfo(){
		
	}


	public int getNumberOfBlocks() {
		return numberOfBlocks;
	}


	public void setNumberOfBlocks(int numberOfBlocks) {
		this.numberOfBlocks = numberOfBlocks;
	}


	public Story getStory() {
		return story;
	}


	public void setStory(Story story) {
		this.story = story;
	}


	public int getHash() {
		return hash;
	}


	public void setHash(int hash) {
		this.hash = hash;
	}


	public ArrayList<ILevelAppearance> getElements() {
		return elements;
	}


	public void setElements(ArrayList<ILevelAppearance> elements) {
		this.elements = elements;
	}

}

package com.opticalcobra.storybear.editor;

import java.io.Serializable;
import java.util.ArrayList;

import com.opticalcobra.storybear.main.ILevelAppearance;

/**
 * This class contains all information to render a level. This file can be exchanged to share a level.
 * @author Tobias
 *
 */
public class StoryInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8932783666533175430L;
	
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


	public ArrayList<ILevelAppearance> getElements() {
		return elements;
	}


	public void setElements(ArrayList<ILevelAppearance> elements) {
		this.elements = elements;
	}


}

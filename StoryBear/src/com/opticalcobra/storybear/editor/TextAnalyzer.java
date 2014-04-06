package com.opticalcobra.storybear.editor;

import java.util.ArrayList;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.game.Character;
import com.opticalcobra.storybear.game.Collectable;
import com.opticalcobra.storybear.game.Landscape;
import com.opticalcobra.storybear.game.Word;

public class TextAnalyzer {

	
	public TextAnalyzer(){
		
	}
	
	//TODO: Methode vervollständigen
	/**
	 * @author Miriam
	 */
	public StoryInfo analyzeText(Story story){
		StoryInfo storyInfo = new StoryInfo();
		String[] words;
		int objectType = 0;
		ArrayList<ILevelAppearance> elements = new ArrayList();
		int numberOfBlocks = 0;			//how many blocks are needed for a word
		int blockPosition = 0; 	//block number, where a word starts in the level
		int stringLength = 0;	//how many pixels are needed
		
		storyInfo.setStory(story);
		
		words = story.getText().split(" ");
		//analyze Text
		//--> check if word of the story is in our db
		//if word in db then return type of word, e.g. collectable, character, ...
		//else return null
		for(String word : words){
			//objectType = checkWordInDB(word); //TODO: Methode schreiben und reinkommentieren
			
			//TODO: Länge ermitteln, wie lang das Wort später im Level sein wird
			//get the length of the word --> on which block will it begin?
			stringLength = this.numberOfPixelsOfString(word);
			numberOfBlocks = (int) Math.ceil(stringLength / Ressources.RASTERSIZEORG);
			
			//TODO: überarbeiten, aktuell nur Dummywerte
			switch (objectType){
			case 0:					//0 means null --> no match found --> save the original word
				elements.add(new Word(blockPosition));
				break;
			case 1:
				elements.add(new Character(blockPosition));
				break;
			case 2:
				elements.add(new Collectable(blockPosition));
				break;
			case 3:
				elements.add(new Landscape(blockPosition));
				break;
			}
			
			blockPosition += numberOfBlocks;	//calculates the beginning of each new words
		}
		
		storyInfo.setNumberOfBlocks(blockPosition);
		storyInfo.setElements(elements);
		
		return storyInfo;
	}
	
	//TODO Methode schreiben
	//tatsächliche Länge des Strings in Pixeln
	private int numberOfPixelsOfString(String word){
		return 0;
	}
	
	
	//TODO Methode schreiben
	/*public File generateLevelFile(){
	 * 
	 * }
	 */
}

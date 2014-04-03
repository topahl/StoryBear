package com.opticalcobra.storybear.editor;

import java.util.ArrayList;
import com.opticalcobra.storybear.main.ILevelAppearance;

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
		
		storyInfo.setStory(story);
		
		words = story.getText().split(" ");
		//analyze Text
		//--> check if word of the story is in our db
		//if word in db then return type of word, e.g. collectable, character, ...
		//else return null
		for(String word : words){
			//objectType = checkWordInDB(word); //TODO: Methode schreiben und reinkommentieren
			/*if(objectType != 0){		//0 means null --> no match found
				switch (objectType){
				case 1:
					elements.add(new Character());
					break;
				case 2:
					
					break;
				
				}
			}*/
		}
		
		//storyInfo.setElements(this.getElements(textWithoutFlexions));
		
		return storyInfo;
	}
	
	
	//TODO Methode schreiben
	/*public File generateLevelFile(){
	 * 
	 * }
	 */
}

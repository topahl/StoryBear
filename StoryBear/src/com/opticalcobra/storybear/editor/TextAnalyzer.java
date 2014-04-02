package com.opticalcobra.storybear.editor;

import java.util.ArrayList;
import com.opticalcobra.storybear.main.ILevelAppearance;

public class TextAnalyzer {

	
	public TextAnalyzer(){
		
	}
	
	
	public StoryInfo analyzeText(String storyText, String title){
		StoryInfo storyInfo = new StoryInfo();
		String textWithoutUnneededWords = this.killUnneededWords(storyText);
		String textWithoutFlexions = this.replaceFlexions(textWithoutUnneededWords);
		
		storyInfo.getStory().setTitle(title);
		storyInfo.getStory().setText(storyText);
		
		storyInfo.setElements(this.getElements(textWithoutFlexions));
		
		return storyInfo;
	}
	
	
	//TODO Methode schreiben
	private ArrayList<ILevelAppearance> getElements(String text){
		ArrayList<ILevelAppearance> elements = new ArrayList();;
		
		return elements;
	}
	
	//TODO Methode schreiben
	private String replaceFlexions(String text){
		
		return text;
	}
	
	//TODO Methode schreiben
	private String killUnneededWords(String text){
	
		return text;
	}
	
	//TODO Methode schreiben
	/*public File generateLevelFile(){
	 * 
	 * }
	 */
}

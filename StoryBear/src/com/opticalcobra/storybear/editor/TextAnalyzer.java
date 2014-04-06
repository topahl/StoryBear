package com.opticalcobra.storybear.editor;

import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.geometry.Rectangle2D;

import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.game.Character;
import com.opticalcobra.storybear.game.Collectable;
import com.opticalcobra.storybear.game.Landscape;
import com.opticalcobra.storybear.game.Word;

public class TextAnalyzer {

	private Font storyTextFont = FontCache.getInstance().getFont("Standard", ((float) (Ressources.STORYTEXTSIZE/Ressources.SCALE)));
	
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
			objectType = checkWordInDB(word);
			
			//get the length of the word in pixels
			stringLength = this.numberOfPixelsOfString(word);
			
			//Math.ceil rundet immer auf: 0.1 wird zu 1.0
			numberOfBlocks = (int) Math.ceil(stringLength / Ressources.RASTERSIZEORG);
			
			//TODO: überarbeiten, aktuell nur Dummywerte
			switch (objectType){
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
			//es wird für alles ein Word angelegt
			elements.add(new Word(blockPosition));
			
			blockPosition += numberOfBlocks;	//calculates the beginning of each new words
		}
		
		storyInfo.setNumberOfBlocks(blockPosition);
		storyInfo.setElements(elements);
		
		return storyInfo;
	}
	
	private int checkWordInDB(String word) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @author Martika
	 * @param word
	 * @return width in pixel
	 */
	public int numberOfPixelsOfString(String word){
		Graphics2D g = (Graphics2D) new BufferedImage(1,1,1).getGraphics();
		g.setFont(storyTextFont);
		FontMetrics fm = g.getFontMetrics();
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(word, g);
		return (int)Math.ceil((rect.getWidth()));
	}
	
	
	//TODO Methode schreiben
	/*public File generateLevelFile(){
	 * 
	 * }
	 */
}

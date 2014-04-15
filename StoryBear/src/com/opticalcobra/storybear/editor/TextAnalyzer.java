package com.opticalcobra.storybear.editor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.geometry.Rectangle2D;

import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.db.DBConstants;
import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.game.Character;
import com.opticalcobra.storybear.game.Collectable;
import com.opticalcobra.storybear.game.Landscape;
import com.opticalcobra.storybear.game.Word;
import com.sun.javafx.tk.quantum.PathIteratorHelper.Struct;

public class TextAnalyzer {

	private Font storyTextFont = FontCache.getInstance().getFont("Standard", ((float) (Ressources.STORYTEXTSIZE/Ressources.SCALE)));
	private Database db= new Database();
	
	
	public TextAnalyzer(){
		
	}
	
	//TODO: Methode vervollständigen
	/**
	 * @author Miriam
	 */
	public StoryInfo analyzeText(Story story){
		StoryInfo storyInfo = new StoryInfo();
		String[] words;
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
			
			//get the length of the word in pixels
			stringLength = this.numberOfPixelsOfString(word);
			
			//Math.ceil rundet immer auf: 0.1 wird zu 1.0
			numberOfBlocks = (int) Math.ceil((double)stringLength / (double)Ressources.RASTERSIZE);
			
			//TODO: überarbeiten, aktuell nur Dummywerte
			try {
				switch (db.queryWordType(word)){
				case DBConstants.WORD_OBJECT_TYPE_CHARACTER:
					elements.add(new Character(blockPosition));
					break;
				case DBConstants.WORD_OBJECT_TYPE_COLLECTABLE:
					elements.add(new Collectable(blockPosition));
					break;
				case DBConstants.WORD_OBJECT_TYPE_MIDDLEGROUND:
					elements.add(new Landscape(blockPosition));
					break;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(word);
			}
			
			elements.add(new Word(word,blockPosition));
			blockPosition += numberOfBlocks;	//calculates the beginning of each new word

		}
		
		storyInfo.setNumberOfBlocks(blockPosition);
		storyInfo.setElements(elements);
		//db.insertStoryInfoToDatabase(storyInfo);
		return storyInfo;
	}
	

	/**
	 * @author Martika
	 * @param word
	 * @return width in pixel
	 */
	public int numberOfPixelsOfString(String word){
		int pixelWidth = 0;
		Graphics2D g = (Graphics2D) new BufferedImage(1,1,1).getGraphics();
		g.setFont(storyTextFont);
		FontMetrics fm = g.getFontMetrics();
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(word, g);
		pixelWidth = (int)Math.ceil((rect.getWidth()));
		pixelWidth = (int) (pixelWidth + Math.ceil(3*Ressources.SCALE));
		return pixelWidth;
	}
	
	
	//TODO Methode schreiben
	/*public File generateLevelFile(){
	 * 
	 * }
	 */
}

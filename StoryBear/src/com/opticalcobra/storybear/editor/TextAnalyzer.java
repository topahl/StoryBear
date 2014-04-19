package com.opticalcobra.storybear.editor;

import java.sql.ResultSet;
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
import com.opticalcobra.storybear.db.WordResult;
import com.opticalcobra.storybear.game.Character;
import com.opticalcobra.storybear.game.Collectable;
import com.opticalcobra.storybear.game.Landscape;
import com.opticalcobra.storybear.game.RenderHint;
import com.opticalcobra.storybear.game.Word;
import com.sun.javafx.tk.quantum.PathIteratorHelper.Struct;

public class TextAnalyzer {

	private Font storyTextFont = FontCache.getInstance().getFont("Standard", ((float) (Ressources.STORYTEXTSIZE)));
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
		ArrayList<RenderHint> renderHint = new ArrayList();
		RenderHint rh;
		
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
				WordResult wr =db.queryWordType(word); 
				switch (wr.getType()){
				case DBConstants.WORD_OBJECT_TYPE_CHARACTER:
					elements.add(new Character(blockPosition,wr.getImage_id()));
					break;
				case DBConstants.WORD_OBJECT_TYPE_COLLECTABLE:
					elements.add(new Collectable(blockPosition));
					break;
				case DBConstants.WORD_OBJECT_TYPE_MIDDLEGROUND:
					elements.add(new Landscape(blockPosition));
					break;
				}

				rh = this.getScheme(word,blockPosition);
				if(rh != null)
					renderHint.add(rh);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(word);
			}
					
			elements.add(new Word(word,blockPosition));
			blockPosition += numberOfBlocks;	//calculates the beginning of each new word

		}
		
		storyInfo.setNumberOfBlocks(blockPosition);
		
		//analyze RenderHint --> are the blocks ok? or are the schemes coming to close in some parts of the story
		//one Scheme has a minimum size of 16 kacheln
		renderHint = this.calculateImportanceOfSchemes(renderHint);
		renderHint = this.calcNewBlockPositions(renderHint,blockPosition);
		
		//insert renderHints in elements
		for(int i=0;i<renderHint.size();i++){
			elements.add(renderHint.get(i));
		}
		//end of analyzing RenderHint
		
		
		storyInfo.setElements(elements);
		db.insertStoryInfoToDatabase(storyInfo);
		return storyInfo;
	}
	
	
	/*
	 * @author Miriam
	 * if the schemes are coming to close after each other their position has to be modified
	 */
	private ArrayList<RenderHint> calcNewBlockPositions(ArrayList<RenderHint> renderHint, int numberOfBlocks){
		//the startpoint of the first scheme is always fix
		if(renderHint.size() > 0){
			if(renderHint.get(0).getBlock() < Ressources.TILESPERPANEL/2)
				renderHint.get(0).setBlock(0);
			else
				renderHint.get(0).setBlock(renderHint.get(0).getBlock() - Ressources.TILESPERPANEL/2);
			
			for(int i=1;i<renderHint.size();i++){
				//if the distance of 2 schemes is bigger than needed
				if(renderHint.get(i).getBlock() - renderHint.get(i-1).getBlock() > 3*Ressources.TILESPERPANEL/2)
					renderHint.get(i).setBlock(renderHint.get(i).getBlock() - Ressources.TILESPERPANEL/2);
				else if(renderHint.get(i).getBlock() - renderHint.get(i-1).getBlock() > Ressources.TILESPERPANEL)
					renderHint.get(i).setBlock(renderHint.get(i-1).getBlock() + Ressources.TILESPERPANEL);
			}	
		}
		
		return renderHint;
	}
	
	
	/*
	 * @author Miriam
	 * if same schemes are coming exactly after each other then the importance should be increased
	 */
	private ArrayList<RenderHint> calculateImportanceOfSchemes(ArrayList<RenderHint> renderHint){
		RenderHint rh;
		for(int i=1;i<renderHint.size();i++){
				rh = renderHint.get(i-1);
				if(renderHint.get(i).getRenderHint() == rh.getRenderHint()){
					renderHint.get(i-1).setImportance(renderHint.get(i-1).getImportance()+1); //increase Importance
					renderHint.remove(i);
					i--;
				}
		}
		return renderHint;
	}
	
	
	
	/*
	 * @author Miriam
	 * analyzes if the words indicates a scheme, eg. Water, Mountains, ... for the Foreground
	 */
	private RenderHint getScheme(String word, int block) throws SQLException{
		RenderHint rh;
		ResultSet rs = db.query("SELECT DISTINCT t2.word FROM term t "
				+ "LEFT JOIN synset s ON t.synset_id =s.id "
				+ "LEFT JOIN term t2 ON t2.synset_id = s.id "
				+ "LEFT JOIN category_link cl ON t2.synset_id = cl.synset_id "
				+ "LEFT JOIN category c ON c.id = cl.category_id "
				+ "LEFT JOIN term_level tl ON t2.level_id = tl.id "
				+ "WHERE t.word in (SELECT basic FROM morph where reflexive= '" +word+ "' ) "
				+ "OR t.word like '" +word+ "' OR t.normalized_word like '" +word+ "' "
				+ "OR t.normalized_word in (SELECT basic FROM morph where reflexive= '" +word+ "' );");
		
		while(rs.next()){
			//checks if scheme is water
			for(int i=0;i<RenderHint.WORDGROUP_LENGTH;i++){
				if((RenderHint.WORDGROUP_WATER.length>i) && (((String)rs.getObject(1)).contains(RenderHint.WORDGROUP_WATER[i]))){
					rh = new RenderHint(block,RenderHint.RENDERHINT_WATER,1);
					rs.close();
					return rh;
				}
				else if((RenderHint.WORDGROUP_MOUNTAINS.length>i) && (((String)rs.getObject(1)).contains(RenderHint.WORDGROUP_MOUNTAINS[i]))){
					rh = new RenderHint(block,RenderHint.RENDERHINT_MOUNTAINS,1);
					rs.close();
					return rh;
				}
				else if((RenderHint.WORDGROUP_CITY.length>i) && (((String)rs.getObject(1)).contains(RenderHint.WORDGROUP_CITY[i]))){
					rh = new RenderHint(block,RenderHint.RENDERHINT_CITY,1);
					rs.close();
					return rh;
				}
				else if((RenderHint.WORDGROUP_WINTER.length>i) && (((String)rs.getObject(1)).contains(RenderHint.WORDGROUP_WINTER[i]))){
					rh = new RenderHint(block,RenderHint.RENDERHINT_WINTER,1);
					rs.close();
					return rh;
				}
			}
			
		}
		rs.close();
		
		return null;
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

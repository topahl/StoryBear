package com.opticalcobra.storybear.editor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.db.DBConstants;
import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.db.WordResult;
import com.opticalcobra.storybear.game.Character;
import com.opticalcobra.storybear.game.Collectable;
import com.opticalcobra.storybear.game.IllustrationBig;
import com.opticalcobra.storybear.game.IllustrationSmall;
import com.opticalcobra.storybear.game.Landscape;
import com.opticalcobra.storybear.game.RenderHint;
import com.opticalcobra.storybear.game.Word;

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
			String word_short = word.replaceAll("[^a-zA-Z äüöß0-9]", "");
			//get the length of the word in pixels
			stringLength = this.numberOfPixelsOfString(word);
			
			//Math.ceil rundet immer auf: 0.1 wird zu 1.0
			numberOfBlocks = (int) Math.ceil((double)stringLength / (double)Ressources.RASTERSIZE);
			System.out.println(word);
			try {
				WordResult wr =db.queryWordType(word_short); 
				switch (wr.getType()){
				case DBConstants.WORD_OBJECT_TYPE_CHARACTER:
					elements.add(new Character(blockPosition,wr.getImage_id()));
					break;
				case DBConstants.WORD_OBJECT_TYPE_COLLECTABLE:
					elements.add(new Collectable(blockPosition,wr.getImage_id()));
					break;
				case DBConstants.WORD_OBJECT_TYPE_MIDDLEGROUND:
					elements.add(new Landscape(blockPosition));
					break;
				case DBConstants.WORD_OBJECT_TYPE_ILLUSTRATION_SMALL:
					elements.add(new IllustrationSmall(blockPosition,wr.getImage_id()));
					break;
				case DBConstants.WORD_OBJECT_TYPE_ILLUSTRATION_BIG:
					elements.add(new IllustrationBig(blockPosition,wr.getImage_id()));
					break;
				}
				rh = this.getScheme(word,blockPosition,wr);
				if(rh != null)
					renderHint.add(rh);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(word_short);
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
//		db.insertStoryInfoToDatabase(storyInfo);
		return storyInfo;
	}
	
	
	/*
	 * @author Miriam
	 * if the schemes are coming to close after each other their position has to be modified
	 */
	private ArrayList<RenderHint> calcNewBlockPositions(ArrayList<RenderHint> renderHint, int numberOfBlocks){
		if(renderHint.size() > 0){
			//the startpoint of the first scheme is always fix
			if(renderHint.get(0).getBlock() < Ressources.TILESPERPANEL/2)
				renderHint.get(0).setBlock(0);
			else
				renderHint.get(0).setBlock(renderHint.get(0).getBlock() - Ressources.TILESPERPANEL/2);
			
			for(int i=1;i<renderHint.size();i++){
				//if the distance of 2 schemes is between 24 and 160
				if((renderHint.get(i).getBlock() - renderHint.get(i-1).getBlock() > 3*Ressources.TILESPERPANEL/2) && (renderHint.get(i).getBlock() - renderHint.get(i-1).getBlock() <= Ressources.MAXLENGTHOFSCHEME))
					renderHint.get(i).setBlock(renderHint.get(i).getBlock() - Ressources.TILESPERPANEL/2);
				//if the distance of 2 schemes is between 16 and 24 --> new block position can't be less 8 like in the case above 
				else if(renderHint.get(i).getBlock() - renderHint.get(i-1).getBlock() > Ressources.TILESPERPANEL)
					renderHint.get(i).setBlock(renderHint.get(i-1).getBlock() + Ressources.TILESPERPANEL);
				//if the distance of 2 schemes is bigger than 160
				else if(renderHint.get(i).getBlock() - renderHint.get(i-1).getBlock() > Ressources.MAXLENGTHOFSCHEME){
					//insert RenderHint with the scheme NONE
					if(renderHint.get(i).getBlock() - renderHint.get(i-1).getBlock() > Ressources.MAXLENGTHOFSCHEME + Ressources.TILESPERPANEL/2)
						renderHint.add(i,new RenderHint(renderHint.get(i-1).getBlock()+Ressources.MAXLENGTHOFSCHEME, RenderHint.RENDERHINT_NONE, 1));
					else
						renderHint.add(i,new RenderHint(renderHint.get(i-1).getBlock()+Ressources.MAXLENGTHOFSCHEME - Ressources.TILESPERPANEL/2, RenderHint.RENDERHINT_NONE, 1));
					i++;
					renderHint.get(i).setBlock(renderHint.get(i).getBlock() - Ressources.TILESPERPANEL/2);
				}		
				else
					renderHint.remove(i);
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
	private RenderHint getScheme(String word, int block, WordResult wr) throws SQLException{
		RenderHint rh;
		ArrayList[] rs = wr.getResultSet();
		
		if(rs.length > 0){
			ArrayList<String> stringResults = rs[0];
			
			for(String s : stringResults){
				for(int i=0;i<RenderHint.WORDGROUP_LENGTH;i++){
					if((RenderHint.WORDGROUP_FOREST.length>i) && s.contains(RenderHint.WORDGROUP_FOREST[i])){
						rh = new RenderHint(block,RenderHint.RENDERHINT_FOREST,1);
						return rh;
					}
					else if((RenderHint.WORDGROUP_CITY.length>i) && s.contains(RenderHint.WORDGROUP_CITY[i])){
						rh = new RenderHint(block,RenderHint.RENDERHINT_CITY,1);
						return rh;
					}
					else if((RenderHint.WORDGROUP_VILLAGE.length>i) && s.contains(RenderHint.WORDGROUP_VILLAGE[i])){
						rh = new RenderHint(block,RenderHint.RENDERHINT_VILLAGE,1);
						return rh;
					}
					else if((RenderHint.WORDGROUP_CASTLE.length>i) && s.contains(RenderHint.WORDGROUP_CASTLE[i])){
						rh = new RenderHint(block,RenderHint.RENDERHINT_CASTLE,1);
						return rh;
					}
					else if((RenderHint.WORDGROUP_LANDSCAPE.length>i) && s.contains(RenderHint.WORDGROUP_LANDSCAPE[i])){
						rh = new RenderHint(block,RenderHint.RENDERHINT_LANDSCAPE,1);
						return rh;
					}
				}
			}
		}
		
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

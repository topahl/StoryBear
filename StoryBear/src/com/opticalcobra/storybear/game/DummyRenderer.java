package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.db.DBConstants;
import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.db.TileResult;
import com.opticalcobra.storybear.debug.DebugSettings;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.editor.TextAnalyzer;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.main.Ringbuffer;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.res.StoryBearRandom;


public class DummyRenderer extends Renderer implements IRenderer{
	private Imagelib il = Imagelib.getInstance();
	private StoryBearRandom rand = StoryBearRandom.getInstance();
	private int lastTileType = 0;
	private int panelnum = 0;
	private StoryInfo storyInfo;
	private int elementPointer = 0;
	private Ringbuffer<TileResult> ringbuffer = new Ringbuffer<TileResult>(3*16); 
	private Database db;
	
	public DummyRenderer(){		
		
		db = new Database();
		storyInfo = new StoryInfo();
		//ringbuffer.write(lastTileType);
		
//		TextAnalyzer textAnalyzer = new TextAnalyzer();
//		Database.requestnum = 0;
//		this.storyInfo = textAnalyzer.analyzeText(db.getStoryFromDatabase(1));
//		System.out.println(Database.requestnum);
		storyInfo = db.getStoryInfoFromDatabase(1);
	}
	
	private BufferedImage getNextMapElement(){
		int next = 0;
		
		if (!(ringbuffer.top() == null)){
			Integer[] following = il.getFollowingTiles(lastTileType, Imagelib.QUERY_FOREGROUND);
			next = following[rand.nextInt(following.length)];
			
		}else{
			lastTileType = next;
			ringbuffer.write(db.getTileInfo(lastTileType));
		}
		
		lastTileType = next;
		ringbuffer.write(db.getTileInfo(lastTileType));
		
		return il.loadLandscapeTile(next, Imagelib.QUERY_FOREGROUND);
	}

	
	@Override
	public void getNextViewPart(JLabel pane) {
		ArrayList<ILevelAppearance> elements = this.storyInfo.getElements();
		
		panelnum++;
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		for(int i=0;i*Ressources.RASTERSIZE<Ressources.WINDOW.width;i++){
			g.drawImage(getNextMapElement(),i*Ressources.RASTERSIZE,0,null);
			if(DebugSettings.vg1tilenum)
				renderText(g,((float) (Ressources.STORYTEXTSIZE/Ressources.SCALE)), lastTileType+"", (i*Ressources.RASTERSIZE)+20,100);
			if(DebugSettings.vg1panelborder)
				g.drawRect(i*Ressources.RASTERSIZE, 0, Ressources.RASTERSIZE, Ressources.WINDOW.height);
			
		}
		for(int i=0;i*Ressources.RASTERSIZE<=Ressources.WINDOW.width;i++){
			if (elementPointer < storyInfo.getElements().size() && elementPointer > 0 &&
							storyInfo.getElements().get(elementPointer).getBlock() % 16 != 0 && i == 0){
			
				if (storyInfo.getElements().get(elementPointer) instanceof Word){
					((Word)elements.get(elementPointer-1)).renderPreviousLostWord(g, ((16 - (elements.get(elementPointer-1).getBlock()%16)))*-1);
				}	
			}
			
			
			if(elementPointer < storyInfo.getElements().size() && 
							storyInfo.getElements().get(elementPointer).getBlock() < (i + (panelnum-1)*16)){
				(elements.get(elementPointer)).render(g);
				elementPointer++;
			}
		}
		
		if(DebugSettings.vg1panelnum)
			renderText(g,50, panelnum+"", 20, 40);

		
		pane.setIcon(new ImageIcon(image));
	}
	
	public Ringbuffer<TileResult> getRingbuffer() {
		return this.ringbuffer;
	}
}

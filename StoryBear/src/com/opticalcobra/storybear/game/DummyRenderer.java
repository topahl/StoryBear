package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.db.DBConstants;
import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.db.TileResult;
import com.opticalcobra.storybear.debug.DebugSettings;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.editor.TextAnalyzer;
import com.opticalcobra.storybear.game.RenderThreadWrapper.Element;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.main.Ringbuffer;
import com.opticalcobra.storybear.main.SBLinkedList;
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
	private static LinkedList<TileResult> tileQue = new SBLinkedList<TileResult>();
	private Database db;
	private ArrayList<Integer> currentTileIds;
	boolean wasFreeRide = false;
	int lastElementPointer = 0;
	
	public DummyRenderer(StoryInfo si){		
		
		db = new Database();
		storyInfo = si;
	}
	
	private BufferedImage getNextMapElement(int nextCurrentElementPointer, int blockInPanel){
		int next = 0;
		int nextSecondStep = 0;
		int nextThirdStep = 0;
		
		boolean walkableTile = true;
		boolean needNewTile= true;
		boolean needNewTileSecond= true;
		boolean newTileFound = false;
		int freeRideCounterStep = 0;
		
		blockInPanel = blockInPanel + ((panelnum-1)*Ressources.TILESPERPANEL);
		
		if (!(storyInfo.getElements().get(nextCurrentElementPointer) instanceof Character) && blockInPanel != 0){
			Integer[] following = il.getFollowingTiles(lastTileType, Imagelib.QUERY_FOREGROUND);
			next = following[rand.nextInt(following.length)];
			lastTileType = next;
			tileQue.add(db.getTileInfo(lastTileType));
			currentTileIds.add(next);
			return il.loadLandscapeTile(next, Imagelib.QUERY_FOREGROUND, null);
		}
		
		
		
		if (storyInfo.getElements().get(nextCurrentElementPointer).getBlock() > blockInPanel){
			freeRideCounterStep = storyInfo.getElements().get(nextCurrentElementPointer).getBlock() - blockInPanel +1;
			if ((blockInPanel+1) % Ressources.TILESPERPANEL == 0){
				freeRideCounterStep--;
			}
		}
		
		
	//The first tile has to be 0
		if (blockInPanel>0){
			if (storyInfo.getElements().get(nextCurrentElementPointer) instanceof Character && freeRideCounterStep==0){
				Integer[] following = il.getFollowingTiles(lastTileType, Imagelib.QUERY_FOREGROUND);
				next = following[rand.nextInt(following.length)];
				
				//Solange suchen, bis eine begehbare Fläche entsteht
				while (!(walkableTile = db.getTileInfo(next).isWalkable())){
					next = following[rand.nextInt(following.length)];
				}
				//Danach ist next begehbar
				newTileFound = true;
				
			}
			
			
			
			while (storyInfo.getElements().size() > nextCurrentElementPointer+1 && !newTileFound &&
					blockInPanel <= storyInfo.getElements().get(nextCurrentElementPointer+1).getBlock()){
				if (freeRideCounterStep == 0){
					nextCurrentElementPointer++;
				}
				
				freeRideCounterStep--;
				//Nächste Kachel kommt ein Character -> Auf begehbare Fläche vorbereiten
				if (storyInfo.getElements().get(nextCurrentElementPointer) instanceof Character && !newTileFound){
					Integer[] following = il.getFollowingTiles(lastTileType, Imagelib.QUERY_FOREGROUND);
					
					while (needNewTile){
						next = following[rand.nextInt(following.length)];
						Integer[] followingSecond = il.getFollowingTiles(next, Imagelib.QUERY_FOREGROUND);
						
						for (int i=0; i<15; i++){
							nextSecondStep = followingSecond[rand.nextInt(followingSecond.length)];
							if (walkableTile = db.getTileInfo(nextSecondStep).isWalkable()){
								needNewTile = false;
								break; //Kachel ist begehbar für next
							}
						}
					}
					
					newTileFound = true;
				}
			}

				
			while (storyInfo.getElements().size() > nextCurrentElementPointer+1 && !newTileFound &&
					blockInPanel == storyInfo.getElements().get(nextCurrentElementPointer+1).getBlock()){
				nextCurrentElementPointer++;
			
				//Übernächste Kachel kommt ein Character -> Auf begehbare Fläche vorbereiten
				if (storyInfo.getElements().get(nextCurrentElementPointer) instanceof Character && !newTileFound){
					Integer[] following = il.getFollowingTiles(lastTileType, Imagelib.QUERY_FOREGROUND);
					
					while (needNewTile){
						for (int i=0; i<15; i++){
							next = following[rand.nextInt(following.length)];
							Integer[] followingSecond = il.getFollowingTiles(next, Imagelib.QUERY_FOREGROUND);
							
							for (int j=0; j<15; j++){
								nextSecondStep = followingSecond[rand.nextInt(followingSecond.length)];
								Integer[] followingThird = il.getFollowingTiles(nextSecondStep, Imagelib.QUERY_FOREGROUND);
								
								for (int k=0; k<15; k++){
									nextThirdStep = followingSecond[rand.nextInt(followingThird.length)];
									if (walkableTile = db.getTileInfo(nextThirdStep).isWalkable()){
										needNewTile = false;
										break; //Kachel ist begehbar für next
									}
								}
								if (!needNewTile){
									break;
								}
							
							}
							if (!needNewTile){
								break;
							}
						}
					}
					newTileFound = true;
				}
			}
					
			if (!newTileFound){
				Integer[] following = il.getFollowingTiles(lastTileType, Imagelib.QUERY_FOREGROUND);
				next = following[rand.nextInt(following.length)];
			}

		}
		
		//System.out.println("Aktueller Block = "+ currentBlock + "mit tileId" + next);
		lastTileType = next;
		tileQue.add(db.getTileInfo(lastTileType));
		
//		System.out.print("\nCurrentBlock" + currentBlock + "tileQue sieht so aus: ");
//		for (int y = 0; y < tileQue.size(); y++){
//			System.out.print(tileQue.get(y).getTileType());
//		}
		
		currentTileIds.add(next);
		return il.loadLandscapeTile(next, Imagelib.QUERY_FOREGROUND, null);
	}

	
	@Override
	public void getNextViewPart(JLabel pane) {
		int currentElementCounter = 0;
		
		ArrayList<ILevelAppearance> elements = this.storyInfo.getElements();
		currentTileIds = new ArrayList<Integer>();
		
		if (panelnum >= 3){
			for (int i = 0; i < Ressources.TILESPERPANEL; i++){
				tileQue.removeFirst();
			}
			Hero.getInstance().setQueCounter(Hero.getInstance().getQueCounter()- Ressources.TILESPERPANEL);
		}

		
		panelnum++;
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage bgimage = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		Graphics2D bg = (Graphics2D) bgimage.getGraphics();
		if(storyInfo.getElements().size() > elementPointer){
			for(int i=0;i<Ressources.TILESPERPANEL;i++){
				g.drawImage(getNextMapElement(elementPointer+currentElementCounter, i),i*Ressources.RASTERSIZE,0,null);

				while (storyInfo.getElements().get(elementPointer+currentElementCounter).getBlock() == storyInfo.getElements().get(elementPointer+currentElementCounter+1).getBlock()){
					currentElementCounter++;
				}
				while (i+ ((panelnum-1)*Ressources.TILESPERPANEL) <storyInfo.getElements().get(elementPointer+currentElementCounter).getBlock()){
					currentElementCounter--;
				}
				
				currentElementCounter++;
				
			
				
				if(DebugSettings.fg1tilenum)
					renderText(g,((float) (Ressources.STORYTEXTSIZE/Ressources.SCALE)), lastTileType+"", (i*Ressources.RASTERSIZE)+20,100);
				if(DebugSettings.fg1panelborder)
					g.drawRect(i*Ressources.RASTERSIZE, 0, Ressources.RASTERSIZE, Ressources.WINDOW.height);
				
			}
			System.out.println();
			for(int i=0;i<Ressources.TILESPERPANEL;i++){

				//Bilder werden geredert
				if(elementPointer < storyInfo.getElements().size() && 
								storyInfo.getElements().get(elementPointer).getBlock() < (i + (panelnum-1)*16)){
					
					ILevelAppearance element = (elements.get(elementPointer));
					if(element instanceof IllustrationBig){
						element.render(bg, currentTileIds.get(0), Ressources.LAYERFOREGROUNDONE, pane);
					} 
					else{
						element.render(g, currentTileIds.get(0), Ressources.LAYERFOREGROUNDONE, pane);
					}
					
					//Wenn mehrere Elemente auf eine Kachel gerendert werden, darf i nicht hochgezählt werden
					if (elementPointer+1 < storyInfo.getElements().size() && 
							storyInfo.getElements().get(elementPointer).getBlock() == storyInfo.getElements().get(elementPointer+1).getBlock()){
						i--;
					} else{
						currentTileIds.remove(0);
					}
					elementPointer++;
				}
			}
		}
	
		if(DebugSettings.fg1panelnum)
			renderText(g,50, panelnum+"", 20, 40);
		
		bg.drawImage(image, 0, 0, null);
		pane.setIcon(new ImageIcon(bgimage));
	}
	
	public LinkedList<TileResult> getTileQue() {
		return this.tileQue;
	}
}

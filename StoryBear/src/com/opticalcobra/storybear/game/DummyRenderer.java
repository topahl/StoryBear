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
	
	public DummyRenderer(StoryInfo si){		
		
		db = new Database();
		storyInfo = si;
	}
	
	private BufferedImage getNextMapElement(int currentBlock){
		int next = 0;
		int nextSecondStep = 0;
		int nextThirdStep = 0;
		//int currentBlock = storyInfo.getElements().get(elementPointerCurrentTile).getBlock();
		int elementPointerCurrentTile=0;
		int nextCurrentElementPointer = storyInfo.getElements().get(elementPointerCurrentTile).getBlock();
		boolean walkableTile = true;
		boolean needNewTile= true;
		boolean needNewTileSecond= true;
		boolean newTileFound = false;
		boolean freeRide = true;
		
		//ermittle aktuellen elementPointer
		
		if (currentBlock == 30){
			System.out.print("8");
		}
		
		while (currentBlock > storyInfo.getElements().get(elementPointerCurrentTile).getBlock()){
			elementPointerCurrentTile++;
			if (elementPointerCurrentTile < storyInfo.getElements().size() &&
					currentBlock == storyInfo.getElements().get(elementPointerCurrentTile).getBlock()){
				freeRide = false;
			}
			else if(elementPointerCurrentTile >= storyInfo.getElements().size()){
				break;
			}
		}
		
		
			
		
		
		//The first tile has to be 0
		if (currentBlock>0){
			if (storyInfo.getElements().size() > elementPointerCurrentTile){
			
				while (currentBlock == storyInfo.getElements().get(elementPointerCurrentTile).getBlock() || freeRide){
					
					if (storyInfo.getElements().get(elementPointerCurrentTile) instanceof Character && !freeRide){
						Integer[] following = il.getFollowingTiles(lastTileType, Imagelib.QUERY_FOREGROUND);
						next = following[rand.nextInt(following.length)];
						
						//Solange suchen, bis eine begehbare Fläche entsteht
						while (!(walkableTile = db.getTileInfo(next).isWalkable())){
							next = following[rand.nextInt(following.length)];
						}
						//Danach ist next begehbar
						newTileFound = true;
						
					}
					
					if (newTileFound){
						break;
					}
					
					
					while (storyInfo.getElements().get(nextCurrentElementPointer).getBlock() == storyInfo.getElements().get(nextCurrentElementPointer+1).getBlock() && !freeRide ){
						nextCurrentElementPointer++;
					}
						
						
					//Nächste Kachel kommt ein Character -> Auf begehbare Fläche vorbereiten
					if (storyInfo.getElements().get(nextCurrentElementPointer) instanceof Character){
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
					
					if (newTileFound){
						break;
					}
					
					while (storyInfo.getElements().get(nextCurrentElementPointer).getBlock() == storyInfo.getElements().get(nextCurrentElementPointer+1).getBlock() ){
						nextCurrentElementPointer++;
					}
					
					//Übernächste Kachel kommt ein Character -> Auf begehbare Fläche vorbereiten
					if (storyInfo.getElements().get(nextCurrentElementPointer) instanceof Character){
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
					
					elementPointerCurrentTile++;
					if (storyInfo.getElements().size() == elementPointerCurrentTile){
						break;
					}
				}
				
				if (!newTileFound){
					Integer[] following = il.getFollowingTiles(lastTileType, Imagelib.QUERY_FOREGROUND);
					next = following[rand.nextInt(following.length)];
				}
			}
		}
		
		//System.out.println("Aktueller Block = "+ currentBlock + "mit tileId" + next);
		lastTileType = next;
		tileQue.add(db.getTileInfo(lastTileType));
//		
//		System.out.print("\nCurrentBlock" + currentBlock + "tileQue sieht so aus: ");
//		for (int y = 0; y < tileQue.size(); y++){
//			System.out.print(tileQue.get(y).getTileType());
//		}
		
		currentTileIds.add(next);
		return il.loadLandscapeTile(next, Imagelib.QUERY_FOREGROUND, null);
	}

	
	@Override
	public void getNextViewPart(JLabel pane) {
		
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
		Graphics2D g = (Graphics2D) image.getGraphics();
		for(int i=0;i<Ressources.TILESPERPANEL;i++){
			g.drawImage(getNextMapElement(storyInfo.getElements().get(elementPointer).getBlock()+i),i*Ressources.RASTERSIZE,0,null);
			if(DebugSettings.fg1tilenum)
				renderText(g,((float) (Ressources.STORYTEXTSIZE/Ressources.SCALE)), lastTileType+"", (i*Ressources.RASTERSIZE)+20,100);
			if(DebugSettings.fg1panelborder)
				g.drawRect(i*Ressources.RASTERSIZE, 0, Ressources.RASTERSIZE, Ressources.WINDOW.height);
			
		}
		for(int i=0;i<Ressources.TILESPERPANEL;i++){

			//Bilder werden geredert
			if(elementPointer < storyInfo.getElements().size() && 
							storyInfo.getElements().get(elementPointer).getBlock() < (i + (panelnum-1)*16)){
				
				
				(elements.get(elementPointer)).render(g, currentTileIds.get(0), Ressources.LAYERFOREGROUNDONE, pane);
				
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
	
		if(DebugSettings.fg1panelnum)
			renderText(g,50, panelnum+"", 20, 40);

		
		pane.setIcon(new ImageIcon(image));
	}
	
	public LinkedList<TileResult> getTileQue() {
		return this.tileQue;
	}
}

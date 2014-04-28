package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.db.TileResult;
import com.opticalcobra.storybear.debug.DebugSettings;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.main.Ringbuffer;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.res.StoryBearRandom;

public class RendererFG2 extends Renderer implements IRenderer {
	private Imagelib il = Imagelib.getInstance();
	private StoryBearRandom rand = StoryBearRandom.getInstance();
	private int lastTileType = 0;
	private int panelnum = 0;
	private StoryInfo storyInfo;
	private int elementPointer = 0;
	//private Ringbuffer<TileResult> ringbuffer = new Ringbuffer<TileResult>(3*16); 
	private LinkedList<TileResult> tileQue;
	private Database db;
	private ArrayList<Integer> currentTileIds;
	
	
	public RendererFG2(LinkedList<TileResult> tileQue, StoryInfo level) {
		this.storyInfo = level;
		this.tileQue = tileQue;
		this.db = new Database();
		
	}


	private BufferedImage getNextMapElement(int currentBlock){
		
		int next = 0;
		
		if (panelnum>3){
			next = tileQue.get(2*Ressources.TILESPERPANEL + currentBlock).getTileType();
		} else{
			next = tileQue.get(((panelnum-1) *Ressources.TILESPERPANEL)+ currentBlock).getTileType();
		}
		
		lastTileType = next;
		currentTileIds.add(lastTileType);
		
		for (int i = 0 ; i < storyInfo.getElements().size(); i++){
			if (storyInfo.getElements().get(i) instanceof RenderHint && storyInfo.getElements().get(i).getBlock() == currentBlock + (panelnum-1)*Ressources.TILESPERPANEL){
				if (((RenderHint)(storyInfo.getElements().get(i))).getRenderHint() == RenderHint.RENDERHINT_FOREST){
					return il.loadLandscapeTile(next, Imagelib.QUERY_FOREGROUNDTWO, "FOREST");
				} else{
					return il.loadLandscapeTile(next, Imagelib.QUERY_FOREGROUNDTWO, "MEADOEW");
				}
			}
		}
		
		return il.loadLandscapeTile(next, Imagelib.QUERY_FOREGROUNDTWO, "MEADOEW");
	}

	
	@Override
	public void getNextViewPart(JLabel pane) {
		int pointerCounter=1;
		ArrayList<ILevelAppearance> elements = this.storyInfo.getElements();
		currentTileIds = new ArrayList<Integer>();
		
		panelnum++;
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		for(int i=0;i*Ressources.RASTERSIZE<Ressources.WINDOW.width;i++){
			g.drawImage(getNextMapElement(i),i*Ressources.RASTERSIZE,0,null);
		}

		for(int i=0;i<Ressources.TILESPERPANEL;i++){
			if (elementPointer < storyInfo.getElements().size() && elementPointer > 0 &&
							storyInfo.getElements().get(elementPointer).getBlock() % 16 != 0 && i == 0){
			
				//Wortübertrag auf ein neues Panel
				if (storyInfo.getElements().get(elementPointer) instanceof Word){
					while (!(storyInfo.getElements().get(elementPointer - pointerCounter) instanceof Word)){
						pointerCounter++;
					}
					((Word)elements.get(elementPointer-pointerCounter)).renderPreviousLostWord(g, ((16 - (elements.get(elementPointer-1).getBlock()%16)))*-1);
				}	
			}	
			
			//Wörter und Bilder werden gerendert
			if(elementPointer < storyInfo.getElements().size() && 
							storyInfo.getElements().get(elementPointer).getBlock() <= (i + (panelnum-1)*16)){
				
				(elements.get(elementPointer)).render(g, currentTileIds.get(0), Ressources.LAYERFOREGROUNDTWO, pane);
				
				//Wenn mehrere Elemente auf eine Kachel gerendert werden, darf i nicht hochgezählt werden
				if (elementPointer+1 < storyInfo.getElements().size() && storyInfo.getElements().get(elementPointer).getBlock() == storyInfo.getElements().get(elementPointer+1).getBlock()){
					i--;
				} else{
					currentTileIds.remove(0);
				}
				elementPointer++;
			}
		}
	
		
		pane.setIcon(new ImageIcon(image));
	}

}

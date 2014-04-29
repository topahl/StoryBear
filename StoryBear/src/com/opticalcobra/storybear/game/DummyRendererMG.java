package com.opticalcobra.storybear.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.opticalcobra.storybear.debug.DebugSettings;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.res.StoryBearRandom;

public class DummyRendererMG extends Renderer implements IRenderer {
	private Imagelib il = Imagelib.getInstance();
	private StoryBearRandom rand = StoryBearRandom.getInstance();
	private int lastTile = 0;
	private int panelnum = 0;
	private int elementPointer = 0; 
	private StoryInfo storyInfo;
	private ArrayList<Integer> currentTileIds;
	
	public DummyRendererMG(StoryInfo si){
		this.storyInfo = si;
	}
	
	
	private BufferedImage getNextMapElement(){
		Integer[] following = il.getFollowingTiles(lastTile, Imagelib.QUERY_MIDDLEGROUND);
		int next = following[rand.nextInt(following.length)];
		lastTile = next;
		currentTileIds.add(next);
		return il.loadLandscapeTile(next, Imagelib.QUERY_MIDDLEGROUND, null);	
	}
	
	
	@Override
	public void getNextViewPart(JLabel pane) {
		ArrayList<ILevelAppearance> elements = this.storyInfo.getElements();
		currentTileIds = new ArrayList<Integer>();
		
		panelnum++;
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		for(int i=0;i*Ressources.RASTERSIZE*4<Ressources.WINDOW.width;i++){
			g.drawImage(getNextMapElement(),i*Ressources.RASTERSIZE*4,0,null);
			if(DebugSettings.mgtilenum)
				renderText(g,((float) (Ressources.STORYTEXTSIZE/Ressources.SCALE)), lastTile+"", (i*4*Ressources.RASTERSIZE)+20,100);
			if(DebugSettings.mgpanelborder)
				g.drawRect(i*4*Ressources.RASTERSIZE, 0, Ressources.RASTERSIZE*4, Ressources.WINDOW.height);
		}
		
		for(int i=0;i*Ressources.RASTERSIZE*4<Ressources.WINDOW.width;i++){
			//Bilder werden gerendert
			if(elementPointer < elements.size() && 
							elements.get(elementPointer).getBlock() < (i + (panelnum-1)*16)){
				
				(elements.get(elementPointer)).render(g, currentTileIds.get(0), Ressources.LAYERMIDDLEGROUND, pane);
				
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
		
		pane.setIcon(new ImageIcon(image));
		if(DebugSettings.mgpanelnum)
			renderText(g,50, panelnum+"", 20, 40);
	}
	
	

}

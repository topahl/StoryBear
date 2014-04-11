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

public class DummyRenderer extends Renderer implements IRenderer{
	private Imagelib il = Imagelib.getInstance();
	private StoryBearRandom rand = StoryBearRandom.getInstance();
	private int lastTile = 0;
	private int panelnum = 0;
	private StoryInfo storyInfo;
	private int elementPointer = 0;
	public DummyRenderer(){		
		
		//Dummy Code	--> TODO: delete, dafür storyInfo übergeben lassen
		this.storyInfo = new StoryInfo();
		ArrayList<ILevelAppearance> elements = new ArrayList<ILevelAppearance>();
		elements.add(new Word("eins",1));
		elements.add(new Word("zwei",2));
		elements.add(new Word("fünf",5));
		elements.add(new Word("neun",9));
		elements.add(new Word("zehn",10));
		elements.add(new Word("vierzehn",14));
		elements.add(new Word("Donaudampfschiff bla bla bla",16));
		elements.add(new Word("zwanzig",20));
		elements.add(new Word("dreiundzwanzig",23));
		elements.add(new Word("fünfundzwanzig",25));
		elements.add(new Word("hallo",33));
		elements.add(new Word("hallo",36));
		elements.add(new Word("hallo",39));
		elements.add(new Word("hallo",42));
		this.storyInfo.setElements(elements);
	}
	
	private BufferedImage getNextMapElement(){
		Integer[] following = il.getFollowingTiles(lastTile, Imagelib.QUERY_FOREGROUND);
		int next = following[rand.nextInt(following.length)];
		lastTile = next;
		return il.loadLandscapeTile(next , Imagelib.QUERY_FOREGROUND);
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
				renderText(g,25, lastTile+"", (i*Ressources.RASTERSIZE)+20,100);
			if(DebugSettings.vg1panelborder)
				g.drawRect(i*Ressources.RASTERSIZE, 0, Ressources.RASTERSIZE, Ressources.WINDOW.height);
	
			if(elementPointer < storyInfo.getElements().size() && storyInfo.getElements().get(elementPointer).getBlock() < (i + (panelnum-1)*16)){
				((Word)elements.get(elementPointer)).render(g);
				elementPointer++;
			}
		}
		if(DebugSettings.vg1panelnum)
			renderText(g,50, panelnum+"", 20, 40);

		
		
		pane.setIcon(new ImageIcon(image));
	}
}

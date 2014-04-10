package com.opticalcobra.storybear.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.debug.DebugSettings;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;
import com.sun.javafx.tk.FontLoader;

public class DummyRenderer extends Renderer implements IRenderer{
	private Imagelib il = Imagelib.getInstance();
	private int lastTile = 0;
	private int panelnum = 0;
	private ArrayList<int[]>dependencies;
	private StoryInfo storyInfo;
	private int elementPointer = 0;
	
	public DummyRenderer(){
		dependencies = new ArrayList<int[]>();
		dependencies.add(new int[]{0,1,3,5,6,7,12,15,18,23,26});	//0
		dependencies.add(new int[]{2,8,11});						
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{4});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{2,8,11});
		dependencies.add(new int[]{9});
		dependencies.add(new int[]{9,10});
		dependencies.add(new int[]{2,8,11});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{13});
		dependencies.add(new int[]{13,14});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{16});
		dependencies.add(new int[]{16,17});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{19});
		dependencies.add(new int[]{20});
		dependencies.add(new int[]{20,21});
		dependencies.add(new int[]{22});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{24,25});
		dependencies.add(new int[]{25});
		dependencies.add(new int[]{0});
		dependencies.add(new int[]{27,28});
		dependencies.add(new int[]{28});
		dependencies.add(new int[]{0});
		
		
		
		
		//Dummy Code	--> TODO: delete, dafür storyInfo übergeben lassen
		this.storyInfo = new StoryInfo();
		ArrayList<ILevelAppearance> elements = new ArrayList();
		elements.add(new Word("eins",1));
		elements.add(new Word("zwei",2));
		elements.add(new Word("fünf",5));
		elements.add(new Word("neun",9));
		elements.add(new Word("zehn",10));
		elements.add(new Word("vierzehn",14));
		elements.add(new Word("siebzehn",17));
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
		int next =dependencies.get(lastTile)[((int)((Math.random()) * dependencies.get(lastTile).length))];
		lastTile = next;
		return il.loadLandscapeTile("images\\layer_slice_vg1.png", next);
		
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
	
			if((elementPointer < this.storyInfo.getElements().size()) && (((Word)elements.get(elementPointer)).getBlock() == (i + (panelnum-1)*17))){
				((Word)elements.get(elementPointer)).render(g);
				elementPointer++;
			}
		}
		if(DebugSettings.vg1panelnum)
			renderText(g,50, panelnum+"", 20, 40);
		
		pane.setIcon(new ImageIcon(image));
	}
}

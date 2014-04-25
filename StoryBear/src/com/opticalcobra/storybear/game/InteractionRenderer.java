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


public class InteractionRenderer extends Renderer{

	private int panelnum = 0;
	private ArrayList<ILevelAppearance> storyInfoElements;
	private Ringbuffer<TileResult> ringbuffer;
	int elementPointer = 0;
	
	
	public InteractionRenderer(Ringbuffer<TileResult> ringbuffer, ArrayList<ILevelAppearance> elememts){		
		this.ringbuffer = ringbuffer;
		this.storyInfoElements = elememts;
	}
	
	
	public void getNextViewPart(JLabel pane) {
		
		
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		for (int i = 0; i < Ressources.TILESPERPANEL; i++){
			//render collectable and character
			
			if (storyInfoElements.get(elementPointer).getBlock()%Ressources.TILESPERPANEL == i){
				if (storyInfoElements.get(elementPointer) instanceof Collectable ||
						storyInfoElements.get(elementPointer) instanceof Character){
					
					if (panelnum < 3){
						storyInfoElements.get(elementPointer).render(g, ringbuffer.top((panelnum)*Ressources.TILESPERPANEL + i +2).getTileType(), Ressources.LAYERINTERACTION, pane);
					} else{
						storyInfoElements.get(elementPointer).render(g, ringbuffer.top(2*Ressources.TILESPERPANEL + 1 +i).getTileType(), Ressources.LAYERINTERACTION, pane);
					}
				}
				
				//Wenn mehrere Elemente auf eine Kachel gerendert werden, darf i nicht hochgezählt werden
				if (elementPointer + 1 <= storyInfoElements.size() && storyInfoElements.get(elementPointer).getBlock()%Ressources.TILESPERPANEL == 
						storyInfoElements.get(elementPointer + 1).getBlock()%Ressources.TILESPERPANEL){
					i--;
				}
				elementPointer++;
			}
		}
		panelnum++;
		pane.setIcon(new ImageIcon(image));
	}
}

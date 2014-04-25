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
	
	
	public InteractionRenderer(Ringbuffer<TileResult> ringbuffer, ArrayList<ILevelAppearance> elememts){		
		this.ringbuffer = ringbuffer;
		this.storyInfoElements = elememts;
	}
	
	
	public void getNextViewPart(JLabel pane) {
		
		panelnum++;
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		for (int i = 0; i < Ressources.TILESPERPANEL; i++){
			//render collectable and character
			if (storyInfoElements.get((panelnum - 1 + i)%Ressources.TILESPERPANEL) instanceof Collectable ||
					storyInfoElements.get((panelnum - 1 + i)%Ressources.TILESPERPANEL) instanceof Character){
				
				if (panelnum < 3){
					storyInfoElements.get((panelnum - 1 + i)%Ressources.TILESPERPANEL).render(g, ringbuffer.top(panelnum - 1 + i).getTileType(), Ressources.LAYERINTERACTION, pane);
				} else{
					storyInfoElements.get((panelnum - 1 + i)%Ressources.TILESPERPANEL).render(g, ringbuffer.top(2*Ressources.TILESPERPANEL + 1).getTileType(), Ressources.LAYERINTERACTION, pane);
				}
			}
			
			//Wenn mehrere Elemente auf eine Kachel gerendert werden, darf i nicht hochgezählt werden
			if (storyInfoElements.get((panelnum + i)%Ressources.TILESPERPANEL).getBlock() == 
					(storyInfoElements.get((panelnum + i)%Ressources.TILESPERPANEL).getBlock())+1){
				i--;
			}
		}
		pane.setIcon(new ImageIcon(image));
	}
}

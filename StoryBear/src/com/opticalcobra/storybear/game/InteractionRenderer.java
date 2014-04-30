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
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.main.Ringbuffer;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.res.StoryBearRandom;


public class InteractionRenderer extends Renderer implements IRenderer{

	private int panelnum = 0;
	private ArrayList<ILevelAppearance> storyInfoElements;
	private LinkedList<TileResult> tileQue;
	private ArrayList<JLabel>[] labels= new ArrayList[3];
	int elementPointer = 0;
	
	
	public InteractionRenderer(LinkedList<TileResult> tileQue, ArrayList<ILevelAppearance> elememts){		
		this.tileQue = tileQue;
		this.storyInfoElements = elememts;
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new ArrayList<JLabel>();
		}
	}
	
	
	public void getNextViewPart(JLabel pane) {
		JLabel currentLabel;
		
		for(JLabel label:labels[panelnum%3]){
			pane.remove(label);
		}
		labels[panelnum%3].clear();
		
		BufferedImage image = new BufferedImage(Ressources.WINDOW.width, Ressources.WINDOW.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
	
		for (int i = 0; i < Ressources.TILESPERPANEL; i++){
			//render collectable and character
			
			if (elementPointer < storyInfoElements.size() && storyInfoElements.get(elementPointer).getBlock()%Ressources.TILESPERPANEL == i){
				if (storyInfoElements.get(elementPointer) instanceof Collectable ||
						storyInfoElements.get(elementPointer) instanceof Character){
					currentLabel = new JLabel();
					if (panelnum < 3){
						tileQue.get((panelnum)*Ressources.TILESPERPANEL + i).setInteractionObjectLabel(currentLabel);
						storyInfoElements.get(elementPointer).render(g, tileQue.get((panelnum)*Ressources.TILESPERPANEL + i).getTileType(), Ressources.LAYERINTERACTION, currentLabel);
					} else{
						tileQue.get(2*Ressources.TILESPERPANEL +i).setInteractionObjectLabel(currentLabel);
						storyInfoElements.get(elementPointer).render(g, tileQue.get(2*Ressources.TILESPERPANEL +i).getTileType(), Ressources.LAYERINTERACTION, currentLabel);
					}
					labels[panelnum%3].add(currentLabel);
					pane.add(currentLabel);
					pane.revalidate();
				}
				
				//Wenn mehrere Elemente auf eine Kachel gerendert werden, darf i nicht hochgezählt werden
				if (elementPointer + 1 < storyInfoElements.size() && storyInfoElements.get(elementPointer).getBlock()%Ressources.TILESPERPANEL == 
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

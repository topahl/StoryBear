package com.opticalcobra.storybear.game;

import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.db.TileResult;
import com.opticalcobra.storybear.main.Ringbuffer;
import com.opticalcobra.storybear.res.Ressources;

public class InteractionLayer extends JLayeredPane {

	private IRenderer renderer;
	private int step = Ressources.WINDOW.width;
	private int currentView = 1; // Zahl des momentan angezeigten JLabels
	private JLayeredPane [] layer;
	private JLabel [] label;
	
	public InteractionLayer() {
		
	}
	
	public void step(){
		step--;
		if(step < 0 ){
			step = Ressources.WINDOW.width;
			currentView = (currentView + 1) % 3;
			RenderThreadWrapper.addRenderTask(renderer,label[(currentView+1)%3]);
		}
		layer[currentView].setLocation(step, 0);
		layer[(currentView+2)%3].setLocation(step-Ressources.WINDOW.width, 0);
		
	}


	public void initialize(IRenderer rendererInteraction, LinkedList<TileResult> tileQue) {
		this.renderer = rendererInteraction;
		
		layer = new JLayeredPane[3];
		label = new JLabel[3];
		
		
		for (int i = 0; i<3; i++){
			layer[i]=new JLayeredPane();
			label[i]=new JLabel();
			this.add(layer[i]);
			RenderThreadWrapper.addRenderTask(renderer, label[i]);
			layer[i].add(label[i]);
			label[i].setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
			if (i==0){
				layer[i].setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
			} else {
				layer[i].setBounds(Ressources.WINDOW.width, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
			}
		}
		
	}

}

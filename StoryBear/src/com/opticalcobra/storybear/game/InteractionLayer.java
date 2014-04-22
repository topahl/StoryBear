package com.opticalcobra.storybear.game;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.res.Ressources;

public class InteractionLayer extends JLayeredPane {

	private IRenderer renderer;
	private int step = Ressources.WINDOW.width;
	private int currentView = 1; // Zahl des momentan angezeigten JLabels
	private JLayeredPane [] layer;
	private JLabel [] label;
	
	public InteractionLayer(IRenderer renderer) {
		layer = new JLayeredPane[3];
		label = new JLabel[3];
		this.renderer=renderer;
		
		for (int i = 0; i<3; i++){
			layer[i]=new JLayeredPane();
			this.add(layer[i]);
			renderer.getNextViewPart(label[i]);
			layer[i].add(label[i]);
			label[i].setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
			if (i==0){
				layer[i].setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
			} else {
				layer[i].setBounds(Ressources.WINDOW.width, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
			}
		}
		
	}
	
	public void step(){
		step--;
		if(step < 0 ){
			step = Ressources.WINDOW.width;
			currentView = (currentView + 1) % 3;
			renderer.getNextViewPart(label[(currentView+1)%3]);
		}
		layer[currentView].setLocation(step, 0);
		layer[(currentView+2)%3].setLocation(step-Ressources.WINDOW.width, 0);
		
	}

}

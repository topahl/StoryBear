package com.opticalcobra.storybear.game;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.res.Ressources;

public class GameLayer extends JLayeredPane {
		
		private IRenderer renderer;
		private int step = Ressources.WINDOW.width;
		private int currentView = 1; // Zahl des momentan angezeigten JLabels
		private JLabel [] layer;
		
		public GameLayer(IRenderer renderer){
			super();
			layer=new JLabel[3];
			this.renderer=renderer;
			
			for (int i = 0; i<3; i++){
				layer[i]=new JLabel();
				this.add(layer[i]);
				renderer.getNextViewPart(layer[i]);
				if (i==0){
					layer[i].setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
				} else {
					layer[i].setBounds(Ressources.WINDOW.width, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
				}
			}
		}
		
		
		/**
		 * Performs a step on the current layer
		 */
		public void step(){
			step--;
			if(step < 0 ){
				step = Ressources.WINDOW.width;
				currentView = (currentView + 1) % 3;
				renderer.getNextViewPart(layer[(currentView+1)%3]);
			}
			layer[currentView].setLocation(step, 0);
			layer[(currentView+2)%3].setLocation(step-Ressources.WINDOW.width, 0);
			
			
			
		}
		
		
}

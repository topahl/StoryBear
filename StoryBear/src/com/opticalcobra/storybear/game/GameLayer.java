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
			layer[0]=new JLabel();
			layer[1]=new JLabel();
			layer[2]=new JLabel();
			this.add(layer[0]);
			this.add(layer[1]);
			this.add(layer[2]);
			this.renderer=renderer;
			renderer.getNextViewPart(layer[0]);
			renderer.getNextViewPart(layer[1]);
			renderer.getNextViewPart(layer[2]);
			layer[0].setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
			layer[1].setBounds(Ressources.WINDOW.width, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
			layer[2].setBounds(Ressources.WINDOW.width, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		}
		
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

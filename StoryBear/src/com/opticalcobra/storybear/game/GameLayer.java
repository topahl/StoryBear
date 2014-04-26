package com.opticalcobra.storybear.game;

import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.db.TileResult;
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
				RenderThreadWrapper.addRenderTask(renderer, layer[i]);
				if (i==0){
					layer[i].setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
				} else {
					layer[i].setBounds(Ressources.WINDOW.width, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
				}
			}
		}
		
		
		public GameLayer(RendererFG2 rendererfg2, LinkedList<TileResult> tileQue) {
			super();
			layer=new JLabel[3];
			this.renderer=renderer;
			
			for (int i = 0; i<3; i++){
				layer[i]=new JLabel();
				this.add(layer[i]);
				RenderThreadWrapper.addRenderTask(renderer, layer[i]);
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
				RenderThreadWrapper.addRenderTask(renderer,layer[(currentView+1)%3]);
			}
			layer[currentView].setLocation(step, 0);
			layer[(currentView+2)%3].setLocation(step-Ressources.WINDOW.width, 0);
			
			
			
		}


		public void initialize(LinkedList<TileResult> tileQue) {
			
		}
		
		
}

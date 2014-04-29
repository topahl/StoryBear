package com.opticalcobra.storybear.game;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;

import com.opticalcobra.storybear.main.Semaphore;
import com.opticalcobra.storybear.menu.LoadingPanel;

public class RenderThreadWrapper extends Thread{
	
	private static RenderThreadWrapper renderThreadWrapper = null;
	
	private JLabel jl;
	private IRenderer ir;
	private static LinkedList<Element> que = new LinkedList<Element>();
	private LoadingPanel loading;
	private boolean enableRenderEnd = false;
	
	private RenderThreadWrapper(){
		this.setPriority(Thread.MIN_PRIORITY);
	}
	
	public static RenderThreadWrapper getInstance(LoadingPanel lp){
		if (renderThreadWrapper == null){
			renderThreadWrapper = new RenderThreadWrapper();
			renderThreadWrapper.start();
			renderThreadWrapper.setLoadingPanel(lp);
		}
		return renderThreadWrapper;
	}

	public static void addRenderTask(IRenderer renderer, JLabel label){
		Element el = new Element();
		el.comp = label;
		el.renderer = renderer;
		que.add(el);
	}
	
	public void setLoadingPanel(LoadingPanel lp) {
		loading = lp;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				loading.setVisible(true);
				
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {}
				enableRenderEnd = true;
			}
		}).start();
	}
	
	@Override
	public void run(){
		while(true){
			if(!que.isEmpty()){
				Element el = que.removeFirst();
//				Debigging 
//				System.out.println(el.renderer.toString());
				el.renderer.getNextViewPart(el.comp);
			}
			this.yield();
			
			// Loadingscreen
			if(enableRenderEnd && que.isEmpty()) 
				loading.setVisible(false);
			loading.setMaximum(que.size());
			loading.update();
		}
	}
	public static class Element{
		public IRenderer renderer;
		public JLabel comp;
	}
	
	public void cleanup() {
		renderThreadWrapper = null;
	}
}

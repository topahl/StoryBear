package com.opticalcobra.storybear.game;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;

import com.opticalcobra.storybear.main.Semaphore;

public class RenderThreadWrapper extends Thread{
	
	private static RenderThreadWrapper renderThreadWrapper = null;
	
	private JLabel jl;
	private IRenderer ir;
	private static LinkedList<Element> que = new LinkedList<Element>();
	
	private RenderThreadWrapper(){
		this.setPriority(Thread.MIN_PRIORITY);
	}
	
	public static RenderThreadWrapper getInstance(){
		if (renderThreadWrapper == null){
			renderThreadWrapper = new RenderThreadWrapper();
			renderThreadWrapper.start();
		}
		return renderThreadWrapper;
	}
	

	public static void addRenderTask(IRenderer renderer, JLabel label){
		Element el = new Element();
		el.comp = label;
		el.renderer = renderer;
		que.add(el);
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
		}
	}
	public static class Element{
		public IRenderer renderer;
		public JLabel comp;
	}
}

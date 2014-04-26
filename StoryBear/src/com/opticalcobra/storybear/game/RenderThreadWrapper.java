package com.opticalcobra.storybear.game;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;

import com.opticalcobra.storybear.main.Semaphore;

public class RenderThreadWrapper extends Thread{
	
	private JLabel jl;
	private IRenderer ir;
	private static LinkedList<Element> que = new LinkedList<Element>();
	
	
	public RenderThreadWrapper(){
		this.setPriority(Thread.MIN_PRIORITY);
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

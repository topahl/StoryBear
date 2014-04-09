package com.opticalcobra.storybear.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Map;

import com.opticalcobra.storybear.res.FontCache;

public abstract class Renderer {
	private static FontCache fc = FontCache.getInstance();
	private static Map desktopHints;
	
	static{
		Toolkit tk = Toolkit.getDefaultToolkit();
		desktopHints = (Map)(tk.getDesktopProperty("awt.font.desktophints"));
	}
	
	protected static void renderText(Graphics2D g,float size ,String text, int x,int y){
		g.setColor(Color.BLACK);
		g.setFont(fc.getFont("Standard", size));
		g.addRenderingHints(desktopHints);
		g.drawString(text, x, y);
	}
}

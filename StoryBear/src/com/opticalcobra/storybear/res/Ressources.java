package com.opticalcobra.storybear.res;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Ressources {
	public static final Dimension SCREEN;
	public static final int GAMESPEED = 10;
	
	static {
		SCREEN=Toolkit.getDefaultToolkit().getScreenSize();
		
	}
}

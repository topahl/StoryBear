package com.opticalcobra.storybear.res;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Ressources {
	public static final Dimension SCREEN;
	public static final int GAMESPEED = 10;
	public static final String RESPATH = "res\\";
	public static final int LANDSCAPETILEWIDTH=120;
	
	
	//Game Character
	//TODO: Dummywerte überarbeiten
	public static final int CHARACTERHEIGHT = 70;
	public static final int CHARACTERWIDTH = 30;
	public static final int CHARACTERSPAWNPOSITIONX = 0;
	public static final int CHARACTERSPAWNPOSITIONY = 400;
	
	
	//Dynamics of the Game
	//TODO: Dummywerte überarbeiten
	public static final int JUMPCONSTANT = 320; //ned for calculation of jump formula
	
	static {
		SCREEN=Toolkit.getDefaultToolkit().getScreenSize();
		
	}
}

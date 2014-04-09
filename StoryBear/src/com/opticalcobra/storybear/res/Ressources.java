package com.opticalcobra.storybear.res;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Ressources {
	
	public static final boolean DEBUG = true;
	
	public static final Dimension FULLHD;
	public static final Dimension SCREEN;
	public static final Dimension WINDOW;
	public static final double SCALE;
	public static final int GAMESPEED = 5;
	public static final String RESPATH = "res\\";
	public static final int RASTERSIZEORG=120;
	public static final int RASTERSIZE;
	public static final int STORYTEXTSIZE=20;
	
	//Game Character
	//TODO: Dummywerte überarbeiten
	public static final int CHARACTERHEIGHT = 70;
	public static final int CHARACTERWIDTH = 50;
	public static final int CHARACTERSPAWNPOSITIONX = 0;
	public static final int CHARACTERSPAWNPOSITIONY = 400;
	
	
	//Dynamics of the Game
	//TODO: Dummywerte überarbeiten
	public static final int JUMPCONSTANTY = 70; //need for calculation of jump formula
	public static final int JUMPCONSTANTX = 15;
	public static final int SPEEDCONSTANTY = 60;
	public static final int RUNCONSTANT = 10;
	
	
	static {
		
		SCREEN=Toolkit.getDefaultToolkit().getScreenSize();
		FULLHD=new Dimension(1920,1080);
		double x = (double)FULLHD.height/(double)SCREEN.height;
		double y = (double)FULLHD.width/(double)SCREEN.width;
		if(x < y){
			SCALE=y;
			WINDOW = new Dimension((int)(FULLHD.width/y),(int)(FULLHD.height/y));
		}else{
			SCALE=x;
			WINDOW = new Dimension((int)(FULLHD.width/x),(int)(FULLHD.height/x));
		}
		RASTERSIZE=(int) (RASTERSIZEORG/SCALE);
		
	}
}

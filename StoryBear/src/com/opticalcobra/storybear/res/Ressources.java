package com.opticalcobra.storybear.res;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Ressources {
	
	public static final boolean DEBUG = false;
	
	public static final Dimension FULLHD;
	public static final Dimension SCREEN;
	public static final Dimension WINDOW;
	public static final double SCALE;
	public static final int GAMESPEED = 5;
	public static final String RESPATH = "res\\";
	public static final int RASTERSIZEORG = 120;
	public static final int RASTERSIZE;
	public static final int STORYTEXTSIZE = 20;
	
	//Colors
	public static final Color SKYCOLOR = new Color(111, 213, 239, 255); //Standard: 158, 234, 252, 255
	
	//Game Character
	//TODO: Dummywerte überarbeiten
	public static final int CHARACTERHEIGHT = 70;
	public static final int CHARACTERWIDTH = 50;
	public static final int CHARACTERSPAWNPOSITIONX = 0;
	public static final int CHARACTERSPAWNPOSITIONY = 400;
	
	
	//Dynamics of the Game
	//TODO: Dummywerte überarbeiten
	public static final int JUMPCONSTANTY = 8; //in- and decreases the speed of the jump
	public static final int JUMPCONSTANTX = 20;
	public static final int SPEEDCONSTANT = 40;	//start speed of a jump
	public static final int RUNCONSTANT = 18;
	
	
	
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

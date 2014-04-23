package com.opticalcobra.storybear.res;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import com.opticalcobra.storybear.game.Window;

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
	public static final int STORYTEXTSIZE = 40;
	
	//Colors
	public static final Color SKYCOLOR = new Color(111, 213, 239, 255); //Standard: 158, 234, 252, 255
	public static final Color SHELFCOLOR = new Color(170,128,86);
	
	//Game Character
	public static final int CHARACTERHEIGHT;
	public static final int CHARACTERWIDTH;
	public static final int CHARACTERSPAWNPOSITIONX = 0;
	//public static final int CHARACTERSPAWNPOSITIONY = 400;
	
	
	//Dynamics of the Game
	//TODO: Dummywerte überarbeiten
	public static final double JUMPCONSTANTY; //in- and decreases the speed of the jump
	public static final int SPEEDCONSTANT;	//start speed of a jump
	public static final int RUNCONSTANT;
	
	
	//Contants for Kacheln and co.
	public static final int TILESPERPANEL = 16;
	public static final int MAXLENGTHOFSCHEME = 160; //unity = tiles
	
	
	//Buttons and Highscore
	public static final int BUTTONSIZE;
	public static final int BUTTONDISTANCE;
	public static final int SCORETEXTSIZE;
	public static final int SCOREDISTANCERIGHT;
	public static final int SCOREDISTANCEUP;
	public static final int SCOREPOINTSFORRUNNING = 2;
	public static final int SCOREDECREASEAFTERMISTAKE = 3;
	
	
	//Layer Konstanten
	
	public static final int LAYERFOREGROUNDONE = 1;
	public static final int LAYERFOREGROUNDTWO = 2;
	public static final int LAYERINTERAKTION = 3;
	public static final int LAYERBACKGROUND = 4;
	public static final int LAYERMIDDLEGROUND = 5;
	
	
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
		WINDOW.width = 16* RASTERSIZE;
		CHARACTERHEIGHT=(int)(90/SCALE);
		CHARACTERWIDTH=(int)(55/SCALE);
		
		JUMPCONSTANTY = 2 / SCALE;
		SPEEDCONSTANT = (int) (36 / SCALE);
		RUNCONSTANT = (int) (7 / SCALE);
		
		BUTTONSIZE = (int)(70 / SCALE);
		BUTTONDISTANCE = (int)(20 / SCALE);
		
		SCORETEXTSIZE = (int)(37 / SCALE);
		SCOREDISTANCERIGHT = (int)(40 / SCALE);
		SCOREDISTANCEUP = (int)(37 /SCALE);
	}
	
}

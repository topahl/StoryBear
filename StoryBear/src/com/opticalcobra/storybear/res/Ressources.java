package com.opticalcobra.storybear.res;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.opticalcobra.storybear.game.Window;

public class Ressources {
	
	public static final boolean DEBUG = false;
	
	public static final String LOADINGPICTURE = "images\\menu_storybook_closed.png";
	
	public static final Dimension FULLHD;
	public static final Dimension SCREEN;
	public static final Dimension WINDOW;
	public static final double SCALE;
	public static final int GAMESPEED = 5;
	public static final String RESPATH;
	public static final int RASTERSIZEORG = 120;
	public static final int RASTERSIZE;
	public static final int STORYTEXTSIZE = 40;
	
	//Colors
	public static final Color SKYCOLOR = new Color(111, 213, 239, 255); //Standard: 158, 234, 252, 255
	public static final Color SHELFCOLOR = new Color(170,128,86);
	public static final Color PAGECOLOR = new Color(252,254,196);
	public static final Color TEXTBROWNCOLOR = new Color(50,20,6);
	
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
	public static final int LAYERINTERACTION = 3;
	public static final int LAYERBACKGROUND = 4;
	public static final int LAYERMIDDLEGROUND = 5;
	
	//ContainerID Konstanten
	public static final int CONTAINERCOLLECTABLEID = 0;
	public static final int CONTAINERILLUSTRATIONBIGID = 1;
	public static final int CONTAINERILLUSTRATIONSMALLID = 2;
	public static final int CONTAINERCHARACTERID = 3;
	public static final int CONTAINERLANDSCAPEID = 4;
	
	
	// Cursor
	public static Cursor CURSORNORMAL;
	public static Cursor CURSORCLICKABLE;
	
	// Menü-Farben
	public static final Color MENUCOLORSELECTED = new Color(178,22,22);
	
	static {
		RESPATH = defaultDirectory();
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
		
		JUMPCONSTANTY = 1.0 / SCALE;
		SPEEDCONSTANT = (int) (20 / SCALE);
		RUNCONSTANT = (int) (7 / SCALE);
		
		BUTTONSIZE = (int)(70 / SCALE);
		BUTTONDISTANCE = (int)(20 / SCALE);
		
		SCORETEXTSIZE = (int)(37 / SCALE);
		SCOREDISTANCERIGHT = (int)(40 / SCALE);
		SCOREDISTANCEUP = (int)(37 /SCALE);
		
		try {
			CURSORNORMAL = Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File(Ressources.RESPATH+"images\\pointer_slice.png")).getSubimage(0, 0, 55, 65), new Point(0,0), "normal");
			CURSORCLICKABLE = Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File(Ressources.RESPATH+"images\\pointer_slice.png")).getSubimage(0, 65, 55, 65), new Point(0,0), "click");
		} catch (HeadlessException | IndexOutOfBoundsException | IOException e) {
			CURSORNORMAL = Cursor.getDefaultCursor();
			CURSORCLICKABLE = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		}
	}
	
	
	private static String defaultDirectory()
	{
		if(System.getenv("development")== null){
		    String OS = System.getProperty("os.name").toUpperCase();
		    if (OS.contains("WIN"))
		        return System.getenv("APPDATA")+"\\StoryBear\\";
		    else if (OS.contains("MAC"))
		        return System.getProperty("user.home") + "/Library/Application/StoryBear/ "
		                + "Support";
		    else if (OS.contains("NUX"))
		        return System.getProperty("user.home")+"\\StoryBear\\";
		    return System.getProperty("user.dir")+"\\StoryBear\\";
		}
		return "res\\";
	}
	
}

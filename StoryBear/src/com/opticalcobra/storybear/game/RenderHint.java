package com.opticalcobra.storybear.game;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class RenderHint implements ILevelAppearance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3391484487924232956L;
	private int block;
	private int occurance; 	//if the same group comes several times, the importance increases
	private int renderHint;
	
	private static transient Imagelib il = Imagelib.getInstance();
	private static transient Database db = new Database();
	
	public static final int RENDERHINT_NONE = -1;
	public static final int RENDERHINT_FOREST = 0;
	public static final int RENDERHINT_CITY = 1;
	public static final int RENDERHINT_VILLAGE = 2;	
	public static final int RENDERHINT_CASTLE = 3;
	public static final int RENDERHINT_LANDSCAPE = 4;
	
	
	public static final int WORDGROUP_LENGTH = 3;
	public static final String[] WORDGROUP_CITY = {"Stadt"};
	public static final String[] WORDGROUP_VILLAGE = {"Hof","Dorf"};
	public static final String[] WORDGROUP_CASTLE = {"Schloss","Burg"};
	public static final String[] WORDGROUP_FOREST = {"Wald","Baum"};
	public static final String[] WORDGROUP_LANDSCAPE = {"Land","Feld","Wiese"};
	

	public RenderHint(int block, int hint, int importance){
		this.block = block;
		this.renderHint = hint;
		this.occurance = importance;
	}
	
	
	public int getRenderHint(){
		return this.renderHint;
	}
	
	public void setBlock(int block){
		this.block = block;
	}
	
	
	@Override
	public int getBlock() {
		return this.block;
	}

	@Override
	public void render(Graphics2D g, int tileTypeId, int layerID, Component toBeNamed) {
		if (layerID == Ressources.LAYERMIDDLEGROUND){
			Point position = db.getObjectPosMiddleground(tileTypeId, Ressources.CONTAINERLANDSCAPEID);
			int image_id;
			String searchWord = "";
			switch (this.renderHint){
			case 0:
				searchWord = "Wald";
				break;
			case 1:
				searchWord = "Stadt";
				break;
			case 2:
				searchWord = "Dorf";
				break;
			case 3:
				searchWord = "Schloss";
				break;
			case 4:
				searchWord = "Land";
				break;
			default:
				break;
			}
			try {
				image_id = db.queryNumberResultOnly("SELECT IMAGE_ID FROM MIDDLEGROUND_OBJECT WHERE WORD = '" + searchWord + "';")[0];
				BufferedImage image = il.loadObjectPic(image_id, "lmg");
				g.drawImage(image,(((block*Ressources.RASTERSIZE))% Ressources.WINDOW.width)+position.x,position.y, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	public int getImportance() {
		return occurance;
	}


	public void setImportance(int importance) {
		this.occurance = importance;
	}

}

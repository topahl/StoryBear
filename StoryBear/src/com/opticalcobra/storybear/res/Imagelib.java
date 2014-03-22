package com.opticalcobra.storybear.res;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.HashMap;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.exceptions.ImageNotFoundException;

/**
 * 
 * @author Tobias
 *
 */
public class Imagelib {
	private static Imagelib instance;
	
	/*
	 * Hashmap prefixes:
	 * 		map-	All map elements
	 * 		int-	Internal graphics (eg cluster graphics)
	 */
	private HashMap<String,BufferedImage> images;  
	private Database db;
	
	private Imagelib(){
		db = new Database();
	}
	
	
	/**
	 * 
	 * loads the requested image if necessary and returns it as {@link BufferedImage}
	 * 
	 * @param graphicName name of the graphic that should be loaded
	 * @return the requested image loaded in RAM
	 * @author Tobias
	 * @exception ImageNotFoundException will be raised if the image was not found in the database
	 */
	public BufferedImage loadMapItem(String graphicName) throws ImageNotFoundException{
		BufferedImage result;
		result = images.get("map-"+graphicName);
		if(result != null){
			return result;
		}
		try {
			db.query("SELECT * FROM "+Constants.IMAGETABLE+" WHERE objectname = "+graphicName);
		} catch (SQLException e) {
			throw new ImageNotFoundException("Could not find an Image for:'"+graphicName+"' in the database", e);
		}
		
		
		return null;
	}
	
	
	public static Imagelib getInstance(){
		if(instance == null){
			instance = new Imagelib();
		}
		return instance;
	}
	
}

package com.opticalcobra.storybear.res;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.db.ImageResult;
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
		images = new HashMap<String,BufferedImage>();
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
	public BufferedImage loadLandscapeTile(String landscape, int type) throws ImageNotFoundException{
		BufferedImage result;
		BufferedImage full;
		
		result = images.get("map-"+landscape+"#"+type);
		if(result != null){
			return result;
		}
		
		try {
			ImageResult ir = db.queryImagedata("SELECT * FROM "+Constants.IMAGETABLE+" WHERE URL = '"+landscape + "' AND X = "+(type*Ressources.LANDSCAPETILEWIDTH));
			full=loadRessourcesImage(landscape);
			result=full.getSubimage(ir.getX(), ir.getY(), ir.getWidth(), ir.getHeight());
			images.put("map-"+landscape+"#"+type, result);
			return result;
		} catch (SQLException e) {
			throw new ImageNotFoundException("Could not find an Image for:'"+landscape+"' and type: "+type+" in the database", e);
		}
	}
	
	private BufferedImage loadRessourcesImage(String graphicName) throws ImageNotFoundException{
		BufferedImage result;
		result = images.get("img-"+graphicName);
		if(result != null){
			return result;
		}
		
		try {
			result = ImageIO.read(new File(Ressources.RESPATH+graphicName));
			images.put("img-"+graphicName, result);
			return result;
		} catch (IOException e) {
			throw new ImageNotFoundException("Could not find an Image for:'"+graphicName+"' on file System", e);
		}
	}
	
	public static Imagelib getInstance(){
		if(instance == null){
			instance = new Imagelib();
		}
		return instance;
	}
	
}

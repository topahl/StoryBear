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
import com.opticalcobra.storybear.exceptions.QueryTypeNotFoundException;

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
	private HashMap<String,BufferedImage> images;   //Hashmap für alle Bilder
	private Database db; //Datenbankverbindung
	private StoryBearRandom rand = StoryBearRandom.getInstance(); //Zufall mit seed
	
	//Constants
	public static final char QUERY_FOREGROUND = 'f';
	public static final char QUERY_MIDDLEGROUND = 'm';
	public static final char QUERY_BACKGROUND = 'b';
	public static final char QUERY_CLOUDS = 'c';
	
	
	private Imagelib(){
		db = new Database();
		images = new HashMap<String,BufferedImage>();
	}
	
	
	/**
	 * 
	 * loads the requested image if necessary and returns it as {@link BufferedImage}
	 * 
	 * @param queryType dertermines the DB table to select on..represents a layer
	 * @param type type id for requested tile
	 * @return the requested image loaded in RAM
	 * @author Tobias
	 * @exception ImageNotFoundException will be raised if the image was not found in the database
	 */
	public BufferedImage loadLandscapeTile(int type, char queryType) throws ImageNotFoundException{
		BufferedImage result;
		BufferedImage full;
		ImageResult image = getRandomID(type, queryType);
		//Request can be handled with internal Hash map
		result = images.get("map-"+image.getId());
		if(result != null){
			return result;
		}
		
		//Request must be handled via database request
		
		//cut images to fit
		full=loadRessourcesImage(image.getUrl());
		result=full.getSubimage((int)(image.getX()/Ressources.SCALE),(int) (image.getY()/Ressources.SCALE),(int) (image.getWidth()/Ressources.SCALE),(int) (image.getHeight()/Ressources.SCALE));
		images.put("map-"+image.getId(), result);
		return result;
	}
	
	
	/**
	 * Loads a full Sprite Image eather from local cache or from file system
	 * @param graphicName Name of the requested File
	 * @return Loaded Image from the requested sprite
	 * @throws ImageNotFoundException The sprite was not on the file System
	 * @author Tobias
	 */
	private BufferedImage loadRessourcesImage(String graphicName) throws ImageNotFoundException{
		BufferedImage result;
		//chech whether image is already cached
		result = images.get("img-"+graphicName);
		if(result != null){
			return result;
		}
		
		try {
			//load images from file system
			BufferedImage temp = ImageIO.read(new File(Ressources.RESPATH+graphicName));
			result = new BufferedImage((int)(temp.getWidth()/Ressources.SCALE),(int)(temp.getHeight()/Ressources.SCALE),BufferedImage.TYPE_INT_ARGB);
			result.getGraphics().drawImage(temp, 0, 0, (int)(temp.getWidth()/Ressources.SCALE),(int)(temp.getHeight()/Ressources.SCALE),null);
			images.put("img-"+graphicName, result);
			return result;
		} catch (IOException e) {
			//images nicht gefunden
			throw new ImageNotFoundException("Could not find an Image for:'"+graphicName+"' on file System", e);
		}
	}
	
	
	/**
	 * Singleton implementation
	 * @return Instance of {@link Imagelib}
	 */
	public static Imagelib getInstance(){
		if(instance == null){
			instance = new Imagelib();
		}
		return instance;
	}

	/**
	 * Return a list of possible tiles to come after the current one
	 * @param type type number of tile to be tested for following tiles
	 * @param queryType Constancy from {@link Imagelib} to identify the layer for witch the query is
	 * @return list of possible following tiles
	 * @author Tobias
	 */
	public Integer[] getFollowingTiles(int type, char queryType){
	
		String query;
		switch (queryType) {// determine which tbles to select on
		case QUERY_BACKGROUND:
			query="SELECT DISTINCT f.following_type_id FROM BACKGROUND b JOIN IMAGES i ON i.id = b.images_id JOIN BACKGROUND_FOLLOWING f ON b.type_id = f.type_id WHERE f.type_id = "+type+";";
			break;
		case QUERY_FOREGROUND:
			query="SELECT DISTINCT f.following_type_id FROM FOREGROUND_ONE b JOIN IMAGES i ON i.id = b.images_id JOIN FOREGROUND_FOLLOWING f ON b.type_id = f.type_id WHERE f.type_id = "+type+";";
			break;
		case QUERY_MIDDLEGROUND:
			query="SELECT DISTINCT f.following_type_id FROM MIDDLEGROUND b JOIN IMAGES i ON i.id = b.images_id JOIN MIDDLEGROUND_FOLLOWING f ON b.type_id = f.type_id WHERE f.type_id = "+type+";";
			break;
		default:
			throw new QueryTypeNotFoundException("Your Querytype was not valid. Type: "+queryType);
		}
		try {
			return db.queryNumberResultOnly(query); // run Query on DB
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a Random ID of an Image suitable for the requested slice type from the database
	 * @param type The type a Image ID should be returned from
	 * @param queryType  Constancy from {@link Imagelib} to identify the layer for witch the query is
	 * @return Image ID
	 * @author Tobias
	 */
	private ImageResult getRandomID(int type, char queryType){
		String dbName;
		switch (queryType) {//determine which database to query on
		case QUERY_BACKGROUND:
			dbName="BACKGROUND";
			break;
		case QUERY_FOREGROUND:
			dbName="FOREGROUND_ONE";
			break;
		case QUERY_MIDDLEGROUND:
			dbName="MIDDLEGROUND";
			break;
		case QUERY_CLOUDS:
			dbName="";
			break;
		default:
			throw new QueryTypeNotFoundException("Your Querytype was not valid. Type: "+queryType);
		}
		try {
			Integer[] ids;
			if(queryType != QUERY_CLOUDS){
				ids = db.queryNumberResultOnly("SELECT i.id from images i JOIN "+dbName+" b ON i.id = b.images_id WHERE b.type_id = "+type+";");
			}
			else{
				ids = db.queryNumberResultOnly("SELECT id from images where url = 'images\\layer_slice_clouds.png'");
			}
				
			return db.queryImagedata(ids[rand.nextInt(ids.length)]);
		} catch (SQLException e) {
			e.printStackTrace();
			return null; //TODO richtiges fehlerhandlich einbauen
		}
		
	}
	
}

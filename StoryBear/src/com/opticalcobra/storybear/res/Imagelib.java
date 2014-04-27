package com.opticalcobra.storybear.res;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

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
	 * 		img-	Internal graphics (eg cluster graphics)
	 * 		hero-	Image of a hero
	 * 		cha-	Character Object
	 * 		menu-	Images for Menu
	 * 		col-    Collectable
	 * 		ils-    Illustration Small (Animals, things)
	 * 		ilb-	Illustration Big (Vehicle, building)
	 */
	
	private HashMap<String,BufferedImage> images;   //Hashmap für alle Bilder
	private Database db; //Datenbankverbindung
	private StoryBearRandom rand = StoryBearRandom.getInstance(); //Zufall mit seed
	private Random realRand = new Random();
	
	//Constants
	public static final char QUERY_FOREGROUND = 'f';
	public static final char QUERY_MIDDLEGROUND = 'm';
	public static final char QUERY_BACKGROUND = 'b';
	public static final char QUERY_CLOUDS = 'c';
	public static final char QUERY_FOREGROUNDTWO = 'v';
	
	public static final int MENU_SHELF = 246;
	public static final int MENU_SCROLL_UP = 247;
	public static final int MENU_SCROLL_DOWN = 248;
	public static final int MENU_SCROLL_THUMB_TOP = 249;
	public static final int MENU_SCROLL_THUMB_BOTTOM = 250;
	public static final int MENU_SCROLL_THUMB_MIDDLE = 251;
	public static final int MENU_BOOK_1 = 261;
	public static final int MENU_BOOK_2 = 262;
	public static final int MENU_BOOK_3 = 263;
	public static final int MENU_BOOK_4 = 264;
	
	public static final int GAME_BUTTON_MENU_BLACK = 252;
	public static final int GAME_BUTTON_MENU_WHITE = 255;
	public static final int GAME_BUTTON_MENU_GREY = 258;
	public static final int GAME_BUTTON_BREAK_BLACK = 253;
	public static final int GAME_BUTTON_BREAK_WHITE = 256;
	public static final int GAME_BUTTON_BREAK_GREY = 259;
	public static final int GAME_BUTTON_EXIT_BLACK = 254;
	public static final int GAME_BUTTON_EXIT_WHITE = 257;
	public static final int GAME_BUTTON_EXIT_GREY = 260;
	
	
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
	public BufferedImage loadLandscapeTile(int type, char queryType, String settingNameForFG) throws ImageNotFoundException{
		BufferedImage result;
		BufferedImage full;
		ImageResult image = getRandomID(type, queryType, settingNameForFG);
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
	 * @param rundirection of the hero pic where he is looking at (front, right, left)
	 * @param type of the hero, e.g. a bear, ...
	 * @return Loaded Image requested image
	 * @exception ImageNotFoundException will be raised if the image was not found in the database
	 * @author Miriam
	 * @throws SQLException 
	 */
	public BufferedImage loadHeroPic(char rundirection, char type) throws ImageNotFoundException, SQLException{
		BufferedImage result;
		BufferedImage full;
		String sql = "SELECT IMAGEID FROM HEROS WHERE RUNDIRECTION = '" + rundirection + "' AND TYPE = '" + type + "'";
		ImageResult image = db.queryImagedata(db.queryNumberResultOnly(sql)[0]);
		
		result = images.get("hero-"+image.getId());
		if(result != null){
			return result;
		}
		
		//cut images to fit
		full=loadRessourcesImage(image.getUrl());
		result=full.getSubimage((int)(image.getX()/Ressources.SCALE),(int) (image.getY()/Ressources.SCALE),(int) (image.getWidth()/Ressources.SCALE),(int) (image.getHeight()/Ressources.SCALE));
		images.put("hero-"+image.getId(), result);
		
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
		//Check whether image is already cached
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
	 * @author Martika
	 * @param image_id
	 * @param prefix
	 * @return
	 */
	public BufferedImage loadObjectPic(int image_id, String prefix){
		BufferedImage result;
		BufferedImage full;
		//Check whether image is already cached
		result = images.get(prefix+"-"+image_id);
		if(result != null){
			return result;
		}
		//Request must be handled via database request
		ImageResult image;
		try {
			image = db.queryImagedata(image_id);
			full=loadRessourcesImage(image.getUrl());
			result=full.getSubimage((int)(image.getX()/Ressources.SCALE),(int) (image.getY()/Ressources.SCALE),(int) (image.getWidth()/Ressources.SCALE),(int) (image.getHeight()/Ressources.SCALE));
			images.put(prefix+"-"+image_id, result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//cut images to fit
		return result;
		
	}
	
	/**
	 * load images for design
	 * @param name name as identifier for image
	 * @return need image
	 * @throws ImageNotFoundException
	 * @author Nicolas
	 */
	public BufferedImage loadDesignImage(String name) throws ImageNotFoundException {
		BufferedImage result;
		BufferedImage full;
		
		try {
			ImageResult image = db.queryImagedata(db.queryNumberResultOnly("SELECT IMAGES_ID FROM DESIGNELEMENTS WHERE NAME = '" + name + "';")[0]);
			result = images.get("design-"+image.getId());
			if(result != null){
				return result;
			}
			
			//cut images to fit
			full = loadRessourcesImage(image.getUrl());
			result = full.getSubimage((int)(image.getX()/Ressources.SCALE),(int) (image.getY()/Ressources.SCALE),(int) (image.getWidth()/Ressources.SCALE),(int) (image.getHeight()/Ressources.SCALE));
			images.put("design-"+image.getId(), result);
			
			return result;
		} catch (SQLException e) {
			throw new ImageNotFoundException("No entry for " + name + " in database", e);
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
		case QUERY_FOREGROUNDTWO:
			query="SELECT DISTINCT f.following_type_id FROM FOREGROUND_TWO b JOIN IMAGES i ON i.id = b.images_id JOIN FOREGROUND_FOLLOWING f ON b.type_id = f.type_id WHERE f.type_id = "+type+";";
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
	private ImageResult getRandomID(int type, char queryType, String settingNameForFG){
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
		case QUERY_FOREGROUNDTWO:
			dbName = "FOREGROUND_TWO";
			break;
		default:
			throw new QueryTypeNotFoundException("Your Querytype was not valid. Type: "+queryType);
		}
		try {
			Integer[] ids;
			if(queryType != QUERY_CLOUDS && queryType != QUERY_FOREGROUNDTWO){
				ids = db.queryNumberResultOnly("SELECT i.id from images i JOIN "+dbName+" b ON i.id = b.images_id WHERE b.type_id = "+type+";");
				return db.queryImagedata(ids[rand.nextInt(ids.length)]);
			}
			else{
				if (queryType == QUERY_CLOUDS){
					ids = db.queryNumberResultOnly("SELECT id from images where url = 'images\\layer_slice_clouds.png'");
					return db.queryImagedata(ids[realRand.nextInt(ids.length)]);
				} else {
					ids = db.queryNumberResultOnly("SELECT i.id from images i JOIN "+dbName+" b ON i.id = b.images_id WHERE b.type_id = "+type+" and b.setting_name = '"+ settingNameForFG +"';");
					return db.queryImagedata(ids[rand.nextInt(ids.length)]);
				}
			}
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null; //TODO richtiges fehlerhandlich einbauen
		}
		
	}
	/**
	 * 
	 * @param type
	 */
	public BufferedImage menuImage (int type){
		BufferedImage result;
		BufferedImage full;
		//Check whether image is already cached
		result = images.get("menu-"+type);
		if(result != null){
			return result;
		}
		//Request must be handled via database request
		ImageResult image;
		try {
			image = db.queryImagedata(type);
			full=loadRessourcesImage(image.getUrl());
			result=full.getSubimage((int)(image.getX()/Ressources.SCALE),(int) (image.getY()/Ressources.SCALE),(int) (image.getWidth()/Ressources.SCALE),(int) (image.getHeight()/Ressources.SCALE));
			images.put("menu-"+type, result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
}

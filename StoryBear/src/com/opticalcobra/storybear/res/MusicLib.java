package com.opticalcobra.storybear.res;

import com.opticalcobra.storybear.db.Database;


/**
 * 
 * @author Nicolas
 *
 */
public class MusicLib {
	private static MusicLib instance;
	
	private Database db;
	
	/**
	 * 
	 */
	private MusicLib() {
		db = new Database();
	}
	
	
	public MusicFile loadMusic(String name) {
		// TODO use Database to load media file from phyical location
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public static MusicLib getInstance() {
		return (instance == null) ? instance = new MusicLib() : instance;
	}
}

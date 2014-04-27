package com.opticalcobra.storybear.db;

import org.hsqldb.server.ServerConstants;

import com.opticalcobra.storybear.res.Ressources;

public class DBConstants {

	public static final int SEREVR_ONLINE = ServerConstants.SERVER_STATE_ONLINE;
	public static final int SEREVR_SHUTDOWN = ServerConstants.SERVER_STATE_SHUTDOWN;
	public static final int SEREVR_OPENING = ServerConstants.SERVER_STATE_OPENING;
	public static final int SEREVR_CLOSING = ServerConstants.SERVER_STATE_CLOSING;
	
	public static final int WORD_OBJECT_TYPE_CHARACTER = 0;
	public static final int WORD_OBJECT_TYPE_COLLECTABLE = 1;
	public static final int WORD_OBJECT_TYPE_ILLUSTRATION_SMALL = 2;
	public static final int WORD_OBJECT_TYPE_ILLUSTRATION_BIG = 3;
	public static final int WORD_OBJECT_TYPE_MIDDLEGROUND = 4;
	public static final int WORD_OBJECT_TYPE_NO_IMAGE = -1;
	
	//Constans for level heights
	public static final int LEVELHEIGHTZERO 		= (int) (720/Ressources.SCALE);
	public static final int LEVELHEIGHTMINUSONE 	= (int) (780/Ressources.SCALE);
	public static final int LEVELHEIGHTMINUSTWO 	= (int) (840/Ressources.SCALE);
	public static final int LEVELHEIGHTMINUSTHREE 	= (int) (910/Ressources.SCALE);
	public static final int LEVELHEIGHTMINUSFORE 	= (int) (990/Ressources.SCALE);
	public static final int LEVELHEIGHTMINUSFIVE 	= (int) (1061/Ressources.SCALE);
	public static final int LEVELHEIGHTPLUSONE 		= (int) (655/Ressources.SCALE);
	public static final int LEVELHEIGHTPLUSTWO 		= (int) (601/Ressources.SCALE);
	//public static final int LEVELHEIGHTPLUSTHREE 	= (int) (599/Ressources.SCALE);
	//public static final int LEVELHEIGHTZERO 		= (int) (719/Ressources.SCALE);
	
		
	

}

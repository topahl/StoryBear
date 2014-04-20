package com.opticalcobra.storybear.db;

import java.sql.ResultSet;
import java.util.ArrayList;

public class WordResult {
	private int type;
	private int image_id;
	private ArrayList[] resultSet;
	
	public WordResult(int type, int image_id, ArrayList[] rs){
		this.type=type;
		this.image_id=image_id;
		this.resultSet = rs;
	}
	
	public ArrayList[] getResultSet(){
		return this.resultSet;
	}
	

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the image_id
	 */
	public int getImage_id() {
		return image_id;
	}
}

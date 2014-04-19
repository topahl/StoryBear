package com.opticalcobra.storybear.db;

import java.sql.ResultSet;

public class WordResult {
	private int type;
	private int image_id;
	private ResultSet resultSet;
	
	public WordResult(int type, int image_id, ResultSet rs){
		this.type=type;
		this.image_id=image_id;
		this.resultSet = rs;
	}
	
	
	public ResultSet getResultSet(){
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

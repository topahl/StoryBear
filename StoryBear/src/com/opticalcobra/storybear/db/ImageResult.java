package com.opticalcobra.storybear.db;

public class ImageResult {
	private int id;
	private int x;
	private int y;
	private int height;
	private int width;
	private String url;

	public ImageResult(int id, int x, int y, int height, int width, String url){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.url=url;
		this.id=id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

}

package com.opticalcobra.storybear.db;

public class ImageResult {
	private int x;
	private int y;
	private int height;
	private int width;
	private String url;

	public ImageResult(int x, int y, int height, int width, String url){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.url=url;
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

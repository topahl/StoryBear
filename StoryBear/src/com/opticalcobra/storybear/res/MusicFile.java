package com.opticalcobra.storybear.res;

import javafx.scene.media.Media;

/**
 * @author Nicolas
 */
public class MusicFile {
	private String category;
	private String title;
	private String path;
	private Media media;

	public MusicFile(String title, String category, String path) {
		this.title = title;
		this.category = category;
		this.path = path;
		this.media = new Media(path);
	}

	public Media getMedia() {
		return media;
	}
}

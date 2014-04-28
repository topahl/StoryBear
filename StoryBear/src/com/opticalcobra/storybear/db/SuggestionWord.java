package com.opticalcobra.storybear.db;

public class SuggestionWord {
	private int imageId;
	private String word;
	private boolean big;
	
	public boolean isBig() {
		return big;
	}
	public void setBig(boolean big) {
		this.big = big;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
}

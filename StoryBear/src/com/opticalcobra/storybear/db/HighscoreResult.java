package com.opticalcobra.storybear.db;

public class HighscoreResult {
	
	private int user_id;
	private int level_id;
	private int score;
	
	public HighscoreResult(int user, int level, int score){
		this.setUser_id(user);
		this.setLevel_id(level);
		this.setScore(score);
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getLevel_id() {
		return level_id;
	}

	public void setLevel_id(int level_id) {
		this.level_id = level_id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

}

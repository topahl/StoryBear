package com.opticalcobra.storybear.editor;

import java.util.Date;
import com.opticalcobra.storybear.main.User;

public class Story {
	
	private String title;
	private String text;
	private User author;		
	private Date changeDate;	
	
	
	public Story(){
		
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public User getAuthor() {
		return author;
	}


	public void setAuthor(User author) {
		this.author = author;
	}


	public Date getChangeDate() {
		return changeDate;
	}


	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

}

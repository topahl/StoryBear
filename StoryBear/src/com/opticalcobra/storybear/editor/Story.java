package com.opticalcobra.storybear.editor;

import java.io.Serializable;
import java.util.Date;

import com.opticalcobra.storybear.main.User;

public class Story implements Serializable, Comparable<Story>{
	
	private static final long serialVersionUID = 4223589285497566422L;
	private String title;
	private String text;
	private User author;		
	private Date changeDate;	
	private int version;
	private int id;
	
	



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


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public int compareTo(Story o) {
		return getTitle().compareTo(o.getTitle());
	}
}

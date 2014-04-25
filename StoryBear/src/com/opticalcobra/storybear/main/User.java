package com.opticalcobra.storybear.main;

import java.io.Serializable;

public class User implements Serializable{
	private static User currentUser;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5273747412308634179L;
	private String name;
	private int id;
	
	public User(String name){
		this.name=name;
	}
	
	public User(int id, String name){
		this(name);
		this.id=id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return name
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * 
	 */
	public static void setCurrentUser(User newCurrentUser) {
		currentUser = newCurrentUser;
	}
	
	public static boolean isCurrentUserSet() {
		return currentUser != null;
	}
}

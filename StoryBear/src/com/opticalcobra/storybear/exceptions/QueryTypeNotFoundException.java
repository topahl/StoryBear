package com.opticalcobra.storybear.exceptions;

public class QueryTypeNotFoundException extends StoryBearException {
	public QueryTypeNotFoundException(){
		super("Given Query Type is not valid");
	}
	public QueryTypeNotFoundException(String message) {
		super(message);
	}
	public QueryTypeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	public QueryTypeNotFoundException(Throwable cause) {
		super(cause);
	}
}

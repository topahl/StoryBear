package com.opticalcobra.storybear.exceptions;

public class ImageNotFoundException extends StoryBearException {
	public ImageNotFoundException(){
		super("An Image could not be loaded");
	}
	public ImageNotFoundException(String message) {
		super(message);
	}
	public ImageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	public ImageNotFoundException(Throwable cause) {
		super(cause);
	}
}

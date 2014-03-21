package com.opticalcobra.storybear.exceptions;

import javax.xml.ws.handler.MessageContext;

/**
 * Header Exception for all StoryBear Exception
 * @author Tobias
 *
 */
public class StoryBearException extends RuntimeException {
	public StoryBearException() {
		super("An internal error has occured");
	}
	public StoryBearException(String message) {
		super(message);
	}
	public StoryBearException(String message, Throwable cause) {
		super(message, cause);
	}
	public StoryBearException(Throwable cause) {
		super(cause);
	}
}

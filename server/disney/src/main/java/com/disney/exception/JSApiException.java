package com.disney.exception;

public class JSApiException extends Exception {

	private static final long serialVersionUID = -8164800807515083049L;

	public JSApiException(String msg){  
		super(msg);  
	}  

	public JSApiException(String message, Throwable cause) {
		super(message, cause);
	}
	
}

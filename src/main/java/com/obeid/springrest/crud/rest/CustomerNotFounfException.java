package com.obeid.springrest.crud.rest;

public class CustomerNotFounfException extends RuntimeException {

	public CustomerNotFounfException() {
		
	}

	public CustomerNotFounfException(String message, 
									Throwable cause, 
									boolean enableSuppression,
									boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public CustomerNotFounfException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CustomerNotFounfException(String message) {
		super(message);
		
	}

	public CustomerNotFounfException(Throwable cause) {
		super(cause);
		
	}

}

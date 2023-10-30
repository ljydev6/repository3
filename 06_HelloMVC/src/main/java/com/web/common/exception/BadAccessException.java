package com.web.common.exception;

public class BadAccessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3038684194123148777L;
	public BadAccessException(String msg) {
		super(msg);
	}
}

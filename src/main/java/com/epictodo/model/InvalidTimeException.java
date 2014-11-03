package com.epictodo.model;

public class InvalidTimeException extends Exception {

	private String time;
	
	public InvalidTimeException(String time) {
		this.time = time;
	}
}


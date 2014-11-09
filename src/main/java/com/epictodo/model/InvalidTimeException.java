package com.epictodo.model;

//@author A0111683L
public class InvalidTimeException extends Exception {

	private String time;
	
	public InvalidTimeException(String time) {
		this.time = time;
	}
}


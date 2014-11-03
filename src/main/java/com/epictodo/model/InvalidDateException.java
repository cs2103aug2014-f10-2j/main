package com.epictodo.model;

public class InvalidDateException extends Exception {
	
	private String date;
	
	public InvalidDateException(String date) {
		this.date = date;
	}

}


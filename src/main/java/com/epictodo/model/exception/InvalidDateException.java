package com.epictodo.model.exception;

//@author A0111683L
public class InvalidDateException extends Exception {

    private String date;

    public InvalidDateException(String date) {
        this.date = date;
    }

}


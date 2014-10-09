//package com.epictodo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

class DeadlineTask extends Task {
	/************** Data members **********************/
	private static ArrayList<String> Tasks = new ArrayList<String>();
	private String taskName;
	private String taskDescription;
	private String date;
	private String time;
	private String dateTime;
	private long unixTimeStamp;

	/************** Constructors **********************/
	DeadlineTask(String taskName, String ddmmyy, String time, String desc) {
		setTaskName(taskName);
		setDate(ddmmyy);
		setTime(time);
		setTaskDescription(taskDescription);
		try{
		setDateTime(ddmmyy, time);
		}catch(Exception e){
			
		}
	}
	
	/**************** Accessors ***********************/
	String getTaskName() {
		return taskName;
	}
	
	String getDate() {
		return date;
	}
	
	String getTime() {
		return time;
	}

	String getTaskDescription() {
		return taskDescription;
	}
	//This method converts the stored unixTimeStamp into "ddMMyy HH:mm"
	String getDateTime() {
		dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm").format(new java.util.Date (unixTimeStamp*1000));
		return dateTime;
	}

	/**************** Mutators ************************/
	void setTaskName(String newTask) {
		taskName = newTask;
	}
	
	void setDate(String newDate) {
		date = newDate;
	}
	
	void setTime(String newTime) {
		time = newTime;
	}

	void setTaskDescription(String newTaskDescription) {
		taskDescription = newTaskDescription;
	}
	
	void setDateTime(String date, String time) throws ParseException {
		String dateTimeTemp = date + " " + time;
		long epoch = new java.text.SimpleDateFormat("ddMMyy HH:mm").parse(dateTimeTemp).getTime() / 1000;
		unixTimeStamp = epoch;
	}
	
/*	
	public static Date insertStartDate(Date calendar) {
		Date result = new Date();
		return result;
	}
	
	public static Date insertEndDate(Date calendar) {
		Date result = new Date();
		return result;
	}
	*/
}

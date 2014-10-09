//package com.epictodo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

class TimedTask extends BaseClass {
	/************** Data members **********************/
	private static ArrayList<String> Tasks = new ArrayList<String>();
	private String taskName;
	private String taskDescription;
	private String date;
	private String time;
	private double duration;
	private String dateTime;
	private long unixTimeStamp;

	/************** Constructors 
	 * @throws ParseException **********************/
	TimedTask() throws ParseException {
		setTaskName("NIL");
		setDate("010170");
		setTime("23:59");
		setDuration(1.5);
		setTaskDescription("NIL");
		setDateTime("010170", "23:59");
	}

	TimedTask(String taskName, String ddmmyy, String time, double duration, String desc) throws ParseException {
		setTaskName(taskName);
		setDate(ddmmyy);
		setTime(time);
		setDuration(duration);
		setTaskDescription(taskDescription);
		setDateTime(ddmmyy, time);
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
	
	double getDuration() {
		return duration;
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
	
	void setDuration(double newDuration) {
		duration = newDuration;
	}

	void setTaskDescription(String newTaskDescription) {
		taskDescription = newTaskDescription;
	}
	
	void setDateTime(String date, String time) throws ParseException {
		String dateTimeTemp = date + " " + time;
		//long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;
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

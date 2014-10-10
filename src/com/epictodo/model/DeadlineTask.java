package com.epictodo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DeadlineTask extends Task {
	/************** Data members **********************/
	private long endDateTime;

	/************** Constructors **********************/
	public DeadlineTask(String taskName, String taskDescription, int priority, String ddmmyy, String time) {
		super(taskName, taskDescription, priority);
		try {
			setDateTime(ddmmyy, time);
		}
		catch(Exception e){}
	}
	
	/**************** Accessors ***********************/
	public String getEndDateTime() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm").format(new java.util.Date (endDateTime*1000));
		return dateTime;
	}

	/**************** Mutators ************************/
	public void setDateTime(String date, String time) throws ParseException {
		String dateTimeTemp = date + " " + time;
		//long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;
		long epoch = new java.text.SimpleDateFormat("ddMMyy HH:mm").parse(dateTimeTemp).getTime() / 1000;
		endDateTime = epoch;
	}
	
/*	void setDateTime(String date, String time) throws ParseException {
		String dateTimeTemp = date + " " + time;
		long epoch = new java.text.SimpleDateFormat("ddMMyy HH:mm").parse(dateTimeTemp).getTime() / 1000;
		unixTimeStamp = epoch;
	}
*/	
	
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

package com.epictodo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DeadlineTask extends Task {
	/************** Data members **********************/
	private long endDateTime;

	/************** Constructors **********************/
	public DeadlineTask(String taskName, String taskDescription, int priority, long endDateTime) {
		super(taskName, taskDescription, priority);
		this.endDateTime = endDateTime;
	}
	
	/**************** Accessors ***********************/
	public long getEndDateTime(){
		return endDateTime;
	}

	/**************** Mutators ************************/
	void setEndDateTime(long endDateTime) {
		this.endDateTime = endDateTime;
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

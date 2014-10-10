package com.epictodo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

class TimedTask {
	/************** Data members **********************/
	private String taskName;
	private String taskDescription;
	private long startDateTime;
	private long endDateTime;

	/************** Constructors 
	 * @throws ParseException **********************/
	TimedTask() throws ParseException {
		setTaskName("NIL");
		setDuration(1.5);
		setTaskDescription("NIL");
		setDateTime("010170", "23:59");
	}

	TimedTask(String taskName, String ddmmyy, String time, double duration, String desc) {
		setTaskName(taskName);
		try{
		setDateTime(ddmmyy, time);
		setDuration(duration);
		}
		catch(Exception e){}
		setTaskDescription(taskDescription);

	}
	


	/**************** Accessors ***********************/
	String getTaskName() {
		return taskName;
	}

	

	String getTaskDescription() {
		return taskDescription;
	}
	//This method converts the stored unixTimeStamp into "ddMMyy HH:mm"
	String getDateTime() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm").format(new java.util.Date (startDateTime*1000));
		return dateTime;
	}
	
	String getendDateTime() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm").format(new java.util.Date (endDateTime*1000));
		return dateTime;
	}

	/**************** Mutators ************************/
	void setTaskName(String newTask) {
		taskName = newTask;
	}
	
	private void setDuration(double duration) {
		// TODO Auto-generated method stub
		long temp = (long) (startDateTime + (duration*60*60));
		endDateTime = temp;
	}
	

	void setTaskDescription(String newTaskDescription) {
		taskDescription = newTaskDescription;
	}
	
	void setDateTime(String date, String time) throws ParseException {
		String dateTimeTemp = date + " " + time;
		//long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;
		long epoch = new java.text.SimpleDateFormat("ddMMyy HH:mm").parse(dateTimeTemp).getTime() / 1000;
		startDateTime = epoch;
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

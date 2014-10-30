package com.epictodo.model;

import java.text.ParseException;

public class DeadlineTask extends Task {
	/************** Data members **********************/
	private long endDateTime;

	/************** Constructors **********************/
	public DeadlineTask(String taskName, String taskDescription, int priority,
			String ddmmyy, String time) {
		super(taskName, taskDescription, priority);
		// This checks whether date and time entered are of correct length
		assert ddmmyy.length() == 6;
		assert time.length() == 4;

		// This checks whether date and time entered are valid integers or not
		try {
			int enteredDate = Integer.parseInt(ddmmyy);
			int enteredTime = Integer.parseInt(time);
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		try {
			setDateTime(ddmmyy, time);
		} catch (Exception e) {
		}
	}
	
	public DeadlineTask(Task t, long endDateTime) {
			super(t.getTaskName(), t.getTaskDescription(), t.getPriority());
			this.endDateTime =endDateTime;
	}

	/**************** Accessors ***********************/
	public long getEndDateTime(){
		return this.endDateTime;
	}
	
	// This method converts the unixTimeStamp to readable date time format
	public String getEndDateTimeAsString() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
				.format(new java.util.Date(endDateTime * 1000));
		return dateTime;
	}

	/**************** Accessors for local class only ***********************/
	public String getDate() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
				.format(new java.util.Date(endDateTime * 1000));
		String date = dateTime.substring(0, 6);
		return date;
	}

	public String getTime() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
				.format(new java.util.Date(endDateTime * 1000));
		// Index 9 is the colon
		String timeHour = dateTime.substring(7, 9);
		String timeMinute = dateTime.substring(10);
		return timeHour + timeMinute;
	}

	/**************** Mutators ************************/
	public void setDateTime(String date, String time) throws ParseException {
		// This checks whether date and time entered are of correct length
		assert date.length() == 6;
		assert time.length() == 4;

		// This checks whether date and time entered are valid integers or not
		try {
			int enteredDate = Integer.parseInt(date);
			int enteredTime = Integer.parseInt(time);
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		String dateTimeTemp = date + " " + time;
		// long epoch = new
		// java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime()
		// / 1000;
		long epoch = new java.text.SimpleDateFormat("ddMMyy HH:mm").parse(
				dateTimeTemp).getTime() / 1000;
		assert epoch != 0;
		endDateTime = epoch;
	}

	/**************** Class methods ************************/
	public String toString() {
		return super.toString() + " by " + this.getEndDateTimeAsString();
	}

	public DeadlineTask clone() {
	
			Task t = super.clone();
			DeadlineTask cloned;
			cloned = new DeadlineTask(t, getEndDateTime());
			return cloned;
	}
}

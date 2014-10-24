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

	/**************** Accessors ***********************/
	public long getEndDateTimeLong() {
		return this.endDateTime;
	}

	// This method converts the unixTimeStamp to readable date time format
	public String getEndDateTime() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
				.format(new java.util.Date(endDateTime * 1000));
		return dateTime;
	}

	/**************** Accessors for local class only ***********************/
	protected String getDate() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
				.format(new java.util.Date(endDateTime * 1000));
		String date = dateTime.substring(0, 6);
		return date;
	}

	protected String getTime() {
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
		return super.toString() + " by " + this.getEndDateTime();
	}

	public DeadlineTask clone() {

		/*
		 * The following code is not efficient
		 */
		// String taskName = super.getTaskName();
		// String taskDescription = super.getTaskDescription();
		// int priority = super.getPriority();

		/*
		 * Use super class method to begin with instead
		 */
		Task t = super.clone();

		if (t instanceof DeadlineTask) {
			return (DeadlineTask) t;
		} else {
			throw new ClassCastException("Not an instance of DeadlineTask");
		}
	}
}

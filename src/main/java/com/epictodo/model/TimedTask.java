package com.epictodo.model;

import java.text.ParseException;

public class TimedTask extends Task {
	/************** Data members **********************/
	private long startDateTime;
	private long endDateTime;

	/**************
	 * Constructors
	 * 
	 * @throws ParseException
	 **********************/

	public TimedTask(String taskName, String taskDescription, int priority,
			String ddmmyy, String time, double duration) {
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
			setDuration(duration);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**************** Accessors ***********************/
	// This method converts the stored unixTimeStamp into "ddMMyy HH:mm"
	public String getStartDateTime() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
				.format(new java.util.Date(startDateTime * 1000));
		return dateTime;
	}

	public String getEndDateTime() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
				.format(new java.util.Date(endDateTime * 1000));
		return dateTime;
	}

	/**************** Accessors for local class only ****/
	private String getStartDate() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
				.format(new java.util.Date(startDateTime * 1000));
		String date = dateTime.substring(0, 6);
		return date;
	}

	private String getStartTime() {
		String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
				.format(new java.util.Date(startDateTime * 1000));
		// Index 9 is the colon
		String timeHour = dateTime.substring(7, 9);
		String timeMinute = dateTime.substring(10);
		return timeHour + timeMinute;
	}

	private double getDuration() {
		long hoursInSeconds = endDateTime - startDateTime;
		double hour = (int) hoursInSeconds / 60 / 60;
		return hour;
	}

	/**************** Mutators ************************/
	public void setDuration(double duration) {
		long temp = (long) (startDateTime + (duration * 60 * 60));
		assert temp != 0;
		endDateTime = temp;
	}

	public void setDateTime(String date, String time) throws ParseException {
		assert date.length() == 6;
		assert time.length() == 4;
		// Check whether entered date and time are valid integers before
		// converting it to string format
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
		startDateTime = epoch;
	}

	/**************** Class methods ************************/
	public String toString() {
		return super.toString() + " from " + this.getStartDateTime() + " to "
				+ this.getEndDateTime();
	}

	/*
	 * public TimedTask clone() { String taskName = super.getTaskName(); String
	 * taskDescription = super.getTaskDescription(); int priority =
	 * super.getPriority(); String date = getStartDate(); String time =
	 * getStartTime(); int duration = getDuration(); TimedTask newClone = new
	 * TimedTask(taskName, taskDescription, priority, date, time, duration);
	 * return newClone; }
	 */

	public TimedTask clone() {
		Task t = super.clone();
		TimedTask cloned;
		try {
			if (t instanceof TimedTask) {
				cloned = (TimedTask) t;
				cloned.setDateTime(getStartDate(), getStartTime());
				cloned.setDuration(getDuration());
			} else {
				throw new ClassCastException("Not an instance of TimedTask");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return cloned;
	}
}
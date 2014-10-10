package com.epictodo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

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
		try {
			setDateTime(ddmmyy, time);
			setDuration(duration);
		} catch (Exception e) {
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

	/**************** Mutators ************************/
	public void setDuration(double duration) {
		long temp = (long) (startDateTime + (duration * 60 * 60));
		endDateTime = temp;
	}

	public void setDateTime(String date, String time) throws ParseException {
		String dateTimeTemp = date + " " + time;
		// long epoch = new
		// java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime()
		// / 1000;
		long epoch = new java.text.SimpleDateFormat("ddMMyy HH:mm").parse(
				dateTimeTemp).getTime() / 1000;
		startDateTime = epoch;
	}

	/**************** Other methods ************************/
	public String toString() {
		return super.toString() + " from " + this.getStartDateTime() + " to "
				+ this.getEndDateTime();
	}

	/*
	 * public static Date insertStartDate(Date calendar) { Date result = new
	 * Date(); return result; }
	 * 
	 * public static Date insertEndDate(Date calendar) { Date result = new
	 * Date(); return result; }
	 */
}

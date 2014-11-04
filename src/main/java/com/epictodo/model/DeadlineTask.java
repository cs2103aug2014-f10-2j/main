package com.epictodo.model;

import static org.junit.Assert.*;

import java.util.*;
import java.text.ParseException;

public class DeadlineTask extends Task {
	/************** Data members **********************/
	private long endDateTime;

	/**************
	 * Constructors
	 * 
	 * @throws Exception
	 **********************/
	public DeadlineTask(String taskName, String taskDescription, int priority,
			String ddmmyy, String time) throws Exception {
		super(taskName, taskDescription, priority);
		// This checks whether date and time entered are of correct length
		assert ddmmyy.length() == 6;
		assert time.length() == 5;
		setDateTime(ddmmyy, time);
	}

	public DeadlineTask(Task t, long endDateTime) throws Exception {
		super(t.getTaskName(), t.getTaskDescription(), t.getPriority());
		this.endDateTime = endDateTime;
	}

	/**************** Accessors ***********************/
	public long getEndDateTime() {
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
	public void setDateTime(String date, String time) throws Exception {
		// This checks whether date and time entered are of correct length
		assert date.length() == 6;
		assert time.length() == 5;

		if (checkTimeIsValid(time) && checkDateIsValid(date)) {
			String dateTimeTemp = date + " " + time;
			// long epoch = new
			// java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime()
			// / 1000;
			long epoch = new java.text.SimpleDateFormat("ddMMyy HH:mm").parse(
					dateTimeTemp).getTime() / 1000;
			assert epoch != 0;
			endDateTime = epoch;
		} else {
			throw new Exception();
		}
	}

	/**************** Class methods ************************/
	public String toString() {
		return super.toString() + " by " + this.getEndDateTimeAsString();
	}

	public DeadlineTask copy() {

		Task t = super.copy();
		DeadlineTask cloned = null;
		try {
			cloned = new DeadlineTask(t, getEndDateTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cloned.setUid(t.getUid());
		return cloned;
	}

	/**************** Class methods(For local class only) ************************/

	private boolean checkTimeIsValid(String time) {
		try {
			String regex = "[0-9]+";
			String hour = time.substring(0, 2);
			assertTrue(hour.matches(regex));
			int hourInt = Integer.parseInt(hour);
			assert (hourInt < 24) && (hourInt >= 00);
			String minute = time.substring(3, 5);
			assertTrue(minute.matches(regex));
			int minuteInt = Integer.parseInt(minute);
			assert (minuteInt <= 59) && (minuteInt > 00);
			String colon = time.substring(2, 3);
			assertEquals(colon, ":");
			String newTime = hour + minute;
			assert newTime.length() == 4;
			assertTrue(newTime.matches(regex));
			return true;
		} catch (Error r) {
			return false;
		}
	}

	private boolean checkDateIsValid(String date) throws ParseException {
		try {
			assert date.length() == 6;
			String regex = "[0-9]+";

			// Step 1: Check if date entered consists of digits
			assertTrue(date.matches(regex));
			String month = date.substring(2, 4);
			int monthInt = Integer.parseInt(month);

			// Step 2: Check if month entered is between Jan to Dec
			assert (monthInt < 13) && (monthInt > 00);
			String day = date.substring(0, 2);
			int dayInt = Integer.parseInt(day);
			String year = date.substring(4, 6);
			int yearInt = Integer.parseInt(year);

			// We accept years from 2014 up to 2038 since unixtimestamp max is
			// year 2038
			assert (yearInt > 13) && (yearInt < 39);
			String yyyy = "20" + year;
			int yyyyInt = Integer.parseInt(yyyy);
			if (isLeapYear(yyyyInt) && monthInt == 02) {

				// Step 3: Check if year entered is leap year and month is Feb
				assert (dayInt > 00) && (dayInt < 30);
			} else if (!isLeapYear(yyyyInt) && monthInt == 02) {

				// Step 4: If not leap year, check is Feb has 28 days
				assert (dayInt > 00) && (dayInt < 29);
			} else if (month.equals("01") || month.equals("03")
					|| month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10")
					|| month.equals("12")) {

				// Step 5: Check months with max 31 days
				assert (dayInt > 00) && (dayInt < 32);
			} else {

				// Step 6: The rest of the months should have max 30 days
				assert (dayInt > 00) && (dayInt < 31);
			}

			// Step 7: Check whether date entered is in the future
			Date currDate = new Date();
			Date enteredDate = new Date(yyyyInt - 1900, monthInt - 1, dayInt);
			assert (enteredDate.after(currDate));
			return true;
		} catch (Error e) {
			return false;
		}
	}

	private boolean isLeapYear(int year) {
		return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
	}
}

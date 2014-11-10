package com.epictodo.model.task;

import java.text.ParseException;
import java.util.Date;

import com.epictodo.model.exception.InvalidDateException;
import com.epictodo.model.exception.InvalidTimeException;

//@author A0111683L
public class DeadlineTask extends Task {
    /**
     * *********** Data members *********************
     */
    private long endDateTime;

    /**
     * *********** Constructors
     *
     * @throws InvalidTimeException
     * @throws InvalidDateException *********************
     */
    public DeadlineTask(String taskName, String taskDescription, int priority, String ddmmyy, String time) throws InvalidDateException, InvalidTimeException {
        super(taskName, taskDescription, priority);
        // This checks whether date and time entered are of correct length
        assert ddmmyy.length() == 6;
        assert time.length() == 5;
        try {
            setDateTime(ddmmyy, time);
        } catch (ParseException e) {

        }
    }

    public DeadlineTask(Task t, long endDateTime) throws Exception {
        super(t.getTaskName(), t.getTaskDescription(), t.getPriority());
        this.endDateTime = endDateTime;
    }

    /**
     * ************* Accessors **********************
     */
    public long getEndDateTime() {
        return this.endDateTime;
    }

    // This method converts the unixTimeStamp to readable date time format
    public String getEndDateTimeAsString() {
        String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm").format(new java.util.Date(endDateTime * 1000));
        return dateTime;
    }

    /**
     * ************* Accessors for local class only **********************
     */
    public String getDate() {
        String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm").format(new java.util.Date(endDateTime * 1000));
        String date = dateTime.substring(0, 6);
        return date;
    }

    public String getTime() {
        String dateTime = new java.text.SimpleDateFormat("ddMMyy HH:mm")
                .format(new java.util.Date(endDateTime * 1000));
        // Index 9 is the colon
        String timeHour = dateTime.substring(7, 9);
        String timeMinute = dateTime.substring(10);
        return timeHour +":"+ timeMinute;
    }

    /**
     * ************* Mutators ***********************
     */
    public void setDateTime(String date, String time) throws ParseException, InvalidDateException, InvalidTimeException {
        // This checks whether date and time entered are of correct length
    	if(time== null){
    		// in case time is missing from user's input
    		time = "10:00";
    	}
    	
        if (checkTimeIsValid(time) && checkDateIsValid(date)) {
            String dateTimeTemp = date + " " + time;
            long epoch = new java.text.SimpleDateFormat("ddMMyy HH:mm").parse(dateTimeTemp).getTime() / 1000;
            assert epoch != 0;
            endDateTime = epoch;
        }
    }

    /**
     * ************* Class methods ***********************
     */
    public String getDetail() {
        return super.getDetail() + "End Date and time: " + this.getEndDateTimeAsString() + '\n';
    }

    public Boolean equals(DeadlineTask task2) {
        Boolean compareTask = super.equals(task2);
        Boolean enddatetime = false;

        if (this.getEndDateTime() == task2.getEndDateTime()) {
            enddatetime = true;
        }

        if (compareTask && enddatetime) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return super.toString() + " by " + this.getEndDateTimeAsString();
    }

    public DeadlineTask copy() {

        Task t = super.copy();
        DeadlineTask cloned = null;
        try {
            cloned = new DeadlineTask(t, getEndDateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cloned.setUid(t.getUid());
        return cloned;
    }

    /**
     * ************* Class methods(For local class only) ***********************
     */

    private boolean checkTimeIsValid(String time) throws InvalidTimeException {
        String regex = "[0-9]+";
        String hour = time.substring(0, 2);
        // Check whether hour is made up of digits
        if (!hour.matches(regex)) {
            throw new InvalidTimeException(time);
        }

        int hourInt = Integer.parseInt(hour);
        // Check whether hour is between 00 and 24
        if ((hourInt >= 25) || (hourInt < 00)) {
            throw new InvalidTimeException(time);
        }

        String minute = time.substring(3, 5);
        // Check whether minute is made up of digits
        if (!minute.matches(regex)) {
            throw new InvalidTimeException(time);
        }

        int minuteInt = Integer.parseInt(minute);
        // Check whether minute is between 00 and 59
        if ((minuteInt >= 60) || (minuteInt < 00)) {
            throw new InvalidTimeException(time);
        }

        // Check whether 3rd character is a colon
        String colon = time.substring(2, 3);
        if (!colon.equals(":")) {
            throw new InvalidTimeException(time);
        }

        // Check whether final time has 4 digits
        String newTime = hour + minute;
        if ((newTime.length() != 4) || (!newTime.matches(regex))) {
            throw new InvalidTimeException(time);
        }
        return true;
    }

    private boolean checkDateIsValid(String date) throws InvalidDateException {

        if (date.length() != 6) {
            throw new InvalidDateException(date);
        }

        String regex = "[0-9]+";

        // Step 1: Check if date entered consists of digits
        if (!date.matches(regex)) {
            throw new InvalidDateException(date);
        }

        String month = date.substring(2, 4);
        int monthInt = Integer.parseInt(month);

        // Step 2: Check if month entered is between Jan to Dec
        if ((monthInt > 13) || (monthInt < 00)) {
            throw new InvalidDateException(date);
        }

        String day = date.substring(0, 2);
        int dayInt = Integer.parseInt(day);
        String year = date.substring(4, 6);
        int yearInt = Integer.parseInt(year);

        // We accept years from 2014 up to 2038 since unixtimestamp max is year 2038
        if ((yearInt < 14) || (yearInt > 38)) {
            throw new InvalidDateException(date);
        }

        String yyyy = "20" + year;
        int yyyyInt = Integer.parseInt(yyyy);
        if (isLeapYear(yyyyInt) && monthInt == 02) {
            // Step 3: Check if year entered is leap year and month is Feb
            if ((dayInt <= 00) || (dayInt >= 30)) {
                throw new InvalidDateException(date);
            }
        } else if (!isLeapYear(yyyyInt) && monthInt == 02) {
            // Step 4: If not leap year, check is Feb has 28 days
            if ((dayInt <= 00) || (dayInt >= 29)) {
                throw new InvalidDateException(date);
            }
        } else if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) {
            // Step 5: Check months with max 31 days
            if ((dayInt <= 00) || (dayInt >= 32)) {
                throw new InvalidDateException(date);
            }
        } else {
            // Step 6: The rest of the months should have max 30 days
            if ((dayInt <= 00) || (dayInt >= 31)) {
                throw new InvalidDateException(date);
            }
        }

        //Step 7: Check whether date entered is in the future
        Date d = new Date();
        Date currDate = new Date(d.getYear(),d.getMonth(),d.getDate());
        Date enteredDate = new Date(yyyyInt - 1900, monthInt - 1, dayInt);
        if (!enteredDate.after(currDate)&&enteredDate.before(currDate)) {
            throw new InvalidDateException(date);
        }
        return true;
    }

    private boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
    }
}

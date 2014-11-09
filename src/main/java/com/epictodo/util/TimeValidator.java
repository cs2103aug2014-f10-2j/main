//@author A0111875E
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Kenneth Ham
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.epictodo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeValidator {
    private Pattern _pattern;
    private Matcher _matcher;
    private static TimeValidator instance = null;
    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]"; //"([01]?[0-9]|2[0-3]):[0-5][0-9]\\S+";
    private static final String TIME24HOURS_PATTERN2 = "([01]?[0-9]|2[0-3])[0-5][0-9]";

    public TimeValidator() {
        _pattern = Pattern.compile(TIME24HOURS_PATTERN);
    }

    /**
     * This method ensures that there will only be one running instance
     *
     * @return
     */
    public static TimeValidator getInstance() {
        if (instance == null) {
            instance = new TimeValidator();
        }

        return instance;
    }

    /**
     * This method validates the time from a given string
     * If time matches the regex expression, it will return a true.
     * <p/>
     * Usage:
     * validate("10:00"); > true
     * validate("Friday"); > false
     * validate("24:10"); > false
     * validate("00:10"); > true
     *
     * @param _time
     * @return boolean
     */
    public boolean validate(final String _time) {
        assert _time.length() == 5;
        _matcher = _pattern.matcher(_time);

//        String time = _time;
//        time = time.replaceAll(TIME24HOURS_PATTERN, "");

//        String corrected_time = time;
//
//        try {
//            corrected_time = new StringBuffer(corrected_time).insert(corrected_time.length() - 2, ":").toString();
//        } catch (StringIndexOutOfBoundsException ex) {
//            throw ex;
//        }

        /**
         * Algorithm to provide a natural response when a negative input was entered.
         * Time validation will ask users if that was the correct input, otherwise users can correct the error.
         */
//        if (_matcher.matches() == true) {
//            return _matcher.matches();
//        } else if (_matcher.matches() == true && !_time.matches(TIME24HOURS_PATTERN) && _time.matches(TIME24HOURS_PATTERN2)) {
//            System.out.println("Did you meant " + time + "hrs?");
//        } else if (_time.matches(TIME24HOURS_PATTERN2)) {
//            System.out.println("Did you meant " + corrected_time + "hrs?");
//        }

        return _matcher.matches();
    }

    /**
     * This method returns a double value of the duration between two time
     * <p/>
     * Usage:
     * getTimeDuration("08:00", "10:00"); > 2.0
     * getTimeDuration("10:00", "14:00"); > 4.0
     *
     * @param start_time
     * @param end_time
     * @return diff_hours
     * @throws ParseException
     */
    public double getTimeDuration(String start_time, String end_time) throws ParseException {
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
        Date date_start;
        Date date_end;

        date_start = time_format.parse(start_time);
        date_end = time_format.parse(end_time);

        // get time in milliseconds
        long time_diff = date_end.getTime() - date_start.getTime();

        long diff_seconds = time_diff / 1000 % 60;
        long diff_minutes = time_diff / (60 * 1000) % 60;
        long diff_hours = time_diff / (60 * 60 * 1000) % 24;
        long diff_days = time_diff / (24 * 60 * 60 * 1000);

        return diff_hours;
    }
}

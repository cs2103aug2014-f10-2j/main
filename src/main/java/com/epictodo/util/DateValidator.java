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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator {
    private Calendar _calendar = Calendar.getInstance();
    private static DateValidator instance = null;
    private static final String DATE_PATTERN = "(\\d+)";
    private static final String DATE_PATTERN_2 = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
    private static final String TIMEX_PATTERN = "((19|20)(\\d{2}))-([1-9]|0[1-9]|1[0-2])-(0[1-9]|[1-9]|[12][0-9]|3[01])-(\\S+)";
    private static final String TIMEX_PATTERN_2 = "((19|20)(\\d{2}))-([1-9]|0[1-9]|1[0-2])-(0[1-9]|[1-9]|[12][0-9]|3[01])(\\S+)";
    private static final String TIME_PATTERN = "((19|20)(\\d{2}))-([1-9]|0[1-9]|1[0-2])-(0[1-9]|[1-9]|[12][0-9]|3[01])-(\\S+T)(([01]?[0-9]|2[0-3]):[0-5][0-9])";
    private static final String TIME_PATTERN_2 = "((19|20)(\\d{2}))-([1-9]|0[1-9]|1[0-2])-(0[1-9]|[1-9]|[12][0-9]|3[01])(T)(([01]?[0-9]|2[0-3]):[0-5][0-9])";

    /**
     * This method ensures that there will only be one running instance
     *
     * @return
     */
    public static DateValidator getInstance() {
        if (instance == null) {
            instance = new DateValidator();
        }

        return instance;
    }

    /**
     * This method is the same as getDateInFormat except it's parsed format is different
     * FUTURE Date is parsed from exactDate() which will return exactly the format of yyyy-MM-dd
     * <p/>
     * Usage:
     * <p/>
     * getDateToCompare("2014-11-09"); > 2014-11-09
     * getDateToCompare("2014-11-23T14:30"); > 2014-11-23
     * getDateToCompare("2014-11-11-WXX-2"); > 2011-11-11
     * getDateToCompare("2014-11-18-WXX-2T10:00"); > 2014-11-18
     *
     * @param _date
     * @return _result
     * @throws ParseException
     */
    public String getDateToCompare(String _date) throws ParseException {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        int num_days;
        String _result;
        String[] exact_date = extractDate(_date);

        Date today_date = date_format.parse(getTodayDate());
        Date next_date = date_format.parse(exact_date[2] + "-" + exact_date[1] + "-" + exact_date[0]);
        num_days = calculateDays(today_date, next_date);

        if (next_date.after(today_date) || next_date.equals(today_date)) {
            if (num_days >= 0 && num_days <= 1) {
                _result = exact_date[2] + "-" + exact_date[1] + "-" + exact_date[0];

                return _result;
            }
        }

        _result = exact_date[2] + "-" + exact_date[1] + "-" + exact_date[0];

        return _result;
    }

    /**
     * This method validates the date and parse to the following format (yyyy-MM-dd)
     * This will return with the format of ddMMyy
     * <p/>
     * Issues:
     * 3 cases:
     * 1. yyyy-MM-dd
     * 2. yyyy-MM-dd-WXX-dT-hh:mm
     * 3. yyyy-MM-ddT-hh:mm
     * <p/>
     * Usage:
     * <p/>
     * getDateInFormat("2014-11-09"); > 091114
     * getDateInFormat("2014-11-23T14:30"); > 231114
     * getDateInFormat("2014-11-11-WXX-2"); > 111114
     * getDateInFormat("2014-11-18-WXX-2T10:00"); > 181114
     *
     * @param _date
     * @return _result
     */
    public String getDateInFormat(String _date) throws ParseException {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        int num_days;
        String _result;
        String[] exact_date = extractDate(_date);

        Pattern date_pattern = Pattern.compile(TIMEX_PATTERN_2);
        Matcher date_matcher = date_pattern.matcher(_date);

        Date today_date = date_format.parse(getTodayDate());
        Date next_date = date_format.parse(exact_date[2] + "-" + exact_date[1] + "-" + exact_date[0]);
        num_days = calculateDays(today_date, next_date);

        if (next_date.after(today_date) || next_date.equals(today_date)) {
            if (num_days >= 0 && num_days <= 1) {
                while (date_matcher.find()) {
                    _result = date_matcher.group(5) + date_matcher.group(4) + date_matcher.group(3);

                    return _result;
                }
            }
        }

        if (!date_matcher.matches()) {
            return null;
        } else {
            _result = date_matcher.group(5) + date_matcher.group(4) + date_matcher.group(3);
        }

        return _result;
    }

    /**
     * This method validates the time and parse to the following format (hh:mm)
     * This will return the time from the given string in the format of hh:mm
     * <p/>
     * Usage:
     * <p/>
     * getTimeInFormat("10:30"); > 10:30
     * getTimeInFormat("2014-11-23T14:30"); > 14:30
     * getTimeInFormat("2014-11-18-WXX-2T10:00"); > 10:00
     *
     * @param _time
     * @return _result
     * @throws ParseException
     */
    public String getTimeInFormat(String _time) throws ParseException {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        int num_days;
        String _result;

        Pattern date_pattern = Pattern.compile(TIME_PATTERN_2);
        Matcher date_matcher = date_pattern.matcher(_time);

        Date today_date = date_format.parse(getTodayDate());
        Date next_date = date_format.parse(_time);
        num_days = calculateDays(today_date, next_date);

        if (next_date.after(today_date) || next_date.equals(today_date)) {
            if (num_days >= 0 && num_days <= 1) {
                while (date_matcher.find()) {
                    _result = date_matcher.group(7);

                    return _result;
                }
            }
        }

        if (!date_matcher.matches()) {
            return null;
        } else {
            _result = date_matcher.group(7);
        }

        return _result;
    }

    /**
     * This method validates the date and parse to the following format (ddMMyy)
     * This works for general cases where the day difference is more than 2 days.
     * This method is similar to getDateInFormat
     * <p/>
     * Usage:
     * validateDate("2014-11-18-WXX-2T10:00"); > 181114
     *
     * @param _date
     * @return
     */
    public String validateDate(String _date) {
        String _result;
        Pattern date_pattern = Pattern.compile(TIMEX_PATTERN);
        Matcher date_matcher = date_pattern.matcher(_date);

        if (!date_matcher.matches()) {
            return null;
        }

        _result = date_matcher.group(5) + date_matcher.group(4) + date_matcher.group(3);

        return _result;
    }

    /**
     * This method validates the token from NLP SUTIME Annotation based on our predefined regex expression
     * This method is parsed to get the time only
     * <p/>
     * Usage:
     * <p/>
     * validateTime("2014-11-18-WXX-2T10:00"); > 10:00
     * validateTime("10:30"); > 10:30
     * validateTime("2014-11-23T14:30"); > 14:30
     * validateTime("2014-11-18-WXX-2T10:00"); > 10:00
     *
     * @param _time
     * @return
     */
    public String validateTime(String _time) {
        String _result;
        Pattern date_pattern = Pattern.compile(TIME_PATTERN);
        Matcher date_matcher = date_pattern.matcher(_time);

        if (!date_matcher.matches()) {
            return null;
        }

        _result = date_matcher.group(7);

        return _result;
    }

    /**
     * Validate date format with regular expression
     *
     * @param date date address for validation
     * @return true valid date format, false invalid date format
     */
    public boolean validateDateExpression(final String date) {
        Pattern date_pattern = Pattern.compile(DATE_PATTERN_2);
        Matcher date_matcher = date_pattern.matcher(date);

        if (date_matcher.matches()) {
            date_matcher.reset();

            if (date_matcher.find()) {
                String _day = date_matcher.group(1);
                String _month = date_matcher.group(2);
                int _year = Integer.parseInt(date_matcher.group(3));

                if (_day.equals("31") &&
                        (_month.equals("4") || _month.equals("6") || _month.equals("9") ||
                                _month.equals("11") || _month.equals("04") || _month.equals("06") ||
                                _month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (_month.equals("2") || _month.equals("02")) {
                    // leap year
                    if (_year % 4 == 0) {
                        if (_day.equals("30") || _day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (_day.equals("29") || _day.equals("30") || _day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * This method will check if date matches dd/MM/yyyy.
     * The algorithm converts that into readable date of format yyyy-MM-dd
     * <p/>
     * Usage:
     * <p/>
     * checkQuickDate("01/11/2014"); > 2014-11-01
     * checkQuickDate("32/11/2014"); > 2014-12-2 (Exception case, automatically calculate date forward)
     *
     * @param _date
     * @return _result
     * @throws ParseException
     */
    public String checkQuickDate(String _date) throws ParseException {
        SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat parse_date_format = new SimpleDateFormat("yyyy-MM-dd");
        String _result;
        int num_days;
        _calendar.setTime(date_format.parse(_date));

        int _year = _calendar.get(Calendar.YEAR);
        int _month = _calendar.get(Calendar.MONTH) + 1;
        int _day = _calendar.get(Calendar.DAY_OF_MONTH);

        _result = String.valueOf(_year) + "-" + String.valueOf(_month) + "-" + String.valueOf(_day);

        String[] exact_date = extractDate(_result);

        Pattern date_pattern = Pattern.compile(TIMEX_PATTERN_2);
        Matcher date_matcher = date_pattern.matcher(_result);

        Date today_date = parse_date_format.parse(getTodayDate());
        Date next_date = parse_date_format.parse(exact_date[2] + "-" + exact_date[1] + "-" + exact_date[0]);
        num_days = calculateDays(today_date, next_date);

        if (next_date.after(today_date) || next_date.equals(today_date)) {
            if (num_days >= 0 && num_days <= 1) {
                while (date_matcher.find()) {
                    _result = date_matcher.group(5) + date_matcher.group(6) + date_matcher.group(4) + date_matcher.group(3);

                    return _result;
                }
            }
        }

        if (!date_matcher.matches()) {
            return null;
        } else {
            _result = date_matcher.group(5) + date_matcher.group(6) + date_matcher.group(4) + date_matcher.group(3);
        }

        return _result;
    }

    /**
     * This method extracts the date based on the format of yyyy-MM-dd
     * This will return an Array of String results containing DAY, MONTH, YEAR
     * <p/>
     * Usage:
     * <p/>
     * extractDate("2014-11-09"); > [09, 11, 2014]
     * extractDate("2014-11-23T14:30"); > [23, 11, 2014]
     * extractDate("2014-11-11-WXX-2"); > [11, 11, 2014]
     * extractDate("2014-11-18-WXX-2T10:00"); > [18, 11, 2014]
     *
     * @param _date
     * @return _list
     * @throws ParseException
     */
    public String[] extractDate(String _date) throws ParseException {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String[] _list = new String[3];
        _calendar.setTime(date_format.parse(_date));

        int _year = _calendar.get(Calendar.YEAR);
        int _month = _calendar.get(Calendar.MONTH) + 1;
        int _day = _calendar.get(Calendar.DAY_OF_MONTH);

        _list[0] = String.valueOf(_day);
        _list[1] = String.valueOf(_month);
        _list[2] = String.valueOf(_year);

        return _list;
    }

    /**
     * This method determines the priority of a task based on natural processing
     * This artificial natural processing calculates the days difference
     * <p/>
     * The priority is determined by the number of weeks apart from today's date
     * The current rule of this determiner decreases the priority each week from today's date
     * The closer the date is to today's date holds the highest priority and decreases each week
     * <p/>
     * Assumptions:
     * 1. User can override the priority based on their preferred choice
     * 2. Priority is set based on this determiner algorithm, may not accurately reflect user's intentions
     * 3. Priority range from 1 - 9. 1 represents the least of priority, 9 represents the most urgent of priority
     * <p/>
     * The higher the priority the more urgent the task is
     * The default priority is set to '2'
     * <p/>
     * Usage: (example based on TODAY's Date = 2014-11-01)
     * <p/>
     * determinePriority("2014-11-08") > 9
     * determinePriority("2014-11-20") > 8
     * determinePriority("2014-11-23") > 7
     * determinePriority("2014-12-07") > 5
     *
     * @param _date
     * @return
     * @throws ParseException
     */
    public String determinePriority(String _date) throws ParseException {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        int num_days;
        String[] exact_date = extractDate(_date);

        Date today_date = date_format.parse(getTodayDate());
        Date next_date = date_format.parse(exact_date[2] + "-" + exact_date[1] + "-" + exact_date[0]);
        num_days = calculateDays(today_date, next_date);

        if (next_date.after(today_date)) {
            if (num_days >= 0 && num_days <= 7) {
                return "9";
            } else if (num_days > 7 && num_days <= 14) {
                return "8";
            } else if (num_days > 14 && num_days <= 21) {
                return "7";
            } else if (num_days > 21 && num_days <= 28) {
                return "6";
            } else if (num_days > 28 && num_days <= 35) {
                return "5";
            } else if (num_days > 35 && num_days <= 42) {
                return "5";
            } else if (num_days > 42 && num_days <= 49) {
                return "4";
            } else if (num_days > 49 && num_days <= 56) {
                return "3";
            } else if (num_days > 56) {
                return "2";
            }
        } else if (next_date.before(today_date)) {
            return "Date is invalid!";
        }

        return "2";
    }

    /**
     * This method compares 2 dates, TODAY and FUTURE
     * This method enables flexiAdd() to determine the number of days between dates in order to process the result
     * <p/>
     * Usage: (example based on TODAY's Date = 2014-11-01)
     * <p/>
     * compareDate("2014-11-03") > 2
     * compareDate("2014-11-20") > 19
     *
     * @param _date
     * @return num_days
     * @throws ParseException
     */
    public int compareDate(String _date) throws ParseException {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        int num_days;

        Date today_date = date_format.parse(getTodayDate());
        Date next_date = date_format.parse(_date);
        num_days = calculateDays(today_date, next_date);

        return num_days;
    }

    /**
     * This method checks and validates if the integer passed is in the format of ddMMyy
     *
     * @param _date
     * @return boolean
     * @throws ParseException
     */
    public boolean checkDateFormat(String _date) throws ParseException {
        Pattern date_pattern = Pattern.compile(DATE_PATTERN);
        Matcher date_matcher = date_pattern.matcher(_date);

        if (!date_matcher.matches()) {
            return false;
        }

        return true;
    }

    /**
     * This method returns today's date
     *
     * @return
     */
    public String getTodayDate() {
        Date _date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        return date_format.format(_date);
    }

    /**
     * This method calculates the number of days difference from today's date
     * This method is the core algorithm of the day calculation which is used in determinePriority() method
     * <p/>
     * Usage:
     * <p/>
     * calculateDays(2014-11-01, 2014-11-05); > 4
     * calculateDays(2014-11-01, 2014-11-10); > 9
     *
     * @param today_date
     * @param next_date
     * @return
     */
    private int calculateDays(Date today_date, Date next_date) {
        return (int) ((next_date.getTime() - today_date.getTime()) / (24 * 60 * 60 * 1000));
    }
}

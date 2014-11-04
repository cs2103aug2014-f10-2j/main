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

public class DateValidator {
    private static final String FIX_PATTERN = "((19|20)(\\d{2}))-([1-9]|0[1-9]|1[0-2])-(0[1-9]|[1-9]|[12][0-9]|3[01])";
    private static final String TIMEX_PATTERN = "((19|20)(\\d{2}))-([1-9]|0[1-9]|1[0-2])-(0[1-9]|[1-9]|[12][0-9]|3[01])-(\\S+)";
    private static final String TIME_PATTERN = "((19|20)(\\d{2}))-([1-9]|0[1-9]|1[0-2])-(0[1-9]|[1-9]|[12][0-9]|3[01])-(\\S+T)(([01]?[0-9]|2[0-3]):[0-5][0-9])";
    private static DateValidator instance = null;

    public static DateValidator getInstance() {
        if (instance == null) {
            instance = new DateValidator();
        }

        return instance;
    }

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

    public int determinePriority(String _date) throws ParseException {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        int num_days;

        Date today_date = date_format.parse(getTodayDate());
        Date next_date = date_format.parse(_date);
        num_days = calculateDays(today_date, next_date);

        if (next_date.after(today_date)) {
            if (num_days >= 1 && num_days <= 7) {
                return 1;
            } else if (num_days > 7 && num_days <= 14) {
                return 2;
            } else if (num_days > 14 && num_days <= 21) {
                return 3;
            } else if (num_days > 21 && num_days <= 28) {
                return 4;
            } else if (num_days > 28 && num_days <= 35) {
                return 5;
            } else if (num_days > 35 && num_days <= 42) {
                return 6;
            } else if (num_days > 42 && num_days <= 49) {
                return 7;
            } else if (num_days > 49 && num_days <= 56) {
                return 8;
            } else if (num_days > 56) {
                return 9;
            }
        } else if (next_date.before(today_date)) {
            System.out.println("Date is invalid!");
        }

        return 2;
    }

    public String getTodayDate() {
        Date _date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        return date_format.format(_date);
    }

    public int calculateDays(Date today_date, Date next_date) {
        return (int) ((next_date.getTime() - today_date.getTime()) / (1000 * 60 * 60 * 24));
    }
}

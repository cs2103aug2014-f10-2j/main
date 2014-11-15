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

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class DateValidatorTest {
    private static final double DELTA = 1e-15;
    private final DateValidator date_validator = new DateValidator();

    /**
     * convertDateFormat Valid Test Case
     *
     * @throws ParseException
     */

    @Test
    public void DateCompareTest() throws ParseException {
        String result = date_validator.convertDateFormat("2014-11-26");
        Assert.assertEquals("2014-11-26", result);
    }

    @Test
    public void DateCompareTest2() throws ParseException {
        String result = date_validator.convertDateFormat("2014-11-26T23:59");
        Assert.assertEquals("2014-11-26", result);
    }

    @Test
    public void DateCompareTest3() throws ParseException {
        String result = date_validator.convertDateFormat("2014-11-26-WXX-2");
        Assert.assertEquals("2014-11-26", result);
    }

    @Test
    public void DateCompareTest4() throws ParseException {
        String result = date_validator.convertDateFormat("2014-11-26-WXX-2T23:59");
        Assert.assertEquals("2014-11-26", result);
    }

    /**
     * convertDateFormat Invalid Test Case
     *
     * @throws ParseException
     */

    @Test(expected = AssertionError.class)
    public void InvalidDateCompareTest() throws ParseException {
        String result = date_validator.convertDateFormat("2014-11-32");
        Assert.assertEquals("2014-11-32", result);
    }

    @Test
    public void InvalidDateCompareTest2() throws ParseException {
        String result = date_validator.convertDateFormat("2014-10-26");
        Assert.assertEquals("2014-10-26", result);
    }

    @Test(expected = AssertionError.class)
    public void InvalidDateCompareTest3() throws ParseException {
        String result = date_validator.convertDateFormat("2014-13-26T14:30");
        Assert.assertEquals("2014-13-26", result);
    }

    /**
     * genericDateFormat Valid Test Cases
     * <p/>
     * Result > ddMMyy
     */

    @Test
    public void GenericDateTest() throws ParseException {
        String results = date_validator.genericDateFormat("2014-11-26");
        Assert.assertEquals("261114", results);
    }

    @Test
    public void GenericDateTest2() throws ParseException {
        String results = date_validator.genericDateFormat("2014-11-13");
        Assert.assertEquals("131114", results);
    }

    /**
     * genericDateFormat Invalid Test Cases
     */

    @Test(expected = AssertionError.class)
    public void InvalidGenericDateTest() throws ParseException {
        String results = date_validator.genericDateFormat("2014-11-26T23:59");
        Assert.assertEquals("261114", results);
    }

    @Test(expected = AssertionError.class)
    public void InvalidGenericDateTest2() throws ParseException {
        String results = date_validator.genericDateFormat("2014-11-26-WXX-2");
        Assert.assertEquals("261114", results);
    }

    @Test(expected = AssertionError.class)
    public void InvalidGenericDateTest3() throws ParseException {
        String results = date_validator.genericDateFormat("2014-11-26-WXX-2T23:59");
        Assert.assertEquals("261114", results);
    }

    /**
     * ValidDateTest Valid Test Cases
     */

    @Test
    public void ValidDateTest() throws ParseException {
        String results = date_validator.validateDate("2014-12-26-WXX-2");
        Assert.assertEquals("261214", results);
    }

    @Test
    public void ValidDateTest2() throws ParseException {
        String results = date_validator.validateDate("2014-12-13-WXX-2T10:00");
        Assert.assertEquals("131214", results);
    }

    /**
     * ValidDateTest Invalid Test Cases
     */

    @Test
    public void InvalidDateTest() throws ParseException {
        String results = date_validator.validateDate("2014-12-13");
        Assert.assertEquals(null, results);
    }

    @Test
    public void InvalidDateTest2() throws ParseException {
        String results = date_validator.validateDate("2014-12-13T23:59");
        Assert.assertEquals(null, results);
    }

    /**
     * GetTimeInFormat Test Cases
     */

    @Test
    public void TimeInFormatTest() throws ParseException {
        String results = date_validator.getTimeInFormat("2014-11-23T14:30");
        Assert.assertEquals("14:30", results);
    }

    /**
     * GetTimeInFormat Invalid Test Case
     */

    @Test(expected = AssertionError.class)
    public void InvalidTimeInFormatTest2() throws ParseException {
        String results = date_validator.getTimeInFormat("2014-12-26-WXX-2T23:59");
        Assert.assertEquals("23:59", results);
    }

    /**
     * ValidateTime Test Cases
     */

    @Test
    public void validateTimeTest() {
        String results = date_validator.validateTime("2014-12-26-WXX-2T10:00");
        Assert.assertEquals("10:00", results);
    }

    /**
     * Invalid ValidateTime Test Case
     */

    @Test
    public void InvalidTimeTest() {
        String results = date_validator.validateTime("2014-12-26T23:59");
        Assert.assertEquals(null, results);
    }

    @Test
    public void InvalidTimeTest2() {
        String results = date_validator.validateTime("23:59");
        Assert.assertEquals(null, results);
    }

    /**
     * Validate Date Expression Test Cases
     */

    @Test
    public void ValidateDateExpTest() {
        boolean valid = date_validator.validateDateExpression("26/10/2014");
        Assert.assertEquals(true, valid);
    }

    @Test
    public void ValidateDateExpTest2() {
        boolean valid = date_validator.validateDateExpression("32/10/2014");
        Assert.assertEquals(false, valid);
    }

    @Test
    public void ValidateDateExpTest3() {
        boolean valid = date_validator.validateDateExpression("26/13/2014");
        Assert.assertEquals(false, valid);
    }

    @Test
    public void ValidateDateExpTest4() {
        boolean valid = date_validator.validateDateExpression("2014/12/26");
        Assert.assertEquals(false, valid);
    }

    @Test
    public void ValidateDateExpTest5() {
        boolean valid = date_validator.validateDateExpression("2014-12-26");
        Assert.assertEquals(false, valid);
    }

    /**
     * FixShortDate Test Cases
     */
    @Test
    public void FixDateTest() throws ParseException {
        String results = date_validator.fixShortDate("26/10/2014");
        Assert.assertEquals("2014-10-26", results);
    }

    @Test
    public void FixDateTest2() throws ParseException {
        String results = date_validator.fixShortDate("26/13/2014");
        Assert.assertEquals("2015-01-26", results);
    }

    @Test
    public void FixDateTest3() throws ParseException {
        String results = date_validator.fixShortDate("34/12/2014");
        Assert.assertEquals("2015-01-03", results);
    }

    /**
     * ExtractDate Test Cases
     */

    @Test
    public void ExtractDateTest() throws ParseException {
        String[] expected = date_validator.extractDate("2014-10-26");
        String[] results = date_validator.extractDate("2014-10-26");
        Assert.assertEquals(expected[0], results[0]);
    }

    @Test
    public void ExtractDateTest2() throws ParseException {
        String[] expected = date_validator.extractDate("2014-10-26");
        String[] results = date_validator.extractDate("2014-10-26");
        Assert.assertEquals(expected[1], results[1]);
    }

    @Test
    public void ExtractDateTest3() throws ParseException {
        String[] expected = date_validator.extractDate("2014-10-26");
        String[] results = date_validator.extractDate("2014-10-26");
        Assert.assertEquals(expected[2], results[2]);
    }

    @Test
    public void ExtractDateTest4() throws ParseException {
        String[] expected = date_validator.extractDate("2014-11-26T23:59");
        String[] results = date_validator.extractDate("2014-11-26T23:59");
        Assert.assertEquals(expected[0], results[0]);
    }

    @Test
    public void ExtractDateTest5() throws ParseException {
        String[] expected = date_validator.extractDate("2014-11-26-WXX-2");
        String[] results = date_validator.extractDate("2014-11-26-WXX-2");
        Assert.assertEquals(expected[0], results[0]);
    }

    @Test
    public void ExtractDateTest6() throws ParseException {
        String[] expected = date_validator.extractDate("2014-11-26-WXX-2T23:59");
        String[] results = date_validator.extractDate("2014-11-26-WXX-2T23:59");
        Assert.assertEquals(expected[0], results[0]);
    }

    /**
     * DeterminePriority Test Cases
     * <p/>
     * Test Assumptions:
     * 1. Test must be adjusted accordingly to Today's Date
     * 2. Test Date cannot be from the past
     */

    @Test
    public void DeterminPriorityTest() throws ParseException {
        String priority = date_validator.determinePriority("2014-11-26");
        Assert.assertEquals("8", priority);
    }

    @Test
    public void DeterminPriorityTest2() throws ParseException {
        String priority = date_validator.determinePriority("2014-12-26");
        Assert.assertEquals("5", priority);
    }

    @Test
    public void DeterminPriorityTest3() throws ParseException {
        String priority = date_validator.determinePriority("2014-10-26");
        Assert.assertEquals("Date is invalid!", priority);
    }
}

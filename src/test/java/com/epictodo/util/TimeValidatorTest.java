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

public class TimeValidatorTest {
    private final TimeValidator time_validator = new TimeValidator();
    private static final double DELTA = 1e-15;

    /**
     * Valid Time Test Cases
     */

    @Test
    public void ValidTimeTest() {
        boolean valid = time_validator.validate("01:30");
        Assert.assertEquals(true, valid);
    }

    @Test
    public void ValidTimeTest2() {
        boolean valid = time_validator.validate("23:59");
        Assert.assertEquals(true, valid);
    }

    @Test
    public void ValidTimeTest3() {
        boolean valid = time_validator.validate("00:00");
        Assert.assertEquals(true, valid);
    }

    @Test
    public void ValidTimeTest4() {
        boolean valid = time_validator.validate("00:59");
        Assert.assertEquals(true, valid);
    }

    @Test
    public void ValidTimeTest5() {
        boolean valid = time_validator.validate("13:00");
        Assert.assertEquals(true, valid);
    }

    @Test(expected = AssertionError.class)
    public void ValidTimeTest6() {
        boolean valid = time_validator.validate("0:30");
        Assert.assertEquals(true, valid);
    }

    @Test(expected = AssertionError.class)
    public void ValidTimeTest7() {
        boolean valid = time_validator.validate("2:0");
        Assert.assertEquals(true, valid);
    }

    /**
     * Invalid Time Test Cases
     */

    @Test
    public void InvalidTimeTest() {
        boolean valid = time_validator.validate("24:00");
        Assert.assertEquals(false, valid);
    }

    @Test
    public void InvalidTimeTest2() {
        boolean valid = time_validator.validate("00:60");
        Assert.assertEquals(false, valid);
    }

    @Test
    public void InvalidTimeTest3() {
        boolean valid = time_validator.validate("12:60");
        Assert.assertEquals(false, valid);
    }

    @Test(expected = AssertionError.class)
    public void InvalidTimeTest4() {
        boolean valid = time_validator.validate("101:00");
        Assert.assertEquals(false, valid);
    }

    @Test(expected = AssertionError.class)
    public void InvalidTimeTest5() {
        boolean valid = time_validator.validate("0:0");
        Assert.assertEquals(false, valid);
    }

    @Test(expected = AssertionError.class)
    public void InvalidTimeTest6() {
        boolean valid = time_validator.validate("14:5");
        Assert.assertEquals(false, valid);
    }

    @Test
    public void InvalidTimeTest7() {
        boolean valid = time_validator.validate("21:90");
        Assert.assertEquals(false, valid);
    }

    @Test
    public void InvalidTimeTest8() {
        boolean valid = time_validator.validate("99:99");
        Assert.assertEquals(false, valid);
    }

    @Test(expected = AssertionError.class)
    public void InvalidTimeTest9() {
        boolean valid = time_validator.validate("2359");
        Assert.assertEquals(false, valid);
    }

    /**
     * Valid Time Duration Test Cases
     */

    @Test
    public void ValidTimeDurationTest() throws ParseException {
        double duration = time_validator.getTimeDuration("08:00", "10:00");
        Assert.assertEquals(2.0, duration, DELTA);
    }

    @Test
    public void ValidTimeDurationTest2() throws ParseException {
        double duration = time_validator.getTimeDuration("08:00", "21:00");
        Assert.assertEquals(13.0, duration, DELTA);
    }

    @Test
    public void ValidTimeDurationTest3() throws ParseException {
        double duration = time_validator.getTimeDuration("8:00", "10:00");
        Assert.assertEquals(2.0, duration, DELTA);
    }

    @Test
    public void ValidTimeDurationTest4() throws ParseException {
        double duration = time_validator.getTimeDuration("00:00", "10:00");
        Assert.assertEquals(10.0, duration, DELTA);
    }

    /**
     * Invalid Time Duration Test Cases
     */

    @Test(expected = AssertionError.class)
    public void InvalidTimeDurationTest() throws ParseException {
        double duration = time_validator.getTimeDuration("24:00", "10:00");
        Assert.assertEquals(10.0, duration, DELTA);
    }

    @Test
    public void InvalidTimeDurationTest2() throws ParseException {
        double duration = time_validator.getTimeDuration("00:90", "10:00");
        Assert.assertEquals(8.0, duration, DELTA);
    }

    @Test(expected = AssertionError.class)
    public void InvalidTimeDurationTest3() throws ParseException {
        double duration = time_validator.getTimeDuration("99:99", "10:00");
        Assert.assertEquals(0.0, duration, DELTA);
    }
}

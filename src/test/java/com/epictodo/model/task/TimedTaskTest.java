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

package com.epictodo.model.task;

import com.epictodo.model.task.Task;
import com.epictodo.model.task.TimedTask;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

//@author A0111683L
public class TimedTaskTest {
    private Task _task;
    private TimedTask timed_task;

    @Before
    public void initialise() throws Exception {
        _task = new Task("Meeting at CLB", "Group Project", 2);
        timed_task = new TimedTask("Do CS2103 online quiz", "Quiz on testing", 5, "241014", "20:00", 2.0);
    }

    @Test
    public void checkSetDuration() {
        timed_task.setDuration(4.0);
        assertEquals(timed_task.getEndDateTimeAsString(), "251014 00:00");
    }

    @Test
    public void checkTimedTaskSetDateTime() throws Exception {
        timed_task.setDateTime("261014", "19:00");
        assertEquals(timed_task.getStartDateTimeAsString(), "261014 19:00");
    }

    @Test
    public void checkTimedTaskToString() {
        assertEquals(timed_task.toString(), "Do CS2103 online quiz from 241014 20:00 to 241014 22:00");
    }

 /*
    public void checkTimedTaskCloneMethod() {
        TimedTask temp_ttask;
        temp_ttask = timed_task.copy();
        assertEquals(temp_ttask.getStartDateTimeAsString(), "241014 20:00");
        assertEquals(temp_ttask.getEndDateTimeAsString(), "241014 22:00");
    }
    */
}

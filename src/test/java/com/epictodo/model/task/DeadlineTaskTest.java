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

import com.epictodo.model.exception.InvalidDateException;
import com.epictodo.model.exception.InvalidTimeException;
import com.epictodo.model.task.DeadlineTask;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

//@author A0111683L
public class DeadlineTaskTest {
    private DeadlineTask _deadline_task;
    private DeadlineTask _deadline_task_copy;

    @Before
    public void initialise() throws InvalidDateException, InvalidTimeException, ParseException {
        _deadline_task = new DeadlineTask("Do CS2103 homework", "Homework on testing", 5, "121214", "10:00");
        _deadline_task_copy = new DeadlineTask("Do CS2103 homework", "Homework on testing", 5, "121214", "10:00");
    }

    @Test
    public void checkDeadlineTaskSetDateTime() throws InvalidDateException, InvalidTimeException, ParseException{
        _deadline_task.setDateTime("131214", "23:59");
        assertEquals(_deadline_task.getEndDateTimeAsString(), "131214 23:59");
    }
    
    @Test
    public void checkGetDetail() {
    	assertEquals("Name: Do CS2103 homework\nDescription: Homework on testing\nEnd Date and time: 121214 10:00\n", _deadline_task.getDetail());
    }
    
    @Test
    public void checkEquals() throws InvalidDateException, InvalidTimeException {
    	assertEquals(_deadline_task_copy.equals(_deadline_task), true);
    }
    
    @Test
    public void checkToString() {
    	assertEquals("Do CS2103 homework by 121214 10:00", _deadline_task.toString());
    }
    
    @Test
    public void checkCopy() throws InvalidDateException, InvalidTimeException {
    	DeadlineTask test_copy = _deadline_task.copy();
    	assertEquals(test_copy.equals(_deadline_task), true);
    }
}
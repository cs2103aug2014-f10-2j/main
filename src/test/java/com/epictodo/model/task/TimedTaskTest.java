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
	private TimedTask _timed_task;
	private TimedTask _timed_task_copy;

	@Before
	public void initialise() throws Exception {
		_task = new Task("Meeting at CLB", "Group Project", 2);
		_timed_task = new TimedTask("Do CS2103 online quiz", "Quiz on testing",
				5, "121214", "20:00", 2.0);
		_timed_task_copy = new TimedTask("Do CS2103 online quiz", "Quiz on testing",
				5, "121214", "20:00", 2.0);
	}

	@Test
	public void checkSetDuration() {
		_timed_task.setDuration(4.0);
		assertEquals("131214 00:00", _timed_task.getEndDateTimeAsString());
	}

	@Test
	public void checkTimedTaskSetDateTime() throws Exception {
		_timed_task.setDateTime("251214", "19:00");
		assertEquals(_timed_task.getStartDateTimeAsString(), "251214 19:00");
	}

	@Test
	public void checkTimedTaskToString() {
		assertEquals(_timed_task.toString(),
				"Do CS2103 online quiz from 121214 20:00 to 121214 22:00");
	}

	@Test
	public void checkGetDetail() {
		assertEquals(
				"Name: Do CS2103 online quiz\nDescription: Quiz on testing\nStart Date and Time: 121214 20:00\nEnd Date and Time: 121214 22:00\n",
				_timed_task.getDetail());
	}
	
	@Test
	public void checkToString() {
		assertEquals("Do CS2103 online quiz from 121214 20:00 to 121214 22:00", _timed_task.toString());
	}
	
	@Test
	public void checkEquals() {
	 	assertEquals(_timed_task_copy.equals(_timed_task), true);
	}
	
	@Test
	public void checkCopy() {
		TimedTask test_copy = _timed_task.copy();
    	assertEquals(test_copy.equals(_timed_task), true);
	}
}

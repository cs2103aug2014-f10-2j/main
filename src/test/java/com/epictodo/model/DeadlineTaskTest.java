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

package com.epictodo.model;

import com.epictodo.model.task.DeadlineTask;
import com.epictodo.model.task.Task;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

//@author A0111683L
public class DeadlineTaskTest {
    private Task _task;
    private DeadlineTask deadline_task;

    @Before
    public void initialise() throws Exception {
        _task = new Task("Meeting at CLB", "Group Project", 2);
        deadline_task = new DeadlineTask("Do CS2103 homework", "Homework on testing", 5, "241014", "10:00");
    }

    @Test
    public void checkDeadlineTaskSetDateTime() throws Exception {
        deadline_task.setDateTime("251014", "23:59");
        assertEquals(deadline_task.getEndDateTimeAsString(), "251014 23:59");
    }

    /*
    @Test
    public void checkDeadlineTaskCloneMethod() {
        DeadlineTask tempDeadlineTask;
        tempDeadlineTask = deadline_task.copy();
        assertEquals(tempDeadlineTask.getTaskName(), "Do CS2103 homework");
        assertEquals(tempDeadlineTask.getTaskDescription(), "Homework on testing");
        assertEquals(tempDeadlineTask.getPriority(), 5);
        assertEquals(tempDeadlineTask.getIsDone(), false);
    }
    */
}
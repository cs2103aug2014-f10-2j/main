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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

//@author A0111683L
public class TaskTest {
    private Task _task;
    private Task _task_copy;

    @Before
    public void initialise() {
        _task = new Task("Meeting at CLB", "Group Project", 2);
        _task_copy = new Task("Meeting at CLB", "Group Project", 2);
    }

    @Test
    public void checkIsDoneMethod() {
        _task.setIsDone(true);
        assertEquals(_task.getIsDone(), true);
    }

    @Test
    public void checkSetTaskNameMethod() {
        _task.setTaskName("newTask");
        assertEquals(_task.getTaskName(), "newTask");
    }

    @Test
    public void checkSetTaskDescriptionMethod() {
        _task.setTaskDescription("newTaskDescription");
        assertEquals(_task.getTaskDescription(), "newTaskDescription");
    }

    @Test
    public void checkSetPriorityMethod() {
        _task.setPriority(10);
        assertEquals(_task.getPriority(), 10);
    }

    @Test
    public void checkGetDetail() {
    	assertEquals("Name: Meeting at CLB\nDescription: Group Project\n", _task.getDetail());
    }
    
    @Test
    public void checkEquals() {
    	assertEquals(_task_copy.equals(_task), true);
    }
    
    @Test
    public void checkToStringMethod() {
    	assertEquals("Meeting at CLB", _task.toString());
    }
    
    @Test
    public void checkCopyMethod() {
    	Task test_copy;
    	test_copy = _task.copy();
    	assertEquals(test_copy.equals(_task), true);
    }
}

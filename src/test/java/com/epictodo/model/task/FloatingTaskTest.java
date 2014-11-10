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

import com.epictodo.model.task.FloatingTask;
import com.epictodo.model.task.Task;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

//@author A0111683L
public class FloatingTaskTest {
    private Task _task;
    private FloatingTask _floating_task;
    private FloatingTask _floating_task_copy;

    @Before
    public void initialise() {
        _task = new Task("Meeting at CLB", "Group Project", 2);
        _floating_task = new FloatingTask("Meeting at CLB", "Group Project", 2);
        _floating_task_copy = new FloatingTask("Meeting at CLB", "Group Project", 2);
    }
    
    @Test
    public void checkToStringMethod() {
    	assertEquals("Meeting at CLB", _floating_task.toString());
    }
    
    @Test
    public void checkGetDetailMethod() {
    	assertEquals("Name: Meeting at CLB\nDescription: Group Project\n", _floating_task.getDetail());
    }
    
    @Test
    public void checkEqualsMethod() {
    	assertEquals(_floating_task_copy.equals(_floating_task), true);
    }
    
    @Test
    public void checkCopyMethod() {
    	FloatingTask test_copy;
    	test_copy = _floating_task.copy();
    	assertEquals(test_copy.equals(_floating_task), true);
    }
}

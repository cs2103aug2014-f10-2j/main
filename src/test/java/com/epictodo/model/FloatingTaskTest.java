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

import com.epictodo.model.FloatingTask;
import com.epictodo.model.Task;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kennetham on 24/10/14.
 */
public class FloatingTaskTest {
    private Task _task;
    private FloatingTask floating_task;

    @Before
    public void initialise() {
        _task = new Task("Meeting at CLB", "Group Project", 2);
        floating_task = new FloatingTask("Meeting at CLB", "Group Project", 2);
    }

    @Test
    public void checkFloatingTaskCloneMethod() {
        FloatingTask temp_ftask;
        temp_ftask = floating_task.clone();
        assertEquals(temp_ftask.getTaskName(), "Meeting at CLB");
        assertEquals(temp_ftask.getTaskDescription(), "Group Project");
        assertEquals(temp_ftask.getPriority(), 2);
        assertEquals(temp_ftask.getIsDone(), false);
    }
}

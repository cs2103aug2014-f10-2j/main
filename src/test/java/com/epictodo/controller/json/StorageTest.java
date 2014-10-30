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

package com.epictodo.controller.json;

import com.epictodo.model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageTest {
    private static final String file_name = "storage_test";
    private Storage _storage = new Storage();
    private Task _task = new Task("Project Meeting", "2103 project meeting", 2);
    private Task task_2 = new Task("Board Meeting", "Board of directors meeting", 2);
    private ArrayList<Task> task_list;
    private ArrayList<Task> expected_task;

    @Before
    public void initialize() throws IOException {
        task_list = new ArrayList<Task>();
        expected_task = Storage.loadDbFile(file_name);

        task_list.add(_task);
        task_list.add(task_2);

        _storage.saveToJson(file_name, task_list);
    }

    @After
    public void tearDown() throws Exception {
        File _file = new File(file_name);
        _file.delete();
    }

    @Test
    public void saveToJsonTest() throws IOException {
        boolean _result = _storage.saveToJson(file_name, task_list);
        assertTrue(_result);
    }

    @Test
    public void loadDbTest() throws IOException {
        assertEquals(expected_task.get(0).getTaskName(), task_list.get(0).getTaskName());
    }
}

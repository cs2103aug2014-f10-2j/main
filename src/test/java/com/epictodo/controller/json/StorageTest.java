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

import com.epictodo.model.DeadlineTask;
import com.epictodo.model.FloatingTask;
import com.epictodo.model.Task;
import com.epictodo.model.Task.TaskType;
import com.epictodo.model.TimedTask;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageTest {
    private static final String file_name = "storage.txt";
    private static Storage _storage = new Storage();
    private Map<TaskType, List<Task>> expected_map = new HashMap<>();
    private Task deadline_task = new DeadlineTask("CS2103 V0.3", "Complete V0.3 for testing", 4, "301014", "1900");
    private Task floating_task = new FloatingTask("CS2103 Project", "V0.3 incomplete version", 4);
    private Task floating_task2 = new FloatingTask("LAJ2202 Project", "Prepare materials to teach", 4);
    private Task timed_task = new TimedTask("LAJ2202", "Japanese conversation", 4, "311014", "1900", 2.0);

    private ArrayList<Task> floating_list;
    private ArrayList<Task> deadline_list;
    private ArrayList<Task> timed_list;

    @Before
    public void initialize() throws IOException {
        floating_list = new ArrayList<>();
        deadline_list = new ArrayList<>();
        timed_list = new ArrayList<>();

        deadline_list.add(deadline_task);
        floating_list.add(floating_task);
        floating_list.add(floating_task2);
        timed_list.add(timed_task);

        expected_map.put(TaskType.FLOATING, floating_list);
        expected_map.put(TaskType.DEADLINE, deadline_list);
        expected_map.put(TaskType.TIMED, timed_list);

        _storage.saveToJson(file_name, expected_map);
    }

    @After
    public void tearDown() throws Exception {
        _storage.saveToJson(file_name, null);
    }

    @Test
    public void saveToJsonTest() throws IOException {
        deadline_list.add(deadline_task);
        floating_list.add(floating_task);
        floating_list.add(floating_task2);
        timed_list.add(timed_task);

        Map<TaskType, List<Task>> map = new HashMap<>();
        map.put(TaskType.FLOATING, floating_list);
        map.put(TaskType.DEADLINE, deadline_list);
        map.put(TaskType.TIMED, timed_list);

        boolean _result = Storage.saveToJson(file_name, map);

        assertTrue(_result);
    }

    @Test
    public void loadDbTest() throws IOException {
        Map<TaskType, List<Task>> storage_map = Storage.loadDbFile(file_name);

        assertEquals(expected_map.get(TaskType.FLOATING).size(), storage_map.get(TaskType.FLOATING).size());
        assertEquals(expected_map.get(TaskType.DEADLINE).size(), storage_map.get(TaskType.DEADLINE).size());
        assertEquals(expected_map.get(TaskType.TIMED).size(), storage_map.get(TaskType.TIMED).size());
        assertEquals(expected_map.get(floating_list.get(0).getTaskDescription()), storage_map.get(floating_list.get(0).getTaskDescription()));
    }
}

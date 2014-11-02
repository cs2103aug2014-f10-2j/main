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
import com.epictodo.model.TimedTask;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StorageTest {
    private static final String file_name = "storage.txt";
    private Storage _storage = new Storage();
    private ArrayList<Task> task_list;
    private ArrayList<Task> expected_task;
    private String ttEndDateTime = "";
    private String dt2EndDateTime = "";

    /*
     * There will be 
     * 2 Floating Task, 
     * 2 deadLined Task
     * 2 Timed Task
     */
    @Before
    public void initialize() throws IOException {
        FloatingTask _task = null;
        FloatingTask task_2 = null;
        DeadlineTask deadline_task = null;
        DeadlineTask deadline_task2 = null;
        TimedTask timedTask = null;
        TimedTask timedTask2 = null;

        try {
            _task = new FloatingTask("Project Meeting", "2103 project meeting", 2);
            task_2 = new FloatingTask("Board Meeting", "Board of directors meeting", 2);
            deadline_task = new DeadlineTask("CS2103 V0.3", "Complete V0.3 for testing", 4, "301014", "19:00");
            deadline_task2 = new DeadlineTask("CS2103 V0.4", "DT2 Desc", 4, "301114", "19:00");
            timedTask = new TimedTask("TimedTask 1", "TimedTask1Desc", 2, "121214", "10:00", 3);
            timedTask2 = new TimedTask("TimedTask 2", "TimedTask2Desc", 3, "131214", "10:00", 2);
            ttEndDateTime = timedTask.getEndDateTimeAsString();
            dt2EndDateTime = deadline_task2.getEndDateTimeAsString();
        } catch (Exception e) {
        }

        task_list = new ArrayList<>();

        // load all tasks into task_list
        task_list.add(_task);
        task_list.add(task_2);
        task_list.add(deadline_task);
        task_list.add(deadline_task2);
        task_list.add(timedTask);
        task_list.add(timedTask2);

        _storage.saveToJson(file_name, task_list);
    }

    @After
    public void tearDown() throws Exception {
        File _file = new File(file_name);
//        _file.delete();
    }

    @Test
    public void timedTaskTest() throws IOException {
        // new loadDBTest
        ArrayList<Task> listOfTasks;

        // load the Json and deserialized them as an ArrayList<Task> and return it in listOfTasks
        listOfTasks = _storage.loadDbFile(file_name);

        //grab the first time Task and compare it's endDateTimeAsString
        TimedTask tt = (TimedTask) listOfTasks.get(0);
        System.out.println(listOfTasks);
        System.out.println(tt.getEndDateTimeAsString());
        assertEquals(ttEndDateTime, tt.getEndDateTimeAsString());
    }

    @Test
    public void deadLineTaskTest() throws IOException {
        // new loadDBTest
        ArrayList<Task> listOfTasks;

        // load the Json and deserialized them as an ArrayList<Task> and return it in listOfTasks
        listOfTasks = _storage.loadDbFile(file_name);

        //grab the second deadline Task and compare it's endDateTimeAsString
        DeadlineTask tt = (DeadlineTask) listOfTasks.get(3);
        assertEquals(dt2EndDateTime, tt.getEndDateTimeAsString());
    }
}

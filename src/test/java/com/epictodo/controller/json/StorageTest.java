//@author A0112918H
package com.epictodo.controller.json;

import static org.junit.Assert.assertEquals;

import java.awt.geom.FlatteningPathIterator;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.epictodo.model.task.DeadlineTask;
import com.epictodo.model.task.FloatingTask;
import com.epictodo.model.task.Task;
import com.epictodo.model.task.TimedTask;

public class StorageTest {
    private static final String file_name = "storage.txt";
    private ArrayList<Task> task_list;
    FloatingTask floating_task = null;
    DeadlineTask deadline_task2 = null;
    TimedTask timed_task = null;
    /*
     * There will be 
     * 2 Floating Task, 
     * 2 deadLined Task
     * 2 Timed Task
     */
    @Before
    public void initialize() throws IOException {


        try {
            floating_task = new FloatingTask("Project Meeting", "2103 project meeting", 2);
            deadline_task2 = new DeadlineTask("CS2103 V0.4", "DT2 Desc", 4, "121214", "19:00");
            timed_task = new TimedTask("TimedTask 1", "TimedTask1Desc", 2, "121214", "10:00", 3);
        } catch (Exception e) {
        }

        task_list = new ArrayList<>();

        // load all tasks into task_list
        task_list.add(floating_task);
        task_list.add(deadline_task2);
        task_list.add(timed_task);

        Storage.saveToJson(file_name, task_list);
    }

    @Test
    public void floatingTaskTest() throws IOException {
        // new loadDBTest
        ArrayList<Task> listOfTasks;

        // load the Json and deserialized them as an ArrayList<Task> and return it in listOfTasks
        listOfTasks = Storage.loadDbFile(file_name);
        
        //grab the first time Task and compare it's endDateTimeAsString
        FloatingTask tt = (FloatingTask) listOfTasks.get(2);
        assertEquals(floating_task.equals(tt),true);
    }
    
    @Test
    public void timedTaskTest() throws IOException {
        // new loadDBTest
        ArrayList<Task> listOfTasks;

        // load the Json and deserialized them as an ArrayList<Task> and return it in listOfTasks
        listOfTasks = Storage.loadDbFile(file_name);

        //grab the first time Task and compare it's endDateTimeAsString
        TimedTask tt = (TimedTask) listOfTasks.get(0);
        assertEquals(timed_task.equals(tt),true);
    }

    @Test
    public void deadLineTaskTest() throws IOException {
        // new loadDBTest
        ArrayList<Task> listOfTasks;

        // load the Json and deserialized them as an ArrayList<Task> and return it in listOfTasks
        listOfTasks = Storage.loadDbFile(file_name);

        //grab the second deadline Task and compare it's endDateTimeAsString
        DeadlineTask tt = (DeadlineTask) listOfTasks.get(1);
        assertEquals(deadline_task2.equals(tt),true);
    }
}

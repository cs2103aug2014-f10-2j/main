//@author A0112918H
package com.epictodo.controller.json;

import static org.junit.Assert.assertEquals;

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
    private String ttEndDateTime = "";
    private String dt2EndDateTime = "";
    FloatingTask _task = null;
    FloatingTask task_2 = null;
    DeadlineTask deadline_task = null;
    DeadlineTask deadline_task2 = null;
    TimedTask timedTask = null;
    TimedTask timedTask2 = null;
    /*
     * There will be 
     * 2 Floating Task, 
     * 2 deadLined Task
     * 2 Timed Task
     */
    @Before
    public void initialize() throws IOException {


        try {
            _task = new FloatingTask("Project Meeting", "2103 project meeting", 2);
            task_2 = new FloatingTask("Board Meeting", "Board of directors meeting", 2);
            deadline_task = new DeadlineTask("CS2103 V0.3", "Complete V0.3 for testing", 4, "121214", "19:00");
            deadline_task2 = new DeadlineTask("CS2103 V0.4", "DT2 Desc", 4, "121214", "19:00");
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

        Storage.saveToJson(file_name, task_list);
    }

    @Test
    public void timedTaskTest() throws IOException {
        // new loadDBTest
        ArrayList<Task> listOfTasks;

        // load the Json and deserialized them as an ArrayList<Task> and return it in listOfTasks
        listOfTasks = Storage.loadDbFile(file_name);

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
        listOfTasks = Storage.loadDbFile(file_name);

        //grab the second deadline Task and compare it's endDateTimeAsString
        DeadlineTask tt = (DeadlineTask) listOfTasks.get(3);
        assertEquals(deadline_task2.equals(tt),true);
    }
}

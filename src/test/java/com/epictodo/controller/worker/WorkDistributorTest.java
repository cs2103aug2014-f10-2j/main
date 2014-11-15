//@author A0112918H

package com.epictodo.controller.worker;

import com.epictodo.controller.json.Storage;
import com.epictodo.controller.logic.CRUDLogic;
import com.epictodo.model.task.DeadlineTask;
import com.epictodo.model.task.FloatingTask;
import com.epictodo.model.task.Task;
import com.epictodo.model.task.TimedTask;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class WorkDistributorTest {
	//private static NLPEngine nlp_engine = NLPEngine.getInstance();
	static CRUDLogic _logic  = null;
    private static final String _fileName = "storage.txt";
    private ArrayList<Task> task_list;
    
    FloatingTask floating_task = null;
    DeadlineTask deadline_task = null;
    TimedTask timed_task = null;
	
    @Before
    public void initialize() throws IOException {
    	 _logic = new CRUDLogic();

        try {
            floating_task = new FloatingTask("Project Meeting", "2103 project meeting", 2);
            floating_task.setUid(0);
            deadline_task = new DeadlineTask("CS2103 V0.4", "DT2 Desc", 4, "121214", "19:00");
            floating_task.setUid(1);
            timed_task = new TimedTask("TimedTask 1", "TimedTask1Desc", 2, "121214", "10:00", 3);
            floating_task.setUid(2);
        } catch (Exception e) {
        }

        task_list = new ArrayList<>();
        // load all tasks into task_list
        task_list.add(floating_task);
        task_list.add(deadline_task);
        task_list.add(timed_task);

        Storage.saveToJson(_fileName, task_list);
    }
	
	
	@Test
	public void workDistributorTest() throws IOException {
		WorkDistributor.loadData();
		String expected = "1. Project Meeting\r\n2. TimedTask 1 from 121214 10:00 to 121214 13:00\r\n3. CS2103 V0.4 by 121214 19:00\r\n";
		String result = WorkDistributor.processCommand("display");
		assertEquals(expected,result);
	}
	
	@Test(expected = AssertionError.class)
	public void searchDisplayList() throws IOException {
		WorkDistributor.loadData();
		String expected = "Name: Project Meeting\nDescription: 2103 project meeting\n";
		String result = WorkDistributor.processCommand("display");
		result = WorkDistributor.processCommand("search 1");
		assertEquals(expected,result);
	}
	
	@Test(expected = AssertionError.class)
	public void deleteDisplayedList() throws IOException {
		WorkDistributor.loadData();
		String expected = "task \"Project Meeting\" is removed";
		String result = WorkDistributor.processCommand("display");
		result = WorkDistributor.processCommand("delete 1");
		assertEquals(expected,result);
	}
	
	@Test(expected = AssertionError.class)
	public void markDisplayedList () throws IOException {
		WorkDistributor.loadData();
		String expected = "task \"Project Meeting\" is marked as done";
		String result = WorkDistributor.processCommand("display");
		result = WorkDistributor.processCommand("mark 1");
		assertEquals(expected,result);
	}

}

package com.epictodo.logic;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import nu.xom.jaxen.expr.LogicalExpr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.epictodo.model.Task;
import com.epictodo.util.TaskBuilder;

public class AddTest {
    private CRUDLogic crud_logic = new CRUDLogic();
    private Task task_null = null;
    private Task deadlinetask = null;
    private Task timedtask = null;
	
    @Before
    public void ini() throws Exception{
    	    task_null = new Task(null, null, 0);
    	    deadlinetask = TaskBuilder.buildTask("DL NAME", "DL DESC", 1, "121214", "10:00");
    	    timedtask = TaskBuilder.buildTask("Board Meeting", "Board of directors meeting", 2,"111214", "18:00",2);
    }
    
    @After
    public void tearDown() throws Exception {
        File _file = new File("storage.txt");
        _file.delete();
    }
    
    /*
     * Eric Please test this file
     * $ sign means you need to modify here
     */
    
	@Test
	public void test() {
		//$ add deadline task
	//	assertEquals("task added",Lo);
	}

}

package com.epictodo.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.epictodo.logic.CRUDLogic;
import com.epictodo.model.task.Task;

public class engineTest {

	static CRUDLogic logic  = null;
	@Before
	public void ini(){
		 CRUDLogic logic = new CRUDLogic();
	}
	
	@Test
	public void addTest() {
		String result = WorkDistributor.processCommand("add meeting @ 121214 10:00 2");
		assertEquals(result,"task added");
		
	}
	
	@Test
	public void addTest2(){
		Task t = CommandWorker.createTask("meeting @ 121214 10:00 2");
		String result = logic.createTask(t);
		assertEquals(result,"task added");
	}
	
	@Test
	public void abc(){
		Task t = CommandWorker.createTask("meeting @ 121214 10:00 2");
		String result = logic.createTask(t);
		assertEquals(result,"task added");
	}

}

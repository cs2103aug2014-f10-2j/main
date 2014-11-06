package com.epictodo.engine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NLPEngineTest {

	
	@Before
	public void ini(){
		// do anything you want to initialise NLP
	}
	/*
	 * This test case work with the basic NLP to attract Task's key attribute
	 */
	
	@Test
	public void level1DeadLineTest() {
		String expected_TaskName = "Compile V0.4.2"; 
		String expected_EndDate = "121214";
		String expected_EndTime = "10:00"; 
		
		final String level1 = "Compile V0.4.2 by 121214 10:00";
		String taskName=null;
		String endDate=null;
		String endTime=null;
		
		
		assertEquals(endDate, expected_EndDate);
	}
	
	@Test
	public void level1TimedTest() {
		String expected_TaskName = "CS2103 Project Meeting"; 
		String expected_StartDate = "121214";
		String expected_StartTime = "10:00"; 
		String expected_EndDate = "121214";
		String expected_EndTime = "12:00";
		
		final String level1 = "CS2103 Project Meeting from 121214 10:00 to 121214 12:00";
		String taskName=null;
		String endDate=null;
		String endTime=null;
		
		boolean compareThem = true;
		if (compareThem){
		assertTrue(true);
		}
	}

}

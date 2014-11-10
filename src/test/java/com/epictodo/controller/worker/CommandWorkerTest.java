package com.epictodo.controller.worker;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.epictodo.controller.nlp.NLPEngine;
import com.epictodo.model.task.DeadlineTask;
import com.epictodo.model.task.Task;

public class CommandWorkerTest {
	
	private static NLPEngine nlp_engine = NLPEngine.getInstance();

	
	/*
	 * This method test if the nlp is returning a proper date format when today is entered
	 */
	@Test
	public void testGetDateViaNlp() {
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		Date date = new Date();
		String today = dateFormat.format(date);
		String keyword = "today";
		String ddmmyy = CommandWorker.getDateViaNlp(keyword);
		assertEquals(ddmmyy, today);
		
	}
	
	/*
	 * This method test if Add works when a specific instruction is given
	 */
	@Test
	public void testAddCommandTest() {
		try{
		DeadlineTask expected_Task = new DeadlineTask("prepare ma1521 cheatsheet", "12/12/14 23:59" ,5 ,"121214", "23:59");
		String input = "prepare ma1521 cheatsheet by 12/12/14 23:59";
		Task t = CommandWorker.createTask(input);
		assertEquals(t, true);
		}
		catch(Exception e){
			
		}
	}
	
	

}

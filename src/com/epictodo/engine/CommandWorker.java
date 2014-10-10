package com.epictodo.engine;

import java.util.Scanner;
import java.util.logging.Logger;

import com.epictodo.model.DeadlineTask;
import com.epictodo.model.FloatingTask;
import com.epictodo.model.Task;
import com.epictodo.model.TimedTask;
import com.epictodo.util.TaskBuilder;

public class CommandWorker {
	private static int _defaultPriority = 2;
	private static String _defaultTime = "09:00";
	/*
	 * This is the helper class to retrieve value from instruction
	 * This class will call TaskBuilder to return a proper task.
	 */
	public static Task createTask(String instruc) {
		Logger logger = Logger.getLogger("System Log");
		Scanner s = new Scanner(instruc);
		String taskName = getTaskNameThroughInstruction(s);
		String taskDate = getTaskDateThroughInstruction(s);
		String taskTime = getTaskTimeThroughInstruction(s);
		double taskDuration = getTaskDurationThroughInstruction(s);
		String taskDesc="";

			if (taskName == null){
				logger.info("invalid command!");
				return null;
			}
			if (taskDate == null){
				return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority);
			}
			if(taskTime==null){
				//Deadline Task (default end time)
				return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, _defaultTime); 
			}
			if(taskDuration != -1){
				// Timed Task
				return TaskBuilder.buildTask (taskName,taskDesc,_defaultPriority, taskDate, taskTime, taskDuration);
			}
				// Deadline Task
			return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, taskTime); 
			
	}

	private static double getTaskDurationThroughInstruction(Scanner s) {
		while(s.hasNext()){
			return s.nextDouble();
		}
		return -1;
	}
	
	private static String getTaskTimeThroughInstruction(Scanner s) {
		while(s.hasNext()){
			return s.next();
		}
		return null;
	}

	private static String getTaskDateThroughInstruction(Scanner s) {
		// exception to be added next time
		while(s.hasNext()){
			return s.next();
		}
		return null;
	}

	private static String getTaskNameThroughInstruction(Scanner s) {
		//exception to be added next time
		String taskName = "";
		while (s.hasNext()){
			String next = s.next();
			if (next.equals ("@"))
				return taskName;
			else
				taskName += next;
		}
		return null;
	}
}

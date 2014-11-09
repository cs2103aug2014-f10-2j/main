package com.epictodo.util;

import com.epictodo.model.DeadlineTask;
import com.epictodo.model.FloatingTask;
import com.epictodo.model.InvalidDateException;
import com.epictodo.model.InvalidTimeException;
import com.epictodo.model.Task;
import com.epictodo.model.TimedTask;

/*
    "start_date_time": 1412907000,
    "end_date_time": 1412914200,
    "task_name": "project meeting 2103",
    "task_description": "Nothing much",
    "priority": 2,
    "is_done": false
	HashMap<String, String> memory = new HashMap<>();
 */

public class TaskBuilder {
	
	
	public static Task buildTask(String taskName, String taskDescription, int priority,
			String ddmmyy, String time, double duration) throws InvalidDateException, InvalidTimeException{
		
		Task tt;
			tt = new TimedTask(taskName, taskDescription, priority, ddmmyy, time, duration);
		return tt;
	}
	
	public static Task buildTask(String taskName, String taskDescription, int priority, String ddmmyy, String time) throws InvalidDateException, InvalidTimeException{
		Task dt;
			dt = new DeadlineTask(taskName, taskDescription, priority, ddmmyy, time);
		return dt;
	}
	
	public static Task buildTask(String taskName, String taskDescription, int priority){
		Task ft;
			ft = new FloatingTask(taskName, taskDescription, priority);
		return ft;
	}
	
}

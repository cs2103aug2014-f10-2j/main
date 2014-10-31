package com.epictodo.util;

import com.epictodo.model.DeadlineTask;
import com.epictodo.model.FloatingTask;
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
			String ddmmyy, String time, double duration){
		
		Task tt;
		try {
			tt = new TimedTask(taskName, taskDescription, priority, ddmmyy, time, duration);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return tt;
	}
	
	public static Task buildTask(String taskName, String taskDescription, int priority, String ddmmyy, String time){
		Task dt;
		try {
			dt = new DeadlineTask(taskName, taskDescription, priority, ddmmyy, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return dt;
	}
	
	public static Task buildTask(String taskName, String taskDescription, int priority){
		Task ft;
		try {
			ft = new FloatingTask(taskName, taskDescription, priority);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return ft;
	}
	
}

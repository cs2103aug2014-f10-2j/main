//package com.epictodo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

class FloatingTask extends BaseClass {
	/************** Data members **********************/
	private static ArrayList<String> Tasks = new ArrayList<String>();
	private String taskName;
	private String taskDescription;

	/************** Constructors **********************/
	FloatingTask(String taskName, String desc) {
		setTaskName(taskName);
		setTaskDescription(taskDescription);
	}
	
	/**************** Accessors ***********************/
	String getTaskName() {
		return taskName;
	}

	String getTaskDescription() {
		return taskDescription;
	}

	/**************** Mutators ************************/
	void setTaskName(String newTask) {
		taskName = newTask;
	}

	void setTaskDescription(String newTaskDescription) {
		taskDescription = newTaskDescription;
	}
	
/*	
	public static Date insertStartDate(Date calendar) {
		Date result = new Date();
		return result;
	}
	
	public static Date insertEndDate(Date calendar) {
		Date result = new Date();
		return result;
	}
	*/
}

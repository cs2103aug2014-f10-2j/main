package com.epictodo.model;

import java.sql.Date;
import java.text.ParseException;
import java.util.*;

public class Task {

	/************** Data members **********************/
	private String taskName;
	private String taskDescription;
	private int priority = 0;
	private boolean isDone = false;

	/************** Constructors **********************/

	public Task(String taskName, String taskDescription, int priority) {
		setTaskName(taskName);
		setTaskDescription(taskDescription);
		setPriority(priority);
		setIsDone(false);
	}
	
	/**************** Accessors ***********************/
	public String getTaskName() {
		return taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public int getPriority() {
		return priority;
	}

	public boolean getIsDone() {
		return isDone;
	}

	/**************** Mutators ************************/
	public void setTaskName(String newTask) {
		taskName = newTask;
	}

	public void setTaskDescription(String newTaskDescription) {
		taskDescription = newTaskDescription;
	}

	public void setPriority(int newPriority) {
		priority = newPriority;
	}

	public void setIsDone(boolean status) {
		isDone = status;
	}
}
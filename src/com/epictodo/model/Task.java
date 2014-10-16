package com.epictodo.model;

import java.sql.Date;
import java.text.ParseException;
import java.util.*;

public class Task {

	/************** Data members **********************/
	private String taskName;
	private String taskDescription;
	private int priority;
	private boolean isDone;

	/************** Constructors **********************/

	public Task(String taskName, String taskDescription, int priority) {
		assert priority >= 0 && priority <= 10;
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
		assert newPriority >= 0 && newPriority <= 10;
		priority = newPriority;
	}

	public void setIsDone(boolean status) {
		assert status == true || status == false;
		isDone = status;
	}
	
	/**************** Other methods ************************/
	public String toString(){
		return getTaskName();
	}
}
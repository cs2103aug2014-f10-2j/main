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

	Task(String taskName, String taskDescription, int priority,
			boolean isDone) {
		setTaskName(taskName);
		setTaskDescription(taskDescription);
		setPriority(priority);
		setIsDone(isDone);
	}

	/**************** Accessors ***********************/
	String getTaskName() {
		return taskName;
	}

	String getTaskDescription() {
		return taskDescription;
	}

	int getPriority() {
		return priority;
	}

	boolean getIsDone() {
		return isDone;
	}

	/**************** Mutators ************************/
	void setTaskName(String newTask) {
		taskName = newTask;
	}

	void setTaskDescription(String newTaskDescription) {
		taskDescription = newTaskDescription;
	}

	void setPriority(int newPriority) {
		priority = newPriority;
	}

	void setIsDone(boolean status) {
		isDone = status;
	}
}
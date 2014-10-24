package com.epictodo.model;

import java.sql.Date;
import java.text.ParseException;
import java.util.*;

public class Task {

	/************** Data members **********************/
	private long uid;
	private String taskName;
	private String taskDescription;
	private int priority;
	private boolean isDone;

	/************** Constructors **********************/

	public Task(String taskName, String taskDescription, int priority) {
		assert priority >= 0 && priority <= 10;
		/*
		 * New member: long uid
		 */
		uid = -1; // -1 indicates an newly created Task object

		setTaskName(taskName);
		setTaskDescription(taskDescription);
		setPriority(priority);
		setIsDone(false);
	}

	/**************** Accessors ***********************/
	public long getUid() {
		return uid;
	}

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
	public void setUid(long uid) {
		this.uid = uid;
	}

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

	/**************** Class methods ************************/
	public String toString() {
		return getTaskName();
	}

	public Task clone() {
		Task cloned = null;
		if (this instanceof FloatingTask) {
			cloned = new FloatingTask(getTaskName(), getTaskDescription(),
					getPriority());
		} else if (this instanceof DeadlineTask) {
			cloned = new DeadlineTask(getTaskName(), getTaskDescription(),
					getPriority(), ((DeadlineTask) this).getDate(),
					((DeadlineTask) this).getTime());
		} else if (this instanceof TimedTask) {
			cloned = new TimedTask(getTaskName(), getTaskDescription(),
					getPriority(), ((TimedTask) this).getStartDate(),
					((TimedTask) this).getStartTime(),
					((TimedTask) this).getDuration());
		} else {
			cloned = new Task(getTaskName(), getTaskDescription(),
					getPriority());
		}
		cloned.setUid(uid);
		return cloned;
	}
}
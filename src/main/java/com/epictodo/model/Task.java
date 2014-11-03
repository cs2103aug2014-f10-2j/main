package com.epictodo.model;

import java.text.ParseException;

public class Task {

	/************** Data members **********************/
	private long uid;
	private String taskName;
	private String taskDescription;
	private int priority;
	private boolean isDone;

	/************** Constructors 
	 * @throws Exception **********************/

	public Task(String taskName, String taskDescription, int priority) throws Exception {
		if ( priority < 0 || priority > 10){
			throw new Exception();
		}
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
		return this.uid;
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

	public Task copy() throws ParseException, InvalidDateException, InvalidTimeException{
		Task cloned= null;
		try {
			cloned = new Task(getTaskName(), getTaskDescription(), getPriority());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cloned.setUid(getUid());
		return cloned;
	}
}
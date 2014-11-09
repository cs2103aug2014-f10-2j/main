package com.epictodo.model;

import java.text.ParseException;

//@author A0111683L
public class Task {

	/************** Data members **********************/
	private long uid;
	private String taskName;
	private String taskDescription;
	private int priority;
	private boolean isDone;

	/************** Constructors 
	 * @throws Exception **********************/

	public Task(String taskName, String taskDescription, int priority) {
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
	public String getDetail() {
		return "Name: " + this.taskName + '\n' + "Description: " + this.taskDescription + '\n';
	}
	
	public Boolean equals(Task task2) {
		Boolean uid = false;
		Boolean taskName = false;
		Boolean taskDescription = false;
		Boolean priority = false;
		Boolean isDone = false;
		
		if (this.getTaskName() == task2.getTaskName()) {
			taskName = true;
		}
		if (this.getUid() == task2.getUid()) {
			uid = true;
		}
		if (this.getTaskDescription() == task2.getTaskDescription()) {
			taskDescription = true;
		}
		if (this.getPriority() == task2.getPriority()) {
			priority = true;
		}
		if (this.getIsDone() == task2.getIsDone()) {
			isDone = true;
		}
		
		if (uid && taskName && taskDescription && priority && isDone) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return getTaskName();
	}

	public Task copy(){
		Task cloned= null;
			cloned = new Task(getTaskName(), getTaskDescription(),
					getPriority());
		cloned.setUid(getUid());
		return cloned;
	}
}
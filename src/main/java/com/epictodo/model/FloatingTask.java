package com.epictodo.model;

import java.text.ParseException;

public class FloatingTask extends Task {
	/************** Data members **********************/

	/************** Constructors 
	 * @throws Exception **********************/
	public FloatingTask(String taskName, String taskDescription, int priority) throws Exception {
		super(taskName, taskDescription, priority);
	}
	public FloatingTask(Task t) throws Exception {
		super(t.getTaskName(), t.getTaskDescription(), t.getPriority());
	}

	/**************** Accessors ***********************/

	/**************** Mutators ************************/

	/**************** Class Methods ************************/
	/*
	 * public FloatingTask clone() { String taskName = super.getTaskName();
	 * String taskDescription = super.getTaskDescription(); int priority =
	 * super.getPriority(); FloatingTask newClone = new FloatingTask(taskName,
	 * taskDescription, priority); return newClone; }
	 */
	public String toString() {
		return super.toString();
	}
	
	public String getDetail() {
		return super.getDetail();
	}
	
	public Boolean equals(FloatingTask task2) {
		Boolean compareTask = super.equals(task2);
		
		if (compareTask) {
			return true;
		} else {
			return false;
		}
	}
	
	public FloatingTask copy() throws ParseException, InvalidDateException, InvalidTimeException{
		Task t = super.copy();
		FloatingTask cloned= null;
		try {
			cloned = new FloatingTask(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cloned.setUid(t.getUid());
		return cloned;
	}
}

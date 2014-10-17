package com.epictodo.model;

public class FloatingTask extends Task {
	/************** Data members **********************/

	/************** Constructors **********************/
	public FloatingTask(String taskName, String taskDescription, int priority) {
		super(taskName, taskDescription, priority);
	}
	
	/**************** Accessors ***********************/

	/**************** Mutators ************************/
	
	/**************** Class Methods ************************/
	public FloatingTask clone() {
		String taskName = super.getTaskName();
		String taskDescription = super.getTaskDescription();
		int priority = super.getPriority();
		FloatingTask newClone = new FloatingTask(taskName, taskDescription, priority);
		return newClone;
	}
}

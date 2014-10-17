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
	public Task clone() {
		return super.clone();
	}
}

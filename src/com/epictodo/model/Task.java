package com.epictodo.model;

import java.util.ArrayList;

class Task {

	/************** Data members **********************/
	private static ArrayList<String> Tasks = new ArrayList<String>();
	private String taskName;
	private String taskDescription;
	private int priority = 0;
	private boolean isDone = false;

	/************** Constructors **********************/
	Task() {
		setTaskName("NIL");
		setTaskDescription("NIL");
		setPriority(0);
		isDone = false;
	}

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

	/**************** Class Methods ************************/
	static String addTask(String str) {
		String result = " ";
		Tasks.add(str);
		return result;
	}

	static String display() {
		String result = " ";
		return result;
	}

	static String deleteTask(String str) {
		String result = " ";
		Tasks.remove(str);
		return result;
	}

	static String searchTask(String str) {
		String result = " ";
		return result;
	}

	// This method allows user to update priority in task in the ArrayList
	static int updatePriority(int priority) {
		int result = 0;
		return result;
	}

	public static void main(String[] args) {
		//Unix timestamp testing
		long epoch = System.currentTimeMillis()/1000;
		String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (epoch*1000));
		System.out.println(date);
	}
}
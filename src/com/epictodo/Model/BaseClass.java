package com.epictodo.Model;

import java.util.ArrayList;

class BaseClass {

	/************** Data members **********************/
	private static ArrayList<String> Tasks = new ArrayList<String>();
	private String taskName;
	private String taskDescription;
	private int priority = 0;
	private boolean isDone = false;

	/************** Constructors **********************/
	public BaseClass() {
		setTaskName("NIL");
		setTaskDescription("NIL");
		setPriority(0);
		isDone = false;
	}

	public BaseClass(String taskName, String taskDescription, int priority,
			boolean isDone) {
		setTaskName(taskName);
		setTaskDescription(taskDescription);
		setPriority(priority);
		setIsDone(isDone);
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

	/**************** Class Methods ************************/
	public static String addTask(String str) {
		String result = " ";
		Tasks.add(str);
		return result;
	}

	public static String display() {
		String result = " ";
		return result;
	}

	public static String deleteTask(String str) {
		String result = " ";
		Tasks.remove(str);
		return result;
	}

	public static String searchTask(String str) {
		String result = " ";
		return result;
	}

	// This method allows user to update priority in task in the ArrayList
	public static int updatePriority(int priority) {
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
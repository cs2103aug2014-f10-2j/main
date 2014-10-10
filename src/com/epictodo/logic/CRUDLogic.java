package com.epictodo.logic;

import com.epictodo.controller.json.*;
import com.epictodo.model.*;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Eric
 *
 */
public class CRUDLogic {

	/*
	 * Constants
	 */
	private static final String STRING_LINE_BREAK = "\r\n";
	private static final String PATH_DATA_FILE = "storage.txt";

	/*
	 * Private Attributes
	 */
	private ArrayList<Task> items;

	/*
	 * Constructor
	 */
	public CRUDLogic() {
		items = new ArrayList<Task>();
	}

	/*
	 * Getters
	 */
	/**
	 * This method returns the whole list of Tasks regardless of their status
	 * 
	 * @return the ArrayList containing all the tasks
	 */
	public ArrayList<Task> getAllTasks() {
		return items;
	}

	/**
	 * This method returns tasks based on whether it has been marked as done
	 * 
	 * @return the ArrayList containing selected tasks
	 */
	public ArrayList<Task> getTasksByName(String keyword) {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i = 0; i < size(); i++) {
			if (items.get(i).getTaskName().toLowerCase()
					.contains(keyword.trim().toLowerCase())) {
				list.add(items.get(i));
			}
		}
		return list;
	}

	/**
	 * This method returns tasks based on whether it has been marked as done
	 * 
	 * @return the ArrayList containing selected tasks
	 */
	public ArrayList<Task> getTasksByStatus(boolean done) {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i = 0; i < size(); i++) {
			if (items.get(i).getIsDone() == done) {
				list.add(items.get(i));
			}
		}
		return list;
	}

	/**
	 * This method returns a list of tasks based on the priority set
	 * 
	 * @param p
	 *            the priority enum
	 * @return the ArrayList containing the selected tasks
	 */
	public ArrayList<Task> getTasksByPriority(int p) {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i = 0; i < size(); i++) {
			if (items.get(i).getPriority() == p) {
				list.add(items.get(i));
			}
		}
		return list;
	}

	/*
	 * Other CRUD Methods
	 */

	/**
	 * This method adds a Task to the list
	 * 
	 * @param t
	 *            the Task obj
	 * @return The result in a String
	 */
	public String createTask(Task t) {
		items.add(t);
		saveToFile();
		return "task added";
	}
	
	
	/**
	 * This method adds an Floating Task to the list
	 * 
	 * @param ft
	 *            the FloatingTask obj
	 * @return The result in a String
	 */
	public String createTask(FloatingTask ft) {
		items.add(ft);
		return "floating task added";
	}

	/**
	 * This method adds an Deadline Task to the list
	 * 
	 * @param dt
	 *            the DeadlineTask obj
	 * @return The result in a String
	 */
	public String createTask(DeadlineTask dt) {
		items.add(dt);
		return "deadline task added";
	}

	/**
	 * This method adds an Floating Task to the list
	 * 
	 * @param tt
	 *            the TimedTask obj
	 * @return The result in a String
	 */
	public String createTask(TimedTask tt) {
		items.add(tt);
		return "timed task added";
	}

	/**
	 * This method removes a
	 * 
	 * @param t
	 * @return
	 */
	public String deleteTask(Task t) {
		if (this.items.remove(t)) {
			return "task removed";
		} else {
			return "can't remove task";
		}
	}

	/*
	 * Other Methods
	 */
	/**
	 * This method returns the number of task obj in the list
	 * 
	 * @return int the size of the list of tasks
	 */
	public int size() {
		return items.size();
	}

	/**
	 * This method returns a string that represent all the tasks in the task
	 * list in RAM
	 * 
	 * @return
	 */
	public String displayAllTaskList() {
		return displayList(items);
	}

	/**
	 * This method returns a string that represent all the tasks in a list
	 * 
	 * @param li
	 * @return
	 */
	public String displayList(ArrayList<Task> li) {
		String retStr = "";
		for (int i = 0; i < size(); i++) {
			retStr += String.valueOf(i + 1) + ". " + li.get(i)
					+ STRING_LINE_BREAK;
		}
		return retStr;
	}

	/**
	 * This method displays the content of all the tasks that matches the
	 * keyword in names
	 * 
	 * @param keyword
	 * @return
	 */
	public String searchForTasks(String keyword) {
		return displayList(getTasksByName(keyword));
	}

	/*
	 * Storage handlers
	 */

	/**
	 * This method loads all tasks from the text file
	 */
	public String loadFromFile() throws IOException {
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks = Storage.loadDbFile(PATH_DATA_FILE);
		return "data loaded";
	}

	/**
	 * This method saves all tasks to the text file
	 */
	public void saveToFile() {
		String filename = PATH_DATA_FILE;
		Storage s = new Storage();
		s.saveToJson(filename, items);
	}
}

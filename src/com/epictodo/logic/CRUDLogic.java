package com.epictodo.logic;

import com.epictodo.controller.json.TokenParser;
import com.epictodo.model.*;

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
	 * Private Attributes
	 */
	private ArrayList<Object> items;

	/*
	 * Constructor
	 */
	public CRUDLogic() {
		items = new ArrayList<Object>();
	}

	/*
	 * Getters
	 */
	/**
	 * This method returns the whole list of Tasks regardless of their status
	 * 
	 * @return the ArrayList containing all the tasks
	 */
	public ArrayList<Object> getAllTasks() {
		/*
		 * CODE HERE
		 */
		return null;
	}

	/**
	 * This method returns tasks based on whether it has been marked as done
	 * 
	 * @return the ArrayList containing selected tasks
	 */
	public ArrayList<Object> getTasksByStatus(boolean done) {
		/*
		 * CODE HERE
		 */
		return null;
	}

	/**
	 * This method returns a list of tasks based on the priority set
	 * 
	 * @param p
	 *            the priority enum
	 * @return the ArrayList containing the selected tasks
	 */
	public ArrayList<Object> getTasksByPriority() {
		/*
		 * CODE HERE
		 */
		return null;
	}

	/*
	 * Other CRUD Methods
	 */

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

	/*
	 * Storage handlers
	 */

	/**
	 * This method loads all tasks from the text file
	 */
	public String loadFromFile() {
		items = TokenParser.jsonObjectHandler();
		return "data loaded";
	}

	/**
	 * This method saves all tasks to the text file
	 */
	public void saveToFile() {
		// Code here
	}
}

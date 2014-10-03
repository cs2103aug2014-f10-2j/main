package com.epictodo.logic;

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
	public ArrayList<Task> getTasksByStatus(boolean done) {
		/*
		 * CODE HERE
		 */
		return null;
	}
	
	/**
	 * This method returns a list of tasks based on the priority set
	 * @param p the priority enum
	 * @return the ArrayList containing the selected tasks
	 */
	public ArrayList<Task> getTasksByPriority(){
		/*
		 * CODE HERE
		 */
		return null;
	}
	
	/*
	 * Other CRUD Methods
	 */
	
	/**
	 * this method allows the user to add a task
	 */
	public void addTask(){
		
	}
	
	/*
	 * Other Methods
	 */
}

package com.epictodo.logic;

import com.epictodo.controller.json.Storage;
import com.epictodo.model.*;

import java.io.IOException;
import java.text.ParseException;
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
	private static final int CONFIG_PRIORITY_MIN = 1;
	private static final int CONFIG_PRIORITY_MAX = 3;

	/*
	 * Private Attributes
	 */
	private long _nextUid; // to track the next available uid for new Task
	private ArrayList<Task> _items; // to store all tasks
	private ArrayList<Undoable> _commands; // to store undoable commands

	/*
	 * Constructor
	 */
	public CRUDLogic() {
		_items = new ArrayList<Task>();
		_commands = new ArrayList<Undoable>();
		_nextUid = 1;
	}

	/*
	 * Getters
	 */
	/**
	 * This method returns the whole list of Tasks regardless of their status
	 * 
	 * @return the ArrayList containing all the tasks
	 * @throws InvalidTimeException 
	 * @throws InvalidDateException 
	 * @throws ParseException 
	 */
	public ArrayList<Task> getAllTasks() throws ParseException, InvalidDateException, InvalidTimeException {
		/*
		 * the return should only deliver a duplicate of the objects
		 */
		ArrayList<Task> retList = new ArrayList<Task>();
		for (int i = 0; i < _items.size(); i++) {
			retList.add(_items.get(i).copy());
		}
		return retList;
	}

	/**
	 * This method returns tasks based on whether it has been marked as done
	 * 
	 * @return the ArrayList containing selected tasks
	 * @throws InvalidTimeException 
	 * @throws InvalidDateException 
	 * @throws ParseException 
	 */
	public ArrayList<Task> getTasksByName(String keyword)
			throws NullPointerException, ParseException, InvalidDateException, InvalidTimeException {
		ArrayList<Task> list = new ArrayList<Task>();

		/*
		 * Exception handling to make sure param is not null
		 */
		if (keyword == null) {
			throw new NullPointerException("Keyword must not be <null>");
		}

		for (int i = 0; i < size(); i++) {
			if (_items.get(i).getTaskName().toLowerCase()
					.contains(keyword.trim().toLowerCase())) {
				list.add(_items.get(i).copy());
			}
		}
		return list;
	}

	/**
	 * This method returns tasks based on whether it has been marked as done
	 * 
	 * @return the ArrayList containing selected tasks
	 * @param boolean when true = Marked as done
	 * @throws InvalidTimeException 
	 * @throws InvalidDateException 
	 * @throws ParseException 
	 */
	public ArrayList<Task> getTasksByStatus(boolean done) throws ParseException, InvalidDateException, InvalidTimeException {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i = 0; i < size(); i++) {
			if (_items.get(i).getIsDone() == done) {
				list.add(_items.get(i).copy());
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
	 * @throws InvalidTimeException 
	 * @throws InvalidDateException 
	 * @throws ParseException 
	 */
	public ArrayList<Task> getTasksByPriority(int p)
			throws IllegalArgumentException, ParseException, InvalidDateException, InvalidTimeException {
		ArrayList<Task> list = new ArrayList<Task>();

		/*
		 * Exception handling to make sure the priority is within valid range
		 */
		if (p < CONFIG_PRIORITY_MIN || p > CONFIG_PRIORITY_MAX) {
			throw new IllegalArgumentException("Illegal priority");
		}
		for (int i = 0; i < size(); i++) {
			if (_items.get(i).getPriority() == p) {
				list.add(_items.get(i).copy());
			}
		}
		return list;
	}

	/*
	 * Other CRUD Methods
	 */

	/**
	 * This method assign UID to new Task and add it to the list
	 * 
	 * @param t
	 *            : the task to add
	 */
	private void addToItems(Task t) {
		t.setUid(_nextUid);
		_items.add(t);
		_nextUid++;
	}

	/**
	 * This method adds a Task to the list
	 * 
	 * @param t
	 *            the Task obj
	 * @return The result in a String
	 */
	public String createTask(Task t) throws NullPointerException {
		if (t == null) {
			return "invalid input";
		}
		addToItems(t);

		/*
		 * Create an undoable command object
		 */
		addCommand(Undoable.CommandType.ADD, t);

		try {
			saveToFile();
		} catch (IOException ioe) {
			_items.remove(t);
			return "Failed to create task due to File IO error";
		}
		return "task added";
	}

	/**
	 * This method adds an Floating Task to the list
	 * 
	 * @param ft
	 *            the FloatingTask obj
	 * @return The result in a String
	 */
	public String createTask(FloatingTask ft) throws NullPointerException {
		return createTask(ft);
	}

	/**
	 * This method adds an Deadline Task to the list
	 * 
	 * @param dt
	 *            the DeadlineTask obj
	 * @return The result in a String
	 */
	public String createTask(DeadlineTask dt) throws NullPointerException {
		return createTask(dt);
	}

	/**
	 * This method adds an Floating Task to the list
	 * 
	 * @param tt
	 *            the TimedTask obj
	 * @return The result in a String
	 */
	public String createTask(TimedTask tt) throws NullPointerException {
		return createTask(tt);
	}

	/**
	 * This method removes a Task by UID
	 * 
	 * @param t
	 * @return
	 */
	public String deleteTask(Task t) {
		if (t == null)
			return "invalid input";
		Task found = getTaskByUid(t.getUid());

		if (found != null && _items.remove(found)) {
			int index = _items.indexOf(getTaskByUid(found.getUid()));

			try {
				/*
				 * create an undoable command
				 */
				addCommand(Undoable.CommandType.DELETE, found, index);

				saveToFile();
				return "task removed";
			} catch (IOException ioe) {
				_items.remove(t);
				return "Failed to create task due to File IO error";
			}
		} else {
			return "can't remove task";
		}
	}

	public String updateTask(Task target, Task replacement) {
		int index = _items.indexOf(getTaskByUid(target.getUid()));
		if (index != -1) {
			_items.set(index, replacement);
			/*
			 * create an undoable command
			 */
			addCommand(Undoable.CommandType.UPDATE, target, replacement);

			/*
			 * save changes to storage
			 */
			try {
				saveToFile();
				return "task updated";
			} catch (IOException e) {
				e.printStackTrace();
				return "there was an error saving the update";
			}
		} else {
			return "invalid input";
		}
	}

	/**
	 * This method invokes the undo operation on the most recent undoable action
	 * 
	 * @return The result in a string
	 */
	public String undoMostRecent() {
		String result = "no more actions to be undone.";

		if (_commands.size() > 0) {
			Undoable comm = _commands.get(_commands.size() - 1);
			result = comm.undo();

			/*
			 * assuming no REDO is allowed
			 */
			_commands.remove(_commands.size() - 1);
		}

		return result;
	}

	/**
	 * This method returns an actual reference to the task with a specific UID
	 * in the item list
	 * 
	 * @param uid
	 * @return
	 */
	private Task getTaskByUid(long uid) {
		for (int i = 0; i < _items.size(); i++) {
			if (_items.get(i).getUid() == uid) {
				return _items.get(i);
			}
		}
		return null;
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
		return _items.size();
	}

	/**
	 * This method returns a string that represent all the tasks in the task
	 * list in RAM
	 * 
	 * @return
	 */
	public String displayAllTaskList() {
		try {
			return displayList(getAllTasks());
		} catch (ParseException | InvalidDateException | InvalidTimeException e) {
			e.printStackTrace();
			return "error!";
		}
		
	}

	/**
	 * This method displays the content of all the tasks that matches the
	 * keyword in names
	 * 
	 * @param keyword
	 * @return
	 */
	public String searchForTasks(String keyword) {
		try {
			return displayList(getTasksByName(keyword));
		} catch (NullPointerException | ParseException | InvalidDateException
				| InvalidTimeException e) {
			e.printStackTrace();
			return "error!";
		}
	}

	/*
	 * Storage handlers
	 */

	/**
	 * This method loads all tasks from the text file
	 */
	public boolean loadFromFile() throws IOException {
		_items = Storage.loadDbFile(PATH_DATA_FILE);
		if (_items == null) {
			_items = new ArrayList<Task>();
		}
		_nextUid = getMaxuID();
		return true;
	}

	/**
	 * This method saves all tasks to the text file
	 */
	public void saveToFile() throws IOException {
		String filename = PATH_DATA_FILE;
		Storage.saveToJson(filename, _items);
	}

	/**
	 * This method returns a string that represent all the tasks in a list
	 * 
	 * @param li
	 * @return
	 */
	public String displayList(ArrayList<Task> li) {
		String retStr = "";
		for (int i = 0; i < li.size(); i++) {
			retStr += String.valueOf(i + 1) + ". " + li.get(i)
					+ STRING_LINE_BREAK;
		}
		return retStr;
	}

	private long getMaxuID() {
		long max = 0;
		if (_items != null) {
			for (int i = 0; i < _items.size(); i++) {
				if (_items.get(i).getUid() > max) {
					max = _items.get(i).getUid();
				}
			}
		}
		return max + 1;
	}

	/**
	 * This method creates an undoable command in the stack
	 * 
	 * @param type
	 * @param target
	 */
	private void addCommand(Undoable.CommandType type, Task target) {
		Undoable comm = new Undoable(_items, type, target);
		_commands.add(comm);
	}

	private void addCommand(Undoable.CommandType type, Task target,
			Task replacement) {
		Undoable comm = new Undoable(_items, type, target, replacement);
		_commands.add(comm);
	}

	private void addCommand(Undoable.CommandType type, Task target, int index) {
		Undoable comm = new Undoable(_items, type, target, index);
		_commands.add(comm);
	}

}

// @author A0112725N

package com.epictodo.logic;

import com.epictodo.controller.json.Storage;
import com.epictodo.model.*;
import com.epictodo.util.TaskDueDateComparator;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class CRUDLogic {

	/*
	 * Message Constants
	 */
	private static final String MSG_CANT_REMOVE_TASK = "can't remove task";
	private static final String MSG_POSTFIX_AS_DONE = "\" as done";
	private static final String MSG_FAILED_TO_MARK_TASK = "failed to mark task \"";
	public static final String MSG_NO_MORE_ACTIONS_TO_BE_REDONE = "no more actions to be redone";
	public static final String MSG_NO_MORE_ACTIONS_TO_BE_UNDONE = "no more actions to be undone";
	private static final String MSG_POSTFIX_IS_REMOVED = "\" is removed";
	private static final String MSG_POSTFIX_IS_UPDATED = "\" is updated";
	private static final String MSG_POSTFIX_MARKED_AS_DONE = "\" is marked as done";
	private static final String MSG_ERROR = "error!";
	private static final String MSG_POSTFIX_IS_ADDED = "\" is added";
	private static final String MSG_PREFIX_TASK = "task \"";
	private static final String MSG_FAILED_IO_ERROR = "failed due to file io error";
	private static final String MSG_INVALID_INPUT = "invalid input";
	private static final String MSG_ILLEGAL_PRIORITY = "illegal priority";
	private static final String MSG_KEYWORD_MUST_NOT_BE_NULL = "keyword must not be <null>";

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
	private ArrayList<Command> _undoList; // to store undoable commands
	private ArrayList<Command> _redoList; // to store undone items

	/*
	 * Constructor
	 */
	public CRUDLogic() {
		_items = new ArrayList<Task>();
		_undoList = new ArrayList<Command>();
		_redoList = new ArrayList<Command>();
		_nextUid = 1;
	}

	/*
	 * CRUD Methods
	 */

	/**
	 * This method returns the whole list of Tasks regardless of their status
	 * 
	 * @return the ArrayList containing all the tasks
	 * @throws InvalidTimeException
	 * @throws InvalidDateException
	 * @throws ParseException
	 */
	public ArrayList<Task> getAllTasks() throws ParseException,
			InvalidDateException, InvalidTimeException {
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
	 * This method returns the whole list of incomplete tasks
	 * 
	 * @return the ArrayList containing all the tasks
	 * @throws InvalidTimeException
	 * @throws InvalidDateException
	 * @throws ParseException
	 */
	public ArrayList<Task> getIncompleteTasks() throws ParseException,
			InvalidDateException, InvalidTimeException {
		/*
		 * the return should only deliver a duplicate of the objects
		 */
		ArrayList<Task> retList = new ArrayList<Task>();
		for (int i = 0; i < _items.size(); i++) {
			if (!_items.get(i).getIsDone())
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
			throws NullPointerException, ParseException, InvalidDateException,
			InvalidTimeException {
		ArrayList<Task> list = new ArrayList<Task>();

		/*
		 * Exception handling to make sure param is not null
		 */
		if (keyword == null) {
			throw new NullPointerException(MSG_KEYWORD_MUST_NOT_BE_NULL);
		}

		for (int i = 0; i < size(); i++) {
			if (_items.get(i).getTaskName().toLowerCase()
					.contains(keyword.trim().toLowerCase())
					&& !_items.get(i).getIsDone()) {
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
	public ArrayList<Task> getTasksByStatus(boolean done)
			throws ParseException, InvalidDateException, InvalidTimeException {
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
			throws IllegalArgumentException, ParseException,
			InvalidDateException, InvalidTimeException {
		ArrayList<Task> list = new ArrayList<Task>();

		/*
		 * Exception handling to make sure the priority is within valid range
		 */
		if (p < CONFIG_PRIORITY_MIN || p > CONFIG_PRIORITY_MAX) {
			throw new IllegalArgumentException(MSG_ILLEGAL_PRIORITY);
		}
		for (int i = 0; i < size(); i++) {
			if (_items.get(i).getPriority() == p && !_items.get(i).getIsDone()) {
				list.add(_items.get(i).copy());
			}
		}
		return list;
	}

	/**
	 * This method returns a list of active tasks in chronological order of due
	 * dates
	 * 
	 * @return array list of tasks
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 * @throws InvalidDateException
	 * @throws InvalidTimeException
	 */
	public ArrayList<Task> getTasksOrderedByDueDate()
			throws IllegalArgumentException, ParseException,
			InvalidDateException, InvalidTimeException {
		ArrayList<Task> list = new ArrayList<Task>();
		ArrayList<Task> temp = new ArrayList<Task>();

		for (int i = 0; i < size(); i++) {
			if (_items.get(i) instanceof FloatingTask) {
				// Add floating tasks to list first
				list.add(_items.get(i).copy());
			} else {
				// Dump all other tasks into a temp list
				temp.add(_items.get(i).copy());
			}
		}

		// sort the temp list
		Collections.sort(temp, new TaskDueDateComparator());

		// add the ordered temp list to the final list
		list.addAll(temp);

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
	public String createTask(Task t) throws NullPointerException {
		if (t == null) {
			return MSG_INVALID_INPUT;
		}
		addToItems(t);

		/*
		 * Create an undoable command object
		 */
		addCommand(Command.CommandType.ADD, t);

		try {
			saveToFile();
		} catch (IOException ioe) {
			_items.remove(t);
			return MSG_FAILED_IO_ERROR;
		}
		return MSG_PREFIX_TASK + t.getTaskName() + MSG_POSTFIX_IS_ADDED;
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
			return MSG_ERROR;
		}
	}

	public String markAsDone(Task t) {
		if (t == null)
			return MSG_INVALID_INPUT;
		Task found = getTaskByUid(t.getUid());

		if (found != null) {
			int index = _items.indexOf(getTaskByUid(found.getUid()));
			try {
				found.setIsDone(true);

				addCommand(Command.CommandType.MARKDONE, found, index);

				saveToFile();
				return MSG_PREFIX_TASK + t.getTaskName()
						+ MSG_POSTFIX_MARKED_AS_DONE;
			} catch (IOException ioe) {
				found.setIsDone(false);
				return MSG_FAILED_TO_MARK_TASK + t.getTaskName()
						+ MSG_POSTFIX_AS_DONE;
			}
		} else {
			return MSG_FAILED_TO_MARK_TASK + t.getTaskName()
					+ MSG_POSTFIX_AS_DONE;
		}
	}

	/**
	 * This method updates a task item by replacing it with an updated one
	 * 
	 * @param target
	 * @param replacement
	 * @return the message indicating the successfulness of the operation
	 */
	public String updateTask(Task target, Task replacement) {
		int index = _items.indexOf(getTaskByUid(target.getUid()));
		if (index != -1) {
			_items.set(index, replacement);
			/*
			 * create an undoable command
			 */
			addCommand(Command.CommandType.UPDATE, target, replacement);

			/*
			 * save changes to storage
			 */
			try {
				saveToFile();
				return MSG_PREFIX_TASK + target.getTaskName()
						+ MSG_POSTFIX_IS_UPDATED;
			} catch (IOException e) {
				e.printStackTrace();
				return MSG_FAILED_IO_ERROR;
			}
		} else {
			return MSG_INVALID_INPUT;
		}
	}

	/*
	 * Undo and Redo methods
	 */

	/**
	 * This method removes a Task by UID
	 * 
	 * @param t
	 * @return
	 */
	public String deleteTask(Task t) {
		if (t == null)
			return MSG_INVALID_INPUT;
		Task found = getTaskByUid(t.getUid());

		int index = _items.indexOf(getTaskByUid(found.getUid()));

		if (found != null && _items.remove(found)) {
			try {
				/*
				 * create an undoable command
				 */
				addCommand(Command.CommandType.DELETE, found, index);

				saveToFile();
				return MSG_PREFIX_TASK + t.getTaskName()
						+ MSG_POSTFIX_IS_REMOVED;
			} catch (IOException ioe) {
				_items.remove(t);
				return MSG_FAILED_IO_ERROR;
			}
		} else {
			return MSG_CANT_REMOVE_TASK;
		}
	}

	/**
	 * This method invokes the undo operation on the most recent undoable action
	 * 
	 * @return The result in a string
	 */
	public String undoMostRecent() {
		String result = MSG_NO_MORE_ACTIONS_TO_BE_UNDONE;

		if (_undoList.size() > 0) {
			Command comm = _undoList.get(_undoList.size() - 1);
			result = comm.undo();

			/*
			 * to enable redo
			 */
			_redoList.add(_undoList.remove(_undoList.size() - 1));
		}

		return result;
	}

	/**
	 * This method invokes the redo operation on the most recent undoable action
	 * 
	 * @return The result in a string
	 */
	public String redoMostRecent() {
		String result = MSG_NO_MORE_ACTIONS_TO_BE_REDONE;

		if (_redoList.size() > 0) {
			Command comm = _redoList.get(_redoList.size() - 1);
			result = comm.redo();
			_undoList.add(_redoList.remove(_redoList.size() - 1));
		}

		return result;
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
			return MSG_ERROR;
		}

	}

	/**
	 * This method returns a string that represent all incomplete tasks list in
	 * RAM
	 * 
	 * @return
	 */
	public String displayIncompleteTaskList() {
		try {
			return displayList(getIncompleteTasks());
		} catch (ParseException | InvalidDateException | InvalidTimeException e) {
			e.printStackTrace();
			return MSG_ERROR;
		}

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
		_nextUid = getMaxUid();
		return true;
	}

	/**
	 * This method saves all tasks to the text file
	 */
	public void saveToFile() throws IOException {
		String filename = PATH_DATA_FILE;
		Storage.saveToJson(filename, _items);
	}

	/*
	 * Private Methods
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

	private long getMaxUid() {
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
	private void addCommand(Command.CommandType type, Task target) {
		Command comm = new Command(_items, type, target);
		_undoList.add(comm);
	}

	private void addCommand(Command.CommandType type, Task target,
			Task replacement) {
		Command comm = new Command(_items, type, target, replacement);
		_undoList.add(comm);
	}

	private void addCommand(Command.CommandType type, Task target, int index) {
		Command comm = new Command(_items, type, target, index);
		_undoList.add(comm);
	}

	public void clearExpiredTask() {
		for (int i = 0; i < size(); i++) {
			Task t = _items.get(i);

			long dateTimeNow = System.currentTimeMillis() / 1000L;

			if (t instanceof TimedTask && !t.getIsDone()
					&& ((TimedTask) t).getEndDateTime() < dateTimeNow) {
				t.setIsDone(true);
			} else if (t instanceof DeadlineTask && !t.getIsDone()
					&& ((DeadlineTask) t).getEndDateTime() < dateTimeNow) {
				t.setIsDone(true);
			}
		}
		try {
			saveToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

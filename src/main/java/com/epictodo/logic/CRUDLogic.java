package com.epictodo.logic;

import com.epictodo.model.Task.TaskType;
import com.epictodo.controller.json.Storage;
import com.epictodo.model.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Map<TaskType, List<Task>> _items; // to store all tasks
    private ArrayList<Task> floating_task;
    private ArrayList<Task> deadline_task;
    private ArrayList<Task> timed_task;
	private ArrayList<Undoable> _commands; // to store undoable commands

	/*
	 * Constructor
	 */
	public CRUDLogic() {
		_items = new HashMap<>();
        floating_task = new ArrayList<>();
        deadline_task = new ArrayList<>();
        timed_task = new ArrayList<>();
		_nextUid = 1;
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
		 * the return should only deliver a duplicate of the objects
		 */
       ArrayList<Task> retList = new ArrayList<>();
		for (int i = 0; i < _items.size(); i++) {
			retList.add(floating_task.get(i).clone());
            retList.add(deadline_task.get(i).clone());
            retList.add(timed_task.get(i).clone());
		}
		return retList;
	}

	/**
	 * This method returns tasks based on whether it has been marked as done
	 * 
	 * @return the ArrayList containing selected tasks
	 */
	public ArrayList<Task> getTasksByName(String keyword)
			throws NullPointerException {
        ArrayList<Task> list = new ArrayList<>();

		/*
		 * Exception handling to make sure param is not null
		 */
        if (keyword == null) {
            throw new NullPointerException("Keyword must not be <null>");
        }

        for (int i = 0; i < size(); i++) {
            if (floating_task.get(i).getTaskName().toLowerCase()
                    .contains(keyword.trim().toLowerCase())) {
                list.add(floating_task.get(i).clone());
            } else if (deadline_task.get(i).getTaskName().toLowerCase()
                    .contains(keyword.trim().toLowerCase())) {
                list.add(deadline_task.get(i).clone());
            } else if (timed_task.get(i).getTaskName().toLowerCase()
                    .contains(keyword.trim().toLowerCase())) {
                list.add(timed_task.get(i).clone());
            }
        }
        return list;
    }

	/**
	 * This method returns tasks based on whether it has been marked as done
	 * 
	 * @return the ArrayList containing selected tasks
	 * @param boolean when true = Marked as done
	 */
	public ArrayList<Task> getTasksByStatus(boolean done) {
		ArrayList<Task> list = new ArrayList<>();
		for (int i = 0; i < size(); i++) {
			if (floating_task.get(i).getIsDone() == done) {
				list.add(floating_task.get(i).clone());
			} else if (deadline_task.get(i).getIsDone() == done) {
                list.add(deadline_task.get(i).clone());
            }  else if (timed_task.get(i).getIsDone() == done) {
                list.add(timed_task.get(i).clone());
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
	public ArrayList<Task> getTasksByPriority(int p)
			throws IllegalArgumentException {
		ArrayList<Task> list = new ArrayList<Task>();

		/*
		 * Exception handling to make sure the priority is within valid range
		 */
		if (p < CONFIG_PRIORITY_MIN || p > CONFIG_PRIORITY_MAX) {
			throw new IllegalArgumentException("Illegal priority");
		}
		for (int i = 0; i < size(); i++) {
			if (floating_task.get(i).getPriority() == p) {
				list.add(floating_task.get(i).clone());
			} else if (deadline_task.get(i).getPriority() == p) {
                list.add(deadline_task.get(i).clone());
            } else if (timed_task.get(i).getPriority() == p) {
                list.add(timed_task.get(i).clone());
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
        _items.put(TaskType.FLOATING, floating_task);
        _items.put(TaskType.FLOATING, deadline_task);
        _items.put(TaskType.FLOATING, timed_task);
//		_items.add(t);
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
			throw new NullPointerException("Cannot create <null> into CRUD");
		}
		addToItems(t);

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
		Task found = getTaskByUid(t.getUid());
		if (found != null) {
            _items.remove(found);
			return "task removed";
		} else {
			return "can't remove task";
		}
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
			if (floating_task.get(i).getUid() == uid) {
				return floating_task.get(i);
			} else if (deadline_task.get(i).getUid() == uid) {
                return deadline_task.get(i);
            } if (timed_task.get(i).getUid() == uid) {
                return timed_task.get(i);
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
		return displayList(getAllTasks());
	}

	/**
	 * This method displays the content of all the tasks that matches the
	 * keyword in names
	 * 
	 * @param keyword
	 * @return
	 */
	public String searchForTasks(String keyword) throws NullPointerException {
		return displayList(getTasksByName(keyword));
	}

	/*
	 * Storage handlers
	 */

	/**
	 * This method loads all tasks from the text file
	 */
	public boolean loadFromFile() throws IOException {
		_items = Storage.loadDbFile(PATH_DATA_FILE);
		_nextUid = getMaxuID();
		return true;
	}

	/**
	 * This method saves all tasks to the text file
	 */
	public void saveToFile() throws IOException {
		Storage.saveToJson(PATH_DATA_FILE, _items);
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
	
	private long getMaxuID(){
		long max =0;
		if (_items!=null){
			for (int i =0; i<_items.size();i++){
				if (floating_task.get(i).getUid()>max){
					max = floating_task.get(i).getUid();
				} else if (deadline_task.get(i).getUid()>max){
                    max = deadline_task.get(i).getUid();
                } if (timed_task.get(i).getUid()>max){
                    max = timed_task.get(i).getUid();
                }
			}
		}
		return max+1;
	}

}

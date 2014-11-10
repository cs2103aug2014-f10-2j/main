// @author A0112725N

package com.epictodo.model.command;

import java.util.ArrayList;

import com.epictodo.model.task.Task;

/**
 * This class represents a undoable command
 */
public class Command implements Undoable {

	/*
	 * Private attributes
	 */
	private ArrayList<Task> _container; // the list of tasks to operate on
	private CommandType _type; // the type of command
	private Task _target; // the original task object
	private Task _replacement; // the updated task object to replace the
	private int _index; // the index where the original item resides in the list

	/*
	 * Constructor
	 */

	/**
	 * @param container
	 * @param type
	 * @param target
	 */
	public Command(ArrayList<Task> container, CommandType type, Task target) {
		_container = container;
		_type = type;
		_target = target;
		_replacement = null;
		_index = -1;
	}

	/*
	 * Constructors
	 */

	/**
	 * Constructor
	 *
	 * @param container
	 * @param type
	 * @param target
	 * @param replacement
	 */
	public Command(ArrayList<Task> container, CommandType type, Task target,
			Task replacement) {
		_container = container;
		_type = type;
		_target = target;
		_replacement = replacement;
		_index = -1;
	}

	/**
	 * Constructor
	 *
	 * @param container
	 * @param type
	 * @param target
	 * @param index
	 */
	public Command(ArrayList<Task> container, CommandType type, Task target,
			int index) {
		_container = container;
		_type = type;
		_target = target;
		_replacement = null;
		_index = index;
	}

	/**
	 * This method invokes the undo action on the list of tasks
	 */
	public String undo() {
		String result = "";

		switch (_type) {
		case ADD:
			_container.remove(_target);
			result = "adding task \"" + _target.getTaskName() + "\" is undone";
			break;

		case DELETE:
			_container.add(_index, _target);

			result = "deleting task \"" + _target.getTaskName()
					+ "\" is undone";
			break;

		case UPDATE:
			_container.set(_container.indexOf(_replacement), _target);

			result = "updating task \"" + _target.getTaskName()
					+ "\" is undone";
			break;
			
		case MARKDONE:
			_container.get(_index).setIsDone(false);

			result = "marking task \"" + _target.getTaskName()
					+ "\"as done is undone";
			
			break;
		}

		return result;
	}

	/*
	 * Interface requirements
	 */

	/**
	 * This method invokes the redo action
	 */
	public String redo() {
		String result = "";

		switch (_type) {
		case ADD:
			_container.add(_target);
			result = "adding task \"" + _target.getTaskName() + "\" is redone";

			break;

		case DELETE:
			_container.remove(_index);
			result = "deleting task \"" + _target.getTaskName()
					+ "\" is redone";
			break;

		case UPDATE:
			_container.set(_container.indexOf(_target), _replacement);
			result = "updating task \"" + _target.getTaskName()
					+ "\" is redone";
			break;
		case MARKDONE:
			_container.get(_index).setIsDone(true);
			result = "marking task \"" + _target.getTaskName()
					+ "\"as done is redone";
		}

		return result;
	}

	/**
	 * This is a overriding toString method
	 */
	public String toString() {
		String desc = "command: ";
		switch (_type) {
		case ADD:
			desc += "add ";
			break;
		case DELETE:
			desc += "delete ";
			break;
		case UPDATE:
			desc += "update ";
			break;
		case MARKDONE:
			desc += "mark as done ";
		}
		desc += "\"" + _target.toString() + "\"";
		if (_type == CommandType.UPDATE) {
			desc += " to \"" + _replacement.toString() + " \"";
		}
		return desc;
	}

	/**
	 * The types of commands that are undoable
	 */
	public enum CommandType {
		ADD, DELETE, UPDATE, MARKDONE
	}
}

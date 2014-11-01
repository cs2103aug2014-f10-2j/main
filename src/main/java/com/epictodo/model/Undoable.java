/**
 * 
 */
package com.epictodo.model;

import java.util.ArrayList;

/**
 * @author Eric
 *
 */
public class Undoable {
	public enum CommandType {
		ADD, DELETE, UPDATE
	}

	private ArrayList<Task> _container;
	private CommandType _type;
	private Task _target;
	private Task _replacement;

	public Undoable(ArrayList<Task> container, CommandType type, Task target) {
		_container = container;
		_type = type;
		_target = target;
		_replacement = null;
	}

	public Undoable(ArrayList<Task> container, CommandType type, Task target,
			Task replacement) {
		_container = container;
		_type = type;
		_target = target;
		_replacement = replacement;
	}

	public String undo() {
		String result = "";

		switch (_type) {
		case ADD:
			_container.remove(_target);
			result = "adding task \"" + _target.getTaskName() + "\" is undone";
			break;

		case DELETE:
			_container.add(_target);
			result = "deleting task \"" + _target.getTaskName()
					+ "\" is undone";
			break;

		case UPDATE:
			Task temp = _replacement;
			_replacement = _target;
			_target = temp;
			result = "updating task \"" + _target.getTaskName()
					+ "\" is undone";
			break;
		}

		return result;
	}

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
		}
		desc += "\"" + _target.toString() + "\"";
		if (_type == CommandType.UPDATE) {
			desc += " to \"" + _replacement.toString() + " \"";
		}
		return desc;
	}
}
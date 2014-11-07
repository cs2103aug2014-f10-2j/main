package com.epictodo.model;

import java.util.ArrayList;

/**
 * @author Eric
 *
 */
public class Command implements Undoable{
	public enum CommandType {
		ADD, DELETE, UPDATE, MARKDONE
	}

	private ArrayList<Task> _container;
	private CommandType _type;
	private Task _target;
	private Task _replacement;
	private int _index;

	public Command(ArrayList<Task> container, CommandType type, Task target) {
		_container = container;
		_type = type;
		_target = target;
		_replacement = null;
		_index = -1;
	}

	public Command(ArrayList<Task> container, CommandType type, Task target,
			Task replacement) {
		_container = container;
		_type = type;
		_target = target;
		_replacement = replacement;
		_index = -1;
	}

	public Command(ArrayList<Task> container, CommandType type, Task target,
			int index) {
		_container = container;
		_type = type;
		_target = target;
		_replacement = null;
		_index = index;
	}

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
			
			result = "marking task \"" + _target.getTaskName() + "\"as done is undone";
		}

		return result;
	}
	
	public String redo(){
		return "";
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

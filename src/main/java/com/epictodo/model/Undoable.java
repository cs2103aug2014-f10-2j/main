/**
 * 
 */
package com.epictodo.model;

/**
 * @author Eric
 *
 */
public class Undoable {
	enum CommandType {
		ADD, DELETE, UPDATE
	}

	private CommandType _type;
	private Task _target;
	private Task _replacement;

	public Undoable(CommandType type, Task target) {
		_type = type;
		_target = target;
		_replacement = null;
	}

	public Undoable(CommandType type, Task target, Task replacement) {
		_type = type;
		_target = target;
		_replacement = replacement;
	}

	public void Undo() {
		// TODO: implement undo code for different types of Undoable
	}
}

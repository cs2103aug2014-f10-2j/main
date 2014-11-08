package com.epictodo.engine;

import java.awt.DisplayMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.EmptyStackException;

import com.epictodo.logic.CRUDLogic;
import com.epictodo.model.InvalidDateException;
import com.epictodo.model.InvalidTimeException;
import com.epictodo.model.Task;

// user input = { command + instruction }
public class WorkDistributor {
	static CRUDLogic logic = new CRUDLogic();
	private final static String[] COMMAND_EXIT = { "exit", "quit" };
	private final static String[] COMMAND_ADD = { "add", "create" };
	private final static String[] COMMAND_UPDATE = { "update", "change",
			"modify" };
	private final static String[] COMMAND_DELETE = { "delete", "remove" };
	private final static String[] COMMAND_SEARCH = { "search", "find" };
	private final static String[] COMMAND_DISPLAY = { "display" };
	private final static String[] COMMAND_UNDO = { "undo","revert" };
	private final static String[] COMMAND_REDO = { "redo" };
	private final static String[] COMMAND_DONE = {"done", "mark"};

	private static final String MSG_INVALID_INPUT = "invalid input";

	enum CommandType {
		DISPLAY, ADD, DELETE, UPDATE, SEARCH, EXIT, INVALID, NULL, UNDO, REDO, DONE
	};
	
	public static boolean loadData() {
		try {
			return logic.loadFromFile();
		} catch (Exception ex) {
			return false;
		}
	}
	
	public static String proceedInstruc(String instruc) {
		String result = "";
		ArrayList<Task> list = null;
		Task t = null;
		// Clear expired timed tasks
		logic.clearExpiredTask();

		CommandType command = defineCommandType(instruc);
		instruc = removeCommand(instruc);

		switch (command) {
		case DISPLAY:
			return logic.displayIncompleteTaskList();

		case ADD:
			t = CommandWorker.createTask(instruc);
			result = logic.createTask(t);
			return result;

		case DELETE:
		case DONE:
		case UPDATE:
		case SEARCH:
			list = searchThroughKeywords(instruc);
			if (list == null){
				return "Cannnot find '"+instruc+"'";
			}
			result = selectItemProcess(list, command);
			return result;
		case EXIT:
			System.exit(0);
			break;
		case UNDO:
			result = logic.undoMostRecent();
			return result;
		case INVALID:
			// todo: defined all invalid cases
			return MSG_INVALID_INPUT;
		default:
			break;
		}

		// todo handle invalid input here
		return null;
	}
	
	private static String selectItemProcess(ArrayList<Task> list, CommandType command) {
		String result =null;
		Task t=null;
		try{
			t = MenuWorker.selectItemFromList(command, list,
					logic.displayList(list));
		}catch(IndexOutOfBoundsException iobe){
			return MSG_INVALID_INPUT;
		}
		result = processCommand(command, result, t);
		return result;
	}

	private static String processCommand(CommandType command, String result,
			Task t) {
		switch(command){
		case DELETE:
			result = logic.deleteTask(t);
			break;
		case DONE:
			result = logic.markAsDone(t);
			break;
		case UPDATE:
			Task updatedTask = MenuWorker.updateTask(t);
			result = logic.updateTask(t, updatedTask);
			break;
		case SEARCH:
			result = t.getDetail();
			break;
		default:
			break;
		}
		return result;
	}

	private static ArrayList<Task> searchThroughKeywords(String instruc) {
			ArrayList<Task> list = new ArrayList<Task>();
		try {
			
			list = logic.getTasksByName(instruc);
		} catch (NullPointerException | ParseException
				| InvalidDateException | InvalidTimeException e) {
			return null;
		}
		return list;
	}

	private static CommandType defineCommandType(String instruc) {
		String command = getCommand(instruc);
		if (compareString(command, ""))
			return CommandType.NULL;
		else if (identifyCommand(command, COMMAND_ADD)) {
			return CommandType.ADD;
		} else if (identifyCommand(command, COMMAND_DELETE)) {
			return CommandType.DELETE;
		} else if (identifyCommand(command, COMMAND_UPDATE)) {
			return CommandType.UPDATE;
		} else if (identifyCommand(command, COMMAND_SEARCH)) {
			return CommandType.SEARCH;
		} else if (identifyCommand(command, COMMAND_DISPLAY)) {
			return CommandType.DISPLAY;
		} else if (identifyCommand(command, COMMAND_EXIT)) {
			return CommandType.EXIT;
		} else if (identifyCommand(command, COMMAND_UNDO)) {
			return CommandType.UNDO;
		} else if (identifyCommand(command,COMMAND_REDO)){
			return CommandType.REDO;
		} else if (identifyCommand(command,COMMAND_DONE)){
			return CommandType.DONE;
		} else{
			return CommandType.INVALID;
		}
	}

	private static boolean identifyCommand(String command, final String[] vocabs) {
		for (int i = 0; i < vocabs.length; i++) {
			if (compareString(command, vocabs[i])) {
				return true;
			}
		}
		return false;
	}

	private static boolean compareString(String text, String text2) {
		return (text.equalsIgnoreCase(text2));
	}

	private static String removeCommand(String instruc) {
		return instruc.substring(getCommand(instruc, true), instruc.length());
	}

	private static int getCommand(String instruc, boolean findLength) {
		String commandTypeString = instruc.trim().split("\\s+")[0];
		return commandTypeString.length();
	}

	private static String getCommand(String instruc) {
		String commandTypeString = instruc.trim().split("\\s+")[0];
		return commandTypeString;
	}
}

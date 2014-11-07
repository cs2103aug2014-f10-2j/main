package com.epictodo.engine;

import java.text.ParseException;
import java.util.ArrayList;

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
	private final static String[] COMMAND_UNDO = { "undo" };
	private final static String[] COMMAND_DONE = {"done", "mark"};

	enum CommandType {
		DISPLAY, ADD, DELETE, UPDATE, SEARCH, EXIT, INVALID, NULL, UNDO, DONE
	};

	public static String proceedInstruc(String instruc) {
		// Clear expired timed tasks
		logic.clearExpiredTask();

		// get command
		CommandType command = defineCommandType(instruc);

		/*
		 * <command> <instruction> Since, NLP should handle only instruction
		 * Therefore, NLP can look into CommandWorker to fill in the attribute
		 * for task nicely.
		 */
		instruc = removeCommand(instruc);
		String result = "";
		ArrayList<Task> list = null;
		Task t = null;
		switch (command) {
		case DISPLAY:
			return logic.displayIncompleteTaskList();

		case ADD:
			t = CommandWorker.createTask(instruc);
			result = logic.createTask(t);
			return result;

		case DELETE:
			try {
				list = logic.getTasksByName(instruc);
			} catch (NullPointerException | ParseException
					| InvalidDateException | InvalidTimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t = MenuWorker.selectItemFromList(command, list,
						logic.displayList(list));
			} catch (edu.stanford.nlp.semgraph.semgrex.ParseException e) {
				return "task not found";
			}
			result = logic.deleteTask(t);
			return result;
			
		case DONE:
			try {
				list = logic.getTasksByName(instruc);
			} catch (NullPointerException | ParseException
					| InvalidDateException | InvalidTimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t = MenuWorker.selectItemFromList(command, list,
						logic.displayList(list));
			} catch (edu.stanford.nlp.semgraph.semgrex.ParseException e) {
				return "task not found";
			}
			result = logic.markAsDone(t);
			return result;
		case UPDATE:
			try {
				list = logic.getTasksByName(instruc);
			} catch (NullPointerException | ParseException
					| InvalidDateException | InvalidTimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t = MenuWorker.selectItemFromList(command, list,
						logic.displayList(list));
			} catch (edu.stanford.nlp.semgraph.semgrex.ParseException e) {
				return "task not found";
			}
			Task updatedTask = MenuWorker.updateTask(t);
			result = logic.updateTask(t, updatedTask);
			return result;

		case SEARCH:
			try {
				list = logic.getTasksByName(instruc);
			} catch (NullPointerException | ParseException
					| InvalidDateException | InvalidTimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t = MenuWorker.selectItemFromList(command, list,
						logic.displayList(list));
			} catch (edu.stanford.nlp.semgraph.semgrex.ParseException e) {
				return "task not found";
			}
			return t.getDetail();


		case EXIT:
			System.exit(0);
			break;

		case INVALID:
			// todo: defined all invalid cases
			return "This is invalid";

		case UNDO:
			result = logic.undoMostRecent();
			return result;

		}

		// todo handle invalid input here
		return null;
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
		} else if (identifyCommand(command,COMMAND_DONE)){
			return CommandType.DONE;
		} else
			return CommandType.INVALID;
	}

	private static boolean identifyCommand(String command, final String[] vocabs) {
		for (int i = 0; i < vocabs.length; i++) {
			if (compareString(command, vocabs[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean loadData() {
		try {
			return logic.loadFromFile();
		} catch (Exception ex) {
			return false;
		}
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

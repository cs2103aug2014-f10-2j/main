package com.epictodo.engine;

import com.epictodo.logic.CRUDLogic;
import com.epictodo.model.Task;

import java.util.ArrayList;

// user input = { command + instruction }
public class WorkDistributor {
	static CRUDLogic logic = new CRUDLogic();
	private final static String COMMAND_EXIT = "exit";
	private final static String COMMAND_ADD = "add";
	private final static String COMMAND_UPDATE = "update";
	private final static String COMMAND_DELETE = "delete";
	private final static String COMMAND_SEARCH = "search";
	private final static String COMMAND_DISPLAY = "display";
	private final static String COMMAND_UNDO = "undo";
	private final static String OPTION_ADD = "1";
	private final static String OPTION_FIND = "2";
	private final static String OPTION_DISPLAY = "3";
	private final static String OPTION_UNDO = "4";
	private final static String OPTION_OTHERS = "5";
	private final static String OPTION_EXIT = "6";

	enum CommandType {
		DISPLAY, ADD, DELETE, UPDATE, SEARCH, EXIT, INVALID, NULL, O_ADD, O_FIND, O_DISPLAY, UNDO, O_OTHERS
	};

	public static String proceedInstruc(String instruc) {

		// get command
		CommandType command = defineCommandType(instruc);

		// read instruction
		instruc = removeCommand(instruc);
		String result = "";
		ArrayList<Task> list = null;
		Task t = null;
		switch (command) {
		case DISPLAY:
			return logic.displayAllTaskList();

		case ADD:
			t = CommandWorker.createTask(instruc);
			result = logic.createTask(t);
			return result;

		case DELETE:
			list = logic.getTasksByName(instruc);
			t = MenuWorker.selectItemFromList(command, list,
					logic.displayList(list));
			result = logic.deleteTask(t);
			return result;

		case UPDATE:
			list = logic.getTasksByName(instruc);
			t = MenuWorker.selectItemFromList(command, list,
					logic.displayList(list));
			// todo: delete task t in memory and storage return successful
			// message in String
			return result;

		case SEARCH:
			list = logic.getTasksByName(instruc);
			t = MenuWorker.selectItemFromList(command, list,
					logic.displayList(list));
			// todo: display task in a proper format return successful message
			// in String
			return result;

		case EXIT:
			System.exit(0);
			break;
		case INVALID:
			// todo: defined all invalid cases
			return "This is invalid";
		case O_ADD:
			t = MenuWorker.addMenu();
			result = logic.createTask(t);
			return result;

		case O_FIND:
			list = logic.getTasksByName(MenuWorker.findMenu());
			t = MenuWorker.selectItemFromList(command, list,
					logic.displayList(list));
			// todo prepare to display them in details as "result"
			return result;

		case UNDO:
			return result;

		case O_OTHERS:
			return result;
		}

		// todo handle invalid input here
		return null;
	}

	private static CommandType defineCommandType(String instruc) {
		String command = getCommand(instruc);
		if (compareString(command, ""))
			return CommandType.NULL;
		else if (compareString(command, COMMAND_ADD))
			return CommandType.ADD;
		else if (compareString(command, COMMAND_DELETE))
			return CommandType.DELETE;
		else if (compareString(command, COMMAND_UPDATE))
			return CommandType.UPDATE;
		else if (compareString(command, COMMAND_SEARCH))
			return CommandType.SEARCH;
		else if (compareString(command, COMMAND_EXIT))
			return CommandType.EXIT;
		else if (compareString(command, COMMAND_DISPLAY))
			return CommandType.DISPLAY;
		else if (compareString(command, COMMAND_UNDO))
			return CommandType.UNDO;
		else if (compareString(command, OPTION_ADD))
			return CommandType.O_ADD;
		else if (compareString(command, OPTION_FIND))
			return CommandType.O_FIND;
		else if (compareString(command, OPTION_UNDO))
			return CommandType.UNDO;
		else if (compareString(command, OPTION_OTHERS))
			return CommandType.O_OTHERS;
		else if (compareString(command, OPTION_EXIT))
			return CommandType.EXIT;
		else if (compareString(command, OPTION_DISPLAY))
			return CommandType.DISPLAY;
		else
			return CommandType.INVALID;
	}

	private static boolean compareString(String text, String text2) {
		return (text.equalsIgnoreCase(text2));
	}

	private static String removeCommand(String instruc) {
		return instruc.replace(getCommand(instruc), "").trim();
	}

	private static String getCommand(String instruc) {
		String commandTypeString = instruc.trim().split("\\s+")[0];
		return commandTypeString;
	}
}

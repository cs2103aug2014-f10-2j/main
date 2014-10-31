package com.epictodo.engine;

import com.epictodo.logic.CRUDLogic;
import com.epictodo.model.Task;

import java.util.ArrayList;

// user input = { command + instruction }
public class WorkDistributor {
	static CRUDLogic logic = new CRUDLogic();
	private final static String[] COMMAND_EXIT = {"exit", "quit"};
	private final static String[] COMMAND_ADD = {"add", "create"};
	private final static String[] COMMAND_UPDATE = {"update", "change", "modify"};
	private final static String[] COMMAND_DELETE = {"delete", "remove"};
	private final static String[] COMMAND_SEARCH = {"search","find"};
	private final static String[] COMMAND_DISPLAY = {"display"};	
	private final static String[] COMMAND_UNDO = {"undo"};
	enum CommandType {
		DISPLAY, ADD, DELETE, UPDATE, SEARCH, EXIT, INVALID, NULL, UNDO
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
			Task updatedTask = MenuWorker.updateTask(t);
			result = logic.updateTask(t, updatedTask); 
			return result;
			
		case SEARCH:
			list = logic.getTasksByName(instruc);
			t = MenuWorker.selectItemFromList(command, list,
					logic.displayList(list));
			// todo: display task in a proper format return successful message
			// in String
			return "";

		case EXIT:
			System.exit(0);
			break;
			
		case INVALID:
			// todo: defined all invalid cases
			return "This is invalid";
			
		case UNDO:
			return result;

		}

		// todo handle invalid input here
		return null;
	}

	private static CommandType defineCommandType(String instruc) {
		String command = getCommand(instruc);
		if (compareString(command, ""))
			return CommandType.NULL;
		else if (identifyCommand(command, COMMAND_ADD)){
			return CommandType.ADD;
		}else if (identifyCommand(command, COMMAND_DELETE)){
			return CommandType.DELETE;
		}else if (identifyCommand(command, COMMAND_UPDATE)){
			return CommandType.UPDATE;
		}else if (identifyCommand(command, COMMAND_SEARCH)){
			return CommandType.SEARCH;
		}else if (identifyCommand(command, COMMAND_DISPLAY)){
			return CommandType.DISPLAY;
		}else if (identifyCommand(command, COMMAND_EXIT)){
			return CommandType.EXIT;
		}else if (identifyCommand(command, COMMAND_UNDO)){
			return CommandType.UNDO;
		}else
			return CommandType.INVALID;
	}
	
	private static boolean identifyCommand(String command, final String[] vocabs){
		for (int i=0; i<vocabs.length; i++){
			if (compareString(command, vocabs[i])){
				return true;
			}
		}
		return false;
	}

	public static boolean loadData(){
		try{
			return logic.loadFromFile();
		}
		catch(Exception ex){
			return false;
		}
	}
	private static boolean compareString(String text, String text2) {
		return (text.equalsIgnoreCase(text2));
	}

	private static String removeCommand(String instruc) {
		return instruc.substring(getCommand(instruc,true), instruc.length());
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

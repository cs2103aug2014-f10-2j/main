package com.epictodo.engine;

import com.epictodo.logic.CRUDLogic;
import com.epictodo.model.Task;

// user input = { command + instruction }
public class WorkDistributor {
	static CRUDLogic logic = new CRUDLogic();
	private final static String COMMAND_EXIT = "exit";
	private final static String COMMAND_ADD = "add";
	private final static String COMMAND_DELETE = "delete";
	private final static String COMMAND_SEARCH = "search";
	private final static String COMMAND_CLEAR = "clear";
	private final static String COMMAND_DISPLAY = "display";
	private final static String OPTION_ADD = "1";
	private final static String OPTION_FIND = "2";
	private final static String OPTION_DISPLAY= "3";
	private final static String OPTION_UNDO = "4";
	private final static String OPTION_OTHERS = "5";
	private final static String OPTION_EXIT = "6";
	
	enum CommandType{
		DISPLAY, ADD, DELETE, CLEAR, SEARCH, EXIT, INVALID, NULL, O_ADD, O_FIND, 
		O_DISPLAY, O_UNDO, O_OTHERS
	};
	
	
	public static String proceedInstruc(String instruc) {
		
		//get command
		CommandType command = defineCommandType(instruc);
		
		//read instruction
		instruc = removeCommand(instruc);
		String result = "";
		Task t = null;
		switch(command){
		case DISPLAY:
			return logic.displayAllTaskList();
			
		case ADD:
			t = CommandWorker.createTask(instruc);
			result = logic.createTask(t);
			return result;
			
		case DELETE:
			return result;
		case CLEAR:
			return result;
		case SEARCH:
			return result;
		case EXIT:
			System.exit(0);
			break;
		case INVALID:
			break;
		case O_ADD:
			t =  MenuWorker.addMenu();
			result = logic.createTask(t);
			return result;
			
		case O_FIND:
			return result;
			
		case O_UNDO:
			return result;
			
		case O_OTHERS:
			return result;
		}
		
		
		return null;
	}
	
	public static CommandType defineCommandType(String instruc){
		String command = getCommand(instruc);
		if (compareString(command,""))
			return CommandType.NULL;
		else if (compareString(command,COMMAND_ADD))
			return CommandType.ADD;
		else if (compareString(command,COMMAND_DELETE))
			return CommandType.DELETE;
		else if (compareString(command,COMMAND_CLEAR))
			return CommandType.CLEAR;
		else if (compareString(command,COMMAND_SEARCH))
			return CommandType.SEARCH;
		else if (compareString(command,COMMAND_EXIT))
			return CommandType.EXIT;	
		else if (compareString(command,COMMAND_DISPLAY))
			return CommandType.DISPLAY;
		else if (compareString(command,OPTION_ADD))
			return CommandType.O_ADD;
		else if (compareString(command,OPTION_FIND))
			return CommandType.O_FIND;
		else if (compareString(command,OPTION_UNDO))
			return CommandType.O_UNDO;
		else if (compareString(command,OPTION_OTHERS))
			return CommandType.O_OTHERS;
		else if (compareString(command,OPTION_EXIT))
			return CommandType.EXIT;	
		else if (compareString(command,OPTION_DISPLAY))
			return CommandType.DISPLAY;
		else
			return CommandType.INVALID;
	}
	
	private static boolean compareString(String text, String text2){
		return (text.equalsIgnoreCase(text2));
	}

	public static String removeCommand(String instruc) {
		return instruc.replace(getCommand(instruc), "").trim();
	}
	
	public static String getCommand(String instruc) {
		String commandTypeString = instruc.trim().split("\\s+")[0];
		return commandTypeString;
	}
}

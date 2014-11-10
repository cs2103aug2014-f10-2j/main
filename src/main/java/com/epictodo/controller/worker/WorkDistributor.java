//@author A0112918H
package com.epictodo.controller.worker;

import java.util.ArrayList;

import com.epictodo.controller.logic.CRUDLogic;
import com.epictodo.model.task.Task;

public class WorkDistributor {
    private static CRUDLogic _logic = new CRUDLogic();
    private final static String[] COMMAND_EXIT = {"exit", "quit"};
    private final static String[] COMMAND_ADD = {"add", "create","+"};
    private final static String[] COMMAND_UPDATE = {"update", "change", "modify"};
    private final static String[] COMMAND_DELETE = {"delete", "remove", "-"};
    private final static String[] COMMAND_SEARCH = {"search", "find", "ls"};
    private final static String[] COMMAND_DISPLAY = {"display", "upcoming"};
    private final static String[] COMMAND_DISPLAYALL = {"all", "displayall", "showall"};
    private final static String[] COMMAND_UNDO = {"undo", "revert"};
    private final static String[] COMMAND_REDO = {"redo"};
    private final static String[] COMMAND_DONE = {"done", "mark"};
    private final static String[] COMMAND_HELP = {"help","?"};
    
	private static final String MSG_HELP = "insert [Command]+[Instruction]\nCommandTypes are:\n\t\t1.Add/Create\n\t\t2.Search/Find\n\t\t3.Update/Change/Modify\n\t\t4.Display/Upcoming\n\t\t5.Undo/Revert\n\t\t6.Redo\n\t\t7.Displayall";
    private final static String MSG_INVALID_INPUT = "invalid input";

    enum CommandType {
        DISPLAY, DISPLAYALL, ADD, DELETE, UPDATE, SEARCH, EXIT, INVALID, NULL, UNDO, REDO, DONE, HELP
    }

    enum KeywordType {
        WORD, TIME, OPTION
    }

    /**
     * Return true if the storage is detected and loaded
     * If error or files not found, false is returned.
     *
     * @param zone Zone of position.
     * @return Lateral location.
     * @throws IllegalArgumentException If zone is <= 0.
     */
    public static boolean loadData() {
        try {
            return _logic.loadFromFile();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Return message after every command is operated
     * If command is not operated successfully, "invalid input" is returned.
     *
     * @param input User input
     * @return Operation result Message.
     */
    public static String processCommand(String input) {
        String result = "";
        ArrayList<Task> list = null;
        Task t = null;
        // Clear expired timed tasks
        _logic.clearExpiredTask();

        CommandType command = defineCommandType(input);
        input = getInstruction(input);

        switch (command) {
            case DISPLAY:
                return _logic.displayIncompleteTaskList();
            case DISPLAYALL:
                return _logic.displayAllTaskList();
            case ADD:
                t = CommandWorker.createTask(input);
                result = _logic.createTask(t);
                return result;

            case DELETE:
            case DONE:
            case UPDATE:
            case SEARCH:

                list = searchThroughKeywords(input);
                if (list.size() == 0) {
                    return "Cannnot find '" + input + "'";
                }
                result = selectItemProcess(list, command);
                return result;

            case EXIT:
                System.exit(0);
                break;
            case UNDO:
                result = _logic.undoMostRecent();
                return result;
            case REDO:
            	result = _logic.redoMostRecent();
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

    /**
     * Calls MenuWorker to prompt for user input
     * Return operation result message
     *
     * @param list        list of possible option from the search result
     * @param commandType Defined user command type
     * @return result message.
     */
    private static String selectItemProcess(ArrayList<Task> list, CommandType commandType) {
        String result = null;
        Task tempTask = null;
        try {
            tempTask = MenuWorker.selectItemFromList(commandType, list,
                    _logic.convertListToString(list));
        } catch (IndexOutOfBoundsException iobe) {
            return MSG_INVALID_INPUT;
        }
        result = processCommand(commandType, tempTask);
        return result;
    }

    /**
     * Calls CRUDLogic class to process the task
     * Return proper result message given from CRUDLogic
     *
     * @param command System defined command type
     * @param task    Y coordinate of position.
     * @return Operation result message
     */
    private static String processCommand(CommandType command, Task task) {
        String result = "";
        switch (command) {
            case DELETE:
                result = _logic.deleteTask(task);
                break;
            case DONE:
                result = _logic.markAsDone(task);
                break;
            case UPDATE:
                Task updatedTask = MenuWorker.updateTask(task);
                if (updatedTask != null) {
                    result = _logic.updateTask(task, updatedTask);
                }
                break;
            case SEARCH:
                result = _logic.searchDetail(task);
                break;
            case HELP:
            	result = MSG_HELP;
            default:
                break;
        }
        return result;
    }

    /**
     * Calls CRUDLogic search by using the keywords
     * Return a list of tasks from the searches
     *
     * @param keyword the key words from user input
     * @return list of possible tasks base on the search result.
     */
    private static ArrayList<Task> searchThroughKeywords(String keyword) {
        ArrayList<Task> list = new ArrayList<Task>();
        Task tempTask = _logic.translateWorkingListId(keyword);
        String date= null;
        if(keyword.length()!=1){
        	date = CommandWorker.getDateViaNlp(keyword);
        }
        KeywordType keywordType = getKeywordType(tempTask, date);

        switch (keywordType) {
            case WORD:
                list = _logic.getTasksByName(keyword);
                break;
            case TIME:
                list = _logic.getTasksByDate(date);
                break;
            case OPTION:
                list.add(tempTask);
                break;
        }
        return list;
    }

    /**
     * Return CommandType base on the command given from the input
     *
     * @param input user input
     * @return Command type
     */
    private static CommandType defineCommandType(String input) {
        //retrieve command key from the user input
        String command = getCommand(input);
        //match them with proper command type
        if (compareString(command, ""))
            return CommandType.NULL;
        else if (matchCommand(command, COMMAND_ADD)) {
            return CommandType.ADD;
        } else if (matchCommand(command, COMMAND_DELETE)) {
            return CommandType.DELETE;
        } else if (matchCommand(command, COMMAND_UPDATE)) {
            return CommandType.UPDATE;
        } else if (matchCommand(command, COMMAND_SEARCH)) {
            return CommandType.SEARCH;
        } else if (matchCommand(command, COMMAND_DISPLAY)) {
            return CommandType.DISPLAY;
        } else if (matchCommand(command, COMMAND_DISPLAYALL)) {
            return CommandType.DISPLAYALL;
        } else if (matchCommand(command, COMMAND_EXIT)) {
            return CommandType.EXIT;
        } else if (matchCommand(command, COMMAND_UNDO)) {
            return CommandType.UNDO;
        } else if (matchCommand(command, COMMAND_REDO)) {
            return CommandType.REDO;
        } else if (matchCommand(command, COMMAND_DONE)) {
            return CommandType.DONE;
        } else if (matchCommand(command, COMMAND_HELP)){
        	return CommandType.HELP;
        } else {
            return CommandType.INVALID;
        }
    }

    /**
     * Return true when the command matches with the vocab or its synonyms
     * if it does not match with each other, false is returned
     *
     * @param command System define command.
     * @param vocabs  command key and its synonyms
     * @return Boolean.
     */
    private static boolean matchCommand(String command, final String[] vocabs) {
        for (int i = 0; i < vocabs.length; i++) {
            if (compareString(command, vocabs[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if both strings are the same
     *
     * @param text1 First String to be compared.
     * @param text2 Second String to be compared.
     * @return true/false
     */
    private static boolean compareString(String text1, String text2) {
        return (text1.equalsIgnoreCase(text2));
    }

    /**
     * Return instruction by removing command from user input
     *
     * @param input user input
     * @return instruction
     * @throws IllegalArgumentException If zone is <= 0.
     */
    private static String getInstruction(String input) {
        return input.substring(getCommandLength(input), input.length()).trim();
    }

    /**
     * Return command length.
     *
     * @param instruc user's input.
     * @return commands length.
     */
    private static int getCommandLength(String instruc) {
        String commandTypeString = instruc.trim().split("\\s+")[0];
        return commandTypeString.length();
    }

    /**
     * Return command type.
     *
     * @param instruc user's input.
     * @return commands.
     */
    private static String getCommand(String instruc) {
        String commandTypeString = instruc.trim().split("\\s+")[0];
        return commandTypeString;
    }
    /**
     * return keyword type 
     *
     * @param task      Task from the option given from user.
     * @param date		date given from NLP.
     * @return Keyword Type.
     */
    private static KeywordType getKeywordType(Task task, String date) {
        if (task != null) {
            return KeywordType.OPTION;
        }else if (date != null) {
            return KeywordType.TIME;
        } else {
            return KeywordType.WORD;
        }
    }
}

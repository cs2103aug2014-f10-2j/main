//@author A0112918H
package com.epictodo.engine;

import com.epictodo.engine.WorkDistributor.CommandType;
import com.epictodo.model.*;
import com.epictodo.model.exception.InvalidDateException;
import com.epictodo.model.exception.InvalidTimeException;
import com.epictodo.util.TaskBuilder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuWorker {
    private static final String LOG_SELECTED_TASK = "Task, %s is retrieved";
	private static final String INSTRUCTION_ENTER_NAME = "Name ( %s ):";
	private static final String INSTRUCTION_ENTER_DESCRIPTION = "Description ( %s ):";
	private static final String INSTRUCTION_ENTER_PRIORITY = "priority ( %s ):";
	private static final String INSTRUCTION_ENTER_START_DATE = "start Date ( %s ):";
	private static final String INSTRUCTION_ENTER_START_TIME = "start Time ( %s ):";
	private static final String INSTRUCTION_ENTER_ENDTIME = "end Time ( %s ):";
	private static final String INSTRUCTION_ENTER_ENDDATE = "end Date ( %s ):";
	private static final String INSTRUCTION_ENTER_DURATION = "duration in hours ( %s ):";
	private static final String INSTRUCTION_SELECT_UPDATE_OPTION = "Enter your option to be updated (or 0 to menu): ";
    private static final String INSTRUCTION_SELECT_DELETE_OPTION = "Enter your option to be deleted (or 0 to menu): ";
    private static final String INSTRUCTION_SELECT_MARK_OPTION = "Enter your option to be marked (or 0 to menu): ";
    private static final String INSTRUCTION_SELECT_SEARCH_OPTION = "Enter your option for details (or 0 to menu): ";
    
	private static final String MSG_UPDATE_INSTRUCTION = "---------------------------------\nplease update info or press [enter] to remain unchange\n---------------------------------";

    private static Logger _logger = Logger.getLogger("System Menu Log");
    private static Scanner _sc = null;

    /**
     * This method displays the task list information and
     * prompt user to select from them.
     * Ultimately, this will return the selected task or the only task
     * If there is no task found in the list, it return null
     * 
     * @param type	CommandType
     * @param list List to select.
     * @param String to be displayed to the user
     * @return Task
     */
    public static Task selectItemFromList(CommandType type, ArrayList<Task> list, String items) throws IndexOutOfBoundsException {
        if (list.size() == 1) {
            return list.get(0);
        }

        _sc = new Scanner(System.in);
        displaySelectInstruction(type, items);

        int option = retrieveInputOption();
        if (option == 0) {
            return null;
        }
        Task t = list.get(option - 1);
        _logger.info(String.format(LOG_SELECTED_TASK,t.getTaskName()));
        return t;
    }

    /**
     * This method prompt user to update their task
     * Return the updatedTask Once the task is done 
     * 
     * @param T original task
     * @return updatedTask
     */
    public static Task updateTask(Task t) {
        if (t == null) return null;
        Task updatedTask = null;
        String taskName, taskDesc, taskStartDate, taskStartTime, taskEndDate, taskEndTime;
        int taskPriority;
        double taskDuration;
        _sc = new Scanner(System.in);
        taskName = updateTaskName(t);
        taskDesc = updateTaskDesc(t);
        taskPriority = updateTaskPriority(t);

        try {
            if (t instanceof TimedTask) {
                taskStartDate = updateStartDate(t);
                taskStartTime = updateStartTime(t);
                taskDuration = updateTaskDuration(t);
                updatedTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority, taskStartDate, taskStartTime, taskDuration);

            } else if (t instanceof DeadlineTask) {
                taskEndDate = updateTaskEndDate(t);
                taskEndTime = updateTaskEndTime(t);
                updatedTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority, taskEndDate, taskEndTime);

            } else if (t instanceof FloatingTask) {
                updatedTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority);
            }
            if(updatedTask !=null) {
            	updatedTask.setUid(t.getUid());
            }
        } catch (InvalidDateException ide) {
            displayLine("invalid Date");
        } catch (InvalidTimeException ite) {
            displayLine("invalid Time");
        }
        return updatedTask;
    }
    
    /**  
     * This method displays instruction to select item
     * 
     * @param type	CommandType
     * @param items String to be displayed to the user 
     */
    private static void displaySelectInstruction(CommandType type, String items) {
        // print out the possible result
        displayLine(items);
        if (type == CommandType.DELETE) {
            display(INSTRUCTION_SELECT_DELETE_OPTION);
        } else if (type == CommandType.UPDATE) {
            display(INSTRUCTION_SELECT_UPDATE_OPTION);
        } else if (type == CommandType.SEARCH) {
            display(INSTRUCTION_SELECT_SEARCH_OPTION);
        } else if (type == CommandType.DONE) {
            display(INSTRUCTION_SELECT_MARK_OPTION);
        }
    }

    /**
     * This method read user's input
     * 
     * @return user input
     */
    private static int retrieveInputOption() {
        int option = 0;
        try {
            option = _sc.nextInt();
        } catch (Exception e) {
            return -2;
        }

        return option;
    }

    /**
     * update task's end time
     * 
     * @param t Task
     * @return endtime String
     */
    private static String updateTaskEndTime(Task t) {
        display(String.format(INSTRUCTION_ENTER_ENDTIME, ((DeadlineTask) t).getTime()));
        String endTime = getUpdatedInfo(_sc, ((DeadlineTask) t).getTime());
        return endTime;
    }

    /**
     * update task's end Date
     * 
     * @param t Task
     * @return endDate String
     */
    private static String updateTaskEndDate(Task t) {
        display(String.format(INSTRUCTION_ENTER_ENDDATE, ((DeadlineTask) t).getDate()));
        String endDate = getUpdatedInfo(_sc, ((DeadlineTask) t).getDate());
        return endDate;
    }

    /**
     * update task's duration
     * 
     * @param t Task
     * @return Duration double
     */
    private static double updateTaskDuration(Task t) {
        double taskDuration;
        display(String.format(INSTRUCTION_ENTER_DURATION, ((TimedTask) t).getDuration()));
        String d = getUpdatedInfo(_sc, String.valueOf(((TimedTask) t).getDuration()));
        taskDuration = Double.valueOf(d);
        return taskDuration;
    }

    /**
     * update task's start time
     * 
     * @param t Task
     * @return endtime String
     */
    private static String updateStartTime(Task t) {
        String startTime;
        display(String.format(INSTRUCTION_ENTER_START_TIME, ((TimedTask) t).getStartTime()));
        startTime = getUpdatedInfo(_sc, ((TimedTask) t).getStartTime());
        return startTime;
    }

    /**
     * update task's start date
     * 
     * @param t Task
     * @return startdate String
     */
    private static String updateStartDate(Task t) {
        String startDate;
        display(String.format(INSTRUCTION_ENTER_START_DATE, ((TimedTask) t).getStartDate()));
        startDate = getUpdatedInfo(_sc, ((TimedTask) t).getStartDate());
        return startDate;
    }

    /**
     * update task's priority
     * 
     * @param t Task
     * @return priority String
     */
    private static int updateTaskPriority(Task t) {
        display(String.format(INSTRUCTION_ENTER_PRIORITY, t.getPriority()));
        String p = getUpdatedInfo(_sc, String.valueOf(t.getPriority()));
        try {
            int taskPriority = Integer.valueOf(p);
            return taskPriority;
        } catch (Exception e) {
            return t.getPriority();
        }
    }

    /**
     * update task's Description
     * 
     * @param t Task
     * @return Description String
     */
    private static String updateTaskDesc(Task t) {
        display(String.format(INSTRUCTION_ENTER_DESCRIPTION, t.getTaskDescription()));
        String taskDesc = getUpdatedInfo(_sc, t.getTaskDescription());
        return taskDesc;
    }

    /**
     * update task's name
     * 
     * @param t Task
     * @return name String
     */
    private static String updateTaskName(Task t) {
        displayLine(MSG_UPDATE_INSTRUCTION);
        display(String.format(INSTRUCTION_ENTER_NAME, t.getTaskName()));
        String taskName = getUpdatedInfo(_sc, t.getTaskName());
        return taskName;
    }

    /**
     * update field or remain unchanged
     * 
     * @param t Task
     * @return updated String String
     */
    private static String getUpdatedInfo(Scanner s, String unchanged) {
        String update = s.nextLine();
        if (update == null || update.equals("")) {
            update = unchanged;
        }
        return update;
    }

    private static void displayLine(String a) {
        System.out.println(a);
    }

    private static void display(String a) {
        System.out.print(a);
    }
}

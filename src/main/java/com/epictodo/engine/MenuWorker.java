//@author A0112918H
package com.epictodo.engine;

import com.epictodo.engine.WorkDistributor.CommandType;
import com.epictodo.model.*;
import com.epictodo.util.TaskBuilder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuWorker {
    private final static String MENU_SELECT_UPDATE_OPTION = "Enter your option to be updated (or 0 to menu): ";
    private final static String MENU_SELECT_DELETE_OPTION = "Enter your option to be deleted (or 0 to menu): ";
    private final static String MENU_SELECT_MARK_OPTION = "Enter your option to be marked (or 0 to menu): ";
    private final static String MENU_SELECT_SEARCH_OPTION = "Enter your option for details (or 0 to menu): ";

    static Logger _logger = Logger.getLogger("System Menu Log");
    static Scanner _sc = null;

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
        return t;
    }

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

    private static void displaySelectInstruction(CommandType type, String items) {
        // print out the possible result
        displayLine(items);
        if (type == CommandType.DELETE) {
            display(MENU_SELECT_DELETE_OPTION);
        } else if (type == CommandType.UPDATE) {
            display(MENU_SELECT_UPDATE_OPTION);
        } else if (type == CommandType.SEARCH) {
            display(MENU_SELECT_SEARCH_OPTION);
        } else if (type == CommandType.DONE) {
            display(MENU_SELECT_MARK_OPTION);
        }
    }

    private static int retrieveInputOption() {
        int option = 0;
        try {
            option = _sc.nextInt();
        } catch (Exception e) {
            return -2;
        }

        return option;
    }

    private static String updateTaskEndTime(Task t) {
        display(String.format("end Time ( %s ):", ((DeadlineTask) t).getTime()));
        String endTime = getUpdatedInfo(_sc, ((DeadlineTask) t).getTime());
        return endTime;
    }

    private static String updateTaskEndDate(Task t) {
        display(String.format("end Date ( %s ):", ((DeadlineTask) t).getDate()));
        String endDate = getUpdatedInfo(_sc, ((DeadlineTask) t).getDate());
        return endDate;
    }

    private static double updateTaskDuration(Task t) {
        double taskDuration;
        display(String.format("duration in hours ( %s ):", ((TimedTask) t).getDuration()));
        String d = getUpdatedInfo(_sc, String.valueOf(((TimedTask) t).getDuration()));
        taskDuration = Double.valueOf(d);
        return taskDuration;
    }

    private static String updateStartTime(Task t) {
        String startTime;
        display(String.format("start Time ( %s ):", ((TimedTask) t).getStartTime()));
        startTime = getUpdatedInfo(_sc, ((TimedTask) t).getStartTime());
        return startTime;
    }

    private static String updateStartDate(Task t) {
        String startDate;
        display(String.format("start Date ( %s ):", ((TimedTask) t).getStartDate()));
        startDate = getUpdatedInfo(_sc, ((TimedTask) t).getStartDate());
        return startDate;
    }

    private static int updateTaskPriority(Task t) {
        display(String.format("priority ( %s ):", t.getPriority()));
        String p = getUpdatedInfo(_sc, String.valueOf(t.getPriority()));
        try {
            int taskPriority = Integer.valueOf(p);
            return taskPriority;
        } catch (Exception e) {
            return t.getPriority();
        }
    }

    private static String updateTaskDesc(Task t) {
        display(String.format("Description ( %s ):", t.getTaskDescription()));
        String taskDesc = getUpdatedInfo(_sc, t.getTaskDescription());
        return taskDesc;
    }

    private static String updateTaskName(Task t) {
        displayLine("please enter the updated info or press enter to remain unchange");
        display(String.format("Name ( %s ):", t.getTaskName()));
        String taskName = getUpdatedInfo(_sc, t.getTaskName());
        return taskName;
    }

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

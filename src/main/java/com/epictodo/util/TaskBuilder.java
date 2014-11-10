
package com.epictodo.util;

import com.epictodo.model.exception.InvalidDateException;
import com.epictodo.model.exception.InvalidTimeException;
import com.epictodo.model.task.DeadlineTask;
import com.epictodo.model.task.FloatingTask;
import com.epictodo.model.task.Task;
import com.epictodo.model.task.TimedTask;


public class TaskBuilder {


    public static Task buildTask(String taskName, String taskDescription, int priority,
                                 String ddmmyy, String time, double duration) throws InvalidDateException, InvalidTimeException {

        Task tt = null;
        tt = new TimedTask(taskName, taskDescription, priority, ddmmyy, time, duration);
        return tt;
    }

    public static Task buildTask(String taskName, String taskDescription, int priority, String ddmmyy, String time) throws InvalidDateException, InvalidTimeException {
        Task dt=null;
        dt = new DeadlineTask(taskName, taskDescription, priority, ddmmyy, time);
        return dt;
    }

    public static Task buildTask(String taskName, String taskDescription, int priority) {
        Task ft= null;
        ft = new FloatingTask(taskName, taskDescription, priority);
        return ft;
    }

}

package com.epictodo.util;

import com.epictodo.model.*;

/*
    "start_date_time": 1412907000,
    "end_date_time": 1412914200,
    "task_name": "project meeting 2103",
    "task_description": "Nothing much",
    "priority": 2,
    "is_done": false
	HashMap<String, String> memory = new HashMap<>();
 */

public class TaskBuilder {


    public static Task buildTask(String taskName, String taskDescription, int priority,
                                 String ddmmyy, String time, double duration) throws InvalidDateException, InvalidTimeException {

        Task tt = null;
        try{
        tt = new TimedTask(taskName, taskDescription, priority, ddmmyy, time, duration);
        } catch(Exception e){}
        return tt;
    }

    public static Task buildTask(String taskName, String taskDescription, int priority, String ddmmyy, String time) throws InvalidDateException, InvalidTimeException {
        Task dt=null;
        try{
        dt = new DeadlineTask(taskName, taskDescription, priority, ddmmyy, time);
        } catch(Exception e){}
        return dt;
    }

    public static Task buildTask(String taskName, String taskDescription, int priority) {
        Task ft= null;
        try{
        ft = new FloatingTask(taskName, taskDescription, priority);
        } catch(Exception e){}
        return ft;
    }

}

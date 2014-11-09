package com.epictodo.engine;

import com.epictodo.model.InvalidDateException;
import com.epictodo.model.InvalidTimeException;
import com.epictodo.model.Response;
import com.epictodo.model.Task;
import com.epictodo.util.TaskBuilder;

import java.text.ParseException;
import java.util.logging.Logger;

public class CommandWorker {
    private static final int CAPACITY = 100;
    private static NLPEngine nlp_engine = NLPEngine.getInstance();
    private static Response _response = new Response();

    /*
     * This is the helper class to retrieve value from instruction
     * This class will call TaskBuilder to return a proper task.
     */
    public static Task createTask(String instruc) {
        boolean is_first = true;
        Logger logger = Logger.getLogger("System Log");
        Task newTask = null;

        try {
            _response = nlp_engine.flexiAdd(instruc);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        StringBuilder taskn_builder = new StringBuilder(CAPACITY);
        StringBuilder taskd_builder = new StringBuilder(CAPACITY);

        // NLP can replace the work below
        for (String task_name : _response.getTaskName()) {
            if (is_first) {
                is_first = false;
            } else {
                taskn_builder.append(' ');
            }

            taskn_builder.append(task_name);
        }

        String taskName = taskn_builder.toString();

        for (String task_desc : _response.getTaskDesc()) {
            if (is_first) {
                is_first = false;
            } else {
                taskd_builder.append(' ');
            }

            taskd_builder.append(task_desc);
        }

        String taskDesc = taskd_builder.toString();

        String taskDate = _response.getTaskDate();
        String taskTime = _response.getTaskTime();
        int taskPriority = _response.getPriority();
        double taskDuration = _response.getTaskDuration();

        try{
        if (taskName.equals("")) {
            logger.info("invalid command!");
        }else if (taskDate == null) {
            logger.info("floating task is created!");
            newTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority);
        }else if (taskDuration > 0) {
            // Timed Task
            logger.info("Timed task is created!");
            newTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority, taskDate, taskTime, taskDuration);
        }

        // Deadline Task
        newTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority, taskDate, taskTime);
        }catch(InvalidTimeException ite){
        	
        }catch(InvalidDateException ide){
        	
        }
        	return newTask;

    }
/*
    public static Task updateTask(String instruc) {
        Logger logger = Logger.getLogger("System Log");
        Task newTask = null;
        //NLP take place here!
        Scanner s = new Scanner(instruc);
        String taskName = getTaskNameThroughInstruction(s);
        String taskDate = getTaskDateThroughInstruction(s);
        String taskTime = getTaskTimeThroughInstruction(s);
        double taskDuration = getTaskDurationThroughInstruction(s);
        String taskDesc = "";

        if (taskName == null) {
            logger.info("invalid command!");
        }
        if (taskDate == null) {
            logger.info("floating task is created!");
            newTask= TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority);
        }
        if (taskTime == null) {
            //Deadline Task (default end time)
            newTask=  TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, _defaultTime);
        }
        if (taskDuration != -1) {
            // Timed Task
            newTask= TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, taskTime, taskDuration);
        }
        // Deadline Task
        newTask= TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, taskTime);
        
        return newTask;
    }
*/


}

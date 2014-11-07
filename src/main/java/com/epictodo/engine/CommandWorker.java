package com.epictodo.engine;

import com.epictodo.model.Response;
import com.epictodo.model.Task;
import com.epictodo.util.TaskBuilder;

import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Logger;

public class CommandWorker {
    private static int _defaultPriority = 2;
    private static final int CAPACITY = 100;
    private static String _defaultTime = "09:00";
    private static NLPEngine nlp_engine = NLPEngine.getInstance();
    private static Response _response = new Response();

    /*
     * This is the helper class to retrieve value from instruction
     * This class will call TaskBuilder to return a proper task.
     */
    public static Task createTask(String instruc) {
        boolean is_first = true;
        Logger logger = Logger.getLogger("System Log");

        try {
            _response = nlp_engine.flexiAdd(instruc);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        StringBuilder taskn_builder = new StringBuilder(CAPACITY);
        StringBuilder taskd_builder = new StringBuilder(CAPACITY);

        // NLP can replace the work below
//		Scanner s = new Scanner(instruc);
//		String taskName = getTaskNameThroughInstruction(s);
//		String taskDate = getTaskDateThroughInstruction(s);
//		String taskTime = getTaskTimeThroughInstruction(s);
//		double taskDuration = getTaskDurationThroughInstruction(s);
//		String taskDesc="";

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

        if (taskName.equals("")) {
            logger.info("invalid command!");
            return null;
        }
        if (taskDate == null) {
            logger.info("floating task is created!");
            return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority);
        }
        if (taskDuration > 0) {
            // Timed Task
            logger.info("Timed task is created!");
            return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, taskTime, taskDuration);
        }
     /*   if (taskTime == null) {
            //Deadline Task (default end time)
            logger.info("DeadLine task with default endtime is created!");
            return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, _defaultTime);
        }
     */
        // Deadline Task
        return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, taskTime);

    }

    public static Task updateTask(String instruc) {
        Logger logger = Logger.getLogger("System Log");

        //NLP take place here!
        Scanner s = new Scanner(instruc);
        String taskName = getTaskNameThroughInstruction(s);
        String taskDate = getTaskDateThroughInstruction(s);
        String taskTime = getTaskTimeThroughInstruction(s);
        double taskDuration = getTaskDurationThroughInstruction(s);
        String taskDesc = "";

        if (taskName == null) {
            logger.info("invalid command!");
            return null;
        }
        if (taskDate == null) {
            logger.info("floating task is created!");
            return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority);
        }
        if (taskTime == null) {
            //Deadline Task (default end time)
            return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, _defaultTime);
        }
        if (taskDuration != -1) {
            // Timed Task
            return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, taskTime, taskDuration);
        }
        // Deadline Task
        return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority, taskDate, taskTime);

    }


    private static double getTaskDurationThroughInstruction(Scanner s) {
        while (s.hasNext()) {
            return s.nextDouble();
        }
        return -1;
    }

    private static String getTaskTimeThroughInstruction(Scanner s) {
        while (s.hasNext()) {
            return s.next();
        }
        return null;
    }

    private static String getTaskDateThroughInstruction(Scanner s) {
        // exception to be added next time
        while (s.hasNext()) {
            return s.next();
        }
        return null;
    }

    private static String getTaskNameThroughInstruction(Scanner s) {
        //exception to be added next time
        String taskName = "";
        while (s.hasNext()) {
            String next = s.next();
            if (next.equals("@"))
                return taskName;
            else
                taskName += (next + " ");
        }
        return taskName.trim();
    }
}

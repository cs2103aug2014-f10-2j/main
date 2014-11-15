//@author A0112918H
package com.epictodo.controller.worker;

import com.epictodo.controller.nlp.NLPEngine;
import com.epictodo.model.exception.InvalidDateException;
import com.epictodo.model.exception.InvalidTimeException;
import com.epictodo.model.nlp.Response;
import com.epictodo.model.nlp.Search;
import com.epictodo.model.task.Task;
import com.epictodo.util.TaskBuilder;

import java.text.ParseException;
import java.util.logging.Logger;

public class CommandWorker {
    private static final String LOG_INVALID_DATE = "invalid date";
    private static final String LOG_INVALID_TIME = "invalid time";
    private static final String LOG_DEADLINETASK = "Creating DeadLine task...";
    private static final String LOG_TIMEDTASK = "Creating Timed task...";
    private static final String LOG_FLOATINGTASK = "Creating floating task...";
    private static final String LOG_INVALID = "invalid command!";
    private static final int CAPACITY = 100;
    private static NLPEngine _nlp_engine = NLPEngine.getInstance();
    private static Response _response;
    private static Search _search;
    private static Logger _logger = Logger.getLogger("System Log");

    /**
     * Return a new Task base on the parsing information.
     * if the instruction cannot be parsed, return null
     *
     * @param instruction user input without command.
     * @return Task
     */
    public static Task createTask(String instruction) {
        Task newTask = null;
        _response = new Response();
        try {
            _response = _nlp_engine.flexiAdd(instruction);
        } catch (Exception ex) {
            _logger.info("Unable to parse user's input!");
            return newTask;
        }
        // NLP can replace the work below
        String taskName = getTaskNameViaNlp();
        String taskDesc = getTaskDescViaNlp();
        String taskDate = _response.getTaskDate();
        String taskTime = _response.getTaskTime();
        String startTime = _response.getStartTime();
        int taskPriority = _response.getPriority();
        double taskDuration = _response.getTaskDuration();

        try {
            if (taskName.equals("") || taskName == null) {
                _logger.info(LOG_INVALID);
            } else if (taskDate == null) {
                // Floating Task
                _logger.info(LOG_FLOATINGTASK);
                newTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority);
            } else if (taskDuration > 0) {
                // Timed Task
                _logger.info(LOG_TIMEDTASK);
                newTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority, taskDate, startTime, taskDuration);
            } else if (taskDate != null) {
                // Deadline Task
                _logger.info(LOG_DEADLINETASK);
                newTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority, taskDate, taskTime);
            }
        } catch (InvalidTimeException ite) {
            _logger.info(LOG_INVALID_TIME);
        } catch (InvalidDateException ide) {
            _logger.info(LOG_INVALID_DATE);
        }

        return newTask;
    }

    /**
     * Return keyword into ddmmyy format
     * If Nlp define keyword isn't any date, return null
     *
     * @param keyword keyword from search
     * @return result String as ddmmyy.
     */
    public static String getDateViaNlp(String keyword) {
        _search = new Search();
        String ddmmyy;

        try {
            _search = _nlp_engine.flexiSearch(keyword);
        } catch (ParseException ex) {
            _logger.info(LOG_INVALID);
            return null;
        }

        ddmmyy = _search.getSearchDate();

        return ddmmyy;
    }

    /**
     * return a string of task name base on NLP info
     *
     * @return task name.
     */
    private static String getTaskNameViaNlp() {
        StringBuilder taskn_builder = new StringBuilder(CAPACITY);
        boolean is_first = true;
        for (String task_name : _response.getTaskName()) {
            if (is_first) {
                is_first = false;
            } else {
                taskn_builder.append(' ');
            }

            taskn_builder.append(task_name);
        }
        return taskn_builder.toString();
    }

    /**
     * return a string of task description base on NLP info
     *
     * @return task description.
     */
    private static String getTaskDescViaNlp() {

        StringBuilder taskd_builder = new StringBuilder(CAPACITY);
        boolean is_first = true;

        for (String task_desc : _response.getTaskDesc()) {
            if (is_first) {
                is_first = false;
            } else {
                taskd_builder.append(' ');
            }

            taskd_builder.append(task_desc);
        }
        return taskd_builder.toString();
    }
}

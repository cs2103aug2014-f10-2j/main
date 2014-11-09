package com.epictodo.engine;

import com.epictodo.engine.WorkDistributor.KeywordType;
import com.epictodo.model.*;
import com.epictodo.util.TaskBuilder;

import java.text.ParseException;
import java.util.logging.Logger;

public class CommandWorker {
    private static final String LOG_INVALID_DATE = "invalid date";
	private static final String LOG_INVALID_TIME = "invalid time";
	private static final String LOG_DEADLINETASK = "DeadLine task is created!";
	private static final String LOG_TIMEDTASK = "Timed task is created!";
	private static final String LOG_FLOATINGTASK = "floating task is created!";
	private static final String LOG_INVALID = "invalid command!";
	private static final int CAPACITY = 100;
    private static NLPEngine _nlp_engine = NLPEngine.getInstance();
    private static Response _response = new Response();
    private static Search _search = new Search();
    private static Logger _logger = Logger.getLogger("System Log");

    /*
     * This is the helper class to retrieve value from instruction
     * This class will call TaskBuilder to return a proper task.
     */
    public static Task createTask(String instruc) {
        Task newTask = null;

        try {
            _response = _nlp_engine.flexiAdd(instruc);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        StringBuilder taskn_builder = new StringBuilder(CAPACITY);
        StringBuilder taskd_builder = new StringBuilder(CAPACITY);

        // NLP can replace the work below

        
        String taskName = getTaskNameViaNlp(taskn_builder);
        String taskDesc = getTaskDescViaNlp(taskd_builder);
        String taskDate = _response.getTaskDate();
        String taskTime = _response.getTaskTime();
        int taskPriority = _response.getPriority();
        double taskDuration = _response.getTaskDuration();

        try{
        if (taskName.equals("")) {
            _logger.info(LOG_INVALID);
        }else if (taskDate == null) {
        	// Floating Task
        //    _logger.info(LOG_FLOATINGTASK);
            newTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority);
        }else if (taskDuration > 0) {
            // Timed Task
        //    _logger.info(LOG_TIMEDTASK);
            newTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority, taskDate, taskTime, taskDuration);
        }else if (taskDate!=null){
        	// Deadline Task
        //	_logger.info(LOG_DEADLINETASK);
        	newTask = TaskBuilder.buildTask(taskName, taskDesc, taskPriority, taskDate, taskTime);
        }
        }catch(InvalidTimeException ite){
        	_logger.info(LOG_INVALID_TIME);
        }catch(InvalidDateException ide){
        	_logger.info(LOG_INVALID_DATE);
        }
        	return newTask;

    }

	public static String getDateViaNlp(String keyword) {
		String ddmmyy ="";

        try {
            _search = _nlp_engine.flexiSearch(keyword);
        } catch (ParseException ex) {
            _logger.info(LOG_INVALID);
        }

        ddmmyy = _search.getSearchDate();

		return ddmmyy;
	}
    
	public static KeywordType getKeywordType(String input) {
		if(input !=null){
			return KeywordType.TIME;
		}else{
		return KeywordType.WORD;
		}
	}
	
	private static String getTaskNameViaNlp(StringBuilder taskn_builder){
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
	private static String getTaskDescViaNlp(StringBuilder taskd_builder){
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

package com.epictodo.engine;

import com.epictodo.engine.WorkDistributor.CommandType;
import com.epictodo.model.DeadlineTask;
import com.epictodo.model.FloatingTask;
import com.epictodo.model.Task;
import com.epictodo.model.TimedTask;
import com.epictodo.util.TaskBuilder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuWorker {
	private final static String MENU_SELECT_UPDATE_OPTION ="Enter your option to be updated (or 0 to menu): ";	
	private final static String MENU_SELECT_DELETE_OPTION ="Enter your option to be deleted (or 0 to menu): ";
	private final static String MENU_SELECT_SEARCH_OPTION ="Enter your option for details (or 0 to menu): ";
	private final static String MENU_SEARCH_INSTRUCTION ="Enter keyword: ";

	static Logger logger = Logger.getLogger("System Menu Log");
	private static int _defaultPriority = 2;
	private static String _defaultTime = "09:00";
	static Scanner s = null;
	
	// KW please study this and apply SLAP
	public static Task addMenu() {
		s =new Scanner(System.in);
		String taskName = "";
		String taskDate = "";
		String taskTime = "";
		double taskDuration = -1;
		String taskDesc="";
	   
	    taskName = getTaskName();
	    taskDesc = getTaskDescription();
	    taskDate = getTaskDate();
	    
	    if (taskDate.equals("")){
	    	return createFloatingTask(taskName, taskDesc, _defaultPriority);
	    }
		    taskTime = getTaskTime();
		    if (!taskTime.equals("")){
		    	Display("Enter Task Duration in hours(Optional)");
		    	String durationTemp = s.nextLine();
		    	if (!durationTemp.equals("")){
		    		taskDuration = Double.valueOf(durationTemp);
					logger.info("timed task is created!");
		    		return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority,taskDate, taskTime, taskDuration);
		    	}else{
					logger.info("deadline task is created!");
		    		return TaskBuilder.buildTask(taskName,taskDesc,_defaultPriority,taskDate,taskTime);
		    	}
		    }
			logger.info("deadline task with default time is created!");
		    return TaskBuilder.buildTask(taskName,taskDesc,_defaultPriority,taskDate,_defaultTime);
	}
	
	public static String getTaskName() {
		Display("Enter Task Name: ");
		String taskName = s.nextLine();
		return taskName;
	}
	
	public static String getTaskDescription() {
		 Display("Enter Description(Optional): ");
		    String taskDesc = s.nextLine();
		    return taskDesc;
	}
	
	public static String getTaskDate() {
		Display("Enter Task Date (DDMMYY): ");
	    String taskDate = s.nextLine();
	    return taskDate;
	}
	
	public static String getTaskTime() {
		Display("Enter Task Time: ");
	    String taskTime = s.nextLine();
	    return taskTime;
	}
	
	public static String findMenu(){
		String result = "";
		s= new Scanner(System.in);
		Display(MENU_SEARCH_INSTRUCTION);
		result = s.nextLine();
		return result;
	}
	
	public static Task createFloatingTask(String taskName, String taskDesc, int priority) {
		logger.info("floating task is created!");
		return TaskBuilder.buildTask(taskName, taskDesc, priority);
	}
	/*
	public static Task createTimedTask(String taskName, String taskDesc, int priority, String taskDate, String taskTime, double taskDuration) {
		Display("Enter Task Duration in hours(Optional)");
		String durationTemp = s.nextLine();
		if (!durationTemp.equals("")){
    		taskDuration = Double.valueOf(durationTemp);
			logger.info("timed task is created!");
    		return TaskBuilder.buildTask(taskName, taskDesc, priority, taskDate, taskTime, taskDuration);
    	} else {
				logger.info("deadline task is created!");
				createDeadlineTask(taskName, taskDesc, priority, taskDate, taskTime);
		    	return TaskBuilder.buildTask(taskName, taskDesc, priority, taskDate, taskTime);
		}
	}
	
	public static Task createDeadlineTask(String taskName, String taskDesc, int priority, String taskDate, String taskTime); {
		return TaskBuilder.buildTask(taskName, taskDesc, priority, taskDate, taskTime);
	}
	*/
	public static Task selectItemFromList(CommandType type,ArrayList<Task> list, String items){
		
		s = new Scanner(System.in);
		DisplayLine(items);
		if(type== CommandType.DELETE){
			Display(MENU_SELECT_DELETE_OPTION);
		}else if(type ==CommandType.UPDATE){
			Display(MENU_SELECT_UPDATE_OPTION);
		}else if(type ==CommandType.SEARCH || type == CommandType.O_FIND){
			Display(MENU_SELECT_SEARCH_OPTION);
		}
		int option = s.nextInt();
		if (option== 0){
			return null;
		}
		logger.info(list.get(option-1).getTaskName()+" is selected");
		return list.get(option-1);
	}
	
	
	private static void DisplayLine(String a){
		System.out.println(a);
	}
	
	private static void Display(String a){
		System.out.print(a);
	}

	public static Task updateTask(Task t) {
		
		Display(String.format("Name ( %s ):\n",t.getTaskName()));
		Display(String.format("Description ( %s ):",t.getTaskDescription()));
		Display(String.format("priority ( %s ):",t.getPriority()));
		if (t instanceof TimedTask){
			Display(String.format("start Datetime ( %s ):",((TimedTask) t).getStartDateTimeAsString()));
			Display(String.format("end Datetime ( %s ):",((TimedTask) t).getEndDateTimeAsString()));
		}else if (t instanceof DeadlineTask){
			DeadlineTask tt = (DeadlineTask) t;
		}else if (t instanceof FloatingTask){ 
			FloatingTask tt = (FloatingTask) t;
		}
		return null;
	}
}

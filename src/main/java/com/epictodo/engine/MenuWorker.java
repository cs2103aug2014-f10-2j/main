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
		    		try{
		    		taskDuration = Double.valueOf(durationTemp);
		    		} catch(Exception e) 
		    		{
		    			return null;
		    		}
					logger.info("a timed task is creating!");
		    		return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority,taskDate, taskTime, taskDuration);
		    	}else{
					logger.info("a deadline task is creating!");
		    		return TaskBuilder.buildTask(taskName,taskDesc,_defaultPriority,taskDate,taskTime);
		    	}
		    }
			logger.info("a deadline task with default time is creatng!");
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
		int option = 0;
		DisplayLine(items);
		if(type== CommandType.DELETE){
			Display(MENU_SELECT_DELETE_OPTION);
		}else if(type ==CommandType.UPDATE){
			Display(MENU_SELECT_UPDATE_OPTION);
		}else if(type ==CommandType.SEARCH){
			Display(MENU_SELECT_SEARCH_OPTION);
		}
		try{
			option = s.nextInt();
			if (option== 0){
				return null;
			}
		}
		catch(Exception e){
			return null;
		};

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
		if (t == null) return null;
		Task result =null;
		s = new Scanner(System.in);
		DisplayLine("please enter the updated info or press enter to remain unchange");
		Display(String.format("Name ( %s ):",t.getTaskName()));
		String taskName = getUpdatedInfo(s,t.getTaskName());
		Display(String.format("Description ( %s ):",t.getTaskDescription()));
		String taskDesc = getUpdatedInfo(s,t.getTaskDescription());
		Display(String.format("priority ( %s ):",t.getPriority()));
		String p = getUpdatedInfo(s, String.valueOf(t.getPriority()));
		int taskPriority = Integer.valueOf(p);
		if (t instanceof TimedTask){
			Display(String.format("start Date ( %s ):",((TimedTask) t).getStartDate()));
			String startDate = getUpdatedInfo(s, ((TimedTask) t).getStartDate());
			Display(String.format("start Time ( %s ):",((TimedTask) t).getStartTime()));
			String startTime = getUpdatedInfo(s, ((TimedTask) t).getStartTime());
			Display(String.format("duration in hours ( %s ):",((TimedTask) t).getDuration()));
			String d = getUpdatedInfo(s, String.valueOf(((TimedTask) t).getDuration()));
			double duration = Double.valueOf(d);
			try {
				result =  new TimedTask(taskName,taskDesc,taskPriority,startDate,startTime,duration);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
			}
			result.setUid(t.getUid());
			return result;
		}else if (t instanceof DeadlineTask){
			Display(String.format("end Date ( %s ):",((DeadlineTask) t).getDate()));
			String endDate = getUpdatedInfo(s, ((DeadlineTask) t).getDate());
			Display(String.format("end Time ( %s ):",((DeadlineTask) t).getTime()));
			String endTime = getUpdatedInfo(s, ((DeadlineTask) t).getTime());
			try {
				result =  new DeadlineTask(taskName,taskDesc,taskPriority,endDate,endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
			}
			result.setUid(t.getUid());
			return result;
		}else if (t instanceof FloatingTask){ 
			try {
				result = new FloatingTask(taskName, taskDesc, taskPriority);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
		}
			result.setUid(t.getUid());
			return result;
		}
		try {
			result = new Task(taskName, taskDesc, taskPriority);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		return null;
		}
		result.setUid(t.getUid());
		return result;
	}
	
	private static String getUpdatedInfo(Scanner s, String unchanged){
		String update = s.nextLine();
		if (update == null || update.equals("")){
			update =unchanged;
		}
		return update;
	}
}

package com.epictodo.engine;

import com.epictodo.engine.WorkDistributor.CommandType;
import com.epictodo.model.DeadlineTask;
import com.epictodo.model.FloatingTask;
import com.epictodo.model.Task;
import com.epictodo.model.TimedTask;
import com.epictodo.util.TaskBuilder;

import edu.stanford.nlp.semgraph.semgrex.ParseException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.xpath.FoundIndex;

public class MenuWorker {
	private final static String MENU_SELECT_UPDATE_OPTION ="Enter your option to be updated (or 0 to menu): ";	
	private final static String MENU_SELECT_DELETE_OPTION ="Enter your option to be deleted (or 0 to menu): ";
	private final static String MENU_SELECT_MARK_OPTION ="Enter your option to be marked (or 0 to menu): ";
	private final static String MENU_SELECT_SEARCH_OPTION ="Enter your option for details (or 0 to menu): ";
	private final static String MENU_SEARCH_INSTRUCTION ="Enter keyword: ";

	static Logger logger = Logger.getLogger("System Menu Log");
	private static int _defaultPriority = 2;
	private static String _defaultTime = "09:00";
	static Scanner s = null;

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
		    	display("Enter Task Duration in hours(Optional)");
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
		display("Enter Task Name: ");
		String taskName = s.nextLine();
		return taskName;
	}
	
	public static String getTaskDescription() {
		 display("Enter Description(Optional): ");
		    String taskDesc = s.nextLine();
		    return taskDesc;
	}
	
	public static String getTaskDate() {
		display("Enter Task Date (DDMMYY): ");
	    String taskDate = s.nextLine();
	    return taskDate;
	}
	
	public static String getTaskTime() {
		display("Enter Task Time: ");
	    String taskTime = s.nextLine();
	    return taskTime;
	}
	
	public static String findMenu(){
		String result = "";
		s= new Scanner(System.in);
		display(MENU_SEARCH_INSTRUCTION);
		result = s.nextLine();
		return result;
	}
	
	public static Task createFloatingTask(String taskName, String taskDesc, int priority) {
		logger.info("floating task is created!");
		return TaskBuilder.buildTask(taskName, taskDesc, priority);
	}

	public static Task selectItemFromList(CommandType type,ArrayList<Task> list, String items)throws IndexOutOfBoundsException{
		if(list.size()==1){
			return list.get(0);
		}
		
		s = new Scanner(System.in);
		displaySelectInstruction(type, items);
		
		int option = retrieveInputOption();
		if (option ==0){
			return null;
		}
		Task t = list.get(option-1);
		return t;
	}

	private static void displaySelectInstruction(CommandType type, String items) {
		displayLine(items);
		if(type== CommandType.DELETE){
			display(MENU_SELECT_DELETE_OPTION);
		}else if(type ==CommandType.UPDATE){
			display(MENU_SELECT_UPDATE_OPTION);
		}else if(type ==CommandType.SEARCH){
			display(MENU_SELECT_SEARCH_OPTION);
		}else if(type == CommandType.DONE){
			display(MENU_SELECT_MARK_OPTION);
		}
	}

	private static int retrieveInputOption() {
		int option = 0;
		try{
			option = s.nextInt();
		}
		catch(Exception e){
			return -2;
		};
		return option;
	}
	
	
	private static void displayLine(String a){
		System.out.println(a);
	}
	
	private static void display(String a){
		System.out.print(a);
	}

	public static Task updateTask(Task t) {
		if (t == null) return null;
		Task result =null;
		s = new Scanner(System.in);
		displayLine("please enter the updated info or press enter to remain unchange");
		display(String.format("Name ( %s ):",t.getTaskName()));
		String taskName = getUpdatedInfo(s,t.getTaskName());
		display(String.format("Description ( %s ):",t.getTaskDescription()));
		String taskDesc = getUpdatedInfo(s,t.getTaskDescription());
		display(String.format("priority ( %s ):",t.getPriority()));
		String p = getUpdatedInfo(s, String.valueOf(t.getPriority()));
		int taskPriority = Integer.valueOf(p);
		if (t instanceof TimedTask){
			display(String.format("start Date ( %s ):",((TimedTask) t).getStartDate()));
			String startDate = getUpdatedInfo(s, ((TimedTask) t).getStartDate());
			display(String.format("start Time ( %s ):",((TimedTask) t).getStartTime()));
			String startTime = getUpdatedInfo(s, ((TimedTask) t).getStartTime());
			display(String.format("duration in hours ( %s ):",((TimedTask) t).getDuration()));
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
			display(String.format("end Date ( %s ):",((DeadlineTask) t).getDate()));
			String endDate = getUpdatedInfo(s, ((DeadlineTask) t).getDate());
			display(String.format("end Time ( %s ):",((DeadlineTask) t).getTime()));
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

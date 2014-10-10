package com.epictodo.engine;

import java.util.ArrayList;
import java.util.Scanner;

import com.epictodo.model.Task;
import com.epictodo.util.TaskBuilder;

public class MenuWorker {
	
	private final static String MENU_SELECT_DELETE_OPTION ="Enter your option to be deleted (or 0 to menu): ";
	private final static String MENU_SELECT_SEARCH_OPTION ="Enter your option for details (or 0 to menu): ";
	
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
		
	    Display("Enter Task Name: ");
	    taskName = s.nextLine();
	    
	    Display("Enter Description(Optional): ");
	    taskDesc = s.nextLine();
	    
	    Display("Enter Task Date (DDMMYY): ");
	    taskDate = s.nextLine();
	    if (taskDate.equals("")){
	    	return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority);
	    }
	    Display("Enter Task Time: ");
		    taskTime = s.nextLine();
		    if (!taskTime.equals("")){
		    	Display("Enter Task Duration in hours(Optional)");
		    	String durationTemp = s.nextLine();
		    	if (!durationTemp.equals("")){
		    		taskDuration = Double.valueOf(durationTemp);
		    		return TaskBuilder.buildTask(taskName, taskDesc, _defaultPriority);
		    	}else{
		    		return TaskBuilder.buildTask(taskName,taskDesc,_defaultPriority,taskDate,taskTime);
		    	}
		    }
		    return TaskBuilder.buildTask(taskName,taskDesc,_defaultPriority,taskDate,_defaultTime);
	}
	
	public static Task selectDeleteMenu(ArrayList<Task> list, String items){
		s = new Scanner(System.in);
		DisplayLine(items);
		Display(MENU_SELECT_DELETE_OPTION);
		int option = s.nextInt();
		if (option== 0){
			return null;
		}
		return list.get(option-1);
	}
	
	public static Task selectSearchMenu(ArrayList<Task> list, String items){
		s = new Scanner(System.in);
		DisplayLine(items);
		Display(MENU_SELECT_DELETE_OPTION);
		int option = s.nextInt();
		if (option== 0){
			return null;
		}
		return list.get(option-1);
	}
	
	public static void DisplayLine(String a){
		System.out.println(a);
	}
	
	public static void Display(String a){
		System.out.print(a);
	}
}

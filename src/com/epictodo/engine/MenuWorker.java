package com.epictodo.engine;

import java.util.Scanner;
import com.epictodo.model.Task;
import com.epictodo.util.TaskBuilder;

public class MenuWorker {
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
	
	private static void Display(String a){
		System.out.print(a);
	}
}

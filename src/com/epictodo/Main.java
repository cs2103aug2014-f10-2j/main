package com.epictodo;

import java.util.Date;
import java.util.Scanner;

import com.epictodo.model.DeadlineTask;
import com.epictodo.model.FloatingTask;
import com.epictodo.model.Task;
import com.epictodo.model.TimedTask;



public class Main {
	// COMMAND INPUT
	private final static String COMMAND_EXIT = "exit";
	private final static String COMMAND_ADD = "add";
	private final static String COMMAND_DELETE = "delete";
	private final static String COMMAND_SEARCH = "search";
	private final static String COMMAND_CLEAR = "clear";
	private final static String COMMAND_DISPLAY = "display";
	private final static String OPTION_ADD = "1";
	private final static String OPTION_FIND = "2";
	private final static String OPTION_DISPLAY= "3";
	private final static String OPTION_UNDO = "4";
	private final static String OPTION_OTHERS = "5";
	private final static String OPTION_EXIT = "6";

	
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		bannerDisplay();
		while(true){
			menuDisplay();
			String instruc = sc.nextLine();
			Display(proceedInstruc(instruc));
			
		}
	}
	
	public static void helpmenu(){
		DisplayLine("Add:\tAdd, ++, F1 <TaskName> <Date> <Time> <Duration>");
		DisplayLine("Find:\tFind, Search, F2 <Keywords> ");
		DisplayLine("Update:\tUpdate, Change, F3 <TaskName>.....");
		DisplayLine("Delete:\tDelete, --, Remove, F4<TaskName>");
		DisplayLine("Undo:\tundo" );
		DisplayLine("Reminder:\tNotif, Reminder, !! <TaskName> <HoursBefore>  ");
		DisplayLine("Description:\tDesc, // <TaskName> <Desc>");
		DisplayLine("Menu:\tMenu, Home");
		DisplayLine("");
		DisplayLine("Press Enter to Menu");
	}
	public static void upcomingEvent(){
	    DisplayLine("1. Family Meeting @140914 ");
	    DisplayLine("2. Dinner with kenneth @190914 ");
	    DisplayLine("3. BasketBall Training @200914");
	}
	public static void otherMenuDisplay(){
		DisplayLine("1. Reminder");
		DisplayLine("2. Tag");
		DisplayLine("3. Description");
		DisplayLine("4. Help");
		DisplayLine("5. Exit");
		System.out.print("Insert Your option or command: ");
	}
	public static void undoCommand(){
		DisplayLine("last command 'Family Meeting is delete' is undo");
		
	}
	public static void deleteCommand(){
		DisplayLine("Family Meeting is deleted");
	}
	public static void updateCommand(){
		DisplayLine("Family Meeting date is updated");
	}
	public static void findCommand(){
	    DisplayLine("Name: Family Meeting");
	    DisplayLine("Date: 14/09/2014");
	    DisplayLine("Time: 1800~2000");
	    DisplayLine("Delete/Update/Menu command can be used here.");
	    sc.nextLine();
	}
	public static void twoEntered(){
		Display("Enter Keyword or @Date: ");
	    sc.nextLine();
	    DisplayLine("1. Family Meeting @140914 ");
	    DisplayLine("2. Family Dinner @191014 ");
	    Display("select it or search a keyword again: ");
	    sc.nextLine();
	    DisplayLine("Name: Family Meeting");
	    DisplayLine("Date: 14/09/2014");
	    DisplayLine("Time: 1800~2000");
	    DisplayLine("Delete/Update/Menu command can be used here.");
	    sc.nextLine();
	}
	
	public static void oneEntered(){
	    Display("Enter Task Name: ");
	    sc.nextLine();
	    Display("Enter Task Date (DDMMYY): ");
	    sc.nextLine();
	    Display("Enter Task Time(Optional): ");
	    sc.nextLine();
	    Display("Enter Task Duration(Optional)");
	    sc.nextLine();
	    DisplayLine("Meeting is scheduled in the list");
	    sc.nextLine();
	}
	public static void addCommand(){
	    DisplayLine("Meeting is scheduled in the list");
	    sc.nextLine();
	}
	public static void bannerDisplay(){
		DisplayLine("___________      .__         ___________        ___     \n"+
		"\\_   _____/_____ |__| ____   \\__    ___/___   __| _/____ \n"+
		" |  ____)_\\____ \\|  |/ ___\\    |    | /  _ \\ / __ |/  _ \\\n"+
		" |        \\  |_> >  \\  \\___    |    |(  <_> ) /_/ (  <_> \n"+
		"/_______  /   __/|__|\\___ >    |____| \\____/\\____ |\\____/ 	\n"+
		"        \\/|__|           \\/                      \\/       \n"+
		"");
	}
	
	public static void menuDisplay(){
		DisplayLine("\n1. Add");
		DisplayLine("2. Find/Update/Delete");
		DisplayLine("3. Display");
		DisplayLine("4. Undo");
		DisplayLine("5. Others");
		DisplayLine("6. Exit");
		System.out.print("Insert Your option or command: ");
	}
	public static void DisplayLine(String a){
		System.out.println(a);
	}
	public static void Display(String a){
		System.out.print(a);
	}
	
	/*
	 * This part is for LanguageProcessor
	 * 
	 */
	static Scanner s = new Scanner(System.in);
	private static String proceedInstruc(String instruc) {
		CommandType command = defineCommandType(instruc);
		instruc = removeCommand(instruc);
		switch(command){
		case DISPLAY:
			
		case ADD:
			Task t = createTask(instruc);
			
		case DELETE:
			
		case CLEAR:

		case SEARCH:
		
		case EXIT:
			System.exit(0);
			break;
		case INVALID:
			break;
		case O_ADD:
			return addGuide();
		case O_FIND:
			break;
			
		case O_UNDO:
			break;
			
		case O_OTHERS:
			break;
		}
		
		
		return null;
	}

	private static Task createTask(String instruc) {
		Scanner s = new Scanner(instruc);
		String taskName = "";
		String taskDate = "";
		String taskTime = "";
		String next= "";
		String taskDesc="";
			taskName = getTaskNameThroughInstruction(s);
			taskDate = getTaskDateThroughInstruction(s);
			
			if (taskDate!=null){
				taskTime = getTaskTimeThroughInstruction(s);
				if(taskTime==null){
					//new deadline Task eric
					DeadlineTask dlt = new DeadlineTask (taskName, taskDesc, 2, taskDate, taskTime); 
					// call add
				}
				else{
					double taskDuration = getTaskDurationThroughInstruction(s);
					//new timed task eric
					TimedTask tt = new TimedTask (taskName,taskDesc,2, taskDate, taskTime, taskDuration); 
					// call add
				}
			}
			else{
				//new floating task eric
				FloatingTask tf = new FloatingTask(taskName, taskDesc,2);
				// call add
			}
		return null;
	}

	private static double getTaskDurationThroughInstruction(Scanner s2) {
		while(s.hasNext()){
			return s.nextDouble();
		}
		return -1;
	}
	
	private static String getTaskTimeThroughInstruction(Scanner s2) {
		while(s.hasNext()){
			return s.next();
		}
		return null;
	}

	private static String getTaskDateThroughInstruction(Scanner s2) {
		// exception to be added next time
		while(s.hasNext()){
			return s.next();
		}
		return null;
	}

	private static String getTaskNameThroughInstruction(Scanner s) {
		//exception to be added next time
		String taskName = "";
		while (s.hasNext()){
			String next = sc.next();
			if (next.equals ("@"))
				return taskName;
			else
				taskName += next;
		}
		return null;
	}

	private static String addGuide() {
		// TODO Auto-generated method stub
		s = new Scanner(System.in);
		String result ="";
	    Display("Enter Task Name: ");
	    String name = s.nextLine();
	    Display("Enter Discription: ");
	    String desc = s.nextLine();
	    Display("Enter Task Date (DDMMYY): ");
	    String date = s.nextLine();
	    
	    if (!date.equals("")){
	    	Display("Enter Task Time(Optional): ");
		   	String time = s.nextLine();
		    if (!time.equals("")){
		    	Display("Enter Task Duration in hours(Optional)");
		    	String durationTemp = sc.nextLine();
		    	if (!durationTemp.equals("")){
		    		double duration = Double.valueOf(durationTemp);
		    	// new timedTask eric
		    	 TimedTask tt = new TimedTask (name, desc, 2,  date, time, duration);
		    	// call add
		    	}else{
		    		// new deadlineTask eric
		    		DeadlineTask dlt =  new DeadlineTask (name, desc, 2, date, time);
		    		// call add
		    	}
		    }
	    }else{
	    	// new floatingTask eric
	    	FloatingTask ft = new FloatingTask (name, desc,2);
	    	// call add
	    }
	    result = name+" is scheduled in the list!";
		return result;
	}

	private static String removeCommand(String instruc) {
		return instruc.replace(getCommand(instruc), "").trim();
	}
	
	private static CommandType defineCommandType(String instruc){
		String command = getCommand(instruc);
		if (compareString(command,""))
			return CommandType.NULL;
		else if (compareString(command,COMMAND_ADD))
			return CommandType.ADD;
		else if (compareString(command,COMMAND_DELETE))
			return CommandType.DELETE;
		else if (compareString(command,COMMAND_CLEAR))
			return CommandType.CLEAR;
		else if (compareString(command,COMMAND_SEARCH))
			return CommandType.SEARCH;
		else if (compareString(command,COMMAND_EXIT))
			return CommandType.EXIT;	
		else if (compareString(command,COMMAND_DISPLAY))
			return CommandType.DISPLAY;
		else if (compareString(command,OPTION_ADD))
			return CommandType.O_ADD;
		else if (compareString(command,OPTION_FIND))
			return CommandType.O_FIND;
		else if (compareString(command,OPTION_UNDO))
			return CommandType.O_UNDO;
		else if (compareString(command,OPTION_OTHERS))
			return CommandType.O_OTHERS;
		else if (compareString(command,OPTION_EXIT))
			return CommandType.EXIT;	
		else if (compareString(command,COMMAND_DISPLAY))
			return CommandType.O_DISPLAY;
		else
			return CommandType.INVALID;
	}
	
	private static String getCommand(String instruc) {
		String commandTypeString = instruc.trim().split("\\s+")[0];
		return commandTypeString;
	}

	private static boolean compareString(String text, String text2){
		return (text.equalsIgnoreCase(text2));
	}

	enum CommandType{
		DISPLAY, ADD, DELETE, CLEAR, SEARCH, EXIT, INVALID, NULL, O_ADD, O_FIND, 
		O_DISPLAY, O_UNDO, O_OTHERS
	};
	
	
	
	
	
}

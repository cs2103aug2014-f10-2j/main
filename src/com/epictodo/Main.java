import java.util.Scanner;

import main.CommandType;



public class main {
	// COMMAND INPUT
	private final static String COMMAND_EXIT = "exit";
	private final static String COMMAND_ADD = "add";
	private final static String COMMAND_DELETE = "delete";
	private final static String COMMAND_SEARCH = "search";
	private final static String COMMAND_CLEAR = "clear";
	private final static String COMMAND_DISPLAY = "display";
	
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		bannerDisplay();
		while(true){
			String instruc = sc.nextLine();
			Display(proceedInstruc(instruc));
			
		}
		
	    //menuDisplay();
	    //sc.nextLine();
	    //otherMenuDisplay();
	    //sc.nextLine();
	    //helpmenu();
	    
	    
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
		"\n");
	}
	
	public static void menuDisplay(){
		DisplayLine("1. Add");
		DisplayLine("2. Find/Update/Delete");
		DisplayLine("3. Undo");
		DisplayLine("4. Others");
		DisplayLine("5. Exit");
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
	private static String proceedInstruc(String instruc) {
		CommandType command = defineCommandType(instruc);
		instruc = removeCommand(instruc);
		switch(command){
		case DISPLAY:
			
		case ADD:
			
		case DELETE:
			
		case CLEAR:

		case SEARCH:
		
		case EXIT:
			System.exit(0);
			break;
		case INVALID:
		}
		
		
		return null;
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
		DISPLAY, ADD, DELETE, CLEAR, SEARCH, EXIT, INVALID, NULL
	};
	
	
	
	
	
}

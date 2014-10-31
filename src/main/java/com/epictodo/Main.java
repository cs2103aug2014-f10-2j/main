package com.epictodo;

import com.epictodo.engine.WorkDistributor;

import java.util.Scanner;


public class Main {
	// COMMAND INPUT
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		WorkDistributor.loadData();
		bannerDisplay();
		while(true){
			menuDisplay();
			String instruc = sc.nextLine();
			Display(WorkDistributor.proceedInstruc(instruc));
		}
	}
	
	public static void helpmenu(){
		DisplayLine("Add:\tAdd, ++, F1 <TaskName> <Date> <Time> <Duration>\n"
		+"Find:\tFind, Search, F2 <Keywords>\n"
		+"Update:\tUpdate, Change, F3 <TaskName>.....\n"
		+"Delete:\tDelete, --, Remove, F4<TaskName>\n"
		+"Undo:\tundo\n"
		+"Reminder:\tNotif, Reminder, !! <TaskName> <HoursBefore>\n"
		+"Description:\tDesc, // <TaskName> <Desc>\n"
		+"Menu:\tMenu, Home\n\n"
		+"Press Enter to Menu");
	}
	
	public static void otherMenuDisplay(){
		DisplayLine("1. Reminder");
		DisplayLine("2. Tag");
		DisplayLine("3. Description");
		DisplayLine("4. Help");
		DisplayLine("5. Exit");
		System.out.print("Insert Your option or command: ");
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
		DisplayLine("2. Find");
		DisplayLine("3. Delete");
		DisplayLine("4. Update");
		DisplayLine("5. Display");
	//	DisplayLine("5. Others");
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
 * 	private final static String OPTION_ADD = "1";
	private final static String OPTION_FIND = "2";
	private final static String OPTION_DELETE = "3";
	private final static String OPTION_UPDATE = "4";
	private final static String OPTION_DISPLAY = "5";
	private final static String OPTION_EXIT = "6";
 */







	
	
	
	
	
}

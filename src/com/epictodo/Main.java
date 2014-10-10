package com.epictodo;

import java.util.Date;
import java.util.Scanner;

import com.epictodo.logic.CRUDLogic;
import com.epictodo.model.DeadlineTask;
import com.epictodo.model.FloatingTask;
import com.epictodo.model.Task;
import com.epictodo.model.TimedTask;
import com.epictodo.engine.*;


public class Main {
	// COMMAND INPUT
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {

		
		bannerDisplay();
		while(true){
			menuDisplay();
			String instruc = sc.nextLine();
			Display(WorkDistributor.proceedInstruc(instruc));
			
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
	










	
	
	
	
	
}

package com.epictodo;

import com.epictodo.engine.NLPEngine;
import com.epictodo.engine.NLPLoadEngine;
import com.epictodo.engine.WorkDistributor;

import java.util.Scanner;


public class Main {
    private static NLPLoadEngine load_engine = NLPLoadEngine.getInstance();
    private static NLPEngine nlp_engine = NLPEngine.getInstance();

    // COMMAND INPUT
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        nlp_engine.mute();

        WorkDistributor.loadData();
        bannerDisplay();
        while (true) {
            Display("\nEnter your command:");
            String instruc = sc.nextLine();
            Display(WorkDistributor.processCommand(instruc));
        }
    }

    public static void helpmenu() {
        DisplayLine("Add:\tAdd, ++, F1 <TaskName> <Date> <Time> <Duration>\n"
                + "Find:\tFind, Search, F2 <Keywords>\n"
                + "Update:\tUpdate, Change, F3 <TaskName>.....\n"
                + "Delete:\tDelete, --, Remove, F4<TaskName>\n"
                + "Undo:\tundo\n"
                + "Reminder:\tNotif, Reminder, !! <TaskName> <HoursBefore>\n"
                + "Description:\tDesc, // <TaskName> <Desc>\n"
                + "Menu:\tMenu, Home\n\n"
                + "Press Enter to Menu");
    }

    public static void bannerDisplay() {
        DisplayLine("___________      .__         ___________        ___     \n" +
                "\\_   _____/_____ |__| ____   \\__    ___/___   __| _/____ \n" +
                " |  ____)_\\____ \\|  |/ ___\\    |    | /  _ \\ / __ |/  _ \\\n" +
                " |        \\  |_> >  \\  \\___    |    |(  <_> ) /_/ (  <_> \n" +
                "/_______  /   __/|__|\\___ >    |____| \\____/\\____ |\\____/ 	\n" +
                "        \\/|__|           \\/                      \\/       \n" +
                "");
    }

    public static void DisplayLine(String a) {
        System.out.println(a);
    }

    public static void Display(String a) {
        System.out.print(a);
    }


}

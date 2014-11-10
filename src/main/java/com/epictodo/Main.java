//@author A0112918H
package com.epictodo;

import java.util.Scanner;

import com.epictodo.controller.worker.WorkDistributor;

public class Main {

	private static final String ASCII_ART_BANNER = "\n___________      .__         ___________        ___     \n"
			+ "\\_   _____/_____ |__| ____   \\__    ___/___   __| _/____ \n"
			+ " |  ____)_\\____ \\|  |/ ___\\    |    | /  _ \\ / __ |/  _ \\\n"
			+ " |        \\  |_> >  \\  \\___    |    |(  <_> ) /_/ (  <_> \n"
			+ "/_______  /   __/|__|\\___ >    |____| \\____/\\____ |\\____/ 	\n"
			+ "        \\/|__|           \\/                      \\/       \n";

	//private static NLPEngine _nlpEngine = NLPEngine.getInstance();

	// COMMAND INPUT
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// _nlpEngine.mute();
		
		WorkDistributor.loadData();
		bannerDisplay();
		while (true) {
			Display("\nEnter your command: ");
			String instruc = sc.nextLine();
			Display(WorkDistributor.processCommand(instruc));
		}
	}

	private static void bannerDisplay() {
		DisplayLine(ASCII_ART_BANNER);
	}

	private static void DisplayLine(String a) {
		System.out.println(a);
	}

	private static void Display(String a) {
		System.out.print(a);
	}
}

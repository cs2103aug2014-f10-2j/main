//@author A0112918H
package com.epictodo;

import com.epictodo.engine.NLPEngine;
import com.epictodo.engine.WorkDistributor;

import java.util.Scanner;

public class Main {
	private static final String ASCII_ART_BANNER = "___________      .__         ___________        ___     \n"
			+ "\\_   _____/_____ |__| ____   \\__    ___/___   __| _/____ \n"
			+ " |  ____)_\\____ \\|  |/ ___\\    |    | /  _ \\ / __ |/  _ \\\n"
			+ " |        \\  |_> >  \\  \\___    |    |(  <_> ) /_/ (  <_> \n"
			+ "/_______  /   __/|__|\\___ >    |____| \\____/\\____ |\\____/ 	\n"
			+ "        \\/|__|           \\/                      \\/       \n";

	private static NLPEngine nlp_engine = NLPEngine.getInstance();

	// COMMAND INPUT
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// nlp_engine.mute();

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

package com.peakTraffic;

import java.util.List;

public class Main {
	
	private static final int usersPerCluster = 3;

	public static void main(String[] args) {
		Interactions interactions = new Interactions();
		int numAccounts = 3000;
		int rangeInteractionsPerAccount = 20;
		int percentage = 100;
		interactions.generateHeavyInteractions(numAccounts, rangeInteractionsPerAccount, percentage);
		interactions.dumpToFile("tests/test_"+numAccounts+"_"+rangeInteractionsPerAccount+"_"+percentage+".txt");
		
		long time = System.currentTimeMillis();
		Tomita tomita = new Tomita(interactions);
		tomita.execute(usersPerCluster);
		List<String> results = tomita.getOrderedClusters();
		long endTime = System.currentTimeMillis();
		
		for(String line:results) {
			System.out.println(line);
		}
		System.out.println("\nTime in ms:"+(endTime-time));		
	}	
}
package com.peakTraffic.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.peakTraffic.Interactions;
import com.peakTraffic.Tomita;


public class Tests {
	
	private static final int usersPerCluster = 3;

	@Test
	public void test() {
		
		Tomita tomita = null;
		BufferedReader bReader = null;
		File folder = new File("tests");
		Interactions interactions = null;
		
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isFile()) {
        		interactions = new Interactions(fileEntry.getPath());
    			tomita = new Tomita(interactions);
    			tomita.execute(usersPerCluster);
    			List<String> results = tomita.getOrderedClusters();
    			System.out.println("Output for "+fileEntry.getName());
    			for(String line:results) {
    				System.out.println(line);
    			}
    			System.out.println();
    			String output = fileEntry.getParent()+"Output\\"+fileEntry.getName();
    			try {
    				bReader = new BufferedReader(new FileReader(output));
    				int i = 0;
    			    try {
    			       String line = bReader.readLine();		
    			       while (line != null) {
    			    	   assertFalse("Different output from expected", i >= results.size());
    			    	   assertEquals(line, results.get(i));
    			    	   line = bReader.readLine();
    			    	   i++;
    			       }
    			    } catch (IOException e) {
    				   e.printStackTrace();
    			    } finally {
    			    	bReader.close();
    			    }
    			} catch(IOException ie) {}
        	}
		}
	}
}
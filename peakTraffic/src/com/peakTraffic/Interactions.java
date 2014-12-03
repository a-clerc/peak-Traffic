package com.peakTraffic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Interactions {

	HashMap<String, HashSet<String>> interactions;
	static final SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US);
	
	public Interactions() {
		this.interactions = new HashMap<String, HashSet<String>>();
	}
	
	public Interactions(String file) {
		this();
		dumpFromFile(file);
	}
		
	public void addInteraction(String sender, String receiver) {
		HashSet<String> reference = interactions.get(sender);
		if(reference != null) {		
			reference.add(receiver);
		} else {
			HashSet<String> newInteraction = new HashSet<String>();
			newInteraction.add(receiver);
			interactions.put(sender, newInteraction);
		}
	}
	
	public boolean checkIfComplete(Set<String> subSet) {
		long numVertexs = subSet.size();
		long numEdges = (numVertexs*(numVertexs-1));		
		for(String sender: subSet) {
			Set<String> nbrs = new HashSet<String>(this.getNbrs(sender));
			nbrs.retainAll(subSet);
			for(String nbr: nbrs) {
				Set<String> receivers = this.getNbrs(nbr); 
				if(receivers.contains(sender)) {
					numEdges--;
				}
			}			
		}
		return numEdges==0;
	}
	
	public void dumpToFile(String file) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(file));
			OutputStreamWriter os = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(os);
			StringBuffer sb;
			Calendar calendar = Calendar.getInstance(Locale.US);
			Date d;
			try {
				for(Entry<String, HashSet<String>> entry: interactions.entrySet()) {
					Set<String> set = entry.getValue();
					Iterator<String> it = set.iterator();
					while(it.hasNext()) {
						calendar.add(Calendar.SECOND, 1);
						d = calendar.getTime();	
						sb = new StringBuffer(sdf.format(d));
						sb.append("\t").append(entry.getKey());
						sb.append("\t").append(it.next());
						bw.write(sb.toString());
						bw.newLine();
					}
				}
			} catch(IOException e) {
			} finally {
				bw.close();
			}
		} catch(IOException e) {}
	}
	
	private void dumpFromFile(String file) {	
	   try {	
		   BufferedReader br = new BufferedReader(new FileReader(file));
		   try {
		       String line = br.readLine();		
		       while (line != null) {
		    	   String[] splittedByTabs = line.split("\\t");
		    	   if(splittedByTabs.length == 3) {
		    		   String toParse = splittedByTabs[0];
					   if(sdf.parse(toParse) != null) {
						   this.addInteraction(splittedByTabs[1], splittedByTabs[2]);
					   }
		    	   } else if(line.equals("")){
		    		   break;
		    	   } else {	   
		    		   throw new IOException("Incorrect Format");
		    	   }
		           line = br.readLine();
		       }
		   } catch (ParseException e) {
			   e.printStackTrace();
		   } catch (IOException e) {
			   e.printStackTrace();
		   } finally {
		       br.close();
		   }
	   } catch (IOException e) {}
	}
	
	public void generateHeavyInteractions(int numVertexs, int range, int percentage) {
		Random r = new Random();
		String facebook = "@facebook.com";
		for(int i = 1; i <= numVertexs; i++) {
			for(int j = 1; j <= range; j++) {
				if(i <= numVertexs-j && r.nextInt(100) <= percentage) {
					this.addInteraction(String.valueOf(i)+facebook, String.valueOf(i+j)+facebook);	
				}
				if(i > j && r.nextInt(100) <= percentage) {
					this.addInteraction(String.valueOf(i)+facebook, String.valueOf(i-j)+facebook);
				}
			}
		}
	}	
	
	public Set<String> getNbrs(String sender) {
		return interactions.get(sender);
	}
	
	public String getPivot(Set<String> subSet) {
		int max = -1;
		String pivot = null;
		for(String element: subSet) {
			if(this.interactions.get(element).size() > max) {
				Set<String> nbrs = this.getNbrs(element);
				pivot = element;
				max = nbrs.size();
			}
		}		
		return pivot;
	}
	
	public Set<String> getSenders() {
		return interactions.keySet();
	}		
}
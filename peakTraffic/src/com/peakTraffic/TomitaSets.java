package com.peakTraffic;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class TomitaSets extends HashSet<String> {
	
	public static final int UNION = 1;
	public static final int REMOVE = 2;
	public static final int INTERSECTION = 3;
		
	public TomitaSets() {
		super();
	}
		
	public TomitaSets(Set<String> set) {
		super(set);
	}
	
	public TomitaSets(Set<String> set1, String element) {
		this(set1);
		this.add(element);
	}
	
	public TomitaSets(Set<String> set1, Set<String> set2, int opSet) {
		this(set1);
		switch(opSet) {
			case INTERSECTION:
				this.retainAll(set2);
				break;
			case UNION:
				this.addAll(set2);
				break;
			case REMOVE:
				this.removeAll(set2);
				break;
		}
	}
}

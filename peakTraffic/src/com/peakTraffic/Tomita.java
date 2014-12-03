package com.peakTraffic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Tomita {
	
	private Interactions _P;
	private long usersPerCluster;
	private List<String> result = new ArrayList<String>();
	
	public Tomita(Interactions p) {
		this._P = p;
	}
	
	public void execute(int usersPerCluster) {
		this.usersPerCluster = usersPerCluster;
		TomitaSets p = new TomitaSets(_P.getSenders());
		this.tomitaAlgorithm(new TomitaSets(), p , new TomitaSets());
	}
	
	public void tomitaAlgorithm(TomitaSets r, TomitaSets p, TomitaSets x) {
		if(r.size()+p.size() < usersPerCluster) {
			return;
		} else if(p.isEmpty() && x.isEmpty() && _P.checkIfComplete(r)) {
			Object[] rArray = r.toArray();
			Arrays.sort(rArray);
			String arrayR = Arrays.toString(rArray); 
			result.add(arrayR.replace("[", "").replace("]", ""));
			return;
		} 		
		TomitaSets pUnionx = new TomitaSets(p, x , TomitaSets.UNION);
		String pivot = _P.getPivot(pUnionx);
		if(pivot != null) {
			TomitaSets pRemoveNbrsU = new TomitaSets(p, _P.getNbrs(pivot), TomitaSets.REMOVE);
			for(String element: pRemoveNbrsU) {
				TomitaSets rUnionV = new TomitaSets(r, element);
				Set<String> neightbousOfV = _P.getNbrs(element);
				TomitaSets pIntersectsNbrsV = new TomitaSets(p, neightbousOfV, TomitaSets.INTERSECTION);
				TomitaSets xIntersectsNbrsV = new TomitaSets(x, neightbousOfV, TomitaSets.INTERSECTION);
				tomitaAlgorithm(rUnionV, pIntersectsNbrsV, xIntersectsNbrsV);
				p.remove(element);
				x.add(element);
			}
		}
	}	
	
	public List<String> getOrderedClusters() {
		Collections.sort(result);
		return result;
	}
}

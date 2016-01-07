package problem.asm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class God {
	private HashSet<iShape> data;
	
	public God(){
		data = new HashSet<iShape>();
	}
	
	public void add(iShape s){
		data.add(s);
	}
	
	public String genesis(){
		ArrayList<iShape> ddata = makeList(data);
		
		String s = "";
		
		s += "digraph G {\n\nfontname = \"Bitstream Vera Sans\"\nfontsize = 8\n\n";
		
		for(iShape datum : ddata){
			s += datum.dotString() + "\n\n";
		}
		
		s += "}";
		
		return s;
	}
	
	private ArrayList<iShape> makeList(Set<iShape> set) {
	    ArrayList<iShape> uniqueList = new ArrayList<iShape>();
	    HashSet<Integer> past = new HashSet<Integer>();
	    for (iShape i : set) {
	        if (!past.contains(i.getId())) {
	            uniqueList.add(i);
	            past.add(i.getId());
	        }
	    }
	    Collections.sort(uniqueList, new Comparator<iShape>() 
	    		{
	    		    public int compare(iShape s1, iShape s2) 
	    		    {
	    		    	return s1.getPriority() - s2.getPriority();
	    		    }
	    		});
	    return uniqueList;
	}
}

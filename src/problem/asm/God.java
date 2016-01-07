package problem.asm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class God {
	private HashSet<iShape> data;
	
	public God(){
		data = new HashSet<iShape>();
	}
	
	public void add(iShape s){
		data.add(s);
	}
	
	public String genesis(){
		ArrayList<iShape> ddata = new ArrayList<iShape>();
		ddata.addAll(data);
		Collections.sort(ddata, new Comparator<iShape>() 
		{
		    public int compare(iShape s1, iShape s2) 
		    {
		    	return s1.getPriority() - s2.getPriority();
		    }
		});
		
		String s = "";
		
		s += "digraph G {\n\nfontname = \"Bitstream Vera Sans\"\nfontsize = 8\n\n";
		
		for(iShape datum : ddata){
			s += datum.dotString() + "\n\n";
		}
		
		s += "}";
		
		return s;
	}
}

package problem.question1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class God {
	private ArrayList<iShape> data;
	
	public God(){
		data = new ArrayList<iShape>();
	}
	
	public void add(iShape s){
		data.add(s);
	}
	
	public String genesis(){
		Collections.sort(data, new Comparator<iShape>() 
		{
		    public int compare(iShape s1, iShape s2) 
		    {
		       return s1.getPriority() - s1.getPriority();
		    }
		});
		
		String s = "";
		
		s += "digraph G {\n\nfontname = \"Bitstream Vera Sans\"\nfontsize = 8\n\n";
		
		for(iShape datum : data){
			s += datum.dotString() + "\n\n";
		}
		
		s += "}";
		
		return s;
	}
}

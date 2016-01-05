package problem.question1;

import java.util.ArrayList;

public class dotBlurb {
	private ArrayList<dotAssignment> lines;
	private String name;
	
	public dotBlurb(String name){
		this.name = name;
		lines = new ArrayList<dotAssignment>();
	}
	
	public boolean addLine(String vr, String vl){
		return lines.add(new dotAssignment(vr, vl));
	}
	
	public void clear(){
		lines = new ArrayList<dotAssignment>();
	}
	
	public String dotString(){
		String s = "";
		
		s += name + " [";
		for(dotAssignment da : lines){
			s += da.dotString() + " ";
		}
		s += "]";
		return s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

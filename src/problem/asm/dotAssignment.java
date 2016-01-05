package problem.question1;

public class dotAssignment {
	private String variable;
	private String value;
	
	public dotAssignment(String vr, String vl){
		variable = vr;
		value = vl;
	}
	
	public String dotString(){
		return variable + " = " + value;
	}
}

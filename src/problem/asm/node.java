package problem.question1;

public abstract class node implements iShape {
	private String name;
	
	public void setName(String s){
		name = s;
	}
	
	public String getName(){
		return name;
	}
	
}

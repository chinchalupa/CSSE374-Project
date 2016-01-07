package problem.asm;

public abstract class node implements iShape {
	private String name;
	
	public void setName(String s){
		name = s;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPriority(){
		return 0;
	}
	
	public int getId(){
		return this.name.hashCode();
	}
	
}

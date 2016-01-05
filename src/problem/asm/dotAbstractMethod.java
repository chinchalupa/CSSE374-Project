package problem.question1;

public class dotAbstractMethod implements plainText{
	private String type;
	private String name;
	
	public dotAbstractMethod(String ty, String nm){
		name = nm;
		type = ty;
	}
	
	public void setName(String nm){
		name = nm;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String nm){
		type = nm;
	}
	
	public String getName(){
		return name;
	}

	@Override
	public String dotString() {
		return "+A " + type + " " + name + "()";
	}
}

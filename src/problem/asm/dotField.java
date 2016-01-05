package problem.asm;

public class dotField implements plainText{
	private String type;
	private String name;

	public dotField() {

	}

	public dotField(String ty, String nm){
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
		return "-" + name + ":" + type;
	}
}

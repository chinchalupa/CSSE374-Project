package problem.question1;

import java.util.ArrayList;

public class dotMethod extends plainText {
	private String level;
	private String type;
	private String name;
	private ArrayList<String> args;
	
	public dotMethod(String le, String ty, String nm, ArrayList<String> ar){
		name = nm;
		type = ty;
		level = le;
		args = ar;
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
		String s = "";
		if(level.equals("private")){
			s += "- ";
		}
		else if(level.equals("protected")){
			s += "# ";
		}
		else{
			s += "+ ";
		}
		s += type + " " + name + "(";
		for(int i=0; i<args.size() - 1; i++){
			s += args.get(i) + ", ";
		}
		if(args.size() > 0){
			s += args.get(args.size() - 1);
		}
		s += ")";
		return s;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public ArrayList<String> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<String> args) {
		this.args = args;
	}
}

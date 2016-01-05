package problem.question1;

import java.util.ArrayList;

public class dotClass extends UMLBoxNode {
	
	private static final String separator = "|";
	
	private ArrayList<dotField> fields;
	private ArrayList<dotMethod> methods;
	
	public dotClass(String name, ArrayList<dotField> fields, ArrayList<dotMethod> methods){
		super();
		this.setName(name);
		this.fields = fields;
		this.methods = methods;
	}

	@Override
	public String getLabel() {
		String s = "";
		
		s += this.getName();
		s += separator;
		for(dotField f : fields){
			s += f.dotString() + "\\l";
		}
		s += separator;
		for(dotMethod m : methods){
			s += m.dotString() + "\\l";
		}
		return s;
	}
}

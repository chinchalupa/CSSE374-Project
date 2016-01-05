package problem.question1;

import java.util.ArrayList;

public class dotAbstractClass extends UMLBoxNode {

	private static final String separator = "|";
	
	private ArrayList<dotField> fields;
	private ArrayList<dotMethod> methods;
	private ArrayList<dotAbstractMethod> amethods;
	
	public dotAbstractClass(String name, ArrayList<dotField> fields, ArrayList<dotMethod> methods, ArrayList<dotAbstractMethod> amethods){
		super();
		this.setName(name);
		this.fields = fields;
		this.methods = methods;
		this.amethods = amethods;
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
		s += separator;
		for(dotAbstractMethod a : amethods){
			s += a.dotString() + "\\l";
		}
		return s;
	}
	
}

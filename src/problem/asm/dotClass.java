package problem.asm;

import java.util.ArrayList;
import java.util.List;

public class dotClass extends UMLBoxNode {
	
	private static final String separator = "|";
	
	private ArrayList<dotField> fields;
	private ArrayList<dotMethod> methods;


	public dotClass(String name) {
		super();
		this.setName(name);
	}
	
	public dotClass(String name, ArrayList<dotField> fields, ArrayList<dotMethod> methods){
		super();
		this.setName(name);
		this.fields = fields;
		this.methods = methods;
	}

	public void setFields(ArrayList<dotField> fields) {
		this.fields = fields;
	}

	public void setMethods(ArrayList<dotMethod> methods) {
		this.methods = methods;
	}

	public List<dotMethod> getMethods() {
		return this.methods;
	}


	public void addField(dotField dField) {
		this.fields.add(dField);
	}

	public void addMethod(dotMethod dMethod) {
		this.methods.add(dMethod);
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

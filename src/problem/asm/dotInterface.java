package problem.asm;

import java.util.ArrayList;

public class dotInterface extends UMLBoxNode {

	private static final String separator = "|";
	
	private ArrayList<dotField> fields;
	private ArrayList<dotAbstractMethod> methods;
	
	public dotInterface(String name, ArrayList<dotField> fields, ArrayList<dotAbstractMethod> methods){
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
		for(dotAbstractMethod m : methods){
			s += m.dotString() + "\\l";
		}
		return s;
	}
	
}

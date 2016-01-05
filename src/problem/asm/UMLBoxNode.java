package problem.asm;

public abstract class UMLBoxNode extends node{

	private static final String fontname = "\"Bitstream Vera Sans\"";
	private static final String fontsize = "8";
	private static final String shape = "\"record\"";
	
	@Override
	public String dotString(){
		String s = "";
		
		dotBlurb db = new dotBlurb("node");
		db.addLine("fontname", fontname);
		db.addLine("fontsize", fontsize);
		db.addLine("shape", shape);
		
		s += db.dotString() + "\n";
		
		dotBlurb db2 = new dotBlurb(this.getName());
		db.addLine("label", "\"{" + this.getLabel() + "}\"");
		
		s += db2.dotString();
		
		return s;
	}
	
	public abstract String getLabel();

}

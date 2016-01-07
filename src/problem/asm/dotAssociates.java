package problem.asm;

public class dotAssociates extends dotEdge {

	public static final String arrowhead = "\"vee\"";
	
	public dotAssociates(String s, String ss){
		super(s,ss);
	}
	
	@Override
	public String getBlurbString() {
		dotBlurb db = new dotBlurb("edge");
		db.addLine("arrowhead", arrowhead);
		db.addLine("style", dotExtends.style);
		return db.dotString();
	}

}

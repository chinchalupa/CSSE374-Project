package problem.question1;

public class dotImplements extends dotEdge {

	private static final String style = "\"dashed\"";
	
	public dotImplements(String s, String ss){
		super(s,ss);
	}
	
	@Override
	public String getBlurbString() {
		dotBlurb db = new dotBlurb("edge");
		db.addLine("arrowhead", dotExtends.arrowhead);
		db.addLine("style", style);
		return db.dotString();
	}

}

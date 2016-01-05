package problem.question1;

public class dotExtends extends dotEdge {

	static final String arrowhead = "\"empty\"";
	static final String style = "solid";
	public dotExtends(String s, String ss){
		super(s,ss);
	}
	
	@Override
	public String getBlurbString() {
		dotBlurb db = new dotBlurb("edge");
		db.addLine("arrowhead", arrowhead);
		db.addLine("style", style);
		return db.dotString();
	}

}

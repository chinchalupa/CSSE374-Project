package problem.asm;

public class dotUses extends dotEdge {

	private static final String style = "\"dashed\"";
	
	public dotUses(String s, String ss){
		super(s,ss);
	}
	
	@Override
	public String getBlurbString() {
		dotBlurb db = new dotBlurb("edge");
		db.addLine("arrowhead", dotAssociates.arrowhead);
		db.addLine("style", style);
		return db.dotString();
	}

}

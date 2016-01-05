package problem.asm;

public class dotExtends extends dotEdge {

	private static final String arrowhead = "\"empty\"";
	
	public dotExtends(String t, String f){
		this.setTo(t);
		this.setFrom(f);
	}
	
	@Override
	public String getBlurbString() {
		dotBlurb db = new dotBlurb("edge");
		db.addLine("arrowhead", arrowhead);
		return db.dotString();
	}

}

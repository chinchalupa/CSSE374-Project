package problem.asm;

public abstract class dotEdge implements iShape {
	private String to;
	private String from;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	public String dotString(){
		String s = "";
		
		s += this.getBlurbString() + "\n";
		
		s += from + " -> " + to;
		
		return s;
	}
	
	public abstract String getBlurbString();
	
}

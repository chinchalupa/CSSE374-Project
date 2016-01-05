package problem.asm;

public abstract class dotEdge implements iShape {
	private String to;
	private String from;
	
	public dotEdge(String t, String f){
		this.setTo(t);
		this.setFrom(f);
	}
	
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
	
	public int getPriority(){
		return 1;
	}
	
	public abstract String getBlurbString();
	
}

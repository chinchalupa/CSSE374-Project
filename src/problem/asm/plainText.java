package problem.asm;

public abstract class plainText implements iShape {
	public int getPriority(){
		return 2;
	}
	public int getId(){
		return this.hashCode();
	}
}

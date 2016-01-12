package problem.asm;

/**
 * Created by wrightjt on 1/11/2016.
 */
public interface IEdge extends ITraversable{
    public abstract String getTo();
    public abstract String getFrom();
    public abstract String getLine();
    public abstract String getArrow();
}

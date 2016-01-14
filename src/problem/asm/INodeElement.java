package problem.asm;

/**
 * Created by wrightjt on 1/11/2016.
 */
public interface INodeElement extends ITraversable {
    public abstract String getName();
    public abstract String getReturnType();
    public abstract INode getContainingClass();
}

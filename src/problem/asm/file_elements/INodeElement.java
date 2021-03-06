package problem.asm.file_elements;

import problem.asm.file_elements.INode;

/**
 * Created by wrightjt on 1/11/2016.
 */
public interface INodeElement {
    public abstract String getName();
    public abstract String getReturnType();
    public abstract String getCollectionType();
    public abstract INode getContainingClass();
}

package problem.asm.file_elements;

import problem.asm.visitors.ITraversable;

/**
 * Created by wrightjt on 1/11/2016.
 */
public interface IEdge extends ITraversable {
    public abstract String getTo();
    public abstract String getFrom();
    public abstract String getLine();
    public abstract String getArrow();
    public void setText(String s);
    public abstract String getText();
    public abstract String getLineName();
    @Override
    public abstract String toString();
    public abstract boolean equals(IEdge edge);
}

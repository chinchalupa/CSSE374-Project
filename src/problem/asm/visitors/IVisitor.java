package problem.asm.visitors;

import problem.asm.file_elements.IEdge;
import problem.asm.file_elements.INode;
import problem.asm.structures.UMLDecorator;

/**
 * Created by wrightjt on 1/11/2016.
 */
public interface IVisitor {
    public abstract void visitNode(INode node);
    public abstract void visitEdge(IEdge edge);
    public abstract void preVisitNode(INode node);
    public abstract void postVisitEdge(IEdge edge);
    public abstract void preVisitEdge(IEdge edge);
    public abstract void postVisitNode(INode node);
    public abstract void separateNodeEdges();
    public abstract void visitDecorator(UMLDecorator umlDecorator);
    public abstract void end();

}

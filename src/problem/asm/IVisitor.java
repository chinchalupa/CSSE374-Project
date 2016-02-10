package problem.asm;

/**
 * Created by wrightjt on 1/11/2016.
 */
public interface IVisitor {
    public abstract void visitNodes(INode node);
    public abstract void visitEdges(IEdge edge);

    public abstract void visitDecorator(UMLDecorator umlDecorator);

}

package problem.asm;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class NodeField implements INodeElement {

    private String name;
    private String type;
    private INode containingClass;
    private INode createdClass;

    public NodeField(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public NodeField(String name, String type, INode containingClass, INode createdClass) {
        this.name = name;
        this.type = type;
        this.containingClass = containingClass;
        this.createdClass = createdClass;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getReturnType() {
        return this.type;
    }


    @Override
    public INode getContainingClass() {
        return this.containingClass;
    }

    public INode getCreatedClass() {
        return createdClass;
    }

    @Override
    public String toString() {
        return this.name + this.type;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitField(this);
    }
}

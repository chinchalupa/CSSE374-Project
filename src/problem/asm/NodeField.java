package problem.asm;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class NodeField implements INodeElement {

    private String name;
    private String type;
    private INode containingClass;
    private String dStructure;



    /**
     * A parameter for a field.
     * @param name - The name of the item.
     * @param type - The type of the field or the type of the collection.
     * @param dStructure - The collection type.
     */
    public NodeField(String name, String type, String dStructure) {
        this.name = name;
        this.type = type;
        this.dStructure = dStructure;
    }

    public NodeField(String name, String type, INode containingClass) {
        this.name = name;
        this.type = type;
        this.containingClass = containingClass;
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

    @Override
    public String getCollectionType() {
        return this.dStructure;
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

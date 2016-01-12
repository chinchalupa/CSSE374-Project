package problem.asm;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class NodeField implements INodeElement {

    private String name;
    private String type;

    public NodeField(String name, String type) {
        this.name = name;
        this.type = type;
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
    public void accept(IVisitor visitor) {
        visitor.visitField(this);
    }
}

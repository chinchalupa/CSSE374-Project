package problem.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class ClassNode implements INode {

    private String name;
    private String type;
    private List<NodeField> fields;
    private List<NodeMethod> methods;

    public ClassNode(String name) {
        this.name = name;
        this.type = null;
        this.fields = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    public ClassNode(String name, String type) {
        this.name = name;
        this.type = type;
        this.fields = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public List<NodeMethod> getMethods() {
        return this.methods;
    }

    public List<NodeField> getFields() {
        return this.fields;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitNodes(this);
    }

    public void addField(NodeField field) {
        this.fields.add(field);
    }

    public void addMethod(NodeMethod method) {
        this.methods.add(method);
    }
}

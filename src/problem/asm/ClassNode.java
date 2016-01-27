package problem.asm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class ClassNode implements INode {

    private String extension;
    private String name;
    private String type;
    private List<NodeField> fields;
    private List<NodeMethod> methods;
    private String color;
    private List<String> interfaces;
    private String patternIdentifier;

    public void setPatternIdentifier(String patternIdentifier) {
        this.patternIdentifier = patternIdentifier;
    }

    public String getPatternIdentifier() {

        return patternIdentifier;
    }

    public ClassNode(String name) {
        this.name = name;
        if(this.name.contains(";")) {
            this.name = this.name.replace(";", "");
        }
        this.type = null;
        this.fields = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.interfaces = new ArrayList<>();
        this.color = "#000000";
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

    @Override
    public void addInterface(String itf) {
        this.interfaces.add(itf);
    }

    @Override
    public void setExtension(String string) {
        this.extension = string;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public void addField(NodeField field) {
//        for(NodeField nodeField : this.fields) {
//            if(nodeField.toString().equals(field.toString())) {
//                return;
//            }
//        }
//        System.out.println("Field added");
        this.fields.add(field);
    }

    @Override
    public void addMethod(NodeMethod method) {
//        for(NodeMethod nodeMethod : this.methods) {
//            if(nodeMethod.toString().equals(method.toString())) {
//                return;
//            }
//        }
        this.methods.add(method);
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

package problem.asm.file_elements;

import problem.asm.visitors.ITraversable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wrightjt on 1/11/2016.
 */
public abstract class INode implements ITraversable {

    String name;
    String miniName;



    String type;
    String extension;
    List<String> interfaces;
    List<NodeMethod> methods;
    List<NodeField> fields;

    public INode(String name) {
        this.name = name;

        this.interfaces = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.fields = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public void setMiniName(String miniName) {
        this.miniName = miniName;
    }

    public String getMiniName() {
        return miniName;
    }

    public List<NodeMethod> getMethods() {
        return methods;
    }

    public void addField(NodeField field) {
        this.fields.add(field);
    }

    public List<NodeField> getFields() {
        return this.fields;
    }

    public void addMethod(NodeMethod method) {
        this.methods.add(method);
    }

    public void addInterface(String string) {
        this.interfaces.add(string);
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<String> getInterfaces() {
        return this.interfaces;
    }

    public String getExtends() {
        return this.extension;
    }

    public abstract List<String> getPatternIdentifier();
    public abstract void addPatternIdentifier(String pattern);
    public abstract void setOutlineColor(String color);
    public abstract void setStyle(String style);

    public abstract String getOutlineColor();
    public abstract String getStyle();

}

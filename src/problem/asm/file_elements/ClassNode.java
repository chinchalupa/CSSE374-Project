package problem.asm.file_elements;

import problem.asm.visitors.IVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class ClassNode extends INode {

    private String name;
    private String outlineColor;
    private String style;
    private List<String> patternIdentifier;


    /**
     * Implementation of the INode. Used to hold data about a class for a diagrams.
     * @param name - The name of the class.
     */
    public ClassNode(String name) {
        super(name);
        this.name = name;
        if(this.name.contains(";")) {
            this.name = this.name.replace(";", "");
        }
        this.type = null;
        this.outlineColor = "#000000";
        this.style = "";

        this.patternIdentifier = new ArrayList<>();
    }

    public String getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(String outlineColor) {
        this.outlineColor = outlineColor;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public void addPatternIdentifier(String patternIdentifier) {
        for(String identifier : this.getPatternIdentifier()) {
            if(identifier.equals(patternIdentifier)) {
                return;
            }
        }
        this.patternIdentifier.add(patternIdentifier);
    }

    @Override
    public List<String> getPatternIdentifier() {
        return this.patternIdentifier;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.preVisitNode(this);
        visitor.visitNode(this);
        visitor.postVisitNode(this);
    }
}

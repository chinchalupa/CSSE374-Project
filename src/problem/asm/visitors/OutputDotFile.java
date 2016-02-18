package problem.asm.visitors;

import problem.asm.file_elements.IEdge;
import problem.asm.file_elements.INode;
import problem.asm.file_elements.NodeField;
import problem.asm.file_elements.NodeMethod;
import problem.asm.structures.Config;
import problem.asm.structures.FileGenerator;
import problem.asm.structures.UMLDecorator;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class OutputDotFile implements IVisitor {

    private OutputStream out;
    private FileGenerator fileGenerator;

    public OutputDotFile(OutputStream out, FileGenerator fileGenerator) {
        this.out = out;
        this.fileGenerator = fileGenerator;
        this.startUp();
    }

    private void write(String s) {
        try {
            s = s + "\n";
            out.write(s.getBytes());
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }

    public void startUp() {
        String s = "digraph G {\n\n";
        s += "fontname = \"Bitstream Vera Sans\"\n" + "fontsize = 8\n";
        s += "rankdir=BT\n";
        this.write(s);
    }

    @Override
    public void end() {
        String s = "}";
        this.write(s);
    }

    @Override
    public void visitNode(INode node) {

        if(this.inOurPackage(node.getName())) {

            String s = "ClassT" + node.getMiniName() + " [label = \"{" + node.getMiniName();
            if(node.getPatternIdentifier() != null) {
                s += "\\l";
                for(String identifier : node.getPatternIdentifier()) {
                    s += identifier + "\\l";
                }
            }
            s += "|";
            for (NodeField field : node.getFields()) {
                s += field.getName() + " : " + field.getReturnType() + "\\l";
            }

            s += "|";

            for (NodeMethod method : node.getMethods()) {
                String methodName = method.getName();

                s += fixInvalidCharacters(methodName) + "(";

                for (String arg : method.getArgs()) {
                    s += arg;
                    if (!arg.equals(method.getArgs().get(method.getArgs().size() - 1))) {
                        s += ", ";
                    }
                }
                s += ") : " + method.getReturnType() + "\\l";
            }

            s += "}\"]";
            this.write(s);
        }
    }

    @Override
    public void visitEdge(IEdge edge) {
        String to = edge.getTo();
        String parsedTo = to.substring(to.lastIndexOf("/") + 1, to.length());

        String s = "ClassT" + parsedTo + " -> ClassT" + edge.getFrom();
        this.write(s);
    }

    @Override
    public void preVisitNode(INode node) {
        String s = "node [shape = \"record\" color = \"#000000\" fillcolor = \"" + node.getOutlineColor() + "\" style=\"" + node.getStyle() + "\"]\n";
        this.write(s);
    }

    @Override
    public void postVisitEdge(IEdge edge) {

    }

    @Override
    public void preVisitEdge(IEdge edge) {
        String s = "edge [arrowhead = " + edge.getArrow() + " style = " + edge.getLine() +
                " label = \"" + edge.getText() + "\"]\n";
        this.write(s);
    }

    @Override
    public void postVisitNode(INode node) {

    }

    @Override
    public void separateNodeEdges() {

    }

    @Override
    public void visitDecorator(UMLDecorator umlDecorator) {
        umlDecorator.updateNodes();
    }

    private boolean inOurPackage(String name) {
        String remodeledName = name.replace("/", ".");
        return Config.inPackageConfiguration(remodeledName);
    }

    private String fixInvalidCharacters(String field) {

        return field.replace("<", "").replace(">", "");
    }
}

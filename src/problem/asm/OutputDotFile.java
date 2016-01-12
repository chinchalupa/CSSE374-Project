package problem.asm;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class OutputDotFile implements IVisitor {

    private OutputStream out;

    public OutputDotFile(OutputStream out) {
        this.out = out;
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
        s += "fontname = \"Bitstream Vera Sans\"\n" + "fontsize = 8";
        this.write(s);
    }

    public void end() {
        String s = "}";
        this.write(s);
    }

    @Override
    public void visitNodes(ClassNode node) {


        String s = "node [shape = \"record\" ]\n";
        s += "ClassT" + node.getName() + " [label = \"{" + node.getName() + "|";
        for(INodeElement field : node.getFields()) {
            s += field.getName() + " : " + field.getReturnType() + "\\l";
        }

        s += "|";

        for(NodeMethod method : node.getMethods()) {
            s += method.getName() + "(";

            for(String arg : method.getArgs()) {
                s += arg;
                if(!arg.equals(method.getArgs().get(method.getArgs().size() - 1))) {
                    s += ", ";
                }
            }
            s += ") : " + method.getReturnType() + "\\l";
        }
        s += "}\"]";
        this.write(s);
    }

    @Override
    public void visitEdges(IEdge edge) {
        String s = "edge [arrowhead = " + edge.getArrow() + " style = " + edge.getLine() + " ]\n";
        s += "ClassT" + edge.getTo() + " -> ClassT" + edge.getFrom();
        this.write(s);
    }

    @Override
    public void visitMethod(NodeMethod nodeMethod) {

    }

    @Override
    public void visitField(NodeField nodeField) {

    }
}

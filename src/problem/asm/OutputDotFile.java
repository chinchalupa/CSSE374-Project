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
        s += "fontname = \"Bitstream Vera Sans\"\n" + "fontsize = 8\n";
        s += "rankdir=BT\n";
        this.write(s);
    }

    public void end() {
        String s = "}";
        this.write(s);
    }

    @Override
    public void visitNodes(ClassNode node) {

        if(this.inOurPackage(node.getName())) {
            String name = node.getName().substring(node.getName().lastIndexOf("/") + 1, node.getName().length());

            String s = "node [shape = \"record\" color = \"#000000\" fillcolor = \"" + node.getOutlineColor() + "\" style=\"" + node.getStyle() + "\"]\n";
            s += "ClassT" + name + " [label = \"{" + name;
            if(node.getPatternIdentifier() != null) {
                s += "\\l" + node.getPatternIdentifier();
            }
            s += "|";
            for (NodeField field : node.getFields()) {
                s += field.getName() + " : " + field.getReturnType() + "\\l";
            }

            s += "|";

            for (NodeMethod method : node.getMethods()) {
                String methodName = method.getName();

                if (hasInvalidCharacters(methodName)) {
                    s += methodName + "(";


                    for (String arg : method.getArgs()) {
                        s += arg;
                        if (!arg.equals(method.getArgs().get(method.getArgs().size() - 1))) {
                            s += ", ";
                        }
                    }
                    s += ") : " + method.getReturnType() + "\\l";
                }
            }
                s += "}\"]";
                this.write(s);
        }
    }

    @Override
    public void visitEdges(IEdge edge) {
        String to = edge.getTo();
        String parsedTo = to.substring(to.lastIndexOf("/") + 1, to.length());
        String s = "edge [arrowhead = " + edge.getArrow() + " style = " + edge.getLine() +
                " label = \"" + edge.getText() + "\"]\n";
        s += "ClassT" + parsedTo + " -> ClassT" + edge.getFrom();
        this.write(s);
    }

    @Override
    public void visitMethod(NodeMethod nodeMethod) {

    }

    @Override
    public void visitField(NodeField nodeField) {

    }

    private boolean inOurPackage(String name) {
        String remodeledName = name.replace("/", ".");
        return Config.inPackageConfiguration(remodeledName);
    }

    private boolean hasInvalidCharacters(String field) {

        return !(field.contains("<") || field.contains(">"));
    }
}

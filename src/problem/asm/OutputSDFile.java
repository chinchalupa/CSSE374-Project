package problem.asm;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wrightjt on 1/13/2016.
 */
public class OutputSDFile implements IVisitor {

    private OutputStream outputStream;

    public OutputSDFile(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private void write(String s) {
        try {
            s += "\n";
            outputStream.write(s.getBytes());
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }


    @Override
    public void visitNodes(ClassNode node) {
        String s = String.format("%s:%s \"%s\"", node.getName(), node.getName(), node.getName());
        this.write(s);
    }

    @Override
    public void visitEdges(IEdge edge) {

    }

    public void visitMethod(NodeMethod nodeMethod) {
    }
//        String s = "";
//        INode containingClass = nodeMethod.getContainingClass();
////        if(nodeMethod.getMethodsCalled().isEmpty()) {
//        if(nodeMethod.getParentClassNode() != null)
//            s += nodeMethod.getParentClassNode().getName() + ":" + nodeMethod.getParentClassNode().getName() + "=";
//        else
//            s+= containingClass.getName() + ":";
//        s += containingClass.getName();
//        s += "." + nodeMethod.getReturnType() + " " + nodeMethod.getName();
//
//
//        s += "(";
//        for (String arg : nodeMethod.getArgs()) {
//            s += arg;
//            if (!arg.equals(nodeMethod.getArgs().get(nodeMethod.getArgs().size() - 1))) {
//                s += ", ";
//            }
//        }
//
//        s += ")";

//        }
//
//        else
//            for(NodeMethod node : nodeMethod.getMethodsCalled()) {
//                s += containingClass.getName() + ":" + containingClass.getName() +
//                        "=" + node.getContainingClass().getName() + "." + node.getReturnType() + " " + node.getName() +
//                        "(";
//                for(String arg : node.getArgs()) {
//                    s+= arg;
//                    if(!arg.equals(node.getArgs().get(node.getArgs().size() - 1))) {
//                        s += ", ";
//                    }
//                }
//                s += ")";
//                s += "\n";
//            }
//        this.write(s);
//    }

    public void visitField(NodeField nodeField) {
//        INode containingClass = nodeField.getContainingClass();
//      String s = String.format("%s:%s=%s.new", containingClass.getName(), containingClass.getName(),
//              nodeField.getCreatedClass().getName());
//        this.write(s);
    }

    @Override
    public void visitDecorator(UMLDecorator umlDecorator) {
        return;
    }
}

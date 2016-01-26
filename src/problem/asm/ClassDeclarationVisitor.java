package problem.asm;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
public class ClassDeclarationVisitor extends ClassVisitor {

    public String name;
    public String superName;
    public String[] interfaces;
    private List<ClassNode> classNodes;
    private List<IEdge> edges;
    private ClassNode classNode;
    private ClassNode superNode;
    private String pkg;

    public ClassDeclarationVisitor(int api, ClassNode classNode, List<ClassNode> classNodes, List<IEdge> edges, String pkg) {
        super(api);
        this.classNodes = classNodes;
        this.edges = edges;
        this.classNode = classNode;
        this.pkg = pkg;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){

        this.name = name;
        this.interfaces = interfaces;
//        this.superName = this.superName.substring(this.superName.lastIndexOf("/") + 1, this.superName.length());

        addExtendsArrow(this.classNode.getName(), superName);

//        }
        for(String itf : this.interfaces)
            addInheritanceArrow(this.classNode.getName(), itf);

        super.visit(version, access, name, signature, superName, interfaces);
    }

    public String getName() {
        return name;
    }

    private void addExtendsArrow(String nodeName, String superName) {
        System.out.println("SUpER: " + superName);
        String cleanSuperName = superName.substring(superName.lastIndexOf("/") + 1, superName.length());
        if(inPackage(superName)) {
            IEdge edge = new Edge(nodeName, cleanSuperName, "\"normal\"", "\"solid\"");
            this.edges.add(edge);
        }
    }

    private void addInheritanceArrow(String nodeName, String itfName) {
        // Inherits
        String cleanedName = itfName.substring(itfName.lastIndexOf("/") + 1, itfName.length());
        IEdge edge = new Edge(nodeName, cleanedName, "\"normal\"", "\"dashed\"");
        if(this.inPackage(itfName)) {
            this.edges.add(edge);
        }
    }



    private boolean inPackage(String s) {
        return s.contains(this.pkg);
    }
}
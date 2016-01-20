package problem.asm;
import java.util.Arrays;
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

    public ClassDeclarationVisitor(int api, ClassNode classNode, List<ClassNode> classNodes, List<IEdge> edges) {
        super(api);
        this.classNodes = classNodes;
        this.edges = edges;
        this.classNode = classNode;
//        this.classNode = getClassNode(classNode);

    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){

        this.name = name;
        this.superName = superName;
        this.interfaces = interfaces;

        this.name = this.name.substring(this.name.lastIndexOf("/") + 1, this.name.length());
        String packagedSuper = this.superName;
        this.superName = this.superName.substring(this.superName.lastIndexOf("/") + 1, this.superName.length());


//        getClassNode(this.classNode);
//        if(DesignParser.inPackage(packagedSuper)) {
//            System.out.println("Added for " + this.superName);
            getSuperNode();
            addInheritanceEdge();
//        }
        for(String itf : this.interfaces)
            addImplementsEdges(itf);

        super.visit(version, access, name, signature, superName, interfaces);
    }

    public String getName() {
        return name;
    }

    private void getSuperNode() {
        this.superNode = new ClassNode(this.superName);

        for(ClassNode node : this.classNodes) {
            if(node.toString().equals(superNode.toString())) {
                this.superNode = node;
                return;
            }
        }
//        System.out.println("\nAdded inherited: " + superNode);
        System.out.println("Super node: " + superNode);
        this.classNodes.add(superNode);
    }

//    private ClassNode getClassNode(ClassNode classNode) {
//
//        for(ClassNode node : classNodes) {
//            if(node.toString().equals(classNode.toString())) {
//                classNode = node;
//                return classNode;
//            }
//        }
//        this.classNodes.add(classNode);
//        return classNode;
//    }

    private void addInheritanceEdge() {
        IEdge edge = new DotExtends(this.classNode.getName(), this.superNode.getName());
        this.edges.add(edge);
    }

    private void addImplementsEdges(String itf) {
//        if(DesignParser.inPackage(itf)) {
            itf = itf.substring(itf.lastIndexOf("/") + 1, itf.length());
            ClassNode newNode = new ClassNode(itf);
            IEdge newEdge = new DotImplements(this.classNode.toString(), newNode.toString());
            for(ClassNode node : this.classNodes) {
                if (node.toString().equals(newNode.toString())) {
                    this.edges.add(newEdge);
                    return;
                }
//            }
//            this.classNodes.add(this.classNode);
            this.edges.add(newEdge);
        }
    }
}
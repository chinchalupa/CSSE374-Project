package problem.asm;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
public class ClassDeclarationVisitor extends ClassVisitor {

    public String name;
    public String[] interfaces;
    private List<INode> classNodes;
    private List<IEdge> edges;
    private INode classNode;

    public ClassDeclarationVisitor(int api, INode classNode, List<INode> classNodes, List<IEdge> edges) {
        super(api);
        this.classNodes = classNodes;
        this.edges = edges;
        this.classNode = classNode;
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
        String cleanSuperName = superName.substring(superName.lastIndexOf("/") + 1, superName.length());
            IEdge edge = new Edge(nodeName, cleanSuperName, "\"normal\"", "\"solid\"", "EXTENDS");

        if(Config.inPackageConfiguration(superName)) {
            this.classNode.setExtension(cleanSuperName);
            this.edges.add(edge);
        }
    }

    private void addInheritanceArrow(String nodeName, String itfName) {
        // Inherits
        String cleanedName = itfName.substring(itfName.lastIndexOf("/") + 1, itfName.length());
        IEdge edge = new Edge(nodeName, cleanedName, "\"normal\"", "\"dashed\"", "IMPLEMENTS");
        if(Config.inPackageConfiguration(itfName)) {
            this.classNode.addInterface(cleanedName);
            this.edges.add(edge);
        }
    }




}
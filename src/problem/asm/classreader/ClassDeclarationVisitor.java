package problem.asm.classreader;

import org.objectweb.asm.ClassVisitor;
import problem.asm.structures.Config;
import problem.asm.file_elements.INode;
import problem.asm.structures.ItemHandler;

public class ClassDeclarationVisitor extends ClassVisitor {

    public String name;
    public String[] interfaces;

    private ItemHandler itemHandler;

    public ClassDeclarationVisitor(int api, ItemHandler itemHandler) {
        super(api);
        this.itemHandler = itemHandler;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){

        this.name = name;
        this.interfaces = interfaces;
        INode activeNode = this.itemHandler.getActiveNode();

        addExtendsArrow(activeNode.getMiniName(), superName);

//        }
        for(String itf : this.interfaces)
            addInheritanceArrow(activeNode.getMiniName(), itf);

        super.visit(version, access, name, signature, superName, interfaces);
    }

    public String getName() {
        return name;
    }

    /**
     * Adds an extends arrow to the list of edges.
     * @param nodeName - The name of the node.
     * @param superName - The name of the class that is being extended from.
     */
    private void addExtendsArrow(String nodeName, String superName) {
        String cleanSuperName = superName.substring(superName.lastIndexOf("/") + 1, superName.length());

        if(Config.inPackageConfiguration(superName)) {
            this.itemHandler.getActiveNode().setExtension(cleanSuperName);
            this.itemHandler.createEdge(nodeName, cleanSuperName, "\"onormal\"", "\"solid\"", "EXTENDS");
        }
    }

    /**
     * Adds an inheritance arrow to the list of edges.
     * @param nodeName - The name of the node.
     * @param itfName - The name of the interface that is being inherited from.
     */
    private void addInheritanceArrow(String nodeName, String itfName) {
        String cleanedName = itfName.substring(itfName.lastIndexOf("/") + 1, itfName.length());
        if(Config.inPackageConfiguration(itfName)) {
            this.itemHandler.getActiveNode().addInterface(cleanedName);
            this.itemHandler.createEdge(nodeName, cleanedName, "\"onormal\"", "\"dashed\"", "IMPLEMENTS");
        }
    }




}
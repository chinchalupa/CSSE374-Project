package problem.asm;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import java.awt.*;
import java.util.List;

public class ClassFieldVisitor extends ClassVisitor{

    private ClassNode classNode;
    private List<IEdge> edges;
    private String pkg;

    public ClassFieldVisitor(int api){
        super(api);
    }
    public ClassFieldVisitor(int api, ClassVisitor decorated, ClassNode node, List<IEdge> edges, String pkg) {
        super(api, decorated);

        this.classNode = node;
        this.edges = edges;
        this.pkg = pkg;
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {

        FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);

        String type = Type.getType(desc).getClassName();
        type = type.replace(".", "/");
        if(signature != null) {
            addFieldToNode(this.classNode.getName(), type, signature);
        }
        else {
            addFieldToNode(this.classNode.getName(), type);
        }
//        String type = Type.getType(desc).getInternalName();
//        type = type.substring(type.lastIndexOf("/") + 1, type.length());
//        signature = getCollectionType(signature);
//
//        if(signature != null) {
//            NodeField newField = new NodeField(name, type + "\\<" + signature + "\\>");
//            this.classNode.addField(newField);
//            addNewUses(this.classNode.getName(), signature);
//        } else {
//            NodeField newField = new NodeField(name, type);
//            this.classNode.addField(newField);
//
//            addNewUses(this.classNode.getName(), type);
//        }

        return toDecorate;
    }

    private void addFieldToNode(String name, String returnType) {
        String cleanName = name.substring(name.lastIndexOf("/") + 1, name.length());
        String cleanReturnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
        NodeField nodeField = new NodeField(cleanName, cleanReturnType);
        this.classNode.addField(nodeField);

        addNewUses(name, returnType);
    }

    private void addFieldToNode(String name, String returnType, String signature) {
        String cleanName = name.substring(name.lastIndexOf("/") + 1, name.length());
        String cleanReturnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
        String cleanSignature = signature.substring(signature.lastIndexOf("/") + 1, signature.indexOf(";"));
        String cleanReturn = cleanReturnType + "\\<" + cleanSignature + "\\>";
        NodeField nodeField = new NodeField(cleanName, cleanReturn);
        this.classNode.addField(nodeField);

        addNewUses(name, signature.substring(signature.indexOf(";")));
    }

    private void addNewUses(String name, String returnType) {
        // Uses arrow
        if(inPackage(returnType)) {
            returnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
            Edge newArrow = new Edge(name, returnType, "\"vee\"", "\"solid\"");
            for (IEdge edge : this.edges) {
                if (edge.toString().equals(newArrow.toString())) {
                    return;
                }
            }
            this.edges.add(newArrow);
        }
    }

    private boolean inPackage(String s) {
        return s.contains(this.pkg);
    }
}
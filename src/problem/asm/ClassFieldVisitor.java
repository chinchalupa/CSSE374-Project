package problem.asm;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import java.util.List;

public class ClassFieldVisitor extends ClassVisitor{

    private ClassNode classNode;
    private List<IEdge> edges;
    private String pkg;

    public ClassFieldVisitor(int api){
        super(api);
    }
    public ClassFieldVisitor(int api, ClassVisitor decorated, ClassNode node, List<IEdge> edges) {
        super(api, decorated);

        this.classNode = node;
        this.edges = edges;
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {

        FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);

        String type = Type.getType(desc).getClassName();
        type = type.replace(".", "/");
        type = type.replace("<", "\\<").replace(">", "\\>");

        if(signature != null) {
            signature = signature.replace("<", "\\<").replace(">", "\\>");
        }

        if(signature != null) {
            addFieldToNode(name, type, signature);
        }
        else {
            addFieldToNode(name, type);
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
        NodeField nodeField = new NodeField(cleanName, cleanReturnType, "");
        this.classNode.addField(nodeField);

        addNewUses(this.classNode.getName(), returnType);
    }

    private void addFieldToNode(String name, String returnType, String signature) {
        String cleanName = name.substring(name.lastIndexOf("/") + 1, name.length());
        String cleanReturnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
        String cleanSignature = signature.substring(signature.lastIndexOf("/") + 1, signature.indexOf(";"));
        String cleanReturn = cleanReturnType + "\\<" + cleanSignature + "\\>";
        NodeField nodeField = new NodeField(cleanName, cleanReturn, cleanSignature);
        this.classNode.addField(nodeField);

        addNewUses(this.classNode.getName(), returnType);
        addNewUses(this.classNode.getName(), cleanSignature);
    }

    private void addNewUses(String name, String returnType) {
        // Uses arrow
        if(Config.inPackageConfiguration(returnType)) {
            returnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
            Edge newArrow = new Edge(name, returnType, "\"vee\"", "\"dashed\"", "USES");
            for (IEdge edge : this.edges) {
                if (edge.toString().equals(newArrow.toString())) {
                    if(edge.getLineName().equals("EXTENDS") || edge.getLineName().equals("IMPLEMENTS")) {
                        break;
                    } else {
                        return;
                    }
                }
            }
            this.edges.add(newArrow);
        }
    }
}
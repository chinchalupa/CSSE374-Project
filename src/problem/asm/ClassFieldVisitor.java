package problem.asm;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import java.util.List;

public class ClassFieldVisitor extends ClassVisitor{

    private ItemHandler itemHandler;

    public ClassFieldVisitor(int api){
        super(api);
    }
    public ClassFieldVisitor(int api, ClassVisitor decorated, ItemHandler itemHandler) {
        super(api, decorated);

        this.itemHandler = itemHandler;
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
            System.out.println("NAME: " + name + " " + signature + " " + type);
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
        INode activeNode = this.itemHandler.getActiveNode();

        String cleanName = name.substring(name.lastIndexOf("/") + 1);
        String cleanReturnType = returnType.substring(returnType.lastIndexOf("/") + 1);
        NodeField nodeField = new NodeField(cleanName, cleanReturnType, "");
        activeNode.addField(nodeField);

        addNewUses(activeNode.getMiniName(), returnType);
    }

    private void addFieldToNode(String name, String returnType, String signature) {
        INode activeNode = this.itemHandler.getActiveNode();

        String cleanName = name.substring(name.lastIndexOf("/") + 1, name.length());
        String cleanReturnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
        System.out.println("SIGNATURE: " + signature);
        String cleanSignature = signature.substring(signature.lastIndexOf("/") + 1, signature.lastIndexOf(";") - 3);
        String cleanReturn = cleanReturnType + "\\<" + cleanSignature + "\\>";
        NodeField nodeField = new NodeField(cleanName, cleanReturn, cleanSignature);
        activeNode.addField(nodeField);

        addNewUses(activeNode.getMiniName(), returnType);
        addNewAggregates(activeNode.getMiniName(), cleanSignature);
    }

    private void addNewUses(String name, String returnType) {
        // Uses arrow
        if(Config.inPackageConfiguration(returnType)) {
            this.itemHandler.createEdge(name, returnType, "\"vee\"", "\"dashed\"", "USES");
        }
    }

    private void addNewAggregates(String name, String returnType) {
        // Aggregates arrow
        System.out.println("Return type " + returnType);
        if(Config.inPackageConfiguration(returnType)) {
            System.out.println("PUSHING AGGREGATES ARROW");
            this.itemHandler.createEdge(name, returnType, "\"odiamond\"", "\"solid\"", "AGGREGATES");
        }
    }
}
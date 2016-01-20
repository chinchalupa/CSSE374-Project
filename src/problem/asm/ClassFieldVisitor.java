package problem.asm;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import java.util.List;

public class ClassFieldVisitor extends ClassVisitor{

    private ClassNode classNode;
    private List<IEdge> edges;

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
        type = type.substring(type.lastIndexOf(".") + 1, type.length());
        signature = getCollectionType(signature);

        if(signature != null) {
            NodeField newField = new NodeField(name, type + "<" + signature + ">");
            this.classNode.addField(newField);

            if(DesignParser.inPackage(desc)) {
                addNewUses(this.classNode.getName(), signature);
            }
        } else {
            NodeField newField = new NodeField(name, type);
            this.classNode.addField(newField);

            if(DesignParser.inPackage(desc)) {
                addNewUses(this.classNode.getName(), type);
            }
        }

//        this.classNode.addField(newEdge);

//        System.out.println("=====METHOD=====");
//        System.out.println("DESC: " + desc);
//        System.out.println(Type.getType(signature));

//        if(DesignParser.inPackage(desc)) {
//            String association = desc.substring(desc.lastIndexOf("/") + 1, desc.indexOf(";"));
//
//            if(signature != null) {
//                String otherClass = signature.substring(signature.lastIndexOf("/") + 1, signature.indexOf(";"));
//                addNewUses(this.classNode.getName(), otherClass);
//            }
//
////            DotUses usesArrow = new DotUses(this.classNode.getName(), association);
//            addNewUses(this.classNode.getName(), association);
////            addNewUses(this.classNode.getName(), association);
////            this.edges.add(new DotUses(this.classNode.getName(), association));
//        }

        return toDecorate;
    }

    private void addNewUses(String name, String returnType) {
        DotUses newArrow = new DotUses(name, returnType);
        for(IEdge edge : this.edges) {
            String temp = edge.getTo() + edge.getFrom();
//            System.out.println(edge.getTo() + edge.getFrom());
            if(temp.equals(newArrow.toString())) {
//                System.out.println("Duplicate detected uses");
                return;
            }
        }
        this.edges.add(newArrow);
    }

    private String getCollectionType(String sig) {
        if(sig == null) {
            return sig;
        } else {
            sig = sig.substring(sig.lastIndexOf("/") + 1, sig.indexOf(";"));
        }
        return sig;
    }
}
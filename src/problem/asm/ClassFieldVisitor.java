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

//        System.out.println("Name: " + name);
//        System.out.println("DESC: " + desc);
//        System.out.println("SIG: " + signature);
//        System.out.println("Value: " + value);


        String type = Type.getType(desc).getClassName();
        type = type.substring(type.lastIndexOf(".") + 1, type.length());

        this.classNode.addField(new NodeField(name, type));

        if(desc.contains(";") && desc.contains("/") && !desc.contains("String")) {
            String association = desc.substring(desc.lastIndexOf("/") + 1, desc.indexOf(";"));

            if(signature != null) {
                String otherClass = signature.substring(signature.lastIndexOf("/") + 1, signature.indexOf(";"));
                this.edges.add(new DotUses(this.classNode.getName(), otherClass));
            }

            this.edges.add(new DotUses(this.classNode.getName(), association));
        }

        return toDecorate;
    }
}
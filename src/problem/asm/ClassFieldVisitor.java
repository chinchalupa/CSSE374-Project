package problem.asm;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
import pizza.pizzas.SimplePizzaFactory;

import java.util.List;

public class ClassFieldVisitor extends ClassVisitor{

    private dotClass dClass;
    private List<dotEdge> edges;

    public ClassFieldVisitor(int api){
        super(api);
    }
    public ClassFieldVisitor(int api, ClassVisitor decorated, dotClass dClass, List<dotEdge> edges) {
        super(api, decorated);

        this.dClass = dClass;
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

        this.dClass.addField(new dotField(type, name));

        if(desc.contains(";") && desc.contains("/")) {
            String association = desc.substring(desc.lastIndexOf("/") + 1, desc.indexOf(";"));

            if(signature != null) {
                String additionalInfo = signature.substring(signature.lastIndexOf("/") + 1, signature.indexOf(";"));
                this.edges.add(new dotUses(this.dClass.getName(), additionalInfo));
            }
            this.edges.add(new dotUses(this.dClass.getName(), association));
        }

        return toDecorate;
    }
}
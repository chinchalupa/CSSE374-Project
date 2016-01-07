package problem.asm;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import java.util.List;

public class ClassFieldVisitor extends ClassVisitor{

    private dotClass dClass;

    public ClassFieldVisitor(int api){
        super(api);
    }
    public ClassFieldVisitor(int api, ClassVisitor decorated, dotClass dClass, List<dotEdge> edges) {
        super(api, decorated);

        this.dClass = dClass;
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {

        FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);

        System.out.println("Name: " + name);

        String type = Type.getType(desc).getClassName();

        this.dClass.addField(new dotField(type, name));

        return toDecorate;
    }
}
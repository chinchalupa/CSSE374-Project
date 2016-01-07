package problem.asm;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
public class ClassFieldVisitor extends ClassVisitor{

    private String type;
    private String name;

    private dotClass dClass;

    public ClassFieldVisitor(int api){
        super(api);
    }
    public ClassFieldVisitor(int api, ClassVisitor decorated, dotClass dClass) {
        super(api, decorated);
        this.dClass = dClass;
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);

        String type = Type.getType(desc).getClassName();

        this.dClass.addField(new dotField(type, name));

        return toDecorate;
    }
}
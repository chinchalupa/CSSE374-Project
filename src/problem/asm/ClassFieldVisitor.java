package problem.asm;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
public class ClassFieldVisitor extends ClassVisitor{

    private String type;
    private String name;

    public String getFieldType() {
        return type;
    }

    public String getFieldName() {
        return name;
    }

    public ClassFieldVisitor(int api){
        super(api);
    }
    public ClassFieldVisitor(int api, ClassVisitor decorated) {
        super(api, decorated);
    }
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
        String type = Type.getType(desc).getClassName();
        System.out.println(" "+type+" "+ name);
        this.type = type;
        this.name = name;
// TODO: add this field to your internal representation of the current class.
// What is a good way to know what the current class is?
        return toDecorate;
    };
}
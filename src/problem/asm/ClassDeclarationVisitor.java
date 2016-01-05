package problem.asm;
import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;
public class ClassDeclarationVisitor extends ClassVisitor {

    public String name;
    public String superName;
    public String[] interfaces;

    public ClassDeclarationVisitor(int api){
        super(api);
    }
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
//        System.out.println("Class: "+name+" extends "+superName+" implements "+Arrays.toString(interfaces));

//        System.out.println("-------------------------------");
        this.name = name;
        this.superName = superName;
        this.interfaces = interfaces;
// TODO: construct an internal representation of the class for later use by decorators
        super.visit(version, access, name, signature, superName, interfaces);
    }

    public String getName() {
        return name;
    }

    public String getSuperName() {
        return superName;
    }

    public String[] getInterfaces() {
        return interfaces;
    }
}
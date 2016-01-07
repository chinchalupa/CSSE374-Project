package problem.asm;
import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;
public class ClassDeclarationVisitor extends ClassVisitor {

    public String name;
    public String superName;
    public String[] interfaces;

    public ClassDeclarationVisitor(int api) {
        super(api);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){

        this.name = name;
        this.superName = superName;
        this.interfaces = interfaces;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    public String getName() {
        return name;
    }
}
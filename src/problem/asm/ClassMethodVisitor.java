package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

public class ClassMethodVisitor extends ClassVisitor {

    private dotClass dClass;

    public ClassMethodVisitor(int api) {
        super(api);
    }

    public ClassMethodVisitor(int api, ClassVisitor decorated, dotClass dClass) {
        super(api, decorated);
        this.dClass = dClass;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

        dotMethod dMethod = new dotMethod(addAccessLevel(access), addReturnType(desc), name, addArguments(desc));
        this.dClass.addMethod(dMethod);
        addAccessLevel(access);
        addReturnType(desc);
        addArguments(desc);

// TODO: add the current method to your internal representation of the current class
// What is a good way for the code to remember what the current class is?
        return toDecorate;
    }

    public String addAccessLevel(int access){
        String level="";
        if((access&Opcodes.ACC_PUBLIC)!=0){
            level="public";
        }else if((access&Opcodes.ACC_PROTECTED)!=0){
            level="protected";
        }else if((access&Opcodes.ACC_PRIVATE)!=0){
            level="private";
        }else{
            level="default";
        }
//        System.out.println("access level: "+level);
// TODO: ADD this information to your representation of the current method.
        return level;
    }

    public String addReturnType(String desc){
        String returnType = Type.getReturnType(desc).getClassName();
//        System.out.println("return type: " + returnType);
        return returnType;
    }

    public List<String> addArguments(String desc){
        ArrayList<String> list = new ArrayList<>();
        Type[] args = Type.getArgumentTypes(desc);
        for(int i=0; i< args.length; i++){
            list.add(args[i].getClassName());
//            System.out.println("arg "+i+": "+arg);
// TODO: ADD this information to your representation of the current method.
        }
        return list;
    }

}
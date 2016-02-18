package problem.asm.classreader;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import problem.asm.structures.Config;
import problem.asm.file_elements.INode;
import problem.asm.structures.ItemHandler;
import problem.asm.file_elements.NodeMethod;

import java.util.ArrayList;
import java.util.List;

public class ClassMethodVisitor extends ClassVisitor {

    private ItemHandler itemHandler;

    public ClassMethodVisitor(int api) {
        super(api);
    }

    public ClassMethodVisitor(int api, ClassVisitor decorated, ItemHandler itemHandler) {
        super(api, decorated);
        this.itemHandler = itemHandler;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//        System.out.println("==============METHOD=================");
//        System.out.println("DESC: " + desc);
//        System.out.println("METHODName: " + name);
//        System.out.println("SIGNATURE: " + signature);
//        System.out.println("==========METHOD===========");
        MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor methodDecorator = toDecorate;

        String returnType = null;
        if(signature != null)
            returnType = addReturnType(desc);
        else
            returnType = addReturnType(desc);

        INode activeNode = this.itemHandler.getActiveNode();

        List<String> arguments = addArguments(desc);
        String accessLevel = addAccessLevel(access);
        String cleanReturnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
        List<String> cleanArgs = new ArrayList<>();
        for(String arg : arguments) {
            cleanArgs.add(arg.substring(arg.lastIndexOf("/") + 1, arg.length()));
        }
        NodeMethod nodeMethod = new NodeMethod(name, cleanReturnType, cleanArgs, accessLevel, activeNode);

        methodDecorator = new ClassMethodInstanceVisitor(Opcodes.ASM5, toDecorate, nodeMethod, itemHandler);
        activeNode.addMethod(nodeMethod);

        // dotAssociates
        for(int i = 0; i < arguments.size(); i++) {
            if(Config.inPackageConfiguration(arguments.get(i))) {
                addNewUses(activeNode.getMiniName(), cleanArgs.get(i));
            }
        }
        if(Config.inPackageConfiguration(returnType)) {
            addNewUses(activeNode.getMiniName(), cleanReturnType);
        }

        return methodDecorator;
    }


    public String addAccessLevel(int access){
        String level="";
        if((access&Opcodes.ACC_PUBLIC)!=0){
            level="+";
        }else if((access&Opcodes.ACC_PROTECTED)!=0){
            level="#";
        }else if((access&Opcodes.ACC_PRIVATE)!=0){
            level="-";
        }else if((access&Opcodes.ACC_STATIC) != 0) {
            level="&";
        }
        else{
            level="default";
        }
        return level;
    }

    /**
     * Adds a return value.
     * @param desc - The descriptor for the method.
     * @return The return type.
     */
    public String addReturnType(String desc){

        Type returnValue = Type.getReturnType(desc);
        String returnType = returnValue.getClassName();
        returnType = returnType.replace(".", "/");
        addNewAssociationArrow(this.itemHandler.getActiveNode().getMiniName(), returnType);
        return returnType;
    }

    public List<String> addArguments(String desc){
        ArrayList<String> list = new ArrayList<>();
        Type[] args = Type.getArgumentTypes(desc);
        for(int i=0; i< args.length; i++){
            String className = args[i].getClassName();
            className = className.replace(".", "/");
            list.add(className);
        }
        return list;
    }


    private void addNewUses(String name, String returnType) {
        // Uses
        if(Config.inPackageConfiguration(returnType)) {
            this.itemHandler.createEdge(name, returnType, "\"vee\"", "\"dashed\"", "USES");
        }
    }

    private void addNewAssociationArrow(String name, String returnType) {
        String cleanReturnType =  returnType.substring(returnType.lastIndexOf("/") + 1);
        if(Config.inPackageConfiguration(returnType)) {
            this.itemHandler.createEdge(name, cleanReturnType, "\"vee\"", "\"solid\"", "ASSOCIATES");
        }
    }


}
package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

public class ClassMethodVisitor extends ClassVisitor {

    private ClassNode classNode;
    private List<IEdge> edges;
    private List<ClassNode> nodes;
    private String pkg;

    public ClassMethodVisitor(int api) {
        super(api);
    }

    public ClassMethodVisitor(int api, ClassVisitor decorated, ClassNode node, List<IEdge> edges, List<ClassNode> nodes, String pkg) {
        super(api, decorated);
        this.classNode = node;
        this.edges = edges;
        this.nodes = nodes;
        this.pkg = pkg;
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


        String returnType = addReturnType(desc);
        List<String> arguments = addArguments(desc);
        String accessLevel = addAccessLevel(access);
        String cleanReturnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
        List<String> cleanArgs = new ArrayList<>();
        for(String arg : arguments) {
            cleanArgs.add(arg.substring(arg.lastIndexOf("/") + 1, arg.length()));
        }
        NodeMethod nodeMethod = new NodeMethod(name, cleanReturnType, cleanArgs, accessLevel, this.classNode, null);

//        methodDecorator = new ClassMethodInstanceVisitor(Opcodes.ASM5, toDecorate, nodeMethod, this.classNode, this.nodes);
//        MethodVisitor toDecorateMore = new ClassMethodInstanceVisitor(Opcodes.ASM5, toDecorate);
        this.classNode.addMethod(nodeMethod);

        // dotAssociates
        for(int i = 0; i < arguments.size(); i++) {
            if(inPackage(arguments.get(i))) {
                addNewUses(this.classNode.getName(), cleanArgs.get(i));
            }
        }
        if(inPackage(returnType)) {
            addNewUses(this.classNode.getName(), cleanReturnType);
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

    public String addReturnType(String desc){
        String returnType = Type.getReturnType(desc).getClassName();
        returnType = returnType.replace(".", "/");
        addNewAssociationArrow(this.classNode.getName(), returnType);
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
        if(inPackage(returnType)) {
            Edge newArrow = new Edge(name, returnType, "\"vee\"", "\"dashed\"");
            for (IEdge edge : this.edges) {
                String temp = edge.getTo() + " " + edge.getFrom();
                if (temp.equals(newArrow.toString())) {
//                System.out.println("Duplicate detected uses");
                    return;
                }
            }
            this.edges.add(newArrow);
        }
    }

    private void addNewAssociationArrow(String name, String returnType) {
        String cleanReturnType =  returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
        if(inPackage(returnType)) {
            Edge newArrow = new Edge(name, cleanReturnType, "\"vee\"", "\"solid\"");
            for (IEdge edge : this.edges) {
                String temp = edge.getTo() + " " + edge.getFrom();
                if (temp.equals(newArrow.toString())) {
                    return;
                }
            }
            this.edges.add(newArrow);
        }
    }

    private boolean inPackage(String s) {
        return s.contains(this.pkg);
    }
}
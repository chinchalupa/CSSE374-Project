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

    public ClassMethodVisitor(int api) {
        super(api);
    }

    public ClassMethodVisitor(int api, ClassVisitor decorated, ClassNode node, List<IEdge> edges, List<ClassNode> nodes) {
        super(api, decorated);
        this.classNode = node;
        this.edges = edges;
        this.nodes = nodes;
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

        NodeMethod nodeMethod = new NodeMethod(name, addReturnType(desc), addArguments(desc), addAccessLevel(access), this.classNode, null);
        nodeMethod = this.getNodeMethod(nodeMethod);

//        if(this.classNode.getName().equals("DesignParser")) {
//            System.out.println(nodeMethod.getName());
//        }

        methodDecorator = new ClassMethodInstanceVisitor(Opcodes.ASM5, toDecorate, nodeMethod, this.classNode, this.nodes);

//        MethodVisitor toDecorateMore = new ClassMethodInstanceVisitor(Opcodes.ASM5, toDecorate);

        // dotAssociates
        for(String arg : addArguments(desc)) {

            if(DesignParser.inPackage(arg)) {
                String association = arg.substring(arg.lastIndexOf(".") + 1, arg.length());
                addNewUses(this.classNode.getName(), association);
            }

//            if(arg.contains(".")) {
//                String association = arg.substring(arg.lastIndexOf(".") + 1, arg.length());
////                System.out.println("Association: " + association);
//                if(!association.contains("String")) {
//                    addNewUses(this.classNode.getName(), association);
//                }
//            }
        }

        return methodDecorator;
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
        return level;
    }

    public String addReturnType(String desc){
        String returnType = Type.getReturnType(desc).getClassName();
        if(DesignParser.inPackage(returnType)) {

            returnType = returnType.substring(returnType.lastIndexOf(".") + 1, returnType.length());
            addNewAssociation(this.classNode.getName(), returnType);
        }
        return returnType;
    }

    public List<String> addArguments(String desc){
        ArrayList<String> list = new ArrayList<>();
        Type[] args = Type.getArgumentTypes(desc);
        for(int i=0; i< args.length; i++){
            String className = args[i].getClassName();
            list.add(className.substring(className.lastIndexOf(".") + 1, className.length()));
        }
        return list;
    }


    private void addNewUses(String name, String returnType) {
        DotUses newArrow = new DotUses(name, returnType);
        for(IEdge edge : this.edges) {
            String temp = edge.getTo() + edge.getFrom();
            if(temp.equals(newArrow.toString())) {
//                System.out.println("Duplicate detected uses");
                return;
            }
        }
        this.edges.add(newArrow);
    }

    private void addNewAssociation(String name, String returnType) {
        DotAssociates newArrow = new DotAssociates(name, returnType);
        for(IEdge edge : this.edges) {
            String temp = edge.getTo() + edge.getFrom();
            if(temp.equals(newArrow.toString())) {
//                System.out.println("Duplicate detected");
                return;
            }
        }
        this.edges.add(newArrow);
    }

    private NodeMethod getNodeMethod(NodeMethod createdMethod) {
        for(NodeMethod nodeMethod : this.classNode.getMethods()) {
            if(nodeMethod.toString().equals(createdMethod.toString())) {
                return nodeMethod;
            }
        }
        this.classNode.addMethod(createdMethod);
//        if(this.classNode.getName().equals("DesignParser")) {
////            System.out.println("ADDED: " + createdMethod.toString());
//        }
        return createdMethod;
    }
}
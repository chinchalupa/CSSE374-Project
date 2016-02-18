package problem.asm.classreader;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import problem.asm.file_elements.ClassNode;
import problem.asm.file_elements.INode;
import problem.asm.structures.ItemHandler;
import problem.asm.file_elements.NodeMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wrightjt on 1/13/2016.
 */
public class ClassMethodInstanceVisitor extends MethodVisitor {

    private NodeMethod nodeMethod;
    private ItemHandler itemHandler;

    public ClassMethodInstanceVisitor(int i, MethodVisitor methodVisitor, NodeMethod nodeMethod, ItemHandler itemHandler) {
        super(i, methodVisitor);

        this.nodeMethod = nodeMethod;
        this.itemHandler = itemHandler;
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

//        System.out.println(name + " " + owner);
        String fullOwner = owner.replace("/", ".");
        String miniOwner = owner.substring(owner.lastIndexOf("/") + 1);

        //Check if owner is an existing class
        INode node = new ClassNode(fullOwner);
        node.setMiniName(miniOwner);
        boolean nodeAlreadyExists = false;

        for(INode finishedNode : this.itemHandler.getCreatedNodes()) {
            if(finishedNode.toString().equals(node.toString())) {
                nodeAlreadyExists = true;
                break;
            }
        }
        for(INode inProgressNode : this.itemHandler.getNodeStack()){
            if(inProgressNode.toString().equals(node.toString())) {
                nodeAlreadyExists = true;
                break;
            }
        }

        String returnType = addReturnType(desc);
        returnType = returnType.substring(returnType.lastIndexOf("/") + 1);

        List<String> cleanArgs = new ArrayList<>();
        for(String arg : addArguments(desc)) {
            cleanArgs.add(arg.substring(arg.lastIndexOf("/") + 1));
        }

        NodeMethod nodeMethod = new NodeMethod(name, returnType, cleanArgs, null, node);


        if(!nodeAlreadyExists) {
            this.itemHandler.offer(node);
            node.addMethod(nodeMethod);
            this.nodeMethod.addMethodCalled(nodeMethod);
//            System.out.println("Added called method: " + this.itemHandler.getActiveNode().getName() + " " + this.nodeMethod.getName());
        }
        else {
            this.nodeMethod.addMethodCalled(nodeMethod);
//            System.out.println("Added method on existing method: " + nodeMethod.getName() + " " + this.itemHandler.getActiveNode().getName());
        }



//            if (DesignParser.inPackage(owner)) {
//                if(name.equals("shuffle")) {
//
//                    System.out.println("=====Method: " + this.nodeMethod.getContainingClass().getName());
//                    System.out.println("Owner: " + owner);
//                    System.out.println("Name of Method: " + name);
//                    System.out.println("Description: " + desc + "\n");
//                }

//                owner = owner.substring(owner.lastIndexOf("/") + 1, owner.length());
//                if(owner.contains("$")) {
//                    owner = owner.substring(0, owner.indexOf("$"));
//                }
//                INode tempNode = getAddedClassNode(owner);

//                boolean noExists = true;
////                NodeMethod tempMethod = new NodeMethod(name, addReturnType(desc), addArguments(desc), null, tempNode, this.nodeMethod.getContainingClass());
////                System.out.println(tempMethod.toString());
//                for(NodeMethod method : tempNode.getMethods()) {
//                    if(method.toString().equals(tempMethod.toString())) {
//                        tempMethod = method;
//                        tempMethod.setParentClassNode(this.nodeMethod.getContainingClass());
//                        noExists = false;
//                    }
//                }
//
//                if(noExists) {
//                    tempNode.addMethod(tempMethod);
//                }
////                System.out.println("HAS METHOD: " + tempMethod.getName() + " " + tempMethod );
//
//                this.nodeMethod.addMethodCalled(tempMethod);
            }
//        }

//    @Override
//    public void visitTypeInsn(int i, String s) {
//        s = s.substring(s.lastIndexOf("/") + 1, s.length());
//        for(ClassNode node : this.classNodes) {
//            if(node.getName().equals(s)) {
//                return;
//            }
//        }
//        this.classNodes.add(new ClassNode(s));

//        if(DesignParser.inPackage(s)) {
//            System.out.println("Created: " + s);
//        }
//        super.visitTypeInsn(i, s);
//    }
//
//    @Override
//    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
////        if(DesignParser.inPackage(owner) && DesignParser.inPackage(desc)) {
////            System.out.println("Owner: " + owner);
////            System.out.println("Name: " + name);
////            System.out.println("Desc: " + desc);
//
//            owner = owner.substring(owner.lastIndexOf("/") + 1, owner.length());
//            String newdesc = desc.substring(desc.lastIndexOf("/") + 1, desc.length());
//
//            INode toNode = getAddedClassNode(newdesc);
//            INode fromNode = getAddedClassNode(owner);
//
//            String type = Type.getType(desc).getClassName();
//
//            type = type.substring(type.lastIndexOf("/") + 1, type.length());
//
//            addGeneratedClassNode(fromNode, toNode);
//
////        }
//    }
//
    public String addAccessLevel(int access){
        String level="";
        if((access& Opcodes.ACC_PUBLIC)!=0){
            level="+";
        }else if((access&Opcodes.ACC_PROTECTED)!=0){
            level="#";
        }else if((access&Opcodes.ACC_PRIVATE)!=0){
            level="-";
        }else if((access&Opcodes.ACC_STATIC) != 0){
            level="&";
        } else {
            level = "default";
        }
        return level;
    }

    public String addReturnType(String desc){
        Type returnValue = Type.getReturnType(desc);
        String returnType = returnValue.getClassName();
        returnType = returnType.replace(".", "/");
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
}

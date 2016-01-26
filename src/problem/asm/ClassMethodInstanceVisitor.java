package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import sun.security.krb5.internal.crypto.Des;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wrightjt on 1/13/2016.
 */
public class ClassMethodInstanceVisitor extends MethodVisitor {

    private List<ClassNode> classNodes;
    private NodeMethod nodeMethod;
    private ClassNode node;

    public ClassMethodInstanceVisitor(int i, MethodVisitor methodVisitor, NodeMethod nodeMethod, ClassNode node, List<ClassNode> classNodes) {
        super(i, methodVisitor);

        this.nodeMethod = nodeMethod;
        this.classNodes = classNodes;
        this.node = node;
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {



//        ClassDeclarationVisitor toDecorate = new ClassDeclarationVisitor(Opcodes.ASM5);

//            if (DesignParser.inPackage(owner)) {
//                if(name.equals("shuffle")) {
//
//                    System.out.println("=====Method: " + this.nodeMethod.getContainingClass().getName());
//                    System.out.println("Owner: " + owner);
//                    System.out.println("Name of Method: " + name);
//                    System.out.println("Description: " + desc + "\n");
//                }

                owner = owner.substring(owner.lastIndexOf("/") + 1, owner.length());
//                if(owner.contains("$")) {
//                    owner = owner.substring(0, owner.indexOf("$"));
//                }
                ClassNode tempNode = getAddedClassNode(owner);

                boolean noExists = true;
                NodeMethod tempMethod = new NodeMethod(name, addReturnType(desc), addArguments(desc), null, tempNode, this.nodeMethod.getContainingClass());
//                System.out.println(tempMethod.toString());
                for(NodeMethod method : tempNode.getMethods()) {
                    if(method.toString().equals(tempMethod.toString())) {
                        tempMethod = method;
                        tempMethod.setParentClassNode(this.nodeMethod.getContainingClass());
                        noExists = false;
                    }
                }

                if(noExists) {
                    tempNode.addMethod(tempMethod);
                }
//                System.out.println("HAS METHOD: " + tempMethod.getName() + " " + tempMethod );

                this.nodeMethod.addMethodCalled(tempMethod);
            }
//        }

    @Override
    public void visitTypeInsn(int i, String s) {
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
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
//        if(DesignParser.inPackage(owner) && DesignParser.inPackage(desc)) {
//            System.out.println("Owner: " + owner);
//            System.out.println("Name: " + name);
//            System.out.println("Desc: " + desc);

            owner = owner.substring(owner.lastIndexOf("/") + 1, owner.length());
            String newdesc = desc.substring(desc.lastIndexOf("/") + 1, desc.length());

            ClassNode toNode = getAddedClassNode(newdesc);
            ClassNode fromNode = getAddedClassNode(owner);

            String type = Type.getType(desc).getClassName();

            type = type.substring(type.lastIndexOf("/") + 1, type.length());

            addGeneratedClassNode(fromNode, toNode);

//        }
    }

    public String addAccessLevel(int access){
        String level="";
        if((access& Opcodes.ACC_PUBLIC)!=0){
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
        if(returnType.contains(".") && !returnType.contains("String")) {
            returnType = returnType.substring(returnType.lastIndexOf(".") + 1, returnType.length());
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

    private ClassNode getAddedClassNode(String owner) {
        ClassNode tempNode = null;

        for (ClassNode oldNode : this.classNodes) {
            if (owner.contains(oldNode.getName())) {
                return oldNode;
//                this.nodeMethod.addCreatedNode(tempNode);
            }
        }

        if (tempNode == null) {
            tempNode = new ClassNode(owner);
            this.classNodes.add(tempNode);
            return tempNode;
        }
        return tempNode;
    }

    public void addGeneratedClassNode(INode owner, INode to) {
        for(NodeField nodeField : this.nodeMethod.getClassNodeFieldsCreated()) {
            if(nodeField.getName().contains(to.getName())) {
                return;
            }
        }
        NodeField newNodeField = new NodeField(to.getName(), null, owner, to);
        this.nodeMethod.addCreatedNode(newNodeField);
    }
}

package problem.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

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

        System.out.println("Method: " + this.nodeMethod.getContainingClass().getName());
        System.out.println("Owner: " + owner);
        System.out.println("Name of Method: " + name);
        System.out.println("Description: " + desc + "\n");

        if (!name.contains("<init>")) {

            if (DesignParser.inPackage(owner)) {
                owner = owner.substring(owner.lastIndexOf("/") + 1, owner.length());
                ClassNode tempNode = getAddedClassNode(owner);
                ClassNode parentNode = null;
                if(DesignParser.inPackage(desc)) {
                    desc = desc.substring(owner.lastIndexOf("/") + 1, desc.indexOf(";"));
                    parentNode = getAddedClassNode(desc);
                }


                NodeMethod tempMethod = null;
                for(NodeMethod method : tempNode.getMethods()) {
                    if(method.getName().contains(name)) {
                        tempMethod = method;
                        tempMethod.setParentClassNode(this.nodeMethod.getContainingClass());
                    }
                }

                if(tempMethod == null) {
                    tempMethod = new NodeMethod(name, addReturnType(desc), addArguments(desc), null, tempNode, this.nodeMethod.getContainingClass());
                    tempNode.addMethod(tempMethod);
                }
//                System.out.println("HAS METHOD: " + tempMethod.getName() + " " + tempMethod );

                this.nodeMethod.addMethodCalled(tempMethod);
            }
        }
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        if(DesignParser.inPackage(owner) && DesignParser.inPackage(desc)) {
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

        }
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
                tempNode = oldNode;
//                this.nodeMethod.addCreatedNode(tempNode);
            }
        }

        if (tempNode == null) {
            tempNode = new ClassNode(owner);
            this.classNodes.add(tempNode);
//            this.nodeMethod.addCreatedNode(tempNode);
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

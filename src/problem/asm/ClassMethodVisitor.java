package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ClassMethodVisitor extends ClassVisitor {

    private INode classNode;
    private List<IEdge> edges;
    private LinkedList<INode> nodes;
    private List<INode> finishedNodes;

    public ClassMethodVisitor(int api) {
        super(api);
    }

    public ClassMethodVisitor(int api, ClassVisitor decorated, INode node, List<IEdge> edges, LinkedList<INode> nodes, List<INode> finishedNodes) {
        super(api, decorated);
        this.classNode = node;
        this.edges = edges;
        this.nodes = nodes;
        this.finishedNodes = finishedNodes;
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



        List<String> arguments = addArguments(desc);
        String accessLevel = addAccessLevel(access);
        String cleanReturnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
        List<String> cleanArgs = new ArrayList<>();
        for(String arg : arguments) {
            cleanArgs.add(arg.substring(arg.lastIndexOf("/") + 1, arg.length()));
        }
        NodeMethod nodeMethod = new NodeMethod(name, cleanReturnType, cleanArgs, accessLevel, this.classNode);

        methodDecorator = new ClassMethodInstanceVisitor(Opcodes.ASM5, toDecorate, nodeMethod, this.classNode, this.nodes, finishedNodes);
//        MethodVisitor toDecorateMore = new ClassMethodInstanceVisitor(Opcodes.ASM5, toDecorate);
        this.classNode.addMethod(nodeMethod);

        // dotAssociates
        for(int i = 0; i < arguments.size(); i++) {
            if(Config.inPackageConfiguration(arguments.get(i))) {
                addNewUses(this.classNode.getMiniName(), cleanArgs.get(i));
            }
        }
        if(Config.inPackageConfiguration(returnType)) {
            addNewUses(this.classNode.getMiniName(), cleanReturnType);
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
//        System.out.println(returnValue.toString());
        returnType = returnType.replace(".", "/");
        addNewAssociationArrow(this.classNode.getMiniName(), returnType);
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
            Edge newArrow = new Edge(name, returnType, "\"vee\"", "\"dashed\"", "USES");
            for (IEdge edge : this.edges) {
                if (edge.toString().equals(newArrow.toString())) {
                    if(edge.getLineName().equals("EXTENDS") || edge.getLineName().equals("IMPLEMENTS")) {
                        break;
                    }
                    else {
                        return;
                    }
                }
            }
            this.edges.add(newArrow);
        }
    }

    private void addNewAssociationArrow(String name, String returnType) {
        String cleanReturnType =  returnType.substring(returnType.lastIndexOf("/") + 1, returnType.length());
        if(Config.inPackageConfiguration(returnType)) {
            Edge newArrow = new Edge(name, cleanReturnType, "\"vee\"", "\"solid\"", "ASSOCIATES");
            for (IEdge edge : this.edges) {
                if (edge.toString().equals(newArrow.toString())) {
                    if(edge.getLineName().equals("EXTENDS") || edge.getLineName().equals("IMPLEMENTS")) {
                        break;
                    } else if(edge.getLineName().equals("USES")) {
                        this.edges.remove(edge);
                        break;
                    } else {
                        return;
                    }
                }
            }
            this.edges.add(newArrow);
        }
    }


}
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
//        System.out.println("Name: " + name);
//        System.out.println("SIGNATURE: " + signature);
//        System.out.println("==========METHOD===========");
        MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor methodDecorator = toDecorate;


        if(!name.contains("<init>") && !name.contains("<clinit>")) {

            NodeMethod nodeMethod = null;

            for(NodeMethod method : this.classNode.getMethods()) {
                if(method.getName().contains(name)) {
                    nodeMethod = method;
                }
            }

            if(nodeMethod == null) {
                nodeMethod = new NodeMethod(name, addReturnType(desc), addArguments(desc), addAccessLevel(access), this.classNode, null);
                this.classNode.addMethod(nodeMethod);
            }

//            System.out.println("OVERLORD: " + name + " " +  nodeMethod);
            methodDecorator = new ClassMethodInstanceVisitor(Opcodes.ASM5, toDecorate, nodeMethod, this.classNode, this.nodes);
        }

//        MethodVisitor toDecorateMore = new ClassMethodInstanceVisitor(Opcodes.ASM5, toDecorate);

        // dotAssociates
        for(String arg : addArguments(desc)) {


            if(arg.contains(".")) {
                String association = arg.substring(arg.lastIndexOf(".") + 1, arg.length());
//                System.out.println("Association: " + association);
                if(!association.contains("String")) {
                    this.edges.add(new DotAssociates(this.classNode.getName(), association));
                }
            }
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
        if(returnType.contains(".") && !returnType.contains("String")) {
            returnType = returnType.substring(returnType.lastIndexOf(".") + 1, returnType.length());

            this.edges.add(new DotAssociates(this.classNode.getName(), returnType));
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
}
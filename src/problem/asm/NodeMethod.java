package problem.asm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class NodeMethod implements INodeElement {

    private String name;
    private String returnType;
    private List<String> args;
    private String security;
    private INode containingClassNode;
    private INode parentClassNode;
    private List<NodeMethod> methodsCalled;
    private List<NodeField> nodesCreated;

    public NodeMethod(String name, String returnType, List<String> args, String security, INode containingClassNode, INode parentClassNode) {
        this.name = name;
        this.returnType = returnType;
        this.args = args;
        this.security = security;
        this.containingClassNode = containingClassNode;
        this.methodsCalled = new ArrayList<>();
        this.nodesCreated = new ArrayList<>();
        this.parentClassNode = parentClassNode;
    }

    public INode getParentClassNode() {
        return parentClassNode;
    }

    public void setParentClassNode(INode parentClassNode) {
        this.parentClassNode = parentClassNode;
    }

    public List<NodeField> getClassNodeFieldsCreated() {
        return this.nodesCreated;
    }

    public void addCreatedNode(NodeField nodeField) {
        this.nodesCreated.add(nodeField);
    }

    public void addPotentialDuplicateMethod(NodeMethod nodeMethod, ArrayList<NodeMethod> existingMethods) {

    }

    public List<NodeMethod> getMethodsCalled() {
        return this.methodsCalled;
    }

    public void addMethodCalled(NodeMethod nodeMethod) {
//        this.methodsCalled.add(nodeMethod);
        for(NodeMethod method : this.methodsCalled) {
            if(method.toString() == nodeMethod.toString()) {
                return;
            }
        }
        this.methodsCalled.add(nodeMethod);
    }

    @Override
    public INode getContainingClass() {
        return this.containingClassNode;
    }

    public List<String> getArgs() {
        return args;
    }

    public String getSecurity() {
        return security;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getReturnType() {
        return this.returnType;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitMethod(this);
    }

    @Override
    public String toString() {
        String s = name + " " + returnType + " ";
        for(String arg : args) {
            s += arg + " ";
        }
        return s;
    }
}

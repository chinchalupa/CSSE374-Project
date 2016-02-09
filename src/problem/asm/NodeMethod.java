package problem.asm;

import java.io.OutputStreamWriter;
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
    private List<NodeMethod> methodsCalled;
    private List<NodeField> nodesCreated;

    /**
     *
     * @param name - The name of the method called.
     * @param returnType - The return type of the method called.
     * @param args - The list of arguments in the method.
     * @param security - The package level of the method.
     * @param containingClassNode - The node that contains the method.
     */
    public NodeMethod(String name, String returnType, List<String> args, String security, INode containingClassNode) {
        this.name = name;
        this.returnType = returnType;
        this.args = args;
        this.security = security;
        this.containingClassNode = containingClassNode;
        this.methodsCalled = new ArrayList<>();
        this.nodesCreated = new ArrayList<>();
    }

    public List<NodeField> getClassNodeFieldsCreated() {
        return this.nodesCreated;
    }

    public void addCreatedNode(NodeField nodeField) {
        this.nodesCreated.add(nodeField);
    }

    public List<NodeMethod> getMethodsCalled() {
        return this.methodsCalled;
    }

    public void addMethodCalled(NodeMethod nodeMethod) {
        for(NodeMethod method : this.methodsCalled) {
            if(method.toString().equals(nodeMethod.toString())) {
                System.out.println("DUPLICATE METHOD: " + method);
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
    public String getCollectionType() {
        return "";
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

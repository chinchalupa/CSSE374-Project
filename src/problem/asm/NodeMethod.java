package problem.asm;

import java.util.List;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class NodeMethod implements INodeElement {

    private String name;
    private String returnType;
    private List<String> args;
    private String security;

    public NodeMethod(String name, String returnType, List<String> args, String security) {
        this.name = name;
        this.returnType = returnType;
        this.args = args;
        this.security = security;
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
}

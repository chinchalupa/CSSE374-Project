package problem.asm;

import problem.car.visitor.ITraverser;

import java.util.List;

/**
 * Created by wrightjt on 1/11/2016.
 */
public interface INode extends ITraversable{

    public abstract String getName();
    public abstract String getType();
    public abstract List<NodeMethod> getMethods();
    public abstract void addField(NodeField field);
    public abstract void addMethod(NodeMethod method);
    public abstract void addInterface(String string);
    public abstract void setExtension(String string);
    public abstract List<String> getInterfaces();
    public abstract String getExtends();
}

package problem.asm;

import java.util.List;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class InterfaceNode implements INode {

    private String name;
    private String type;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public List<NodeMethod> getMethods() {
        return null;
    }

    @Override
    public void accept(IVisitor visitor) {

    }
}

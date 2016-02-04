package problem.asm;

import java.util.List;

/**
 * Created by Jeremy on 1/26/2016.
 */
public class SingletonDetector extends UMLDecorator {

    private List<INode> nodes;

    /**
     * Detector for singletons.
     * @param uml - The uml to modify.
     */
    public SingletonDetector(FileGenerator uml) {
        super(uml);
        this.nodes = uml.updateNodes();
    }

    @Override
    public List<INode> updateNodes() {

        for(INode node : this.nodes) {
            boolean hasSelfField = false;
            boolean hasReturnMethod = false;
            String name = node.getName().substring(node.getName().lastIndexOf("/") + 1);

            for(NodeField field : node.getFields()) {

                if(field.getReturnType().equals(name) || field.getReturnType().equals(node.getExtends())) {
                    hasSelfField = true;
                    break;
                }
            }
            for(NodeMethod method : node.getMethods()) {
                if(method.getReturnType().equals(name) || method.getReturnType().equals(node.getExtends())) {
                    hasReturnMethod = true;
                }
            }
            if(hasReturnMethod && hasSelfField) {
                node.setOutlineColor("#0000ff");
                node.addPatternIdentifier("\\<\\<Singleton\\>\\>");
            }
        }
        return this.nodes;
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }

    @Override
    public void generateClassList() {
        super.generateClassList();
    }

    @Override
    public void generateNodes() throws Exception {
        super.generateNodes();
    }

    @Override
    public void write() throws Exception {
        super.write();
    }

}

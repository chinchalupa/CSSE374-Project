package problem.asm;

import java.util.List;

/**
 * Created by Jeremy on 2/3/2016.
 */
public class CompositeDetector extends UMLDecorator {

    private List<INode> nodes;

    public CompositeDetector(FileGenerator uml) {
        super(uml);
        this.nodes = uml.updateNodes();
    }

    @Override
    public List<INode> updateNodes() {
        for(INode node : nodes) {
            String name = node.getName().substring(node.getName().lastIndexOf("/") + 1);
            for(NodeField field : node.getFields()) {
                System.out.println("NAME: " + name + " " + field.getCollectionType() + " " + field.getReturnType());
                String returnType = field.getReturnType();
                if(!field.getCollectionType().equals("")) {
                    returnType = returnType.substring(returnType.indexOf("<") + 1, returnType.lastIndexOf("\\"));
                }
                if(!(field.getCollectionType()).equals("") && (returnType.equals(name))) {
                    node.setStyle("filled");
                    node.setOutlineColor("#ffff00");
                    markSelfContainingFields(node);
                }
            }
        }
        return this.nodes;
    }

    private void markSelfContainingFields(INode node) {
        if(node.getExtends() != null) {
            node.addPatternIdentifier("\\<\\<Composite\\>\\>");
        } else {
            node.addPatternIdentifier("\\<\\<Component\\>\\>");
            for(INode superNode : this.nodes) {
                String name = superNode.getName().substring(superNode.getName().lastIndexOf("/") + 1);
                if(name.equals(node.getExtends())) {
                    superNode.addPatternIdentifier("\\<\\<Component\\>\\>");
                    superNode.setStyle("filled");
                    superNode.setOutlineColor("#ffff00");
                }
            }
        }

    }
}

package problem.asm;

import java.util.List;

/**
 * Created by Jeremy on 2/3/2016.
 */
public class CompositeDetector extends UMLDecorator {


    public CompositeDetector(FileGenerator uml) {
        super(uml);
    }

    @Override
    public List<INode> updateNodes() {
        for(INode node : super.getNodes()) {
            for(NodeField field : node.getFields()) {
//                System.out.println("NAME: " + name + " " + field.getCollectionType() + " " + field.getReturnType());
                String returnType = field.getReturnType();
                if(!field.getCollectionType().equals("")) {
                    returnType = returnType.substring(returnType.indexOf("<") + 1, returnType.lastIndexOf("\\"));
                }
                if(!(field.getCollectionType()).equals("") && (returnType.equals(node.getMiniName()))) {
                    node.setStyle("filled");
                    node.setOutlineColor("#ffff00");
                    markSelfContainingFields(node);
                }
            }
        }
        return super.getNodes();
    }

    private void markSelfContainingFields(INode node) {
        if(node.getExtends() != null) {
            node.addPatternIdentifier("\\<\\<Composite\\>\\>");
        } else {
            node.addPatternIdentifier("\\<\\<Component\\>\\>");
            for(INode superNode : super.getNodes()) {
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

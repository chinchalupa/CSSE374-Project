package problem.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 2/3/2016.
 */
public class CompositeDetector extends UMLDecorator {

    private List<String> compositeNodeNames = new ArrayList<>();
    private List<INode> compositeObjects = new ArrayList<>();

    public CompositeDetector(FileGenerator uml) {
        super(uml);
    }

    /**
     * Find all the composites from their properties.
     */
    private void findComposites() {
        for(INode node : super.getNodes()) {
            for(NodeField field : node.getFields()) {
                if(field.getCollectionType().equals(node.getExtends())) {
                    this.compositeNodeNames.add(node.getExtends());
                    this.compositeObjects.add(node);
                    node.addPatternIdentifier("\\<\\<Composite\\>\\>");
                    node.setOutlineColor("#ffff00");
                    node.setStyle("filled");
                }
            }
        }
    }

    /**
     * Find all the components from the composites.
     */
    private void findComponents() {
        for(INode node : super.getNodes()) {
            if(compositeNodeNames.contains(node.getMiniName()) && !node.getPatternIdentifier().contains("\\<\\<Composite\\>\\>")) {
                this.compositeObjects.add(node);
                node.addPatternIdentifier("\\<\\<Component\\>\\>");
                node.setOutlineColor("#ffff00");
                node.setStyle("filled");
            }
        }
    }

    /**
     * Find all the leaves from the composite objects.
     */
    private void findLeaves() {
        for(INode node : super.getNodes()) {
            for(INode compositeNode : this.compositeObjects) {
                System.out.println(compositeNode.getMiniName() + " " + node.getExtends() + " " + node.getMiniName());
                if(compositeNode.getMiniName().equals(node.getExtends()) && !this.compositeObjects.contains(node)) {
                    node.addPatternIdentifier("\\<\\<Leaf\\>\\>");
                    node.setOutlineColor("#ffff00");
                    node.setStyle("filled");
                }
            }
        }
    }

    @Override
    public List<INode> updateNodes() {
        System.out.println("DETECTING COMPOSITES...");
        findComposites();
        findComponents();
        findLeaves();
        return super.getNodes();
    }

}

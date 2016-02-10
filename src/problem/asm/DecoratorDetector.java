package problem.asm;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Jeremy on 1/26/2016.
 */
public class DecoratorDetector extends UMLDecorator {

    private HashSet<INode> decoratorNodes;

    public DecoratorDetector(FileGenerator uml) {
        super(uml);
        this.decoratorNodes = new HashSet<>();
    }

    @Override
    public List<INode> updateNodes() {

        findPotentialDecoratorNodes();
        findPotentialDecorations();
        findPotentialComponents();

        return super.getNodes();
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }

    private void findPotentialDecoratorNodes() {
        for(INode node : super.getNodes()) {
            String extension = node.getExtends();

            if(extension != null) {
                // Find a uses edge that goes to the extends
                for (IEdge edge : super.getEdges()) {

                    System.out.println("EDGE: " + edge.getLineName());

                    if (edge.getTo().equals(node.getMiniName()) && edge.getFrom().equals(extension)
                        && edge.getLineName().equals("USES")) {
                        edge.setText("<<decorates>>");
                        node.addPatternIdentifier("\\<\\<Decorator\\>\\>");
                        node.setStyle("filled");
                        node.setOutlineColor("#00ff00");
                        this.decoratorNodes.add(node);
                    }
                }
            }
        }
    }

    private void findPotentialDecorations() {
        for(INode node : this.decoratorNodes) {
            for(INode decoration : super.getNodes()) {
                String extension = decoration.getExtends();
                if(extension != null) {
                    if (extension.equals(node.getMiniName())) {
                        decoration.addPatternIdentifier("\\<\\<Decorator\\>\\>");
                        decoration.setStyle("filled");
                        decoration.setOutlineColor("#00ff00");
                    }
                }
            }
        }
    }

    private void findPotentialComponents() {
        for(INode node : this.decoratorNodes) {
            String extensionName = node.getExtends();
            for(INode searchingNode : super.getNodes()) {
                if(extensionName.equals(node.getMiniName())) {
                    searchingNode.addPatternIdentifier("\\<\\<Component\\>\\>");
                    searchingNode.setStyle("filled");
                    searchingNode.setOutlineColor("#00ff00");
                }
            }
        }
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

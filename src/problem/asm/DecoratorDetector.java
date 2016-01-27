package problem.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/26/2016.
 */
public class DecoratorDetector extends UMLDecorator {

    private final FileGenerator uml;
    private List<ClassNode> decoratorNodes;

    public DecoratorDetector(FileGenerator uml) {
        super(uml);
        this.uml = uml;
        this.decoratorNodes = new ArrayList<>();
    }

    @Override
    public List<ClassNode> getNodes() {

        findPotentialDecoratorNodes();
        findPotentialDecorations();

        return super.getNodes();
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }

    private void findPotentialDecoratorNodes() {
        for(ClassNode node : this.uml.classNodeList) {
            String name = node.getName();
            String cleanedName = name.substring(name.lastIndexOf("/") + 1, name.length());
            String extension = node.getExtension();
            System.out.println("Node: " + cleanedName + " " + "Extension: " + extension);

            if(extension != null) {
                // Find a uses edge that goes to the extends
                for (IEdge edge : this.uml.edgeList) {

                    if (edge.getTo().equals(name) && edge.getFrom().equals(extension)
                            && edge.getLineName().equals("USES")) {
                        System.out.println("Added node: " + node.getName());
                        this.decoratorNodes.add(node);
                        node.setPatternIdentifier("\\<\\<decorator\\>\\>");

                    }
                }
            }
        }
    }

    private void findPotentialDecorations() {
        for(ClassNode node : this.decoratorNodes) {
            String name = node.getName().substring(node.getName().lastIndexOf("/") + 1, node.getName().length());
            for(ClassNode decoration : this.uml.classNodeList) {
                String extension = decoration.getExtension();
                if(extension != null) {
                    System.out.println("Potential node: " + name + " " + decoration.getExtension());
                    if (extension.equals(name)) {
                        System.out.println("FOUND A DECORATION");
                        decoration.setPatternIdentifier("\\<\\<decorator\\>\\>");
                    }
                }
            }
        }
    }
}

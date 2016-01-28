package problem.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/26/2016.
 */
public class DecoratorDetector extends UMLDecorator {

    private List<ClassNode> decoratorNodes;

    public DecoratorDetector(FileGenerator uml) {
        super(uml);
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
        for(ClassNode node : super.getNodes()) {
            String name = node.getName();
            String cleanedName = name.substring(name.lastIndexOf("/") + 1, name.length());
            String extension = node.getExtension();

            if(extension != null) {
                // Find a uses edge that goes to the extends
                for (IEdge edge : super.getEdges()) {

                    if (edge.getTo().equals(name) && edge.getFrom().equals(extension)
                            && edge.getLineName().equals("USES")) {
                        this.decoratorNodes.add(node);
                        node.setPatternIdentifier("\\<\\<Decorator\\>\\>");
//                        System.out.println(node.getName());

                    }
                }
            }
        }
    }

    private void findPotentialDecorations() {
        for(ClassNode node : this.decoratorNodes) {
            String name = node.getName().substring(node.getName().lastIndexOf("/") + 1, node.getName().length());
            for(ClassNode decoration : super.getNodes()) {
                String extension = decoration.getExtension();
                if(extension != null) {
                    if (extension.equals(name)) {
                        decoration.setPatternIdentifier("\\<\\<Decorator\\>\\>");
                    }
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

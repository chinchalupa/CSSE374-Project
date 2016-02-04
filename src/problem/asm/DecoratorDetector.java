package problem.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/26/2016.
 */
public class DecoratorDetector extends UMLDecorator implements ITraversable{

    private List<ClassNode> decoratorNodes;
    private List<ClassNode> nodes;

    public DecoratorDetector(FileGenerator uml) {
        super(uml);
        this.decoratorNodes = new ArrayList<>();
        this.nodes = uml.updateNodes();
    }

    @Override
    public List<ClassNode> updateNodes() {

        findPotentialDecoratorNodes();
        findPotentialDecorations();
        findPotentialComponents();

        return this.nodes;
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }

    private void findPotentialDecoratorNodes() {
        for(ClassNode node : this.nodes) {
            String name = node.getName();
            String cleanedName = name.substring(name.lastIndexOf("/") + 1, name.length());
            String extension = node.getExtension();

            if(extension != null) {
                // Find a uses edge that goes to the extends
                for (IEdge edge : super.getEdges()) {

                    if (edge.getTo().equals(name) && edge.getFrom().equals(extension)
                            && edge.getLineName().equals("USES")) {
                        if(node.getPatternIdentifier() == null) {
                            edge.setText("<<decorates>>");
                            node.setPatternIdentifier("\\<\\<Decorator\\>\\>");
                            node.setStyle("filled");
                            node.setOutlineColor("#00ff00");
                        }
                        this.decoratorNodes.add(node);

                    }
                }
            }
        }
    }

    private void findPotentialDecorations() {
        for(ClassNode node : this.decoratorNodes) {
            String name = node.getName().substring(node.getName().lastIndexOf("/") + 1, node.getName().length());
            for(ClassNode decoration : this.nodes) {
                String extension = decoration.getExtension();
                if(extension != null) {
                    if (extension.equals(name)) {
                        decoration.setPatternIdentifier("\\<\\<Decorator\\>\\>");
                        decoration.setStyle("filled");
                        decoration.setOutlineColor("#00ff00");
                    }
                }
            }
        }
    }

    private void findPotentialComponents() {
        for(ClassNode node : this.decoratorNodes) {
            String extensionName = node.getExtension();
//            System.out.println("EXTENSION: " + extensionName);
            for(ClassNode searchingNode : this.nodes) {
                String name = searchingNode.getName().substring(searchingNode.getName().lastIndexOf("/") + 1, searchingNode.getName().length());
//                System.out.println(name + " " + extensionName);
                if(extensionName.equals(name)) {
                    searchingNode.setPatternIdentifier("\\<\\<Component\\>\\>");
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


    @Override
    public void accept(IVisitor visitor) {
        visitor.visitDecorator(this);
    }
}

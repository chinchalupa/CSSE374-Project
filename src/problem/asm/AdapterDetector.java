package problem.asm;

import java.util.List;

/**
 * Created by Jeremy on 1/27/2016.
 */
public class AdapterDetector extends UMLDecorator {


    public AdapterDetector(FileGenerator uml) {
        super(uml);
    }

    @Override
    public List<ClassNode> getNodes() {
        for(IEdge edge : super.getEdges()) {
            if(edge.getLineName().equals("USES")) {
                String edgeName = edge.getTo();
                edgeName = edgeName.substring(edgeName.lastIndexOf("/") + 1);
                System.out.println(edgeName);

                for(ClassNode node : super.getNodes()) {
                    String nodeName = node.getName().substring(node.getName().lastIndexOf("/") + 1);
                    List<String> itf = node.getInterfaces();
                    String ext = node.getExtends();
                    if(nodeName.equals(edgeName) && (itf.size() > 0 || ext != null)) {
                        node.setPatternIdentifier("\\<\\<Adapter\\>\\>");
                    }
                }
            }
        }
        return super.getNodes();
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }
}

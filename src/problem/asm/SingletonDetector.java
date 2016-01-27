package problem.asm;

import java.io.File;
import java.util.List;

/**
 * Created by Jeremy on 1/26/2016.
 */
public class SingletonDetector extends UMLDecorator {

    private final FileGenerator uml;

    public SingletonDetector(FileGenerator uml) {
        super(uml);
        this.uml = uml;
    }

    @Override
    public List<ClassNode> getNodes() {
        boolean hasSelfField = false;
        boolean hasReturnMethod = false;
        for(ClassNode node : this.uml.classNodeList) {
            String name = node.getName().substring(node.getName().lastIndexOf("/") + 1);

            for(NodeField field : node.getFields()) {
//                System.out.println(field.getReturnType());
                if(field.getReturnType().equals(name)) {
                    hasSelfField = true;
                    break;
                }
            }
            for(NodeMethod method : node.getMethods()) {
                if(method.getReturnType().equals(name)) {
                    hasReturnMethod = true;
                }
            }
            if(hasReturnMethod && hasSelfField) {
                node.setColor("#000077");
            }
        }


        return super.getNodes();
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }
}

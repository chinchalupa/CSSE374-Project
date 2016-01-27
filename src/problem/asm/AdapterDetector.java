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
        return super.getNodes();
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }
}

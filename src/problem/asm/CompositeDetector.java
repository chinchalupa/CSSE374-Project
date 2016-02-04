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
    public List<ClassNode> updateNodes() {
        return null;
    }
}

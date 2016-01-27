package problem.asm;

import java.util.List;

/**
 * Created by Jeremy on 1/26/2016.
 */
public class UMLDecorator extends FileGenerator {

    private final FileGenerator uml;

    public UMLDecorator(FileGenerator uml) {
        super();
        this.uml = uml;
    }

    @Override
    public List<ClassNode> getNodes() {
        return uml.getNodes();
    }

    @Override
    public List<IEdge> getEdges() {
        return uml.getEdges();
    }

    @Override
    public void generateClassList() {
        this.uml.generateClassList();
    }

    @Override
    public void generateNodes() throws Exception {
        this.uml.generateNodes();
    }

    @Override
    public void write() throws Exception {
        this.uml.write();
    }
}

package problem.asm.structures;

import problem.asm.file_elements.IEdge;
import problem.asm.file_elements.INode;
import problem.asm.visitors.ITraversable;
import problem.asm.visitors.IVisitor;

import java.util.List;
import java.util.Set;

/**
 * Created by Jeremy on 1/26/2016.
 */
public abstract class UMLDecorator extends FileGenerator implements ITraversable {

    private final FileGenerator uml;

    public UMLDecorator(FileGenerator uml) {
        super();
        this.uml = uml;
    }


    @Override
    public abstract List<INode> updateNodes();

    public List<INode> getNodes() {
        return uml.itemHandler.getCreatedNodes();
    }

    public Set<IEdge> getEdges() {
        return uml.itemHandler.getEdges();
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

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitDecorator(this);
    }
}

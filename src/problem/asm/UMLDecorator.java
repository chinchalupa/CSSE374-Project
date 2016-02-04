package problem.asm;

import jdk.internal.org.objectweb.asm.ClassVisitor;

import java.util.List;

/**
 * Created by Jeremy on 1/26/2016.
 */
public abstract class UMLDecorator extends FileGenerator implements ITraversable{

    private final FileGenerator uml;

    public UMLDecorator(FileGenerator uml) {
        super();
        this.uml = uml;
    }

    @Override
    public abstract List<ClassNode> updateNodes();

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

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitDecorator(this);
    }
}

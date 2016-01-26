package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/25/2016.
 */
public abstract class FileGenerator {

    protected String outputLocation;
    protected String inputFile;

    protected List<ClassNode> classNodeList;
    protected List<IEdge> edgeList;

    public FileGenerator() {
        this.outputLocation = "./input_output";
        this.inputFile = "./src/problem/asm";
    }

    public FileGenerator(String outputLocation, String inputFile) {
        this.outputLocation = outputLocation;
        this.inputFile = inputFile;

        this.classNodeList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
    }

    public abstract List<ClassNode> getNodes();

    public abstract List<IEdge> getEdges();

    public abstract void generateClassList();

    public abstract void generateNodes() throws Exception;

    public abstract void write() throws Exception;
}

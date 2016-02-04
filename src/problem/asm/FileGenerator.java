package problem.asm;

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

    protected Config config;

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

    public FileGenerator(String configLocation) {
        this.config = Config.newInstance(configLocation);
    }

    public abstract List<ClassNode> updateNodes();

    public abstract List<IEdge> getEdges();


    public abstract void generateClassList();

    public abstract void generateNodes() throws Exception;

    public abstract void write() throws Exception;
}

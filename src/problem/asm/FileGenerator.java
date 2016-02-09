package problem.asm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Jeremy on 1/25/2016.
 */
public abstract class FileGenerator {

    protected String outputLocation;
    protected String inputFile;

    protected Stack<INode> classNodeList;
    protected ArrayList<INode> finishedClassNodeList;
    protected List<IEdge> edgeList;

    protected Config config;

    public FileGenerator() {
        this.outputLocation = "./input_output";
        this.inputFile = "./src/problem/asm";
    }

    public FileGenerator(String outputLocation, String inputFile) {
        this.outputLocation = outputLocation;
        this.inputFile = inputFile;

        this.classNodeList = new Stack<>();
        this.edgeList = new ArrayList<>();
        this.finishedClassNodeList = new ArrayList<>();
    }

    public FileGenerator(String configLocation) {
        this.config = Config.newInstance(configLocation);

        this.classNodeList = new Stack<>();
        this.edgeList = new ArrayList<>();
        this.finishedClassNodeList = new ArrayList<>();
    }

    public abstract List<INode> updateNodes();

    public List<IEdge> getEdges() {
        return this.edgeList;
    }

    public List<INode> getNodes() {
        return this.finishedClassNodeList;
    }


    public abstract void generateClassList();

    public abstract void generateNodes() throws Exception;

    public abstract void write() throws Exception;
}

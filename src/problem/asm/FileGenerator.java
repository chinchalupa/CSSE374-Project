package problem.asm;

import java.util.*;

/**
 * Created by Jeremy on 1/25/2016.
 */
public abstract class FileGenerator {

    protected String outputLocation;
    protected String inputFile;
    protected ItemHandler itemHandler;

    protected List<String> startingClassStrings;
    protected IVisitor visitor;

    //TODO: DELETE
//    public FileGenerator(String outputLocation, String inputFile) {
//        this.outputLocation = outputLocation;
//        this.inputFile = inputFile;
//        if(Config.getInstance() != null) {
//            this.outputLocation = Config.getInstance().getDotFileOutputLocation();
//        }
//    }

    public FileGenerator() {
        this.startingClassStrings = new ArrayList<>();
        this.itemHandler = new ItemHandler();
    }

    public abstract List<INode> updateNodes();

    public int getTotalStartingClassSize() {
        return this.startingClassStrings.size();
    }


    public void generateClassList() {
        this.startingClassStrings = Config.getInstance().getFormattedListOfClasses();
        System.out.println(this.startingClassStrings.size());
    }

    public abstract void generateNodes() throws Exception;

    public abstract void write() throws Exception;

    public IVisitor getVisitor() {
        return this.visitor;
    }

    public void setVisitor(IVisitor visitor) {
        this.visitor = visitor;
    }
}

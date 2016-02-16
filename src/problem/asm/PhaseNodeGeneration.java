package problem.asm;

import java.io.File;

/**
 * Created by Jeremy on 2/14/2016.
 */
public class PhaseNodeGeneration implements ExecuteCapable{

    private FileGenerator fileGenerator;



    public PhaseNodeGeneration(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    @Override
    public void execute() {
        try {
            fileGenerator.generateNodes();
        } catch (Exception e) {
            System.out.println("Error generating nodes...");
            System.exit(1);
        }
    }

    @Override
    public String getExecutionString() {
        return "Generating nodes and edges";
    }
}

package problem.asm.phases;

import problem.asm.structures.FileGenerator;

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
            System.exit(1);
        }
    }

    @Override
    public String getExecutionString() {
        return "Generating nodes and edges";
    }
}

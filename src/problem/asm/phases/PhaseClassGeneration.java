package problem.asm.phases;

import problem.asm.structures.FileGenerator;

/**
 * Created by Jeremy on 2/14/2016.
 */
public class PhaseClassGeneration implements ExecuteCapable {

    private FileGenerator fileGenerator;

    public PhaseClassGeneration(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    @Override
    public void execute() {
        this.fileGenerator.generateClassList();
    }

    @Override
    public String getExecutionString() {
        return "Generating classes";
    }
}

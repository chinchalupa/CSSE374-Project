package problem.asm.phases;

import problem.asm.structures.FileGenerator;

/**
 * Created by Jeremy on 2/14/2016.
 */
public class PhaseWrite implements ExecuteCapable {

    private FileGenerator fileGenerator;

    public PhaseWrite(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    @Override
    public void execute() {
        try {
            this.fileGenerator.write();
        } catch (Exception e) {
            System.exit(1);
        }
    }

    @Override
    public String getExecutionString() {
        return "Writing to file";
    }
}

package problem.asm;

import java.io.IOException;

/**
 * Created by Jeremy on 2/16/2016.
 */
public class RunDotExecutable implements ExecuteCapable {
    public RunDotExecutable() {
    }

    @Override
    public void execute() {
        try {
            Runtime.getRuntime().exec("dot -T png -o " + Config.getInstance().getImageLocation() + " " + Config.getInstance().getDotFileOutputLocation());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getExecutionString() {
        return "Building png file";
    }
}

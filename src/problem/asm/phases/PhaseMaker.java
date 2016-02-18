package problem.asm.phases;

import problem.asm.structures.Config;
import problem.asm.structures.FileGenerator;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by Jeremy on 2/17/2016.
 */
public class PhaseMaker {

    private FileGenerator fileGenerator;

    public PhaseMaker(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }


    public void runPhases() throws Exception {
        List<String> phases = Config.getInstance().getStringList("phases");

        for (String phase : phases) {
            Constructor detector = Class.forName(phase).getConstructor(FileGenerator.class);
            ExecuteCapable newExecuteCapable = (ExecuteCapable) detector.newInstance(this.fileGenerator);
            newExecuteCapable.execute();
        }

    }
}

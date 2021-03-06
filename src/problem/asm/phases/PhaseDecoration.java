package problem.asm.phases;

import problem.asm.structures.Config;
import problem.asm.structures.FileGenerator;
import problem.asm.structures.UMLDecorator;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by Jeremy on 2/15/2016.
 */
public class PhaseDecoration implements ExecuteCapable {

    private FileGenerator fileGenerator;

    public PhaseDecoration(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    @Override
    public void execute() {
        List<String> patterns = Config.getInstance().getStringList("detectors");
        for(String pattern : patterns) {
            Constructor detector = null;
            try {
                detector = Class.forName(pattern).getConstructor(FileGenerator.class);
                UMLDecorator decorator = (UMLDecorator) detector.newInstance(this.fileGenerator);
                this.fileGenerator.getVisitor().visitDecorator(decorator);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getExecutionString() {
        return null;
    }
}

package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/25/2016.
 */
public class FileGenerator {

    protected String outputLocation;
    protected String inputFile;

    public FileGenerator(String outputLocation, String inputFile) {
        this.outputLocation = outputLocation;
        this.inputFile = inputFile;

    }
}

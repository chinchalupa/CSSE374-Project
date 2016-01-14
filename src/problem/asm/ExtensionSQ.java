package problem.asm;

/**
 * Created by wrightjt on 1/14/2016.
 */
public class ExtensionSQ implements FileOutputType {

    private int iterations;
    private String className;
    private String pack;
    private String methodName;
    private String extension;
    private String outputLocation;

    public ExtensionSQ(int iterations, String className, String pack, String methodName, String outputLocation) {
        this.iterations = iterations;
        this.extension = ".sq";
        this.className = className;
        this.pack = pack;
        this.methodName = methodName;
        this.outputLocation = outputLocation + this.extension;
    }

    @Override
    public String getOutputLocation() {
        return this.outputLocation;
    }

    public int getIterations() {
        return iterations;
    }

    public String getClassName() {
        return className;
    }

    public String getPack() {
        return pack;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String getExtension() {
        return this.extension;
    }
}

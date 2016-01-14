package problem.asm;

/**
 * Created by wrightjt on 1/14/2016.
 */
public class ExtensionDot implements FileOutputType {

    private String extension;
    private String outputLocation;

    @Override
    public String getOutputLocation() {
        return this.outputLocation;
    }

    public ExtensionDot(String outputLocation) {
        this.extension = ".dot";
        this.outputLocation = outputLocation + this.extension;
    }

    @Override
    public String getExtension() {
        return this.extension;
    }
}

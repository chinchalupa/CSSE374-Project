package problem.asm;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jeremy on 2/16/2016.
 */
public class RunDotExecutable implements ExecuteCapable {

    private FileGenerator fileGenerator;

    public RunDotExecutable(FileGenerator fileGenerator) {
    }

    @Override
    public void execute() {
        try {
            File pngFile = new File(Config.getInstance().getImageLocation());
            File dotFile = new File(Config.getInstance().getDotFileOutputLocation());

            String pngLocation = pngFile.getAbsolutePath();
            String dotLocation = dotFile.getAbsolutePath();
            pngLocation = pngLocation.replace("/", "\\");
            dotLocation = dotLocation.replace("/", "\\");


            String executed = " -T png -o \"" + pngLocation + "\" \"" + dotLocation + "\"";
            String userPx86 = System.getenv("ProgramFiles(X86)");
            userPx86 += "\\GraphViz2.38\\bin\\dot.exe";
            System.out.println(userPx86);
            System.out.println(executed);
            Process p = Runtime.getRuntime().exec(userPx86 + executed);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getExecutionString() {
        return "Building png file";
    }
}

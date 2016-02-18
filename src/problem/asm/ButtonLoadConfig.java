package problem.asm;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Jeremy on 2/14/2016.
 */
public class ButtonLoadConfig extends Button {

    private JLabel label;

    public ButtonLoadConfig(String text, FileGenerator fileGenerator, JLabel label) {
        super(text, fileGenerator);
        this.label = label;
    }

    @Override
    public void execute() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON Filter", "json"));
        fileChooser.setCurrentDirectory(new File("configurations"));
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            String filepath = fileChooser.getSelectedFile().getPath();
            Config.newInstance(filepath);
            try {
                this.fileGenerator.setVisitor(new OutputDotFile(new FileOutputStream(Config.getInstance().getDotFileOutputLocation()), this.fileGenerator));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.label.setText(filepath);
        }

    }

    @Override
    public String getExecutionString() {
        System.out.println("SET LABEL");
        return "Loading file";
    }
}

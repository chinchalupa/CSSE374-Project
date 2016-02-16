package problem.asm;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Jeremy on 2/14/2016.
 */
public class ButtonAnalyze extends Button {

    private String info;
    private JLabel label;
    private JPanel panel;
    private JFrame frame;

    public ButtonAnalyze(String text, FileGenerator fileGenerator, JLabel label, JPanel panel, JFrame frame) {
        super(text, fileGenerator);
        this.info = "Waiting for user input...";
        this.label = label;
        this.panel = panel;
        this.frame = frame;
    }

    @Override
    public void execute() {
        System.out.println(Config.getInstance().getPhases());
        for(String phase : Config.getInstance().getPhases()) {
            try {
                Constructor detector = Class.forName(phase).getConstructor(FileGenerator.class);
                ExecuteCapable newExecuteCapable = (ExecuteCapable) detector.newInstance(this.fileGenerator);
                this.label.setText(newExecuteCapable.getExecutionString());
                this.label.revalidate();
                this.label.repaint();
                this.panel.revalidate();
                this.panel.repaint();
                this.label.setBounds(label.getX(), label.getY(), 200, 50);
                newExecuteCapable.execute();
                this.info = newExecuteCapable.getExecutionString();
                System.out.println(label.getText());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public String getExecutionString() {
        return info;
    }
}

package problem.asm;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jeremy on 2/14/2016.
 */
public class ButtonAnalyze extends Button implements Observer {

    private String info;
    private JLabel label;
    private JPanel panel;
    private JFrame frame;
    private JFrame newFrame;

    public ButtonAnalyze(String text, FileGenerator fileGenerator, JLabel label, JPanel panel, JFrame frame, JFrame newFrame) {
        super(text, fileGenerator);
        this.info = "Waiting for user input...";
        this.label = label;
        this.panel = panel;
        this.frame = frame;
        this.newFrame = newFrame;
        this.setEnabled(false);
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
                newExecuteCapable.execute();
                this.info = newExecuteCapable.getExecutionString();
                System.out.println(label.getText());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        System.out.println("ENABLED");
        Config config = Config.getInstance();
        config.callbothshits();
        this.newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.newFrame.setVisible(true);
    }

    @Override
    public String getExecutionString() {
        return info;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("UPDATING");
        this.setEnabled(true);
    }
}

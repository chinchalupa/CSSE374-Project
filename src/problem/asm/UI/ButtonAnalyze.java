package problem.asm.UI;

import problem.asm.structures.Config;
import problem.asm.structures.FileGenerator;
import problem.asm.phases.PhaseMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jeremy on 2/14/2016.
 */
public class ButtonAnalyze extends Button implements Observer {

    private String info;
    private JLabel label;
    private JFrame frame;
    private ScreenFrame newFrame;

    public ButtonAnalyze(String text, FileGenerator fileGenerator, JLabel label, JPanel panel, JFrame frame, ScreenFrame newFrame) {
        super(text, fileGenerator);
        this.info = "Waiting for user input...";
        this.label = label;
        this.frame = frame;
        this.newFrame = newFrame;
        this.setEnabled(false);
    }

    @Override
    public void execute() {
        PhaseMaker phaseMaker = new PhaseMaker(this.fileGenerator);
        try {
            phaseMaker.runPhases();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CheckboxPanel panel = new CheckboxPanel();
        CheckboxTreeFactory treeFactory = new CheckboxTreeFactory(this.fileGenerator);
        treeFactory.execute();

        panel.add(treeFactory.getTree());
        this.newFrame.addPanel(panel, BorderLayout.LINE_START);

        this.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Config config = Config.newInstance(Config.getInstance().getConfigurationLocation().getPath());
        config.notifyOthers();
        this.newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.newFrame.setVisible(true);
    }

    @Override
    public String getExecutionString() {
        return info;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setEnabled(true);
    }
}

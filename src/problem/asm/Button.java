package problem.asm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jeremy on 2/14/2016.
 */
public abstract class Button extends JButton implements ActionListener, ExecuteCapable {

    protected FileGenerator fileGenerator;

    public Button(String text, FileGenerator fileGenerator) {
        super(text);
        this.fileGenerator = fileGenerator;
        this.addActionListener(this);
    }

    public void setComputing(String newText) {
        this.setText(newText);
        setEnabled(false);
    }

    public void setFinished() {
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.execute();
    }
}

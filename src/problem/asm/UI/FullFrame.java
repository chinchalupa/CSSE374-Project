package problem.asm.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jeremy on 2/15/2016.
 */
public class FullFrame extends JFrame {
    
    public FullFrame(String title) throws HeadlessException {
        super(title);
        this.setSize(new Dimension(1920, 1080));
    }
}

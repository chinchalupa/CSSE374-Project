package problem.asm;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jeremy on 2/14/2016.
 */
public class ScreenFrame extends JFrame {

    private JLabel label;
    private JPanel contentPanel;

    public ScreenFrame(String title) throws HeadlessException {
        super(title);
        this.setSize(new Dimension(600, 400));
        this.contentPanel = new JPanel();
        contentPanel.setLayout(null);
        this.getContentPane().add(this.contentPanel);
    }

    public void updateLabel() {
    }

    /**
     * Add a button to the frame.
     * @param x - The x coordinate of the button.
     * @param y - The y coordinate of the button.
     * @param button - The button to add to the frame.
     * @param panel - The panel to add the button to. If null, the item will be added to the content frame.
     */
    public void addButton(int x, int y, Button button, JPanel panel) {
        if(panel == null) {
            this.contentPanel.add(button);
//            button.setLayout(null);
//            button.setLocation(x, y);
            button.setBounds(x, y, 120, 40);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public JPanel addPanel(int startX, int startY, int endX, int endY) {
        JPanel newPanel = new JPanel();
        return newPanel;
    }
    public JLabel addLabel(int startX, int startY, String startingText) {
        JLabel label = new JLabel(startingText);
        label.setBounds(startX, startY, 400, 40);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.contentPanel.add(label);
        contentPanel.revalidate();
        contentPanel.repaint();
        return label;
    }

    public JPanel getContentPanel() {
        return this.contentPanel;
    }




}

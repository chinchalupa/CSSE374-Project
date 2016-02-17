package problem.asm;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jeremy on 2/14/2016.
 */
public class ScreenFrame extends JFrame {

    private JLabel label;
    private JPanel contentPanel;

    /**
     * Draws a screen with a title, a certain size, and a Layout.
     * @param title - The title of the window.
     * @param sizeX - The X size of the window.
     * @param sizeY - The Y size of the window.
     * @throws HeadlessException - No header.
     */
    public ScreenFrame(String title, int sizeX, int sizeY) throws HeadlessException {
        super(title);
        this.setSize(new Dimension(sizeX, sizeY));
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BorderLayout());
        this.getContentPane().add(this.contentPanel);
    }

    public void updateLabel() {
    }

    /**
     * Add a button to the frame.
     * @param button - The button to add to the frame.
     * @param panel - The panel to add the button to. If null, the item will be added to the content frame.
     */
    public void addButton(Button button, JPanel panel, String location) {
        if(panel == null) {
            this.contentPanel.add(button, location);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public JPanel addPanel(int startX, int startY, int endX, int endY) {
        JPanel newPanel = new JPanel();
        return newPanel;
    }

    /**
     * Adds a panel to the screen.
     * @param panel - The panel to add.
     * @param location - The direction to add the panel.
     */
    public void addPanel(JPanel panel, String location) {
        this.contentPanel.add(panel, location);
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
    }

    public JLabel addLabel(String startingText, String location) {
        JLabel label = new JLabel(startingText);
//        label.setBounds(startX, startY, 400, 40);
//        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.contentPanel.add(label, location);
        contentPanel.revalidate();
        contentPanel.repaint();
        return label;
    }

    public JPanel getContentPanel() {
        return this.contentPanel;
    }




}

package problem.asm;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jeremy on 2/16/2016.
 */
public class ImagePanel extends JPanel implements Observer {

    BufferedImage image;


    /**
     * Image panel for handling drawing images.
     * @param imageLocation - The location of the image.
     */
    public ImagePanel(String imageLocation) {
        try {
            image = ImageIO.read(new File("input_output/our_project.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Image scaledImage = image.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        g2.drawImage(scaledImage, 0, 0, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        String imageLocation = Config.getInstance().getImageLocation();
        System.out.println("UPDATED IMAGE LOCATION");
        try {
            this.image = ImageIO.read(new File(imageLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package problem.asm;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.util.*;

public class DesignParser {


    /**
     * Reads in a list of Java Classes and reverse engineers their design.
     *
     * @param args: the names of the classes, separated by spaces.
     * For example: java DesignParser java.lang.String edu.rosehulman.csse374.ClassFieldVisitor

java.lang.Math
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {

        boolean generatingFiles = true;

        FileGenerator umlGenerator = new UMLGenerator();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Config config = Config.getInstance();
                ScreenFrame frame = new ScreenFrame("THE BEST CSSE374 PROJECT EVER!", 600, 400);
                ScreenFrame fullFrame = new ScreenFrame("I WANT THIS PROJECT TO END", 1920, 1080);

                ImagePanel imagePanel = new ImagePanel(Config.getInstance().getImageLocation());
                config.addObserver(imagePanel);
                fullFrame.addPanel(imagePanel, BorderLayout.CENTER);

                JLabel label = frame.addLabel("Waiting for user input...", BorderLayout.PAGE_END);
                frame.addButton(new ButtonLoadConfig("Load Config", umlGenerator, label), null, BorderLayout.PAGE_START);
                ButtonAnalyze analyzeButton = new ButtonAnalyze("Analyze", umlGenerator, label, frame.getContentPanel(), frame, fullFrame);
                config.addObserver(analyzeButton);
                frame.addButton(analyzeButton, null, BorderLayout.CENTER);
//                fullFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


                frame.setVisible(true);
            }
        });


        System.out.println("Welcome to our CSSE374 project!");
        System.out.println("Supported filetypes(.dot, .sq)");
        Scanner scanner = new Scanner(System.in);


        while(generatingFiles) {
            System.out.println("Enter the config file to use:");


            String configLocation = scanner.nextLine();
            Config config = Config.newInstance(configLocation);


            System.out.println("Would you like to generate a new file (y/n)?");
            generatingFiles = scanner.nextLine().trim().equals("y");

        }
    }
}

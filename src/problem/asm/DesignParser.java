package problem.asm;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Constructor;
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
                ScreenFrame frame = new ScreenFrame("THE BEST CSSE374 PROJECT EVER!");
                JLabel label = frame.addLabel(100, 150, "Waiting for user input...");
                frame.addButton(180, 100, new ButtonLoadConfig("Load Config", umlGenerator, label), null);
                frame.addButton(300, 100, new ButtonAnalyze("Analyze", umlGenerator, label, frame.getContentPanel(), frame), null);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

package problem.asm;
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

        System.out.println("Welcome to our CSSE374 project!");
        System.out.println("Supported filetypes(.dot, .sq)");
        Scanner scanner = new Scanner(System.in);


        while(generatingFiles) {
            System.out.println("Enter the config file to use:");


            String configLocation = scanner.nextLine();
            Config config = Config.newInstance(configLocation);
            FileGenerator umlGenerator = new UMLGenerator();

//            if(config.shouldDetectSingletons()) {
//
//                umlGenerator = new SingletonDetector(umlGenerator);
//            }
//
//            if(config.shouldDetectDecorators()) {
//                umlGenerator = new DecoratorDetector(umlGenerator);
//            }
//
//            if(config.shouldDetectAdapters()) {
//                umlGenerator = new AdapterDetector(umlGenerator);
//            }

            umlGenerator.generateClassList();
            umlGenerator.generateNodes();
            umlGenerator.getNodes();
            umlGenerator.write();
            System.out.println("Would you like to generate a new file (y/n)?");
            generatingFiles = scanner.nextLine().trim().equals("y");

        }
    }
}

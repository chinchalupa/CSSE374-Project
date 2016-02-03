package problem.asm;
import java.io.*;
import java.util.*;

import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

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

            if(config.shouldDetectSingletons()) {
                umlGenerator = new SingletonDetector(umlGenerator);
            }

            if(config.shouldDetectDecorators()) {
                umlGenerator = new DecoratorDetector(umlGenerator);
            }

            umlGenerator.generateClassList();
            umlGenerator.generateNodes();
            umlGenerator.getNodes();
            umlGenerator.write();
//            Config config = Config.newInstance(configLocation);
//            for(String s : config.getClassesAndPackageClassesList()) {
//                System.out.println(s);
//            }
//
//            switch (filetype) {
//                case ".dot":
//                    System.out.println("Where would you like to save your file to?");
//                    String saveLocation = scanner.nextLine();
//                    System.out.println("What class/directory would you like to read?");
//                    String readClass = scanner.nextLine();
//                    saveLocation = saveLocation.length() > 0 ? saveLocation : "input_output/new_file" + filetype;
//                    readClass = readClass.length() > 0 ? readClass : "./src/problem/asm";
//                    FileGenerator umlGenerator = new UMLGenerator(saveLocation, readClass);
//
//                    System.out.println("Detect Singletons (y/n)?");
//
//                    boolean detectSingletons = scanner.nextLine().trim().equals("y");
//                    if(detectSingletons) {
//                        umlGenerator = new SingletonDetector(umlGenerator);
//                    }
//                    System.out.println("Detect Decorators (y/n)?");
//                    boolean detectingDecorators = scanner.nextLine().trim().equals("y");
//
//                    if(detectingDecorators) {
//                        umlGenerator = new DecoratorDetector(umlGenerator);
//                    }
//
//                    System.out.println("Detect Adapters (y/n)?");
//                    boolean detectAdapters = scanner.nextLine().trim().equals("y");
//                    if(detectAdapters) {
//                        umlGenerator = new AdapterDetector(umlGenerator);
//                    }
//
//                    umlGenerator.generateClassList();
//                    umlGenerator.generateNodes();
//
//                    umlGenerator.getNodes();
//                    umlGenerator.write();
//
//                    System.out.println(saveLocation);
//
//                    break;
//
//                default:
//                    System.out.println("Unsupported filetype. Try again (.dot, .sq)");
//                    break;
//
//            }
            System.out.println("Would you like to generate a new file (y/n)?");
            generatingFiles = scanner.nextLine().trim().equals("y");

        }
    }
}

package problem.asm;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class DesignParser {

    /**
     * Reads in a list of Java Classes and reverse engineers their design.
     *
     * @param args: the names of the classes, separated by spaces.
     * For example: java DesignParser java.lang.String edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{

        God god = new God();

        Scanner in = new Scanner(System.in);
        System.out.println("Enter in the full source of the file e.g. (./src/oldLab)");
        String source = in.nextLine();
        System.out.println("Enter in the full package name e.g. (oldLab)");
        String location = in.nextLine();

        List<dotExtends> extendCases = new ArrayList<>();

        for(String className : DesignParser.getListOfFiles(source, location)) {



// ASM's ClassReader does the heavy lifting of parsing the compiled Java class
            ClassReader reader = new ClassReader(className);


            String rawClass = reader.getClassName();
            String refinedClass = rawClass.substring(rawClass.lastIndexOf("/") + 1, rawClass.length());

            dotClass dClass = new dotClass(refinedClass, new ArrayList<>(), new ArrayList<>());



// make class declaration visitor to get superclass and interfaces
            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5);
// DECORATE declaration visitor with field visitor
            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, dClass);
// DECORATE field visitor with method visitor
            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, dClass);
// TODO: add more DECORATORS here in later milestones to accomplish specific tasks
// Tell the Reader to use our (heavily decorated) ClassVisitor to visit the class
            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

            String rawSuperClass = reader.getSuperName();
            String[] rawImplements = reader.getInterfaces();

            String refinedSuperClass = rawSuperClass.substring(rawSuperClass.lastIndexOf("/") + 1, rawSuperClass.length());
            List<String> implementedFrom = new ArrayList<>();


//            System.out.println("REFINED:\t\t " + refinedClass);
//            System.out.println("INHERITS FROM:\t " + refinedSuperClass);
//            for(String implemented : rawImplements) {
//                implementedFrom.add(implemented.substring(implemented.lastIndexOf("/") + 1, implemented.length()));
//                System.out.println("IMPLEMENTS:\t\t " + implementedFrom.get(implementedFrom.size() - 1));
//            }

            god.add(dClass);
//            for(dotMethod method : dClass.getMethods()) {
//                System.out.println("METHOD: " + method.getName());
//            }

            // Create all the classes
            // Store all the edges
            // Exclude the Object edges

//            if(!refinedSuperClass.contains("Object")) {
                extendCases.add(new dotExtends(refinedSuperClass, refinedClass));
//            }
        }

        for(dotExtends e : extendCases) {
            god.add(e);
        }

        System.out.println(god.genesis());

    }



    public static List<String> getListOfFiles(String location, String prefix) {
        List<String> filenames = new ArrayList<>();
        File directory = new File(location);

        File[] listOfFiles = directory.listFiles();
        List<File> files = Arrays.asList(listOfFiles);

        for(File file : files) {
            String s = file.getName();
            s = s.substring(0, s.indexOf(".") + 1);
            s = s.replace(".java", "");
            s = s.replace(".", "");
            s = s.replace("\\", ".");
            filenames.add(prefix + "." + s);
        }

        return filenames;
    }
}
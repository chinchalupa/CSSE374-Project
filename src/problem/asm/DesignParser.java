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
            System.out.println("Enter the filetype you would like to create:");

            String filetype = scanner.nextLine();

            switch (filetype) {
                case ".dot":
                    FileGenerator umlGenerator = new UMLGenerator("./input_output/test.dot", "java.lang.Runtime");

                    System.out.println("Detect Singletons (y/n)?");

                    boolean detectSingletons = scanner.nextLine().trim().equals("y");
                    if(detectSingletons) {
                        umlGenerator = new SingletonDetector(umlGenerator);
                    }
                    umlGenerator.generateClassList();
                    umlGenerator.generateNodes();

                    umlGenerator.getNodes();
                    umlGenerator.write();
                    break;

                default:
                    System.out.println("Unsupported filetype. Try again (.dot, .sq)");
                    break;

            }
            System.out.println("Would you like to generate a new file (y/n)?");
            generatingFiles = scanner.nextLine().trim().equals("y");

        }
    }


//        for(String className : files) {
////            System.out.println("

//        List<IEdge> edges = new ArrayList<>();
//        List<ClassNode> nodes = new ArrayList<>();
//        List<String> files = DesignParser.getListOfFiles();
//        if(files.isEmpty()) {
//            files.add("java.util.Collections");
//        }
//Classname: " + className);
//
//// ASM's ClassReader does the heavy lifting of parsing the compiled Java class
//            ClassReader reader = new ClassReader(className);
//
//
//            String rawClass = reader.getClassName();
//            String refinedClass = rawClass.substring(rawClass.lastIndexOf("/") + 1, rawClass.length());
//
//            ClassNode classNode = null;
//            for(ClassNode node : nodes) {
//                if(node.getName().equals(refinedClass)) {
//                    classNode = node;
////                    System.out.println("Found classnode: " + classNode);
//                    break;
//                }
//            }
//
//            if(classNode == null) {
//                classNode = new ClassNode(refinedClass);
////                System.out.println("\nAdded " + refinedClass);
//                nodes.add(classNode);
//            }


//            System.out.println(nodes);
//            System.out.println("Added: " + classNode.getName());

// make class declaration visitor to get superclass and interfaces
//            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, classNode, nodes, edges);
//// DECORATE declaration visitor with field visitor
//            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, classNode, edges);
//// DECORATE field visitor with method visitor
//            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, classNode, edges, nodes);
//
//
//            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);


//            String rawSuperClass = reader.getSuperName();
//            String[] rawImplements = reader.getInterfaces();
//
//            String refinedSuperClass = rawSuperClass.substring(rawSuperClass.lastIndexOf("/") + 1, rawSuperClass.length());
//            List<String> implementedFrom = new ArrayList<>();
//
//            for(String implemented : rawImplements) {
//                implementedFrom.add(implemented.substring(implemented.lastIndexOf("/") + 1, implemented.length()));
//            }
//
////            System.out.println(refinedClass + " " + refinedSuperClass);
//
//            if(!refinedSuperClass.contains("Object")) {
//                edges.add(new DotExtends(refinedSuperClass, refinedClass));
//            }
//            for(String impFrom : implementedFrom) {
//                edges.add(new DotImplements(impFrom, refinedClass));
//            }
//        }
//
//        OutputDotFile outputDotFile = new OutputDotFile(new FileOutputStream(extensionDot.getOutputLocation()));
//        OutputSDFile outputSDFile = new OutputSDFile(new FileOutputStream(extensionSQ.getOutputLocation()));
//        DesignParser.outputDSFile(nodes, outputSDFile, extensionSQ.getIterations());
//
//
//        for(INode node : nodes) {
//            ITraversable iTraversable = (ITraversable) node;
//            iTraversable.accept(outputDotFile);
//        }
//
//        for(IEdge edge : edges) {
//            ITraversable edgeTraversable = (ITraversable) edge;
//            edgeTraversable.accept(outputDotFile);
//        }
//
//        for(ClassNode node : nodes) {
//            System.out.println("Name: " + node.getName());
//            for(NodeMethod method : node.getMethods()) {
////                System.out.println("Method: " + method);
//                if(method.toString().contains("shuffle")) {
//                    for(NodeMethod methodMethod : method.getMethodsCalled()) {
//                        System.out.println("Has: " + methodMethod.toString());
//                    }
//                }
//            }
//        }
//
//        outputDotFile.end();




//    public static List<String> getListOfFiles() {
//
//
//        Scanner in = new Scanner(System.in);
//        System.out.println("Welcome to our CSSE374 Project!");
//        System.out.println("Currently, we can make .dot files and .sd files.");
//        System.out.println("Enter your desired project to use: ");
//
//        String source = in.nextLine();
//        if(source.length() < 1) {
//            source = "./src/oldLab";
//            System.out.println("No location specified. Using Lab3-1.");
//        }
//
//        String savelcation = source;
//        String location = source.replace("./src/", "");
//        location = location.replace("/", ".");
//
//        pack = location.substring(location.lastIndexOf(".") + 1, location.length());
//
//
//        System.out.println("Enter the output source");
//        String oSource = in.nextLine();
//        System.out.println("Enter the .dot file name");
//        String dotLocation = in.nextLine();
//
//        extensionDot = new ExtensionDot(oSource + "/" + dotLocation);
//
//        System.out.println("Enter the number of iterations: ");
//        int iterations = Integer.parseInt(in.nextLine());
//        System.out.println("Enter the starting class: ");
//        String startClass = in.nextLine();
//        System.out.println("Enter the method name: ");
//        String startMethod = in.nextLine();
//        System.out.println("Enter the SQ file name");
//        String sqLocation = in.nextLine();
//
//        extensionSQ = new ExtensionSQ(iterations, startClass.trim(), pack, startMethod,oSource +"/" + sqLocation);
////        System.out.println(startClass.length());
////        System.out.println("Start: " + startClass.trim());
//
//        if (oSource.length() < 1) {
//            oSource = "./input_output/output.dot";
//        }
//
//        List<String> filenames = new ArrayList<>();
//        System.out.println("Location: " + savelcation);
//        File directory = new File(savelcation);
//
//        if(savelcation.contains("/")) {
//            File[] listOfFiles = directory.listFiles();
//            List<File> files = Arrays.asList(listOfFiles);
//
//
//            for (File file : files) {
//                String s = file.getName();
//                s = s.substring(0, s.indexOf(".") + 1);
//                s = s.replace(".java", "");
//                s = s.replace(".", "");
//                s = s.replace("\\", ".");
//                filenames.add(location + "." + s);
//            }
//        }
//
//        return filenames;
//    }
//
////    public static boolean inPackage(String string) {
////        return string.contains(pack);
////    }
//
//    public static void outputDSFile(List<ClassNode> classNodes, OutputSDFile sdFile, int counter) {
//        NodeMethod startNode = null;
//        for(ClassNode node : classNodes) {
//            if(extensionSQ.getClassName().contains(node.getName())) {
////                System.out.println(node.getName() + " " + extensionSQ.getClassName());
////                System.out.println("FOUND FILE");
//                for (NodeMethod method : node.getMethods()) {
////                    System.out.println(method.getName() + " " + extensionSQ.getMethodName());
////                    System.out.println("Method: " + method.getName());
//                    if (method.getName().contains(extensionSQ.getMethodName())) {
//                        startNode = method;
//                        break;
//                    }
//                }
//            }
//            ITraversable iTraversable = (ITraversable) node;
//            node.accept(sdFile);
//        }
//
//        if(startNode != null) {
//            ITraversable iTraversable = (ITraversable) startNode;
//            startNode.accept(sdFile);
//
//
//            recursiveIterDSFile(startNode, sdFile, counter);
//        }
//        else {
//            System.out.println("ERROR READING FILE");
//        }
//    }
//
//    public static void recursiveIterDSFile(NodeMethod nodeMethod, OutputSDFile sdFile, int counter) {
//        if(counter <= 0) {
//            ITraversable iTraversable = (ITraversable) nodeMethod;
//            iTraversable.accept(sdFile);
//        }
//
//        else {
//            for(NodeField nodeField : nodeMethod.getClassNodeFieldsCreated()) {
//                ITraversable iTraversable = (ITraversable) nodeField;
//                iTraversable.accept(sdFile);
//            }
//
//            for (NodeMethod method : nodeMethod.getMethodsCalled()) {
//                System.out.println(method.toString());
////                System.out.println("Added method: " + method.toString());
//                ITraversable iTraversable = (ITraversable) method;
//                iTraversable.accept(sdFile);
//
//                recursiveIterDSFile(method, sdFile, counter--);
//            }
//        }
//    }
}

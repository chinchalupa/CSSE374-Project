package problem.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/25/2016.
 */
public class UMLGenerator extends FileGenerator {

    protected List<ClassNode> classNodeList;
    protected List<IEdge> edgeList;
    protected List<String> startingClassStrings;
    private String pkg;

    public UMLGenerator(String outputLocation, String inputFile) {
        super(outputLocation, inputFile);
        this.pkg = this.inputFile.replace("./src/", "");
        this.classNodeList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
        this.startingClassStrings = new ArrayList<>();
    }

    public void generateClassList() {
        File directory = new File(this.inputFile);
        File files[] = directory.listFiles();
        for(File file : files) {
            this.startingClassStrings.add(file.getPath().replace(".\\src\\", "").replace("\\", ".").replace(".java", ""));
            System.out.println(this.startingClassStrings.get(this.startingClassStrings.size() - 1));
        }
    }

    public void generateNodes() throws Exception {
        for(String file : startingClassStrings) {

            ClassReader reader = new ClassReader(file);

            ClassNode node = new ClassNode(reader.getClassName());


// make class declaration visitor to get superclass and interfaces
            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, node, classNodeList, edgeList, pkg);
//// DECORATE declaration visitor with field visitor
            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, node, edgeList, pkg);
//// DECORATE field visitor with method visitor
            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, node, edgeList, classNodeList, pkg);

            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

            this.classNodeList.add(node);
//
        }
    }

    public void write() throws Exception {
        OutputStream outputStream = new FileOutputStream(this.outputLocation);
        OutputDotFile visitor = new OutputDotFile(outputStream, this.pkg);
        for(ClassNode node : this.classNodeList) {
            node.accept(visitor);
        }

        for(IEdge edge : this.edgeList) {
            edge.accept(visitor);
        }
    }
}

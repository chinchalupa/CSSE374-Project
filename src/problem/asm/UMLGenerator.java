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

    protected List<String> startingClassStrings;
//    private List<String> pkg;
    private String pkg;
    protected Config config;

    public UMLGenerator(String outputLocation, String inputFile) {
        super(outputLocation, inputFile);
        this.pkg = this.inputFile.replace("./src/", "");
        this.startingClassStrings = new ArrayList<>();
    }

    public UMLGenerator() {
//        super(configLocation);
        this.config = Config.getInstance();
        this.outputLocation = this.config.getDotFileOutputLocation();
        this.classNodeList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
    }

    public void generateClassList() {
        this.startingClassStrings = this.config.getClassesAndPackageClassesList();
    }

    @Override
    public List<ClassNode> getNodes() {
        return this.classNodeList;
    }

    @Override
    public List<IEdge> getEdges() {
        return this.edgeList;
    }

    public void generateNodes() throws Exception {
        for(String file : startingClassStrings) {

            ClassReader reader = new ClassReader(file);

            ClassNode node = new ClassNode(reader.getClassName());

            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, node, classNodeList, edgeList);

            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, node, edgeList);

            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, node, edgeList, classNodeList);

            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

            this.classNodeList.add(node);
        }
    }

    public void write() throws Exception {
        OutputStream outputStream = new FileOutputStream(this.outputLocation);
        OutputDotFile visitor = new OutputDotFile(outputStream);

        for(ClassNode node : this.classNodeList) {
            node.accept(visitor);
        }

        for(IEdge edge : this.edgeList) {
            edge.accept(visitor);
        }

        visitor.end();
    }

    public List<String> getClasses() {
        return this.startingClassStrings;
    }
}

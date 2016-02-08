package problem.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/25/2016.
 */
public class UMLGenerator extends FileGenerator {

    protected List<String> startingClassStrings;

    public UMLGenerator(String outputLocation, String inputFile) {
        super(outputLocation, inputFile);
        this.startingClassStrings = new ArrayList<>();
    }

    public UMLGenerator() {
//        super(configLocation);
        this.outputLocation = Config.getInstance().getDotFileOutputLocation();
        this.classNodeList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
    }

    public void generateClassList() {
        this.startingClassStrings = Config.getInstance().getClassesAndPackageClassesList();
    }

    @Override
    public List<INode> updateNodes() {
        return this.classNodeList;
    }

    @Override
    public List<IEdge> getEdges() {
        return this.edgeList;
    }

    public void generateNodes() throws Exception {

        for(String file : startingClassStrings) {

            System.out.println(file);

            ClassReader reader = new ClassReader(file);

            INode node = new ClassNode(reader.getClassName());

            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, node, classNodeList, edgeList);

            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, node, edgeList);

            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, node, edgeList, classNodeList);

            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

            this.classNodeList.add(node);
        }
    }

    public void write() throws Exception {
        OutputStream outputStream = new FileOutputStream(this.outputLocation);
        OutputDotFile visitor = new OutputDotFile(outputStream, this);

        ArrayList<String> patterns = Config.getInstance().detectedPatterns();
        for(String pattern : patterns) {
            Constructor detector = Class.forName(pattern).getConstructor(FileGenerator.class);
            UMLDecorator decorator = (UMLDecorator) detector.newInstance(this);
            visitor.visitDecorator(decorator);
        }

        for(INode node : this.classNodeList) {
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

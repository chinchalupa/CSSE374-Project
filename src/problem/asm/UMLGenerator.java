package problem.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Jeremy on 1/25/2016.
 */
public class UMLGenerator extends FileGenerator {

    protected List<String> startingClassStrings;

    public UMLGenerator(String outputLocation, String inputFile) {
        super(outputLocation, inputFile);
        this.startingClassStrings = new ArrayList<>();
    }

    public UMLGenerator(String configurationLocation) {
        super(configurationLocation);
        this.outputLocation = Config.getInstance().getDotFileOutputLocation();
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

        this.createAllClassNodes();

        while(!this.classNodeList.isEmpty()) {

            INode node = this.classNodeList.pop();

            ClassReader reader = new ClassReader(node.getName());

            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, node, edgeList);

            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, node, edgeList);

            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, node, edgeList, classNodeList);

            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

            this.finishedClassNodeList.add(node);
        }

    }

    public List<INode> createAllClassNodes() {

        for(String file : this.startingClassStrings) {
            INode node = new ClassNode(file);
            String miniName = file.substring(file.lastIndexOf(".") + 1);
            node.setMiniName(miniName);
            this.classNodeList.push(node);
        }
        return this.classNodeList;
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

        for(INode node : this.finishedClassNodeList) {
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

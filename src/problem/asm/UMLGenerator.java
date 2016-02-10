package problem.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created by Jeremy on 1/25/2016.
 */
public class UMLGenerator extends FileGenerator {


    public UMLGenerator(String outputLocation, String inputFile) {
        super(outputLocation, inputFile);
    }

    public UMLGenerator() {
        super();
        this.outputLocation = Config.getInstance().getDotFileOutputLocation();
    }

    @Override
    public LinkedList<INode> updateNodes() {
        return this.itemHandler.getNodeStack();
    }

    public void generateNodes() throws Exception {

        this.createAllClassNodes();

        int maxNodes = getTotalStartingClassSize();

        while(!this.itemHandler.getNodeStack().isEmpty() && maxNodes != 0) {

            INode node = this.itemHandler.poll();

            ClassReader reader = new ClassReader(node.getName());

            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, this.itemHandler);

            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, this.itemHandler);

            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, this.itemHandler);

            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

            this.itemHandler.getCreatedNodes().add(node);
//            System.out.println("ADDED: " + node);

            maxNodes--;

//            System.out.println("NODE: " + node + " " + maxNodes);
        }
    }

    public void createAllClassNodes() {

        for(String file : this.startingClassStrings) {
            INode node = new ClassNode(file);
            String miniName = file.substring(file.lastIndexOf(".") + 1);
            node.setMiniName(miniName);
            this.itemHandler.offer(node);
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

        for(INode node : this.itemHandler.getCreatedNodes()) {
            node.accept(visitor);
        }

        for(IEdge edge : this.itemHandler.getEdges()) {
            edge.accept(visitor);
        }

        visitor.end();
    }



    public List<String> getClasses() {
        return this.startingClassStrings;
    }
}

package problem.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.LineNumberNode;

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

    public UMLGenerator(String configurationLocation) {
        super(configurationLocation);
        this.outputLocation = Config.getInstance().getDotFileOutputLocation();
    }

    @Override
    public LinkedList<INode> updateNodes() {
        return this.classNodeList;
    }

    @Override
    public List<IEdge> getEdges() {
        return this.edgeList;
    }

    public void generateNodes() throws Exception {

        this.createAllClassNodes();

        int maxNodes = getTotalStartingClassSize();

        while(!this.classNodeList.isEmpty() && maxNodes != 0) {

            for(INode node : this.classNodeList) {
                System.out.println("LIST NODE: " + node);
            }

            INode node = this.classNodeList.poll();

            ClassReader reader = new ClassReader(node.getName());

            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, node, edgeList);

            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, node, edgeList);

            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, node, edgeList, classNodeList, finishedClassNodeList);

            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

            this.finishedClassNodeList.add(node);
            System.out.println("ADDED: " + node);

            maxNodes--;

            System.out.println("NODE: " + node + " " + maxNodes);
        }
    }

    public List<INode> createAllClassNodes() {

        for(String file : this.startingClassStrings) {
            INode node = new ClassNode(file);
            String miniName = file.substring(file.lastIndexOf(".") + 1);
            node.setMiniName(miniName);
            this.classNodeList.offer(node);
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

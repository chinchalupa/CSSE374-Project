package problem.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created by Jeremy on 1/25/2016.
 */
public class UMLGenerator extends FileGenerator {


    /**
     * UML Generators take in an ItemHandler that handles added nodes and edges.
     */
    //TODO: Get item handler in.
    public UMLGenerator() {

        super();
    }

    @Override
    public List<INode> updateNodes() {
        return this.itemHandler.getCreatedNodes();
    }

    public void generateNodes() throws Exception {

        this.createAllClassNodes();

        int maxNodes = getTotalStartingClassSize();

        System.out.println(this.itemHandler.getNodeStack());

        while(!this.itemHandler.getNodeStack().isEmpty() && maxNodes != 0) {

            INode node = this.itemHandler.poll();
            System.out.println("Error on node: " + node.getName());

            ClassReader reader = new ClassReader(node.getName());

            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, this.itemHandler);

            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, this.itemHandler);

            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, this.itemHandler);

            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

            this.itemHandler.getCreatedNodes().add(node);

            maxNodes--;
        }
    }

    public void createAllClassNodes() {

        this.itemHandler.clearEdges();

        while(!this.itemHandler.getNodeStack().isEmpty()) {
            this.itemHandler.poll();
        }



        for(String file : this.startingClassStrings) {
            System.out.println(file);
            INode node = new ClassNode(file);
            String miniName = file.substring(file.lastIndexOf(".") + 1);
            node.setMiniName(miniName);
            this.itemHandler.offer(node);
        }
    }

    public void write() throws Exception {
        if(this.outputLocation == null) {
            this.outputLocation = Config.getInstance().getDotFileOutputLocation();
        }
        OutputStream outputStream = new FileOutputStream(this.outputLocation);
        visitor = new OutputDotFile(outputStream, this);


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

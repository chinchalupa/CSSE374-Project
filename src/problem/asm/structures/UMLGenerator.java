package problem.asm.structures;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import problem.asm.classreader.ClassDeclarationVisitor;
import problem.asm.classreader.ClassFieldVisitor;
import problem.asm.classreader.ClassMethodVisitor;
import problem.asm.file_elements.ClassNode;
import problem.asm.file_elements.IEdge;
import problem.asm.file_elements.INode;
import problem.asm.visitors.OutputDotFile;

import java.io.FileOutputStream;
import java.io.OutputStream;
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


        while(!this.itemHandler.getNodeStack().isEmpty() && maxNodes != 0) {

            INode node = this.itemHandler.poll();

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

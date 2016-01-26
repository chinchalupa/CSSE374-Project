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
    private String pkg;

    public UMLGenerator(String outputLocation, String inputFile) {
        super(outputLocation, inputFile);
        this.pkg = this.inputFile.replace("./src/", "");
        System.out.println(this.pkg);
        this.startingClassStrings = new ArrayList<>();
    }

    public void generateClassList() {
        File directory = new File(this.inputFile);
        File files[] = directory.listFiles();
        if(files != null) {
            for (File file : files) {
                this.startingClassStrings.add(file.getPath().replace(".\\src\\", "").replace("\\", ".").replace(".java", ""));
            }
        } else {
            this.startingClassStrings.add(directory.getPath());
        }
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
            System.out.println("Node created " + node.getName());

            ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, node, classNodeList, edgeList, pkg);

            ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, node, edgeList, pkg);

            ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, node, edgeList, classNodeList, pkg);

            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

            this.classNodeList.add(node);
        }
    }

    public void write() throws Exception {
        OutputStream outputStream = new FileOutputStream(this.outputLocation);
        OutputDotFile visitor = new OutputDotFile(outputStream, this.pkg);
        System.out.println("Writing: " + this.classNodeList.size());
        System.out.println(this.classNodeList.get(0));

        for(ClassNode node : this.classNodeList) {
            node.accept(visitor);
        }

        for(IEdge edge : this.edgeList) {
            edge.accept(visitor);
        }

        visitor.end();
    }
}

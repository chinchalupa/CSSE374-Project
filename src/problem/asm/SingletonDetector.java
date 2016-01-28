package problem.asm;

import java.io.File;
import java.io.FilterInputStream;
import java.util.List;

/**
 * Created by Jeremy on 1/26/2016.
 */
public class SingletonDetector extends UMLDecorator {

//    private final FileGenerator uml;

    public SingletonDetector(FileGenerator uml) {
        super(uml);
    }

    @Override
    public List<ClassNode> getNodes() {

        for(ClassNode node : super.getNodes()) {
            boolean hasSelfField = false;
            boolean hasReturnMethod = false;
            String name = node.getName().substring(node.getName().lastIndexOf("/") + 1);

            for(NodeField field : node.getFields()) {
//                System.out.println(field.getReturnType());
                System.out.println(node.getExtension());
                if(field.getReturnType().equals(name) || field.getReturnType().equals(node.getExtension())) {
                    hasSelfField = true;
                    System.out.println("Check");
                    break;
                }
            }
            for(NodeMethod method : node.getMethods()) {
                System.out.println(method.getReturnType());
                if(method.getReturnType().equals(name) || method.getReturnType().equals(node.getExtension())) {
                    System.out.println("Check");
                    hasReturnMethod = true;
                }
            }
            if(hasReturnMethod && hasSelfField) {
                node.setColor("#000077");
                node.setPatternIdentifier("\\<\\<Singleton\\>\\>");
//                System.out.println(name + " is a Singleton");
            }
        }


        return super.getNodes();
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }

    @Override
    public void generateClassList() {
        super.generateClassList();
    }

    @Override
    public void generateNodes() throws Exception {
        super.generateNodes();
    }

    @Override
    public void write() throws Exception {
        super.write();
    }
}

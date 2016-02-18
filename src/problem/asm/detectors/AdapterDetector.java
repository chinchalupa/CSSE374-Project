package problem.asm.detectors;

import problem.asm.file_elements.IEdge;
import problem.asm.file_elements.INode;
import problem.asm.file_elements.NodeMethod;
import problem.asm.structures.Config;
import problem.asm.structures.FileGenerator;
import problem.asm.structures.UMLDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/27/2016.
 */
public class AdapterDetector extends UMLDecorator {

    private List<String> itf;
    private List<String> adaptees;


    /**
     * Detector for adapters on the UML Diagram.
     * @param uml - The UML Diagram.
     */
    public AdapterDetector(FileGenerator uml) {
        super(uml);
        this.itf = new ArrayList<>();
        this.adaptees = new ArrayList<>();
        System.out.println("DETECTING ADAPTERS...");
    }

    @Override
    public List<INode> updateNodes() {
        for(IEdge edge : super.getEdges()) {
            if(edge.getLineName().equals("USES")) {
                String edgeName = edge.getTo();
                edgeName = edgeName.substring(edgeName.lastIndexOf("/") + 1);

                for(INode node : super.getNodes()) {
                    List<String> itfs = node.getInterfaces();
                    String ext = node.getExtends();
                    if(node.getMiniName().equals(edgeName) && (itfs.size() > 0 || ext != null)) {





                        String edgeFrom = edge.getFrom();
                        if(checkIfNodeReallyIsAdapterOfClass(node, edgeFrom)) {

                            node.addPatternIdentifier("\\<\\<Adapter\\>\\>");
                            node.setOutlineColor("#ff0000");
                            node.setStyle("filled");

                            if (ext != null) {
                                this.adaptees.add(ext);
                            }
                            this.itf.add(edgeFrom);
                            addAdaptsArrow(node.getMiniName(), edgeFrom);

                            for (String it : itfs) {
                                this.adaptees.add(it);

                            }
                        }
                    }
                }
            }
        }

        getItfs();
        getExtensions();

        return super.getNodes();
    }

    /**
     * Checks if the node REALLY is an adapter.
     */
    private boolean checkIfNodeReallyIsAdapterOfClass(INode node, String adaptee) {

        int counter = 0;

        for(NodeMethod method : node.getMethods()) {
            for(NodeMethod calledMethod : method.getMethodsCalled()) {
                String containingClass = calledMethod.getContainingClass().getMiniName();
                if(containingClass.equals(adaptee)) {
                    counter++;
                }
            }
        }

        return (counter >= Config.getInstance().getAdapterMinimumCount());
    }

    public void addAdaptsArrow(String node, String extension) {
        for(IEdge edge : super.getEdges()) {
            if(!edge.getLineName().equals("EXTENDS")) {
//                String to = edge.getTo().substring(edge.getTo().lastIndexOf("/") + 1);
                if(edge.getTo().equals(node) && edge.getFrom().equals(extension))  {
                    edge.setText("\\<\\<adapts\\>\\>");
                    return;
                }
            }
        }
    }

    private void getItfs() {
        for(String s : itf) {
            for(INode node : super.getNodes()) {
                if(node.getMiniName().equals(s)) {

                    node.addPatternIdentifier("\\<\\<Target\\>\\>");
                    node.setOutlineColor("#ff0000");
                    node.setStyle("filled");
                    }
                }
            }
    }

    private void getExtensions() {
        for(String s : adaptees) {
            for(INode node : super.getNodes()) {
                if(node.getMiniName().equals(s)) {

                    node.addPatternIdentifier("\\<\\<Adaptee\\>\\>");
                    node.setOutlineColor("#ff0000");
                    node.setStyle("filled");
                }
            }
        }
    }
}

package problem.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/27/2016.
 */
public class AdapterDetector extends UMLDecorator {

    private List<String> itf;
    private List<String> adaptees;


    public AdapterDetector(FileGenerator uml) {
        super(uml);
        this.itf = new ArrayList<>();
        this.adaptees = new ArrayList<>();
        System.out.println("DETECTING ADAPTERS...");
    }

    @Override
    public List<ClassNode> getNodes() {
        for(IEdge edge : super.getEdges()) {
            if(edge.getLineName().equals("USES")) {
                String edgeName = edge.getTo();
                edgeName = edgeName.substring(edgeName.lastIndexOf("/") + 1);

                for(ClassNode node : super.getNodes()) {
                    String nodeName = node.getName().substring(node.getName().lastIndexOf("/") + 1);
                    List<String> itf = node.getInterfaces();
                    String ext = node.getExtension();
                    if(nodeName.equals(edgeName) && (itf.size() > 0 || ext != null)) {
                        if(node.getPatternIdentifier() == null) {
                            node.setPatternIdentifier("\\<\\<Adapter\\>\\>");
                            node.setOutlineColor("#ff0000");
                            node.setStyle("filled");
                        }
                        String edgeFrom = edge.getFrom();
                        this.itf.add(edgeFrom);
                        this.adaptees.add(ext);
                        if(ext != null) {
                            addAdaptsArrow(nodeName, ext);
                        }
                        for(String it : itf) {
                            addAdaptsArrow(nodeName, it);
                            this.adaptees.add(it);
                        }
                    }
                }
            }
        }

        getItfs();
        getExtensions();

        return super.getNodes();
    }

    public void addAdaptsArrow(String node, String extension) {
        System.out.println("DATA: " + node + " " + extension);
        for(IEdge edge : super.getEdges()) {
            if(edge.getLineName().equals("EXTENDS") || edge.getLineName().equals("IMPLEMENTS")) {
//                System.out.println(edge.getLineName() + " " + edge.getTo() + " " + edge.getFrom());
                String to = edge.getTo().substring(edge.getTo().lastIndexOf("/") + 1);
//                System.out.println(to.equals(node) + " " + edge.getFrom().equals(extension));
                if(to.equals(node) && edge.getFrom().equals(extension))  {
                    edge.setText("\\<\\<adapts\\>\\>");
                }
            }
        }
    }

    private void getItfs() {
        for(String s : itf) {
            for(ClassNode node : super.getNodes()) {
                String nodeName = node.getName().substring(node.getName().lastIndexOf("/") + 1);
                if(nodeName.equals(s)) {
                    if(node.getPatternIdentifier() == null) {

                        node.setPatternIdentifier("\\<\\<Target\\>\\>");
                        node.setOutlineColor("#ff0000");
                        node.setStyle("filled");
                    }
                }
            }
//            System.out.println("ADAPTEE: " + s);
        }
    }

    private void getExtensions() {
        for(String s : adaptees) {
            for(ClassNode node : super.getNodes()) {
                String nodeName = node.getName().substring(node.getName().lastIndexOf("/") + 1);
                if(nodeName.equals(s)) {
                    if(node.getPatternIdentifier() == null) {

                        node.setPatternIdentifier("\\<\\<Adaptee\\>\\>");
                        node.setOutlineColor("#ff0000");
                        node.setStyle("filled");
                    }
                }
            }
        }
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }
}

package problem.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 1/27/2016.
 */
public class AdapterDetector extends UMLDecorator{

    private List<String> itf;
    private List<String> adaptees;
    private List<INode> nodes;


    public AdapterDetector(FileGenerator uml) {
        super(uml);
        this.itf = new ArrayList<>();
        this.adaptees = new ArrayList<>();
        this.nodes = uml.updateNodes();
        System.out.println("DETECTING ADAPTERS...");
    }

    @Override
    public List<INode> updateNodes() {
        System.out.println("GOT NODES");
        for(IEdge edge : super.getEdges()) {
            if(edge.getLineName().equals("USES")) {
                String edgeName = edge.getTo();
                edgeName = edgeName.substring(edgeName.lastIndexOf("/") + 1);

                for(INode node : this.nodes) {
                    String nodeName = node.getName().substring(node.getName().lastIndexOf("/") + 1);
                    List<String> itf = node.getInterfaces();
                    String ext = node.getExtends();
                    if(nodeName.equals(edgeName) && (itf.size() > 0 || ext != null)) {
                        node.addPatternIdentifier("\\<\\<Adapter\\>\\>");
                        node.setOutlineColor("#ff0000");
                        node.setStyle("filled");

                        String edgeFrom = edge.getFrom();

                        this.itf.add(edgeFrom);
                        addAdaptsArrow(nodeName, edgeFrom);
                        if (ext != null) {
                            this.adaptees.add(ext);
                        }
                        for (String it : itf) {
                            this.adaptees.add(it);
//                    }
                        }
                    }
                }
            }
        }

        getItfs();
        getExtensions();

        return this.nodes;
    }

    public void addAdaptsArrow(String node, String extension) {
        for(IEdge edge : super.getEdges()) {
            if(!edge.getLineName().equals("EXTENDS")) {
                String to = edge.getTo().substring(edge.getTo().lastIndexOf("/") + 1);
                if(to.equals(node) && edge.getFrom().equals(extension))  {
                    edge.setText("\\<\\<adapts\\>\\>");
                    return;
                }
            }
        }
    }

    private void getItfs() {
        for(String s : itf) {
            for(INode node : this.nodes) {
                String nodeName = node.getName().substring(node.getName().lastIndexOf("/") + 1);
                if(nodeName.equals(s)) {

                    node.addPatternIdentifier("\\<\\<Target\\>\\>");
                    node.setOutlineColor("#ff0000");
                    node.setStyle("filled");
                    }
                }
            }
    }

    private void getExtensions() {
        for(String s : adaptees) {
            for(INode node : this.nodes) {
                String nodeName = node.getName().substring(node.getName().lastIndexOf("/") + 1);
                if(nodeName.equals(s)) {

                    node.addPatternIdentifier("\\<\\<Adaptee\\>\\>");
                    node.setOutlineColor("#ff0000");
                    node.setStyle("filled");
                }
            }
        }
    }

    @Override
    public List<IEdge> getEdges() {
        return super.getEdges();
    }


}

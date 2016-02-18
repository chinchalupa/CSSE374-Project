package problem.asm;

import java.util.*;

/**
 * Created by Jeremy on 2/9/2016.
 */
public class ItemHandler {

    private Set<IEdge> edges;
    private LinkedList<INode> nodes;
    private List<INode> createdNodes;
    private INode activeNode;

    public ItemHandler(Set<IEdge> edges, LinkedList<INode> nodes) {
        this.edges = edges;
        this.nodes = nodes;
    }

    public ItemHandler() {
        this.edges = new HashSet<>();
        this.nodes = new LinkedList<>();
        this.createdNodes = new ArrayList<>();
    }

    public LinkedList<INode> getNodeStack() {
        return nodes;
    }

    public INode poll() {
        INode node = this.nodes.poll();
        this.activeNode = node;
        return this.activeNode;
    }

    public INode getActiveNode() {
        return this.activeNode;
    }

    public void offer(INode node) {
        this.nodes.offer(node);
    }

    public Set<IEdge> getEdges() {
        return this.edges;
    }

    public List<INode> getCreatedNodes() {
        return this.createdNodes;
    }

    /**
     * Handles the creation of edges and the logic for handling duplicates.
     * @param to - The arrow to go to.
     * @param from - The arrow you come from.
     * @param arrowType - The type of arrowhead as a string.
     * @param lineType - The type of line as a string.
     * @param name - The name on the arrow.
     * @return - The arrow added if more restrictions need to be placed on the arrow.
     */
    public IEdge createEdge(String to, String from, String arrowType, String lineType, String name) {
        if(to.contains("/")) {
            to  = to.substring(to.lastIndexOf("/") + 1);
        }
        if(from.contains("/")) {
            from = from.substring(from.lastIndexOf("/") + 1);
        }

        IEdge edge = new Edge(to, from, arrowType, lineType, name);

        List<IEdge> edgesToRemove = new ArrayList<>();


        if(name.equals("EXTENDS") || name.equals("IMPLEMENTS")) {
            this.edges.add(edge);
            return edge;
        }
        else if(name.equals("USES")) { // Uses arrow
            for(IEdge oldEdge : this.getEdges()) {
                if(oldEdge.equals(edge)) {
                    if(oldEdge.getLineName().equals("ASSOCIATES") || oldEdge.getLineName().equals("AGGREGATES")
                            || oldEdge.getLineName().equals("USES")) {
                        // Associates and uses exists
                        return oldEdge;
                    }
                }
            }
        }
        else if(name.equals("AGGREGATES")) {
            for(IEdge oldEdge : this.getEdges()) {
                if(oldEdge.equals(edge)) {
                    if(oldEdge.getLineName().equals("ASSOCIATES") || oldEdge.getLineName().equals("USES")) {
//                        this.edges.remove(oldEdge);
                        edgesToRemove.add(oldEdge);
                    }
                    else if(oldEdge.getLineName().equals("AGGREGATES")) {
                        return oldEdge;
                    }
                }
            }
        }
        else if(name.equals("ASSOCIATES")) {
            for(IEdge oldEdge : this.getEdges()) {
                if(oldEdge.equals(edge)) {
                    if(oldEdge.getLineName().equals("USES")) {
                        edgesToRemove.add(oldEdge);
                    }
                    else if(oldEdge.getLineName().equals("AGGREGATES")) {
                        return oldEdge;
                    }
                    else if(oldEdge.getLineName().equals("ASSOCIATES")) {
                        return oldEdge;
                    }
                }
            }
        }

        for(IEdge badEdge : edgesToRemove) {
            this.edges.remove(badEdge);
        }
        this.edges.add(edge);
        return edge;
    }

    public INode createNode(String name, String miniName) {
        INode node = new ClassNode(name);
        node.setMiniName(miniName);
        if(isNotScannedNode(node)) {
            this.nodes.push(node);
        }
        return node;
    }

    private boolean isNotScannedNode(INode node) {
        for(INode scannedNode : this.createdNodes) {
            if(scannedNode.toString().equals(node.toString())) {
                return false;
            }
        }
        return true;
    }

    public void clearEdges() {
        this.edges = new HashSet<>();
    }
}

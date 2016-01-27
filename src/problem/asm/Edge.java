package problem.asm;

/**
 * Created by Jeremy on 1/25/2016.
 */
public class Edge implements IEdge {
    private String lineName;
    private String to;
    private String from;
    private String arrowType;
    private String lineType;

    public Edge(String to, String from, String arrowType, String lineType, String lineName) {
        this.to = to;
        this.from = from;
        this.arrowType = arrowType;
        this.lineType = lineType;
        this.lineName = lineName;
    }

    @Override
    public String getLineName() {
        return lineName;
    }

    @Override
    public String getTo() {
        return this.to;
    }

    @Override
    public String getFrom() {
        return this.from;
    }

    @Override
    public String getLine() {
        return this.lineType;
    }

    @Override
    public String getArrow() {
        return this.arrowType;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitEdges(this);
    }

    @Override
    public String toString() {
        return this.to + this.from;
    }
}

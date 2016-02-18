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
    private String text;


    public Edge(String to, String from, String arrowType, String lineType, String lineName) {
        this.to = to;
        this.from = from;
        this.arrowType = arrowType;
        this.lineType = lineType;

        this.lineName = lineName;
        this.text = "";
    }

    @Override
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
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
        visitor.preVisitEdge(this);
        visitor.visitEdge(this);
        visitor.postVisitEdge(this);
    }

    @Override
    public String toString() {
        return this.to + this.from;
    }

    @Override
    public boolean equals(IEdge edge) {
        return this.getTo().equals(edge.getTo()) && this.getFrom().equals(edge.getFrom());
    }
}

package problem.asm;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class DotAssociates implements IEdge {

    private String to;
    private String from;
    private String line;
    private String arrow;

    public DotAssociates(String to, String from) {
        this.to = to;
        this.from = from;
        this.line = "solid";
        this.arrow = "vee";
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public String getLine() {
        return line;
    }

    @Override
    public String getArrow() {
        return arrow;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitEdges(this);
    }
}

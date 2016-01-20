package problem.asm;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class DotExtends implements IEdge {

    private String to;
    private String from;
    private String line;
    private String arrow;

    public DotExtends(String to, String from) {
        this.to = to;
        this.from = from;
        this.arrow = "empty";
        this.line = "solid";
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
    public String toString() {
        return this.to + this.from;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitEdges(this);
    }
}

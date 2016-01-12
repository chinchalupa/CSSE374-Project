package problem.asm;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class DotImplements implements IEdge {

    private String to;
    private String from;
    private String line;
    private String arrow;

    public DotImplements(String to, String from) {
        this.to = to;
        this.from = from;
        this.arrow = "empty";
        this.line = "dashed";
    }

    public DotImplements(String to, String from, String line, String arrow) {
        this.to = to;
        this.from = from;
        this.line = line;
        this.arrow = arrow;
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

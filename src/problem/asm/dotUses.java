package problem.asm;

import problem.asm.IEdge;
import problem.asm.IVisitor;

/**
 * Created by wrightjt on 1/11/2016.
 */
public class DotUses implements IEdge {

    private String to;
    private String from;
    private String line;
    private String arrow;

    public DotUses(String to, String from) {
        this.to = to;
        this.from = from;
        this.line = "dashed";
        this.arrow = "vee";
    }

    public DotUses(String to, String from, String line, String arrow) {
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
    public String toString() {
        return this.to + this.from;
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

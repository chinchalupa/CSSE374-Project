package problem.car.impl;

import problem.car.api.IBody;
import problem.car.api.ICar;
import problem.car.api.IEngine;
import problem.car.api.IWheel;
import problem.car.visitor.IVisitor;
import problem.car.visitor.VisitorAdapter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wrightjt on 12/14/2015.
 */
public class CarXmlOutputStream extends VisitorAdapter {

    private OutputStream out;

    public CarXmlOutputStream(OutputStream out) {
        this.out = out;
    }

    private void write(String s) {
        try {
            s = s + "\n";
            out.write(s.getBytes());
        } catch(IOException e) {
            new RuntimeException(e);
        }
    }

    @Override
    public void preVisit(ICar c) {
        String s = String.format("<car vin=\"%s\" make=\"%s\" model=\"%s\"/>",
                c.getVIN(),
                c.getMake(),
                c.getModel());
        this.write(s);
    }

    @Override
    public void postVisit(ICar c) {
        String s = "</car>";
        this.write(s);
    }

    @Override
    public void visit(IBody b) {
        String s = String.format("<car type=\"%s\" material=\"%s\"/>",
                b.getType(),
                b.getMaterial());
        this.write(s);
    }

    @Override
    public void visit(IEngine e) {
        String s = String.format("<car cylinder=\"%d\" capacity=\"%.2f\"/>",
                e.getCylinder(),
                e.getCapacity());
        this.write(s);
    }

    @Override
    public void visit(IWheel w) {
        String s = String.format("<wheel vendor=%d radius=%.2f width=%.2f />",
                //"<car vendor=%d radius=%.2f width=%.2f/>",
                w.getVendor(),
                w.getRadius(),
                w.getWidth());
        this.write(s);
    }
}

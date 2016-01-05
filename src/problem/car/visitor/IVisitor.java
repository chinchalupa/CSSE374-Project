package problem.car.visitor;

import problem.car.api.IBody;
import problem.car.api.ICar;
import problem.car.api.IEngine;
import problem.car.api.IWheel;

/**
 * Created by wrightjt on 12/14/2015.
 */
public interface IVisitor {
    public void visit(ICar c);
    public void preVisit(ICar c);
    public void postVisit(ICar c);

    public void visit(IBody b);
    public void preVisit(IBody b);
    public void postVisit(IBody b);

    public void visit(IEngine e);
    public void preVisit(IEngine e);
    public void postVisit(IEngine e);

    public void visit(IWheel w);
    public void preVisit(IWheel w);
    public void postVisit(IWheel w);
}

package lab72;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by Jeremy on 1/31/2016.
 */
public class TowerSprite extends AbstractSprite {

    public TowerSprite(double x, double y, double width, double height) {
        super(x, y, width, height);
//        multiSprite.addSprite(new CircleSprite(x, y, width, height));
        multiSprite.addSprite(new RectangleSprite(x+5, y-40, width-10, height-10));
        multiSprite.addSprite(new RectangleSprite(x+10, y-70, width-20, height-20));
        multiSprite.addSprite(new CircleSprite(x+15, y-90, width-30, height-30));
        shape = new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void move(Dimension space) {
        Rectangle2D bounds = this.computeNewBoundsAfterMoving(space);
        shape = new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        for(ISprite s : multiSprite.getListOfSprites()) {
            s.move(space);
        }
    }
}

package lab72;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Jeremy on 1/31/2016.
 */
public class BoxSprite extends AbstractSprite {
    public BoxSprite(double x, double y, double width, double height) {
        super(x, y, width, height);
        shape = new Rectangle2D.Double(x, y, width, height);
        multiSprite.addSprite(new RectangleSprite(x-50, y-50, width, height));
        multiSprite.addSprite(new RectangleSprite(x+50, y+50, width, height));
        multiSprite.addSprite(new RectangleSprite(x-50, y+50, width, height));
        multiSprite.addSprite(new RectangleSprite(x+50, y-50, width, height));
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

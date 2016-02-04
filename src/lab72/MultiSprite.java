package lab72;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by Jeremy on 1/31/2016.
 */
public class MultiSprite {
    public ArrayList<ISprite> listOfSprites;

    public MultiSprite() {
        this.listOfSprites = new ArrayList<>();
    }

    public void addSprite(ISprite sprite) {
        this.listOfSprites.add(sprite);
    }

    public ArrayList<ISprite> getListOfSprites() {
        return listOfSprites;
    }
}

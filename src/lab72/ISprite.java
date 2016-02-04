package lab72;

import java.awt.Dimension;
import java.awt.Shape;
import java.util.ArrayList;

public interface ISprite {
	public void move(Dimension space);
	public Shape getShape();
	public ArrayList<ISprite> getComposingSprites();
}

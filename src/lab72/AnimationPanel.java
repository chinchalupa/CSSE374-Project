package lab72;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import lab72.ISprite;
import lab72.SpriteFactory;

public class AnimationPanel extends JPanel {
	private static final long serialVersionUID = -9141525646098105526L;
	
	private List<ISprite> sprites;
	private volatile boolean animating;
	private long sleepTime;

	public AnimationPanel(long sleepTime) {
		super(true);
		animating = false;
		sprites = Collections.synchronizedList(new ArrayList<ISprite>());
		this.sleepTime = sleepTime;
	}
	
	public void add(ISprite s) {
		sprites.add(s);
		this.repaint();
	}	
	
	@Override
	public Dimension getSize() {
		Dimension d = super.getSize();
		d.width -= SpriteFactory.WIDTH;
		d.height -= SpriteFactory.HEIGHT;
		return d;
	}
	
	public void animate() {
		if(animating)
			return;
		
		animating = true;
		
		Thread animator = new Thread() {
			@Override
			public void run() {
				while(animating) {
					long start = System.currentTimeMillis();

					synchronized(sprites) {
						for(ISprite s: sprites) {
							s.move(getSize());
						}
					}
					repaint();

					long end = System.currentTimeMillis();
					long delta = sleepTime - (end - start);
					delta = (delta > 0)? delta : 0;
					
					try {
						Thread.sleep(delta);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		animator.start();
	}
	
	public void reset() {
		animating = false;
		sprites.clear();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics = (Graphics2D)g;
		synchronized(sprites) {
			for(ISprite s: sprites) {
				if(s.getShape() != null)  {
					graphics.draw(s.getShape());
				}

				if(s.getComposingSprites().size() > 0) {
					System.out.println(s.getComposingSprites().size());
				}
				for(ISprite sprite : s.getComposingSprites()) {
					if(sprite.getShape() != null) {
						graphics.draw(sprite.getShape());
					}
				}
			}
		}
	}
}
